package com.example.demo.utils;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

import org.mapstruct.factory.Mappers;

import com.example.demo.dto.MonthlyOrderStatisticResponse;
import com.example.demo.mapper.OrderMapper;

public class LocalMonths {

	public static final Class<LocalMonths> INSTANCE = LocalMonths.class;
	private static final DateFormatSymbols uk_dfs = new DateFormatSymbols(Locale.UK);
	
	public String[] getLocalMOnths() {
		String[] months = uk_dfs.getMonths();
		return months;
	}
	
}
