package edu.brown.cs.student.main.DataTypes;

public class Positive implements IDataType {
  private int id;
  private String trait;

  public Positive(int id, String trait) {
    this.id = id;
    this.trait = trait;
  }
}
