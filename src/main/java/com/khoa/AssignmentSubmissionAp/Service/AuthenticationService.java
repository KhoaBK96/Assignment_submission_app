package com.khoa.AssignmentSubmissionAp.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.khoa.AssignmentSubmissionAp.Repository.UserRepository;
import com.khoa.AssignmentSubmissionAp.domain.AuthenticationRequest;
import com.khoa.AssignmentSubmissionAp.domain.AuthenticationResponse;
import com.khoa.AssignmentSubmissionAp.domain.RegisterRequest;
import com.khoa.AssignmentSubmissionAp.domain.Role;
import com.khoa.AssignmentSubmissionAp.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	
	public AuthenticationResponse register(RegisterRequest request) {
		
		User user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.cohortStartDate(request.getCohortStartDate())
				.build();
		userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
				
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);
		
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		
		var jwtToken = jwtService.generateToken(user);
				
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

}
