package edu.brown.cs.student.main;

public class Rent implements IDataType {
  final String fit;
  final Integer userId;
  final Integer itemId;
  final Integer rating;
  final String rentedFor;
  final String category;
  final String size;
  final Integer id;

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
