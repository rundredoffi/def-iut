package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.Granted;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * GrantedDAO class
 * Singleton class that handles the access to the database for the Granted class
 */
public class GrantedDAO {

	private static GrantedDAO instance;

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the GrantedDAO class
	 */
	public static GrantedDAO getInstance() {
		if (instance == null) {
			instance = new GrantedDAO();
		}
		return instance;
	}

	/**
	 * Select a granted in the database
	 *
	 * @param id the id of the user
	 * @return the granted list
	 * @throws SQLException if the querry fails
	 */
	public ArrayList<Granted> select(int id) throws SQLException {
		String query = "SELECT * FROM Granted WHERE grantedUser =?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<Granted> grantedList = new ArrayList<Granted>();

		while (resultSet.next()) {
			grantedList.add(new Granted(
					resultSet.getInt("grantedUser"),
					resultSet.getInt("grantedBadgeRank"),
					resultSet.getString("grantedDate")));
		}

		return grantedList;
	}

	/**
	 * Insert a granted in the database
	 *
	 * @param granted the granted to insert
	 * @throws SQLException if the querry fails
	 */
	public void insert(Granted granted) throws SQLException {
		String query = "INSERT INTO Granted (grantedUser, grantedBadgeRank, grantedDate) VALUES (?, ?, ?)";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, granted.getGrantedUser());
		preparedStatement.setInt(2, granted.getGrantedBadgeRank());
		preparedStatement.setString(3, granted.getGrantedDate());

		preparedStatement.executeUpdate();
	}

	/**
	 * Delete a granted in the database
	 *
	 * @param granted the granted to delete
	 * @throws SQLException if the querry fails
	 */
	public void delete(Granted granted) throws SQLException {
		String query = "DELETE FROM Granted WHERE grantedUser = ? AND grantedBadgeRank = ?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, granted.getGrantedUser());
		preparedStatement.setInt(2, granted.getGrantedBadgeRank());

		preparedStatement.executeUpdate();
	}

	/**
	 * Verify if a granted exists in the database
	 *
	 * @param granted the granted to verify
	 * @return true if the granted exists, false otherwise
	 */
	public boolean exists(Granted granted) {
		try {
			String query = "SELECT * FROM Granted WHERE grantedUser = ? AND grantedBadgeRank = ?";

			PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
			preparedStatement.setInt(1, granted.getGrantedUser());
			preparedStatement.setInt(2, granted.getGrantedBadgeRank());

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
