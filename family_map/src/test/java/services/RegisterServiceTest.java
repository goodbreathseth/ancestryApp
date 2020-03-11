package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import database.DatabaseException;
import requests.RegisterRequest;

import static org.junit.Assert.*;

public class RegisterServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void register() throws DatabaseException, SQLException, FileNotFoundException {
        RegisterRequest registerRequest = new RegisterRequest("Seth",
                "password", "email", "Seth", "Hinton", "m");
        RegisterService registerService = new RegisterService();
        assertEquals("Seth", registerService.register(registerRequest, false).getUserName());
    }

    @Test
    public void invalidRegister() throws DatabaseException, SQLException, FileNotFoundException {

        RegisterRequest registerRequest = new RegisterRequest("wrongUser",
                "password", "email", "wrong", "Hinton", "gender");
        RegisterService registerService = new RegisterService();
        assertEquals("M or F must be selected for gender", registerService.register(registerRequest, false).getMessage());
    }
}