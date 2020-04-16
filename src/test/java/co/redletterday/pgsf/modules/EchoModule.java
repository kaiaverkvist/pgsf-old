package co.redletterday.pgsf.modules;

import co.redletterday.pgsf.PgsfServer;
import co.redletterday.pgsf.module.ServerModule;
import co.redletterday.pgsf.payloads.TestPayload;
import co.redletterday.pgsf.routing.RegisteredHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoModule implements ServerModule {

    static Logger logger = LoggerFactory.getLogger(PgsfServer.class);

    public void onInit(PgsfServer server) {
        logger.info("EchoModule onInit() called.");

        server.router.register(new RegisteredHandler<TestPayload>(s -> {
            System.out.println(s.sender.getRemoteSocketAddress());
            System.out.println(s.payload.name);
        }));
    }

    public void onDestroy() {
        logger.info("EchoModule destroyed.");
    }
}
