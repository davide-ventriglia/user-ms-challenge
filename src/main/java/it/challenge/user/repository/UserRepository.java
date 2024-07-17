package it.challenge.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.challenge.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	
	public void deleteByEmail(String email);
	
	public List<User> findByFirstNameIgnoreCase(String firstName);
	
	public List<User> findByLastNameIgnoreCase(String lastName);
	
	public List<User> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
