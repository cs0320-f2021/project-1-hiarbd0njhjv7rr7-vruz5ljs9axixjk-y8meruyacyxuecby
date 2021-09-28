package edu.brown.cs.student.main;

public class FieldParser {

  /**
   * Empty Constructor for FieldParser
   */
  FieldParser(){

  }

  /**
   * Parses weight by turning it into an int and assigning a number based on weight group
   * @param weight
   * @return
   */
  public int parseWeight(String weight){
    weight = weight.substring(0, weight.length() - 3); /** cuts off "lbs" from end of weight **/
    int numWeight = Integer.parseInt(weight);
    if (numWeight <= 110){
      return 1;
    }
    else if (numWeight <= 120){
      return 2;
    }
    else if (numWeight <= 130){
      return 3;
    }
    else if (numWeight <= 140){
      return 4;
    }
    else if (numWeight <= 150){
      return 5;
    }
    else if (numWeight <= 160){
      return 6;
    }
    else if (numWeight <= 170){
      return 7;
    }
    else if (numWeight <= 180){
      return 8;
    }
    else if (numWeight <= 190){
      return 9;
    }
    else if (numWeight <= 200){
      return 9;
    }
    else{
      return 11;
    }
  }

  public int parseBust(String bust){ //TODO
    return 0;
  }

  public int parseHeight(String height){ //TODO
    return 0;
  }

  /**
   * Splits age into integer age groups.
   * @param age
   * @return
   */
  public int parseAge(int age){
    if (age <= 19){
      return 1;
    }
    else if (age <= 24){
      return 2;
    }
    else if (age <= 29){
      return 3;
    }
    else if (age <= 34){
      return 4;
    }
    else if (age <= 39){
      return 5;
    }
    else if (age <= 44){
      return 6;
    }
    else if (age <= 49){
      return 7;
    }
    else if (age <= 54){
      return 8;
    }
    else if (age <= 59){
      return 9;
    }
    else if (age <= 64){
      return 10;
    }
    else if (age <= 69){
      return 11;
    }
    else{
      return 12;
    }
  }

  /**
   * Parses body type into a unique int
   * @param body
   * @return
   */
  public int parseBody(String body){
    switch (body) {
      case "petite":
        return 1;
      case "full bust":
        return 2;
      case "hourglass":
        return 3;
      case "athletic":
        return 4;
      case "straight & narrow":
        return 5;
      case "pear":
        return 6;
      case "apple":
        return 7;
      default:
        return 0;
    }
  }

  /**
   * Parses the horoscope string into an int based on the horoscope.
   * @param horoscope
   * @return
   */
  public int parseHoroscope(String horoscope){
    switch (horoscope) {
      case "Aries":
        return 1;
      case "Taurus":
        return 2;
      case "Gemini":
        return 3;
      case "Cancer":
        return 4;
      case "Leo":
        return 5;
      case "Virgo":
        return 6;
      case "Libra":
        return 7;
      case "Scorpio":
        return 8;
      case "Sagittarius":
        return 9;
      case "Capricorn":
        return 10;
      case "Aquarius":
        return 11;
      case "Pisces":
        return 12;
      default:
        return 0;
    }
  }
}
