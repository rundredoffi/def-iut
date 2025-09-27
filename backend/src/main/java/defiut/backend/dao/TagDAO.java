package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * TagDAO class
 * Singleton class that handles the access to the database for the Tag class
 */
public class TagDAO {

	private static TagDAO instance;

	/**
	 * Singleton constructor
	 *
	 * @return an instance of the TagDAO class
	 */
	public static TagDAO getInstance() {
		if (instance == null) {
			instance = new TagDAO();
		}
		return instance;
	}

	/**
	 * Select all the tags from the database
	 *
	 * @return an ArrayList of all the tags
	 * @throws SQLException if the querry fails
	 */
	public ArrayList<Tag> findAll() throws SQLException {
		String querry = "SELECT * FROM Tag";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);

		ResultSet resultSet = preparedStatement.executeQuery();
		ArrayList<Tag> tags = new ArrayList<Tag>();
		while (resultSet.next()) {
			int id = resultSet.getInt("tagId");
			String name = resultSet.getString("tagName");
			String color = resultSet.getString("tagColor");
			tags.add(new Tag(id, name, color));
		}
		return tags;
	}

	/**
	 * Retrieves a Tag object from the database based on its ID.
	 *
	 * @param tagId The ID of the tag to retrieve.
	 * @return A Tag object if a tag with the specified ID exists, or null if no such tag is found.
	 * @throws SQLException If a database access error occurs or the query fails.
	 */
	public Tag findById(int tagId) throws SQLException {
		String querry = "SELECT * FROM Tag WHERE tagId = ?";
		PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(querry);
		preparedStatement.setInt(1, tagId);

		ResultSet resultSet = preparedStatement.executeQuery();
		Tag tag = null;
		if (resultSet.next()) {
			int id = resultSet.getInt("tagId");
			String name = resultSet.getString("tagName");
			String color = resultSet.getString("tagColor");
			tag = new Tag(id, name, color);
		}
		return tag;
	}
}