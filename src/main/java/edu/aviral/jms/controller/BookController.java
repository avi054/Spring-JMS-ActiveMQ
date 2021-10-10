package edu.aviral.jms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.aviral.jms.pojo.Book;
import edu.aviral.jms.pojo.BookOrder;
import edu.aviral.jms.pojo.Customer;
import edu.aviral.jms.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	List<Book> books = Arrays.asList(
			new Book("bookId-1", "Let us C"), 
			new Book("bookId-2", "Java J2EE"),
			new Book("bookId-3", "Spring Framework"));

	List<Customer> customers = Arrays.asList(
			new Customer("steve-rog", "Steve Rogers"),
			new Customer("tony-05", "Tony Stark"),
			new Customer("thor_101", "Thor"));

	@GetMapping("/process/{customerId}")
	public ResponseEntity<BookOrder> orderBook(@PathVariable String customerId) {
		Book book;
		Customer customer;

		switch (customerId) {
		case "steve-rog":
			customer = customers.get(0);
			break;
		case "tony-05":
			customer = customers.get(1);
			break;
		case "thor_101":
			customer = customers.get(2);
			break;
		default:
			customer = customers.get(0);
		}

		int nextInt = new Random().nextInt(3);
		book = books.get(nextInt);

		BookOrder bookOrder = new BookOrder(UUID.randomUUID().toString(), book, customer);
		bookService.sendOrder(bookOrder);

		return ResponseEntity.ok(bookOrder);
	}

}
