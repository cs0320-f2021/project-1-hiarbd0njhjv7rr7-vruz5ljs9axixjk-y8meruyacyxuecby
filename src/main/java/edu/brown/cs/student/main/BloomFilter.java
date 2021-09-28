package edu.brown.cs.student.main;
import java.util.BitSet;

public class BloomFilter {
  private BitSet _hashOneSet;
  private BitSet _hashTwoSet;
  private BitSet _hashThreeSet;
  private Hasher _hasher;
  private FieldParser _fp;

  /**
   * BloomFilter constructor, called via the "users" command in REPL
   */
  BloomFilter(/** add constructor information here */){
    _hasher = new Hasher();
    _fp = new FieldParser();
    //TODO: write constructor, create BloomFilter
  }

  /**
   * Finds k similar users to a user with a certain id; called via the "similar" command in REPL
   * @param k number of similar users to find
   * @param userID user ID of user to find similar users to
   */
  public void similar(int k, int userID){
    //TODO: write class
  }

  /**
   * Also finds k similar users, but with the input user data instead of an ID
   * Data types in constructor match that of the users database
   * @param k number of users to find
   * @param weight
   * @param bust
   * @param height
   * @param age
   * @param body
   * @param horoscope
   */
  public void similar(int k, String weight, String bust, String height, int age,
                      String body, String horoscope){
    //TODO: write class
  }
}

/** NOTES
 * Hash the pool instead of each individual number for bust, weight, height
 * Can also pull data from other databases that are unique about individuals
 * Use hash functions that exist in java already (EX: java hash OR implement your own)
 * Three hash functions, each applied to every field, then turn that bit on or off
 */
