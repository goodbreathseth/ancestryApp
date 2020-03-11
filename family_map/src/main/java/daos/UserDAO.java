package daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import database.Database;
import database.DatabaseException;
import models.UserModel;

/**
 * User data access object to create, read, update and delete users
 */
public class UserDAO extends Database {

        private Database db;

    {
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDAO() throws SQLException {
    }


    /**
     * Method to get a user from the database
     * @param username unique String of the username to retrieve
     * @param password String of the password correlating to user in case the username is
     *                 correct, but the password is not known
     * @param passwordIsNeeded
     * @return the corresponding User object
     */
    public UserModel getUser(String username, String password, boolean passwordIsNeeded) throws SQLException, DatabaseException {

        // Open database connection
        conn = db.openConnection();

        String sql = "SELECT userName, password, email, firstName, lastName, gender, personID FROM user";
        stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        //If there is a user in the table that has a matching username and password as what is
        //given, return that user in a UserModel
        UserModel user = new UserModel();
        if (passwordIsNeeded) {
            while (rs.next()) {
                if (rs.getString("userName").equals(username) &&
                        rs.getString("password").equals(password)) {
                    user.setUserName(rs.getString("userName"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setGender(rs.getString("gender"));
                    user.setPersonID(rs.getString("personID"));
                    // Close database connection
                    db.closeConnection(true);
                    return user;
                }
            }
        }

        else {
            while (rs.next()) {
                if (rs.getString("userName").equals(username)) {
                    user.setUserName(rs.getString("userName"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setGender(rs.getString("gender"));
                    user.setPersonID(rs.getString("personID"));
                    // Close database connection
                    db.closeConnection(true);
                    return user;
                }
            }
        }

        // Close database connection
        db.closeConnection(true);
        return null;
    }


    /**
     * Method adds user to database.
     * @param u User object to add
     * @return true if user was successfully added. False if otherwise
     */
    public boolean addUser(UserModel u)  throws SQLException, DatabaseException {

        try {
            //Check if user exists before adding to the database.
            //If user exists, close database and return false
            if (userExists(u.getUserName())) {
                //db.closeConnection(true);
                return false;
            }

            //Generate a personID for the user
            if (u.getPersonID() == null)
                u.setPersonID(UUID.randomUUID().toString());

            // Open database connection
            conn = db.openConnection();

            // Insert a user Tuple
            String insert = "insert into user values ( ?, ?, ?, ?, ?, ?, ? ) ";
            stmt = conn.prepareStatement(insert);       // throws SQLException
            stmt.setString(1, u.getUserName());                              // throws SQLException
            stmt.setString(2, u.getPassword());                       // throws SQLException
            stmt.setString(3, u.getEmail());            // throws SQLException
            stmt.setString(4, u.getFirstName());
            stmt.setString(5, u.getLastName());
            stmt.setString(6, u.getGender());
            stmt.setString(7, u.getPersonID());
            stmt.executeUpdate();                             // throws SQLException
            stmt.close();                                     // throws SQLException

            // Close database connection
            db.closeConnection(true);
        }

        catch (SQLException ex) {
            try {
                db.closeConnection(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * Checks the database if a user already exists.  Used when adding a user to make
     * sure the user is not already in the database
     * @param userName unique String userName to search for
     * @return true if user exists.  False if user does not
     */
    public boolean userExists(String userName) throws SQLException, DatabaseException {
        // Open database connection
        conn = db.openConnection();

        //Query the database to find if the userName exists.  If it does, return true
        String sql = "SELECT userName FROM user";
        stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
            if (rs.getString("userName").equals(userName)) {
                //Close database connection
                db.closeConnection(true);
                return true;
            }

        //Close database connection
        db.closeConnection(true);
        return false;
    }

    public boolean deleteAll() throws DatabaseException, SQLException {
        deleteTableData("user");
        deleteTableData("authtoken");
        deleteTableData("events");
        deleteTableData("persons");
        return true;
    }

    public boolean deleteEventsAndPersonsRelatedToUser(String userName) {
        try {
            // Open database connection
            conn = db.openConnection();

            //Create sql statement
            String sql = "delete from events where descendant = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString( 1, userName );
            stmt.executeUpdate();
            stmt.close();

            sql = "delete from persons where descendant = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString( 1, userName );
            stmt.executeUpdate();
            stmt.close();

            sql = "delete from persons where descendant = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString( 1, "n/a" );
            stmt.executeUpdate();
            stmt.close();

            //Close database connection
            db.closeConnection(true);
        }
        catch (SQLException ex) {
            try {
                db.closeConnection(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Method cycles through all users in user table and calls delete on them
     * @return true once all user are successfully deleted
     */
    private boolean deleteTableData(String tableName) throws SQLException, DatabaseException {
        try {
            // Open database connection
            conn = db.openConnection();

            //Create spl statement
            String sql = "DELETE FROM " + tableName;
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();

            //Close database connection
            db.closeConnection(true);
        }
        catch (SQLException ex) {
            try {
                db.closeConnection(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (DatabaseException e) {
            try {
                db.closeConnection(false);
            } catch (Exception ex) {
                e.printStackTrace();
            }
        }
        return true;

    }

}
