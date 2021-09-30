package edu.brown.cs.student.main;

import java.lang.reflect.Field;

public class  DeconstructedObject <T extends IDataType> {
  private String className;
  private String[] columns;
  private String[] values;
  private String[] datatypes;

  public DeconstructedObject(T object){
    try{
      Class classType = object.getClass();
      String className = classType.getName();
      Field[] fields = classType.getDeclaredFields();
      String[] columns = new String[fields.length];
      String[] values = new String[fields.length];
      String[] datatypes = new String[fields.length];

      for (int i = 0; i < fields.length; i++) {
        Field currField = fields[i];
        String currColumn = currField.getName();
        String currValue;
        Class<?> currClass = currField.getType();
        if (currClass.isAssignableFrom(Integer.class)) {
          currValue = ((Integer) currField.getInt(object)).toString();
          datatypes[i] = "INTEGER";
        } else {
          currValue = (String) currField.get(object);
          datatypes[i] = "TEXT";
        }
        columns[i] = currColumn;
        values[i] = currValue;
      }
      this.className = className;
      this.columns = columns;
      this.values = values;
      this.datatypes = datatypes;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

  }

  public String getClassName() {
    return className;
  }

  public String[] getColumns() {
    return columns;
  }

  public String[] getDatatypes() {
    return datatypes;
  }

  public String[] getValues() {
    return values;
  }
}
