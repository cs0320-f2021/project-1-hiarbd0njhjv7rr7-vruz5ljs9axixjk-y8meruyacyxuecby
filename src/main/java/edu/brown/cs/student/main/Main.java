package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.student.main.BloomFilter.BloomFilter;
import edu.brown.cs.student.main.BloomFilter.BloomList;
import edu.brown.cs.student.main.DataTypes.Interest;
import edu.brown.cs.student.main.DataTypes.Negative;
import edu.brown.cs.student.main.DataTypes.Positive;
import edu.brown.cs.student.main.DataTypes.User;
import edu.brown.cs.student.main.DataTypes.Skill;
import edu.brown.cs.student.main.ORM.ORM;
import edu.brown.cs.student.main.REPL.AddHandler;
import edu.brown.cs.student.main.REPL.BadCommandException;
import edu.brown.cs.student.main.REPL.REPLCommandHandler;
import edu.brown.cs.student.main.REPL.RecommenderSystemHandler;
import edu.brown.cs.student.main.REPL.StarHandler;
import edu.brown.cs.student.main.REPL.SubtractHandler;
import edu.brown.cs.student.main.REPL.UsersHandler;
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
  @SuppressWarnings("checkstyle:MagicNumber")
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

      Map<String, Class<? extends REPLCommandHandler>> commands = Map.of(
          "add", AddHandler.class,
          "subtract", SubtractHandler.class,
          "stars", StarHandler.class,
          "users", UsersHandler.class,
          "recsys_load", RecommenderSystemHandler.class
      );

      Map<String,REPLCommandHandler> existingCommands = new HashMap<>();

      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          String[] arguments = input.split(" ");

          if (existingCommands.get(arguments[0]) == null){

            Class<? extends REPLCommandHandler> rep = commands.get(arguments[0]);
            if (rep == null){
              throw new BadCommandException(new String[]{"You have entered an invalid command"});
            }
            Constructor<? extends REPLCommandHandler> constructor = null;

            for (Constructor<?> cxtor : rep.getConstructors()){
              constructor = (Constructor<? extends REPLCommandHandler>) cxtor;
              break;
            }

            assert constructor != null;
            REPLCommandHandler newCommandHandler = constructor.newInstance();
            String[] relatedCommands = newCommandHandler.relevantCommands();
            for (String relatedCommand : relatedCommands) {
              existingCommands.put(relatedCommand, newCommandHandler);
            }
          }

          REPLCommandHandler commandHandler = existingCommands.get(arguments[0]);
          commandHandler.parseCommand(arguments);

        } catch (Exception e) {
           e.printStackTrace();
          System.out.println("ERROR: We couldn't process your input");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
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
