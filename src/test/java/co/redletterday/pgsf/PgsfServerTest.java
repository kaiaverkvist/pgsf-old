package co.redletterday.pgsf;

import co.redletterday.pgsf.modules.EchoModule;
import org.junit.jupiter.api.Assertions;
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

        Assertions.assertNotNull(server.getModules(), "server module list was not initialized properly.");
    }
}