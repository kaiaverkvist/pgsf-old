package co.redletterday.pgsf;

import co.redletterday.pgsf.module.ServerModule;
import co.redletterday.pgsf.networking.IncomingMessage;
import co.redletterday.pgsf.networking.payloads.OnWebSocketConnectOrDisconnect;
import co.redletterday.pgsf.routing.MessageRouter;
import com.google.gson.Gson;
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
        IncomingMessage message = new IncomingMessage(payload.getClass().getName(), payload);

        router.route(sender, message);

        logger.debug(sender.getRemoteSocketAddress() + " connected.");
    }

    @Override
    public void onClose(WebSocket sender, int code, String reason, boolean remote) {

        // We create an artificial disconnect/connect message.
        OnWebSocketConnectOrDisconnect payload = new OnWebSocketConnectOrDisconnect(reason);
        IncomingMessage message = new IncomingMessage(payload.getClass().getName(), payload);

        router.route(sender, message);

        logger.debug(sender.getRemoteSocketAddress() + " disconnected.");
    }

    @Override
    public void onMessage(WebSocket sender, String messageJson) {

        Gson gson = new Gson();
        IncomingMessage incomingMessage = gson.fromJson(messageJson, IncomingMessage.class);

        router.route(sender, incomingMessage);
    }

    @Override
    public void onError(WebSocket sender, Exception ex) {
        logger.error(sender.getRemoteSocketAddress() + " | ERROR | ", ex);
    }

    @Override
    public void onStart() {

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


        logger.debug("Starting PgsfServer.");
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
