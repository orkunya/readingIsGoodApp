package com.example.demo.dto;

import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.dto.CustomerRegisterRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderResponseDto {
	
	private String id;
	private String createdUserName;
	// private String customerId;
	private String responseMessage; 
	private RegisterResponseCode responseCode;
	
	public OrderResponseDto(OrderRequest orderRequest) {
		this.createdUserName = orderRequest.getCreatedUserName();
	}
}
