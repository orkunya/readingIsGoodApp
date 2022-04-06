package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderRequest {
	
	// private static final long serialVersionUID = 989898343434343434L;
	
	private String createdUserName;
	private List<OrderDetail> orderedBookList;
	
}
