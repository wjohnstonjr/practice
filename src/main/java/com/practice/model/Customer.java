package com.practice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@org.springframework.data.annotation.Id
	private Long id;
	private String firstName;
	private String lastName;
	private Long addressId;
	private transient Address address;

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
