package com.example.demo.repository;



import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.util.Assert;

import com.example.demo.model.Book;
import com.example.demo.model.Order;


@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends MongoRepository<Order, String>{
	
	//List<Customer> getBy_isActive(@Param("active") Boolean isActive);
	@Query("{'createdUserName' : { $eq: ?0},'yearNum' : { $eq: ?1}}")
	List<Order> findCustomersYearlyOrders(String userName, int yearNum);
	
	@Query("{'createdUserName' : { $eq: ?0}}")
	List<Order> findCustomersAllOrders(String userName, Pageable pageable);
	
	@Query("{'orderTime' : { $gte: ?0},'orderTime' : { $lte: ?1}}")
	List<Order> findByDateInterval(LocalDateTime startDate, LocalDateTime endDate);
	
}
