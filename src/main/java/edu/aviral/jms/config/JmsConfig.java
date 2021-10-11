package edu.aviral.jms.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.xstream.XStreamMarshaller;

import edu.aviral.jms.custommessagelistener.BookOrderProcessingMessageListener;
import edu.aviral.jms.pojo.Book;
import edu.aviral.jms.pojo.BookOrder;
import edu.aviral.jms.pojo.Customer;

@EnableJms
@Configuration
public class JmsConfig implements JmsListenerConfigurer{
	
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
	
	
	/**
		Note: these are listener configurations
	 */
	// App will work even if DefaultJmsListenerContainerFactory is not defined.
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
//		factory.setConcurrency("1-1");
//		factory.setMessageConverter(jacksonJmsMessageConverter());
		factory.setMessageConverter(xmlMarshallingMesssgeConverter());
		return factory;
	}
	
	
	// ##########  Spring by default makes available a SimpleMessageConvertor ############ //
	
//	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
// Serializes/deserializes messages to/from JSON format. 		
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_myType");
		// if sender and the receiver rely on the same message converter bean,
		// then this typeId must be same.
		return converter;
	}
	
	
	@Bean
	public MessageConverter xmlMarshallingMesssgeConverter() {
		MarshallingMessageConverter converter = new MarshallingMessageConverter(xmlMarshaller());
		converter.setTargetType(MessageType.TEXT);
		return converter;
	}

	@Bean //other marshallers can also be used, here we are using xstreme
	public XStreamMarshaller xmlMarshaller() {
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setSupportedClasses(Book.class, Customer.class, BookOrder.class);
		return marshaller;
	}

	
	// ************** Custom message listener ***********************//
	
	@Bean
	public BookOrderProcessingMessageListener jmsMessageListener() {
		return new BookOrderProcessingMessageListener();
	}
	
	@Override
	public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
		SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
		endpoint.setMessageListener(jmsMessageListener());
		endpoint.setDestination("book.order.processed.queue");
		endpoint.setConcurrency("1");
		endpoint.setId("book-processed-id");
		endpoint.setSubscription("my-subs");
		registrar.setContainerFactory(jmsListenerContainerFactory());
		registrar.registerEndpoint(endpoint, jmsListenerContainerFactory());
	}
	
}
