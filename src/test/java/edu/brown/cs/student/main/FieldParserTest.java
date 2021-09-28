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
    assertEquals(fp.parseHeight("6' 10\""), 6);
    assertEquals(fp.parseHeight("5' 3\""), 3);
    assertEquals(fp.parseHeight("5' 2\""), 3);
    assertEquals(fp.parseHeight("5' 4\""), 3);
    //TODO: fix 5'1 and 6'1
  }

  @Test
  public void testAge(){

  }

  @Test
  public void testBody(){

  }

  @Test
  public void testHoroscope(){

  }
}
