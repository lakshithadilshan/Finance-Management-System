package systemuser.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/fms";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            // Load the JDBC driver
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        // Create and return a connection to the database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
