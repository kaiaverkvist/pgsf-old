package co.redletterday.pgsf;

import co.redletterday.pgsf.networking.Payload;
import co.redletterday.pgsf.payloads.TestPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayloadMutatorTest {

    String payloadString = "{\"@class\":\"co.redletterday.pgsf.payloads.TestPayload\",\"name\":\"sss\"}";
    TestPayload testPayload = new TestPayload("sss");

    @Test
    void testPayloadToJson() {

        // Mutate the test payload to json
        String json = PayloadMutator.mutatePayloadToJson(testPayload);

        // Check that it is the same as what we expect.
        Assertions.assertEquals(payloadString, json, "incorrectly mutated payload to json");
    }

    @Test
    void testJsonToPayload() {
        Payload payload = PayloadMutator.mutateJsonToPayload(payloadString);

        Assertions.assertEquals(testPayload.getClass(), payload.getClass(), "incorrectly mutated json to payload");
    }
}