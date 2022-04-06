package com.example.demo.dto;

import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.dto.CustomerRegisterRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookScanningResponseDto {
	
	private String name;
	private String publicId;
	// private String customerId;
	private String responseMessage; 
	private RegisterResponseCode responseCode;
	
	public BookScanningResponseDto(BookScanningRequest bookScanningRequest) {
		this.name = bookScanningRequest.getName();
		this.publicId = bookScanningRequest.getPublicId();
	}
}
