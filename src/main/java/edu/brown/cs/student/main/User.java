package edu.brown.cs.student.main;

import java.io.IOException;

public class User implements IDataType {
  Integer user_id;
  Integer weight;
  String bust_size;
  Integer height;
  Integer age;
  String body_type;
  String horoscope;

  public User(Integer user_id, Integer weight, String bust_size, Integer height,
              Integer age, String body_type, String horoscope) {
    this.user_id = user_id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;
  }

  /**
   * Takes the fields of the User object and creates a
   * BloomFilter object.
   * @return BloomFilter object
   * @throws IOException when there is a parsing error
   */
  public BloomFilter makeBloomFilter() throws IOException {
    return new BloomFilter(weight.toString(), bust_size, height.toString(),
        age, body_type, horoscope, user_id.toString());
  }
}
