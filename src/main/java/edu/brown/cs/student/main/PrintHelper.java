package edu.brown.cs.student.main;

/**
 * The PrintHelper contains various method to simplify printing to the console.
 *
 * These methods include:
 * - printing in various colors
 * - printing in various colors, followed by a new line
 * - clearing the current console line
 */
public class PrintHelper {

  public static void printlnBlack(String s){
    System.out.print("\u001B[30m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  public static void printlnRed(String s){
    System.out.print("\u001b[31m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  public static void printlnGreen(String s){
    System.out.print("\u001B[32m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  public static void printlnYellow(String s){
    System.out.print("\u001B[33m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  /**
   * Takes in a string and prints the string to the console in blue by using the
   * relevant ANSI escape codes, followed by a new line.
   *
   * @param s - the string to be printed
   */
  public static void printlnBlue(String s){
    System.out.print("\u001B[34m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  public static void printlnMagenta(String s){
    System.out.print("\u001B[35m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  public static void printlnCyan(String s){
    System.out.print("\u001B[36m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  public static void printlnWhite(String s){
    System.out.print("\u001B[37m");
    System.out.print(s);
    System.out.print("\u001b[0m");
    System.out.println();
  }

  public static void printBlack(String s){
    System.out.print("\u001B[30m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void printRed(String s){
    System.out.print("\u001b[31m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void printGreen(String s){
    System.out.print("\u001B[32m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void printYellow(String s){
    System.out.print("\u001B[33m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void printBlue(String s){
    System.out.print("\u001B[34m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void printMagenta(String s){
    System.out.print("\u001B[35m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void printCyan(String s){
    System.out.print("\u001B[36m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void printWhite(String s){
    System.out.print("\u001B[37m");
    System.out.print(s);
    System.out.print("\u001b[0m");
  }

  public static void clearLine(){
    System.out.print("\u001b[2K" + "\r");
  }
}
