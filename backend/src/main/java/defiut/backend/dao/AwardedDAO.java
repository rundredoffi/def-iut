package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.Achievement;
import defiut.backend.model.AppUser;
import defiut.backend.model.Awarded;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * AwardedDAO class
 * Singleton class that handles the access to the database for the Awarded class
 */
public class AwardedDAO {

	private static AwardedDAO instance;

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the AwardedDAO class
	 */
	public static AwardedDAO getInstance() {
		if (instance == null) {
			instance = new AwardedDAO();
		}
		return instance;
	}

	/**
	 * Insert an awarded achievement in the database
	 *
	 * @param awarded the awarded achievement to insert
	 * @throws SQLException if the query fails
	 */
	public void insert(Awarded awarded) throws SQLException {
		String query = "INSERT INTO Awarded (awardedUserId, awardedAchievementId, awardedDate) VALUES (?, ?, ?)";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, awarded.getAppUser().getId());
		preparedStatement.setInt(2, awarded.getAchievement().getId());
		preparedStatement.setDate(3, new java.sql.Date(awarded.getDate().getTime()));

		preparedStatement.executeUpdate();
	}

	/**
	 * Delete an awarded achievement from the database
	 *
	 * @param awarded the awarded achievement to delete
	 * @throws SQLException if the query fails
	 */
	public void delete(Awarded awarded) throws SQLException {
		String query = "DELETE FROM Awarded WHERE awardedUserId=? AND awardedAchievementId=?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, awarded.getAppUser().getId());
		preparedStatement.setInt(2, awarded.getAchievement().getId());

		preparedStatement.executeUpdate();
	}

	/**
	 * Select all awarded achievements for a specific user
	 *
	 * @param appUser the user whose awards to retrieve
	 * @return list of awarded achievements for the user
	 * @throws SQLException if the query fails
	 */
	public ArrayList<Awarded> selectByUser(AppUser appUser) throws SQLException {
		String query = "SELECT * FROM Awarded WHERE awardedUserId=?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, appUser.getId());

		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<Awarded> awardedList = new ArrayList<>();

		while (resultSet.next()) {
			Achievement achievement = AchievementDAO.getInstance().select(resultSet.getInt("awardedAchievementId"));
			awardedList.add(new Awarded(
					appUser,
					achievement,
					resultSet.getDate("awardedDate")
			));
		}

		return awardedList;
	}

	/**
	 * Select all users who received a specific achievement
	 *
	 * @param achievement the achievement to search for
	 * @return list of awarded instances for the achievement
	 * @throws SQLException if the query fails
	 */
	public ArrayList<Awarded> selectByAchievement(Achievement achievement) throws SQLException {
		String query = "SELECT * FROM Awarded WHERE awardedAchievementId=?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, achievement.getId());

		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<Awarded> awardedList = new ArrayList<>();

		while (resultSet.next()) {
			AppUser appUser = AppUserDAO.getInstance().select(resultSet.getInt("awardedUserId"));
			awardedList.add(new Awarded(
					appUser,
					achievement,
					resultSet.getDate("awardedDate")
			));
		}

		return awardedList;
	}

	/**
	 * Find all awarded achievements in the database
	 *
	 * @return list of all awarded achievements
	 * @throws SQLException if the query fails
	 */
	public ArrayList<Awarded> findAll() throws SQLException {
		String query = "SELECT * FROM Awarded";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<Awarded> awardedList = new ArrayList<>();

		while (resultSet.next()) {
			AppUser appUser = AppUserDAO.getInstance().select(resultSet.getInt("awardedUserId"));
			Achievement achievement = AchievementDAO.getInstance().select(resultSet.getInt("awardedAchievementId"));
			awardedList.add(new Awarded(
					appUser,
					achievement,
					resultSet.getDate("awardedDate")
			));
		}

		return awardedList;
	}

	/**
	 * Check if an awarded achievement exists in the database
	 *
	 * @param awarded the awarded achievement to check
	 * @return true if the awarded achievement exists, false otherwise
	 */
	public boolean exists(Awarded awarded) {
		try {
			String query = "SELECT * FROM Awarded WHERE awardedUserId=? AND awardedAchievementId=?";

			PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
			preparedStatement.setInt(1, awarded.getAppUser().getId());
			preparedStatement.setInt(2, awarded.getAchievement().getId());

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete all achievements for a specific user
	 *
	 * @param appUser the user whose achievements should be deleted
	 * @throws SQLException if the query fails
	 */
	public void deleteAllForUser(AppUser appUser) throws SQLException {
		String query = "DELETE FROM Awarded WHERE awardedUserId=?";

		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, appUser.getId());

		preparedStatement.executeUpdate();
	}

	/**
	 * Checks if a user has been awarded a specific achievement.
	 *
	 * @param userId The ID of the user to check.
	 * @param achievementId The ID of the achievement to check.
	 * @return true if the user has the achievement, false otherwise.
	 * @throws SQLException if a database access error occurs.
	 */
	public boolean hasUserAchievement(int userId, int achievementId) {
		try {
			String query = "SELECT * FROM Awarded WHERE awardedUserId=? AND awardedAchievementId=?";

			PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, achievementId);

			ResultSet resultSet = preparedStatement.executeQuery();

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}