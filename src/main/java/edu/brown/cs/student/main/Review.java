package edu.brown.cs.student.main;

import java.util.Date;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Review review = (Review) o;
    return id.equals(review.id) && reviewDate.equals(review.reviewDate) &&
        reviewSummary.equals(review.reviewSummary) && reviewText.equals(review.reviewText);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reviewDate, reviewSummary, reviewText);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getReviewDate() {
    return reviewDate;
  }

  public void setReviewDate(String reviewDate) {
    this.reviewDate = reviewDate;
  }

  public String getReviewSummary() {
    return reviewSummary;
  }

  public void setReviewSummary(String reviewSummary) {
    this.reviewSummary = reviewSummary;
  }

  public String getReviewText() {
    return reviewText;
  }

  public void setReviewText(String reviewText) {
    this.reviewText = reviewText;
  }
}
