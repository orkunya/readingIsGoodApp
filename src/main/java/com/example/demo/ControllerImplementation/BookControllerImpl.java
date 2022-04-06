package com.example.demo.ControllerImplementation;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Constants.Constants;
import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.controller.BookController;
import com.example.demo.controller.CustomerController;
import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.BookScanningRequest;
import com.example.demo.dto.BookScanningResponseDto;

import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Book;

import com.example.demo.repository.BookRepository;
import com.mongodb.MongoException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class BookControllerImpl implements BookController {

	private static Logger logger = Logger.getLogger(BookControllerImpl.class.getName());
	@Autowired
	private static final BookMapper MAPPER = BookMapper.INSTANCE;
	private final BookRepository bookRepository;
	private int numberOfBooks = 0;

	public BookControllerImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	};

	@Override
	public ResponseEntity<BookScanningResponseDto> scanBook(
			@Validated BookScanningRequest bookScanningRequest) {

		BookScanningResponseDto bookScanningResponseDto = new BookScanningResponseDto(
				bookScanningRequest);
		
		String validateMessage = validateNullControl(bookScanningRequest);
		
		if (!validateMessage.isEmpty()) {
			bookScanningResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			bookScanningResponseDto.setResponseMessage(validateMessage);
			return new ResponseEntity<>(bookScanningResponseDto, HttpStatus.BAD_REQUEST);
		}	
		try
		{
			BookInfoDto bookInfoDto = new BookInfoDto(bookScanningRequest);
			Book book = MAPPER.toBookModel(bookInfoDto);
			Book response = new Book();
			if (isBookExist(bookScanningRequest.getPublicId())) {
				book.setNumberOf(bookInfoDto.getNumberOf() + numberOfBooks);
				response = bookRepository.save(book);
			}else {
				response = bookRepository.insert(book);
			}
			bookScanningResponseDto.setResponseMessage(bookInfoDto.getNumberOf() + Constants.BOOK_SCANNING_SUCCESS + book.getNumberOf());
			bookScanningResponseDto.setResponseCode(RegisterResponseCode.SCANNED);
			return new ResponseEntity<>(bookScanningResponseDto, HttpStatus.OK);
		}
		catch (MongoException ex)
		{
			bookScanningResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			bookScanningResponseDto.setResponseMessage(ex.getMessage());
			return new ResponseEntity<>(bookScanningResponseDto, HttpStatus.EXPECTATION_FAILED);
		}
		catch (Exception ex)
		{
			bookScanningResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			bookScanningResponseDto.setResponseMessage(ex.getMessage());
			return new ResponseEntity<>(bookScanningResponseDto, HttpStatus.EXPECTATION_FAILED);
		}


	}

	public boolean isBookExist(String publicId) {
		Book response = getBookDetail(publicId);
	    if(response == null) {
	    	return false;
	    }
		numberOfBooks = response.getNumberOf();
		return true;
	}
	
	@Override
	public Book getBookDetail(String publicId) {
		Optional<Book> response = bookRepository.findById(publicId);
		if(response.isEmpty()) {
			return null;
		}
		return response.get();
	}
	
	@Override
	@Transactional
	public Book updateBook(Book book) {
		
		Book response = new Book();
		try
		{
			response = bookRepository.save(book);
		}
		catch (MongoException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw ex;
		}
		return response;
	}

	public String validateNullControl(BookScanningRequest input) {
		if (input.getPublicId() == null || input.getPublicId().isEmpty()) {
			return Constants.ERROR_MESSAGE_BOOK_PUBLICID_EMPTY;
		}
		if (input.getName() == null || input.getName().isEmpty()) {
			return Constants.ERROR_MESSAGE_BOOK_NAME_EMPTY;
		}
		if (input.getAuthor() == null || input.getAuthor().isEmpty()) {
			return Constants.ERROR_MESSAGE_BOOK_AUTHOR_EMPTY;
		}
		if (input.getPrice() <= 0) {
			return Constants.ERROR_MESSAGE_PRICE_CONTROL;
		}
		return "";
	}

}
