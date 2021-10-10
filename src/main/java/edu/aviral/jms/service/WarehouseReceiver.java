package edu.aviral.jms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import edu.aviral.jms.pojo.BookOrder;

@Service
public class WarehouseReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseReceiver.class);

	@JmsListener(destination = "book.order.queue")
	public void receiveMessage(BookOrder bookOrder) {

		LOGGER.info("Received a message...");
		LOGGER.info("Received message : {}", bookOrder);
	}
}
