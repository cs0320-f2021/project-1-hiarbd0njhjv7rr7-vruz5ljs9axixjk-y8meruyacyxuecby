package edu.brown.cs.student.main.BloomFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;

public class BloomList {
  ArrayList<BloomFilter> _filters;
  String[][] _similarities;
  final int NUMBITS = 500;

  /**
   * Constructor for BloomList.
   */
  public BloomList() {
    _filters = new ArrayList<BloomFilter>();
  }

  /**
   * Adds the paramater bloom filter to the bloom list.
   * @param bf
   */
  public void insert(BloomFilter bf){
    _filters.add(bf);
  }

  /**
   * Calculates similarity cardinalities for each bloom filter in the list, then sorts
   * _similarities by cardinality sum (second column), then prints out the k most similar
   * users (via userID) to the target.
   * @param target
   * @param k
   */
  public void findKSimilar(BloomFilter target, int k){
    this.calculateSimilarity(target);
    Arrays.sort(_similarities, new Comparator<String[]>() { /** sorts array by cardinality sum */
    @Override
    public int compare(String[] one, String[] two) { /** defines new compare method */
      if (Integer.parseInt(one[1]) < Integer.parseInt(two[1])) {
        return 1;
      } else {
        return -1;
      }
    }
    });

    if (k > _similarities.length){ /** if input k is larger than number of users, print the max */
      for (int i = 0; i < _similarities.length; i++){
        System.out.println("Similar #" + (i+1) + " Identifier: " + _similarities[i][0] + " has " + _similarities[i][1] + " properties in common.");
      }
    }
    else{
      for (int i = 0; i < k; i++){
        System.out.println("Similar #" + (i+1) + " Identifier: " + _similarities[i][0] + " has " + _similarities[i][1] + " properties in common.");
      }
    }
  }

  /**
   * Loops through all loaded data and compares each corresponding bitset using AND operation:
   * https://en.wikipedia.org/wiki/Bitwise_operation#AND.
   * Number of bits that are common in each set are then summed up as a measure of similarity.
   * The bloom filters that have the most bits in common across all 3 bitsets are said to be
   * the most similar!
   * @param target
   */
  private void calculateSimilarity(BloomFilter target){
    this.reloadSimilarities();
    int c = 0;
    for (BloomFilter b: _filters){
      BitSet bits = b.getBitSet();
      BitSet targetBits = target.getBitSet();
      int cardinalitySum = this.compareAnd(targetBits, bits);
      _similarities[c][0] = b.getIdentifier(); //adds identifier to column 1
      _similarities[c][1] = Integer.toString(cardinalitySum); //adds cardinality sum to column 2
      c++;
    }
  }

  /**
   * Loads 2D array of userID and similarity measures with number of bloom filters in the list
   */
  private void reloadSimilarities(){
    _similarities = new String[_filters.size()][2];
  }

  /**
   * Conducts logical AND comparison of the two input bitsets
   * @param first
   * @param second
   * @return
   */
  private int compareAnd(BitSet first, BitSet second){
    int countAnd = 0;
    for (int i = 0; i < NUMBITS; i++){
      if (first.get(i) == true && second.get(i) == true){
        countAnd++;
      }
    }
    return countAnd;
  }

  /**
   * Returns number of bloom filters in the BloomList
   */
  public int size(){
    return _filters.size();
  }
}
