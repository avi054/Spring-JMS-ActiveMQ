package edu.aviral.jms.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	//determine 'queue name' and on which Message Queue Server(MOM) this queue resides
//	@JmsListener(destination = "${ORDER_QUEUE}", containerFactory = "warehouseFactory")
	@JmsListener(destination = "${ORDER_QUEUE}")
	public void receiveMessage(String message) {
		System.out.println("Order received : " + message);
	}
}
