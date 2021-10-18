package edu.brown.cs.student.main.BloomFilter;

/** Superclass of UserHasher and StudentHasher; used to avoid conflict in BloomFilter */
public class Hasher {
  Hasher(){

  }

  public int[] uHashOne(int weight, int bust, int height, int age,
                      int body, int horoscope){
    return new int[2];
  }
  public int[] uHashTwo(int weight, int bust, int height, int age,
                      int body, int horoscope){
    return new int[2];
  }
  public int[] uHashThree(int weight, int bust, int height, int age,
                        int body, int horoscope){
    return new int[2];
  }
  public int[] sHashOne(){
    return new int[2];
  }
  public int[] sHashTwo(){
    return new int[2];
  }
  public int[] sHashThree(){
    return new int[2];
  }
  public int[] antiHash() { return new int[2]; }
}
