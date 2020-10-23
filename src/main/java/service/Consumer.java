package service;

import dto.ItemDTO;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import utils.JsonDeserializer;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Consumer {

    @Inject private JsonDeserializer deserializer;

    private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    private Connection connection = null;
    private Session session = null;
    private Queue queue = null;

    private List<ItemDTO> items = new ArrayList<>();
    private String queueName = "QUEUE";

    public void start() {
        try {
            if (connection == null) {
                connection = factory.createQueueConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                queue = session.createQueue(queueName);

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
                    System.err.println("Message read");
                }
            } catch (JMSException e) {
//            context.setRollbackOnly();
            }
        }
    }

    public List<ItemDTO> getItems() {
        return items;
    }
}
