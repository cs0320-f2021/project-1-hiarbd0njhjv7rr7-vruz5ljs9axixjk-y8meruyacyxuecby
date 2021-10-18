package edu.brown.cs.student.main.BloomFilter;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class StudentHasherTest {

  @Test
  public void hashTest(){
    StudentHasher hasher = new StudentHasher("Personally", "Sophomore",
        "Late morning (9:00AM - 12:00PM); Late Afternoon (3:00PM - 6:00PM); Evening (6:00PM - 9:00PM)", "C++",
        "non-citizen", "Yes", "", "", "");
    int[] resultsOne = hasher.sHashOne();
    //System.out.println(resultsOne[0]);
    //System.out.println(resultsOne[1]);
    int[] resultsTwo = hasher.sHashTwo();
    for (int i = 0; i < resultsTwo.length; i++){
      //System.out.println(resultsTwo[i]);
    }
    int[] resultsThree = hasher.sHashThree();
    for (int i = 0; i < resultsThree.length; i++){
      System.out.println(resultsThree[i]);
    }
  }
}
