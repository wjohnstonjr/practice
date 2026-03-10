package com.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.NameAndState;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/nameandstate")
public class NameAndStateController {
	
	private final CustomerDatabaseController customerDatabaseController;
	
	public NameAndStateController(CustomerDatabaseController customerRepository) {
		this.customerDatabaseController = customerRepository;
	}
	
	@GetMapping("")
	public Flux<NameAndState> getCustomers() {
		return customerDatabaseController.getNameAndStates();
	}
}

