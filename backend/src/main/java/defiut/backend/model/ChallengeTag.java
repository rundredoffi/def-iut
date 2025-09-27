package defiut.backend.model;

/**
 * ChallengeTag class
 * Represents a link between a challenge and a tag
 */
public class ChallengeTag {

	// Attributes
	private int challenge;
	private int tag;

	// Constructor

	/**
	 * Default constructor
	 *
	 * @param challenge the challenge
	 * @param tag       the tag
	 */
	public ChallengeTag(int challenge, int tag) {
		this.setChallenge(challenge);
		this.setTag(tag);
	}

	// Getters

	/**
	 * Get the challenge
	 *
	 * @return the challenge
	 */
	public int getChallenge() {
		return this.challenge;
	}

	/**
	 * Set the challenge
	 *
	 * @param challenge the challenge
	 */
	public void setChallenge(int challenge) {
		this.challenge = challenge;
	}

	// Setters

	/**
	 * Get the tag
	 *
	 * @return the tag
	 */
	public int getTag() {
		return this.tag;
	}

	/**
	 * Set the tag
	 *
	 * @param tag the tag
	 */
	public void setTag(int tag) {
		this.tag = tag;
	}
}
