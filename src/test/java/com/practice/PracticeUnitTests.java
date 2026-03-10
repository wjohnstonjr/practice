package com.practice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import com.google.protobuf.Empty;
import com.practice.application.PracticeConfiguration;
import com.practice.controller.AddressRestController;
import com.practice.controller.CustomerRSocketController;
import com.practice.controller.CustomerRestController;
import com.practice.controller.CustomergRpcController;
import com.practice.controller.GraphQLController;
import com.practice.model.Address;
import com.practice.model.Customer;

import io.grpc.stub.StreamObserver;

@DataR2dbcTest()
@SpringBootConfiguration
@Import(PracticeConfiguration.class)
class PracticeUnitTests {
	@Autowired
	private CustomerRestController customerRestController;
	@Autowired
	private AddressRestController addressRestController;
	@Autowired
	private GraphQLController graphQLController;
	@Autowired
	private CustomergRpcController customergRpcController;
	@Autowired
	private CustomerRSocketController customerRSocketController;

	@Test
	void restTest() {
		/*
		 * REST testing
		 */
		assertNotNull(customerRestController);
		assertNotNull(addressRestController);
		List<Customer> customers = customerRestController.getCustomers().collectList().block();
		Address address = new Address("123 Main Street", "Big City", "New State", "12345");
		Address createdAddress = addressRestController.createAddress(address).block();
		address.setId(createdAddress.getId());
		assertEquals(address, createdAddress);
		Customer customer = new Customer("First", "Last");
		customer.setAddressId(createdAddress.getId());
		Customer createdCustomer = customerRestController.createCustomer(customer).block();
		customer.setId(createdCustomer.getId());
		assertEquals(customer, createdCustomer);
		assertEquals(customers.size() + 1, customerRestController.getCustomers().collectList().block().size());
		assertEquals(1L, customerRSocketController.deleteCustomer(createdCustomer.getId()).block());
		assertEquals(1L, addressRestController.deleteAddress(createdAddress.getId()).block());
	}

	@Test
	void graphqlTest() {
		/*
		 * GraphQL testing
		 */
		assertNotNull(graphQLController);
		List<Customer> customers = graphQLController.customer(null, null).collectList().block();
		Address address = new Address("123 Main Street", "Big City", "New State", "12345");
		Address createdAddress = graphQLController.createAddress(address, null).block();
		address.setId(createdAddress.getId());
		assertEquals(address, createdAddress);
		Customer customer = new Customer("First", "Last");
		customer.setAddressId(createdAddress.getId());
		Customer createdCustomer = graphQLController.createCustomer(customer, null).block();
		customer.setId(createdCustomer.getId());
		assertEquals(customer, createdCustomer);
		assertEquals(customers.size() + 1, graphQLController.customer(null, null).collectList().block().size());
		assertEquals(1L, graphQLController.deleteCustomer(createdCustomer.getId(), null).block());
		assertEquals(1L, graphQLController.deleteAddress(createdAddress.getId(), null).block());
	}

	@Test
	void rsocketTest() {
		/*
		 * RSocket testing
		 */
		assertNotNull(customerRSocketController);
		List<Customer> customers = customerRSocketController.getCustomers().collectList().block();
		Address address = new Address("123 Main Street", "Big City", "New State", "12345");
		Address createdAddress = customerRSocketController.createAddress(address).block();
		address.setId(createdAddress.getId());
		assertEquals(address, createdAddress);
		Customer customer = new Customer("First", "Last");
		customer.setAddressId(createdAddress.getId());
		Customer createdCustomer = customerRSocketController.createCustomer(customer).block();
		customer.setId(createdCustomer.getId());
		assertEquals(customer, createdCustomer);
		assertEquals(customers.size() + 1, customerRSocketController.getCustomers().collectList().block().size());
		assertEquals(1L, customerRSocketController.deleteCustomer(createdCustomer.getId()).block());
		assertEquals(1L, customerRSocketController.deleteAddress(createdAddress.getId()).block());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void grpcTest() {
		/*
		 * gRPC testing
		 */
		assertNotNull(customergRpcController);
		assertNotNull(customerRestController);
		List<Customer> customers = customerRestController.getCustomers().collectList().block();
		
		/*
		 * Create address
		 */
		StreamObserver<com.practice.gRPC.CustomerOuterClass.Address> addressObserver = mock(StreamObserver.class);
		com.practice.gRPC.CustomerOuterClass.Address grpcAddress = com.practice.gRPC.CustomerOuterClass.Address.newBuilder()
				.setStreet("123 Main Street").setCity("Big City").setState("New State").setZip("12345").build();
		ArgumentCaptor<com.practice.gRPC.CustomerOuterClass.Address> addressCaptor = ArgumentCaptor.forClass(com.practice.gRPC.CustomerOuterClass.Address.class);
		customergRpcController.createAddress(grpcAddress, addressObserver);
		verify(addressObserver).onNext(addressCaptor.capture());
	    verify(addressObserver).onCompleted();
	    verify(addressObserver, never()).onError(any(Throwable.class));
		com.practice.gRPC.CustomerOuterClass.Address createdGrpcAddress = addressCaptor.getValue();
		grpcAddress = grpcAddress.toBuilder().setId(createdGrpcAddress.getId()).build();
		assertEquals(grpcAddress, createdGrpcAddress);
	    
		
		/*
		 * Create Customer
		 */
		StreamObserver<com.practice.gRPC.CustomerOuterClass.Customer> customerObserver = mock(StreamObserver.class);
		com.practice.gRPC.CustomerOuterClass.Customer grpcCustomer = com.practice.gRPC.CustomerOuterClass.Customer.newBuilder()
				.setFirstName("First").setLastName("Last").setAddress(createdGrpcAddress).build();
		ArgumentCaptor<com.practice.gRPC.CustomerOuterClass.Customer> customerCaptor = ArgumentCaptor.forClass(com.practice.gRPC.CustomerOuterClass.Customer.class);
		customergRpcController.createCustomer(grpcCustomer, customerObserver);
		verify(customerObserver).onNext(customerCaptor.capture());
	    verify(customerObserver).onCompleted();
	    verify(customerObserver, never()).onError(any(Throwable.class));
		com.practice.gRPC.CustomerOuterClass.Customer createdGrpcCustomer = customerCaptor.getValue();
		grpcCustomer = grpcCustomer.toBuilder().setId(createdGrpcCustomer.getId()).build();
		assertEquals(grpcCustomer, createdGrpcCustomer);

		/*
	     * Get customers
	     */
		customerObserver = mock(StreamObserver.class);
		customergRpcController.getCustomers(Empty.getDefaultInstance(), customerObserver);
		verify(customerObserver, times(customers.size() + 1)).onNext(any(com.practice.gRPC.CustomerOuterClass.Customer.class));
	    verify(customerObserver).onCompleted();
	    verify(customerObserver, never()).onError(any(Throwable.class));

		/*
		 * Delete customer
		 */
	    StreamObserver<com.google.protobuf.Int32Value> countObserver = mock(StreamObserver.class);
	    com.google.protobuf.Int32Value id = com.google.protobuf.Int32Value.newBuilder().setValue(createdGrpcCustomer.getId()).build();
	    ArgumentCaptor<com.google.protobuf.Int32Value> countCaptor = ArgumentCaptor.forClass(com.google.protobuf.Int32Value.class);
		customergRpcController.deleteCustomer(id, countObserver);
		verify(countObserver).onNext(countCaptor.capture());
	    verify(countObserver).onCompleted();
	    verify(countObserver, never()).onError(any(Throwable.class));
	    assertEquals(1, countCaptor.getValue().getValue());

		/*
		 * Delete address
		 */
	    countObserver = mock(StreamObserver.class);
		id = com.google.protobuf.Int32Value.newBuilder().setValue(createdGrpcAddress.getId()).build();
		countCaptor = ArgumentCaptor.forClass(com.google.protobuf.Int32Value.class);
		customergRpcController.deleteAddress(id, countObserver);
		verify(countObserver).onNext(countCaptor.capture());
	    verify(countObserver).onCompleted();
	    verify(countObserver, never()).onError(any(Throwable.class));
	    assertEquals(1, countCaptor.getValue().getValue());
	}
}
