package endpoint;

import lombok.extern.java.Log;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

import javax.websocket.OnError;
import java.util.logging.Level;

@PushEndpoint("/push")
@Log
public class Endpoint {

    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(String message) {
        log.log(Level.INFO, "Socket received message : " + message);
        return message;
    }

    @OnOpen
    public void onOpen() {
        log.log(Level.INFO, "Server socket connected");
    }

    @OnClose
    public void onClose() {
        log.log(Level.INFO, "Server socket closed");
    }

    @OnError
    public void onError(Exception e) {
        log.log(Level.INFO, "Exception in Server Socket : " + e.getMessage());
    }
}
