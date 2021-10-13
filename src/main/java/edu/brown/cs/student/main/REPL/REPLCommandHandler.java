package edu.brown.cs.student.main.REPL;

public interface REPLCommandHandler {
  boolean checkValidCommand(String[] args);

  Object parseCommand(String[] args);
}
