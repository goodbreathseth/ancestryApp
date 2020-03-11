package results;

import java.util.ArrayList;

import models.PersonsModel;

/**
 * Errors: Invalid auth token, Internal Server error
 * Success Response Body: The response body returns a JSON object with a data attribute that
 * contains an array of Person objects.  Each Person object has the same format as described in
 * previous section on the /person/[personID] API
 *      data: [  ​ Array of Person objects
 *
 * Error Response Body:
 *      message: Description of the error
 */
public class PersonAllResult {
    private PersonsModel[] data;
    private String message;

    public PersonsModel[] getData() {
        return data;
    }

    public void setData(PersonsModel[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
