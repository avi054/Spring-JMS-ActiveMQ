package edu.aviral.jms.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Customer {

	private final String customerId;
	private final String fullName;

	@JsonCreator
	public Customer(@JsonProperty("customerId") String customerId,
					@JsonProperty("fullName") String fullName) {
		super();
		this.customerId = customerId;
		this.fullName = fullName;
	}
}
