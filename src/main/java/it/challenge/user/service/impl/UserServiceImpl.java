package it.challenge.user.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import it.challenge.user.dto.UserDTO;
import it.challenge.user.exception.CustomAlreadyExistsException;
import it.challenge.user.exception.CustomNotFoundException;
import it.challenge.user.model.User;
import it.challenge.user.repository.UserRepository;
import it.challenge.user.service.UserService;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
	@Override
	public UserDTO createUser(UserDTO userDto) {
		User u = userRepository.findByEmail(userDto.getEmail());
		if (u!=null)
			throw new CustomAlreadyExistsException(
					"The user with email [" + userDto.getEmail() + "] is already present in the database.");
		User user = modelMapper.map(userDto, User.class);
		userRepository.save(user);
		return null;
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		User u = userRepository.findByEmail(email);
		if (u==null)
			throw new CustomNotFoundException("The user with email [" + email + "] does not exists in the database.");
		return modelMapper.map(u, UserDTO.class);
	}

	@Override
	public List<UserDTO> searchUsers(String firstName, String lastName) {
		List<User> users;
		if(firstName != null && lastName != null)
			users = userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
		else if(firstName == null && lastName != null)
			users = userRepository.findByLastNameIgnoreCase(lastName);
		else if(firstName != null && lastName == null)
			users = userRepository.findByFirstNameIgnoreCase(firstName);
		else
			users = userRepository.findAll();
		return users.stream().map((u) -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
	}

	@Override
	public void updateUser(String email, UserDTO userDTO) {
		User u = userRepository.findByEmail(email);
		if (u==null)
			throw new CustomNotFoundException("The user with email [" + email + "] does not exists in the database.");
		u.setAddress(userDTO.getAddress());
		u.setFirstName(userDTO.getFirstName());
		u.setLastName(userDTO.getLastName());
		userRepository.save(u);
	}

	@Transactional
	@Override
	public void deleteUser(String email) {
		User u = userRepository.findByEmail(email);
		if (u==null)
			throw new CustomNotFoundException("The user with email [" + email + "] does not exists in the database.");
		userRepository.deleteByEmail(email);
	}

}
