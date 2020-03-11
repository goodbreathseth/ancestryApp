package daos;

import java.sql.SQLException;
import java.util.UUID;

import database.Database;
import database.DatabaseException;
import models.AuthtokenModel;
import models.UserModel;

/**
 * Authtoken data access object to create, read, update, delete tokens
 */
public class AuthtokenDAO extends Database {
    public AuthtokenDAO() throws SQLException {
    }
    private Database db;

    {
        try {
            db = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addAuthtoken(String userName, String authtoken) throws DatabaseException, SQLException {

        // Open database connection
        conn = db.openConnection();

        //Generate an authtoken for the user and add it to
        //authtoken DAO
        String insert = "insert into authtoken values ( ?, ? ) ";
        stmt = conn.prepareStatement(insert);
        stmt.setString(1, userName);
        stmt.setString(2, authtoken);
        stmt.executeUpdate();
        stmt.close();

        // Close database connection
        db.closeConnection(true);

        return true;
    }


}
