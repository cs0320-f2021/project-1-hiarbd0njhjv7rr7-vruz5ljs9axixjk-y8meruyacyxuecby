package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  @Test
  public void negativeSubtraction(){
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.subtract(50, 100);
    assertEquals(-50, output, 0.01);
  }

  @Test
  public void supernegativeSubtraction(){
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.subtract(50, 1000000);
    assertEquals(-999950, output, 0.01);
  }

  @Test
  public void negativeAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(-10, 15);
    assertEquals(5, output, 0.01);
  }

  @Test
  public void subtractionviaAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(-10, 3);
    assertEquals(-7, output, 0.01);
  }
}
