package defiut.backend.model;

/**
 * Granted class
 * Represents badge granted to a player
 */
public class Granted {

	// Attributes
	private int grantedUser;
	private int grantedBadgeRank;
	private String grantedDate;

	// Constructor

	/**
	 * Default constructor
	 *
	 * @param grantedUser      the user who granted the badge
	 * @param grantedBadgeRank the badge rank granted
	 * @param grantedDate      the date of the badge granted
	 */
	public Granted(int grantedUser, int grantedBadgeRank, String grantedDate) {
		this.setGrantedUser(grantedUser);
		this.setGrantedBadgeRank(grantedBadgeRank);
		this.setGrantedDate(grantedDate);
	}

	// Getters

	/**
	 * Get the user who granted the badge
	 *
	 * @return the user who granted the badge
	 */
	public int getGrantedUser() {
		return this.grantedUser;
	}

	/**
	 * Set the user who granted the badge
	 *
	 * @param grantedUser the user who granted the badge
	 */
	public void setGrantedUser(int grantedUser) {
		this.grantedUser = grantedUser;
	}

	/**
	 * Get the badge rank granted
	 *
	 * @return the badge rank granted
	 */
	public int getGrantedBadgeRank() {
		return this.grantedBadgeRank;
	}

	// Setters

	/**
	 * Set the badge rank granted
	 *
	 * @param grantedBadgeRank the badge rank granted
	 */
	public void setGrantedBadgeRank(int grantedBadgeRank) {
		this.grantedBadgeRank = grantedBadgeRank;
	}

	/**
	 * Get the date of the badge granted
	 *
	 * @return the date of the badge granted
	 */
	public String getGrantedDate() {
		return this.grantedDate;
	}

	/**
	 * Set the date of the badge granted
	 *
	 * @param grantedDate the date of the badge granted
	 */
	public void setGrantedDate(String grantedDate) {
		this.grantedDate = grantedDate;
	}
}
