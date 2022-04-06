package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.HashMap;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDetail {

	private String bookPublicId;
	private int numberOfBooksOrdered;
	private double bookPrice;
	
}
