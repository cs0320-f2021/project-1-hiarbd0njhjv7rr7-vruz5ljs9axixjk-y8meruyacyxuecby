package edu.brown.cs.student.main;

import java.sql.Connection;
import java.sql.DriverManager;
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
    if (conn == null){
      return false;
    }
    return true;
  }

  @Override
  public String generateCreateStatement(String tableName, String[] attributes, String[] datatypes) {
    return null;
  }

  @Override
  public String generateInsertStatement(String tableName, String[] columns, String[] values) {
    return null;
  }

  @Override
  public String generateUpdateStatement(String tableName, String[] conditions, String[] columns,
                                        String[] new_values) {
    return null;
  }

  @Override
  public String generateDeleteStatement(String tableName, String[] conditions) {
    return null;
  }


  @Override
  public ResultSet runQuery(String query) {
    return null;
  }

  @Override
  public int runUpdate(String query) {
    return 0;
  }

  @Override
  public int runDelete(String query) {
    return 0;
  }

  @Override
  public int runCreate(String query) {
    return 0;
  }
}
