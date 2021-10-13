package edu.brown.cs.student.main.API.main;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import edu.brown.cs.student.main.API.client.ApiClient;
import edu.brown.cs.student.main.API.client.ClientRequestGenerator;

import java.lang.reflect.Type;
import java.util.List;

public class ApiAggregator {
  private final ApiClient client = new ApiClient();

  /**
   *
   * @param dataType - String representing the type of data being considered (Example: rent, review, users)
   * @return - List of the appropriate objects
   * @throws Exception
   */
  public List<Object> getData(String dataType) throws Exception {
    Gson gson = new Gson();
    Type type = setType(dataType);
    String filename = "https://runwayapi.herokuapp.com/integration";
    String response1 = client.makeRequest(ClientRequestGenerator.getSecuredPostRequest(filename));
    String response2 = client.makeRequest(ClientRequestGenerator.getSecuredPostRequest(filename));
    response1 = generateExtras("", filename, response1);
    response2 = generateExtras("", filename, response2);
    String best_response = response1.length() > response2.length() ? response1 : response2;
    return gson.fromJson(best_response, type);
  }

  /**
   * Creates request until the result is not an error message ( At most 5 times )
   * @param server - The number of the api that we want to call
   * @param filename - The name of the api we are referencing
   * @param response - The response generated on the first call
   * @return - The response as a string
   */
  private String generateExtras(String server, String filename, String response) {
    String error_message = "{\"message\": \"Your API call failed due to a malicious error by the course staff\"}";
    try{
      for(int i = 0; i < 5; i++){
        if(response.equals(error_message)){
          response = client.makeRequest(ClientRequestGenerator.getSecuredPostRequest(filename + server));
        }else break;
      }
    }catch (Exception ignored) {}
    return response;
  }

  /**
   *
   * @param dataType - The type of data we are setting as a String
   * @return - A Type variable set to the desired type
   * @throws Exception
   */
  public Type setType(String dataType) throws Exception {
    Type type;

        /*

        Example usage from Project 1: Sprint...

        if(dataType.equals("rent")){
            return new TypeToken<List<Rent>>(){}.getType();
        }else if(dataType.equals("reviews")){
            return new TypeToken<List<Review>>(){}.getType();
        }else if (dataType.equals("users")){
            return new TypeToken<List<User>>(){}.getType();
        }else {
            throw new Exception("The aggregator does not contain a content type called: " + dataType);
        }
        */


    // dummy return
    return new TypeToken<List<Object>>(){}.getType();
  }

}
