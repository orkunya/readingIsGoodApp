package com.example.demo.mapper;

import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.model.Customer;
import com.example.demo.dto.CustomerRegisterRequest;

public class CustomerMapperImpl extends CustomerMapper {

	@Override
	public Customer toCustomerModel(CustomerInfoDto customerInfoDto) {
	  if(customerInfoDto == null) {
		  return null;
	  }
	  Customer customer = new Customer();
	  customer.setFirstName(customerInfoDto.getFirstName());
	  customer.setLastName(customerInfoDto.getLastName());
	  customer.setUserName(customerInfoDto.getUserName());
	  customer.setEMailAddress(customerInfoDto.getEMailAddress());
	  customer.setPhoneNumber(customerInfoDto.getPhoneNumber());
	  customer.setAddress(customerInfoDto.getAddress());
	  customer.setPassword(customerInfoDto.getPassword());
	  return customer;
	  
	}
}
