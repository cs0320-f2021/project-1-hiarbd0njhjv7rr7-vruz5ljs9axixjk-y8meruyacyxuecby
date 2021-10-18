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
  private String pos;
  private String neg;
  private String interest;

  public StudentCategorical(String id) {
    this.id = id;
  }
  public StudentCategorical(String id, String meettype, String grade, String meettime,
                            String lang, String marg, String prefer, String pos, String neg,
                            String interest) {
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
          interest);
    } catch (IOException e) {
//      e.printStackTrace();
      System.out.println("Unable to make Student BloomFilter");
    }
    return null;
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

  public void setPos(String pos) {
    this.pos = pos;
  }

  public void setNeg(String neg) {
    this.neg = neg;
  }

  public void setInterest(String interest) {
    this.interest = interest;
  }
}
