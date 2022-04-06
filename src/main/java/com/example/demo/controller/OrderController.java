package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.dto.BookScanningRequest;
import com.example.demo.dto.BookScanningResponseDto;
import com.example.demo.dto.CustomersOrderListRequest;
import com.example.demo.dto.MonthlyOrderStatisticDto;
import com.example.demo.dto.MonthlyOrderStatisticRequest;
import com.example.demo.dto.MonthlyOrderStatisticResponse;
import com.example.demo.dto.OrderByDateIntervalRequest;
import com.example.demo.dto.OrderByIdRequest;
import com.example.demo.dto.OrderInfoDto;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponseDto;
import com.example.demo.model.Order;
import java.util.Optional;

import lombok.ToString;


@Validated
@RequestMapping(value= "/readingisgood/order/v0")
public interface OrderController {

	@PostMapping("/order")
	ResponseEntity<OrderResponseDto> giveOrder(@Validated @RequestBody OrderRequest request);
	
	@PostMapping("/monthlyStatistic")
	ResponseEntity<List<MonthlyOrderStatisticResponse>> monthlyStatisticByCustomer(@Validated  @RequestBody MonthlyOrderStatisticRequest request);
	
	@PostMapping("/customersList")
	ResponseEntity<List<Order>> customersAllList(@Validated @RequestBody CustomersOrderListRequest request);
	
	@PostMapping("/listById")
	Optional<Order> listById(@Validated @RequestBody OrderByIdRequest request);
	
	@PostMapping("/listByDateInterval")
	ResponseEntity<List<Order>> listByDateInterval(@Validated @RequestBody OrderByDateIntervalRequest request);

	
}
