package com.example.demo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import com.example.demo.ControllerImplementation.CustomerControllerImpl;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.dto.CustomerRegisterRequest;
import com.example.demo.dto.CustomerRegisteringResponseDto;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

import org.junit.Assert;
import org.junit.Before;

public class CustomerControllerImplTest {
	CustomerControllerImpl customerControllerImplTest;
	CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
	private static final CustomerMapper MAPPER = CustomerMapper.INSTANCE;

	@Mock
	CustomerRepository customerRepositoryMock;

	Customer requestCustomer;

	@BeforeEach
	public void setUp() {
		customerRegisterRequest.setUserName("orkunya");
		customerRegisterRequest.setFirstName("Orkun");
		customerRegisterRequest.setLastName("Yasar");
		customerRegisterRequest.setAddress("Istanbul");
		customerRegisterRequest.setMailAddress("orkunya@gmail.com");
		customerRegisterRequest.setPhoneNumber("5385010490");
		customerRegisterRequest.setPassword("sfght689");

		CustomerInfoDto customerInfoDto = new CustomerInfoDto(customerRegisterRequest);
		requestCustomer = MAPPER.toCustomerModel(customerInfoDto);

		customerRepositoryMock = PowerMockito.mock(CustomerRepository.class);
		// BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(24);

		customerControllerImplTest = new CustomerControllerImpl(customerRepositoryMock /*,bCryptPasswordEncoder*/);

	}

	@Test
	public void registerCustomerTest() {

		PowerMockito.doReturn(requestCustomer).when(customerRepositoryMock).insert(any(Customer.class));

		ResponseEntity<CustomerRegisteringResponseDto> response = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

		Assert.assertEquals(response.getBody().getUserName(), "orkunya");

		// step2
		customerRegisterRequest.setPhoneNumber("123");

		ResponseEntity<CustomerRegisteringResponseDto> response2 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

		// step3
		Optional<Customer> findByIdResponse = null;

		PowerMockito.doReturn(findByIdResponse).when(customerRepositoryMock).findById(any(String.class));

		ResponseEntity<CustomerRegisteringResponseDto> response3 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

	}

	@Test
	public void registerCustomerTestNullCase() {

		customerRegisterRequest.setUserName(null);
		ResponseEntity<CustomerRegisteringResponseDto> response1 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

		// step2
		customerRegisterRequest.setUserName("orkunya");
		Optional<Customer> findByIdResponse = Optional.of(requestCustomer);

		PowerMockito.doReturn(findByIdResponse).when(customerRepositoryMock).findById(any(String.class));

		ResponseEntity<CustomerRegisteringResponseDto> response2 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

		// step3
		customerRegisterRequest.setFirstName("");
		ResponseEntity<CustomerRegisteringResponseDto> response3 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

		// step4
		customerRegisterRequest.setFirstName("Orkun");
		customerRegisterRequest.setLastName("");
		ResponseEntity<CustomerRegisteringResponseDto> response4 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

		// step5
		customerRegisterRequest.setLastName("Yasar");
		customerRegisterRequest.setPhoneNumber("");
		ResponseEntity<CustomerRegisteringResponseDto> response5 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);

		// step5
		customerRegisterRequest.setPhoneNumber("5385010490");
		customerRegisterRequest.setMailAddress("");
		ResponseEntity<CustomerRegisteringResponseDto> response6 = customerControllerImplTest
				.registerCustomer(customerRegisterRequest);
		
		customerRegisterRequest.setMailAddress("orkunya@gmail.com");
	}

}
