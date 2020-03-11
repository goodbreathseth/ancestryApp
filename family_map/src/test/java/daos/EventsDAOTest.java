package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import models.EventsModel;
import services.ClearService;

import static org.junit.Assert.*;

public class EventsDAOTest {

    @Before
    public void setUp() throws Exception {
        EventsModel eventsModel = new EventsModel("eventID", "Adam","personID",  12.3, 32.1, "US",
                "PHX", "Baptism", 2018);
        EventsDAO eventsDAO = new EventsDAO();
        eventsDAO.addEvent(eventsModel);
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        authtokenDAO.addAuthtoken("Adam", "authtoken");
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void getEvent() throws SQLException {
        EventsDAO eventsDAO = new EventsDAO();
        assertEquals("Adam" ,eventsDAO.getEvent("eventID", "authtoken").getDescendant());
    }

    @Test
    public void invalidGetEvent() throws SQLException {

        EventsDAO eventsDAO = new EventsDAO();
        assertNull(eventsDAO.getEvent("eventID", "wrongAuthtoken"));
    }

    @Test
    public void getAllEventsForUser() throws SQLException {

        EventsDAO eventsDAO = new EventsDAO();
        assertEquals(1, eventsDAO.getAllEventsForUser("authtoken").length);
    }

    @Test
    public void invalidGetAllEventsForUser() throws SQLException {
        EventsDAO eventsDAO = new EventsDAO();
        assertNull(eventsDAO.getAllEventsForUser("incorrect"));

    }

    @Test
    public void addEvent() throws SQLException {
        EventsDAO eventsDAO = new EventsDAO();
        assertEquals(true, eventsDAO.addEvent(new EventsModel("eventID2", "Adam","personID2",  98.7, 78.9, "US",
                "PHX", "Birth", 2010)));
    }

    @Test
    public void otherPositiveAddEvent() throws SQLException {
        EventsDAO eventsDAO = new EventsDAO();
        assertEquals(true, eventsDAO.addEvent(new EventsModel("afs", "Adam","fds",  12.4, 4.5, "US",
                "PHX", "Baptism", 1230)));

    }
}