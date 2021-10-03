package edu.brown.cs.student.main;
import java.io.IOException;
import java.util.BitSet;

public class BloomFilter {
  private BitSet _bitArrayHash1, _bitArrayHash2, _bitArrayHash3;
  private BitSet[] _filter;
  private Hasher _hasher;
  private FieldParser _fp;
  private String _userID;

  /**
   * BloomFilter constructor, called via the "users" command in REPL to load everything.
   * From https://hur.st/bloomfilter/?n=56&p=0.01&m=&k=3:
   * With 56 possible categories from FieldParser, a desired 1% false positive rate,
   * and 3 hash functions, there should be 693 bits in the array.
   * If this is too many bits, we can add more hash functions to reduce that #.
   * Creates all instance variables, parses all input fields, then hashes them and adds to
   * bloom filter.
   * Throws IO Exception if parsing goes wrong, which means the input in REPL was incorrect.
   */
  BloomFilter(String weight, String bust, String height, int age,
              String body, String horoscope, String userID) throws IOException {
    _userID = userID;
    _hasher = new Hasher();
    _fp = new FieldParser();
    _bitArrayHash1 = new BitSet(693);
    _bitArrayHash2 = new BitSet(693);
    _bitArrayHash3 = new BitSet(693);
    _filter = new BitSet[3]; /** holds the three bit arrays (one for each hash) **/
    _filter[0] = _bitArrayHash1;
    _filter[1] = _bitArrayHash2;
    _filter[2] = _bitArrayHash3;
    try {
      int w = _fp.parseWeight(weight);
      int bu = _fp.parseBust(bust);
      int he = _fp.parseHeight(height);
      int a = _fp.parseAge(age);
      int bo = _fp.parseBody(body);
      int ho = _fp.parseHoroscope(horoscope);
      this.hashAll(w, bu, he, a, bo, ho);
    }
    catch (Exception e){
      throw new IOException();
    }
  }

  /**
   * Alternate BloomFilter constructor that makes the filter from three given bitsets.
   * Used in BloomList to save defensive copies.
   * @param one
   * @param two
   * @param three
   */
  BloomFilter(BitSet one, BitSet two, BitSet three, String userID){
    _userID = userID;
    _filter = new BitSet[3]; /** holds the three bit arrays (one for each hash) **/
    _filter[0] = one;
    _filter[1] = two;
    _filter[2] = three;
  }

  /**
   * Returns array of all 3 bitsets in the filter
   * @return
   */
  public BitSet[] getBitSets(){
    return _filter;
  }

  /**
   * Returns userID of the user/entry associated with this bloom filter
   * @return
   */
  public String getUserID(){
    return _userID;
  }

  /** sets each bitset in the filter to true at the location returned by hash1, hash2, and hash3
   * respectively (hash1 = filter[0], hash2 = filter[1], hash3 = filter[2]) **/
  private void hashAll(int w, int bu, int he, int a, int bo, int ho){
    _filter[0].set(_hasher.hashOne(w, bu, he, a, bo, ho));
    _filter[1].set(_hasher.hashTwo(w, bu, he, a, bo, ho));
    _filter[2].set(_hasher.hashThree(w, bu, he, a, bo, ho));
  }

}
