package de.az.ware.connection.websocket;

import de.az.ware.connection.Connection;
import de.az.ware.connection.ConnectionListener;
import de.az.ware.connection.ConnectionProvider;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClient implements ConnectionProvider, Connection {

    private ConnectionListener listener;

    private org.java_websocket.client.WebSocketClient client;

    public WebSocketClient(String adress) {
        try {
            createClient(new URI(adress));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not create URI", e);
        }
    }

    public WebSocketClient(URI uri) {
        createClient(uri);
    }

    private void createClient(URI uri){

        client = new org.java_websocket.client.WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                listener.onConnected(WebSocketClient.this);
            }

            @Override
            public void onMessage(String message) {
                listener.onMessage(WebSocketClient.this, message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                listener.onDisconnected(WebSocketClient.this);
            }

            @Override
            public void onError(Exception ex) {

            }
        };

    }

    public void connect(){
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setConnectionListener(ConnectionListener listener) {
        this.listener = listener;
    }

    @Override
    public void sendMessage(String message) {
        if(client.isOpen()) client.send(message);
    }

}
