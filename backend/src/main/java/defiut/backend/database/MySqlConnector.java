package defiut.backend.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * MySqlConnector class
 * Singleton class that handles the connection to the database
 */
public class MySqlConnector {

	// Attributes
	private static MySqlConnector instance;
	// private static final String DATABASE_URL = "jdbc:mysql://mysql:3306/defiut";
	// private static final String USERNAME = "defiut";
	// private static final String PASSWORD = "1234";
	private static String databaseUrl;
	private static String username;
	private static String password;

	// Constructor

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the MySqlConnector class
	 */
	public static MySqlConnector getInstance() {
		if (instance == null) {
			instance = new MySqlConnector();
			loadProperties();
		}
		return instance;
	}

	private static void loadProperties() {
		Properties properties = new Properties();
		try (InputStream inputStream = MySqlConnector.class.getClassLoader()
				.getResourceAsStream("application.properties")) {
			if (inputStream == null) {
				throw new FileNotFoundException("Properties file not found in classpath");
			}
			properties.load(inputStream);
			databaseUrl = properties.getProperty("db.url");
			username = properties.getProperty("db.username");
			password = properties.getProperty("db.password");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Connection

	/**
	 * Get a connection to the database
	 *
	 * @return a connection to the database
	 * @throws SQLException if the connection fails
	 */
	public Connection getConnection() throws SQLException {
		// return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		return DriverManager.getConnection(databaseUrl, username, password);
	}

	// Methods

	/**
	 * Get the last inserted id
	 *
	 * @param connection the connection to the database
	 * @return the last inserted id
	 * @throws SQLException if the querry fails
	 */
	public int getLastInsertedId(Connection connection) throws SQLException {
		String querry = "SELECT LAST_INSERT_ID()";
		PreparedStatement preparedStatement = connection.prepareStatement(querry);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		int ret = resultSet.getInt(1);
		if (ret == 0) {
			ret = -1;
		}
		return ret;
	}
}