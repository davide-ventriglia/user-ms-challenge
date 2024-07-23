package it.challenge.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import it.challenge.user.model.User;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);
    }

    @Test
    public void testFindByEmail() {
        User foundUser = userRepository.findByEmail("test@gmail.com");
        assertNotNull(foundUser);
        assertEquals("John", foundUser.getFirstName());
    }

    @Test
    public void testDeleteByEmail() {
        userRepository.deleteByEmail("test@gmail.com");
        User foundUser = userRepository.findByEmail("test@gmail.com");
        assertEquals(null, foundUser);
    }

    @Test
    public void testFindByFirstNameIgnoreCase() {
        List<User> users = userRepository.findByFirstNameIgnoreCase("john");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Doe", users.get(0).getLastName());
    }

    @Test
    public void testFindByLastNameIgnoreCase() {
        List<User> users = userRepository.findByLastNameIgnoreCase("doe");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getFirstName());
    }

    @Test
    public void testFindByFirstNameIgnoreCaseAndLastNameIgnoreCase() {
        List<User> users = userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase("john", "doe");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("test@gmail.com", users.get(0).getEmail());
    }
}
