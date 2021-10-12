package edu.brown.cs.student.main.ORM;


import edu.brown.cs.student.main.DataTypes.Review;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.List;

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

    String selectStatement = "SELECT * FROM review WHERE id=4;";

    assertEquals(selectStatement,
        emptyDB.generateSelectStatement("review", "id=4"));

    emptyDB.runUpdate(reviewStatement);

    assertTrue(emptyDB.tableExists("review"));

    emptyDB.runUpdate(insertStatement);
    // to test this I manually looked at the Database, and the data does
    // show up


    Constructor<Review> constructor = null;

    for (Constructor<?> cxtor : Review.class.getConstructors()){
      constructor = (Constructor<Review>) cxtor;
    }

    String newInsertStatement = emptyDB.generateInsertStatement("review",
        reviewColumns,
        new String[]{"4", "'5/5/12'", "'nice clothes'", "'bottom text'"});
    emptyDB.runUpdate(newInsertStatement);
    List<Review> reviewList = emptyDB.runQuery(selectStatement, constructor);
    Review testRev = new Review(4, "5/5/12",
        "'nice clothes'", "'reviewText'");
    assertFalse(reviewList.contains(testRev));

  }
}
