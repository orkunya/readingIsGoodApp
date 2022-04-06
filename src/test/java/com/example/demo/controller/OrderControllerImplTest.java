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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.ControllerImplementation.BookControllerImpl;
import com.example.demo.ControllerImplementation.CustomerControllerImpl;
import com.example.demo.ControllerImplementation.OrderControllerImpl;
import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.BookScanningRequest;
import com.example.demo.dto.BookScanningResponseDto;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.dto.CustomerRegisterRequest;
import com.example.demo.dto.CustomerRegisteringResponseDto;
import com.example.demo.dto.MonthlyOrderStatisticRequest;
import com.example.demo.dto.MonthlyOrderStatisticResponse;
import com.example.demo.dto.OrderDetail;
import com.example.demo.dto.OrderInfoDto;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponseDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;

import org.junit.Assert;
import org.junit.Before;

public class OrderControllerImplTest {
	OrderControllerImpl orderControllerImplTest;
	OrderRequest orderRequest = new OrderRequest();
	private static final OrderMapper MAPPER = OrderMapper.INSTANCE;

	@Mock
	OrderRepository orderRepositoryMock;

	@Mock
	BookController bookControllerMock;
	
	@Mock
	CustomerController customerControllerMock;
	
	Order requestOrder;
	Book requestBook = new Book();
	Customer requestCustomer = new Customer();

	@BeforeEach
	public void setUp() {
		orderRequest.setCreatedUserName("orkunya");
		List<Order> orderList = new ArrayList<Order>();
		List<OrderDetail> orderDetailList = new ArrayList();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setBookPrice(20);
		orderDetail.setBookPublicId("200");
		orderDetail.setNumberOfBooksOrdered(4);
		orderDetailList.add(orderDetail);
		Order order2 = new Order();
		order2.setCreatedUserName("test1");
		order2.setId("dere");
		order2.setOrderTime(LocalDateTime.now().plusMonths(1));
		order2.setYearNum(2022);
		order2.setNumberOfBookList(orderDetailList);
		Order order3 = new Order();
		order3.setCreatedUserName("test2");
		order3.setId("dere");
		order3.setOrderTime(LocalDateTime.now().plusMonths(2));
		order3.setYearNum(2022);
		order3.setNumberOfBookList(orderDetailList);
		Order order4 = new Order();
		order4.setCreatedUserName("test3");
		order4.setId("dere");
		order4.setOrderTime(LocalDateTime.now());
		order4.setYearNum(2022);
		order4.setNumberOfBookList(orderDetailList);
		orderList.add(order3);
		orderList.add(order2);
		orderList.add(order4);
		
		orderRequest.setOrderedBookList(orderDetailList);

		OrderInfoDto bookInfoDto = new OrderInfoDto(orderRequest);
		requestOrder = MAPPER.toOrderModel(bookInfoDto);

		orderRepositoryMock = PowerMockito.mock(OrderRepository.class);
		bookControllerMock = PowerMockito.mock(BookController.class);
		customerControllerMock = PowerMockito.mock(CustomerController.class);

		orderControllerImplTest = new OrderControllerImpl(orderRepositoryMock,bookControllerMock,customerControllerMock);
		
		requestBook.setName("Harry Potter");
		requestBook.setNumberOf(1);
		requestBook.setPrice(20);
		requestBook.setPublicId("100");
		
		requestCustomer.setUserName("orkunya");
		
		PowerMockito.doReturn(requestOrder).when(orderRepositoryMock).insert(any(Order.class));	
		PowerMockito.doReturn(requestBook).when(bookControllerMock).getBookDetail(any(String.class));
		PowerMockito.doReturn(requestBook).when(bookControllerMock).updateBook(any(Book.class));
		PowerMockito.doReturn(requestCustomer).when(customerControllerMock).getCustomer(any(String.class));

	}

	@Test
	public void scanBookTest() {


		ResponseEntity<OrderResponseDto> response = orderControllerImplTest
				.giveOrder(orderRequest);

		Assert.assertEquals(response.getBody().getCreatedUserName(), "orkunya");
		
		requestBook.setNumberOf(5);
		PowerMockito.doReturn(requestBook).when(bookControllerMock).getBookDetail(any(String.class));
		
		ResponseEntity<OrderResponseDto> response2 = orderControllerImplTest
				.giveOrder(orderRequest);
		Assert.assertEquals(response.getBody().getCreatedUserName(), "orkunya");

	}

	@Test
	public void monthlyStatisticByCustomerTest() {
		
		MonthlyOrderStatisticRequest monthlyOrderStatisticRequest = new MonthlyOrderStatisticRequest();
		monthlyOrderStatisticRequest.setUserName("orkunya");
		monthlyOrderStatisticRequest.setYearNum(2022);
		
		ResponseEntity<List<MonthlyOrderStatisticResponse>> responseList = orderControllerImplTest
				.monthlyStatisticByCustomer(monthlyOrderStatisticRequest);
		
		Assert.assertEquals(responseList.getBody().get(0).getMonthName(), "January");
		
	}


}
