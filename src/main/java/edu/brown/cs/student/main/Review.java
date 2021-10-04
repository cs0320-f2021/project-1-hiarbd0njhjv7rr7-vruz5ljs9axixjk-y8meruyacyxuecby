package edu.brown.cs.student.main;

import java.util.Date;

public class Review implements IDataType {
  Integer id;
  String reviewDate;
  String reviewSummary;
  String reviewText;

  public Review(int id, String reviewDate, String reviewSummary, String reviewText){
    this.id = id;
    this.reviewDate = reviewDate;
    this.reviewSummary = reviewSummary;
    this.reviewText = reviewText;
  }
}
