package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MonthlyOrderStatisticRequest {
	
	private String userName;
	private int yearNum;
	
}
