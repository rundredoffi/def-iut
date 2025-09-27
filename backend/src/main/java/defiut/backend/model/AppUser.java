package defiut.backend.model;

import defiut.backend.model.domain.UserRole;

import java.io.Serializable;

/**
 * AppUser class
 * Represents a user of the application either an administrator or a player
 */
public class AppUser implements Serializable {

	// Attributes
	private int id;
	private String nickname;
	private String email;
	private String password;
	private UserRole role;
	private int score;

	// Constructors

	/**
	 * Default constructor
	 *
	 * @param nickname the nickname of the user
	 * @param email    the email of the user
	 * @param password the password of the user
	 * @param role     the role of the user
	 * @param score    the score of the user
	 */
	public AppUser(String nickname, String email, String password, UserRole role, int score) {
		this.setId(-1);
		this.setNickname(nickname);
		this.setEmail(email);
		this.setPassword(password);
		this.setRole(role);
		this.setScore(score);
	}

	/**
	 * Detailed constructor
	 *
	 * @param nickname the nickname of the user
	 * @param email    the email of the user
	 * @param password the password of the user
	 * @param role     the role of the user
	 * @param score    the score of the user
	 */
	public AppUser(int id, String nickname, String email, String password, UserRole role, int score) {
		this.setId(id);
		this.setNickname(nickname);
		this.setEmail(email);
		this.setPassword(password);
		this.setRole(role);
		this.setScore(score);
	}

	// Getters

	/**
	 * Get the id of the user
	 *
	 * @return the id of the user
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the id of the user
	 *
	 * @param id the id of the user
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the nickname of the user
	 *
	 * @return the nickname of the user
	 */
	public String getNickname() {
		return this.nickname;
	}

	/**
	 * Set the nickname of the user
	 *
	 * @param nickname the nickname of the user
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Get the email of the user
	 *
	 * @return the email of the user
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Set the email of the user
	 *
	 * @param email the email of the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	// Setters

	/**
	 * Get the password of the user
	 *
	 * @return the password of the user
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Set the password of the user
	 *
	 * @param password the password of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the role of the user
	 *
	 * @return the role of the user
	 */
	public UserRole getRole() {
		return this.role;
	}

	/**
	 * Set the role of the user
	 *
	 * @param role the role of the user
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * Get the score of the user
	 *
	 * @return the score of the user
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Set the score of the user
	 *
	 * @param score the score of the user
	 */
	public void setScore(int score) {
		this.score = score;
	}

	// Methods

	/**
	 * Get a printable representation of the user
	 *
	 * @return a string representing the user
	 */
	public String toString() {
		return "User [id=" + this.id + ", nickname=" + this.nickname + ", email=" + this.email + ", password="
				+ this.password + ", role=" + this.role + ", score=" + this.score + "]";
	}
}