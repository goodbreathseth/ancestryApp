package daos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import database.DatabaseException;
import models.AuthtokenModel;
import models.PersonsModel;
import services.ClearService;

import static org.junit.Assert.*;

public class PersonsDAOTest {

    @Before
    public void setUp() throws Exception {
        PersonsModel personsModel = new PersonsModel("personID", "Adam", "Seth", "Hinton", "m",
                "Clark", "Nan", "yoMomma");
        PersonsDAO personsDAO = new PersonsDAO();
        personsDAO.addPerson(personsModel);
        AuthtokenDAO authtokenDAO = new AuthtokenDAO();
        authtokenDAO.addAuthtoken("Adam", "authtoken");

    }

    @After
    public void tearDown() throws Exception {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void getPerson() throws SQLException {
        PersonsDAO personsDAO = new PersonsDAO();
        assertEquals("Adam" ,personsDAO.getPerson("personID", "authtoken").getDescendant());
    }

    @Test
    public void invalidGetPerson() throws SQLException {
        PersonsDAO personsDAO = new PersonsDAO();
        assertNull(personsDAO.getPerson("personID", "wrongAuthtoken"));
    }

    @Test
    public void getAllFamilyMembersOfUser() throws SQLException, DatabaseException {

        PersonsDAO personsDAO = new PersonsDAO();
        assertEquals(2, personsDAO.getAllFamilyMembersOfUser("authtoken").length);
    }

    @Test
    public void invalidGetAllFamilyMembers() throws SQLException {
        PersonsDAO personsDAO = new PersonsDAO();
        assertNull(personsDAO.getAllFamilyMembersOfUser("incorrect"));
    }

    @Test
    public void addPerson() throws SQLException {
        PersonsDAO personsDAO = new PersonsDAO();
        assertEquals(true, personsDAO.addPerson(new PersonsModel("fedsad", "descendant", "Adam", "Hinton", "m",
                "Clark", "Nan", "yoMomma")));
    }

    @Test
    public void otherPositivePerson() throws SQLException {
        PersonsDAO personsDAO = new PersonsDAO();
        assertEquals(true, personsDAO.addPerson(new PersonsModel("asdfjkl", "descendant", "Carl", "Hinton", "m",
                "Clark", "Nan", "yoMomma")));
    }
}