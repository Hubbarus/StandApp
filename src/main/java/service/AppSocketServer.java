package service;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

@Startup
@Singleton
public class AppSocketServer extends WebSocketServer {

    private static int TCP_PORT = 8888;

    private Set<WebSocket> connections;

    public AppSocketServer() {
        super(new InetSocketAddress(TCP_PORT));
        connections = new HashSet<>();
    }

    @PostConstruct
    public void init() {
        this.start();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        connections.add(webSocket);
        System.err.println("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        connections.remove(webSocket);
        System.err.println("Closed connection to " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        System.err.println("Message from client: " + message);
        for (WebSocket sock : connections) {
            sock.send("hi");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception e) {
        if (conn != null) {
            connections.remove(conn);
            System.err.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
            e.printStackTrace();
        }
    }
}
