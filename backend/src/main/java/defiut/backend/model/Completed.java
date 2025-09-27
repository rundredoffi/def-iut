package defiut.backend.model;

/**
 * Completed class
 * Represents a completed challenge by a player
 */
public class Completed {

	// Attributes
	private int completedUser;
	private int completedChallenge;
	private String completedStatus;
	private String completedDate;

	// Constructor

	/**
	 * Default constructor
	 *
	 * @param completedUser      the user who completed the challenge
	 * @param completedChallenge the challenge completed
	 * @param completedStatus    the status of the challenge
	 * @param completedDate      the date of the challenge
	 */
	public Completed(int completedUser, int completedChallenge, String completedStatus, String completedDate) {
		this.setCompletedUser(completedUser);
		this.setCompletedChallenge(completedChallenge);
		this.setCompletedStatus(completedStatus);
		this.setCompletedDate(completedDate);
	}

	// Getters

	/**
	 * Get the user who completed the challenge
	 *
	 * @return the user who completed the challenge
	 */
	public int getCompletedUser() {
		return this.completedUser;
	}

	/**
	 * Set the user who completed the challenge
	 *
	 * @param completedUser the user who completed the challenge
	 */
	public void setCompletedUser(int completedUser) {
		this.completedUser = completedUser;
	}

	/**
	 * Get the challenge completed
	 *
	 * @return the challenge completed
	 */
	public int getCompletedChallenge() {
		return this.completedChallenge;
	}

	/**
	 * Set the challenge completed
	 *
	 * @param completedChallenge the challenge completed
	 */
	public void setCompletedChallenge(int completedChallenge) {
		this.completedChallenge = completedChallenge;
	}

	// Setters

	/**
	 * Get the status of the challenge
	 *
	 * @return the status of the challenge
	 */
	public String getCompletedStatus() {
		return this.completedStatus;
	}

	/**
	 * Set the status of the challenge
	 *
	 * @param completedStatus the status of the challenge
	 */
	public void setCompletedStatus(String completedStatus) {
		this.completedStatus = completedStatus;
	}

	/**
	 * Get the date of the challenge
	 *
	 * @return the date of the challenge
	 */
	public String getCompletedDate() {
		return this.completedDate;
	}

	/**
	 * Set the date of the challenge
	 *
	 * @param completedDate the date of the challenge
	 */
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
}
