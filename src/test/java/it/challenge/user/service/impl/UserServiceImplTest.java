package it.challenge.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import it.challenge.user.dto.UserDTO;
import it.challenge.user.exception.CustomAlreadyExistsException;
import it.challenge.user.exception.CustomNotFoundException;
import it.challenge.user.model.User;
import it.challenge.user.repository.UserRepository;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        User existingUser = new User();
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(existingUser);

        assertThrows(CustomAlreadyExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testCreateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        UserDTO createdUser = userService.createUser(userDTO);

        assertNull(createdUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserByEmail_UserNotFound() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(null);

        assertThrows(CustomNotFoundException.class, () -> userService.getUserByEmail("test@gmail.com"));
    }

    @Test
    void testGetUserByEmail_Success() {
        User user = new User();
        user.setEmail("test@gmail.com");
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        UserDTO userDTO = userService.getUserByEmail("test@gmail.com");

        assertNotNull(userDTO);
        assertEquals("test@gmail.com", userDTO.getEmail());
    }

    @Test
    void testSearchUsers() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        List<User> users = Arrays.asList(user);
        when(userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase("John", "Doe")).thenReturn(users);

        List<UserDTO> result = userService.searchUsers("John", "Doe");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);

        assertThrows(CustomNotFoundException.class, () -> userService.updateUser("test@gmail.com", userDTO));
    }

    @Test
    void testUpdateUser_Success() {
        User user = new User();
        user.setEmail("test@gmail.com");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setAddress("123 Street");
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        userService.updateUser("test@gmail.com", userDTO);

        verify(userRepository, times(1)).save(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("123 Street", user.getAddress());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(null);

        assertThrows(CustomNotFoundException.class, () -> userService.deleteUser("test@gmail.com"));
    }

    @Test
    void testDeleteUser_Success() {
        User user = new User();
        user.setEmail("test@gmail.com");
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);

        userService.deleteUser("test@gmail.com");

        verify(userRepository, times(1)).deleteByEmail("test@gmail.com");
    }

    @Test
    void testImportFromCsv_ValidFile() throws IOException {
        String csvContent = "email,firstName,lastName,address\n" +
                            "test1@gmail.com,mario,rossi,via roma 1\n" +
                            "test2@gmail.com,giorgio,vanni,via milano 2\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", new ByteArrayInputStream(csvContent.getBytes()));

        String response = userService.importFromCsv(file);

        assertEquals("File uploaded and processed successfully.", response);
        verify(userRepository, times(2)).save(any(User.class));
    }
}
