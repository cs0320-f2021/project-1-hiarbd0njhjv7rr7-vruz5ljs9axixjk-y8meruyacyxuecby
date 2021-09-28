package edu.brown.cs.student.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
  public String generateCreateStatement(String tableName, String[] attributes, String[] datatypes) {
    return null;
  }

  @Override
  public String generateInsertStatement(String tableName, String[] columns, String[] values) {
    StringBuilder insert = new StringBuilder("INSERT INTO " + tableName +" (");
    StringBuilder placeholder = new StringBuilder(" VALUES \n (");
    String delim = "";

    for (int i = 0; i < columns.length; i++) {
      String currColumn = columns[i];
      String currValue = values[i];
      insert.append(delim);
      placeholder.append(delim);
      delim = ", ";
      insert.append(currColumn);
      placeholder.append(currValue);
    }

    placeholder.append(");");
    insert.append(")\n");
    insert.append(placeholder.toString());

    return insert.toString();
  }

  @Override
  public String generateUpdateStatement(String tableName, String condition, String[] columns,
                                        String[] new_values) {
    StringBuilder update = new StringBuilder("UPDATE " + tableName + "\n");
    StringBuilder set = new StringBuilder("SET ");
    String where = "\nWHERE\n" + condition;
    String delim = "";

    for (int i = 0; i < columns.length; i++) {
      String currColumn = columns[i];
      String currValue = new_values[i];
      set.append(delim);
      set.append(currColumn);
      set.append(" = ");
      set.append(currValue);
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
  public String generateDeleteStatement(String tableName, String condition) {
    StringBuilder delete = new StringBuilder("DELETE FROM " + tableName + "\n");
    String where = "\nWHERE\n" + condition;

    if (!condition.equals("")){
      delete.append(where);
    }
    delete.append(";");
    return delete.toString();
  }


  @Override
  public ResultSet runQuery(String query) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(query);
      ResultSet rs = prep.executeQuery();
      prep.close();
      return rs;
    } catch (SQLException e) {
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
