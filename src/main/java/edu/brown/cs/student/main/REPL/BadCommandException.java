package edu.brown.cs.student.main.REPL;

public class BadCommandException extends IllegalArgumentException{

  final String[] args;

  public BadCommandException(String[] args){
    this.args = args;
  }
}
