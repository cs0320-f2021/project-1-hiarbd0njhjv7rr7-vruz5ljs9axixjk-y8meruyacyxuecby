package edu.brown.cs.student.main;

public class ORM {
  private IDatabase database;

  public ORM(String dbName){
    this.database = new SQLite3Database(dbName);
  }

}
