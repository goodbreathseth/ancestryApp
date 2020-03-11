package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import database.DatabaseException;
import models.AuthtokenModel;
import services.ClearService;

import static org.junit.Assert.*;

public class AuthtokenDAOTest {

    @Before
    public void setUp() throws Exception {
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        authtokenDAO.addAuthtoken("userName", "authtoken");
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void addAuthtoken() throws SQLException, DatabaseException {
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        assertTrue(authtokenDAO.addAuthtoken("cool_kid", "coolAuthToken"));
    }

    @Test
    public void anotherTrueAddAuthtoken() throws SQLException, DatabaseException {
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        assertTrue(authtokenDAO.addAuthtoken("test2", "coolAuthToken2"));
    }
}