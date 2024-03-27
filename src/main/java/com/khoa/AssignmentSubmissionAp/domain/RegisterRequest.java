package com.khoa.AssignmentSubmissionAp.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	private String username;	
	private String email;	
	private String password;
	private Role role;
	private LocalDate cohortStartDate;
}
