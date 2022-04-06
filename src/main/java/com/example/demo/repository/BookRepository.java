package com.example.demo.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.demo.model.Book;


@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookRepository extends MongoRepository<Book, String>{
	
	//List<Customer> getBy_isActive(@Param("active") Boolean isActive);

}
