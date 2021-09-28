package edu.brown.cs.student.main;

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
   * @param condition - the condition to check on for the update (if no condition, pass empty string)
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
   * @param conditions - the conditions to check on for the delete query (formatted as `column_name = condition`)
   * @return - the String representing the SQL delete query for the specified conditions and values
   */
  String generateDeleteStatement(String tableName, String[] conditions);

  ResultSet runQuery(String query);
  int runUpdate(String query);
  int runDelete(String query);
  int runCreate(String query);

}
