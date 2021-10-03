package edu.brown.cs.student.main;

public class User implements IDataType {
  Integer userId;
  Integer weight;
  String bustSize;
  Integer height;
  Integer age;
  String bodyType;
  String horoscope;

  public User(Integer userId, Integer weight, String bustSize, Integer height,
              Integer age, String bodyType, String horoscope) {
    this.userId = userId;
    this.weight = weight;
    this.bustSize = bustSize;
    this.height = height;
    this.age = age;
    this.bodyType = bodyType;
    this.horoscope = horoscope;
  }
}
