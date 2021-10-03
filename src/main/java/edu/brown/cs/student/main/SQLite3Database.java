package edu.brown.cs.student.main;

import javax.xml.transform.Result;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SQLite3Database implements IDatabase {

  private Connection conn;

  public SQLite3Database(String dbName){
    this.connectToDatabase(dbName);
  }

  protected Connection establishConnection(String dbName){
    try {
      Class.forName("org.sqlite.JDBC");
      return DriverManager.getConnection("jdbc:sqlite:" + dbName);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public boolean connectToDatabase(String dbName) {
    conn = this.establishConnection(dbName);
    return conn != null;
  }

  @Override
  public boolean tableExists(String tableName) {
    try {
      DatabaseMetaData meta = conn.getMetaData();
      ResultSet resultSet = meta.getTables(null, null, tableName.toUpperCase(), null);
      return resultSet.next();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ERROR: tableExists SQLException " + e);
    }
    return false;
  }

  @Override
  public String generateCreateStatement(String tableName, String[] columns, String[] datatypes) {
    // if no table, call generateCreate
    StringBuilder create = new StringBuilder("CREATE TABLE IF NOT EXISTS "
                                              + tableName + "(");
    String delim = ",\n";

    for (int i = 0; i < columns.length; i++) {
      String currCol = columns[i];
      String currType = datatypes[i];
      create.append(columns[i] + " " + datatypes[i]);
      if (i == columns.length - 1) delim = ");";
      create.append(delim);
    }

    return create.toString();
  }

  @Override
  public String generateInsertStatement(String tableName, String[] columns,
                                        String[] values, String[] types) {
    StringBuilder insert = new StringBuilder("INSERT INTO " + tableName + " (");
    StringBuilder placeholder = new StringBuilder(" VALUES \n (");
    String delim = "";

    for (int i = 0; i < columns.length; i++) {
      String currColumn = columns[i];
      String currValue = values[i];
      insert.append(delim);
      placeholder.append(delim);
      delim = ", ";
      insert.append(currColumn);
      if (types[i].equals("TEXT")) {
        currValue = "'" + currValue + "'";
      }
      placeholder.append(currValue);
    }

    placeholder.append(");");
    insert.append(")\n");
    insert.append(placeholder.toString());

    return insert.toString();
  }

  @Override
  public String generateUpdateStatement(String tableName, String condition, String[] checkColumns, String[] checkValues) {
    StringBuilder update = new StringBuilder("UPDATE " + tableName + "\n");
    String set = "SET " + condition;
    StringBuilder where = new StringBuilder("\nWHERE\n");
    String delim = "";

    for (int i = 0; i < checkColumns.length; i++) {
      String currColumn = checkColumns[i];
      String currValue = checkValues[i];
      where.append(delim);
      where.append(currColumn);
      where.append(" = ");
      where.append(currValue);
      delim = ", ";
    }

    update.append(set);

    if (!condition.equals("")){
      update.append(where);
    }
    update.append(";");
    return update.toString();
  }

  @Override
  public String generateDeleteStatement(String tableName, String[] columns, String[] values) {
    StringBuilder delete = new StringBuilder("DELETE FROM " + tableName + "\n");
    StringBuilder where = new StringBuilder("\nWHERE\n");
    String delim = "";

    for (int i = 0; i < columns.length; i++) {
      where.append(delim).append(columns[i]).append("=").append(values[i]);
      delim = " AND ";
    }

    delete.append(";");
    return delete.toString();
  }

  @Override
  public String generateSelectStatement(String tableName, String condition) {
    return "SELECT * FROM " + tableName + " WHERE " + condition + ";";
  }


  @Override
  public <T extends IDataType> List<T> runQuery(String query, Constructor<? extends T> constructor) {
    PreparedStatement prep;

    try {
      HashMap<String, String> columnNameToDataType = new HashMap<String, String>();
      HashMap<Integer, String> columnIndexToName = new HashMap<Integer, String>();
      String tableName = constructor.getName().toLowerCase();
      String getColumnTypesQuery = "PRAGMA table_info(" + tableName +");";
      prep = conn.prepareStatement(getColumnTypesQuery);
      ResultSet rs = prep.executeQuery();

      if (!rs.isClosed()) {
        while (rs.next()) {
          Integer columnIndex = rs.getInt(1);
          String columnName = rs.getString(2);
          String columnType = rs.getString(3);
          columnNameToDataType.put(columnName, columnType);
          columnIndexToName.put(columnIndex, columnName);
        }
      }

      prep.close();
      rs.close();

      prep = conn.prepareStatement(query);
      rs = prep.executeQuery();

      List<T> returnList = new ArrayList<T>();

      if (!rs.isClosed()) {
        while (rs.next()) {
          Object[] convertedArgs = new Object[columnNameToDataType.keySet().size()];

          for (Integer columnIndex : columnIndexToName.keySet()) {
            String currColumnName = columnIndexToName.get(columnIndex);
            String currDataType = columnNameToDataType.get(currColumnName);
            if (currDataType.equals("INTEGER")) {
              convertedArgs[columnIndex]=rs.getInt(columnIndex+1);
            } else if (currDataType.equals("TEXT")) {
              convertedArgs[columnIndex]=rs.getString(columnIndex+1);
            }
          }

          returnList.add(constructor.newInstance(convertedArgs));
        }
      }

      return returnList;
    } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int runUpdate(String query) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(query);
      prep.close();
      int res = prep.executeUpdate();
      prep.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

}
