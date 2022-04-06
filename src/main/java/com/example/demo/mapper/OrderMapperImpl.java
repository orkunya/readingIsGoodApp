package com.example.demo.mapper;

import com.example.demo.dto.BookInfoDto;
import com.example.demo.dto.CustomerInfoDto;
import com.example.demo.model.Book;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.dto.CustomerRegisterRequest;
import com.example.demo.dto.OrderInfoDto;

public class OrderMapperImpl extends OrderMapper {

	@Override
	public Order toOrderModel(OrderInfoDto orderInfoDto) {
	  if(orderInfoDto == null) {
		  return null;
	  }
	  Order order = new Order();
	  order.setId(orderInfoDto.getId());
	  order.setCreatedUserName(orderInfoDto.getCreatedUserName());
	  order.setNumberOfBookList(orderInfoDto.getNumberOfBookList());
	  order.setOrderTime(orderInfoDto.getOrderTime());
	  order.setYearNum(orderInfoDto.getYearNum());
	  return order;
	  
	}
}
