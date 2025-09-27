package defiut.backend.dao;

import defiut.backend.database.MySqlConnector;
import defiut.backend.model.Achievement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * AchievementDAO class
 * Singleton class that handles the access to the database for the Achievement class
 */
public class AchievementDAO {

    private static AchievementDAO instance;

    /**
     * Singleton constructor
     *
     * @return an instance of the AchievementDAO class
     */
    public static AchievementDAO getInstance() {
        if (instance == null) {
            instance = new AchievementDAO();
        }
        return instance;
    }

    /**
     * Select an achievement from the database by its ID
     *
     * @param id the id of the achievement
     * @return the achievement
     * @throws SQLException if the query fails
     */
    public Achievement select(int id) throws SQLException {
        String query = "SELECT * FROM Achievement WHERE achievementId=?";

        PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Achievement(resultSet.getInt("achievementId"), resultSet.getString("achievementTitle"), resultSet.getString("achievementDescription"), resultSet.getString("achievementColor"));
        }
        return null;
    }

    /**
     * Find all achievements in the database
     *
     * @return list of all achievements
     * @throws SQLException if the query fails
     */
    public ArrayList<Achievement> findAll() throws SQLException {
        String query = "SELECT * FROM Achievement";

        PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Achievement> achievements = new ArrayList<>();

        while (resultSet.next()) {
            achievements.add(new Achievement(resultSet.getInt("achievementId"), resultSet.getString("achievementTitle"), resultSet.getString("achievementDescription"), resultSet.getString("achievementColor")));
        }

        return achievements;
    }

    /**
     * Insert an achievement in the database
     *
     * @param achievement the achievement to insert
     * @throws SQLException if the query fails
     */
    public void insert(Achievement achievement) throws SQLException {
        String query = "INSERT INTO Achievement (title, description, color) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
        preparedStatement.setString(1, achievement.getTitle());
        preparedStatement.setString(2, achievement.getDescription());
        preparedStatement.setString(3, achievement.getColor());

        preparedStatement.executeUpdate();
    }

    /**
     * Update an achievement in the database
     *
     * @param achievement the achievement to update
     * @throws SQLException if the query fails
     */
    public void update(Achievement achievement) throws SQLException {
        String query = "UPDATE Achievement SET title=?, description=?, color=? WHERE id=?";

        PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
        preparedStatement.setString(1, achievement.getTitle());
        preparedStatement.setString(2, achievement.getDescription());
        preparedStatement.setString(3, achievement.getColor());
        preparedStatement.setInt(4, achievement.getId());

        preparedStatement.executeUpdate();
    }

    /**
     * Delete an achievement from the database
     *
     * @param achievement the achievement to delete
     * @throws SQLException if the query fails
     */
    public void delete(Achievement achievement) throws SQLException {
        String query = "DELETE FROM Achievement WHERE id=?";

        PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
        preparedStatement.setInt(1, achievement.getId());

        preparedStatement.executeUpdate();
    }

    /**
     * Check if an achievement exists in the database
     *
     * @param achievement the achievement to check
     * @return true if the achievement exists, false otherwise
     */
    public boolean exists(Achievement achievement) {
        try {
            String query = "SELECT * FROM Achievement WHERE id=?";

            PreparedStatement preparedStatement = MySqlConnector.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setInt(1, achievement.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}