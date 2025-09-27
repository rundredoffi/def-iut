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

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * REST controller managing admin-only operations
 */
@RestController
@RequestMapping("/api")
public class AdminController {

	private final AuthenticationController authenticationController = new AuthenticationController();

	/**
	 * Checks if a user has admin role based on their authentication token
	 *
	 * @param token The authentication token
	 * @return true if user is admin, false otherwise
	 */
	private boolean isAdmin(String token) {
		AppUser user = this.authenticationController.tokenToUser(token);
		return user != null && user.getRole() == UserRole.ADMIN;
	}

	/**
	 * Get list of all users in the system
	 *
	 * @param JSONToken JSON containing authentication token
	 * @return List of users with their details or error message
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/admin/users")
	public ResponseEntity<String> getUsers(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser loggedUser = this.authenticationController.tokenToUser(token);
			if (loggedUser == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
			if (loggedUser.getRole() != UserRole.ADMIN)
				return new ResponseEntity<>("User is not an admin", HttpStatus.BAD_REQUEST);

			ArrayList<AppUser> users = AppUserDAO.getInstance().findAll();
			JSONObject res = new JSONObject();
			JSONArray usersJson = new JSONArray();

			for (AppUser user : users) {
				JSONObject userJson = new JSONObject();
				userJson.put("id", user.getId());
				userJson.put("name", user.getNickname());
				userJson.put("email", user.getEmail());
				userJson.put("role", user.getRole().toString());
				userJson.put("score", user.getScore());
				usersJson.put(userJson);
			}

			res.put("users", usersJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update user information as admin
	 *
	 * @param userJson JSON containing user data and authentication token
	 * @return Success message or error status
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/admin/user/update")
	public ResponseEntity<String> updateUserAdmin(@RequestBody String userJson) {
		try {
			String token = new JSONObject(userJson).getString("token");
			AppUser loggedUser = this.authenticationController.tokenToUser(token);
			if (loggedUser == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
			if (loggedUser.getRole() != UserRole.ADMIN)
				return new ResponseEntity<>("User is not an admin", HttpStatus.BAD_REQUEST);

			JSONObject userObject = new JSONObject(userJson);
			int idUser = userObject.getInt("id");
			AppUser userToUpdate = AppUserDAO.getInstance().select(idUser);
			String oldEmail = userToUpdate.getEmail();

			userToUpdate.setNickname(userObject.getString("name"));
			userToUpdate.setEmail(userObject.getString("email"));
			userToUpdate.setRole(UserRole.valueOf(userObject.getString("role")));
			userToUpdate.setScore(userObject.getInt("score"));

			AppUserDAO.getInstance().update(userToUpdate);

			LogUtil.writeLog("User " + oldEmail + " updated by admin : " + userToUpdate.getEmail() + " - " + userToUpdate.getNickname());
			return new ResponseEntity<>("User updated", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete a user and all associated data as admin
	 *
	 * @param userJson JSON containing user ID and authentication token
	 * @return Success message or error status
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/admin/user/delete")
	public ResponseEntity<String> deleteUserAdmin(@RequestBody String userJson) {
		try {
			String token = new JSONObject(userJson).getString("token");
			AppUser loggedUser = this.authenticationController.tokenToUser(token);
			if (loggedUser == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
			if (loggedUser.getRole() != UserRole.ADMIN)
				return new ResponseEntity<>("User is not an admin", HttpStatus.BAD_REQUEST);

			int idUser = new JSONObject(userJson).getInt("id");
			AppUser user = AppUserDAO.getInstance().select(idUser);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			ArrayList<Completed> completedList = CompletedDAO.getInstance().select(user.getId());
			for (Completed completed : completedList) CompletedDAO.getInstance().delete(completed);
			ArrayList<Granted> grantedList = GrantedDAO.getInstance().select(user.getId());
			for (Granted granted : grantedList) GrantedDAO.getInstance().delete(granted);
			AwardedDAO.getInstance().deleteAllForUser(user);
			if (AwardedDAO.getInstance().selectByUser(user).isEmpty()) {
				AppUserDAO.getInstance().delete(user);
			} else {
				return new ResponseEntity<>("Error during the deletion", HttpStatus.BAD_REQUEST);
			}
			LogUtil.writeLog("User " + user.getEmail() + " deleted by admin");
			return new ResponseEntity<>("User deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieve system logs
	 *
	 * @param JSONToken JSON containing authentication token
	 * @return List of log entries or error message
	 */
	@CrossOrigin(origins = "*")
	@PostMapping("/logs")
	public ResponseEntity<String> getLogs(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser loggedUser = this.authenticationController.tokenToUser(token);
			if (loggedUser == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
			if (loggedUser.getRole() != UserRole.ADMIN)
				return new ResponseEntity<>("User is not an admin", HttpStatus.BAD_REQUEST);

			JSONObject res = new JSONObject();
			JSONArray logsJson = new JSONArray();

			try (BufferedReader br = LogUtil.openReader()) {
				if (br != null) {
					String line;
					while ((line = br.readLine()) != null) {
						logsJson.put(line);
					}
				}
			}

			res.put("logs", logsJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}