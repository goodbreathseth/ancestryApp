package com.example.goodb.bighugeproject.serverProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import helper.HttpHelper;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventAllResult;
import results.LoginResult;
import results.PersonAllResult;
import results.PersonResult;
import results.RegisterResult;

public class ServerProxy {
    HttpHelper helper = new HttpHelper();
    RegisterResult registerResult;
    LoginResult loginResult;

    public RegisterResult register(String host, String port, RegisterRequest registerRequest) {
        try {
            URL url = new URL("http://" + host + ":" + port + "/user/register");
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();

            String reqData = helper.requestToJson(registerRequest);
            OutputStream reqBody = conn.getOutputStream();
            // Write the JSON data to the request body
            helper.writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the input stream containing the HTTP response body
                InputStream respBody = conn.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = helper.readString(respBody);

                // Display the JSON data returned from the server
                System.out.println(respData);
                registerResult =  helper.stringToRegisterResult(respData);


            } else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + conn.getResponseMessage());
                registerResult = new RegisterResult();
                registerResult.setMessage("Error contacting server");
            }
        } catch (IOException e) {
            registerResult = new RegisterResult();
            registerResult.setMessage("Error contacting server");
            e.printStackTrace();
            return registerResult;
        }

        return registerResult;
    }

    public LoginResult login(String host, String port, LoginRequest loginRequest) {
        try {
            URL url = new URL("http://" + host + ":" + port + "/user/login");
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();

            String reqData = helper.requestToJson(loginRequest);
            OutputStream reqBody = conn.getOutputStream();
            // Write the JSON data to the request body
            helper.writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the input stream containing the HTTP response body
                InputStream respBody = conn.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = helper.readString(respBody);

                // Display the JSON data returned from the server
                System.out.println(respData);
                loginResult =  helper.stringToLoginResult(respData);

            } else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + conn.getResponseMessage());
                loginResult = new LoginResult();
                loginResult.setMessage("Error contacting server");
            }
        } catch (IOException e) {
            loginResult = new LoginResult();
            loginResult.setMessage("Error contacting server");
            e.printStackTrace();
            return loginResult;
        }

        return loginResult;
    }

    public PersonResult getPerson(String host, String port, String authToken, String personID) {
        PersonResult personResult;

        try {
            URL url = new URL("http://" + host + ":" + port + "/person/" + personID);
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) url.openConnection();

            // Indicate that this request will not contain an HTTP request body
            conn.setDoOutput(false);

            // Specify that we are sending an HTTP GET request
            conn.setRequestMethod("GET");

            // Add an auth token to the request in the HTTP "Authorization" header
            conn.addRequestProperty("Authorization", authToken);

            // Connect to the server and send the HTTP request
            conn.connect();


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the input stream containing the HTTP response body
                InputStream respBody = conn.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = helper.readString(respBody);

                // Display the JSON data returned from the server
                System.out.println(respData);
                personResult =  helper.stringToGetPersonResult(respData);

            } else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + conn.getResponseMessage());
                personResult = new PersonResult();
                personResult.setMessage("Error contacting server");
            }
        } catch (IOException e) {
            personResult = new PersonResult();
            personResult.setMessage("Error contacting server");
            e.printStackTrace();
            return personResult;
        }

        return personResult;

    }

    public PersonAllResult getAllPeople(String host, String port, String authToken) {
        PersonAllResult allPeople;

        try {
            URL url = new URL("http://" + host + ":" + port + "/person");
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) url.openConnection();

            // Indicate that this request will not contain an HTTP request body
            conn.setDoOutput(false);

            // Specify that we are sending an HTTP GET request
            conn.setRequestMethod("GET");

            // Add an auth token to the request in the HTTP "Authorization" header
            conn.addRequestProperty("Authorization", authToken);

            // Connect to the server and send the HTTP request
            conn.connect();


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the input stream containing the HTTP response body
                InputStream respBody = conn.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = helper.readString(respBody);

                // Display the JSON data returned from the server
                System.out.println(respData);
                allPeople =  helper.stringToGetPeople(respData);

            } else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + conn.getResponseMessage());
                allPeople = new PersonAllResult();
                allPeople.setMessage("Error contacting server");
            }
        } catch (IOException e) {
            allPeople = new PersonAllResult();
            allPeople.setMessage("Error contacting server");
            e.printStackTrace();
            return allPeople;
        }

        return allPeople;

    }

    public EventAllResult getAllEvents(String host, String port, String authToken) {
        EventAllResult allEvents;

        try {
            URL url = new URL("http://" + host + ":" + port + "/event");
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) url.openConnection();

            // Indicate that this request will not contain an HTTP request body
            conn.setDoOutput(false);

            // Specify that we are sending an HTTP GET request
            conn.setRequestMethod("GET");

            // Add an auth token to the request in the HTTP "Authorization" header
            conn.addRequestProperty("Authorization", authToken);

            // Connect to the server and send the HTTP request
            conn.connect();


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the input stream containing the HTTP response body
                InputStream respBody = conn.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = helper.readString(respBody);

                // Display the JSON data returned from the server
                System.out.println(respData);
                allEvents =  helper.stringToGetAllEvents(respData);

            } else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + conn.getResponseMessage());
                allEvents = new EventAllResult();
                allEvents.setMessage("Error contacting server");
            }
        } catch (IOException e) {
            allEvents = new EventAllResult();
            allEvents.setMessage("Error contacting server");
            e.printStackTrace();
            return allEvents;
        }

        return allEvents;

    }


}
