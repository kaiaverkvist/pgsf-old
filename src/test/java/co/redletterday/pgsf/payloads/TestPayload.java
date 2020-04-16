package co.redletterday.pgsf.payloads;

import co.redletterday.pgsf.networking.Payload;

public class TestPayload implements Payload {
    public String name;

    public TestPayload(String name) {
        this.name = name;
    }
}
