package results;

/**
 * Errors: Invalid request data (missing values, invalid values, etc.), Internal Server error
 * Success Response Body:
 *      message: Successfully added X users, Y persons, and Z events to the database.
 * Error Response Body:
 *      message: Description of the error
 */
public class LoadResult {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
