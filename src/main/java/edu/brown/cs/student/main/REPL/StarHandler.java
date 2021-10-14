package edu.brown.cs.student.main.REPL;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class StarHandler implements REPLCommandHandler {

  private String[][] sheet;

  @Override
  public boolean checkValidCommand(String[] args) {
    if (args[0].equals("stars") && args.length == 2){
      return true;
    } else if (args[0].equals("naive_neighbors")){
      return true;
    }
    return false;
  }

  private void loadStars(String[] args){
    try{
      sheet = new String[1][1];
      String filename = args[1];
      BufferedReader readsize = new BufferedReader(new FileReader(filename));
      int count = -1; /** negative one to cut out the column headers line */
      String line;
      while ((line = readsize.readLine()) != null) {
        count += 1;
      }
      sheet = new String[count][6]; /** load data by storing in 2D array */
      BufferedReader readsave = new BufferedReader(new FileReader(filename));
      count = -1;
      String row;
      while ((row = readsave.readLine()) != null) {
        if (count == -1) {
          count += 1;
        } else {
          String[] columns = row.split(",");
          sheet[count][0] = columns[0];
          sheet[count][1] = columns[1];
          sheet[count][2] = columns[2];
          sheet[count][3] = columns[3];
          sheet[count][4] = columns[4];
          sheet[count][5] = "0"; /** this column represents distance from given coords */
          count += 1;
        }
      }
      System.out.println("Read " + count + " stars from " + filename);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void processNearestNeighboursByCoordinates(String[] args) throws IOException {
    int k = Integer.parseInt(args[1]);
    if (k > sheet.length) { /** cannot return k stars if k > # of stars */
      throw new IOException();
    }
    double x = Double.parseDouble(args[2]);
    double y = Double.parseDouble(args[3]);
    double z = Double.parseDouble(args[4]);
    this.fillDistances(sheet, x, y, z);
    this.sortCSVData(sheet);
    this.printKNearest(sheet, k, ",");
  }

  private void processNearestNeighboursByName(String[] args) throws IOException {
    int k = Integer.parseInt(args[1]);
    if (k > sheet.length) { /** cannot return k stars if k > # of stars */
      throw new IOException();
    }
    String name = "";
    for (int i = 2; i < args.length; i++) { /** builds name from arguments */
      if (i == 2) {
        name += args[2].substring(1);
      } else {
        name += " ";
        name += args[i];
      }
      if (i == args.length - 1) {
        name = name.substring(0, name.length() - 1);
      }
    }
    double x = Double.MAX_VALUE;
    double y = 0;
    double z = 0;
    for (int i = 0; i < sheet.length; i++) {
      if (sheet[i][1].equals(name)) {
        x = Double.parseDouble(sheet[i][2]);
        y = Double.parseDouble(sheet[i][3]);
        z = Double.parseDouble(sheet[i][4]);
        break;
      }
    }
    if (x == Double.MAX_VALUE) { /** this means no star was found with the name */
      throw new IOException();
    }
    this.fillDistances(sheet, x, y, z);
    this.sortCSVData(sheet);
    this.printKNearest(sheet, k, name);
  }

  /**
   * Calculates distance between two points given by x/y/z 1 and 2.
   * @param x1
   * @param y1
   * @param z1
   * @param x2
   * @param y2
   * @param z2
   * @return distance between points 1 and 2
   */
  private double distanceFormula(double x1, double y1, double z1, double x2, double y2, double z2) {
    return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
  }

  /**
   * Intakes x, y, and z positions of desired location, then fills in the last column of sheet
   * to represent the distance from each star to the location.
   * @param sheet 2D array of CSV data
   * @param x
   * @param y
   * @param z
   */
  private void fillDistances(String[][] sheet, Double x, Double y, Double z) {
    for (int i = 0; i < sheet.length; i++) {
      sheet[i][5] = Double.toString(this.distanceFormula(x, y, z,
          Double.parseDouble(sheet[i][2]), Double.parseDouble(sheet[i][3]),
          Double.parseDouble(sheet[i][4])));
    }
  }

  /**
   * Intakes 2D array of CSV data, then sorts it based on the distance column from closest
   * to furthest away.
   * @param sheet
   * CREDIT FOR 2D SORT METHOD: https://www.delftstack.com/howto/java/sort-2d-array-java/
   */
  private void sortCSVData(String[][] sheet) {
    Arrays.sort(sheet, new Comparator<String[]>() { /** sorts array by fifth column, distance */
    @Override
    public int compare(String[] one, String[] two) { /** defines new compare method */
      if (Double.parseDouble(one[5]) > Double.parseDouble(two[5])) {
        return 1;
      } else {
        return -1;
      }
    }
    });
  }

  /**
   * Prints star IDs of the k nearest neighbors from the sorted sheet.
   * @param sheet
   * @param k
   * @param name: name of the specified star; this isn't included in printed nearest neighbors
   */
  private void printKNearest(String[][] sheet, int k, String name) {
    for (int i = 0; i < k; i++) { /** prints k nearest neighbors, excluding the specified star */
      if (!sheet[i][1].equals(name)) {
        System.out.println(sheet[i][0]);
      } else {
        if (sheet.length < k) {
          k += 1;
        }
      }
    }
  }

  @Override
  public Object parseCommand(String[] args) {
    if (!this.checkValidCommand(args)){
      throw new BadCommandException(args);
    }
    if (args[0].equals("stars")){
      this.loadStars(args);
    } else if (args[0].equals("naive_neighbors") && args.length == 5){
      try{
        this.processNearestNeighboursByCoordinates(args);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {

    }
    return null;
  }

  @Override
  public String[] relevantCommands() {
    return new String[]{"stars", "naive_neighbors"};
  }

  public StarHandler(){}
}
