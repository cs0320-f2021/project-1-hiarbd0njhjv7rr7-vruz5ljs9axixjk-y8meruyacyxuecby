package edu.brown.cs.student.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SQLite3DatabaseTest {

  @Test
  public void testFunctionality(){
    SQLite3Database emptyDB = new SQLite3Database("data/testDB/test.sqlite3");
    assertFalse(emptyDB.tableExists("Rent"));
    String[] reviewColumns = new String[]{"id", "reviewDate", "reviewSummary", "reviewText"};
    String[] reviewTypes = new String[]{"INTEGER", "TEXT", "TEXT", "TEXT"};
    String reviewStatement = "CREATE TABLE IF NOT EXISTS review("
        + "id INTEGER,"
        + "reviewDate TEXT,"
        + "reviewSummary TEXT,"
        + "reviewText TEXT);";

    assertEquals(reviewStatement, emptyDB.generateCreateStatement("review",
        reviewColumns,
        new String[]{"INTEGER", "TEXT", "TEXT", "TEXT"}));

    String insertStatement = "INSERT INTO review VALUES (" +
        "2, '5/5/12', 'nice clothes', 'bottom text');";

    assertEquals(insertStatement, emptyDB.generateInsertStatement("review",
        reviewColumns,
        new String[]{"2", "'5/5/12'", "'nice clothes'", "'bottom text'"}));

    String deleteStatement = "DELETE FROM review\n"
        + "WHERE\n"
        + "id=4 AND reviewDate='5/5/12' AND reviewSummary='nice clothes' AND reviewText='bottom text';";

    assertEquals(deleteStatement,
        emptyDB.generateDeleteStatement("review",
            new String[]{"id", "reviewDate", "reviewSummary", "reviewText"},
            new String[]{"4", "'5/5/12'", "'nice clothes'", "'bottom text'"}));
  }
}
