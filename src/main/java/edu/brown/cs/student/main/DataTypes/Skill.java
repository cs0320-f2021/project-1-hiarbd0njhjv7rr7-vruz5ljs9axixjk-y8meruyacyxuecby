package edu.brown.cs.student.main.DataTypes;

public class Skill implements IDataType {
  private int id;
  private String name;
  private int commenting;
  private int testing;
  private int OOP;
  private int algorithms;
  private int teamwork;
  private int frontend;

  public Skill(int id, String name, int commenting, int testing, int OOP, int algorithms,
                int teamwork, int frontend) {
    this.id = id;
    this.name = name;
    this.commenting = commenting;
    this.testing = testing;
    this.OOP = OOP;
    this.algorithms = algorithms;
    this.teamwork = teamwork;
    this.frontend = frontend;
  }

}
