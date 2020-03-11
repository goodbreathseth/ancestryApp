package results;

/**
 * Errors: Invalid username or generations parameter, Internal Server error
 * Success Response Body:
 *      message: Successfully added X persons and Y events to the database.
 *
 * Error Response Body:
 *      message: Description of the error
 */
public class FillResult {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
