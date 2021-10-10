package edu.aviral.jms.pojo;

import lombok.Getter;

@Getter
public class BookOrder {

	private final String bookOrderId;
	private final Book book;
	private final Customer customer;

	public BookOrder(String bookOrderId, Book book, Customer customer) {
		super();
		this.bookOrderId = bookOrderId;
		this.book = book;
		this.customer = customer;
	}
}
