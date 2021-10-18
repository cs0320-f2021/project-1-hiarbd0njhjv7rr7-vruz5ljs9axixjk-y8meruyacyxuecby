package edu.brown.cs.student.main.DataTypes;

import edu.brown.cs.student.main.BloomFilter.BloomFilter;

import java.io.IOException;

public class StudentCategorical {
  private final String id;
  private String meettype;
  private String grade;
  private String meettime;
  private String lang;
  private String marg;
  private String prefer;
  private String pos = "";
  private String neg = "";
  private int interest = 0;

  public StudentCategorical(String id) {
    this.id = id;
  }
  public StudentCategorical(String id, String meettype, String grade, String meettime,
                            String lang, String marg, String prefer, String pos, String neg,
                            Integer interest) {
    this.id = id;
    this.meettype = meettype;
    this.grade = grade;
    this.meettime = meettime;
    this.lang = lang;
    this.marg = marg;
    this.prefer = prefer;
    this.pos = pos;
    this.neg = neg;
    this.interest = interest;
  }

  /**
   * Creates a BloomFilter object from the Student data.
   * @return BloomFilter object with Student data
   */
  public BloomFilter makeBloomFilter() {
    try {
      return new BloomFilter(id, meettype, grade, meettime,
          lang, marg, prefer, pos, neg,
          String.valueOf(interest));
    } catch (IOException e) {
//      e.printStackTrace();
      System.out.println("Unable to make Student BloomFilter");
    }
    return null;
  }

  public String getId() {
    return this.id;
  }

  public void setMeettype(String meettype) {
    this.meettype = meettype;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public void setMeettime(String meettime) {
    this.meettime = meettime;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public void setMarg(String marg) {
    this.marg = marg;
  }

  public void setPrefer(String prefer) {
    this.prefer = prefer;
  }

  /**
   * Adds the positive to the string of current positives.
   * @param pos
   */
  public void addPos(String pos) {
    this.pos += pos;
  }

  /**
   * Adds the negative to the string of current negatives.
   * @param neg
   */
  public void addNeg(String neg) {
    this.neg += neg;
  }

  /**
   * Adds the interest to the string of current interests.
   * @param interest
   */
  public void addInterest(int interest) {
    this.interest *= interest;
  }
}
