package it.challenge.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.challenge.user.dto.UserDTO;
import it.challenge.user.service.UserService;

@RestController
@RequestMapping("/api/v1.0/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		UserDTO createdUser = userService.createUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@GetMapping("/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable(name = "email") String email) {
		UserDTO user = userService.getUserByEmail(email);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam(name = "firstName", required = false) String firstName,
			@RequestParam(name = "lastName", required = false) String lastName) {
		List<UserDTO> users = userService.searchUsers(firstName, lastName);
		return ResponseEntity.ok(users);
	}

	@PutMapping("/{email}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable(name = "email") String email, @RequestBody UserDTO userDTO) {
		userService.updateUser(email, userDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{email}")
	public ResponseEntity<Void> deleteUser(@PathVariable(name = "email") String email) {
		userService.deleteUser(email);
		return ResponseEntity.noContent().build();
	}

}
