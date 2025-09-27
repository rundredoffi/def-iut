package defiut.backend.controller;

import defiut.backend.dao.*;
import defiut.backend.model.*;
import defiut.backend.model.domain.UserRole;
import defiut.backend.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	private UserController userController;
	private AuthenticationController mockAuthController;

	@BeforeEach
	void setup() {
		userController = new UserController();
		mockAuthController = mock(AuthenticationController.class);
		ReflectionTestUtils.setField(userController, "authenticationController", mockAuthController);
	}

	@Test
	void testGetScores() {
		try (MockedStatic<AppUserDAO> mockedAppUserDAO = mockStatic(AppUserDAO.class);
		     MockedStatic<GrantedDAO> mockedGrantedDAO = mockStatic(GrantedDAO.class)) {

			ArrayList<AppUser> users = new ArrayList<>();
			users.add(new AppUser(1, "user1", "user1@test.com", "pwd", UserRole.USER, 100));
			users.add(new AppUser(2, "user2", "user2@test.com", "pwd", UserRole.USER, 50));

			AppUserDAO mockAppDao = mock(AppUserDAO.class);
			GrantedDAO mockGrantedDao = mock(GrantedDAO.class);

			mockedAppUserDAO.when(AppUserDAO::getInstance).thenReturn(mockAppDao);
			mockedGrantedDAO.when(GrantedDAO::getInstance).thenReturn(mockGrantedDao);

			when(mockAppDao.findAll()).thenReturn(users);

			ResponseEntity<String> resp = userController.getUsersScore();

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertTrue(resp.getBody().contains("user1"));
			assertTrue(resp.getBody().contains("100"));
		} catch (SQLException e) {
			fail(e);
		}
	}

	@Test
	void testAdd() {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			JSONObject userJson = new JSONObject();
			userJson.put("name", "test");
			userJson.put("email", "test@test.com");
			userJson.put("password", "pwd");

			AppUserDAO mockDao = mock(AppUserDAO.class);
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			when(mockDao.emailExists("test@test.com")).thenReturn(false);
			when(mockDao.nicknameExists("test")).thenReturn(false);

			ResponseEntity<String> resp = userController.addUser(userJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals("User added", resp.getBody());
			verify(mockDao).insert(any());
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testUpdate() {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			AppUser user = new AppUser(1, "test", "test@test.com", "pwd", UserRole.USER, 0);

			JSONObject userJson = new JSONObject();
			userJson.put("token", "tok");
			userJson.put("name", "newName");
			userJson.put("email", "new@test.com");

			when(mockAuthController.tokenToUser("tok")).thenReturn(user);

			AppUserDAO mockDao = mock(AppUserDAO.class);
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			when(mockDao.select(1)).thenReturn(user);

			ResponseEntity<String> resp = userController.updateUser(userJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals("User updated", resp.getBody());
			verify(mockDao).update(any());
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testDelete() {
		try (MockedStatic<AppUserDAO> mockedAppUserDAO = mockStatic(AppUserDAO.class);
		     MockedStatic<AwardedDAO> mockedAwardedDAO = mockStatic(AwardedDAO.class);
		     MockedStatic<CompletedDAO> mockedCompletedDAO = mockStatic(CompletedDAO.class);
		     MockedStatic<GrantedDAO> mockedGrantedDAO = mockStatic(GrantedDAO.class)) {

			AppUser user = new AppUser(1, "test", "test@test.com", "pwd", UserRole.USER, 0);
			JSONObject userJson = new JSONObject();
			userJson.put("token", "tok");

			when(mockAuthController.tokenToUser("tok")).thenReturn(user);

			AppUserDAO mockAppDao = mock(AppUserDAO.class);
			AwardedDAO mockAwardedDao = mock(AwardedDAO.class);
			CompletedDAO mockCompletedDao = mock(CompletedDAO.class);
			GrantedDAO mockGrantedDao = mock(GrantedDAO.class);

			mockedAppUserDAO.when(AppUserDAO::getInstance).thenReturn(mockAppDao);
			mockedAwardedDAO.when(AwardedDAO::getInstance).thenReturn(mockAwardedDao);
			mockedCompletedDAO.when(CompletedDAO::getInstance).thenReturn(mockCompletedDao);
			mockedGrantedDAO.when(GrantedDAO::getInstance).thenReturn(mockGrantedDao);

			when(mockCompletedDao.select(1)).thenReturn(new ArrayList<>());
			when(mockGrantedDao.select(1)).thenReturn(new ArrayList<>());

			ResponseEntity<String> resp = userController.deleteUser(userJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals("User deleted", resp.getBody());
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testLogin() {
		try (MockedStatic<AwardedDAO> mockedAwardedDAO = mockStatic(AwardedDAO.class)) {
			AppUser user = new AppUser(1, "test", "test@test.com", "pwd", UserRole.USER, 0);
			JSONObject userJson = new JSONObject();
			userJson.put("email", "test@test.com");
			userJson.put("password", "pwd");

			when(mockAuthController.authenticate("test@test.com", "pwd")).thenReturn(user);
			when(mockAuthController.userToToken(user)).thenReturn("tok");

			AwardedDAO mockAwardedDao = mock(AwardedDAO.class);
			mockedAwardedDAO.when(AwardedDAO::getInstance).thenReturn(mockAwardedDao);
			when(mockAwardedDao.selectByUser(user)).thenReturn(new ArrayList<>());

			ResponseEntity<String> resp = userController.login(userJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertTrue(resp.getBody().contains("tok"));
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testLogout() {
		try {
			JSONObject userJson = new JSONObject();
			userJson.put("token", "tok");

			AppUser user = new AppUser(1, "test", "test@test.com", "pwd", UserRole.USER, 0);
			when(mockAuthController.isAuthenticated("tok")).thenReturn(true);
			when(mockAuthController.tokenToUser("tok")).thenReturn(user);

			ResponseEntity<String> resp = userController.logout(userJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals("User logged out", resp.getBody());
			verify(mockAuthController).logout("tok");
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testGetUser() {
		try (MockedStatic<AwardedDAO> mockedAwardedDAO = mockStatic(AwardedDAO.class)) {
			AppUser user = new AppUser(1, "test", "test@test.com", "pwd", UserRole.USER, 0);
			JSONObject tokenJson = new JSONObject();
			tokenJson.put("token", "tok");

			when(mockAuthController.tokenToUser("tok")).thenReturn(user);

			AwardedDAO mockAwardedDao = mock(AwardedDAO.class);
			mockedAwardedDAO.when(AwardedDAO::getInstance).thenReturn(mockAwardedDao);
			when(mockAwardedDao.selectByUser(user)).thenReturn(new ArrayList<>());

			ResponseEntity<String> resp = userController.getUserByToken(tokenJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertTrue(resp.getBody().contains("test@test.com"));
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testGetPoints() {
		try {
			AppUser user = new AppUser(1, "test", "test@test.com", "pwd", UserRole.USER, 100);
			JSONObject tokenJson = new JSONObject();
			tokenJson.put("token", "tok");

			when(mockAuthController.tokenToUser("tok")).thenReturn(user);

			ResponseEntity<String> resp = userController.getPointsByUser(tokenJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertTrue(resp.getBody().contains("100"));
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void testGetBadges() {
		try (MockedStatic<GrantedDAO> mockedGrantedDAO = mockStatic(GrantedDAO.class)) {
			AppUser user = new AppUser(1, "test", "test@test.com", "pwd", UserRole.USER, 0);
			JSONObject tokenJson = new JSONObject();
			tokenJson.put("token", "tok");

			when(mockAuthController.tokenToUser("tok")).thenReturn(user);

			GrantedDAO mockGrantedDao = mock(GrantedDAO.class);
			mockedGrantedDAO.when(GrantedDAO::getInstance).thenReturn(mockGrantedDao);
			when(mockGrantedDao.select(1)).thenReturn(new ArrayList<>());

			ResponseEntity<String> resp = userController.getBadgesByUser(tokenJson.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertTrue(resp.getBody().contains("badges"));
		} catch (Exception e) {
			fail(e);
		}
	}
}