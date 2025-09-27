package defiut.backend.controller;

import defiut.backend.dao.AppUserDAO;
import defiut.backend.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * This class is responsible for the authentication of the users.
 */
public class AuthenticationController {

	private static final HashMap<String, AppUser> authenticatedUsers = new HashMap<>();

	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	/**
	 * Default constructor.
	 */
	public AuthenticationController() {
	}

	/**
	 * Hashes a password.
	 *
	 * @param password the password to hash
	 * @return the hashed password
	 * @throws NoSuchAlgorithmException if the algorithm is not found
	 */
	public String hashPassword(String password) throws NoSuchAlgorithmException {
		// Could use iterations... Could use salt...
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(password.getBytes());
		return Base64.getEncoder().encodeToString(hash);
	}

	/**
	 * Authenticates a user.
	 *
	 * @param email    the email of the user
	 * @param password the password of the user
	 * @return the authenticated user or null if the authentication failed
	 * @throws Exception if the connection to the database failed
	 */
	public AppUser authenticate(String email, String password) throws Exception {
		AppUser ret = null;
		if (AppUserDAO.getInstance().emailExists(email)) {
			AppUser user = AppUserDAO.getInstance().findByEmail(email);
			boolean authenticated = false;
			Iterator<Map.Entry<String, AppUser>> it = authenticatedUsers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, AppUser> pair = it.next();
				AppUser authUser = pair.getValue();
				if (authUser.getEmail().equals(user.getEmail())) {
					authenticated = true;
					break;
				}
			}
			if (!authenticated) {
				if (user.getPassword().equals(this.hashPassword(password))) {
					String token = Jwts.builder()
							.setSubject(user.getEmail())
							.claim("role", user.getRole())
							.setIssuer("defiut-app")
							.signWith(key)
							.compact();
					authenticatedUsers.put(token, user);
					ret = user;
				} else {
					throw new Exception("Invalid credentials");
				}
			} else {
				throw new Exception("User already authenticated");
			}
		} else {
			throw new Exception("User not found");
		}
		return ret;
	}

	/**
	 * Logs out a user.
	 *
	 * @param token the user to log out
	 */
	public void logout(String token) {
		authenticatedUsers.remove(token);
	}

	/**
	 * Checks if a user is authenticated.
	 *
	 * @param token the token of the session
	 * @return true if the user is authenticated, false otherwise
	 */
	public boolean isAuthenticated(String token) {
		boolean ret = authenticatedUsers.containsKey(token);
		return ret;
	}

	/**
	 * Converts a user to a token.
	 *
	 * @param user the user to convert
	 * @return the token
	 */
	public String userToToken(AppUser user) {
		String ret = null;
		Iterator<Map.Entry<String, AppUser>> it = authenticatedUsers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, AppUser> pair = it.next();
			AppUser authUser = pair.getValue();
			if (authUser.getEmail().equals(user.getEmail())) {
				ret = pair.getKey();
				break;
			}
		}
		return ret;
	}

	/**
	 * Converts a token to a user.
	 *
	 * @param token the token to convert
	 * @return the user
	 */
	public AppUser tokenToUser(String token) {
		return authenticatedUsers.get(token);
	}

	/**
	 * Edit a user linked to a session.
	 *
	 * @param token the token of the session
	 * @param user  the user to edit
	 */
	public void editUser(String token, AppUser user) {
		authenticatedUsers.replace(token, user);
	}
}