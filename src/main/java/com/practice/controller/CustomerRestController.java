package com.practice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.CustomerEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

	private final CustomerDatabaseController customerDatabaseController;

	public CustomerRestController(CustomerDatabaseController customerRepository) {
		this.customerDatabaseController = customerRepository;
	}

	@GetMapping("/{id}")
	public Mono<CustomerEntity> getCustomer(@PathVariable Long id) {
		return customerDatabaseController.getCustomer(id);
	}

	@GetMapping(path="", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<CustomerEntity> getCustomers() {
		return customerDatabaseController.getCustomers();
	}
	
	@PutMapping("")
	public Mono<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) {
		return customerDatabaseController.createCustomer(customer);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Long> deleteCustomer(@PathVariable Long id) {
		return customerDatabaseController.deleteCustomer(id);
	}
}

