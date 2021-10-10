package edu.aviral.jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import edu.aviral.jms.pojo.BookOrder;

@Service
public class BookOrderSender {

	private static final String BOOK_QUEUE = "book.order.queue";
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(BookOrderSender.class);

	public void sendOrder(BookOrder message) {
		
		logger.info("Order send with orderId: {}", message.getBookOrderId());
		jmsTemplate.convertAndSend(BOOK_QUEUE, message);
		
		
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
