package service;

import dto.ItemDTO;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import utils.JsonDeserializer;

import javax.ejb.EJB;
import javax.ejb.Singleton;
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

    @EJB private JsonDeserializer deserializer;

    private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
            ActiveMQConnection.DEFAULT_USER,
            ActiveMQConnection.DEFAULT_PASSWORD,
            ActiveMQConnection.DEFAULT_BROKER_URL);

    private List<ItemDTO> items = new ArrayList<>();
    private String queueName = "QUEUE";

    public void start() {
        try {
            Connection connection = factory.createQueueConnection();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new ShopListener());
            connection.start();
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
