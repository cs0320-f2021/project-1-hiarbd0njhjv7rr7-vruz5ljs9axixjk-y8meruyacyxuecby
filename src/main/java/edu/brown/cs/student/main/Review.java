package edu.brown.cs.student.main;

import java.util.Date;

public class Review implements IDataType {
  Integer id;
  Date review_date;
  String review_summary;
  String review_text;

  public Review(int id, Date reviewDate, String reviewSummary, String reviewText){
    this.id = id;
    this.review_date = reviewDate;
    this.review_summary = reviewSummary;
    this.review_text = reviewText;
  }
}
