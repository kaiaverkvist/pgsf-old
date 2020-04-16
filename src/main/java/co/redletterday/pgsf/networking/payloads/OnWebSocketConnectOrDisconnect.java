package co.redletterday.pgsf.networking.payloads;

import co.redletterday.pgsf.networking.Payload;

public class OnWebSocketConnectOrDisconnect implements Payload {
    public String message;

    public OnWebSocketConnectOrDisconnect(String message) {
        this.message = message;
    }
}
