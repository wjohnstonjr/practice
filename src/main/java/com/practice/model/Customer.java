package com.practice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Customer {

	private Long id;
	private String firstName;
	private String lastName;
	private Long addressId;
	private Address address;

	protected Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.id = null;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressId = null;
		this.address = null;
	}
}
