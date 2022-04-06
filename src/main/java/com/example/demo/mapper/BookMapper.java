package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.dto.CustomerRegisterRequest;

//@Mapper(componentModel = "spring")   mapstruct bir porblemden dolayı çalışmadığı için implementasyon sınıfı manuel yaratıldı 
public abstract class BookMapper {
	
	public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
	
	public abstract Book toBookModel(BookInfoDto bookInfoDto);
	//@Mapping(source="userName", target="userName")
	//public abstract Customer toCustomerModel(CustomerInfoDto customerInfoDto);
	
	// public abstract CustomerInfoDto toCustomerInfoDto (CustomerRegisterRequest customerRegisterRequest);

}
