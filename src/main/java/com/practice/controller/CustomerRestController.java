package com.practice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.Customer;

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
	public Mono<Customer> getCustomer(@PathVariable Long id) {
		return customerDatabaseController.getCustomer(id);
	}

	@GetMapping("")
	public Flux<Customer> getCustomers() {
		return customerDatabaseController.getCustomers();
	}
	
	@PutMapping("")
	public Mono<Customer> createCustomer(@RequestBody Customer customer) {
		return customerDatabaseController.createCustomer(customer);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Long> deleteCustomer(@PathVariable Long id) {
		return customerDatabaseController.deleteCustomer(id);
	}
}

