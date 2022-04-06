package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.dto.CustomerRegisteringResponseDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "book")
@Data
public class Book {

	@Id
	@Field(name = "publicId")
	@Indexed(unique = false)
	private String publicId;
	
	@Field(name = "name")
	private String name;
	
	@Field(name = "price")
	private double price;
	
	@Field(name = "numberOf")
	private int numberOf;


}
