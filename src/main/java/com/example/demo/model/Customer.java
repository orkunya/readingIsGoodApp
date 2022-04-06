package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.dto.CustomerRegisteringResponseDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "customer")
@Data
public class Customer {

	@Id
	@Field(name = "userName")
	@Indexed(unique = true)
	private String userName;
	
	@Field(name = "firstName")
	private String firstName;
	
	@Field(name = "lastName")
	private String lastName;
	
	@Field(name = "address")
	private String address;
	
	@Field(name = "eMailAddress")
	private String eMailAddress;
	
	@Field(name = "phoneNumber")
	private String phoneNumber;
	
	@Field(name = "password")
	private String password;

	/*
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	} */

	/*private Boolean _isActive;

	public Boolean getIsActive() {
		return _isActive;
	}

	public void setIsActive(Boolean isActive) {
		_isActive = isActive;
	} */
}
