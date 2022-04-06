package com.example.demo.dto;

import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.dto.CustomerRegisterRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomerRegisteringResponseDto {
	
	private String userName;
	// private String customerId;
	private String responseMessage; 
	private RegisterResponseCode responseCode;
	
	public CustomerRegisteringResponseDto(CustomerRegisterRequest customerRegisterRequest) {
		this.userName = customerRegisterRequest.getUserName();
	}
}
