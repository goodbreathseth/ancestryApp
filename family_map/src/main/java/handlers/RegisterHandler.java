package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import database.DatabaseException;
import helper.HttpHelper;
import requests.RegisterRequest;
import results.RegisterResult;
import services.RegisterService;

public class RegisterHandler implements HttpHandler {

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

                //Create a registerRequest object and populate it with the Json request variables
                RegisterRequest request;
                Gson gson = new Gson();
                request =  gson.fromJson(reqData, RegisterRequest.class);

                //Create a registerService, call the register method and pass in the request
                RegisterService service = new RegisterService();
                RegisterResult result = null;
                try {
                    result = service.register(request, true);
                } catch (SQLException e) {
                    e.printStackTrace();

                    //System.out.println(e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf("(") + 1, e.getLocalizedMessage().indexOf(")")));

                } catch (DatabaseException e) {
                    e.printStackTrace();
                }

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
        }
    }
}
