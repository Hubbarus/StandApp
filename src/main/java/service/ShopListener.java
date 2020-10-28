package service;

import dto.ItemDTO;;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import utils.JsonDeserializer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.logging.Level;

@Log
public class ShopListener implements MessageListener {

    private final JsonDeserializer deserializer;
    private final JSFModel jsfModel;

    public ShopListener(JsonDeserializer deserializer, JSFModel jsfModel) {
        this.deserializer = deserializer;
        this.jsfModel = jsfModel;
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                String text = msg.getText();
                List<ItemDTO> items = deserializer.deserialize(text);

                log.log(Level.INFO, String.format("Message from %s was read", Consumer.QUEUE_NAME));

                if (!items.equals(jsfModel.getItems())) {
                    jsfModel.setItems(items);
                    jsfModel.update();
                }
            } else {
                log.log(Level.SEVERE, String.format("Not supported message has been received : %s", message));
            }
        } catch (JMSException e) {
            log.log(Level.SEVERE, "Exception in ShopListener: " + e.getMessage());
        }
    }
}