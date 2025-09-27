package defiut.backend.controller;

import defiut.backend.dao.*;
import defiut.backend.model.*;
import defiut.backend.model.domain.ChallengeDifficulty;
import defiut.backend.model.domain.ChallengeLanguage;
import defiut.backend.model.domain.UserRole;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ChallengeControllerTest {

	@InjectMocks
	private ChallengeController challengeController;

	@Mock
	private AuthenticationController authenticationController;

	@Mock
	private ChallengeDAO challengeDAO;

	@Mock
	private CompletedDAO completedDAO;

	@Mock
	private AppUserDAO appUserDAO;

	private AppUser testUser;
	private Challenge testChallenge;
	private String testToken;

	@BeforeEach
	void setup() throws SQLException {
		MockitoAnnotations.openMocks(this);
		testUser = new AppUser(1, "test", "test@test.com", "password", UserRole.USER, 0);
		testChallenge = new Challenge(1, "Test Challenge", "2023-01-01", ChallengeDifficulty.EASY,
				ChallengeLanguage.JAVA, "Description", "flag", 1, 100);
		testToken = "test-token";

		when(authenticationController.tokenToUser(anyString())).thenReturn(testUser);
		when(challengeDAO.select(anyInt())).thenReturn(testChallenge);
	}

	@Test
	void testGetChallenges() throws Exception {
		ArrayList<Challenge> challenges = new ArrayList<>();
		challenges.add(testChallenge);
		when(challengeDAO.findAll()).thenReturn(challenges);

		ResponseEntity<String> response = challengeController.getChallenges();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONObject jsonResponse = new JSONObject(response.getBody());
		assertTrue(jsonResponse.has("challenges"));
	}

	@Test
	void testGetTags() throws Exception {
		ArrayList<ChallengeTag> challengeTags = new ArrayList<>();
		ArrayList<Tag> tags = new ArrayList<>();

		ResponseEntity<String> response = challengeController.getTagsByChallenge(1);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONObject jsonResponse = new JSONObject(response.getBody());
		assertTrue(jsonResponse.has("tags"));
	}
}