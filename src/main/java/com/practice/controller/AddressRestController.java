package com.practice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.AddressEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/address")
public class AddressRestController {

	private final CustomerDatabaseController customerDatabaseController;

	public AddressRestController(CustomerDatabaseController customerRepository) {
		this.customerDatabaseController = customerRepository;
	}

	@GetMapping("/{id}")
	public Mono<AddressEntity> getAddress(@PathVariable Long id) {
		return customerDatabaseController.getAddress(id);
	}

	@GetMapping("")
	public Flux<AddressEntity> getAddresses() {
		return customerDatabaseController.getAddresses();
	}
	
	@PutMapping("")
	public Mono<AddressEntity> createAddress(@RequestBody AddressEntity address) {
		return customerDatabaseController.createAddress(address);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Long> deleteAddress(@PathVariable Long id) {
		return customerDatabaseController.deleteAddress(id);
	}
}

