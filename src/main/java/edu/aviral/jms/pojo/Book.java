package edu.aviral.jms.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Book {

	private final String bookId;
	private final String title;

	@JsonCreator
	public Book(@JsonProperty("bookId") String bookId, 
				@JsonProperty("title") String title) {
		super();
		this.bookId = bookId;
		this.title = title;
	}
}
