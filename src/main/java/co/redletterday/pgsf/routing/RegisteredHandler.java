package co.redletterday.pgsf.routing;

import co.redletterday.pgsf.networking.MessageContainer;
import co.redletterday.pgsf.networking.Payload;

import java.util.function.Consumer;

public class RegisteredHandler<T extends Payload> {

    public Consumer<MessageContainer<T>> callback;

    public RegisteredHandler(Consumer<MessageContainer<T>> callback) {
        this.callback = callback;
    }
}
