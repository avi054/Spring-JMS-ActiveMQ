package edu.aviral.jms.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	@Autowired
	private JmsTemplate jmsTemplate;

	private static final Logger logger = LoggerFactory.getLogger(Sender.class);
	
	public void sendMessage(String destination, String message) {
		// destination = messageQueueName
		// it will automatically create a message queue if not available.
		
		logger.info("Message sent: queueName = {}, message = {} ", destination, message);
		
		jmsTemplate.convertAndSend(destination, message);
	}
}
