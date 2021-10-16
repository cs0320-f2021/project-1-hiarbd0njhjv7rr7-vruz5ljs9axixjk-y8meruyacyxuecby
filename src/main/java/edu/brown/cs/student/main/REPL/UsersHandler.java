package edu.brown.cs.student.main.REPL;

import edu.brown.cs.student.main.BloomFilter.BloomFilter;
import edu.brown.cs.student.main.BloomFilter.BloomList;
import edu.brown.cs.student.main.DataTypes.User;
import edu.brown.cs.student.main.ORM.ORM;

import java.io.IOException;
import java.util.List;

public class UsersHandler implements REPLCommandHandler{

  BloomList bloomFilters;
  ORM orm;

  @Override
  public boolean checkValidCommand(String[] args) {
    if (args[0].equals("users") && args.length == 2){
      return true;
    } else if (args[0].equals("similar") && (args.length == 3 || args.length == 8)){
      return true;
    }
    return false;
  }

  private void loadUsersToORM(String[] args) throws IOException, ClassNotFoundException {
    bloomFilters = new BloomList();
    /** Create new bloom filter for every entry in arguments[1] specified file,
    then add to bloomFilters BloomList */
    if (args[1].endsWith(".sqlite3")) { //only works with sqlite databases
      orm = new ORM(args[1]);
      List<User> userList = orm.sql("SELECT * FROM users");
      for (User user : userList) {
        bloomFilters.insert(user.makeBloomFilter());
      }
    }
  }

  private void findSimilarUsersByID(String args[]) throws IOException {
    //create desired bloom filter from data in SQL database
    //compare to arraylist using AND or XNOR
    //save and return k most similar
    if (orm == null) {
      throw new IOException("ERROR: Database not loaded!");
    }
    int k = Integer.parseInt(args[1]);
    List<User> resultList = orm.where("user_id", args[2], User.class);
    if (!resultList.isEmpty()) {
      BloomFilter toCompare = resultList.get(0).makeBloomFilter();
      bloomFilters.findKSimilar(toCompare, k);
    } else {
      throw new IOException("ERROR: No such user_id");
    }
  }

  private void findSimilarUsersByAttributes(String args[]) throws IOException {
    int k = Integer.parseInt(args[1]);
    /** creates bloom filter from given arguments with userID 1 (irrelevant) */
    BloomFilter toCompare = new BloomFilter(args[2], args[3], args[4],
        Integer.parseInt(args[5]), args[6], args[7], "1");
    bloomFilters.findKSimilar(toCompare, k);
//
  }

  @Override
  public Object parseCommand(String[] args) {
    if (!this.checkValidCommand(args)){
      throw new BadCommandException(args);
    }
    if (args[0].equals("users")){
      try {
        this.loadUsersToORM(args);
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else if (args[0].equals("similar") && args.length == 3){
      try {
        this.findSimilarUsersByID(args);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (args[0].equals("similar") && args.length == 8){
      try {
        this.findSimilarUsersByAttributes(args);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public String[] relevantCommands() {
    return new String[]{"users", "similar"};
  }

  public UsersHandler(){};
}
