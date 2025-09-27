package defiut.backend.model;

/**
 * Tag class
 * Represents a tag of a challenge
 */
public class Tag {

	// Attributes
	private int id;
	private String name;
	private String color;

	// Constructors

	/**
	 * Default constructor
	 *
	 * @param name  the name of the tag
	 * @param color the color of the tag
	 */
	public Tag(String name, String color) {
		this.setId(-1);
		this.setName(name);
		this.setColor(color);
	}

	/**
	 * Detailed constructor
	 *
	 * @param id    the id of the tag
	 * @param name  the name of the tag
	 * @param color the color of the tag
	 */
	public Tag(int id, String name, String color) {
		this.setId(id);
		this.setName(name);
		this.setColor(color);
	}

	// Getters

	/**
	 * Get the id of the tag
	 *
	 * @param id the id of the tag
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the id of the tag
	 *
	 * @param id the id of the tag
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the name of the tag
	 *
	 * @return the name of the tag
	 */
	public String getName() {
		return this.name;
	}

	// Setters

	/**
	 * Set the name of the tag
	 *
	 * @param name the name of the tag
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the color of the tag
	 *
	 * @return the color of the tag
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * Set the color of the tag
	 *
	 * @param color the color of the tag
	 */
	public void setColor(String color) {
		this.color = color;
	}

	// Methods

	/**
	 * Get a printable representation of the tag
	 *
	 * @return a string representing the tag
	 */
	public String toString() {
		return "Tag: " + this.getName() + " (" + this.getColor() + ")";
	}
}
