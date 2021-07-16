package com.gamingage.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Users findByUsername(String username);
	Users findByEmail(String email);
	void deleteById(int id);
	List<Users> findByRole(String role);
	
	Boolean existsByEmail(String email);
	
}
