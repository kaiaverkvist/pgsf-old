package co.redletterday.pgsf.modules;

import co.redletterday.pgsf.PgsfServer;
import co.redletterday.pgsf.module.ServerModule;
import co.redletterday.pgsf.payloads.TestPayload;
import co.redletterday.pgsf.routing.RegisteredHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoModule implements ServerModule {

    static Logger logger = LoggerFactory.getLogger(EchoModule.class);

    public void onInit(PgsfServer server) {
        logger.info("EchoModule onInit() called.");

        server.router.register(new RegisteredHandler<TestPayload>(TestPayload.class, s -> {

            System.out.println("Payload called with name: " + s.payload.name);
        }));
    }

    public void onDestroy() {
        logger.info("EchoModule destroyed.");
    }
}
