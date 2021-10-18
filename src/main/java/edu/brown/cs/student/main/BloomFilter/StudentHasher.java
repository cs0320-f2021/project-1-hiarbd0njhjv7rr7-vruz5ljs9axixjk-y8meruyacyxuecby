package edu.brown.cs.student.main.BloomFilter;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

public class StudentHasher<HashTable> extends Hasher {
  String _mty, _g, _mti, _l, _m, _p, _pos, _neg, _i;
  HashMap<String, Integer> _alphabetPrimes;

  /**
   * Constructor to initialize the variables.
   */
  public StudentHasher(String mty, String g, String mti, String l, String m, String p,
                String pos, String neg, String i) {
    _mty = mty;
    _g = g;
    _mti = mti;
    _l = l;
    _m = m;
    _p = p;
    _pos = pos;
    _neg = neg;
    _i = i;
    _alphabetPrimes = new HashMap<String, Integer>();
    this.populatePrimes();
  }

  /**
   * @returns two ints that represent the hash of meeting type and grade using the following:
   * Each letter is assigned a unique prime number. The hash of a string is then each letter's
   * corresponding prime number multiplied by its position in the string.
   * To generate a valid index in the bitset, a seeded random number generator is used.
   */
  @Override
  public int[] sHashOne() {
    int[] ret = new int[2];
    Random generator1 = new Random(this.calculateOne(_mty));
    ret[0] = generator1.nextInt(498);
    Random generator2 = new Random(this.calculateOne(_g));
    ret[1] = generator2.nextInt(498);
    return ret;
  }

  /**
   * @returns two ints that represent the hash of the preferred language and marginal group
   * identities using the following:
   * Each letter is assigned a unique prime number. The hash of a string is then each letter's
   * corresponding prime number added to its position in the string.
   * To generate a valid index in the bitset, a seeded random number generator is used.
   * If a person does not prefer to be with a marginal group, the bit 499 is reserved to be
   * set to 1 to signify this.
   */
  @Override
  public int[] sHashTwo() {
    if (_p.equals("") || _p.equals("No") || _m.equals("")) {
      int[] ret = new int[2];
      ret[0] = 499;
      Random generator1 = new Random(this.calculateTwo(_l));
      ret[1] = generator1.nextInt(498);
      return ret;
    } else {
      String[] groups = _m.split("; "); /** splits marginalized string into each group */
      int[] ret = new int[1 + groups.length];
      for (int i = 0; i < groups.length; i++) {
        Random generator1 = new Random(this.calculateTwo(groups[i]));
        ret[i] = generator1.nextInt(498);
      }
      Random generator2 = new Random(this.calculateTwo(_l));
      ret[ret.length - 1] = generator2.nextInt(498);
      return ret;
    }
  }

  /**
   * @returns two ints that represent the hash of meeting type and grade using the following:
   * Each letter is assigned a unique prime number. The hash of a string is then the
   * sum of each letter's corresponding prime numbers.
   * To generate a valid index in the bitset, a seeded random number generator is used.
   */
  @Override
  public int[] sHashThree(){
    String[] times = _mti.split("; "); /** splits meeting times into each time */
    int[] ret = new int[times.length];
    for (int i = 0; i < times.length; i++) {
      Random generator1 = new Random(this.calculateThree(times[i]));
      ret[i] = generator1.nextInt(498);
    }
    return ret;
  }

  /**
   * Hashes all anti fields with the following method:
   * Each letter is assigned a unique prime number. The hash of a string is then the
   * sum of each letter's corresponding prime numbers plus the fourth power of the index
   * in the string the letter is found.
   * To generate a valid index in the bitset, a seeded random number generator is used.
   * @return
   */
  @Override
  public int[] antiHash(){
    int[] ret = new int[3];
    Random generator1 = new Random(this.calculateAnti(_pos));
    ret[0] = generator1.nextInt(498);
    Random generator2 = new Random(this.calculateAnti(_neg));
    ret[1] = generator2.nextInt(498);
    Random generator3 = new Random(this.calculateAnti(_i));
    ret[2] = generator3.nextInt(498);
    return ret;
  }

  /**
   * Populates the primes to alphabets HashMap by assigning each alphabet letter a prime number.
   * This is used for hashing the strings.
   */
  private void populatePrimes(){
    _alphabetPrimes.put("a", 2);
    _alphabetPrimes.put("b", 3);
    _alphabetPrimes.put("c", 5);
    _alphabetPrimes.put("d", 7);
    _alphabetPrimes.put("e", 11);
    _alphabetPrimes.put("f", 13);
    _alphabetPrimes.put("g", 17);
    _alphabetPrimes.put("h", 19);
    _alphabetPrimes.put("i", 23);
    _alphabetPrimes.put("j", 29);
    _alphabetPrimes.put("k", 31);
    _alphabetPrimes.put("l", 37);
    _alphabetPrimes.put("m", 41);
    _alphabetPrimes.put("n", 43);
    _alphabetPrimes.put("o", 47);
    _alphabetPrimes.put("p", 53);
    _alphabetPrimes.put("q", 59);
    _alphabetPrimes.put("r", 61);
    _alphabetPrimes.put("s", 67);
    _alphabetPrimes.put("t", 71);
    _alphabetPrimes.put("u", 73);
    _alphabetPrimes.put("v", 79);
    _alphabetPrimes.put("w", 83);
    _alphabetPrimes.put("x", 89);
    _alphabetPrimes.put("y", 97);
    _alphabetPrimes.put("z", 101);
    _alphabetPrimes.put("+", 103);
    _alphabetPrimes.put("-", 107);
    _alphabetPrimes.put("3", 109);
    _alphabetPrimes.put("6", 113);
    _alphabetPrimes.put("9", 127);
    _alphabetPrimes.put("1", 131);
    _alphabetPrimes.put("2", 137);
    _alphabetPrimes.put("0", 139);
    _alphabetPrimes.put("(", 149);
    _alphabetPrimes.put(")", 151);
    _alphabetPrimes.put(":", 157);
    _alphabetPrimes.put(" ", 163);

  }

  /** performs mathematical calculation to hash the given string using method one */
  private int calculateOne(String toCalc){
    int count = 0;
    for (int i = 0; i < toCalc.length(); i++){
      String op = toCalc.substring(i, i+1);
      op = op.toLowerCase();
      count += _alphabetPrimes.get(op) * (i + 1);
    }
    return count;
  }

  /** performs mathematical calculation to hash the given string using method two */
  private int calculateTwo(String toCalc){
    int count = 0;
    for (int i = 0; i < toCalc.length(); i++){
      String op = toCalc.substring(i, i+1);
      op = op.toLowerCase();
      count += _alphabetPrimes.get(op) + (i + 1);
    }
    return count;
  }

  /** performs mathematical calculation to hash the given string using method three */
  private int calculateThree(String toCalc){
    int count = 0;
    for (int i = 0; i < toCalc.length(); i++){
      String op = toCalc.substring(i, i+1);
      op = op.toLowerCase();
      count += _alphabetPrimes.get(op);
    }
    return count;
  }

  /** performs mathematical calculation to hash the given string using anti's method */
  private int calculateAnti(String toCalc){
    int count = 0;
    for (int i = 0; i < toCalc.length(); i++){
      String op = toCalc.substring(i, i+1);
      op = op.toLowerCase();
      count += _alphabetPrimes.get(op) + Math.pow((i + 1), 4);
    }
    return count;
  }
}
