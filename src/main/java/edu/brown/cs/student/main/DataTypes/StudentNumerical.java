package edu.brown.cs.student.main.DataTypes;

import edu.brown.cs.student.main.KDtree.coordinates.Coordinate;

import java.util.List;

public class StudentNumerical implements Coordinate<String> {
  private final String id;
  private final List<Double> data;

  public StudentNumerical(Skill skill) {
    this.id = String.valueOf(skill.getId());
    this.data = skill.getDoubleData();
  }
  @Override
  public Double getCoordinateVal(int dim) {
    return data.get(dim - 1);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public List<Double> getCoordinates() {
    return data;
  }
}
