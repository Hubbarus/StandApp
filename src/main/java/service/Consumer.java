package service;

import dto.ItemDTO;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import utils.JsonDeserializer;
import utils.UpdateChecker;

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
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class Consumer {

    @Inject private JsonDeserializer deserializer;
    @Inject private JSFModel wrapper;

    private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    private Connection connection = null;
    private Session session = null;
    private Queue queue = null;

    private List<ItemDTO> items = new ArrayList<>();
    private String queueName = "QUEUE";

    @PostConstruct
    public void init() {
        start();
    }

    public void start() {
        try {
            if (connection == null) {
                connection = factory.createQueueConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                queue = session.createQueue(queueName);
                System.err.println("ActiveMQ connection established");

                ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(queue);
                consumer.setMessageListener(new ShopListener());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private class ShopListener implements MessageListener {

        @Override
        public void onMessage(Message message) {
            try {
                if (message instanceof TextMessage) {
                    TextMessage msg = (TextMessage) message;
                    String text = msg.getText();
                    items = deserializer.deserialize(text);
                    System.err.println("Message was read");

//                    if (UpdateChecker.checkIfUpdated(items, wrapper.getItems())) {
//                        wrapper.setItems(items);
//                        wrapper.setUpdated(true);
//                        wrapper.update();
//                    }
                }
            } catch (JMSException e) {
                System.err.println("Exception in ShopListener");
            }
        }
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    @PreDestroy
    public void close() {
        try {
            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            System.err.println("Error while closing Consumer");
        }

    }
}
