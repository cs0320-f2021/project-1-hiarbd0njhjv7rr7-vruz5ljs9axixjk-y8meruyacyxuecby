package edu.brown.cs.student.main;

public class Rent implements IDataType {
  String fit;
  Integer user_id;
  Integer item_id;
  Integer rating;
  String rented_for;
  String category;
  String size;
  Integer id;

  public Rent(String fit, Integer user_id, Integer item_id, Integer rating,
              String rented_for, String category, String size, Integer id){
    this.fit = fit;
    this.user_id = user_id;
    this.item_id = item_id;
    this.rating = rating;
    this.rented_for = rented_for;
    this.category = category;
    this.size = size;
    this.id = id;
  }
}
