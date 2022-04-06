package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderInfoDto {

	private String createdUserName;
	private List<OrderDetail> numberOfBookList;
	private LocalDateTime orderTime;
	private String id;
	private int yearNum;
	
	public OrderInfoDto(OrderRequest orderRequest) {
		this.createdUserName = orderRequest.getCreatedUserName();
		this.orderTime = LocalDateTime.now();
		this.numberOfBookList = orderRequest.getOrderedBookList();
		this.id = UUID.randomUUID().toString();
		this.yearNum = this.orderTime.getYear();
	}
	
}
