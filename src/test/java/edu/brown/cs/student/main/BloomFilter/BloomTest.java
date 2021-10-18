package edu.brown.cs.student.main.BloomFilter;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class BloomTest {

  @Test
  public void test() throws IOException {
    BloomList bl = new BloomList(); /** three filters from justusersSMALL */
    BloomFilter user1 = new BloomFilter("145lbs", "34b", "5' 9\"", 27, "athletic", "Libra", "151944");
    BloomFilter user2 = new BloomFilter("114lbs", "32b", "5' 3\"", 33, "petite", "Scorpio", "154309");
    BloomFilter user3 = new BloomFilter("135lbs", "34b", "5' 3\"", 33, "athletic", "Sagittarius", "185966");
    BloomFilter user4 = new BloomFilter("200lbs", "36b", "6' 4\"", 19, "pear", "Leo", "123456");
    bl.insert(user1);
    bl.insert(user2);
    bl.insert(user3);
    bl.insert(user4);
    assertEquals(bl.size(), 4);
    /** prints k similar users to users 1, 2, and 3 in the bloom filter */
    bl.findKSimilar(user1, 4); // THIS WORKS!
    bl.findKSimilar(user3, 4); // THIS WORKS!
    bl.findKSimilar(user2, 5); // THIS WORKS!
    bl.findKSimilar(user4, 4);
  }
}