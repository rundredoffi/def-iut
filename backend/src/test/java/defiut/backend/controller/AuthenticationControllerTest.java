package defiut.backend.controller;

import defiut.backend.dao.AppUserDAO;
import defiut.backend.model.AppUser;
import defiut.backend.model.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

	private AuthenticationController authController;
	private AppUser testUser;
	private AppUserDAO mockDao;

	@BeforeEach
	void setup() {
		authController = new AuthenticationController();
		testUser = new AppUser(1, "test", "test@test.com", "hashedPassword", UserRole.USER, 0);
		mockDao = mock(AppUserDAO.class);

		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			when(mockDao.emailExists("test@test.com")).thenReturn(true);
			when(mockDao.findByEmail("test@test.com")).thenReturn(testUser);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testAuth() throws Exception {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);

			testUser.setPassword(authController.hashPassword("password123"));

			AppUser authenticatedUser = authController.authenticate("test@test.com", "password123");

			assertNotNull(authenticatedUser);
			assertEquals(testUser, authenticatedUser);

			Exception exception = assertThrows(Exception.class, () ->
					authController.authenticate("test@test.com", "password123")
			);
			assertEquals("User already authenticated", exception.getMessage());

			authController.logout(authController.userToToken(testUser));
			exception = assertThrows(Exception.class, () ->
					authController.authenticate("test@test.com", "wrongPassword")
			);
			assertEquals("Invalid credentials", exception.getMessage());

			when(mockDao.emailExists("notfound@test.com")).thenReturn(false);
			exception = assertThrows(Exception.class, () ->
					authController.authenticate("notfound@test.com", "password123")
			);
			assertEquals("User not found", exception.getMessage());
		}
	}

	@Test
	void testLogout() throws Exception {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			testUser.setPassword(authController.hashPassword("password123"));
			AppUser user = authController.authenticate("test@test.com", "password123");
			String token = authController.userToToken(user);

			assertTrue(authController.isAuthenticated(token));
			authController.logout(token);
			assertFalse(authController.isAuthenticated(token));
		}
	}

	@Test
	void testIsAuth() throws Exception {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			testUser.setPassword(authController.hashPassword("password123"));
			AppUser user = authController.authenticate("test@test.com", "password123");
			String token = authController.userToToken(user);

			assertTrue(authController.isAuthenticated(token));
			assertFalse(authController.isAuthenticated("invalidToken"));
		}
	}

	@Test
	void testToken() throws Exception {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			testUser.setPassword(authController.hashPassword("password123"));
			AppUser user = authController.authenticate("test@test.com", "password123");
			String token = authController.userToToken(user);

			assertNotNull(token);
			assertEquals(64, token.length());

			AppUser notAuthUser = new AppUser(2, "test2", "test2@test.com", "pwd", UserRole.USER, 0);
			assertNull(authController.userToToken(notAuthUser));
		}
	}

	@Test
	void testUser() throws Exception {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			testUser.setPassword(authController.hashPassword("password123"));
			AppUser user = authController.authenticate("test@test.com", "password123");
			String token = authController.userToToken(user);

			assertEquals(user, authController.tokenToUser(token));
			assertNull(authController.tokenToUser("invalidToken"));
		}
	}

	@Test
	void testEdit() throws Exception {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			testUser.setPassword(authController.hashPassword("password123"));
			AppUser user = authController.authenticate("test@test.com", "password123");
			String token = authController.userToToken(user);

			AppUser updatedUser = new AppUser(1, "updated", "test@test.com", "hashedPassword", UserRole.USER, 10);
			authController.editUser(token, updatedUser);

			assertEquals(updatedUser, authController.tokenToUser(token));
		}
	}
}