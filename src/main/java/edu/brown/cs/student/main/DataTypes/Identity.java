package edu.brown.cs.student.main.DataTypes;

import java.util.Map;

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

  public int getId() {
    return id;
  }

  /**
   * Method to populate a map of categorical data.
   * @param categoricalMap
   */
  public void populateCategoricalMap(Map<Integer, StudentCategorical> categoricalMap) {
    if (!categoricalMap.containsKey(id)) {
      categoricalMap.put(id, new StudentCategorical(String.valueOf(id)));
    }
    StudentCategorical student = categoricalMap.get(id);
    student.setGrade(grade);
    student.setLang(preferred_language);
    student.setMarg(marginalized_groups);
    student.setPrefer(prefer_group);
    student.setMeettime(meeting_times);
    student.setMeettype(meeting);
  }

  public String getMeeting() {
    return meeting;
  }

  public String getGrade() {
    return grade;
  }

  public String getYears_of_experience() {
    return years_of_experience;
  }

  public String getHoroscope() {
    return horoscope;
  }

  public String getMeeting_times() {
    return meeting_times;
  }

  public String getPreferred_language() {
    return preferred_language;
  }

  public String getMarginalized_groups() {
    return marginalized_groups;
  }

  public String getPrefer_group() {
    return prefer_group;
  }
}
