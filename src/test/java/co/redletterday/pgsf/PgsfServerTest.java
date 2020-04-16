package co.redletterday.pgsf;

import co.redletterday.pgsf.modules.EchoModule;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

class PgsfServerTest {

    private PgsfServer server;

    public PgsfServerTest() {

    }

    @Test
    void initAndAddModules() {

        try {
            server = new PgsfServer(8083);
            server.register(new EchoModule());

            server.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull("server module list was not initialized properly.", server.getModules());

        Assert.assertEquals(server.getModules().size(), 1);
    }
}