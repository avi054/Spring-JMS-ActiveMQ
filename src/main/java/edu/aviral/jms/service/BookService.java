package edu.aviral.jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import edu.aviral.jms.pojo.BookOrder;

@Service
public class BookService {

	private static final String BOOK_QUEUE = "book.order.queue";
	
	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendOrder(BookOrder message) {
		jmsTemplate.convertAndSend(BOOK_QUEUE, message);
		System.out.println("message sent: " + message.getBookOrderId());
		
		
//		jmsTemplate.send(BOOK_QUEUE, new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				return null;
//			}
//		});
		
		
//		jmsTemplate.send(BOOK_QUEUE, session -> {
//			return null;
//		});
	}
}
