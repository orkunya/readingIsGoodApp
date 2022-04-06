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

import com.example.demo.ControllerImplementation.BookControllerImpl;
import com.example.demo.ControllerImplementation.CustomerControllerImpl;
import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.BookScanningRequest;
import com.example.demo.dto.BookScanningResponseDto;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.dto.CustomerRegisterRequest;
import com.example.demo.dto.CustomerRegisteringResponseDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CustomerRepository;

import org.junit.Assert;
import org.junit.Before;

public class BookControllerImplTest {
	BookControllerImpl bookControllerImplTest;
	BookScanningRequest bookScanningRequest = new BookScanningRequest();
	private static final BookMapper MAPPER = BookMapper.INSTANCE;

	@Mock
	BookRepository bookRepositoryMock;

	Book requestBook;

	@BeforeEach
	public void setUp() {
		bookScanningRequest.setName("Harry Potter");
		bookScanningRequest.setAuthor("Rowling");
		bookScanningRequest.setNumberOf(2);
		bookScanningRequest.setPrice(20);
		bookScanningRequest.setPublicId("100");

		BookInfoDto bookInfoDto = new BookInfoDto(bookScanningRequest);
		requestBook = MAPPER.toBookModel(bookInfoDto);

		bookRepositoryMock = PowerMockito.mock(BookRepository.class);

		bookControllerImplTest = new BookControllerImpl(bookRepositoryMock);

	}

	@Test
	public void scanBookTest() {

		PowerMockito.doReturn(requestBook).when(bookRepositoryMock).insert(any(Book.class));
		PowerMockito.doReturn(requestBook).when(bookRepositoryMock).save(any(Book.class));

		ResponseEntity<BookScanningResponseDto> response = bookControllerImplTest
				.scanBook(bookScanningRequest);

		Assert.assertEquals(response.getBody().getPublicId(), "100");
		
		Optional<Book> findByIdResponse = Optional.of(requestBook);

		PowerMockito.doReturn(findByIdResponse).when(bookRepositoryMock).findById(any(String.class));
		
		ResponseEntity<BookScanningResponseDto> response2 = bookControllerImplTest
				.scanBook(bookScanningRequest);
		
		Assert.assertEquals(response.getBody().getPublicId(), "100");

	}



}
