package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import database.DatabaseException;
import helper.HttpHelper;
import helper.LoadJsonFile;
import requests.LoadRequest;
import requests.RegisterRequest;
import results.LoadResult;
import services.LoadService;

public class LoadHandler implements HttpHandler {
    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            //Get the request. If it is a "post" request, do the following
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                // Extract the JSON string from the HTTP request body
                HttpHelper helper = new HttpHelper();
                String reqData = helper.httpRequestToJson(exchange);

                //Display/log the request JSON data
                System.out.println(reqData);

                //Create a LoadRequest object and populate it with the Json request variables

                LoadRequest request;
                Gson gson = new Gson();
                request =  gson.fromJson(reqData, LoadRequest.class);

                //RegisterRequest registerRequest = gson.fromJson(reqData, RegisterRequest.class);


                //Create a LoadService, call the Load method and pass in the request
                LoadService service = new LoadService();
                LoadResult result = service.load(request);

                //THIS IS WHERE I'M TESTING THE LOADING JSON FILE STUFF
                LoadJsonFile loader = new LoadJsonFile();

                //Use gson to convert the result to a json object
                String jsonResponse;
                jsonResponse = gson.toJson(result);

                //Send the HTTP response to the client
                if (helper.sendHttpResponse(exchange, jsonResponse))
                    success = true;
            }
            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                // We are not sending a response body, so close the response body
                // output stream, indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
