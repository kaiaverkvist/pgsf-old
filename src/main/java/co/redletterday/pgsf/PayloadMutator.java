package co.redletterday.pgsf;

import co.redletterday.pgsf.networking.Payload;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

public class PayloadMutator {

    // Create the genson instance we'll use to serialize.
    // Notes: useClassMetadata adds a tag to serialization with the path to the serialized object.
    private static Genson genson = new GensonBuilder()
            .useClassMetadata(true)
            .useRuntimeType(true)
            .create();

    /**
     * Mutates a json string to a Payload implementation.
     * Note: Payload implementation MUST have an empty no args constructor.
     * @param json string containing json
     * @return payload implementation
     */
    public static Payload mutateJsonToPayload(String json) {
        // Deserialize and return the Payload.
        Payload payload = genson.deserialize(json, Payload.class);
        return payload;
    }

    /**
     * Mutates a payload implementation to json string.
     * @param payload to mutate
     * @return json
     */
    public static String mutatePayloadToJson(Payload payload) {
        return genson.serialize(payload);
    }
}
