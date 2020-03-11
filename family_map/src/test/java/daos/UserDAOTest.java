package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import database.DatabaseException;
import models.UserModel;
import requests.RegisterRequest;
import services.ClearService;
import services.RegisterService;

import static org.junit.Assert.*;

public class UserDAOTest {

    @Before
    public void setUp() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("seth_hinton", "password", "seth@gmail.com", "Seth",
                "Hinton", "m");
        RegisterService registerService = new RegisterService();
        registerService.register(registerRequest, false);
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void getUser() throws SQLException, DatabaseException {
        UserDAO userDAO = new UserDAO();
        assertEquals("seth_hinton",
                userDAO.getUser("seth_hinton", "password", true).getUserName());
  }

    @Test
    public void invalidGetUser() throws SQLException, DatabaseException {
        // bad
        UserDAO userDAO = new UserDAO();
        assertEquals(null, userDAO.getUser("seth_hinton", "wrongPassword", true));
    }

    @Test
    public void invalidAddUser() throws DatabaseException, SQLException {
        UserDAO userDAO = new UserDAO();
        assertEquals(false, userDAO.addUser(new UserModel("seth_hinton", "password", "seth@gmail.com", "Seth",
                "Hinton", "m", "ASJFDKL")));

    }

    @Test
    public void addUser() throws DatabaseException, SQLException {
        UserDAO userDAO = new UserDAO();
        assertEquals(true, userDAO.addUser(new UserModel("johnn", "password",
                "email", "john", "appleseed", "m", "asfjdkl")));

    }

    @Test
    public void invalidUserExists() throws SQLException, DatabaseException {
        UserDAO userDAO = new UserDAO();
        assertEquals(false, userDAO.userExists("invalid"));
    }

    @Test
    public void userExists() throws SQLException, DatabaseException {
        UserDAO userDAO = new UserDAO();
        assertEquals(true, userDAO.userExists("seth_hinton"));
    }

    @Test
    public void deleteAll() throws SQLException, DatabaseException {
        UserDAO userDAO = new UserDAO();
        assertEquals(true, userDAO.deleteAll());
    }

    @Test
    public void alsoValidDeleteAll() throws SQLException, DatabaseException {
        UserDAO userDAO = new UserDAO();
        assertEquals(true, userDAO.deleteAll());
    }

    @Test
    public void deleteEventsAndPersonsRelatedToUser() throws SQLException {
        UserDAO userDAO = new UserDAO();
        assertEquals(true, userDAO.deleteEventsAndPersonsRelatedToUser("seth_hinton"));
    }

    @Test
    public void invalidDeleteEventsPersonsRelatedToUser() throws SQLException {
        UserDAO userDAO = new UserDAO();
        assertEquals(true, userDAO.deleteEventsAndPersonsRelatedToUser("invalid"));
    }
}