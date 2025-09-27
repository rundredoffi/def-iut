package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.Completed;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * CompletedDAO class
 * Singleton class that handles the access to the database for the Completed
 * class
 */
public class CompletedDAO {

	private static CompletedDAO instance;

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the CompletedDAO class
	 */
	public static CompletedDAO getInstance() {
		if (instance == null) {
			instance = new CompletedDAO();
		}
		return instance;
	}

	/**
	 * Select a completed in the database
	 *
	 * @param id the id of the user
	 * @return the completed list
	 * @throws SQLException if the querry fails
	 */
	public ArrayList<Completed> select(int id) throws SQLException {
		String query = "SELECT * FROM Completed WHERE completedUser=?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<Completed> completedList = new ArrayList<Completed>();

		while (resultSet.next()) {
			completedList.add(new Completed(resultSet.getInt("completedUser"), resultSet.getInt("completedChallenge"),
					resultSet.getString("completedStatus"), resultSet.getString("completedDate")));
		}

		return completedList;
	}

	/**
	 * Insert a completed in the database
	 *
	 * @param completed the completed to insert
	 * @throws SQLException if the querry fails
	 */
	public void insert(Completed completed) throws SQLException {
		String query = "INSERT INTO Completed (completedUser, completedChallenge, completedStatus, completedDate) VALUES (?, ?, ?, ?)";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, completed.getCompletedUser());
		preparedStatement.setInt(2, completed.getCompletedChallenge());
		preparedStatement.setString(3, completed.getCompletedStatus());
		preparedStatement.setString(4, completed.getCompletedDate());

		preparedStatement.executeUpdate();
	}

	/**
	 * Update a completed in the database
	 *
	 * @param completed the completed to update
	 * @throws SQLException if the querry fails
	 */
	public void update(Completed completed) throws SQLException {
		String query = "UPDATE Completed SET completedStatus=?, completedDate=? WHERE completedUser=? AND completedChallenge=?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setString(1, completed.getCompletedStatus());
		preparedStatement.setString(2, completed.getCompletedDate());
		preparedStatement.setInt(3, completed.getCompletedUser());
		preparedStatement.setInt(4, completed.getCompletedChallenge());

		preparedStatement.executeUpdate();
	}

	/**
	 * Delete a completed in the database
	 *
	 * @param completed the completed to delete
	 * @throws SQLException if the querry fails
	 */
	public void delete(Completed completed) throws SQLException {
		String query = "DELETE FROM Completed WHERE completedUser=? AND completedChallenge=?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, completed.getCompletedUser());
		preparedStatement.setInt(2, completed.getCompletedChallenge());

		preparedStatement.executeUpdate();
	}

	/**
	 * Verify if a completed exists in the database
	 *
	 * @param completed the completed to verify
	 * @return true if the completed exists, false otherwise
	 */
	public boolean exists(Completed completed) {
		try {
			String query = "SELECT * FROM Completed WHERE completedUser=? AND completedChallenge=?";

			PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
			preparedStatement.setInt(1, completed.getCompletedUser());
			preparedStatement.setInt(2, completed.getCompletedChallenge());

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if a challenge with the specified ID is marked as completed in the database.
	 *
	 * This method queries the "Completed" table to determine if there is an entry
	 * with the given challenge ID and a status of "COMPLETED".
	 *
	 * @param challengeId The ID of the challenge to check.
	 * @return true if the challenge is completed, false otherwise.
	 */
	public boolean challengeIsCompleted(int challengeId) {
		try {
			String query = "SELECT * FROM Completed WHERE completedChallenge=? AND completedStatus='COMPLETED'";

			PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
			preparedStatement.setInt(1, challengeId);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
