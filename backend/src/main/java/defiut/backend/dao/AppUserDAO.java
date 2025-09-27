package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.AppUser;
import defiut.backend.model.domain.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * AppUserDAO class
 * Singleton class that handles the access to the database for the AppUser class
 */
public class AppUserDAO {

	private static AppUserDAO instance;

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the AppUserDAO class
	 */
	public static AppUserDAO getInstance() {
		if (instance == null) {
			instance = new AppUserDAO();
		}
		return instance;
	}

	/**
	 * Insert a user in the database
	 *
	 * @param user the user to insert
	 * @throws SQLException if the querry fails
	 */
	public void insert(AppUser user) throws SQLException {
		String querry = "INSERT INTO User (userNickname, userEmail, userPassword, userRole, userScore) VALUES (?, ?, ?, ?, ?)";
		Connection connection = MySqlConnector.getInstance().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(querry);

		preparedStatement.setString(1, user.getNickname());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPassword());
		preparedStatement.setString(4, user.getRole().toString());
		preparedStatement.setInt(5, user.getScore());

		preparedStatement.executeUpdate();

		user.setId(MySqlConnector.getInstance().getLastInsertedId(connection));
	}

	/**
	 * Update a user in the database
	 *
	 * @param user the user to update
	 * @throws SQLException if the querry fails
	 */
	public void update(AppUser user) throws SQLException {
		String querry = "UPDATE User SET userNickname=?, userEmail=?, userPassword=?, userRole=?, userScore=? WHERE userId=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setString(1, user.getNickname());
		preparedStatement.setString(2, user.getEmail());
		preparedStatement.setString(3, user.getPassword());
		preparedStatement.setString(4, user.getRole().toString());
		preparedStatement.setInt(5, user.getScore());
		preparedStatement.setInt(6, user.getId());

		preparedStatement.executeUpdate();
	}

	/**
	 * Delete a user from the database
	 *
	 * @param user the user to delete
	 * @throws SQLException if the querry fails
	 */
	public void delete(AppUser user) throws SQLException {
		String querry = "DELETE FROM User WHERE userId=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setInt(1, user.getId());

		preparedStatement.executeUpdate();
	}

	/**
	 * Select a user from the database
	 *
	 * @param id the id of the user to select
	 * @return the user selected
	 * @throws SQLException if the querry fails
	 */
	public AppUser select(int id) throws SQLException {
		String querry = "SELECT * FROM User WHERE userId=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			String nickname = resultSet.getString("userNickname");
			String email = resultSet.getString("userEmail");
			String password = resultSet.getString("userPassword");
			UserRole role = UserRole.valueOf(resultSet.getString("userRole"));
			int score = resultSet.getInt("userScore");
			return new AppUser(id, nickname, email, password, role, score);
		} else {
			return null;
		}
	}

	/**
	 * Select all the users from the database
	 *
	 * @return an ArrayList of all the users
	 * @throws SQLException if the querry fails
	 */
	public ArrayList<AppUser> findAll() throws SQLException {
		String querry = "SELECT * FROM User";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<AppUser> users = new ArrayList<AppUser>();
		while (resultSet.next()) {
			int id = resultSet.getInt("userId");
			String nickname = resultSet.getString("userNickname");
			String email = resultSet.getString("userEmail");
			String password = resultSet.getString("userPassword");
			UserRole role = UserRole.valueOf(resultSet.getString("userRole"));
			int score = resultSet.getInt("userScore");
			users.add(new AppUser(id, nickname, email, password, role, score));
		}
		return users;
	}

	/**
	 * Check if an email exists in the database
	 *
	 * @param email the email to check
	 * @return true if the email exists, false otherwise
	 * @throws SQLException if the querry fails
	 */
	public boolean emailExists(String email) throws SQLException {
		String querry = "SELECT * FROM User WHERE userEmail=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setString(1, email);

		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.next();
	}

	/**
	 * Check if a nickname exists in the database
	 *
	 * @param nickname the nickname to check
	 * @return true if the nickname exists, false otherwise
	 * @throws SQLException if the querry fails
	 */
	public boolean nicknameExists(String nickname) throws SQLException {
		String querry = "SELECT * FROM User WHERE userNickname=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setString(1, nickname);

		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.next();
	}

	/**
	 * Find a user by his email
	 *
	 * @param email the email of the user to find
	 * @return the user found
	 * @throws SQLException if the querry fails
	 */
	public AppUser findByEmail(String email) throws SQLException {
		String querry = "SELECT * FROM User WHERE userEmail=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setString(1, email);

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int id = resultSet.getInt("userId");
			String nickname = resultSet.getString("userNickname");
			String password = resultSet.getString("userPassword");
			UserRole role = UserRole.valueOf(resultSet.getString("userRole"));
			int score = resultSet.getInt("userScore");
			return new AppUser(id, nickname, email, password, role, score);
		} else {
			return null;
		}
	}

	/**
	 * Find a user by his nickname
	 *
	 * @param nickname the nickname of the user to find
	 * @return the user found
	 * @throws SQLException if the querry fails
	 */
	public AppUser findByNickname(String nickname) throws SQLException {
		String querry = "SELECT * FROM User WHERE userNickname=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setString(1, nickname);

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int id = resultSet.getInt("userId");
			String email = resultSet.getString("userEmail");
			String password = resultSet.getString("userPassword");
			UserRole role = UserRole.valueOf(resultSet.getString("userRole"));
			int score = resultSet.getInt("userScore");
			return new AppUser(id, nickname, email, password, role, score);
		} else {
			return null;
		}
	}
}
