package requests;

/**
 * Holds all the necessary info for a login request such as:
 * username, password
 */
public class LoginRequest {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public LoginRequest(){}

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean allFieldsAreFilled() {
        boolean filled = true;

        if      (userName == null || userName.equals("") ||
                password == null || password.equals(""))
            filled = false;

        return filled;
    }
}
