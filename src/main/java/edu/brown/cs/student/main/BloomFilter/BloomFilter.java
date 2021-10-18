package edu.brown.cs.student.main.BloomFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

public class BloomFilter {
  private BitSet _filter;
  private BitSet _antifilter;
  private Hasher _hasher;
  private UserFieldParser _fp;
  private String _identifier;
  private String _type;
  private final int NUMBITS = 500;

  /**
   * Creates all instance variables when users are input, parses all input fields, then hashes them
   * and adds to bloom filter.
   * Throws IO Exception if parsing goes wrong, which means the input in REPL was incorrect.
   */
  public BloomFilter(String weight, String bust, String height, int age,
                     String body, String horoscope, String userID) throws IOException {
    _type = "user";
    _identifier = userID;
    _hasher = new UserHasher();
    _fp = new UserFieldParser();
    _filter = new BitSet(NUMBITS); /** holds the three bit arrays (one for each hash) **/
    try {
      int w = _fp.parseWeight(weight);
      int bu = _fp.parseBust(bust);
      int he = _fp.parseHeight(height);
      int a = _fp.parseAge(age);
      int bo = _fp.parseBody(body);
      int ho = _fp.parseHoroscope(horoscope);
      this.hashAllUsers(w, bu, he, a, bo, ho);
    }
    catch (Exception e) {
      throw new IOException();
    }
  }

  /**
   * Alternate constructor to initialize information related to the integration dataset from
   * an API call.
   * Fields pos, neg, and interest are called "anti" fields. In a separate bitset, we will check
   * for how DISSIMILAR they are from each other as a measure of similarity (we think the best
   * groups have different interests and good/bad personality traits to balance each other out).
   */
  public BloomFilter(String ID, String meettype, String grade, String meettime,
                    String lang, String marg, String prefer, String pos,
                     String neg, String interest) throws IOException {
    _type = "student";
    _identifier = ID;
    _hasher = new StudentHasher(meettype, grade, meettime, lang, marg, prefer, pos, neg, interest);
    _filter = new BitSet(NUMBITS);
    _antifilter = new BitSet(NUMBITS);
    try {
      this.hashAllStudents();
    }
    catch (Exception e) {
      throw new IOException();
    }
  }

  public BloomFilter(String interest, String Negative, String Positive, String id){
    _type = "studentDB";
    _identifier = id;
  }

  /**
   * Returns the filter's core bitset such that it can't be modified
   * @return
   */
  public BitSet getBitSet() {
    BitSet newFilter = new BitSet(NUMBITS);
    for (int i = 0; i < NUMBITS; i++){
      if (_filter != null && _filter.get(i) == true){
        newFilter.set(i);
      }
    }
    return newFilter;
  }

  /**
   * Returns the filter's anti field bitset such that it can't be modified
   * @return
   */
  public BitSet getAntiSet() {
    BitSet newFilter = new BitSet(NUMBITS);
    for (int i = 0; i < NUMBITS; i++){
      if (_antifilter != null && _antifilter.get(i) == true){
        newFilter.set(i);
      }
    }
    return newFilter;
  }

  /**
   * Returns userID of the user/entry associated with this bloom filter
   * @return
   */
  public String getIdentifier() {
    return _identifier;
  }

  /** sets bitset to true at the location returned by hash1, hash2, and hash3 for users **/
  private void hashAllUsers(int w, int bu, int he, int a, int bo, int ho){
    if (_type.equals("user")){
      int[] hash1 = _hasher.uHashOne(w, bu, he, a, bo, ho);
      int[] hash2 = _hasher.uHashTwo(w, bu, he, a, bo, ho);
      int[] hash3 = _hasher.uHashThree(w, bu, he, a, bo, ho);
      _filter.set(hash1[0]);
      _filter.set(hash1[1]);
      _filter.set(hash2[0]);
      _filter.set(hash2[1]);
      _filter.set(hash3[0]);
      _filter.set(hash3[1]);
    }
  }

  /**
   * Sets bitset to true at the location returned by hash1, hash2, and hash3 for students
   */
  private void hashAllStudents(){
    if (_type.equals("student")){
      int[] hash1 = _hasher.sHashOne();
      int[] hash2 = _hasher.sHashTwo();
      int[] hash3 = _hasher.sHashThree();
      for (int i = 0; i < hash1.length; i++){
        _filter.set(hash1[i]);
      }
      for (int i = 0; i < hash2.length; i++){
        _filter.set(hash2[i]);
      }
      for (int i = 0; i < hash3.length; i++){
        _filter.set(hash3[i]);
      }

      int[] antihashes = _hasher.antiHash();
      for (int i = 0; i < antihashes.length; i++){
        _antifilter.set(antihashes[i]);
      }
    }
  }
}
