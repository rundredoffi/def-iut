package defiut.backend;

import defiut.backend.dao.AppUserDAO;
import defiut.backend.model.AppUser;
import defiut.backend.model.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testUserCreation() {
		AppUser user = new AppUser(1, "testUser", "test@email.com", "password", UserRole.USER, 0);

		assertEquals(1, user.getId());
		assertEquals("testUser", user.getNickname());
		assertEquals("test@email.com", user.getEmail());
		assertEquals("password", user.getPassword());
		assertEquals(UserRole.USER, user.getRole());
		assertEquals(0, user.getScore());
	}

	@Test
	void testUserScore() {
		AppUser user = new AppUser(1, "testUser", "test@email.com", "password", UserRole.USER, 0);
		user.setScore(100);
		assertEquals(100, user.getScore());
	}

	@Test
	void testUserRole() {
		AppUser user = new AppUser(1, "testUser", "test@email.com", "password", UserRole.USER, 0);
		user.setRole(UserRole.ADMIN);
		assertEquals(UserRole.ADMIN, user.getRole());
	}
}