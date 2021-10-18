package edu.brown.cs.student.main.ORM;

import edu.brown.cs.student.main.DataTypes.IDataType;
import edu.brown.cs.student.main.PrintHelper;
import edu.brown.cs.student.main.DataTypes.Rent;
import edu.brown.cs.student.main.DataTypes.Review;
import edu.brown.cs.student.main.DataTypes.User;

import java.lang.reflect.Constructor;
import java.util.List;


public class ORM {
  private IDatabase database;

  public ORM(String dbName){
    this.database = new SQLite3Database(dbName);
  }

  public <T extends IDataType> boolean insert(T object){

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

  /**
   * Helper method to get constructor of any class declared in the project
   * @param className name of Class constructor to get
   * @param <T> - generic datatype extending IDataType
   * @return constructor of target class
   * @throws ClassNotFoundException - when class not found
   */
  private <T extends IDataType> Constructor<? extends T> getConstructor(String className) throws ClassNotFoundException {
    String pkgName = "edu.brown.cs.student.main.DataTypes.";
    Class<?> tClass = Class.forName(pkgName+className);
    for (Constructor<?> cxtor : tClass.getConstructors()) {
      return (Constructor<? extends T>) cxtor;
    }
    return null;
  }

  private String getClassNameFromQuery(String sqlQuery){
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

    targetClass = targetClass.substring(0, 1).toUpperCase() + targetClass.substring(1);
    if (targetClass.endsWith("s")){
      targetClass = targetClass.substring(0, targetClass.length() - 1);
    }
    return targetClass;
  }


  /**
   * Runs a given sqlQuery or update to the SQL database
   * @param sqlQuery - query to run
   * @param <T> - Datatype to return
   * @throws ClassNotFoundException - when class is not founds
   * @return List with results of query if a query, otherwise null
   */
  public <T extends  IDataType> List<T> sql(String sqlQuery) throws ClassNotFoundException {
    if (sqlQuery.toUpperCase().contains("SELECT")) {
      Constructor<? extends T> constructor = null;

      String targetClass = getClassNameFromQuery(sqlQuery);
      constructor = getConstructor(targetClass);

      if (constructor == null) {
        return null;
      }

      return this.database.runQuery(sqlQuery, constructor);
    } else {
      this.database.runUpdate(sqlQuery);
      return null;
    }
  }

}
