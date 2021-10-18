package edu.brown.cs.student.main.KDtree.coordinates;

import java.util.List;

/** Interface of a Coordinate with a specified type ID.
 @param <T> Any type for the ID of the coordinate that is specified
 when constructing a Coordinate.
 */
public interface Coordinate<T> {

  /**
   * Get the coordinate value at the dimension requested.
   *
   * @param dim the dimension number, from 1 to n where n is a positive integer.
   * @return a Integer value, any real number.
   */
  Double getCoordinateVal(int dim);

  /**
   * Get the ID.
   *
   * @return id of type with which the Coordinate was created.
   */
  T getId();

  /**
   * Get all coordinate values of the Coordinate.
   *
   * @return a List of Integer; i.e., a list of as many real integers as there are dimensions.
   */
  List<Double> getCoordinates();

  /**
   * Represent the Coordinate as a String.
   *
   * @return a String representation of a Coordinate.
   */
  String toString();
}
