package services;

import java.sql.SQLException;
import java.util.UUID;

import daos.AuthtokenDAO;
import daos.UserDAO;
import database.DatabaseException;
import models.UserModel;
import requests.LoginRequest;
import results.LoginResult;

/**
 * Logs in the user and returns an auth token
 */
public class LoginService {

    /**
     * Logs in the user with the provided username and password
     * and returns an auth token
     * @param l a login request containing username and password Strings
     * @return the corresponding LoginResult
     * Success Response Body:
     *
     *   authToken: cf7a368f, // Non-empty auth token string
     *   userName: susan, // User name passed in with request
     *   personID: 39f9fe46 // Non-empty string containing the Person ID of the
     *   //    user’s generated Person object
     *
     * Error Response Body:
     *
     *   message: Description of the error
     */
    public LoginResult login(LoginRequest l) throws SQLException, DatabaseException {

        LoginResult result = new LoginResult();

        //Check if the request has all the necessary fields filled properly.
        //If there is an error, set it in the result body
        if (!l.allFieldsAreFilled()) {
            result.setMessage("Not all fields have been filled");
            return result;
        }

        //Check if the user exists in database, if so, log user in.
        //Create authtoken, add it to authtoken table and return it
        //If password and username don't match, send error message
        UserDAO userDao = new UserDAO();
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        UserModel user;
        if (userDao.userExists(l.getUserName())) {
            user = userDao.getUser(l.getUserName(), l.getPassword(), true);
            if (user == null) {
                result.setMessage("Incorrect password");
                return result;
            }

            //Generate authtoken and add it to the authtoken database
            String authtoken = UUID.randomUUID().toString();
            authtokenDAO.addAuthtoken(user.getUserName(), authtoken);

            //Create the result object to return
            result.setAuthToken(authtoken);
            result.setPersonID(user.getPersonID());
            result.setUserName(user.getUserName());
            return result;

        }
        else {
            result.setMessage("User does not exist");
            return result;
        }
    }
}
