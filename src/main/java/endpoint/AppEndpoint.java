package endpoint;

import lombok.SneakyThrows;
import service.Consumer;
import service.JSFModel;

import javax.inject.Inject;
import javax.jms.Message;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.time.temporal.ChronoUnit;

@ServerEndpoint(value = "/push", encoders = MessageEncoder.class)
public class AppEndpoint {

    @Inject private JSFModel jsfModel;
    @Inject private Consumer consumer;

    public AppEndpoint() {
        System.err.println("class loaded " + this.getClass());
    }

    @OnOpen
    public void onOpen(Session session) {
        System.err.printf("Session opened, id: %s%n", session.getId());
        try {
            session.getBasicRemote().sendText("Server connected!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @SneakyThrows
    @OnMessage
    public void onMessage(String message, Session session) {
        System.err.printf("Message received. Session id: %s Message: %s%n",
                session.getId(), message);
        while (session.isOpen()) {
            Thread.sleep(10000);

            if (!jsfModel.getItems().equals(consumer.getItems())) {
                jsfModel.setItems(consumer.getItems());
                try {
                    session.getBasicRemote().sendObject(consumer.getItems());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        }
//        try {
//            session.getBasicRemote().sendText(String.format("We received your message: %s%n", message));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        System.err.printf("Session closed with id: %s%n", session.getId());
    }
}