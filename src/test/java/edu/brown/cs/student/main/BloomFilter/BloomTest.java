package edu.brown.cs.student.main.BloomFilter;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class BloomTest {

  @Test
  public void usersTest() throws IOException {
    BloomList bl = new BloomList(); /** filters from justusersSMALL */
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
    System.out.println(bl.findKSimilar(user1, 4)); // THIS WORKS!
    System.out.println(bl.findKSimilar(user3, 4)); // THIS WORKS!
    System.out.println(bl.findKSimilar(user2, 5)); // THIS WORKS!
    System.out.println(bl.findKSimilar(user4, 4));
  }

  @Test
  public void studentsTest() throws IOException {
    BloomList bl = new BloomList();
    BloomFilter student1 = new BloomFilter("1", "Personally", "Junior", "Early morning (6:00AM - 9:00AM); Late Night (12:00AM - 3:00AM)", "", "", "", "Honesty", "Cold", "Politics");
    BloomFilter student2 = new BloomFilter("2", "Personally", "Grad", "Early morning (6:00AM - 9:00AM); Late Night (12:00AM - 3:00AM)", "", "", "", "Honesty", "Slacker", "Literature");
    BloomFilter student3 = new BloomFilter("14", "Virtually", "Grad", "Early morning (6:00AM - 9:00AM); Late morning (9:00AM - 12:00PM); Early Afternoon (12:00PM - 3:00PM)", "C++", "None", "No", "Knowledge", "Selfish", "Music");
    BloomFilter student4 = new BloomFilter("17", "Personally", "Freshman", "Early morning (6:00AM - 9:00AM); Late Afternoon (3:00PM - 6:00PM); Evening (6:00PM - 9:00PM); Night (9:00PM - 12:00AM)", "JavaScript", "BIPOC; Woman", "Yes", "Quick Learner", "Cold", "Biology");
    BloomFilter testStudent = new BloomFilter("12345", "Personally", "Freshman", "Early morning (6:00AM - 9:00AM)", "JavaScript", "BIPOC", "Yes", "Honesty", "Procrastinator", "Music");
    bl.insert(student1);
    bl.insert(student2);
    bl.insert(student3);
    bl.insert(student4);
    /* assertEquals(bl.size(), 4);
    bl.findKSimilar(student1, 10);
    System.out.println();
    bl.findKSimilar(student2, 10);
    System.out.println();
    bl.findKSimilar(student3, 10);
    System.out.println(); */
    System.out.println(bl.findKSimilar(student4, 3));
    System.out.println();
    System.out.println(bl.findKSimilar(testStudent, 3));
  }
}