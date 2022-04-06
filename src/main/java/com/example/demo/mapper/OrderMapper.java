package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.dto.CustomerRegisterRequest;
import com.example.demo.dto.OrderInfoDto;

//@Mapper(componentModel = "spring")   mapstruct bir porblemden dolayı çalışmadığı için implementasyon sınıfı manuel yaratıldı 
public abstract class OrderMapper {
	
	public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
	
	public abstract Order toOrderModel(OrderInfoDto orderInfoDto);
	//@Mapping(source="userName", target="userName")
	//public abstract Customer toCustomerModel(CustomerInfoDto customerInfoDto);
	
	// public abstract CustomerInfoDto toCustomerInfoDto (CustomerRegisterRequest customerRegisterRequest);
	
}
