package defiut.backend.controller;

import defiut.backend.dao.*;
import defiut.backend.model.*;
import defiut.backend.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * REST Controller managing challenge-related operations
 */
@RestController
@RequestMapping("/api")
public class ChallengeController {

	private final AuthenticationController authenticationController = new AuthenticationController();

	/**
	 * Get list of all challenges
	 *
	 * @return List of all challenges with basic info
	 */
	@GetMapping("/challenges")
	public ResponseEntity<String> getChallenges() {
		try {
			ArrayList<Challenge> challenges = ChallengeDAO.getInstance().findAll();
			JSONObject res = new JSONObject();
			JSONArray challengesJson = new JSONArray();

			for (Challenge challenge : challenges) {
				JSONObject challengeJson = new JSONObject();
				challengeJson.put("id", challenge.getId());
				challengeJson.put("name", challenge.getName());
				challengeJson.put("date", challenge.getDate());
				challengeJson.put("difficulty", challenge.getDifficulty().toString());
				challengeJson.put("language", challenge.getLanguage().toString());
				challengesJson.put(challengeJson);
			}

			res.put("challenges", challengesJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get list of challenges that a user has started but not completed
	 *
	 * @param JSONToken Authentication token
	 * @return List of started challenges with details
	 */
	@PostMapping("/challenges/started")
	public ResponseEntity<String> getStartedChallengesByUser(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			int idUser = user.getId();
			ArrayList<Completed> completedList = CompletedDAO.getInstance().select(idUser);
			ArrayList<Challenge> challenges = new ArrayList<>();

			for (Completed completed : completedList) {
				Challenge challenge = ChallengeDAO.getInstance().select(completed.getCompletedChallenge());
				if ("STARTED".equals(completed.getCompletedStatus())) challenges.add(challenge);
			}

			JSONObject res = new JSONObject();
			JSONArray challengesJson = new JSONArray();

			for (Challenge challenge : challenges) {
				JSONObject challengeJson = new JSONObject();
				challengeJson.put("id", challenge.getId());
				challengeJson.put("name", challenge.getName());
				challengeJson.put("date", challenge.getDate());
				challengeJson.put("difficulty", challenge.getDifficulty().toString());
				challengeJson.put("language", challenge.getLanguage().toString());
				challengeJson.put("description", challenge.getDescription());
				challengesJson.put(challengeJson);
			}

			res.put("challenges", challengesJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get list of challenges that a user has completed
	 *
	 * @param JSONToken Authentication token
	 * @return List of completed challenges with details
	 */
	@PostMapping("/challenges/completed")
	public ResponseEntity<String> getCompletedChallengesByUser(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			int idUser = user.getId();
			ArrayList<Completed> completedList = CompletedDAO.getInstance().select(idUser);
			ArrayList<Challenge> challenges = new ArrayList<>();

			for (Completed completed : completedList) {
				Challenge challenge = ChallengeDAO.getInstance().select(completed.getCompletedChallenge());
				if ("COMPLETED".equals(completed.getCompletedStatus())) challenges.add(challenge);
			}

			JSONObject res = new JSONObject();
			JSONArray challengesJson = new JSONArray();

			for (Challenge challenge : challenges) {
				JSONObject challengeJson = new JSONObject();
				challengeJson.put("id", challenge.getId());
				challengeJson.put("name", challenge.getName());
				challengeJson.put("date", challenge.getDate());
				challengeJson.put("difficulty", challenge.getDifficulty().toString());
				challengeJson.put("language", challenge.getLanguage().toString());
				challengeJson.put("description", challenge.getDescription());
				challengesJson.put(challengeJson);
			}

			res.put("challenges", challengesJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get list of all challenges with their completion status for a user
	 *
	 * @param JSONToken Authentication token
	 * @return List of all challenges with status and details
	 */
	@PostMapping("/challenges/list")
	public ResponseEntity<String> getChallengesByUser(@RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			int idUser = user.getId();
			ArrayList<Completed> completedList = CompletedDAO.getInstance().select(idUser);
			ArrayList<Challenge> challenges = ChallengeDAO.getInstance().findAll();

			JSONObject res = new JSONObject();
			JSONArray challengesJson = new JSONArray();

			for (Challenge challenge : challenges) {
				String status = "NOT STARTED";
				for (Completed completed : completedList) {
					if (completed.getCompletedChallenge() == challenge.getId()) {
						status = completed.getCompletedStatus();
					}
				}
				JSONObject challengeJson = new JSONObject();
				challengeJson.put("status", status);
				challengeJson.put("id", challenge.getId());
				challengeJson.put("name", challenge.getName());
				challengeJson.put("date", challenge.getDate());
				challengeJson.put("difficulty", challenge.getDifficulty().toString());
				challengeJson.put("language", challenge.getLanguage().toString());
				challengeJson.put("description", challenge.getDescription());
				challengesJson.put(challengeJson);
			}

			res.put("challenges", challengesJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get details of a specific challenge by ID
	 *
	 * @param id        Challenge ID
	 * @param JSONToken Authentication token
	 * @return Challenge details including user's completion status
	 */
	@PostMapping("/challenges/{id}")
	public ResponseEntity<String> getChallengeById(@PathVariable int id, @RequestBody String JSONToken) {
		try {
			String token = new JSONObject(JSONToken).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			Challenge challenge = ChallengeDAO.getInstance().select(id);
			if (challenge == null) return new ResponseEntity<>("Challenge not found", HttpStatus.BAD_REQUEST);

			int idUser = user.getId();
			ArrayList<Completed> completedList = CompletedDAO.getInstance().select(idUser);
			String status = "NOT STARTED";
			for (Completed completed : completedList) {
				if (completed.getCompletedChallenge() == challenge.getId()) {
					status = completed.getCompletedStatus();
				}
			}

			JSONObject res = new JSONObject();
			res.put("id", challenge.getId());
			res.put("name", challenge.getName());
			res.put("date", challenge.getDate());
			res.put("difficulty", challenge.getDifficulty().toString());
			res.put("language", challenge.getLanguage().toString());
			res.put("description", challenge.getDescription());
			res.put("points", challenge.getPoints());
			res.put("status", status);

			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Verify a submitted flag for a challenge
	 *
	 * @param idChallenge Challenge ID
	 * @param JSON        Contains token and submitted flag
	 * @return Result of verification and updated user status
	 */
	@PostMapping("/challenges/{id}/flag")
	public ResponseEntity<String> verifyFlag(@PathVariable("id") int idChallenge, @RequestBody String JSON) {
		try {
			String token = new JSONObject(JSON).getString("token");
			AppUser user = this.authenticationController.tokenToUser(token);
			if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);

			int idUser = user.getId();

			JSONObject res = new JSONObject();
			String flag = new JSONObject(JSON).getString("flag");

			Challenge challenge = ChallengeDAO.getInstance().select(idChallenge);
			if (challenge == null) return new ResponseEntity<>("Challenge not found", HttpStatus.BAD_REQUEST);

			String result = "WRONG";
			String status = "STARTED";
			if (challenge.getFlag().equals(flag.trim())) {
				result = "OK";
				status = "COMPLETED";
				this.addBadgeToUser(idUser, challenge);
			}
			res.put("result", result);
			boolean updated = false;

			Completed completed = new Completed(idUser, idChallenge, status,
					LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			if (CompletedDAO.getInstance().exists(completed)) {
				ArrayList<Completed> completedList = CompletedDAO.getInstance().select(idUser);
				for (Completed c : completedList) {
					if (c.getCompletedChallenge() == idChallenge) {
						if (!"COMPLETED".equals(c.getCompletedStatus())) {
							CompletedDAO.getInstance().update(completed);
							updated = true;
						}
					}
				}
			} else {
				CompletedDAO.getInstance().insert(completed);
				updated = true;
			}

			if (updated && "OK".equals(result)) {
				user.setScore(user.getScore() + challenge.getPoints());
				AppUserDAO.getInstance().update(user);
				this.authenticationController.editUser(token, user);
			}

			if ("OK".equals(result)) {
				LogUtil.writeLog("User " + user.getEmail() + " completed challenge " + challenge.getName() + " with flag " + flag);
			} else {
				LogUtil.writeLog("User " + user.getEmail() + " failed to complete challenge " + challenge.getName() + " with flag " + flag);
			}
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get tags associated with a specific challenge
	 *
	 * @param id Challenge ID
	 * @return List of tags with their details
	 */
	@GetMapping("/challenges/{id}/tags")
	public ResponseEntity<String> getTagsByChallenge(@PathVariable int id) {
		try {
			ArrayList<ChallengeTag> challengeTags = ChallengeTagDAO.getInstance().findAll();
			ArrayList<Tag> tags = TagDAO.getInstance().findAll();
			ArrayList<Tag> challengeTagsList = new ArrayList<>();

			for (ChallengeTag challengeTag : challengeTags) {
				if (challengeTag.getChallenge() == id) {
					for (Tag tag : tags) {
						if (tag.getId() == challengeTag.getTag()) {
							challengeTagsList.add(tag);
						}
					}
				}
			}

			JSONObject res = new JSONObject();
			JSONArray tagsJson = new JSONArray();

			for (Tag tag : challengeTagsList) {
				JSONObject tagJson = new JSONObject();
				tagJson.put("id", tag.getId());
				tagJson.put("name", tag.getName());
				tagJson.put("color", tag.getColor());
				tagsJson.put(tagJson);
			}

			res.put("tags", tagsJson);
			return new ResponseEntity<>(res.toString(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Add badges to user based on challenge completion
	 *
	 * @param idUser    User ID
	 * @param challenge Completed challenge
	 */
	private void addBadgeToUser(int idUser, Challenge challenge) {
		try {
			AchievementDAO achievementDAO = AchievementDAO.getInstance();
			AwardedDAO awardedDAO = AwardedDAO.getInstance();
			ArrayList<Achievement> achievements = achievementDAO.findAll();
			for (Achievement achievement : achievements) {
				if (!awardedDAO.hasUserAchievement(idUser, achievement.getId())) {
					this.checkUnlockedBadges(achievement.getId(), idUser, challenge);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check and award badges based on achievement criteria
	 *
	 * @param achievementId Achievement ID to check
	 * @param idUser        User ID
	 * @param challenge     Completed challenge
	 */
	private void checkUnlockedBadges(int achievementId, int idUser, Challenge challenge) {
		try {
			CompletedDAO completedDAO = CompletedDAO.getInstance();
			ArrayList<Completed> completedList = completedDAO.select(idUser);

			AppUser user = AppUserDAO.getInstance().select(idUser);
			Achievement achievement = AchievementDAO.getInstance().select(achievementId);

			switch (achievementId) {
				case 1:
					ChallengeTagDAO challengeTagDAO = ChallengeTagDAO.getInstance();
					ArrayList<ChallengeTag> challengeTags = challengeTagDAO.findByChallenge(challenge.getId());
					for (ChallengeTag challengeTag : challengeTags) {
						Tag tag = TagDAO.getInstance().findById(challengeTag.getTag());
						if (tag.getName().trim().toLowerCase().startsWith("crypto")) {
							Awarded awarded = new Awarded(user, achievement, new Date());
							if (!AwardedDAO.getInstance().exists(awarded)) {
								AwardedDAO.getInstance().insert(awarded);
								LogUtil.writeLog("User " + idUser + " has earned the badge " + awarded.getAchievement().getTitle());
							}
						}
					}
					break;
				case 2:
					for (Completed completed : completedList) {
						if ("STARTED".equals(completed.getCompletedStatus()) && completed.getCompletedChallenge() == challenge.getId()) {
							Awarded awarded = new Awarded(user, achievement, new Date());
							if (!AwardedDAO.getInstance().exists(awarded)) {
								AwardedDAO.getInstance().insert(awarded);
								LogUtil.writeLog("User " + idUser + " has earned the badge " + awarded.getAchievement().getTitle());
								break;
							}
						}
					}
					break;
				case 3:
					if (!completedDAO.challengeIsCompleted(challenge.getId())) {
						Awarded awarded = new Awarded(user, achievement, new Date());
						if (!AwardedDAO.getInstance().exists(awarded)) {
							AwardedDAO.getInstance().insert(awarded);
							LogUtil.writeLog("User " + idUser + " has earned the badge " + awarded.getAchievement().getTitle());
						}
					}
					break;
				case 4:
					boolean hasTwoIn24h = false;
					for (Completed c1 : completedList) {
						if ("COMPLETED".equals(c1.getCompletedStatus())) {
							for (Completed c2 : completedList) {
								if ("COMPLETED".equals(c2.getCompletedStatus()) && c1.getCompletedChallenge() != c2.getCompletedChallenge()) {
									java.time.LocalDate date1 = java.time.LocalDate.parse(c1.getCompletedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
									java.time.LocalDate date2 = java.time.LocalDate.parse(c2.getCompletedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
									if (date1.equals(date2) || date1.plusDays(1).equals(date2) || date1.minusDays(1).equals(date2)) {
										hasTwoIn24h = true;
										break;
									}
								}
							}
						}
					}
					if (hasTwoIn24h) {
						Awarded awarded = new Awarded(user, achievement, new Date());
						if (!AwardedDAO.getInstance().exists(awarded)) {
							AwardedDAO.getInstance().insert(awarded);
							LogUtil.writeLog("User " + idUser + " has earned the badge " + awarded.getAchievement().getTitle());
						}
					}
					break;
				case 5:
					if (!completedDAO.challengeIsCompleted(challenge.getId()) && timeCheck()) {
						Awarded awarded = new Awarded(user, achievement, new Date());
						if (!AwardedDAO.getInstance().exists(awarded)) {
							AwardedDAO.getInstance().insert(awarded);
							LogUtil.writeLog("User " + idUser + " has earned the badge " + awarded.getAchievement().getTitle());
						}
					}
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if current time is between midnight and 6AM
	 *
	 * @return true if current time is between midnight and 6AM
	 */
	private boolean timeCheck() {
		LocalTime now = LocalTime.now();
		LocalTime start = LocalTime.MIDNIGHT;
		LocalTime end = LocalTime.of(6, 0);
		return !now.isBefore(start) && !now.isAfter(end);
	}
}