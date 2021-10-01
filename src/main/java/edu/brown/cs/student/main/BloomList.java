package edu.brown.cs.student.main;

import java.util.ArrayList;
import java.util.BitSet;

public class BloomList {
  ArrayList<BloomFilter> _filters;
  BloomFilter _defense;

  /**
   * Constructor for BloomList
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
   * Loops through all loaded data and compares each corresponding bitset using AND operation:
   * https://en.wikipedia.org/wiki/Bitwise_operation#AND.
   * Number of bits that are common in each set are then summed up as a measure of similarity.
   * @param target
   * @param k
   */
  public void findKNear(BloomFilter target, int k){
    for (BloomFilter b: _filters){
      BitSet[] bits = b.getBitSets();
      this.saveDefensiveCopy(target); /** save defensive copy before AND operations, which modify */
      BitSet[] targetBits = target.getBitSets();
      targetBits[0].and(bits[0]);
      targetBits[1].and(bits[1]);
      targetBits[2].and(bits[2]);
      int cardinalitySum = targetBits[0].cardinality() + targetBits[1].cardinality() +
          targetBits[2].cardinality();
      //TODO: add sums to array of some sort to produce K nearest
      this.loadDefensiveCopy(target);
    }
  }

  /**
   * Saves copy of bloom filter to reset after AND comparisons
   */
  private void saveDefensiveCopy(BloomFilter bf) {
    BitSet[] sets = bf.getBitSets();
    _defense = new BloomFilter(sets[0], sets[1], sets[2]);
  }

  /**
   * Loads defensive copy into the input BloomFilter bf
   */
  private void loadDefensiveCopy(BloomFilter bf) {
    BitSet[] sets = _defense.getBitSets();
    bf = new BloomFilter(sets[0], sets[1], sets[2]);
  }
}
