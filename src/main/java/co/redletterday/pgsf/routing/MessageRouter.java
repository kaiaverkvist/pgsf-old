package co.redletterday.pgsf.routing;

import co.redletterday.pgsf.PgsfServer;
import co.redletterday.pgsf.networking.IncomingMessage;
import co.redletterday.pgsf.networking.MessageContainer;
import org.java_websocket.WebSocket;

import java.util.ArrayList;

public class MessageRouter {

    // Stores the registered handlers for future reference.
    protected ArrayList<RegisteredHandler> handlers;

    /**
     * The MessageRouter routes socket messages to the correct handlers.
     * @param server the pgsf instance is passed in.
     */
    public MessageRouter(PgsfServer server) {
        handlers = new ArrayList<>();
    }

    /**
     * Sends a message through the message router to all the registered receivers.
     * @param sender websocket sender
     * @param message incoming message
     * @return counter of how many handlers were executed
     */
    public int route(WebSocket sender, IncomingMessage message) {

        //
        int callbacksExecuted = 0;

        // Iterate through the handler master list and trigger the callbacks.
        for(RegisteredHandler<?> handler : handlers) {

            // Check that our handler callback type is the same as the payload.
            if(handler.callback.getClass() == message.payload.getClass()) {

                // Create a container with the payload data and the websocket sender.
                MessageContainer container = new MessageContainer<>(sender, message.payload);

                // This calls the callback with the container object.
                // Note: The container is not strongly typed at this level, but will be strongly typed
                // on the receiving end.
                handler.callback.accept(container);

                // Increment the counter.
                callbacksExecuted++;
            }
        }

        // Return the count.
        return callbacksExecuted;
    }

    /**
     * Registers a payload type along with a callback to be called upon
     * @param handler handler instance
     */
    public void register(RegisteredHandler<?> handler) {
        // Add the handler to the master list.
        handlers.add(handler);
    }
}
