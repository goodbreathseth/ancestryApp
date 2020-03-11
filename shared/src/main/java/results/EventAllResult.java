package results;

import java.util.ArrayList;

import models.EventsModel;

/**
 *  Invalid auth token, Internal Server error
 *  Success Response Body: The response body returns a JSON object with a data attribute that
 *  contains an array of Event objects.  Each Event object has the same format as described in
 *  previous section on the /event/[eventID] API.
 *
 *       data: [  ​ Array of Event objects ]
 *
 *  Error Response Body:
 *      message: description of the error
 */
public class EventAllResult {
    private EventsModel[] data;

    public EventsModel[] getData() {
        return data;
    }

    public void setData(EventsModel[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
