package defiut.backend.model;

import defiut.backend.model.domain.ChallengeDifficulty;
import defiut.backend.model.domain.ChallengeLanguage;

/**
 * Challenge class
 * Represents a challenge that a player can complete
 */
public class Challenge {

	// Attributes
	private int id;
	private String name;
	private String date;
	private ChallengeDifficulty difficulty;
	private ChallengeLanguage language;
	private String description;
	private String flag;
	private int docker;
	private int points;

	// Constructors

	/**
	 * Default constructor
	 *
	 * @param name        the name of the challenge
	 * @param date        the date of the challenge
	 * @param difficulty  the difficulty of the challenge
	 * @param language    the language of the challenge
	 * @param description the description of the challenge
	 * @param flag        the flag of the challenge
	 * @param docker      the docker of the challenge
	 * @param points      the points of the challenge
	 */
	public Challenge(String name, String date, ChallengeDifficulty difficulty, ChallengeLanguage language,
	                 String description, String flag, int docker, int points) {
		this.setId(-1);
		this.setName(name);
		this.setDate(date);
		this.setDifficulty(difficulty);
		this.setLanguage(language);
		this.setDescription(description);
		this.setFlag(flag);
		this.setDocker(docker);
		this.setPoints(points);
	}

	/**
	 * Detailed constructor
	 *
	 * @param id          the id of the challenge
	 * @param name        the name of the challenge
	 * @param date        the date of the challenge
	 * @param difficulty  the difficulty of the challenge
	 * @param language    the language of the challenge
	 * @param description the description of the challenge
	 * @param flag        the flag of the challenge
	 * @param docker      the docker of the challenge
	 * @param points      the points of the challenge
	 */
	public Challenge(int id, String name, String date, ChallengeDifficulty difficulty, ChallengeLanguage language,
	                 String description, String flag, int docker, int points) {
		this.setId(id);
		this.setName(name);
		this.setDate(date);
		this.setDifficulty(difficulty);
		this.setLanguage(language);
		this.setDescription(description);
		this.setFlag(flag);
		this.setDocker(docker);
		this.setPoints(points);
	}

	// Getters

	/**
	 * Get the id of the challenge
	 *
	 * @return the id of the challenge
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the id of the challenge
	 *
	 * @param id the id of the challenge
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the name of the challenge
	 *
	 * @return the name of the challenge
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the challenge
	 *
	 * @param name the name of the challenge
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the date of the challenge
	 *
	 * @return the date of the challenge
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * Set the date of the challenge
	 *
	 * @param date the date of the challenge
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Get the difficulty of the challenge
	 *
	 * @return the difficulty of the challenge
	 */
	public ChallengeDifficulty getDifficulty() {
		return this.difficulty;
	}

	/**
	 * Set the difficulty of the challenge
	 *
	 * @param difficulty the difficulty of the challenge
	 */
	public void setDifficulty(ChallengeDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Get the language of the challenge
	 *
	 * @return the language of the challenge
	 */
	public ChallengeLanguage getLanguage() {
		return this.language;
	}

	// Setters

	/**
	 * Set the language of the challenge
	 *
	 * @param language the language of the challenge
	 */
	public void setLanguage(ChallengeLanguage language) {
		this.language = language;
	}

	/**
	 * Get the description of the challenge
	 *
	 * @return the description of the challenge
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Set the description of the challenge
	 *
	 * @param description the description of the challenge
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the flag of the challenge
	 *
	 * @return the flag of the challenge
	 */
	public String getFlag() {
		return this.flag;
	}

	/**
	 * Set the flag of the challenge
	 *
	 * @param flag the flag of the challenge
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * Get the docker of the challenge
	 *
	 * @return the docker of the challenge
	 */
	public int getDocker() {
		return this.docker;
	}

	/**
	 * Set the docker of the challenge
	 *
	 * @param docker the docker of the challenge
	 */
	public void setDocker(int docker) {
		this.docker = docker;
	}

	/**
	 * Get the points of the challenge
	 *
	 * @return the points of the challenge
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Set the points of the challenge
	 *
	 * @param points the points of the challenge
	 */
	public void setPoints(int points) {
		this.points = points;
	}
}
