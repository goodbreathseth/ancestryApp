package database;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(String closeConnection_failed, SQLException e) {
    }
}
