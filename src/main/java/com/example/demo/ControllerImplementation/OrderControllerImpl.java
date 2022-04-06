package com.example.demo.ControllerImplementation;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Constants.Constants;
import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.controller.BookController;
import com.example.demo.controller.OrderController;
import com.example.demo.controller.CustomerController;
import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.BookScanningRequest;
import com.example.demo.dto.BookScanningResponseDto;
import com.example.demo.dto.CustomersOrderListRequest;
import com.example.demo.dto.MonthlyOrderStatisticDto;
import com.example.demo.dto.MonthlyOrderStatisticRequest;
import com.example.demo.dto.MonthlyOrderStatisticResponse;
import com.example.demo.dto.OrderByDateIntervalRequest;
import com.example.demo.dto.OrderByIdRequest;
import com.example.demo.dto.OrderDetail;
import com.example.demo.dto.OrderInfoDto;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponseDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.utils.LocalMonths;
import com.mongodb.MongoException;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class OrderControllerImpl implements OrderController {

	private static Logger logger = Logger.getLogger(OrderControllerImpl.class.getName());
	@Autowired
	private static final OrderMapper MAPPER = OrderMapper.INSTANCE;
	private final BookController bookController;
	private final CustomerController customerController;
	private final OrderRepository orderRepository;
	private int numberOfBooks = 0;
	private static final LocalMonths localMonths = new LocalMonths();
	private final String[] months = localMonths.getLocalMOnths();
	private HashMap<Integer, MonthlyOrderStatisticResponse> monthlyOrderStatisticHashmap = new HashMap<Integer, MonthlyOrderStatisticResponse>();

	public OrderControllerImpl(OrderRepository orderRepository, BookController bookController, CustomerController customerController) {
		this.orderRepository = orderRepository;
		this.bookController = bookController;
		this.customerController = customerController;
	};

	@Override
	@Transactional
	public ResponseEntity<OrderResponseDto> giveOrder(@Validated OrderRequest orderRequest) {

		OrderResponseDto orderResponseDto = new OrderResponseDto(orderRequest);

		String validateMessage = validateInput(orderRequest);

		if (!validateMessage.isEmpty()) {
			orderResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			orderResponseDto.setResponseMessage(validateMessage);
			return new ResponseEntity<>(orderResponseDto, HttpStatus.BAD_REQUEST);
		}

		try {
			for (OrderDetail order : orderRequest.getOrderedBookList()) {
				Book book = bookController.getBookDetail(order.getBookPublicId());
				book.setNumberOf(book.getNumberOf() - order.getNumberOfBooksOrdered());
				Book updatedBook = bookController.updateBook(book);
				order.setBookPrice(book.getPrice());
			}

			OrderInfoDto orderInfoDto = new OrderInfoDto(orderRequest);
			Order order = MAPPER.toOrderModel(orderInfoDto);
			Order response = orderRepository.insert(order);
			orderResponseDto.setId(response.getId());
			orderResponseDto.setResponseMessage(Constants.ORDER_SUCCESS + "No: " + response.getId());
			orderResponseDto.setResponseCode(RegisterResponseCode.ORDERED);
			return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
		} catch (MongoException ex) {
			orderResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			orderResponseDto.setResponseMessage(ex.getMessage());
			return new ResponseEntity<>(orderResponseDto, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception ex) {
			orderResponseDto.setResponseCode(RegisterResponseCode.FAILED);
			orderResponseDto.setResponseMessage(ex.getMessage());
			return new ResponseEntity<>(orderResponseDto, HttpStatus.EXPECTATION_FAILED);
		}

	}

	public HashMap<Integer, MonthlyOrderStatisticResponse> getMonthlyOrderStatisticHashmap() {
		HashMap<Integer, MonthlyOrderStatisticResponse> monthlyOrderStatisticHashmap = new HashMap<>();
		String[] months = this.months;
		int monthNum = 1;
		for (String month : months) {
			MonthlyOrderStatisticResponse monthlyOrderStatisticResponse = new MonthlyOrderStatisticResponse();
			monthlyOrderStatisticResponse.setMonthName(month);
			monthlyOrderStatisticHashmap.put(monthNum, monthlyOrderStatisticResponse);
			monthNum++;
			if (monthNum == 13) {
				break;
			}
		}
		return monthlyOrderStatisticHashmap;
	}

	public void categorizeOrderToMonth(Order order) {
		Integer monthNum = order.getOrderTime().getMonthValue();
		MonthlyOrderStatisticResponse currentOrder = monthlyOrderStatisticHashmap.get(monthNum);
		int currentOrderCount = currentOrder.getTotalOrderCount();
		int currentBookCount = currentOrder.getTotalBookCount();
		double currentPurchasedAmount = currentOrder.getTotalPurchasedAmount();
		monthlyOrderStatisticHashmap.get(monthNum).setTotalOrderCount(currentOrderCount + 1);
		int orderBookCount = 0;
		double orderBookTotalPrice = 0;
		for (OrderDetail orderdetail : order.getNumberOfBookList()) {
			orderBookCount = orderBookCount + (orderdetail.getNumberOfBooksOrdered());
			orderBookTotalPrice = orderdetail.getBookPrice() * orderdetail.getNumberOfBooksOrdered();
		}
		monthlyOrderStatisticHashmap.get(monthNum).setTotalBookCount(orderBookCount + currentBookCount);
		monthlyOrderStatisticHashmap.get(monthNum)
				.setTotalPurchasedAmount(orderBookTotalPrice + currentPurchasedAmount);
	}

	public static int compareMonths(Order order1, Order order2) {
		int month1 = order1.getOrderTime().getMonthValue(), month2 = order2.getOrderTime().getMonthValue();
		if (month1 > month2) {
			return 1;
		} else
			return -1;
	}

	@Override
	public Optional<Order> listById(OrderByIdRequest orderByIdRequest) {
		Optional<Order> response = orderRepository.findById(orderByIdRequest.getId());
		return response;
	}

	@Override
	public ResponseEntity<List<Order>> listByDateInterval(OrderByDateIntervalRequest orderByDateIntervalRequest) {
		List<Order> orderList = orderRepository.findByDateInterval(orderByDateIntervalRequest.getStartDate(),
				orderByDateIntervalRequest.getEndDate());

		return new ResponseEntity<>(orderList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Order>> customersAllList(CustomersOrderListRequest customersOrderListRequest) {
		Pageable pageable = PageRequest.of(customersOrderListRequest.getPageNumber(),
				customersOrderListRequest.getPageSize());
		List<Order> pageOrderList = orderRepository.findCustomersAllOrders(customersOrderListRequest.getUserName(),
				pageable);

		return new ResponseEntity<>(pageOrderList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<MonthlyOrderStatisticResponse>> monthlyStatisticByCustomer(
			MonthlyOrderStatisticRequest monthlyOrderStatisticRequest) {
		Customer customer = customerController.getCustomer(monthlyOrderStatisticRequest.getUserName());
		List<MonthlyOrderStatisticResponse> resultList = new ArrayList<>();
		if (customer == null) {
			return new ResponseEntity<>(resultList, HttpStatus.NO_CONTENT);
		}
		
		this.monthlyOrderStatisticHashmap = getMonthlyOrderStatisticHashmap();
		List<Order> orderList = orderRepository.findCustomersYearlyOrders(monthlyOrderStatisticRequest.getUserName(),
				monthlyOrderStatisticRequest.getYearNum());
	
		orderList.stream().sorted((o1, o2) -> compareMonths(o1, o2)).forEach(item -> categorizeOrderToMonth(item));

		
		monthlyOrderStatisticHashmap.forEach((k, v) -> {
			resultList.add(v);
		});

		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}

	public int numberOfBooksInStock(String publicId) {
		Book book = bookController.getBookDetail(publicId);
		if (book == null) {
			return 0;
		}
		return book.getNumberOf();
	}

	public String validateInput(OrderRequest input) {
		if (input.getCreatedUserName() == null || input.getCreatedUserName().isEmpty()) {
			return Constants.ERROR_MESSAGE_CREATED_USER_EMPTY;
		}
		if (input.getOrderedBookList() == null || input.getOrderedBookList().size() == 0) {
			return Constants.ERROR_MESSAGE_ORDER_LIST_EMPTY;
		}
		for (OrderDetail order : input.getOrderedBookList()) {
			if (order.getNumberOfBooksOrdered() < 1) {
				return Constants.ERROR_MESSAGE_ORDER_INCORRECT_QUANTITY + order.getBookPublicId();
			}
			int numberOfBooksInStock = numberOfBooksInStock(order.getBookPublicId());
			if (numberOfBooksInStock < order.getNumberOfBooksOrdered()) {
				return Constants.ERROR_MESSAGE_ORDER_BOOK_NUMBER + order.getBookPublicId();
			}
		}
		return "";
	}

}
