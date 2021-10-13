package edu.brown.cs.student.main.API.client;

import java.net.URI;
import java.net.http.HttpRequest;

/**
 * This class generates the HttpRequests that are then used to make requests from the ApiClient.
 */
public class ClientRequestGenerator {


  /**
   * Similar to the introductory GET request, but restricted to api key holders only. Try calling it without the API
   * Key configured and see what happens!
   *
   * @return an HttpRequest object for accessing the secured resource.
   */
  public static HttpRequest getSecuredRequest(String filepath) {
    ClientAuth clientAuth = new ClientAuth();
    String apiKey = clientAuth.getApiKey();
    String[] apiKeyArray = apiKey.split(" ");
    String user = apiKeyArray[0];
    String password = apiKeyArray[1];
    String reqUri = filepath + user + "&key=" + password;
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(reqUri))
              .build();
    return request;
  }

  /**
   * Similar to the secured GET request, but is a POST request instead.
   *
   * @param filepath - path to the URL to request from
   * @return an HttpRequest object for accessing and posting to the secured resource.
   */
  public static HttpRequest getSecuredPostRequest(String filepath) {
    String apiKey = ClientAuth.getApiKey();
    String[] apiKeyArray = apiKey.split(" ");
    String auth = apiKeyArray[0];
    String password = apiKeyArray[1];
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(filepath))
        .header("x-api-key", password)
        .POST(HttpRequest.BodyPublishers.ofString("{\"auth\":\"" + auth + "\"}"))
        .build();
    return request;
  }
}
