package edu.brown.cs.student.main;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ORMTest {
  @Test
  public void testInsertDelete() {
    String testDB = "data/testDB/ORMtest.sqlite3";
    String dbName = ManageDB.create(testDB);
    ORM orm = new ORM(dbName);
    Review testRev = new Review(4, "5/5/12",
        "nice clothes", "reviewText");
    assertTrue(orm.insert(testRev));

    List<Review> rList = orm.where("id=?", "4", Review.class);
    assertTrue(rList.contains(testRev));

    assertTrue(orm.delete(testRev));

    rList = orm.where("id=?", "4", Review.class);
    assertFalse(rList.contains(testRev));
    assertFalse(orm.delete(testRev));
    ManageDB.delete(testDB);
  }

  @Test
  public void testUpdate() {
    String testDB = "data/testDB/UpdateTest.sqlite3";
    String dbName = ManageDB.create(testDB);
    ORM orm = new ORM(dbName);
    Review testRev = new Review(4, "5/5/12",
        "nice clothes", "reviewText");
    assertTrue(orm.insert(testRev));
    testRev.setId(6);

    assertTrue(orm.update(testRev, "id"));
    assertTrue(orm.where("id=?", "6", Review.class).contains(testRev));
    assertTrue(orm.update(testRev, "id", "5"));
    Review testRev5 = new Review(5, "5/5/12",
        "nice clothes", "reviewText");
    assertTrue(orm.where("id=?", "5", Review.class).contains(testRev5));

    ManageDB.delete(testDB);
  }



}
