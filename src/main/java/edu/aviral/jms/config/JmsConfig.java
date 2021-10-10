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
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

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
	
/**	
 	Note: We can remove the JmsTemplate Bean here, because it will be automatically given by
 	spring. And since we have already provided our custom ActiveMQ and MessageConverter containing
 	the @Bean annotation, those beans will override the default configurations
 	and inject them into the JmsTemplate provided by spring.
 	
	//previously JmsTemplate was using the internal activeMQ instance by spring boot
	// this Bean will override the default JmsTemplate.
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory());
		// we must specify the custom message converter to the jmsTemplate.
		jmsTemplate.setMessageConverter(customMessageConverter());
		return jmsTemplate;
//		return new JmsTemplate(connectionFactory());
	}
	*/
	// App will work even if DefaultJmsListenerContainerFactory is not defined.
	@Bean
	public DefaultJmsListenerContainerFactory containerFactory() {
		DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
		containerFactory.setConnectionFactory(connectionFactory());
		containerFactory.setConcurrency("1-1");
		return containerFactory;
	}
	
	
	// ##########  Spring by default makes available a SimpleMessageConvertor ############ //
	
	@Bean
	public MessageConverter customMessageConverter() {
		
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_myType");
		// if sender and the receiver rely on the same message converter bean,
		// then this typeId must be same.
		return converter;
	}
	
	
	
	
	
	
	
	
	
	
	
}
