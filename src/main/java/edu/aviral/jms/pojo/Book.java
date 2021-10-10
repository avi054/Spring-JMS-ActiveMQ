package edu.aviral.jms.pojo;

import lombok.Getter;

@Getter
public class Book {

	private final String bookId;
	private final String title;

	public Book(String bookId, String title) {
		super();
		this.bookId = bookId;
		this.title = title;
	}
}
