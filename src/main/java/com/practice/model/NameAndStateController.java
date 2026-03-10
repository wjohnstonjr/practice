package com.practice.model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.controller.CustomerDatabaseController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/nameandstate")
public class NameAndStateController {
	
	private final CustomerDatabaseController customerDatabaseController;
	
	public NameAndStateController(CustomerDatabaseController customerRepository) {
		this.customerDatabaseController = customerRepository;
	}
	
	@GetMapping("")
	public Flux<NameAndState> getNameAndStates() {
		return customerDatabaseController.getNameAndStates();
	}
}

