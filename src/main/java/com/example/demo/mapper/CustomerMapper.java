package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.model.Customer;
import com.example.demo.dto.CustomerRegisterRequest;

//@Mapper(componentModel = "spring")   mapstruct bir porblemden dolayı çalışmadığı için implementasyon sınıfı manuel yaratıldı 
public abstract class CustomerMapper {
	
	public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
	
	public abstract Customer toCustomerModel(CustomerInfoDto customerInfoDto);
	//@Mapping(source="userName", target="userName")
	//public abstract Customer toCustomerModel(CustomerInfoDto customerInfoDto);
	
	// public abstract CustomerInfoDto toCustomerInfoDto (CustomerRegisterRequest customerRegisterRequest);

}
