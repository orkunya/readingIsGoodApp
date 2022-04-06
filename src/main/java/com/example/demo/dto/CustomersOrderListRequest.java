package com.example.demo.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CustomersOrderListRequest {

	private String userName;
	private int pageNumber;
	private int pageSize;
	
}
