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

    return res > 0;
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
    Constructor<? extends T> constructor = null;
    for (Constructor<?> cxtor : classObject.getConstructors()){
      constructor = (Constructor<? extends T>) cxtor;
    }
    String select = this.database.generateSelectStatement(tableName, newCondition);
    return this.database.runQuery(select, constructor);
  }

  public <T extends  IDataType> boolean update(T object, String condition, String value) {
    String newCondition = condition + "=" + value;
    return updateHelper(object, condition, newCondition);
  }

  public <T extends  IDataType> boolean update(T object, String condition) {
    String newCondition = "";
    return updateHelper(object, condition, newCondition);
  }

  /**
   * update helper, to reduce repeated code, finds parameters to pass to update
   * statement.
   * @param object - object to update
   * @param condition - condition to update on
   * @param newCondition - condition statement
   * @return boolean representing success
   */
  private <T extends IDataType> boolean updateHelper(T object, String condition, String newCondition) {
    DeconstructedObject<T> obj = new DeconstructedObject<T>(object);
    String tableName = obj.getClassName().toLowerCase();
    String[] columns = obj.getColumns();
    String[] values = obj.getValues();

    int index = -1;

    for (int i = 0; i < columns.length; i++) {
      if (condition.equals(columns[i])) {
        index = i;
        if (newCondition.equals("")) {
          newCondition = condition + "=" + values[i];
        }
        break;
      }
    }

    if (index == -1) {
      PrintHelper.printlnRed("Condition not found.");
      return false;
    }

    String[] newColumns = new String[columns.length-1];
    String[] newValues = new String[values.length-1];

    int adjust = 0;

    for (int j = 0; j < columns.length; j++) {
      if (j == index) {
        adjust = 1;
      } else {
        newColumns[j-adjust] = columns[j];
        newValues[j-adjust] = values[j];
      }
    }

    String query = this.database.generateUpdateStatement(tableName, newCondition,
        newColumns, newValues);
    int res = this.database.runUpdate(query);

    return res > 0;
  }
  public <T extends  IDataType> List<T> sql(String sqlQuery) {
    if (sqlQuery.toUpperCase().contains("SELECT")){
      if (sqlQuery.contains("FROM user")){
        Constructor<? extends T> constructor = null;

        for (Constructor<?> cxtor : User.class.getConstructors()){
          constructor = (Constructor<? extends T>) cxtor;
        }

        return this.database.runQuery(sqlQuery, constructor);
      } else if (sqlQuery.contains("FROM rent")){
        Constructor<? extends T> constructor = null;

        for (Constructor<?> cxtor : Rent.class.getConstructors()){
          constructor = (Constructor<? extends T>) cxtor;
        }

        return this.database.runQuery(sqlQuery, constructor);
      } else if (sqlQuery.contains("FROM review")){
        Constructor<? extends T> constructor = null;

        for (Constructor<?> cxtor : Review.class.getConstructors()){
          constructor = (Constructor<? extends T>) cxtor;
        }

        return this.database.runQuery(sqlQuery, constructor);
      }
    } else {
      this.database.runUpdate(sqlQuery);
      return null;
    }
    return null;
  }

}
