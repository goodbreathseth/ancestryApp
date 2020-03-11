package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import daos.AuthtokenDAO;
import daos.UserDAO;
import database.DatabaseException;
import models.UserModel;

import static org.junit.Assert.*;

public class FillServiceTest {

    @Before
    public void setUp() throws Exception {
        UserDAO userDAO = new UserDAO();
        UserModel userModel = new UserModel("Seth", "password", "email",
                "Seth", "Hinton", "m", "personID");
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
    public void fill() throws DatabaseException, SQLException, FileNotFoundException {
        FillService fillService = new FillService();
        assertEquals("User does not exist", fillService.fill("", 4).getMessage());

    }

    @Test
    public void otherFill() throws DatabaseException, SQLException, FileNotFoundException {
        FillService fillService = new FillService();
        int num = -9;
        assertEquals("Incorrect generation amount", fillService.fill("Seth", num).getMessage());
    }

    @Test
    public void fillError() {
        FillService fillService = new FillService();
        assertEquals("Incorrect argument length", fillService.fillError().getMessage());
    }

    @Test
    public void fillError2() {
        FillService fillService = new FillService();
        assertEquals("Incorrect argument length", fillService.fillError().getMessage());
    }

}