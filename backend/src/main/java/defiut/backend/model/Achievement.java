package defiut.backend.model;

import java.io.Serializable;

/**
 * Achievement class
 * Represents a challenge that a player can earn
 */
public class Achievement implements Serializable {

    // Attributes
    private int id;
    private String title;
    private String description;
    private String color;

    // Constructor
    /**
     * Default constructor
     * 
     * @param id          the id of the achievement
     * @param title       the title of the achievement
     * @param description the description of the achievement
     * @param color       the color associated with the achievement
     */
    public Achievement(int id, String title, String description, String color) {
        this.setId(id);
        this.setTitle(title);
        this.setDescription(description);
        this.setColor(color);
    }

    // Getters
    /**
     * Get the id of the achievement
     * 
     * @return the id of the achievement
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the title of the achievement
     * 
     * @return the title of the achievement
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the description of the achievement
     * 
     * @return the description of the achievement
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the color of the achievement
     * 
     * @return the color of the achievement
     */
    public String getColor() {
        return this.color;
    }

    // Setters
    /**
     * Set the id of the achievement
     * 
     * @param id the id of the achievement
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the title of the achievement
     * 
     * @param title the title of the achievement
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the description of the achievement
     * 
     * @param description the description of the achievement
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the color of the achievement
     * 
     * @param color the color of the achievement
     */
    public void setColor(String color) {
        this.color = color;
    }

    // Methods
    /**
     * Get a printable representation of the achievement
     * 
     * @return a string representing the achievement
     */
    public String toString() {
        return "Achievement [id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", color="
                + this.color + "]";
    }
}