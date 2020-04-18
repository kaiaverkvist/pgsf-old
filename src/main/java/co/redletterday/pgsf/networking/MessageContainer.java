package co.redletterday.pgsf.networking;

import org.java_websocket.WebSocket;

/**
 * MessageContainer is the generic used to cast the Payload type into the actual payload object.
 * @param <T>
 */
public class MessageContainer<T> {
    public WebSocket sender;
    public T payload;

    public MessageContainer(WebSocket sender, T payload) {
        this.sender = sender;
        this.payload = payload;
    }
}
