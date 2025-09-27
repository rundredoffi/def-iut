package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.ChallengeTag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ChallengeTagDAO class
 * Singleton class that handles the access to the database for the ChallengeTag
 * class
 */
public class ChallengeTagDAO {

	private static ChallengeTagDAO instance;

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the ChallengeTagDAO class
	 */
	public static ChallengeTagDAO getInstance() {
		if (instance == null) {
			instance = new ChallengeTagDAO();
		}
		return instance;
	}

	/**
	 * Select all the challenge-tag link from the database
	 *
	 * @return an ArrayList of all the challenge-tag
	 * @throws SQLException if the querry fails
	 */
	public ArrayList<ChallengeTag> findAll() throws SQLException {
		String querry = "SELECT * FROM ChallengeTag";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<ChallengeTag> challengeTags = new ArrayList<ChallengeTag>();
		while (resultSet.next()) {
			int challenge = resultSet.getInt("challengeTagChallenge");
			int tag = resultSet.getInt("challengeTagTag");
			challengeTags.add(new ChallengeTag(challenge, tag));
		}
		return challengeTags;
	}

	/**
	 * Find all challenge-tag links for a specific tag
	 *
	 * @param tagId the id of the tag to search for
	 * @return an ArrayList of all the challenge-tag links for the specified tag
	 * @throws SQLException if the query fails
	 */
	public ArrayList<ChallengeTag> findByTag(int tagId) throws SQLException {
		String query = "SELECT * FROM ChallengeTag WHERE challengeTagTag = ?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, tagId);

		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<ChallengeTag> challengeTags = new ArrayList<ChallengeTag>();
		while (resultSet.next()) {
			int challenge = resultSet.getInt("challengeTagChallenge");
			int tag = resultSet.getInt("challengeTagTag");
			challengeTags.add(new ChallengeTag(challenge, tag));
		}
		return challengeTags;
	}

	/**
	 * Find all challenge-tag links for a specific challenge
	 *
	 * @param challengeId the id of the tag to search for
	 * @return an ArrayList of all the challenge-tag links for the specified tag
	 * @throws SQLException if the query fails
	 */
	public ArrayList<ChallengeTag> findByChallenge(int challengeId) throws SQLException {
		String query = "SELECT * FROM ChallengeTag WHERE challengeTagChallenge = ?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
		preparedStatement.setInt(1, challengeId);

		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<ChallengeTag> challengeTags = new ArrayList<ChallengeTag>();
		while (resultSet.next()) {
			int challenge = resultSet.getInt("challengeTagChallenge");
			int tag = resultSet.getInt("challengeTagTag");
			challengeTags.add(new ChallengeTag(challenge, tag));
		}
		return challengeTags;
	}
}