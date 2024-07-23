package it.challenge.user.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import it.challenge.user.dto.UserDTO;
import it.challenge.user.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/v1.0/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@gmail.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("test@gmail.com")));

        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        when(userService.getUserByEmail("test@gmail.com")).thenReturn(userDTO);

        mockMvc.perform(get("/api/v1.0/user/{email}", "test@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@gmail.com")));

        verify(userService, times(1)).getUserByEmail("test@gmail.com");
    }

    @Test
    void testSearchUsers() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setFirstName("John");
        UserDTO user2 = new UserDTO();
        user2.setFirstName("Jane");
        List<UserDTO> users = Arrays.asList(user1, user2);
        when(userService.searchUsers("John", "Doe")).thenReturn(users);

        mockMvc.perform(get("/api/v1.0/user/search")
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));

        verify(userService, times(1)).searchUsers("John", "Doe");
    }


    @Test
    void testUpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");

        mockMvc.perform(put("/api/v1.0/user/test@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@gmail.com\"}"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).updateUser(eq("test@gmail.com"), any(UserDTO.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1.0/user/test@gmail.com"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser("test@gmail.com");
    }

    @Test
    void testImportFromCsv_EmptyFile() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);

        mockMvc.perform(multipart("/api/v1.0/user/import-csv").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Please upload a CSV file."));

        verify(userService, never()).importFromCsv(any(MultipartFile.class));
    }

    @Test
    void testImportFromCsv_ValidFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "content".getBytes());
        when(userService.importFromCsv(any(MultipartFile.class))).thenReturn("File imported successfully.");

        mockMvc.perform(multipart("/api/v1.0/user/import-csv").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File imported successfully."));

        verify(userService, times(1)).importFromCsv(any(MultipartFile.class));
    }
}
