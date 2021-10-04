package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  /**
   * Called within main to activate REPL.
   * 4 commands: add, subtract, stars, and naive_neighbors.
   *
   * @throws IOException when an invalid command is given in the command line.
   */
  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      String[][] sheet = new String[1][1];
      BloomList bloomFilters = new BloomList();
      ORM orm = null;
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] arguments = input.split(" ");
          MathBot mb = new MathBot();
          if (arguments[0].equals("add") && arguments.length == 3) {
            System.out.println(mb.add(Double.parseDouble(arguments[1]), Double.parseDouble(arguments
                [2])));
          } else if (arguments[0].equals("subtract") && arguments.length == 3) {
            System.out.println(mb.subtract(Double.parseDouble(arguments[1]), Double.
                parseDouble(arguments[2])));
          } else if (arguments[0].equals("stars") && arguments.length == 2) {
            try {
              String filename = arguments[1];
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
            } catch (Exception e) {
              throw new IOException();
            }
          } else if (arguments.length == 5 && arguments[0].equals("naive_neighbors")) {
            int k = Integer.parseInt(arguments[1]);
            if (k > sheet.length) { /** cannot return k stars if k > # of stars */
              throw new IOException();
            }
            double x = Double.parseDouble(arguments[2]);
            double y = Double.parseDouble(arguments[3]);
            double z = Double.parseDouble(arguments[4]);
            this.fillDistances(sheet, x, y, z);
            this.sortCSVData(sheet);
            this.printKNearest(sheet, k, ",");
          } else if (arguments[0].equals("naive_neighbors")) {
            int k = Integer.parseInt(arguments[1]);
            if (k > sheet.length) { /** cannot return k stars if k > # of stars */
              throw new IOException();
            }
            String name = "";
            for (int i = 2; i < arguments.length; i++) { /** builds name from arguments */
              if (i == 2) {
                name += arguments[2].substring(1);
              } else {
                name += " ";
                name += arguments[i];
              }
              if (i == arguments.length - 1) {
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
          } else if (arguments[0].equals("users") && arguments.length == 2) {
            bloomFilters = new BloomList();
            /** Create new bloom filter for every entry in arguments[1] specified file,
            then add to bloomFilters BloomList */
            if (arguments[1].endsWith(".sqlite3")) { //only works with sqlite databases
              orm = new ORM(arguments[1]);
              List<User> userList = orm.sql("SELECT * FROM user");
              for (User user : userList) {
                bloomFilters.insert(user.makeBloomFilter());
              }
            }

          } else if (arguments[0].equals("similar") && arguments.length == 3) { //TODO
            //create desired bloom filter from data in SQL database
            //compare to arraylist using AND or XNOR
            //save and return k most similar
            if (orm == null) {
              throw new IOException("ERROR: Database not loaded!");
            }
            int k = Integer.parseInt(arguments[1]);
            List<User> resultList = orm.where("user_id", arguments[2], User.class);
            if (!resultList.isEmpty()) {
              BloomFilter toCompare = resultList.get(0).makeBloomFilter();
              bloomFilters.findKSimilar(toCompare, k);
            } else {
              throw new IOException("ERROR: No such user_id");
            }
          } else if (arguments[0].equals("similar") && arguments.length == 8) {
            int k = Integer.parseInt(arguments[1]);
            /** creates bloom filter from given arguments with userID 1 (irrelevant) */
            BloomFilter toCompare = new BloomFilter(arguments[2], arguments[3], arguments[4],
                Integer.parseInt(arguments[5]), arguments[6], arguments[7], "1");
            bloomFilters.findKSimilar(toCompare, k);
          } else {
            throw new IOException();
          }
        } catch (Exception e) {
          // e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }
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

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
