package com.example.demo.ControllerImplementation;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Constants.Constants;
import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.controller.CustomerController;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.dto.CustomerRegisteringResponseDto;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.dto.CustomerRegisterRequest;
import com.example.demo.repository.CustomerRepository;

import com.mongodb.MongoException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
public class CustomerControllerImpl implements CustomerController {

	private static Logger logger = Logger.getLogger(CustomerControllerImpl.class.getName());
	@Autowired
	private static final CustomerMapper MAPPER = CustomerMapper.INSTANCE;
	private final CustomerRepository customerRepository;
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	public CustomerControllerImpl(CustomerRepository customerRepository /*BCryptPasswordEncoder bCryptPasswordEncoder*/) {
		this.customerRepository = customerRepository;
		// this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	};

	/*
	public void Authentication() {
		
		AuthenticationManager authenticationManager ;
		Authentication authentication = null;
		authentication.setAuthenticated(true);
		JWTAuthenticationFilter jWTAuthenticationFilter = new JWTAuthenticationFilter(null);
		jWTAuthenticationFilter.attemptAuthentication(null, null);
	}*/
	@Override
	public ResponseEntity<CustomerRegisteringResponseDto> registerCustomer(
			@Validated CustomerRegisterRequest customerRegisterRequest) {
	//	customerRegisterRequest.setPassword(bCryptPasswordEncoder.encode(customerRegisterRequest.getPassword()));
		CustomerRegisteringResponseDto customerRegisteringResponseDto = new CustomerRegisteringResponseDto(
				customerRegisterRequest);
		String validateMessage = validateNullControl(customerRegisterRequest);
		if (!validateMessage.isEmpty()) {
			customerRegisteringResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			customerRegisteringResponseDto.setResponseMessage(validateMessage);
			return new ResponseEntity<>(customerRegisteringResponseDto, HttpStatus.BAD_REQUEST);
		}

		if (isCustomerExist(customerRegisterRequest.getUserName())) {
			customerRegisteringResponseDto.setResponseMessage(Constants.CUSTOMER_EXIST_MESSAGE);
			customerRegisteringResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			return new ResponseEntity<>(customerRegisteringResponseDto, HttpStatus.EXPECTATION_FAILED);
		}

		CustomerInfoDto customerInfoDto = new CustomerInfoDto(customerRegisterRequest);
		if (!customerInfoDto.getErrorMessage().isEmpty()) {
			customerRegisteringResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			customerRegisteringResponseDto.setResponseMessage(customerInfoDto.getErrorMessage());
			return new ResponseEntity<>(customerRegisteringResponseDto, HttpStatus.BAD_REQUEST);
		}
		
		try
		{
			Customer customer = MAPPER.toCustomerModel(customerInfoDto);
			Customer response = customerRepository.insert(customer);
			customerRegisteringResponseDto.setResponseCode(RegisterResponseCode.REGISTERED);
			customerRegisteringResponseDto.setResponseMessage(Constants.CUSTOMER_REGISTERING_SUCCESS);
						
			return new ResponseEntity<>(customerRegisteringResponseDto, HttpStatus.OK);
		}
		catch (MongoException ex)
		{
			customerRegisteringResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			customerRegisteringResponseDto.setResponseMessage(ex.getMessage());
			return new ResponseEntity<>(customerRegisteringResponseDto, HttpStatus.EXPECTATION_FAILED);
		}
		catch (Exception ex)
		{
			customerRegisteringResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			customerRegisteringResponseDto.setResponseMessage(ex.getMessage());
			return new ResponseEntity<>(customerRegisteringResponseDto, HttpStatus.EXPECTATION_FAILED);
		}


	}

	public boolean isCustomerExist(String userName) {
		Customer response = getCustomer(userName);
		if (response == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public Customer getCustomer(String userName) {
		Optional<Customer> response = customerRepository.findById(userName);
		if(response == null || response.isEmpty()) {
			return null;
		}
		return response.get();
	}

	public String validateNullControl(CustomerRegisterRequest input) {
		if (input.getUserName() == null || input.getUserName().isEmpty()) {
			return Constants.ERROR_MESSAGE_USERNAME_EMPTY;
		}
		if (input.getFirstName() == null || input.getFirstName().isEmpty()) {
			return Constants.ERROR_MESSAGE_FIRSTNAME_EMPTY;
		}
		if (input.getLastName() == null || input.getLastName().isEmpty()) {
			return Constants.ERROR_MESSAGE_LASTNAME_EMPTY;
		}
		if (input.getPhoneNumber() == null || input.getPhoneNumber().isEmpty()) {
			return Constants.ERROR_MESSAGE_PHONENUMBER_EMPTY;
		}
		if (input.getMailAddress() == null || input.getMailAddress().isEmpty()) {
			return Constants.ERROR_MESSAGE_MAILADDRESS_EMPTY;
		}
		/*if (input.getPassword() == null || input.getPassword().isEmpty()) {
			return "Password address can not be empty";
		}*/
		return "";
	}

}
