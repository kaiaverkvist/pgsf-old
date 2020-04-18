package co.redletterday.pgsf.payloads;

import co.redletterday.pgsf.networking.Payload;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

public class TestPayload implements Payload {
    public String name;

    public TestPayload(String name) {
        this.name = name;
    }

    public TestPayload() {

    }
}
