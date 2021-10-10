package edu.brown.cs.student.main;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class BloomTest {

  @Test
  public void testAll() throws IOException {
    BloomList bl = new BloomList(); /** three filters from justusersSMALL */
    BloomFilter user1 = new BloomFilter("145lbs", "34b", "5' 9\"", 27, "athletic", "Libra", "151944");
    BloomFilter user2 = new BloomFilter("114lbs", "32b", "5' 3\"", 33, "petite", "Scorpio", "154309");
    BloomFilter user3 = new BloomFilter("135lbs", "34b", "5' 3\"", 33, "athletic", "Sagittarius", "185966");
    bl.insert(user1);
    bl.insert(user2);
    bl.insert(user3);
    assertEquals(bl.size(), 3);
    bl.findKSimilar(user2, 1);
  }
}