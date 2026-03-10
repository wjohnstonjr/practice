package com.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.practice.model.Address;
import com.practice.model.AddressRepository;
import com.practice.model.Customer;
import com.practice.model.CustomerRepository;
import com.practice.model.NameAndState;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerDatabaseController {
	private final CustomerRepository customerRepository;
	private final AddressRepository addressRepository;

	public CustomerDatabaseController(CustomerRepository customerRepository, AddressRepository addressRepository) {
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
	}

	@Transactional
	public Mono<Customer> getCustomer(@PathVariable Long id) {
		return customerRepository.findById(id);
	}

	@Transactional
	public Flux<Customer> getCustomers() {
		return customerRepository.findAll();
	}
	
	@Transactional
	public Mono<Customer> createCustomer(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Transactional
	public Mono<Long> deleteCustomer(@PathVariable Long id) {
		return customerRepository.deleteById(id.longValue());
	}

	@Transactional
	public Flux<NameAndState> getNameAndStates() {
		return customerRepository.findNameAndState();
	}

	@Transactional
	public Flux<Address> getAddresses() {
		return addressRepository.findAll();
	}
	
	@Transactional
	public Mono<Address> getAddress(@PathVariable Long id) {
		return addressRepository.findById(id);
	}

	@Transactional
	public Mono<Address> createAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}

	@Transactional
	public Mono<Long> deleteAddress(@RequestBody Long id) {
		return addressRepository.deleteById(id.longValue());
	}
}
