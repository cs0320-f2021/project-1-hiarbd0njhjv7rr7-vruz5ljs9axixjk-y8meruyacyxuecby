package edu.brown.cs.student.main;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class FieldParserTest {

  @Test
  public void testWeight(){
    FieldParser fp = new FieldParser();
    assertEquals(fp.parseWeight("105lbs"), 1);
    assertEquals(fp.parseWeight("110lbs"), 1);
    assertEquals(fp.parseWeight("177lbs"), 8);
    assertEquals(fp.parseWeight("215lbs"), 10);
  }

  @Test
  public void testBust(){

  }

  @Test
  public void testHeight(){
    FieldParser fp = new FieldParser();
    assertEquals(fp.parseHeight("5' 8\""), 5);
    assertEquals(fp.parseHeight("5' 11\""), 2);
    assertEquals(fp.parseHeight("6' 0\""), 2);
    assertEquals(fp.parseHeight("6' 1\""), 7);
    assertEquals(fp.parseHeight("6' 3\""), 7);
    assertEquals(fp.parseHeight("6' 10\""), 6);
    assertEquals(fp.parseHeight("5' 3\""), 3);
    assertEquals(fp.parseHeight("5' 2\""), 3);
    assertEquals(fp.parseHeight("5' 1\""), 3);
    assertEquals(fp.parseHeight("5' 4\""), 4);
  }

  @Test
  public void testAge(){
    FieldParser fp = new FieldParser();
    assertEquals(fp.parseAge(15), 1);
    assertEquals(fp.parseAge(69), 11);
    assertEquals(fp.parseAge(42), 6);
    assertEquals(fp.parseAge(23), 2);
    assertEquals(fp.parseAge(24), 2);
  }

  @Test
  public void testBody(){
    FieldParser fp = new FieldParser();
    assertEquals(fp.parseBody("apple"), 7);
    assertEquals(fp.parseBody("hourglass"), 3);
    assertEquals(fp.parseBody("straight & narrow"), 5);
  }

  @Test
  public void testHoroscope(){
    FieldParser fp = new FieldParser();
    assertEquals(fp.parseHoroscope("Leo"), 5);
  }
}
