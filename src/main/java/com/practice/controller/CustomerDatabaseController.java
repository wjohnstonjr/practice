package com.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.practice.data.AddressEntity;
import com.practice.data.AddressRepository;
import com.practice.data.CustomerEntity;
import com.practice.data.CustomerRepository;
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
	public Mono<CustomerEntity> getCustomer(@PathVariable Long id) {
		return customerRepository.findById(id);
	}

	@Transactional
	public Flux<CustomerEntity> getCustomers() {
		return customerRepository.findAll();
	}
	
	@Transactional
	public Mono<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) {
		return customerRepository.save(customer);
	}
	
	@Transactional
	public Mono<CustomerEntity> updateCustomer(@RequestBody CustomerEntity customer) {
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
	public Flux<AddressEntity> getAddresses() {
		return addressRepository.findAll();
	}
	
	@Transactional
	public Mono<AddressEntity> getAddress(@PathVariable Long id) {
		return addressRepository.findById(id);
	}

	@Transactional
	public Mono<AddressEntity> createAddress(@RequestBody AddressEntity address) {
		return addressRepository.save(address);
	}

	@Transactional
	public Mono<AddressEntity> updateAddress(@RequestBody AddressEntity address) {
		return addressRepository.save(address);
	}

	@Transactional
	public Mono<Long> deleteAddress(@RequestBody Long id) {
		return addressRepository.deleteById(id.longValue());
	}
}
