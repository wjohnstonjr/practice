package com.practice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.Address;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/address")
public class AddressRestController {

	private final CustomerController customerController;

	public AddressRestController(CustomerController customerController) {
		this.customerController = customerController;
	}

	@GetMapping("/{id}")
	public Mono<Address> getAddress(@PathVariable Long id) {
		return customerController.getAddress(id);
	}

	@GetMapping("")
	public Flux<Address> getAddresses() {
		return customerController.getAddresses();
	}
	
	@PutMapping("")
	public Mono<Address> createAddress(@RequestBody Address address) {
		return customerController.createAddress(address);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Long> deleteAddress(@PathVariable Long id) {
		return customerController.deleteAddress(id);
	}
}

