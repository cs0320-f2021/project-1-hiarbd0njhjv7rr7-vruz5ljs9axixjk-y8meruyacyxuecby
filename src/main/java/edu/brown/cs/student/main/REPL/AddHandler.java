package edu.brown.cs.student.main.REPL;

import edu.brown.cs.student.main.MathBot;

public class AddHandler implements REPLCommandHandler {

  @Override
  public boolean checkValidCommand(String[] args) {
    if (!(args.length == 3)){
      return false;
    }
    try {
      Double.parseDouble(args[1]);
      Double.parseDouble(args[2]);
      return true;
    } catch (NumberFormatException e){
      return false;
    }
  }

  @Override
  public Object parseCommand(String[] args) {
    if (!this.checkValidCommand(args)){
      throw new BadCommandException(args);
    }
    MathBot mb = new MathBot();
    System.out.println(mb.add(Double.parseDouble(args[1]), Double.parseDouble(args[2])));
    return null;
  }

  @Override
  public String[] relevantCommands() {
    return new String[]{"add"};
  }

  public AddHandler(){}
}
