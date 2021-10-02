package edu.brown.cs.student.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;

public class BloomList {
  ArrayList<BloomFilter> _filters;
  BloomFilter _defense;
  int[][] _similarities;

  /**
   * Constructor for BloomList.
   */
  BloomList() {
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
      if (one[1] > two[1]) {
        return 1;
      } else {
        return -1;
      }
    }
    });
    for (int i = 0; i < k; i++){
      System.out.println("UserID: " + _similarities[k][0]);
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
      BitSet[] bits = b.getBitSets();
      this.saveDefensiveCopy(target); /** save defensive copy before AND operations, which modify */
      BitSet[] targetBits = target.getBitSets();
      targetBits[0].and(bits[0]);
      targetBits[1].and(bits[1]);
      targetBits[2].and(bits[2]);
      int cardinalitySum = targetBits[0].cardinality() + targetBits[1].cardinality() +
          targetBits[2].cardinality();
      _similarities[c][0] = Integer.parseInt(b.getUserID()); //adds userID to column 1
      _similarities[c][1] = cardinalitySum; //adds cardinality sum to column 2
      this.loadDefensiveCopy(target);
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
   * Saves copy of bloom filter bf to reset after AND comparisons
   */
  private void saveDefensiveCopy(BloomFilter bf) {
    BitSet[] sets = bf.getBitSets();
    _defense = new BloomFilter(sets[0], sets[1], sets[2], bf.getUserID());
  }

  /**
   * Loads defensive copy into the input BloomFilter bf
   */
  private void loadDefensiveCopy(BloomFilter bf) {
    BitSet[] sets = _defense.getBitSets();
    bf = new BloomFilter(sets[0], sets[1], sets[2], _defense.getUserID());
  }
}
