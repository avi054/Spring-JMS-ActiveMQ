package edu.aviral.jms.custommessagelistener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookOrderProcessingMessageListener implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(BookOrderProcessingMessageListener.class);
	
	@Override		//its a jms message not a text message
	public void onMessage(Message message) {
		try {
			String text = ((TextMessage)message).getText();
			logger.info(text);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}

//Step1: Create a custom message listener, not using @JmsListener this time.
//Step2: Register the custom message listener
