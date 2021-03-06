package edu.brown.cs.student.main.ORM;

import edu.brown.cs.student.main.DataTypes.IDataType;
import edu.brown.cs.student.main.PrintHelper;

import java.lang.reflect.Constructor;
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

public class SQLite3Database implements IDatabase {

  private Connection conn;
  private final String classPrefix = "edu.brown.cs.student.main.";
  public SQLite3Database(String dbName) {
    this.connectToDatabase(dbName);
  }

  protected Connection establishConnection(String dbName) {
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
      System.out.println("ERROR: tableExists threw SQLException " + e);
    }
    return false;
  }

  @Override
  public String generateCreateStatement(String tableName, String[] columns, String[] datatypes) {
    // if no table, call generateCreate
    StringBuilder create = new StringBuilder("CREATE TABLE IF NOT EXISTS "
                                              + tableName + "(");
    String delim = ",";

    for (int i = 0; i < columns.length; i++) {
      String currCol = columns[i];
      String currType = datatypes[i];
      create.append(columns[i]).append(" ").append(datatypes[i]);
      if (i == columns.length - 1) {
        delim = ");";
      }
      create.append(delim);
    }

    return create.toString();
  }

  @Override
  public String generateInsertStatement(String tableName, String[] columns,
                                        String[] values) {
    StringBuilder insert = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
    String delim = "";

    for (int i = 0; i < columns.length; i++) {
      String currValue = values[i];
      insert.append(delim);

      delim = ", ";
      insert.append(currValue);
    }

    insert.append(");");

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
      delim = " AND ";
    }

    update.append(set);

    if (!condition.equals("")) {
      update.append(where);
    }
    update.append(";");
    return update.toString();
  }

  @Override
  public String generateDeleteStatement(String tableName, String[] columns, String[] values) {
    StringBuilder delete = new StringBuilder("DELETE FROM " + tableName + "\n" + "WHERE\n");
    String delim = "";

    for (int i = 0; i < columns.length; i++) {
      delete.append(delim).append(columns[i]).append("=").append(values[i]);
      delim = " AND ";
    }

    delete.append(";");
    return delete.toString();
  }

  @Override
  public String generateSelectStatement(String tableName, String condition) {
    return "SELECT * FROM " + tableName + " WHERE " + condition + ";";
  }

  private String getTableNameFromQuery(String sqlQuery){
    String[] queryWords = sqlQuery.split(" ");
    String targetClass = null;
    for (int i = 0; i < queryWords.length; i++) {
      // check if FROM is not the last word
      if (queryWords[i].equals("FROM") && (i + 1 < queryWords.length)) {
        targetClass = queryWords[i + 1];
      }
    }

    // Do we want this to happen?
    if (targetClass == null) {
      return null;
    }

    return targetClass;
  }

  @Override
  public <T extends IDataType> List<T> runQuery(String query, Constructor<? extends T> constructor) {
    PreparedStatement prep;

    try {
      HashMap<String, String> columnNameToDataType = new HashMap<String, String>();
      HashMap<Integer, String> columnIndexToName = new HashMap<Integer, String>();
      String tableName = getTableNameFromQuery(query);

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
              convertedArgs[columnIndex] = rs.getInt(columnIndex+1);
            } else if (currDataType.equals("TEXT")) {
              convertedArgs[columnIndex] = rs.getString(columnIndex+1);
            }
          }

          returnList.add(constructor.newInstance(convertedArgs));
        }
      }
      prep.close();

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
      int res = prep.executeUpdate();
      prep.close();
      return res;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

}
