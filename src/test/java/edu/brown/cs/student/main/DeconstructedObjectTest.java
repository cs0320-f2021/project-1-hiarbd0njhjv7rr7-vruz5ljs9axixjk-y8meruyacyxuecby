package edu.brown.cs.student.main;

import org.junit.Assert;
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

  @Test
  public void testGetters() {
    // Testing if the fields were set correctly
    DeconstructedObject<Rent> rent = new DeconstructedObject<>(initRent());
    System.out.println(rent.getClassName());
    assertEquals("edu.brown.cs.student.main.Rent", rent.getClassName());
    assertArrayEquals(new String[]{"fit", "userId", "itemId", "rating", "rentedFor", "category", "size", "id"},
        rent.getColumns());
    assertArrayEquals(new String[]{"tight", "1", "11", "3", "vacation", "dress", "large", "0"},
        rent.getValues());
    assertArrayEquals(new String[]{"TEXT", "INTEGER", "INTEGER", "INTEGER", "TEXT", "TEXT", "TEXT", "INTEGER"},
        rent.getDatatypes());
  }

}
