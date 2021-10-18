package edu.brown.cs.student.main.DataTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  public int getId() {
    return id;
  }

  /**
   * Returns numerical data as a list.
   * @return list containing numerical data
   */
  public List<Integer> getData() {
    return new ArrayList<>(Arrays.asList(commenting, testing, OOP, algorithms, teamwork, frontend));
  }
  /**
   * Returns numerical data as a list of Doubles.
   * @return list containing numerical data as Doubles
   */
  public List<Double> getDoubleData() {
    ArrayList<Double> doubleList = new ArrayList<>();
    List<Integer> intList = getData();
    for (int i : intList) {
      doubleList.add(Double.valueOf(i));
    }
    return doubleList;
  }
}
