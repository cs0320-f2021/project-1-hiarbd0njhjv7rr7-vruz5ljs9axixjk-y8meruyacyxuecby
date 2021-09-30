package edu.brown.cs.student.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class ORM {
  private IDatabase database;

  public ORM(String dbName){
    this.database = new SQLite3Database(dbName);
  }

  public <T extends  IDataType> boolean insert(T object){

    DeconstructedObject<T> obj = new DeconstructedObject<T>(object);
    String tableName = obj.getClassName().toLowerCase();
    String[] columns = obj.getColumns();
    String[] values = obj.getValues();
    String[] datatypes = obj.getDatatypes();

    if (!this.database.tableExists(tableName)){
      String create = database.generateCreateStatement(tableName, columns, datatypes);
      int res = database.runUpdate(create);
    }
    String query = database.generateInsertStatement(tableName, columns, values);
    int res = database.runUpdate(query);

    if (res > 0){
      return true;
    }
    return false;
  }

  public <T extends  IDataType> boolean delete(T object){
    DeconstructedObject<T> obj = new DeconstructedObject<T>(object);
    String tableName = obj.getClassName().toLowerCase();
    String[] columns = obj.getColumns();
    String[] values = obj.getValues();
    String query = database.generateDeleteStatement(tableName, columns, values);
    int res = database.runUpdate(query);

    if (res > 0){
      return true;
    }
    return false;

  }

  public <T extends  IDataType> List<T> where(String condition, String value, Class<T> classObject){
    String newCondition = condition.replace("?", value);
    String tableName = classObject.getSimpleName().toLowerCase();
    Constructor<?> constructor;
    for (Constructor<?> cxtor : classObject.getConstructors()){
      constructor = cxtor;
    }
    String select = this.database.generateSelectStatement(tableName, newCondition);
    this.database.runQuery(select, constructor);
    return null;
  }

  public <T extends  IDataType> boolean update(T object, String condition, String value) {
    return false;
  }

  public <T extends  IDataType> boolean update(T object, String condition) {
    return false;
  }

  public <T extends  IDataType> List<T> sql(String sqlQuery) {
    if (sqlQuery.toUpperCase().contains("SELECT")){
//      this.database.runQuery(sqlQuery, T) ;
    } else {
      this.database.runUpdate(sqlQuery);
    }
    return null;
  }

}
