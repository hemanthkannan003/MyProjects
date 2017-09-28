package databaseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnector implements AutoCloseable {
	protected Connection connection;
	// Database connection parameters

	private String url = "jdbc:mysql://www.papademas.net:3306/dbfp";
	private String username = "fpuser";
	private String password = "510";
//	private String url = "jdbc:mysql://localhost:3306/Test_Project?zeroDateTimeBehavior=convertToNull";
//	private String username = "kamalasekar";
//	private String password = "password";

	public DataBaseConnector() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Database connection failed due to " + e);
			System.exit(-1);
		}
	}

	@Override
	public void close() {
    	try {
            connection.close();
            connection = null;
        } catch(SQLException e) {
            System.out.println("Error closing connection: " + e);
        }
	}

}
