package edu.brown.cs.student.main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to create database files for testing purposes
 * and then delete after finished with testing.
 */
public final class ManageDB {

  private ManageDB() { }

  /**
   *  Method to create a test database.
   * @param fileName - file name
   * @return String - file path to the database
   */
  public static String create(String fileName) {
    String url = "jdbc:sqlite:" + fileName;

    try (Connection conn = DriverManager.getConnection(url)) {
      return fileName;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println("ERROR: Database not created");
    }
    return null;
  }

  /**
   * Deletes database.
   * @param fileName - to delete
   * @return boolean
   */
  public static boolean delete(String fileName) {
    return new File(fileName).delete();
  }
}
