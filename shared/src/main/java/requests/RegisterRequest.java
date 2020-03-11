package requests;

/**
 * Holds all the necessary info for a register request such as:
 * username, password, email, firstname, lastname, gender
 */
public class RegisterRequest {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    public RegisterRequest(){}

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean allFieldsAreFilled() {
        boolean filled = true;

        if      (userName == null || userName.equals("") ||
                 password == null || password.equals("") ||
                 email == null || email.equals("") ||
                 firstName == null || firstName.equals("") ||
                 lastName == null || lastName.equals("") ||
                 gender == null || gender.equals(""))
            filled = false;

        return filled;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getUserName() {
        return userName;
    }
}
