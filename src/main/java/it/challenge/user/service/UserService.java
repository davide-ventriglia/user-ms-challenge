package it.challenge.user.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.challenge.user.dto.UserDTO;

public interface UserService {
	
	public UserDTO createUser(UserDTO userDto);
	
	public UserDTO getUserByEmail(String email);
	
	public List<UserDTO> searchUsers(String firstName, String lastName);
	
	public void updateUser(String email, UserDTO userDTO);
	
	public void deleteUser(String email);
	
	public String importFromCsv(MultipartFile file) throws IOException;
}
