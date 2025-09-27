package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.Challenge;
import defiut.backend.model.domain.ChallengeDifficulty;
import defiut.backend.model.domain.ChallengeLanguage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ChallengeDAO class
 * Singleton class that handles the access to the database for the Challenge
 * class
 */
public class ChallengeDAO {

	private static ChallengeDAO instance;

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the ChallengeDAO class
	 */
	public static ChallengeDAO getInstance() {
		if (instance == null) {
			instance = new ChallengeDAO();
		}
		return instance;
	}

	/**
	 * Insert a challenge in the database
	 *
	 * @param challenge the challenge to insert
	 * @throws SQLException if the querry fails
	 */
	public void insert(Challenge challenge) throws SQLException {
		String querry = "INSERT INTO Challenge (challengeName, challengeDate, challengeDifficulty, challengeLanguage, challengeBadge, challengeDescription, challengeFlag) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = MySqlConnector.getInstance().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(querry);

		preparedStatement.setString(1, challenge.getName());
		preparedStatement.setString(2, challenge.getDate());
		preparedStatement.setString(3, challenge.getDifficulty().toString());
		preparedStatement.setString(4, challenge.getLanguage().toString());
		preparedStatement.setString(6, challenge.getDescription());
		preparedStatement.setString(7, challenge.getFlag());

		preparedStatement.executeUpdate();

		challenge.setId(MySqlConnector.getInstance().getLastInsertedId(connection));
	}

	/**
	 * Update a challenge in the database
	 *
	 * @param challenge the challenge to update
	 * @throws SQLException if the querry fails
	 */
	public void update(Challenge challenge) throws SQLException {
		String querry = "UPDATE Challenge SET challengeName=?, challengeDate=?, challengeDifficulty=?, challengeLanguage=?, challengeBadge=?, challengeDescription=?, challengeFlag=? WHERE challengeId=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setString(1, challenge.getName());
		preparedStatement.setString(2, challenge.getDate());
		preparedStatement.setString(3, challenge.getDifficulty().toString());
		preparedStatement.setString(4, challenge.getLanguage().toString());
		preparedStatement.setInt(6, challenge.getId());
		preparedStatement.setString(7, challenge.getDescription());
		preparedStatement.setString(8, challenge.getFlag());

		preparedStatement.executeUpdate();
	}

	/**
	 * Delete a challenge in the database
	 *
	 * @param challenge the challenge to delete
	 * @throws SQLException if the querry fails
	 */
	public void delete(Challenge challenge) throws SQLException {
		String querry = "DELETE FROM Challenge WHERE challengeId=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setInt(1, challenge.getId());

		preparedStatement.executeUpdate();
	}

	/**
	 * Select a challenge in the database
	 *
	 * @param id the id of the challenge to select
	 * @return the challenge selected
	 * @throws SQLException if the querry fails
	 */
	public Challenge select(int id) throws SQLException {
		String querry = "SELECT * FROM Challenge WHERE challengeId=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setInt(1, id);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			return new Challenge(
					resultSet.getInt("challengeId"),
					resultSet.getString("challengeName"),
					resultSet.getString("challengeDate"),
					ChallengeDifficulty.valueOf(resultSet.getString("challengeDifficulty")),
					ChallengeLanguage.valueOf(resultSet.getString("challengeLanguage")),
					resultSet.getString("challengeDescription"),
					resultSet.getString("challengeFlag"),
					resultSet.getInt("challengeDocker"),
					resultSet.getInt("challengePoints"));
		}

		return null;
	}

	/**
	 * Select all challenges in the database
	 *
	 * @return all challenges in the database
	 * @throws SQLException if the querry fails
	 */
	public ArrayList<Challenge> findAll() throws SQLException {
		String querry = "SELECT * FROM Challenge";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<Challenge> challenges = new ArrayList<>();
		while (resultSet.next()) {
			challenges.add(new Challenge(
					resultSet.getInt("challengeId"),
					resultSet.getString("challengeName"),
					resultSet.getString("challengeDate"),
					ChallengeDifficulty.valueOf(resultSet.getString("challengeDifficulty")),
					ChallengeLanguage.valueOf(resultSet.getString("challengeLanguage")),
					resultSet.getString("challengeDescription"),
					resultSet.getString("challengeFlag"),
					resultSet.getInt("challengeDocker"),
					resultSet.getInt("challengePoints")));
		}

		return challenges;
	}

	/**
	 * Check if a challenge exists in the database
	 *
	 * @param name the name of the challenge to check
	 * @return true if the challenge exists, false otherwise
	 * @throws SQLException if the querry fails
	 */
	public boolean exists(String name) throws SQLException {
		String querry = "SELECT * FROM Challenge WHERE challengeName=?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		preparedStatement.setString(1, name);

		ResultSet resultSet = preparedStatement.executeQuery();

		return resultSet.next();
	}
}
