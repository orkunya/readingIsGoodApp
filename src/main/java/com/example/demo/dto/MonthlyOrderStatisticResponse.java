package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MonthlyOrderStatisticResponse {
	
	private String monthName = "";
	private int totalOrderCount= 0;
	private int totalBookCount = 0;
	private double totalPurchasedAmount = 0;
	
}
