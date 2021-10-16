package edu.brown.cs.student.main.DataTypes;

import edu.brown.cs.student.main.BloomFilter.BloomFilter;

import java.io.IOException;

public class User implements IDataType {
  Integer user_id;
  String weight;
  String bust_size;
  Integer height;
  Integer age;
  String body_type;
  String horoscope;

  public User(String user_id, String weight, String bust_size, String height,
              String age, String body_type, String horoscope) {
    this.user_id = Integer.parseInt(user_id);
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = getHeight(height);
    this.age = Integer.parseInt(age);
    this.body_type = body_type;
    this.horoscope = horoscope;
  }

  private Integer getHeight(String height){

    int totalHeight;
    String[] splitHeight = height.split("'");
    int feet = Integer.parseInt(splitHeight[0]);
    int inches = Integer.parseInt(splitHeight[1].replaceAll("[^\\d.]",""));

    totalHeight = feet * 12 + inches;
    return totalHeight;
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
