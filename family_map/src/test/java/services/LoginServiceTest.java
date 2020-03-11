package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import daos.AuthtokenDAO;
import daos.UserDAO;
import database.DatabaseException;
import models.UserModel;
import requests.LoginRequest;

import static org.junit.Assert.*;

public class LoginServiceTest {

    @Before
    public void setUp() throws Exception {
        UserModel userModel = new UserModel("Seth", "password", "email",
                "Seth", "Hinton", "m", "personID");
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(userModel);
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        authtokenDAO.addAuthtoken("Seth", "authtoken");
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void login() throws DatabaseException, SQLException {
        LoginRequest loginRequest = new LoginRequest("Seth", "password");
        LoginService loginService = new LoginService();
        assertEquals("Seth", loginService.login(loginRequest).getUserName());
    }

    @Test
    public void invalidLogin() throws DatabaseException, SQLException {
        LoginRequest loginRequest = new LoginRequest("Seth", "invalid");
        LoginService loginService = new LoginService();
        assertEquals("Incorrect password", loginService.login(loginRequest).getMessage());

    }
}