package edu.aviral.jms.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import edu.aviral.jms.pojo.BookOrder;
import edu.aviral.jms.pojo.ProcessedBookOrder;

@Service
public class WarehouseProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseProcessingService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void processOrder(BookOrder bookOrder){
        ProcessedBookOrder order = 
        		new ProcessedBookOrder(bookOrder, new Date(), new Date());
        
        LOGGER.info("Sent to order process queue");
        jmsTemplate.convertAndSend("book.order.processed.queue", order);
    }

}