package edu.brown.cs.student.main.BloomFilter;
import java.io.IOException;
import java.util.BitSet;

public class BloomFilter {
  private BitSet _filter;
  private Hasher _hasher;
  private UserFieldParser _fp;
  private String _identifier;
  private String _type;
  final int NUMBITS = 500;

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
   * Alternate constructor to initialize information related to the student dataset.
   * @param name
   * @param meettype
   * @param grade
   * @param meettime
   * @param lang
   * @param marg
   * @param prefer
   */
  public BloomFilter(String name, String meettype, String grade, String meettime,
                    String lang, String marg, String prefer) throws IOException {
    _type = "student";
    _identifier = name;
    _hasher = new StudentHasher(meettype, grade, meettime, lang, marg, prefer);
    _filter = new BitSet(NUMBITS);
    try {
      this.hashAllStudents();
    }
    catch (Exception e) {
      throw new IOException();
    }
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
  }
}
