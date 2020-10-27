package service;

import dto.ItemDTO;
import exception.NotSupportedMessageException;
import exception.QueueException;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import utils.JsonDeserializer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;
import java.util.logging.Level;

@Singleton
@Startup
@Log
public class Consumer {

    private static final String QUEUE_NAME = "QUEUE";

    @Inject private JsonDeserializer deserializer;
    @Inject private JSFModel wrapper;

    private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    private Connection connection = null;
    private Session session = null;

    @PostConstruct
    public void start() throws QueueException {
        try {
            if (connection == null) {
                connection = factory.createQueueConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Queue queue = session.createQueue(QUEUE_NAME);
                System.err.println("ActiveMQ connection established");

                ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(queue);
                consumer.setMessageListener(new ShopListener());
            }
        } catch (JMSException e) {
            log.log(Level.SEVERE, "Error in init() of ActiveMQ connection");
            throw new QueueException("Error in init() of ActiveMQ connection", e);
        }
    }

    @PreDestroy
    public void close() throws QueueException {
        try {
            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            log.log(Level.SEVERE, "Error while closing Consumer");
            throw new QueueException("Error while closing Consumer", e);
        }

    }

    private class ShopListener implements MessageListener {

        @SneakyThrows
        @Override
        public void onMessage(Message message) {
            try {
                if (message instanceof TextMessage) {
                    TextMessage msg = (TextMessage) message;
                    String text = msg.getText();
                    List<ItemDTO> items = deserializer.deserialize(text);

                    log.log(Level.INFO, String.format("Message from %s was read", QUEUE_NAME));
                    System.err.printf("Message from %s was read\n", QUEUE_NAME);

                    if (!items.equals(wrapper.getItems())) {
                        wrapper.setItems(items);
                        wrapper.update();
                    }
                } else {
                    log.log(Level.SEVERE, String.format("Not supported message has been received : %s", message));
                    throw new NotSupportedMessageException(
                            String.format("Not supported message has been received : %s", message));
                }
            } catch (JMSException e) {
                System.err.println();
                log.log(Level.SEVERE, "Exception in ShopListener");
                throw new QueueException("Exception in ShopListener", e);
            }
        }
    }
}
