package edu.brown.cs.student.main;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;

public interface IDatabase {

  /**
   * connectToDatabase establishes a connection with the database
   *
   * @param dbName - the name of the database to connect to
   * @return - true if connected successfully, false otherwise
   */
  boolean connectToDatabase(String dbName);

  /**
   * generateCreateStatement takes in a tableName, an array of attributes and
   * corresponding datatypes and returns an SQL create query representing the inputs.
   * attributes and datatypes should have the same length, and each attribute
   * at index i in attributes should correspond to the datatupe stored in index i
   * in datatypes
   *
   * @param tableName - the name of the table to create
   * @param attributes - the attributes or column names to be part of the database
   * @param datatypes - the datatypes of each column
   * @return - the String represented the SQL Create Table query for the specified attributes
   */
  String generateCreateStatement(String tableName, String[] attributes, String[] datatypes);

  /**
   * generateInsertStatement takes in a tableName, an array of columns and
   * corresponding values and returns an SQL insert query representing to the inputs.
   * columns and values should have the same length, and each attribute
   * at index i in attributes should correspond to the datatupe stored in index i
   * in datatypes
   *
   * @param tableName - the name of the table to insert a new row to
   * @param columns - the attributes or column names to be inserted for the table
   * @param values - the values to be inserted into each column
   * @return - the String representing the SQL Insert query for the specified attributes
   */
  String generateInsertStatement(String tableName, String[] columns, String[] values);

  /**
   * generateUpdateStatement takes in a tableName, an array of conditions, and
   * corresponding values and returns an SQL insert query representing to the inputs.
   * columns and values should have the same length, and each attribute
   * at index i in attributes should correspond to the datatupe stored in index i
   * in datatypes
   *
   * @param tableName - the table to be updated
   * @param condition - the condition to check on for the update (formatted as `column_name = condition`,
   *                  if no condition, pass empty string)
   * @param columns - the attributes or column names to be updated
   * @param new_values - the values to be updated in each column
   * @return - the String representing the SQL update query for the specified conditions and values
   */
  String generateUpdateStatement(String tableName, String condition, String[] columns, String[] new_values);

  /**
   * generateDeleteStatement takes in a tableName, an array of conditions, and
   * corresponding values and returns an SQL insert query representing to the inputs.
   * columns and values should have the same length, and each attribute
   * at index i in attributes should correspond to the datatupe stored in index i
   * in datatypes
   *
   * @param tableName - the table to be deleted
   * @param condition - the condition to check on for the delete query (formatted as `column_name = condition`,
   *                  if no condition, pass empty string)
   * @return - the String representing the SQL delete query for the specified conditions and values
   */
  String generateDeleteStatement(String tableName, String[] columns, String[] values);

  String generateSelectStatement(String tableName, String condition);

  /**
   * runQuery is used to execute a query on the database
   *
   * @param query - the SQL query to be executed
   * @return - the ResultSet from executing the query
   */
  <T extends IDataType> List<T> runQuery(String query, Constructor<T> constructor);

  /**
   * runQuery is used to execute an update on the database (i.e. update, delete, create queries)
   *
   * @param query - the SQL query to be executed
   * @return - the result from executing the update
   */
  int runUpdate(String query);

  /**
   * Checks if a table with the given name exists within the database
   * @param tableName to check if exists
   * @return boolean saying whether table exists or not
   */
  boolean tableExists(String tableName);


}
