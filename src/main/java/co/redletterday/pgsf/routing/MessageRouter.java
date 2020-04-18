package co.redletterday.pgsf.routing;

import co.redletterday.pgsf.PgsfServer;
import co.redletterday.pgsf.networking.MessageContainer;
import co.redletterday.pgsf.networking.Payload;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MessageRouter {

    static Logger logger = LoggerFactory.getLogger(MessageRouter.class);

    // Stores the registered handlers for future reference.
    protected ArrayList<RegisteredHandler<?>> handlers;

    private PgsfServer server;

    /**
     * The MessageRouter routes socket messages to the correct handlers.
     * @param server the pgsf instance is passed in.
     */
    public MessageRouter(PgsfServer server) {
        this.server = server;

        handlers = new ArrayList<>();
    }

    /**
     * Sends a message through the message router to all the registered receivers.
     * @param sender websocket sender
     * @param payload payload of the message
     * @return counter of how many handlers were executed
     */
    public int route(WebSocket sender, Payload payload) {

        // Keep track of the callback execution count.
        int callbacksExecuted = 0;

        // Iterate through the handler master list and trigger the callbacks.
        for(RegisteredHandler<?> handler : handlers) {

            // Check that our handler callback type is the same as the payload.
            if(handler.getTypeToken().getName().equals(payload.getClass().getName())) {

                // Create a container with the payload data and the websocket sender.
                MessageContainer container = new MessageContainer<>(sender, payload);

                logger.debug("Callback is called with container {}", container.payload.getClass());

                // This calls the callback with the container object.
                // Note: The container is not strongly typed at this level, but will be strongly typed
                // on the receiving end.
                handler.callback.accept(container);

                // Increment the counter.
                callbacksExecuted++;
            }
        }

        // Finally, return the count.
        return callbacksExecuted;
    }

    /**
     * Registers a payload type along with a callback to be called upon
     * @param handler handler instance
     */
    public void register(RegisteredHandler<?> handler) {

        logger.debug("Registered handler for payload type {}", handler.getTypeToken());

        // Add the handler to the master list.
        handlers.add(handler);
    }
}
