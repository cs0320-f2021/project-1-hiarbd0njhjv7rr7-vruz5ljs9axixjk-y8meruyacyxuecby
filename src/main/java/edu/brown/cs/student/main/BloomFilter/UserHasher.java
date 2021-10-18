package edu.brown.cs.student.main.BloomFilter;

import java.util.Random;

public class UserHasher extends Hasher{

  /**
   * Empty Constructor for the hasher.
   */
  UserHasher(){

  }

  /** add everything, then multiply by horoscope, then add 164 (range: 168 - 692)
   low bound: ((1 + 1 + 1 + 1 + 1) * 1) + 163 = 169
   high bound: ((13 + 4 + 8 + 12 + 7) * 12) + 163 = 692 **/
  @Override
  public int[] uHashOne(int weight, int bust, int height, int age,
                     int body, int horoscope){
    //return ((weight + bust + height + age + body) * horoscope) + 164;
    int[] ret = new int[2];
    Random generator1 = new Random(weight);
    ret[0] = generator1.nextInt(500);
    Random generator2 = new Random(bust);
    ret[1] = generator2.nextInt(500);
    return ret;
  }

  /** add everything, but then multiply by weight (range: 5 - 559)
   low bound: (1 + 1 + 1 + 1 + 1) * 1 = 5
   high bound: (4 + 8 + 12 + 7 + 12) * 13 = 559 **/
  @Override
  public int[] uHashTwo(int weight, int bust, int height, int age,
                     int body, int horoscope){
    //return ((bust + height + age + body + horoscope) * weight);
    int[] ret = new int[2];
    Random generator1 = new Random(height);
    ret[0] = generator1.nextInt(500);
    Random generator2 = new Random(body);
    ret[1] = generator2.nextInt(500);
    return ret;
  }

  /** Add everything, but then multiply by age and add mod * 10 (range: 55 - 608)
   * low bound: ((1 + 1 + 1 + 1 + 1) * 1) + ((term % 10) * 10) = 55
   * high bound: ((13 + 4 + 8 + 7 + 12) * 12) + ((term % 10) * 10) = 608 **/
  @Override
  public int[] uHashThree(int weight, int bust, int height, int age,
                       int body, int horoscope){
    //int ret = (weight + bust + height + body + horoscope) * age;
    //return ret + ((ret % 10) * 10);
    int[] ret = new int[2];
    Random generator1 = new Random(age);
    ret[0] = generator1.nextInt(500);
    Random generator2 = new Random(horoscope);
    ret[1] = generator2.nextInt(500);
    return ret;
  }
}
