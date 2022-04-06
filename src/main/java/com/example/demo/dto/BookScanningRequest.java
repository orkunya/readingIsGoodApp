package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookScanningRequest {
	
	// private static final long serialVersionUID = 989898343434343434L;
	
	private String publicId;
	private String name;
	private String author;
	private int numberOf;
	private double price;

}
