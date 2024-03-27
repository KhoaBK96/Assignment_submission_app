package com.khoa.AssignmentSubmissionAp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoa.AssignmentSubmissionAp.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
}
