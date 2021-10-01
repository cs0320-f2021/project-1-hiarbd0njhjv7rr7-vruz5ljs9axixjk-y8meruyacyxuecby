package edu.brown.cs.student.main;

public class Hasher {

  /**
   * Empty Constructor for the hasher.
   */
  Hasher(){

  }

  public int hashOne(int weight, int bust, int height, int age,
                     int body, int horoscope){
    /** add everything, then multiply by horoscope, then add 164 (range: 168 - 692)
        low bound: ((1 + 1 + 1 + 1 + 1) * 1) + 163 = 169
        high bound: ((13 + 4 + 8 + 12 + 7) * 12) + 163 = 692 **/

    return ((weight + bust + height + age + body) * horoscope) + 164;
  }

  public int hashTwo(int weight, int bust, int height, int age,
                     int body, int horoscope){
    /** add everything, but then multiply by weight (range: 5 - 559)
        low bound: (1 + 1 + 1 + 1 + 1) * 1 = 5
        high bound: (4 + 8 + 12 + 7 + 12) * 13 = 559 **/
    return ((bust + height + age + body + horoscope) * weight);
  }

  public int hashThree(int weight, int bust, int height, int age,
                       int body, int horoscope){
    /** Add everything, but then multiply by age and add mod * 10 (range: 55 - 608)
     * low bound: ((1 + 1 + 1 + 1 + 1) * 1) + ((term % 10) * 10) = 55
     * high bound: ((13 + 4 + 8 + 7 + 12) * 12) + ((term % 10) * 10) = 608 **/
    int ret = (weight + bust + height + body + horoscope) * age;
    return ret + ((ret % 10) * 10);
  }
}
