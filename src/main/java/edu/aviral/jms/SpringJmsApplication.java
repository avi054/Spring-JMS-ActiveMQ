package edu.aviral.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@SpringBootApplication
public class SpringJmsApplication implements CommandLineRunner {
	
	@Value("${ORDER_QUEUE}")
	private String orderQueue;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	public static void main(String[] args) {
//		ConfigurableApplicationContext context = 
				SpringApplication.run(SpringJmsApplication.class, args);
//				.close();

//		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

//		jmsTemplate.convertAndSend("order-queue", "Hello World");
	}

	@Override
	public void run(String... args) throws Exception {
		
		jmsTemplate.convertAndSend(orderQueue, "Hello Again!");
	}

}
