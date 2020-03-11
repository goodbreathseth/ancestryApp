package services;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import daos.UserDAO;
import database.DatabaseException;
import models.UserModel;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;

/**
 * This class creates a new user account, generates 4 generations of ancestor data for the new user,
 * logs the user in, and returns an auth token
 */
public class RegisterService {

    private String personID;

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * This method creates a new user account, generates 4 generations of
     * ancestor data for the new user, logs the user in,
     * and returns an auth token
     * @param r RegisterRequest containing all the necessary info needed
     *          to create a new user
     * @param fillGenerations
     * @return corresponding RegisterResult
     * Success Response Body: {
     *   authToken: cf7a368f, // Non-empty auth token string
     *   userName: susan, // User name passed in with request
     *   personID: 39f9fe46 // Non-empty string containing the Person ID of the
     *   //    user’s generated Person object }
     *
     * Error Response Body:
     *   { message: Description of the error }
     */
    public RegisterResult register(RegisterRequest r, boolean fillGenerations) throws SQLException, DatabaseException, FileNotFoundException {
        RegisterResult result = new RegisterResult();

        //Check if the request has all the necessary fields filled properly.
        //If there is an error, set it in the result body
        if (!r.allFieldsAreFilled()) {
            result.setMessage("Not all fields have been filled");
            return result;
        }
        if (!r.getGender().toLowerCase().equals("m") && !r.getGender().toLowerCase().equals("f")) {
            result.setMessage("M or F must be selected for gender");
            return result;
        }

        //Instantiate userDAO and userModel using gson
        Gson gson = new Gson();
        UserModel user = gson.fromJson(gson.toJson(r), UserModel.class);
        if (!fillGenerations && (personID != null)) {
            user.setPersonID(personID);
        }
        UserDAO dao = new UserDAO();

        //Call dao function to add a user
        if (!dao.addUser(user)) {
            result.setMessage("User already exists");
            return result;
        }

        //Log user in and return an authtoken
        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(r.getUserName());
        loginRequest.setPassword(r.getPassword());
        LoginResult loginResult = loginService.login(loginRequest);
        result = gson.fromJson(gson.toJson(loginResult), RegisterResult.class);

        //Create 4 generations of data using fill method
        if (fillGenerations) {
            FillService fillService = new FillService();
            fillService.fill(r.getUserName(), 4);
        }


        return result;
    }
}
