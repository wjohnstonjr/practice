package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.practice.model.Address;
import com.practice.model.AddressMapper;
import com.practice.model.Customer;
import com.practice.model.CustomerMapper;
import com.practice.model.NameAndState;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {
	@Autowired
	private CustomerDatabaseController customerDatabaseController;

	@Transactional
	public Mono<Customer> getCustomer(@PathVariable Long id) {
		return customerDatabaseController.getCustomer(id).map(entity -> CustomerMapper.INSTANCE.entityToDto(entity));
	}

	@Transactional
	public Flux<Customer> getCustomers() {
		return customerDatabaseController.getCustomers().map(entity -> CustomerMapper.INSTANCE.entityToDto(entity));
	}
	
	@Transactional
	public Mono<Customer> createCustomer(@RequestBody Customer customer) {
		return customerDatabaseController.createCustomer(CustomerMapper.INSTANCE.dtoToEntity(customer)).map(entity -> CustomerMapper.INSTANCE.entityToDto(entity));
	}
	
	@Transactional
	public Mono<Long> deleteCustomer(@PathVariable Long id) {
		return customerDatabaseController.deleteCustomer(id.longValue());
	}

	@Transactional
	public Flux<NameAndState> getNameAndStates() {
		return customerDatabaseController.getNameAndStates();
	}

	@Transactional
	public Flux<Address> getAddresses() {
		return customerDatabaseController.getAddresses().map(entity -> AddressMapper.INSTANCE.entityToDto(entity));
	}
	
	@Transactional
	public Mono<Address> getAddress(@PathVariable Long id) {
		return customerDatabaseController.getAddress(id).map(entity -> AddressMapper.INSTANCE.entityToDto(entity));
	}

	@Transactional
	public Mono<Address> createAddress(@RequestBody Address address) {
		return customerDatabaseController.createAddress(AddressMapper.INSTANCE.dtoToEntity(address)).map(entity -> AddressMapper.INSTANCE.entityToDto(entity));
	}

	@Transactional
	public Mono<Long> deleteAddress(@RequestBody Long id) {
		return customerDatabaseController.deleteAddress(id.longValue());
	}
}
