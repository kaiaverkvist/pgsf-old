package co.redletterday.pgsf;

import co.redletterday.pgsf.module.ServerModule;
import co.redletterday.pgsf.networking.Payload;
import co.redletterday.pgsf.networking.payloads.OnWebSocketConnectOrDisconnect;
import co.redletterday.pgsf.routing.MessageRouter;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * co.redletterday.PgsfServer is the main runner for the server.
 * Use this as the entry point for pgsf.
 */
public class PgsfServer extends WebSocketServer {

    static Logger logger = LoggerFactory.getLogger(PgsfServer.class);

    public MessageRouter router;

    private ArrayList<ServerModule> modules;

    private boolean hasStarted;

    public PgsfServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));

        router = new MessageRouter(this);
        modules = new ArrayList<ServerModule>();
    }

    @Override
    public void onOpen(WebSocket sender, ClientHandshake clientHandshake) {

        // We create an artificial disconnect/connect message.
        OnWebSocketConnectOrDisconnect payload = new OnWebSocketConnectOrDisconnect("connect");

        router.route(sender, payload);

        logger.debug(sender.getRemoteSocketAddress() + " connected.");
    }

    @Override
    public void onClose(WebSocket sender, int code, String reason, boolean remote) {

        // We create an artificial disconnect/connect message.
        OnWebSocketConnectOrDisconnect payload = new OnWebSocketConnectOrDisconnect(reason);

        router.route(sender, payload);

        logger.debug(sender.getRemoteSocketAddress() + " disconnected.");
    }

    @Override
    public void onMessage(WebSocket sender, String messageJson) {

        try {
            Payload payload = PayloadMutator.mutateJsonToPayload(messageJson);
            router.route(sender, payload);
        } catch(Exception ex) {
            logger.error("Unable to parse json: {} (contents: {})", ex.getMessage(), messageJson);
        }
    }

    @Override
    public void onError(WebSocket sender, Exception ex) {
        logger.error(sender.getRemoteSocketAddress() + " | ERROR | ", ex);
    }

    @Override
    public void onStart() {
        logger.debug("OnStart called");
    }

    @Override
    public void start() {
        super.start();

        // Initializes all the other modules.
        for (ServerModule module : modules) {
            module.onInit(this);
        }

        // Sets the started flag to true.
        hasStarted = true;


        logger.debug("Starting PgsfServer. (10:58)");
        logger.debug("WebSocket server has started. Port: {}", this.getPort());
    }

    /**
     * Registers a co.redletterday.module with the server, effectively enabling the code within it to run.
     * @param module an instance of an implementation of ServerModule.
     * @return this
     */
    public PgsfServer register(ServerModule module) {

        String name = module.getClass().getName();

        // Add the co.redletterday.module to the master list.
        modules.add(module);

        logger.info("Added module {}", name);

        if(hasStarted) {
            logger.warn("Module {} may not have started correctly. Make sure you call start() after registering all modules.", name);
        }

        return this;
    }

    /**
     * Returns the co.redletterday.module master list.
     * @return co.redletterday.modules
     */
    public ArrayList<ServerModule> getModules() {
        return modules;
    }
}
