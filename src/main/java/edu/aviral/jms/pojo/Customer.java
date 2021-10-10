package edu.aviral.jms.pojo;

import lombok.Getter;

@Getter
public class Customer {

	private final String customerId;
	private final String fullName;

	public Customer(String customerId, String fullName) {
		super();
		this.customerId = customerId;
		this.fullName = fullName;
	}
}
