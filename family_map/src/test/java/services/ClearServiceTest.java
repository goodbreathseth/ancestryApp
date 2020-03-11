package services;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import database.DatabaseException;

import static org.junit.Assert.*;

public class ClearServiceTest {



    @Test
    public void clear() throws DatabaseException, SQLException {
        ClearService clearService = new ClearService();
        assertEquals("Clear succeeded", clearService.clear().getMessage());
    }

    @Test
    public void clearTest2() throws DatabaseException, SQLException {
        ClearService clearService = new ClearService();
        assertEquals("Clear succeeded", clearService.clear().getMessage());
    }
}