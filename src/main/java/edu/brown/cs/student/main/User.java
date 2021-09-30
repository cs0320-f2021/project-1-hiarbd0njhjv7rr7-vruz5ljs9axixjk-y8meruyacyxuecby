package edu.brown.cs.student.main;

public class User implements IDataType {
  Integer user_id;
  Integer weight;
  String bust_size;
  Integer height;
  Integer age;
  String body_type;
  String horoscope;

  public User(Integer user_id, Integer weight, String bust_size, Integer height,
              Integer age, String body_type, String horoscope){
    this.user_id = user_id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;
  }
}
