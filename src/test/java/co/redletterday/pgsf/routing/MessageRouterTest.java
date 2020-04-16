package co.redletterday.pgsf.routing;

import co.redletterday.pgsf.PgsfServer;
import co.redletterday.pgsf.modules.EchoModule;
import co.redletterday.pgsf.networking.IncomingMessage;
import co.redletterday.pgsf.payloads.TestPayload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

class MessageRouterTest {

    private PgsfServer server;

    public MessageRouterTest() {

    }

    @Test
    void testRegister() {
        try {
            server = new PgsfServer(8085);

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
    void testRoute() {
        try {
            server = new PgsfServer(8085);

            // Register the test module.
            server.register(new EchoModule());

            IncomingMessage incomingMessage = new IncomingMessage(TestPayload.class.getName(), new TestPayload("sss"));

            int count = server.router.route(null, incomingMessage);

            Assertions.assertEquals(0, count, "routing did not trigger the expected callback count");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}