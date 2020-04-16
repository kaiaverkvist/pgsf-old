package co.redletterday.pgsf.routing;

import co.redletterday.pgsf.PgsfServer;
import co.redletterday.pgsf.modules.EchoModule;
import co.redletterday.pgsf.networking.IncomingMessage;
import co.redletterday.pgsf.payloads.TestPayload;
import org.junit.Assert;
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
            Assert.assertEquals("server module list size was not 0 prior to register()", 0, server.getModules().size());

            // Register the test module.
            server.register(new EchoModule());

            // The module list should have a size of 1 after adding a single module.
            Assert.assertEquals("server module list size was not 1 after adding module", 1, server.getModules().size());

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

            Assert.assertEquals("routing did not trigger the expected callback count", 0, count);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}