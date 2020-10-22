package service;

import dto.ItemDTO;
import org.jboss.ejb3.annotation.ResourceAdapter;
import utils.JsonDeserializer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;

//@Singleton
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destinationLookup", propertyValue="QUEUE"),
//        @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "MSGConnectionFactory"),
//        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "QUEUE"),
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
//        @ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge")
})
@ResourceAdapter("activemq-ra")
public class ShopMessageListener implements MessageListener {

//    @Resource MessageDrivenContext context;
    @Inject JsonDeserializer deserializer;
    @Inject MSGConnectionFactory factory;
    private String queueName = "QUEUE";
    private Connection connection = null;
    private Session session = null;

    @PostConstruct
    private void init() {
        try {
            connection = factory.createQueueConnection();
            connection.start();

            System.err.println("CONNECTION DONE!");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                String text = msg.getText();
                List<ItemDTO> deserialize = deserializer.deserialize(text);
                System.out.println("MEEEEEEEESAAAAAGE RECEEEEEIIIIVIED! : " + deserialize);
            }
        } catch (JMSException e) {
//            context.setRollbackOnly();
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
