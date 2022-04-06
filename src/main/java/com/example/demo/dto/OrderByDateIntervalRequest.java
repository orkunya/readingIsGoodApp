package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderByDateIntervalRequest {
	
	private LocalDateTime startDate;
	private LocalDateTime endDate;

}
