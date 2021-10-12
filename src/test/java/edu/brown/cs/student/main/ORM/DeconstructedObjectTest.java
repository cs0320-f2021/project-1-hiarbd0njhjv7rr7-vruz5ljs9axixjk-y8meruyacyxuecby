package edu.brown.cs.student.main.ORM;

import edu.brown.cs.student.main.DataTypes.Rent;
import edu.brown.cs.student.main.DataTypes.User;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class DeconstructedObjectTest {

  private Rent initRent() {
    return new Rent("tight",
        1,
        11,
        3,
        "vacation",
        "dress",
        "large",
        0);
  }

  private User initUser() {
    return new User(200, 154, "small", 170, 20,
        "stocky", "libra");
  }

  @Test
  public void testGetters() {
    // Testing if the fields were set correctly
    DeconstructedObject<Rent> rent = new DeconstructedObject<>(initRent());
    assertEquals("Rent", rent.getClassName());
    assertArrayEquals(new String[]{"fit", "userId", "itemId", "rating", "rentedFor", "category", "size", "id"},
        rent.getColumns());
    assertArrayEquals(new String[]{"'tight'", "1", "11", "3", "'vacation'", "'dress'", "'large'", "0"},
        rent.getValues());
    assertArrayEquals(new String[]{"TEXT", "INTEGER", "INTEGER", "INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER"},
        rent.getDatatypes());

    DeconstructedObject<User> user = new DeconstructedObject<>(initUser());
    assertEquals("User", user.getClassName());
    assertArrayEquals(new String[]{"user_id", "weight", "bust_size", "height", "age", "body_type", "horoscope"},
        user.getColumns());
    assertArrayEquals(new String[]{"200", "154", "'small'", "170", "20", "'stocky'", "'libra'"},
        user.getValues());
    assertArrayEquals(new String[]{"INTEGER", "INTEGER", "TEXT", "INTEGER", "INTEGER", "TEXT", "TEXT"},
        user.getDatatypes());
  }

}
