package com.practice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.practice.model.AddressEntity;
import com.practice.model.CustomerEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerRSocketController {

	private final CustomerDatabaseController customerDatabaseController;

	public CustomerRSocketController(CustomerDatabaseController customerRepository) {
		this.customerDatabaseController = customerRepository;
	}

	/*
	 * rsc --stream --route=getCustomers  --debug tcp://localhost:7000
	 */
	@MessageMapping("getCustomers")
	public Flux<CustomerEntity> getCustomers() {
		return customerDatabaseController.getCustomers();
	}

	/*
	 * rsc --request --route=getCustomer --data=1  --debug tcp://localhost:7000
	 */
	@MessageMapping("getCustomer")
	public Mono<CustomerEntity> getCustomer(Long id) {
		return customerDatabaseController.getCustomer(id);
	}
	

	/*
	 * rsc --request --route=createAddress --data={\"street\":\"123 Main St.\",\"city\":\"Big City\",\"state\":\"New State\",\"zip\":\"123456\"}  --debug tcp://localhost:7000
	 */
	@MessageMapping("createAddress")
	public Mono<AddressEntity> createAddress(AddressEntity address) {
		return customerDatabaseController.createAddress(address);
	}

	/*
	 * rsc --request --route=deleteAddress --data=7  --debug tcp://localhost:7000
	 */
	@MessageMapping("deleteAddress")
	public Mono<Long> deleteAddress(Long id) {
		return customerDatabaseController.deleteAddress(id);
	}
	/*
	 * rsc --request --route=createCustomer --data={\"firstName\":\"B.J.\",\"lastName\":\"Johnston\",\"addressId\":1}  --debug tcp://localhost:7000
	 */
	@MessageMapping("createCustomer")
	public Mono<CustomerEntity> createCustomer(CustomerEntity customer) {
		return customerDatabaseController.createCustomer(customer);
	}

	/*
	 * rsc --request --route=deleteCustomer --data=7  --debug tcp://localhost:7000
	 */
	@MessageMapping("deleteCustomer")
	public Mono<Long> deleteCustomer(Long id) {
		return customerDatabaseController.deleteCustomer(id);
	}
}

