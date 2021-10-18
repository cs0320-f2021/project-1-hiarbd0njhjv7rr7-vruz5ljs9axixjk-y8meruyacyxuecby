package edu.brown.cs.student.main.REPL;

import edu.brown.cs.student.main.API.main.ApiAggregator;
import edu.brown.cs.student.main.BloomFilter.BloomFilter;
import edu.brown.cs.student.main.BloomFilter.BloomList;
import edu.brown.cs.student.main.DataTypes.Identity;
import edu.brown.cs.student.main.DataTypes.Interest;
import edu.brown.cs.student.main.DataTypes.Negative;
import edu.brown.cs.student.main.DataTypes.Positive;
import edu.brown.cs.student.main.DataTypes.Skill;
import edu.brown.cs.student.main.DataTypes.StudentCategorical;
import edu.brown.cs.student.main.DataTypes.StudentNumerical;
import edu.brown.cs.student.main.KDtree.coordinates.Coordinate;
import edu.brown.cs.student.main.KDtree.coordinates.KdTree;
import edu.brown.cs.student.main.KDtree.node.Node;
import edu.brown.cs.student.main.KDtree.searchAlgorithms.KdTreeSearch;
import edu.brown.cs.student.main.ORM.ORM;
import edu.brown.cs.student.main.PrintHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommenderSystemHandler implements REPLCommandHandler {

  private ORM orm;
  private ApiAggregator api;
  private BloomList studentBloomList;
  private Map<String, Coordinate<String>> idCoordinateMap = new HashMap<>();
  private Map<String, BloomFilter> bloomFilterMap = new HashMap<>();


  @Override
  public boolean checkValidCommand(String[] args) {
    if (args[0].equals("recsys_load") && args.length == 2) {
      return true;
    } else if (args[0].equals("recsys_rec") && args.length == 3) {
      return true;
    } else if (args[0].equals("recsys_gen_groups") && args.length == 2) {
      return true;
    }
    return false;
  }

  private void processLoad(String[] args) {
    try {
      this.api = new ApiAggregator();
      this.orm = new ORM("data/project-1/integration.sqlite3");
      List<Negative> negList = orm.sql("SELECT * FROM negative");
      List<Positive> posList = orm.sql("SELECT * FROM positive");
      List<Interest> interestList = orm.sql("SELECT * FROM interests");
      List<Skill> skillsList = orm.sql("SELECT * FROM skills");
      List<Object> identities = api.getData("Identity");

      studentBloomList = new BloomList();
      Collection<StudentCategorical> categoricalData =
          categoricalParser(negList, posList, interestList, identities);

      for (StudentCategorical sc : categoricalData) {
        BloomFilter scBF = sc.makeBloomFilter();
        studentBloomList.insert(scBF);
        bloomFilterMap.put(sc.getId(), scBF);
      }

      for (Skill skill : skillsList) {
        Coordinate<String> coord = new StudentNumerical(skill);
        idCoordinateMap.put(coord.getId(), coord);
      }
      PrintHelper.printlnCyan("Loaded Recommender with " + identities.size() + " students.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Collection<StudentCategorical> categoricalParser(List<Negative> negList,
                                                           List<Positive> posList,
                                                           List<Interest> interestList,
                                                           List<Object> identityList)
      throws Exception {
    Map<Integer, StudentCategorical> categoricalMap = new HashMap<>();
    for (Negative negative : negList) {
      int id = negative.getId();
      if (!categoricalMap.containsKey(id)) {
        categoricalMap.put(id, new StudentCategorical(String.valueOf(id)));
      }
      categoricalMap.get(id).addNeg(negative.getTrait());
    }
    for (Positive positive : posList) {
      int id = positive.getId();
      if (!categoricalMap.containsKey(id)) {
        categoricalMap.put(id, new StudentCategorical(String.valueOf(id)));
      }
      categoricalMap.get(id).addPos(positive.getTrait());
    }
    for (Interest interest : interestList) {
      int id = interest.getId();
      if (!categoricalMap.containsKey(id)) {
        categoricalMap.put(id, new StudentCategorical(String.valueOf(id)));
      }
      categoricalMap.get(id).addInterest(interest.getInterest());
    }
    for (Object identity : identityList) {
      Identity identityObj = Identity.class.cast(identity);
      identityObj.populateCategoricalMap(categoricalMap);
    }
    return categoricalMap.values();
  }

  private void processRecommendations(String[] args) {
    int numRecs = Integer.parseInt(args[1]);
    String id = args[2];
    BloomFilter targetBF = bloomFilterMap.get(id);
    Coordinate<String> origCoord = idCoordinateMap.get(id);
    Coordinate<String> targetCoord = null;
    // Invert data so we find students that fill in the target student's weaknesses
    if (origCoord instanceof StudentNumerical) {
      targetCoord = ((StudentNumerical) origCoord).invertData();
    }
    List<Coordinate<String>> coordinates = new ArrayList<>(idCoordinateMap.values());
    idCoordinateMap.put(id, targetCoord);
    KdTree<String> studentTree = new KdTree<>(6, coordinates);
    Node<Coordinate<String>> rootNode = studentTree.getRoot();


    List<Coordinate<String>> kdTreeRecs = new KdTreeSearch<String>()
        .getNearestNeighborsResult(numRecs, targetCoord, rootNode, true);
    List<String> bfRecs = studentBloomList.findKSimilar(targetBF, numRecs);

    List<String> recommendations = new ArrayList<>();
    for (Coordinate<String> coord : kdTreeRecs) {
      if (bfRecs.contains(coord.getId())) {
        recommendations.add(coord.getId());
      }
    }
    int size = recommendations.size();
    while (size < numRecs) {
      if (!kdTreeRecs.isEmpty()) {
        String newId = kdTreeRecs.remove(0).getId();
        if (!recommendations.contains(newId)) {
          recommendations.add(newId);
          size += 1;
        }
      }
      if (!bfRecs.isEmpty()) {
        String newId = bfRecs.remove(0);
        if (!recommendations.contains(newId)) {
          recommendations.add(newId);
          size += 1;
        }
      }
    }
    if (recommendations.size() > numRecs) {
      recommendations = recommendations.subList(0, numRecs);
    }
    for (String rec : recommendations) {
      System.out.println(rec);
    }
    // Replace targetCoord with origCoord for other uses of creating matches.
    idCoordinateMap.put(id, origCoord);
  }

  private void processGenerateGroups(String[] args) {
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
    if (!this.checkValidCommand(args)) {
      throw new BadCommandException(args);
    }
    if (args[0].equals("recsys_load")) {
      this.processLoad(args);
    } else if (args[0].equals("recsys_rec")) {
      try {
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
    return new String[] {"recsys_load", "recsys_rec", "recsys_gen_groups"};
  }

  public RecommenderSystemHandler() {
  }
}
