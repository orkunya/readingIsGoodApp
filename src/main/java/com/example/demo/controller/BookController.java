package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.dto.BookScanningRequest;
import com.example.demo.dto.BookScanningResponseDto;
import com.example.demo.model.Book;


@Validated
@RequestMapping(value= "/readingisgood/book/v0")
public interface BookController {

	@PostMapping("/scan")
	ResponseEntity<BookScanningResponseDto> scanBook(@Validated @RequestBody BookScanningRequest request);
	
	Book getBookDetail(String publicId);
	
	Book updateBook(Book book);


	
}
