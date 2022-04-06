package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import com.example.demo.Constants.RegisterResponseCode;
import com.example.demo.dto.CustomerRegisteringResponseDto;
import com.example.demo.dto.OrderDetail;

import lombok.Data;

@Document(collection = "order")
@Data
public class Order {

	@Id
	@Field(name = "id")
	@Indexed(unique = true)
	private String id;
	
	@Field(name = "createdUserName")
	private String createdUserName;
	
	@Field(name = "numberOfBookList")
	private List<OrderDetail> numberOfBookList;
	
	@Field(name = "orderTime")
	private LocalDateTime orderTime;

	@Field(name = "yearNum")
	private int yearNum;

}
