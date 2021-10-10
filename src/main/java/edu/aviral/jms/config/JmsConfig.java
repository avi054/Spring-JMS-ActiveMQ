package edu.aviral.jms.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class JmsConfig {
	
	@Value("${activeMq.username}")
	private String userName;
	
	@Value("${activeMq.password}")
	private String password;
	
	@Value("${activeMq.brokerUrl}")
	private String brokerUrl;

//	@Bean
//	public JmsListenerContainerFactory<?> warehouseFactory(ConnectionFactory factory,
//			DefaultJmsListenerContainerFactoryConfigurer configurer) {
//
//		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
//		configurer.configure(containerFactory, factory);
//		return containerFactory;
//	}
	
	
	// #####################   ActiveMQ Config ######################################//
	
	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory factory = 
				new ActiveMQConnectionFactory(userName, password, brokerUrl);
		return factory;
	}
	
	
	//previously JmsTemplate was using the internal activeMQ instance by spring boot
	
	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(connectionFactory());
	}
	
	// App will work even if DefaultJmsListenerContainerFactory is not defined.
	@Bean
	public DefaultJmsListenerContainerFactory containerFactory() {
		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
		containerFactory.setConnectionFactory(connectionFactory());
		containerFactory.setConcurrency("1-1");
		return containerFactory;
	}
	
}
