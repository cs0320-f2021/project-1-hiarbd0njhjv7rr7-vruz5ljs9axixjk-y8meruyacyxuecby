package edu.brown.cs.student.main;
import java.util.BitSet;

public class BloomFilter {
  private BitSet _bitArrayHash1, _bitArrayHash2, _bitArrayHash3;
  private BitSet[] _filter;
  private Hasher _hasher;
  private FieldParser _fp;

  /**
   * BloomFilter constructor, called via the "users" command in REPL to load everything.
   * From https://hur.st/bloomfilter/?n=56&p=0.01&m=&k=3:
   * With 56 possible categories from FieldParser, a desired 1% false positive rate,
   * and 3 hash functions, there should be 693 bits in the array.
   * If this is too many bits, we can add more hash functions to reduce that #.
   */
  BloomFilter(String database){
    _hasher = new Hasher();
    _fp = new FieldParser();
    _bitArrayHash1 = new BitSet(693);
    _bitArrayHash2 = new BitSet(693);
    _bitArrayHash3 = new BitSet(693);
    _filter = new BitSet[3]; /** holds the three bit arrays (one for each hash) **/
    _filter[0] = _bitArrayHash1;
    _filter[1] = _bitArrayHash2;
    _filter[2] = _bitArrayHash3;
  }

  /**
   * Finds k similar users to a user with a certain id; called via the "similar" command in REPL
   * @param k number of similar users to find
   * @param userID user ID of user to find similar users to
   */
  public void similar(int k, int userID){
    //TODO: write class
    //based on userID, pull all fields from SQL
    //feed fields into field parser to create unique ints for each
    //feed ints into hashers to check against bitsets
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
    int w = _fp.parseWeight(weight);
    int he = _fp.parseHeight(height);
    int a = _fp.parseAge(age);
    int bo = _fp.parseBody(body);
    int ho = _fp.parseHoroscope(horoscope);

    //then feed these into each hash function
    //then check against all other data
  }

}

/** NOTES
 * Hash the pool instead of each individual number for bust, weight, height
 * Can also pull data from other databases that are unique about individuals
 * Use hash functions that exist in java already (EX: java hash OR implement your own)
 * Three hash functions, each applied to every field, then turn that bit on or off
 */
