package results;

/**
 * Errors: Internal Server error
 * Success Response Body:
 *      message: Clear succeeded.
 *
 *  Error Response Body:
 *      message: Description of the error
 */
public class ClearResult {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
