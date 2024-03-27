package com.khoa.AssignmentSubmissionAp.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class AuthenticationRequest {
	
	 private String email;
	 private String password;
	 private LocalDate cohortStartDate;
	  
}
