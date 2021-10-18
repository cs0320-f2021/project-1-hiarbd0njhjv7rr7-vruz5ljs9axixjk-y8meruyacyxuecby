package edu.brown.cs.student.main.REPL;

import edu.brown.cs.student.main.API.main.ApiAggregator;
import edu.brown.cs.student.main.BloomFilter.BloomList;
import edu.brown.cs.student.main.DataTypes.Interest;
import edu.brown.cs.student.main.DataTypes.Negative;
import edu.brown.cs.student.main.DataTypes.Positive;
import edu.brown.cs.student.main.DataTypes.Skill;
import edu.brown.cs.student.main.ORM.ORM;
import edu.brown.cs.student.main.PrintHelper;

import java.io.IOException;
import java.util.List;

public class RecommenderSystemHandler implements REPLCommandHandler{

  private ORM orm;
  private List<Negative> negList;
  private List<Positive> posList;
  private List<Interest> interestList;
  private List<Skill> skillsList;
  private ApiAggregator api;
  private List<Object> identities;


  @Override
  public boolean checkValidCommand(String[] args) {
    if (args[0].equals("recsys_load") && args.length == 2){
      return true;
    } else if (args[0].equals("recsys_rec") && args.length == 3){
      return true;
    } else if (args[0].equals("recsys_gen_groups") && args.length == 2){
      return true;
    }
    return false;
  }

  private void processLoad(String[] args){
    try {
      this.api = new ApiAggregator();
      this.orm = new ORM("data/project-1/integration.sqlite3");
      this.negList = orm.sql("SELECT * FROM negative");
      this.posList = orm.sql("SELECT * FROM positive");
      this.interestList = orm.sql("SELECT * FROM interests");
      this.skillsList = orm.sql("SELECT * FROM skills");
      this.identities = api.getData("Identity");
      PrintHelper.printlnCyan("Loaded data for users!");
    } catch(Exception e){
      e.printStackTrace();
    }
  }

  private void processRecommendations(String[] args){
    // TODO: IMPLEMENT
    PrintHelper.printlnBlue("recsys_rec");
  }

  private void processGenerateGroups(String[] args){
    // TODO: IMPLEMENT
    int k = Integer.parseInt(args[1]);
    String targetID = args[2];
    //initialize bloomlist
    BloomList bl = new BloomList();
    //from API and database, create all bloom filters and add to bloomlist
    //create new bloomfilter from the targetID's data
    //call findKNearest on bloomlist on this floomfilter to print out the necessary groups
    PrintHelper.printlnBlue("recsys_gen_groups");
  }

  @Override
  public Object parseCommand(String[] args) {
    if (!this.checkValidCommand(args)){
      throw new BadCommandException(args);
    }
    if (args[0].equals("recsys_load")){
      this.processLoad(args);
    }
    else if (args[0].equals("recsys_rec")){
      try{
        this.processRecommendations(args);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      try {
        this.processGenerateGroups(args);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public String[] relevantCommands() {
    return new String[]{"recsys_load","recsys_rec","recsys_gen_groups"};
  }

  public RecommenderSystemHandler(){}
}
