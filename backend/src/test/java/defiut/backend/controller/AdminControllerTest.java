package defiut.backend.controller;

import defiut.backend.dao.AppUserDAO;
import defiut.backend.dao.AwardedDAO;
import defiut.backend.dao.CompletedDAO;
import defiut.backend.dao.GrantedDAO;
import defiut.backend.model.AppUser;
import defiut.backend.model.Awarded;
import defiut.backend.model.Completed;
import defiut.backend.model.Granted;
import defiut.backend.model.domain.UserRole;
import defiut.backend.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

	private AdminController adminController;
	private AuthenticationController mockAuthController;

	@BeforeEach
	void setUp() {
		adminController = new AdminController();
		mockAuthController = mock(AuthenticationController.class);
		ReflectionTestUtils.setField(adminController, "authenticationController", mockAuthController);
	}

	@Test
	void testGetUser() {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			AppUser adminUser = new AppUser(1, "admin", "admin@test.com", "pwd", UserRole.ADMIN, 99);
			when(mockAuthController.tokenToUser("tok")).thenReturn(adminUser);

			AppUser user = new AppUser(2, "user", "user@test.com", "pw", UserRole.USER, 42);
			ArrayList<AppUser> users = new ArrayList<>();
			users.add(user);

			AppUserDAO mockDao = mock(AppUserDAO.class);
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			when(mockDao.findAll()).thenReturn(users);

			JSONObject param = new JSONObject();
			param.put("token", "tok");
			ResponseEntity<String> resp = adminController.getUsers(param.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertTrue(resp.getBody().contains("user@test.com"));
		} catch (SQLException | JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testUpdateUserAdmin() {
		try (MockedStatic<AppUserDAO> mockedStatic = mockStatic(AppUserDAO.class)) {
			AppUser adminUser = new AppUser(1, "admin", "admin@test.com", "pwd", UserRole.ADMIN, 0);
			AppUser userToUpdate = new AppUser(2, "bob", "bob@mail.com", "pw", UserRole.USER, 5);
			JSONObject data = new JSONObject();
			data.put("token", "tok");
			data.put("id", 2);
			data.put("name", "bobNew");
			data.put("email", "bob@new.com");
			data.put("role", "USER");
			data.put("score", 64);

			when(mockAuthController.tokenToUser("tok")).thenReturn(adminUser);

			AppUserDAO mockDao = mock(AppUserDAO.class);
			mockedStatic.when(AppUserDAO::getInstance).thenReturn(mockDao);
			when(mockDao.select(2)).thenReturn(userToUpdate);

			ResponseEntity<String> resp = adminController.updateUserAdmin(data.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals("User updated", resp.getBody());
			verify(mockDao).update(any());
		} catch (SQLException | JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testDeleteUserAdmin() {
		try (MockedStatic<AppUserDAO> mockedAppUserDAO = mockStatic(AppUserDAO.class);
		     MockedStatic<CompletedDAO> mockedCompletedDAO = mockStatic(CompletedDAO.class);
		     MockedStatic<GrantedDAO> mockedGrantedDAO = mockStatic(GrantedDAO.class);
		     MockedStatic<AwardedDAO> mockedAwardedDAO = mockStatic(AwardedDAO.class)) {

			AppUser adminUser = new AppUser(1, "admin", "admin@test.com", "pwd", UserRole.ADMIN, 0);
			AppUser user = new AppUser(3, "user", "user@mail.com", "pw", UserRole.USER, 10);

			when(mockAuthController.tokenToUser("tok")).thenReturn(adminUser);

			AppUserDAO mockAppDao = mock(AppUserDAO.class);
			CompletedDAO mockCompletedDao = mock(CompletedDAO.class);
			GrantedDAO mockGrantedDao = mock(GrantedDAO.class);
			AwardedDAO mockAwardedDao = mock(AwardedDAO.class);

			mockedAppUserDAO.when(AppUserDAO::getInstance).thenReturn(mockAppDao);
			mockedCompletedDAO.when(CompletedDAO::getInstance).thenReturn(mockCompletedDao);
			mockedGrantedDAO.when(GrantedDAO::getInstance).thenReturn(mockGrantedDao);
			mockedAwardedDAO.when(AwardedDAO::getInstance).thenReturn(mockAwardedDao);

			when(mockAppDao.select(3)).thenReturn(user);
			when(mockCompletedDao.select(3)).thenReturn(new ArrayList<>());
			when(mockGrantedDao.select(3)).thenReturn(new ArrayList<>());
			doNothing().when(mockAwardedDao).deleteAllForUser(user);
			ArrayList<Awarded> emptyAwardedList = new ArrayList<>();
			when(mockAwardedDao.selectByUser(user)).thenReturn(emptyAwardedList);
			doNothing().when(mockAppDao).delete(user);

			JSONObject data = new JSONObject();
			data.put("token", "tok");
			data.put("id", 3);
			ResponseEntity<String> resp = adminController.deleteUserAdmin(data.toString());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals("User deleted", resp.getBody());
		} catch (SQLException | JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testLogAdmin() throws IOException {
		try (MockedStatic<LogUtil> mockedLogUtil = mockStatic(LogUtil.class)) {
			AppUser adminUser = new AppUser(1, "admin", "admin@test.com", "pw", UserRole.ADMIN, 0);
			when(mockAuthController.tokenToUser("tok")).thenReturn(adminUser);

			BufferedReader br = mock(BufferedReader.class);
			when(br.readLine())
					.thenReturn("ligne1")
					.thenReturn("ligne2")
					.thenReturn(null);

			mockedLogUtil.when(LogUtil::openReader).thenReturn(br);

			try {
				JSONObject data = new JSONObject();
				data.put("token", "tok");
				ResponseEntity<String> resp = adminController.getLogs(data.toString());

				assertEquals(HttpStatus.OK, resp.getStatusCode());
				assertTrue(resp.getBody().contains("ligne1"));
				assertTrue(resp.getBody().contains("ligne2"));
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
	}
}