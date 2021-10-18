package edu.brown.cs.student.main.DataTypes;

public class Identity {
  private int id;
  private String name;
  private String meeting;
  private String grade;
  private String years_of_experience;
  private String horoscope;
  private String meeting_times;
  private String preferred_language;
  private String marginalized_groups;
  private String prefer_group;

  public Identity(int id, String name, String meeting, String grade,
                  String years_of_experience, String horoscope, String meeting_times,
                  String preferred_language, String marginalized_groups,
                  String prefer_group) {
    this.id = id;
    this.name = name;
    this.meeting = meeting;
    this.grade = grade;
    this.years_of_experience = years_of_experience;
    this.horoscope = horoscope;
    this.meeting_times = meeting_times;
    this.preferred_language = preferred_language;
    this.marginalized_groups = marginalized_groups;
    this.prefer_group = prefer_group;
  }

}
