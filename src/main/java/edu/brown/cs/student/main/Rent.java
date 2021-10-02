package edu.brown.cs.student.main;

public class Rent implements IDataType {
  public final String fit;
  public final Integer userId;
  public final Integer itemId;
  public final Integer rating;
  public final String rentedFor;
  public final String category;
  public final String size;
  public final Integer id;

  public Rent(String fit, Integer userId, Integer itemId, Integer rating,
              String rentedFor, String category, String size, Integer id) {
    this.fit = fit;
    this.userId = userId;
    this.itemId = itemId;
    this.rating = rating;
    this.rentedFor = rentedFor;
    this.category = category;
    this.size = size;
    this.id = id;
  }
}
