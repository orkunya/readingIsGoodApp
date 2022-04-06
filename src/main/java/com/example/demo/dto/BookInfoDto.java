package com.example.demo.dto;

import com.example.demo.Constants.RegisterResponseCode;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookInfoDto {

	private String name;
	private String publicId;
	//private String privateId;
	private int numberOf;
	private double price;
	
	public BookInfoDto(BookScanningRequest bookScanningRequest) {
		this.name = bookScanningRequest.getName();
		this.publicId = bookScanningRequest.getPublicId();
		this.numberOf = bookScanningRequest.getNumberOf();
		this.price = bookScanningRequest.getPrice();
	}
}
