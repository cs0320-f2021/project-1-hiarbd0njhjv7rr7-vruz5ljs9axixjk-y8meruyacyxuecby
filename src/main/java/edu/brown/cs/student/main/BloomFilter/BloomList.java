package edu.brown.cs.student.main.BloomFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;

public class BloomList {
  ArrayList<BloomFilter> _filters;
  int[][] _similarities;
  final int NUMBITS = 693;

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
    Arrays.sort(_similarities, new Comparator<int[]>() { /** sorts array by cardinality sum */
    @Override
    public int compare(int[] one, int[] two) { /** defines new compare method */
      if (one[1] < two[1]) {
        return 1;
      } else {
        return -1;
      }
    }
    });

    if (k > _similarities.length){ /** if input k is larger than number of users, print the max */
      for (int i = 0; i < _similarities.length; i++){
        System.out.println("Similar #" + (i+1) + " UserID: " + _similarities[i][0]);
      }
    }
    else{
      for (int i = 0; i < k; i++){
        System.out.println("Similar #" + (i+1) + " UserID: " + _similarities[i][0]);
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
  private void calculateSimilarity(BloomFilter target){ /** FIX THIS: it only finds the similarity of the first element added SEE TESTS */
    this.reloadSimilarities();
    int c = 0;
    for (BloomFilter b: _filters){
      BitSet[] bits = b.getBitSets();
      BitSet[] targetBits = target.getBitSets();

      int cardinalitySum = 0;
      cardinalitySum += this.compareAnd(targetBits[0], bits[0]);
      cardinalitySum += this.compareAnd(targetBits[1], bits[1]);
      cardinalitySum += this.compareAnd(targetBits[2], bits[2]);

      _similarities[c][0] = Integer.parseInt(b.getUserID()); //adds userID to column 1
      _similarities[c][1] = cardinalitySum; //adds cardinality sum to column 2
      c++;
    }
  }

  /**
   * Loads 2D array of userID and similarity measures with number of bloom filters in the list
   */
  private void reloadSimilarities(){
    _similarities = new int[_filters.size()][2];
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
