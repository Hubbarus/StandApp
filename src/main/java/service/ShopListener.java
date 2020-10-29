package service;

import dto.ItemDTO;;
import lombok.extern.java.Log;
import utils.JsonDeserializer;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.logging.Level;

@Log
@Dependent
public class ShopListener implements MessageListener {

    @Inject private JsonDeserializer deserializer;
    @Inject private JSFModel jsfModel;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                String text = msg.getText();
                List<ItemDTO> items = deserializer.deserialize(text);

                log.log(Level.INFO, String.format("Message from %s was read", Consumer.QUEUE_NAME));

                jsfModel.update(items);
            } else {
                log.log(Level.SEVERE, String.format("Not supported message has been received : %s", message));
            }
        } catch (JMSException e) {
            log.log(Level.SEVERE, "Exception in ShopListener: " + e.getMessage());
        }
    }
}