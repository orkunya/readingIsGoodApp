package com.example.demo.mapper;

import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.dto.CustomerRegisterRequest;

public class BookMapperImpl extends BookMapper {

	@Override
	public Book toBookModel(BookInfoDto bookInfoDto) {
	  if(bookInfoDto == null) {
		  return null;
	  }
	  Book book = new Book();
	  book.setPublicId(bookInfoDto.getPublicId());
	  book.setName(bookInfoDto.getName());
	  book.setNumberOf(bookInfoDto.getNumberOf());
	  book.setPrice(bookInfoDto.getPrice());
	  return book;
	  
	}
}
