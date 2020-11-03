package endpoint;

import lombok.extern.java.Log;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;

/**
 * App's main endpoint class.
 */
@ServerEndpoint("/push")
@Log
public class Endpoint {

    @OnMessage
    public String onMessage(Session session, String message) {
        log.log(Level.INFO, "Socket received message : " + message);
        return message;
    }

    @OnOpen
    public void onOpen(Session session) {
        log.log(Level.INFO, "Server socket connected");
    }

    @OnClose
    public void onClose() {
        log.log(Level.INFO, "Server socket closed");
    }

    @OnError
    public void onError(Throwable e) {
        log.log(Level.INFO, "Exception in Server Socket : " + e.getMessage());
    }
}
