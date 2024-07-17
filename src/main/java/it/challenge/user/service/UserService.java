package it.challenge.user.service;

import java.util.List;

import it.challenge.user.dto.UserDTO;

public interface UserService {
	
	public UserDTO createUser(UserDTO userDto);
	
	public UserDTO getUserByEmail(String email);
	
	public List<UserDTO> searchUsers(String firstName, String lastName);
	
	public void updateUser(String email, UserDTO userDTO);
	
	public void deleteUser(String email);
}
