package com.example.demo.controller;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.CustomerRegisteringResponseDto;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.dto.CustomerRegisterRequest;

@Validated
@RequestMapping(value= "/readingisgood/books-retail/v0")
public interface CustomerController {
	
	@PostMapping("/customer/register")
	ResponseEntity<CustomerRegisteringResponseDto> registerCustomer(@Validated @RequestBody CustomerRegisterRequest request);
	
	Customer getCustomer(String request);

}
