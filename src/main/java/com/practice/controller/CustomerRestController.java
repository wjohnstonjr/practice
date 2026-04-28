package com.practice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

	private final CustomerController customerController;

	public CustomerRestController(CustomerController customerController) {
		this.customerController = customerController;
	}

	@GetMapping("/{id}")
	public Mono<Customer> getCustomer(@PathVariable Long id) {
		return customerController.getCustomer(id).publishOn(Schedulers.boundedElastic()).map(customer -> {
			customer.setAddress(customerController.getAddress(customer.getAddressId()).block());
			return customer;
		});
	}

	@GetMapping(path="") //, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> getCustomers() {
		return customerController.getCustomers().publishOn(Schedulers.boundedElastic()).map(customer -> {
			customer.setAddress(customerController.getAddress(customer.getAddressId()).block());
			return customer;
		});
	}
	
	@PutMapping("")
	public Mono<Customer> createCustomer(@RequestBody Customer customer) {
		return customerController.createCustomer(customer);
	}
	
	@PostMapping("")
	public Mono<Customer> updateCustomer(@RequestBody Customer customer) {
		return customerController.updateCustomer(customer);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Long> deleteCustomer(@PathVariable Long id) {
		return customerController.deleteCustomer(id);
	}
}

