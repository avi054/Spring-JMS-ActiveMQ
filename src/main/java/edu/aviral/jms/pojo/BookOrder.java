package edu.aviral.jms.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BookOrder {

	private final String bookOrderId;
	private final Book book;
	private final Customer customer;

	// Jackson has to know the order in which the fields need to be passed
	// these are POJOs with nested type
	// we are not mentioning the default constructor here

	/*
	Cannot construct instance of `edu.aviral.jms.pojo.Book` 
	(no Creators, like default constructor, exist): cannot deserialize from Object value 
	(no delegate- or property-based Creator)
	 at [Source: (String)"{}"; line: 1, column: 63] (through reference chain: 
	 					edu.aviral.jms.pojo.BookOrder["book"])
	*/
	
	@JsonCreator  // mentioning a creator
	public BookOrder(@JsonProperty("bookOrderId") String bookOrderId,
					@JsonProperty("book") Book book,
					@JsonProperty("customer") Customer customer) {
		super();
		this.bookOrderId = bookOrderId;
		this.book = book;
		this.customer = customer;
	}
}
