package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.practice.model.Address;
import com.practice.model.Customer;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CustomerGraphQLController {
	
	@Autowired
	private CustomerController customerController;
	
    @QueryMapping
    public Flux<Customer> customer(@Argument Long id, DataFetchingEnvironment env) {
    	if ((id != null) && (id != 0L)) {
    		return customerController.getCustomer(id).flux();
    	} else {
    		return customerController.getCustomers();
    	}
    }

    @SchemaMapping
    public Mono<Address> address(Customer customer) {
        return customerController.getAddress(customer.getAddressId());
    }
    
    @MutationMapping
	public Mono<Customer> createCustomer(@Argument Customer customer, DataFetchingEnvironment env) {
		return customerController.createCustomer(customer);
	}
    
    @MutationMapping
	public Mono<Long> deleteCustomer(@Argument Long id, DataFetchingEnvironment env) {
		return customerController.deleteCustomer(id);
	}
    
    @MutationMapping
	public Mono<Address> createAddress(@Argument Address address, DataFetchingEnvironment env) {
		return customerController.createAddress(address);
	}
    
    @MutationMapping
	public Mono<Long> deleteAddress(@Argument Long id, DataFetchingEnvironment env) {
		return customerController.deleteAddress(id);
	}
}