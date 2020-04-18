package co.redletterday.pgsf.routing;

import co.redletterday.pgsf.networking.MessageContainer;
import co.redletterday.pgsf.networking.Payload;

import java.util.function.Consumer;

public class RegisteredHandler<T extends Payload> {

    private final Class<T> typeToken;
    
    public Consumer<MessageContainer<T>> callback;

    public Class<T> getTypeToken() {
        return typeToken;
    }

    public RegisteredHandler(Class<T> typeToken, Consumer<MessageContainer<T>> callback) {
        this.typeToken = typeToken;

        this.callback = callback;
    }
}
