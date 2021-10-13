package edu.brown.cs.student.main.DataTypes;

public class Interests implements IDataType {
  private int id;
  private String interest;

  public Interests(int id, String interest) {
    this.id = id;
    this.interest = interest;
  }
}
