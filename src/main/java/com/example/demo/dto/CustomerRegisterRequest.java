package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomerRegisterRequest {
	
	// private static final long serialVersionUID = 989898343434343434L;
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String mailAddress;
	private String phoneNumber;

}
