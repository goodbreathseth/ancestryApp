package services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import daos.AuthtokenDAO;
import daos.PersonsDAO;
import models.AuthtokenModel;
import models.PersonsModel;

import static org.junit.Assert.*;

public class PersonServiceTest {

    @Before
    public void setUp() throws Exception {
        PersonsModel personsModel = new PersonsModel("personID", "descendant",
                "firstName", "lastName", "m", "father", "mother", "spouse");
        PersonsDAO personsDAO = new PersonsDAO();
        personsDAO.addPerson(personsModel);
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        authtokenDAO.addAuthtoken("descendant", "authtoken");
    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void person() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "authtoken");
        PersonService personService = new PersonService();
        assertEquals(2, personService.person(authtokenModel).getData().length);
    }

    @Test
    public void invalidPerson() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "wrongAuthtoken");
        PersonService personService = new PersonService();
        assertNull(personService.person(authtokenModel).getData());
    }

    @Test
    public void person1() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "authtoken");
        PersonService personService = new PersonService();
        assertEquals("descendant", personService.person(authtokenModel, "personID").getDescendant());
    }

    @Test
    public void invalidsinglePerson() throws SQLException {
        AuthtokenModel authtokenModel = new AuthtokenModel("descendant", "wrongAuthtoken");
        PersonService personService = new PersonService();
        assertNull(personService.person(authtokenModel, "personID").getDescendant());
    }

    @Test
    public void personError() {
        PersonService personService = new PersonService();
        assertEquals("Error", personService.personError("Error").getMessage());
    }

    @Test
    public void anotherPersonError() {
        PersonService personService = new PersonService();
        assertEquals("Error2", personService.personError("Error2").getMessage());

    }
}