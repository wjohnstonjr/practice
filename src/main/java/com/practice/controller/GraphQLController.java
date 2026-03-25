package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.practice.model.AddressEntity;
import com.practice.model.CustomerEntity;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class GraphQLController {
	
	@Autowired
	private CustomerDatabaseController customerDatabaseController;
	
    @QueryMapping
    public Flux<CustomerEntity> customer(@Argument Long id, DataFetchingEnvironment env) {
    	if ((id != null) && (id != 0L)) {
    		return customerDatabaseController.getCustomer(id).flux();
    	} else {
    		return customerDatabaseController.getCustomers();
    	}
    }

    @SchemaMapping
    public Mono<AddressEntity> address(CustomerEntity customer) {
        return customerDatabaseController.getAddress(customer.getAddressId());
    }
    
    @MutationMapping
	public Mono<CustomerEntity> createCustomer(@Argument CustomerEntity customer, DataFetchingEnvironment env) {
		return customerDatabaseController.createCustomer(customer);
	}
    
    @MutationMapping
	public Mono<Long> deleteCustomer(@Argument Long id, DataFetchingEnvironment env) {
		return customerDatabaseController.deleteCustomer(id);
	}
    
    @MutationMapping
	public Mono<AddressEntity> createAddress(@Argument AddressEntity address, DataFetchingEnvironment env) {
		return customerDatabaseController.createAddress(address);
	}
    
    @MutationMapping
	public Mono<Long> deleteAddress(@Argument Long id, DataFetchingEnvironment env) {
		return customerDatabaseController.deleteAddress(id);
	}
}