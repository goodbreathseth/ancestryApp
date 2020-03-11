package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import daos.AuthtokenDAO;
import daos.EventsDAO;
import models.AuthtokenModel;
import models.EventsModel;
import requests.RegisterRequest;

import static org.junit.Assert.*;

public class EventServiceTest {

    @Before
    public void setUp() throws Exception {
        EventsModel eventsModel = new EventsModel("eventID", "descendant",
                "personID", 12.3, 32.1, "country", "city", "Birth", 2018);
        EventsDAO eventsDAO = new EventsDAO();
        eventsDAO.addEvent(eventsModel);
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        authtokenDAO.addAuthtoken("descendant", "authtoken");
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void event() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "authtoken");
        EventService eventService = new EventService();
        assertEquals(1, eventService.event(authtokenModel).getData().length);
    }

    @Test
    public void invalidEventAll() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "wrongAuthtoken");
        EventService eventService = new EventService();
        assertNull(eventService.event(authtokenModel).getData());
    }

    @Test
    public void event1() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "authtoken");
        EventService eventService = new EventService();
        assertEquals("descendant", eventService.event(authtokenModel, "eventID").getDescendant());
    }

    @Test
    public void invalidSingleEvent() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "wrongAuthtoken");
        EventService eventService = new EventService();
        assertNull(eventService.event(authtokenModel, "eventID").getDescendant());
    }

    @Test
    public void eventError() {
        EventService eventService = new EventService();
        assertEquals("Error", eventService.eventError("Error").getMessage());
    }

    @Test
    public void anotherPositiveEventError() {
        EventService eventService = new EventService();
        assertEquals("Error2", eventService.eventError("Error2").getMessage());

    }
}