package services;

import com.google.gson.Gson;

import java.sql.SQLException;

import daos.PersonsDAO;
import models.AuthtokenModel;
import models.PersonsModel;
import results.PersonAllResult;
import results.PersonResult;
import results.PersonResult;

/**
 * Returns the single Person object with the specified ID
 */
public class PersonService {
    /**
     * Method that returns a single Person object with specified ID and authtoken
     * @param a Authtoken for user
     * @param personID to search for
     * @return the corresponding PersonResult
     * Success Response Body:
     *        descendant: susan, // Name of user account this person belongs to
     *        personID: 7255e93e, // Person’s unique ID
     *        firstName: Stuart, // Person’s first name
     *        lastName: Klocke, // Person’s last name
     *        gender: m, // Person’s gender (m or f)
     *        father: 7255e93e // ID of person’s father ​[OPTIONAL, can be missing]
     *        mother: f42126c8 // ID of person’s mother ​[OPTIONAL, can be missing]
     *        spouse:f42126c8 // ID of person’s spouse ​[OPTIONAL, can be missing]
     * Error Response Body:
     *        message: Description of the error
     */
    public PersonResult person(AuthtokenModel a, String personID) throws SQLException {
        PersonResult result = new PersonResult();

        //Check if the authtoken was provided.  If it wasn't return a
        //result with a message saying that the authtoken was not given
        if (a.getToken().equals("")) {
            result.setMessage("Authtoken was not given");
            return result;
        }

        //Using the provided authtoken, query the database in the authtoken table
        //for any entries with this token, if it finds one, use the userName provided
        //and call the personID.  Make sure that person is related to the user
        PersonsDAO personsDAO = new PersonsDAO();
        PersonsModel personsModel = personsDAO.getPerson(personID, a.getToken());

        //Handle any errors I could have
        if (personsModel == null) {
            result.setMessage("No user found from authtoken provided");
            return result;
        }
        if (personsModel.getPersonID().equals("No persons in database")) {
            result.setMessage(personsModel.getPersonID());
            return result;
        }
        if (personsModel.getPersonID().equals("No specific person related to user found in database")) {
            result.setMessage(personsModel.getPersonID());
            return result;
        }

        //If everything turned out correct, convert the personsModel to a personResult
        //and return
        Gson gson = new Gson();
        result = gson.fromJson(gson.toJson(personsModel), PersonResult.class);
        return result;
    }

    /**
     * Method that returns all Person objects related to the user with given authtoken
     * @param a the authtoken to search the database and identify the user withh
     * @return PersonResult with either an array of PersonModels or an error message
     * @throws SQLException
     */
    public PersonAllResult person(AuthtokenModel a) throws SQLException {
        PersonAllResult result = new PersonAllResult();

        //Check if the authtoken was provided.  If it wasn't return a
        //result with a message saying that the authtoken was not given
        if (a.getToken().equals("")) {
            result.setMessage("Authtoken was not given");
            return result;
        }

        //Using the provided authtoken, query the database in the authtoken table
        //for any entries with this token, if it finds one, use the UserID provided
        //in the same row to call all family members related to that user
        PersonsDAO personsDAO = new PersonsDAO();
        PersonsModel[] personsModels = personsDAO.getAllFamilyMembersOfUser(a.getToken());
        if (personsModels == null) {
            result.setMessage("No user found from provided authtoken");
            return result;
        }
        if (personsModels.length == 0) {
            result.setMessage("No persons found related to the user");
            return result;
        }
        result.setData(personsModels);

        return result;
    }

    public PersonResult personError(String s) {
        PersonResult result = new PersonResult();
        result.setMessage(s);
        return result;
    }
}
