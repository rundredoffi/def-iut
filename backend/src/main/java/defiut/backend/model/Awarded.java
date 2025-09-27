package defiut.backend.model;

import java.util.Date;

/**
 * Awarded class
 * Represents an award that a player can earn
 */
public class Awarded {
    private AppUser appUser;
    private Achievement achievement;
    private Date date;
    /**
     * Default constructor
     *
     * @param appUser           the user of the award
     * @param achievement       the achievement of the award
     * @param date              the date of the award
     */
    public Awarded(AppUser appUser, Achievement achievement, Date date) {
        this.setAppUser(appUser);
        this.setAchievement(achievement);
        this.setDate(date);
    }

    /**
     * Get the user of the award
     *
     * @return the user of the award
     */
    public AppUser getAppUser() {
        return this.appUser;
    }

    /**
     * Set the user of the award
     *
     * @param user the id of the award
     */
    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    /**
     * Get the achievement of the award
     *
     * @return the achievement of the award
     */
    public Achievement getAchievement() {
        return this.achievement;
    }

    /**
     * Set the achievement of the award
     *
     * @param achievement the achievement of the award
     */
    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    /**
     * Get the date of the award
     *
     * @return the date of the award
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Set the date of the award
     *
     * @param date the date of the award
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
