package defiut.backend.controller;

import defiut.backend.dao.*;
import defiut.backend.model.*;
import defiut.backend.model.domain.UserRole;
import defiut.backend.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * REST controller managing user operations
 */
@RestController
@RequestMapping("/api")
public class UserController {

	private final AuthenticationController authenticationController = new AuthenticationController();

	/**
	 * Get scores for all users and assign badges to top 3
	 *
	 * @return List of users with their scores
	 */
	@GetMapping("/users/score")
	public ResponseEntity<String> getUsersScore() {
		try {
			ArrayList<AppUser> users = AppUserDAO.getInstance().findAll();
			JSONObject res = new JSONObject();
			JSONArray usersJson = new JSONArray();

			for (AppUser user : users) {
				JSONObject userJson = new JSONObject();
				userJson.put("name", user.getNickname());
				userJson.put("score", user.getScore());
				usersJson.put(userJson);
			}

			users.sort((u1, u2) -> u2.getScore() - u1.getScore());

			if (users.size() >= 3) {
				for (int i = 0; i < 3; i++) {
					AppUser user = users.get(i);
					Granted granted = new Granted(user.getId(), i + 1, java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					if (!GrantedDAO.getInstance().exists(granted)) {
						LogUtil.writeLog("User " + user.getEmail() + " granted rank " + (i + 1) + " badge");
						GrantedDAO.getInstance().insert(granted);
					}
				}
			}

			res.put("users", usersJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Register a new user
	 *
	 * @param userJson JSON containing user registration data
	 * @return Success message or error status
	 */
	@PostMapping("/users")
	public ResponseEntity<String> addUser(@RequestBody String userJson) {
		try {
			JSONObject userObject = new JSONObject(userJson);
			if (AppUserDAO.getInstance().emailExists(userObject.getString("email"))) {
				return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
			}
			if (AppUserDAO.getInstance().nicknameExists(userObject.getString("name"))) {
				return new ResponseEntity<>("Nickname already exists", HttpStatus.BAD_REQUEST);
			}
			if (userObject.getString("name").trim().length() == 0) {
				return new ResponseEntity<>("Nickname cannot be empty", HttpStatus.BAD_REQUEST);
			} else {
				AppUser user = new AppUser(
						userObject.getString("name").trim(),
						userObject.getString("email"),
						this.authenticationController.hashPassword(userObject.getString("password")),
						UserRole.USER,
						0
				);
				AppUserDAO.getInstance().insert(user);
				return new ResponseEntity<>("User added", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update user profile information
	 *
	 * @param userJson JSON containing updated user data and authentication token
	 * @return Success message or error status
	 */
	@PostMapping("/user/update")
	public ResponseEntity<String> updateUser(@RequestBody String userJson) {
		try {
			String token = new JSONObject(userJson).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			int idUser = user.getId();
			JSONObject userObject = new JSONObject(userJson);
			AppUser userToUpdate = AppUserDAO.getInstance().select(idUser);
			String oldEmail = userToUpdate.getEmail();

			userToUpdate.setNickname(userObject.getString("name"));
			userToUpdate.setEmail(userObject.getString("email"));

			AppUserDAO.getInstance().update(userToUpdate);
			this.authenticationController.editUser(token, userToUpdate);

			LogUtil.writeLog("User " + oldEmail + " updated his profile : " + userToUpdate.getEmail() + " - " + userToUpdate.getNickname());
			return new ResponseEntity<>("User updated", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete user account and all associated data
	 *
	 * @param userJson JSON containing authentication token
	 * @return Success message or error status
	 */
	@PostMapping("/user/delete")
	public ResponseEntity<String> deleteUser(@RequestBody String userJson) {
		try {
			String token = new JSONObject(userJson).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			AwardedDAO.getInstance().deleteAllForUser(user);
			ArrayList<Completed> completedList = CompletedDAO.getInstance().select(user.getId());
			for (Completed completed : completedList) CompletedDAO.getInstance().delete(completed);
			ArrayList<Granted> grantedList = GrantedDAO.getInstance().select(user.getId());
			for (Granted granted : grantedList) GrantedDAO.getInstance().delete(granted);
			AppUserDAO.getInstance().delete(user);

			LogUtil.writeLog("User " + user.getEmail() + " deleted his account");
			return new ResponseEntity<>("User deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Authenticate user and get access token
	 *
	 * @param userJson JSON containing login credentials
	 * @return Access token and user info or error message
	 */
	@PostMapping("/users/login")
	public ResponseEntity<String> login(@RequestBody String userJson) {
		try {
			JSONObject userObject = new JSONObject(userJson);
			try {
				AppUser user = authenticationController.authenticate(userObject.getString("email"), userObject.getString("password"));
				String token = this.authenticationController.userToToken(user);
				JSONObject res = new JSONObject();
				res.put("token", token);
				res.put("id", user.getId());
				res.put("role", user.getRole().toString());
				res.put("email", user.getEmail());
				LogUtil.writeLog("User " + userObject.getString("email") + " logged in");
                this.addBadgeOsToUser(user);
				return new ResponseEntity<>(res.toString(), HttpStatus.OK);
			} catch (Exception e) {
				LogUtil.writeLog("User " + userObject.getString("email") + " failed to log in");
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * End user session
	 *
	 * @param userJson JSON containing authentication token
	 * @return Success message or error status
	 */
	@PostMapping("/users/logout")
	public ResponseEntity<String> logout(@RequestBody String userJson) {
		try {
			JSONObject userObject = new JSONObject(userJson);
			String token = userObject.getString("token");
			if (!authenticationController.isAuthenticated(token)) {
				return new ResponseEntity<>("User not logged in", HttpStatus.BAD_REQUEST);
			} else {
				AppUser user = this.authenticationController.tokenToUser(token);
				authenticationController.logout(token);
				LogUtil.writeLog("User " + user.getEmail() + " logged out");
				return new ResponseEntity<>("User logged out", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get user information by token
	 *
	 * @param JSONToken JSON containing authentication token
	 * @return User details and awards or error message
	 */
	@PostMapping("/user")
	public ResponseEntity<String> getUserByToken(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			ArrayList<Awarded> awardedList = AwardedDAO.getInstance().selectByUser(user);

			JSONObject res = new JSONObject();
			res.put("id", user.getId());
			res.put("name", user.getNickname());
			res.put("email", user.getEmail());
			res.put("role", user.getRole().toString());
			res.put("score", user.getScore());
			JSONArray awardsJson = new JSONArray();
			awardedList.forEach(awarded -> {
				JSONObject awardObj = new JSONObject();
				awardObj.put("title", awarded.getAchievement().getTitle());
				awardObj.put("description", awarded.getAchievement().getDescription());
				awardObj.put("color", awarded.getAchievement().getColor());
				awardObj.put("date", awarded.getDate().toString());
				awardsJson.put(awardObj);
			});
			res.put("awards", awardsJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get user's points/score
	 *
	 * @param JSONToken JSON containing authentication token
	 * @return User's score or error message
	 */
	@PostMapping("/users/points")
	public ResponseEntity<String> getPointsByUser(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			JSONObject res = new JSONObject();
			res.put("points", user.getScore());
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get user's earned badges
	 *
	 * @param JSONToken JSON containing authentication token
	 * @return List of user's badges or error message
	 */
	@PostMapping("/users/badges")
	public ResponseEntity<String> getBadgesByUser(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			ArrayList<Granted> grantedBadges = GrantedDAO.getInstance().select(user.getId());
			JSONObject res = new JSONObject();
			JSONArray badgesJson = new JSONArray();

			for (Granted granted : grantedBadges) {
				JSONObject badgeJson = new JSONObject();
				badgeJson.put("rank", granted.getGrantedBadgeRank());
				badgeJson.put("date", granted.getGrantedDate());
				badgesJson.put(badgeJson);
			}

			res.put("badges", badgesJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    /**
     * Adds the Linus Torval badge to a user who logs in with a Linux OS
     *
     * @param appUser User who should receive the badge
     */
    private void addBadgeOsToUser(AppUser appUser) throws SQLException {
        String osName = System.getProperty("os.name").toLowerCase();
        ArrayList<Awarded> listAwards = AwardedDAO.getInstance().selectByUser(appUser);
        Achievement achievement = AchievementDAO.getInstance().select(6);
        boolean alreadyAwarded = listAwards.stream().anyMatch(a -> a.getAchievement().getId() == achievement.getId());
        if ((osName.contains("nix") || osName.contains("nux")) && !alreadyAwarded) {
            Awarded award = new Awarded(appUser, achievement, new Date());
            AwardedDAO.getInstance().insert(award);
            LogUtil.writeLog("User " + appUser.getEmail() + " has earned the badge " + achievement.getTitle());
        }
    }
}