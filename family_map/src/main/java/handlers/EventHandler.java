package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import helper.HttpHelper;
import models.AuthtokenModel;
import results.EventAllResult;
import results.EventResult;
import services.EventService;

public class EventHandler implements HttpHandler {
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
            //Get the request. If it is a "get" request, do the following
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                //Find out if the user provides an authToken
                //If so, retrieve the user from database to see if authtoken matches
                String authTokenStr = "";
                Headers reqHeader = exchange.getRequestHeaders();
                if (reqHeader.containsKey("Authorization"))
                    authTokenStr = reqHeader.getFirst("Authorization");

                AuthtokenModel authtokenModel = new AuthtokenModel();
                authtokenModel.setToken(authTokenStr);


                //Find out if the user wrote the amount of generations in their request or not
                String urlPath = exchange.getRequestURI().getPath();
                String[] urlArr = urlPath.split("/");
                boolean useResultOne = false;

                //Create a EventService, call the Event method and pass in the request
                EventService service = new EventService();
                EventAllResult result = null;
                EventResult result2 = null;
                if (urlArr.length == 2) {
                    result = service.event(authtokenModel);
                    useResultOne = true;
                }
                else if (urlArr.length == 3)
                    result2 = service.event(authtokenModel, urlArr[2]);
                else
                    result2 = service.eventError("Incorrect amount of arguments");



                //Instantiate gson and helper classes
                Gson gson = new Gson();
                HttpHelper helper = new HttpHelper();

                //Use gson to convert the result to a json object
                String jsonResponse;
                if (useResultOne)
                    jsonResponse = gson.toJson(result);
                else
                    jsonResponse = gson.toJson(result2);


                //Send the HTTP response to the client
                if (helper.sendHttpResponse(exchange, jsonResponse))
                    success = true;
            }
            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                // Since the client request was invalid, they will not receive the
                // list of games, so we close the response body output stream,
                // indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
