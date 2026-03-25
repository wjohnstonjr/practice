package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import com.practice.gRPC.CustomerOuterClass.Address;
import com.practice.gRPC.CustomerOuterClass.Customer;
import com.practice.gRPC.Customer_InterfaceGrpc.Customer_InterfaceImplBase;
import com.practice.model.AddressEntity;
import com.practice.model.CustomerEntity;

import io.grpc.stub.StreamObserver;
import reactor.core.scheduler.Schedulers;

@GrpcService
public class CustomergRpcController extends Customer_InterfaceImplBase {
	@Autowired
	private CustomerDatabaseController customerDatabaseController;

	private Address getAddress(CustomerEntity customer) {
		Address address = Address.newBuilder().build();
		if (customer.getAddressId() != null) {
			AddressEntity customerAddress = customerDatabaseController.getAddress(customer.getAddressId())
					.block();
			if (customerAddress != null) {
				address = Address.newBuilder().setId(customerAddress.getId().intValue())
						.setStreet(customerAddress.getStreet()).setCity(customerAddress.getCity())
						.setState(customerAddress.getState()).setZip(customerAddress.getZip()).build();
			}
		}
		return address;
	}

	/*
	 * grpcurl -plaintext localhost:9090
	 * com.practice.gRPC.Customer_Interface.getCustomers
	 */
	@Override
	public void getCustomers(com.google.protobuf.Empty request, StreamObserver<Customer> responseObserver) {
		customerDatabaseController.getCustomers().publishOn(Schedulers.boundedElastic()).doOnNext(customer -> {
			responseObserver.onNext(
					Customer.newBuilder().setId(customer.getId().intValue()).setFirstName(customer.getFirstName())
							.setLastName(customer.getLastName()).setAddress(getAddress(customer)).build());
		}).doOnComplete(() -> responseObserver.onCompleted()).blockLast();
	}

	@Override
	public void createAddress(com.practice.gRPC.CustomerOuterClass.Address request,
			io.grpc.stub.StreamObserver<com.practice.gRPC.CustomerOuterClass.Address> responseObserver) {
		AddressEntity newAddress = new AddressEntity(request.getStreet(),
				request.getCity(), request.getState(), request.getZip());
		customerDatabaseController.createAddress(newAddress).publishOn(Schedulers.boundedElastic()).doOnNext(address -> {
			responseObserver.onNext(
					Address.newBuilder().setId(address.getId().intValue()).setStreet(address.getStreet())
							.setCity(address.getCity()).setState(address.getState()).setZip(address.getZip()).build());
			responseObserver.onCompleted();
		}).block();
	}

	@Override
	public void deleteAddress(com.google.protobuf.Int32Value request,
			io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value> responseObserver) {
		customerDatabaseController.deleteAddress((long) request.getValue()).publishOn(Schedulers.boundedElastic()).doOnNext(count -> {
			responseObserver.onNext(
					com.google.protobuf.Int32Value.newBuilder().setValue(count.intValue()).build());
			responseObserver.onCompleted();
		}).block();
	}

	@Override
	public void createCustomer(com.practice.gRPC.CustomerOuterClass.Customer request,
			io.grpc.stub.StreamObserver<com.practice.gRPC.CustomerOuterClass.Customer> responseObserver) {
		CustomerEntity newCustomer = new CustomerEntity(request.getFirstName(),
				request.getLastName());
		newCustomer.setAddressId((long)request.getAddress().getId());
		customerDatabaseController.createCustomer(newCustomer).publishOn(Schedulers.boundedElastic()).doOnNext(customer -> {
			responseObserver.onNext(
					Customer.newBuilder().setId(customer.getId().intValue()).setFirstName(customer.getFirstName())
							.setLastName(customer.getLastName()).setAddress(getAddress(customer)).build());
			responseObserver.onCompleted();
		}).block();
	}

	@Override
	public void deleteCustomer(com.google.protobuf.Int32Value request,
			io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value> responseObserver) {
		customerDatabaseController.deleteCustomer((long) request.getValue()).publishOn(Schedulers.boundedElastic()).doOnNext(count -> {
			responseObserver.onNext(
					com.google.protobuf.Int32Value.newBuilder().setValue(count.intValue()).build());
			responseObserver.onCompleted();
		}).block();
	}
}
