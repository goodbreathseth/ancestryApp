package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import database.DatabaseException;
import models.EventsModel;
import models.PersonsModel;
import models.UserModel;
import requests.LoadRequest;
import results.LoadResult;

import static org.junit.Assert.*;

public class LoadServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void load() throws FileNotFoundException, DatabaseException, SQLException {
        UserModel[] userModel = new UserModel[1];
        userModel[0] = new UserModel("Seth", "password", "email",
                "Seth", "Hinton", "m", "personID");

        EventsModel[] eventsModels = new EventsModel[1];
        eventsModels[0] = new EventsModel("eventID", "descendant",
                "personID", 12.3, 32.1, "country", "city", "Birth", 2018);

        PersonsModel[] personsModels = new PersonsModel[1];
        personsModels[0] = new PersonsModel("personID", "descendant",
                "firstName", "lastName", "m", "father", "mother", "spouse");

        LoadRequest loadRequest = new LoadRequest(userModel, personsModels, eventsModels);
        LoadService loadService = new LoadService();
        assertEquals("Successfully added 1 users, 1 persons, and 1 events to the database", loadService.load(loadRequest).getMessage());
    }

    @Test
    public void invalidLoad() throws FileNotFoundException, DatabaseException, SQLException {
        UserModel[] userModel = new UserModel[0];
        EventsModel[] eventsModels = new EventsModel[0];
        PersonsModel[] personsModels = new PersonsModel[0];
        LoadRequest loadRequest = new LoadRequest(userModel, personsModels, eventsModels);
        LoadService loadService = new LoadService();
        assertEquals("Not all fields have been filled", loadService.load(loadRequest).getMessage());

    }
}