//package service;
//
//import dto.ItemDTO;
//import org.apache.activemq.ActiveMQConnection;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import utils.JsonDeserializer;
//
//import javax.ejb.EJB;
//import javax.ejb.LocalBean;
//import javax.ejb.MessageDriven;
//import javax.ejb.Stateful;
//import javax.inject.Inject;
//import javax.jms.Connection;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageConsumer;
//import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;
//import javax.jms.Queue;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//import java.util.List;
//
//@MessageDriven
//public class Consumer {
//
//    @EJB private JsonDeserializer deserializer;
////    @Inject ShopMessageListener listener;
////    private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
////            ActiveMQConnection.DEFAULT_USER,
////            ActiveMQConnection.DEFAULT_PASSWORD,
////            ActiveMQConnection.DEFAULT_BROKER_URL);
//    @Inject private MSGConnectionFactory factory;
//    private String queueName = "QUEUE";
//
//    public List<ItemDTO> receive() {
//        try {
//            Connection connection = factory.createQueueConnection();
//            connection.start();
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Queue queue = session.createQueue(queueName);
//
//            MessageConsumer consumer = session.createConsumer(queue);
//            consumer.setMessageListener(new MsgListener());
////            TextMessage receive = (TextMessage) consumer.receive();
////            String text = receive.getText();
////
////            List<ItemDTO> items = deserializer.deserialize(text);
////            System.out.println(items);
//
//            consumer.close();
//            session.close();
//            connection.close();
//            return null;
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private class MsgListener implements MessageListener {
//
//        @Override
//        public void onMessage(Message message) {
//            try {
//            if (message instanceof TextMessage) {
//                TextMessage msg = (TextMessage) message;
//                String text = msg.getText();
//                List<ItemDTO> deserialize = deserializer.deserialize(text);
//                System.err.println("MEEEEEEEESAAAAAGE RECEEEEEIIIIVIED! : " + deserialize);
//            }
//                System.err.println("HOOORAAAY!");
//        } catch (JMSException e) {
////            context.setRollbackOnly();
//        }
//        }
//    }
//}
