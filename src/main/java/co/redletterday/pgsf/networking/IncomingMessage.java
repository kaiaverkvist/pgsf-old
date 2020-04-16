package co.redletterday.pgsf.networking;

public class IncomingMessage {
    public String name;
    public Payload payload;

    public IncomingMessage(String name, Payload payload) {
        this.name = name;
        this.payload = payload;
    }
}
