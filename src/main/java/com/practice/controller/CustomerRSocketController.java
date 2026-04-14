package com.practice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.practice.model.Address;
import com.practice.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerRSocketController {

	private final CustomerController customerController;

	public CustomerRSocketController(CustomerController customerController) {
		this.customerController = customerController;
	}

	/*
	 * rsc --stream --route=getCustomers  --debug tcp://localhost:7000
	 */
	@MessageMapping("getCustomers")
	public Flux<Customer> getCustomers() {
		return customerController.getCustomers();
	}

	/*
	 * rsc --request --route=getCustomer --data=1  --debug tcp://localhost:7000
	 */
	@MessageMapping("getCustomer")
	public Mono<Customer> getCustomer(Long id) {
		return customerController.getCustomer(id);
	}
	

	/*
	 * rsc --request --route=createAddress --data={\"street\":\"123 Main St.\",\"city\":\"Big City\",\"state\":\"New State\",\"zip\":\"123456\"}  --debug tcp://localhost:7000
	 */
	@MessageMapping("createAddress")
	public Mono<Address> createAddress(Address address) {
		return customerController.createAddress(address);
	}

	/*
	 * rsc --request --route=deleteAddress --data=7  --debug tcp://localhost:7000
	 */
	@MessageMapping("deleteAddress")
	public Mono<Long> deleteAddress(Long id) {
		return customerController.deleteAddress(id);
	}
	/*
	 * rsc --request --route=createCustomer --data={\"firstName\":\"B.J.\",\"lastName\":\"Johnston\",\"addressId\":1}  --debug tcp://localhost:7000
	 */
	@MessageMapping("createCustomer")
	public Mono<Customer> createCustomer(Customer customer) {
		return customerController.createCustomer(customer);
	}

	/*
	 * rsc --request --route=deleteCustomer --data=7  --debug tcp://localhost:7000
	 */
	@MessageMapping("deleteCustomer")
	public Mono<Long> deleteCustomer(Long id) {
		return customerController.deleteCustomer(id);
	}
}

