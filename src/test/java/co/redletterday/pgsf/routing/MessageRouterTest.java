package co.redletterday.pgsf.routing;

import co.redletterday.pgsf.PayloadMutator;
import co.redletterday.pgsf.PgsfServer;
import co.redletterday.pgsf.modules.EchoModule;
import co.redletterday.pgsf.networking.Payload;
import co.redletterday.pgsf.payloads.TestPayload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

class MessageRouterTest {

    public MessageRouterTest() {

    }

    @Test
    void testRegister() {
        try {
            PgsfServer server = new PgsfServer(8083);

            // Check that we have an empty module list.
            Assertions.assertEquals(0, server.getModules().size(), "server module list size was not 0 prior to register()");

            // Register the test module.
            server.register(new EchoModule());

            // The module list should have a size of 1 after adding a single module.
            Assertions.assertEquals(1, server.getModules().size(), "server module list size was not 1 after adding module");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRouteFromPayload() {
        try {
            PgsfServer server  = new PgsfServer(8084);

            // Register the test module.
            server.register(new EchoModule());

            // Starts the server.
            server.start();

            // Let's route a test payload and check how many handlers got executed.
            TestPayload payload = new TestPayload("sss");
            int count = server.router.route(null, payload);

            Assertions.assertEquals(1, count, "routing did not trigger the expected callback count");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRouteFromJson() {
        try {
            PgsfServer server  = new PgsfServer(8085);

            // Register the test module.
            server.register(new EchoModule());

            // Starts the server.
            server.start();

            // Create a test payload and serialize it to a string.
            TestPayload testPayload = new TestPayload("sss");
            String json = PayloadMutator.mutatePayloadToJson(testPayload);

            // Get the Payload implementation from the json string.
            Payload finalPayload = PayloadMutator.mutateJsonToPayload(json);

            // Feed the final payload into the router.
            int count = server.router.route(null, finalPayload);

            Assertions.assertEquals(1, count, "routing did not trigger the expected callback count");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}