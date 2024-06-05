package pw.stamina.causam.subscribe;

import pw.stamina.causam.select.*;
import pw.stamina.causam.publish.listen.*;
import java.util.*;
import pw.stamina.causam.publish.exception.*;

final class ImmutableSubscription<T> implements Subscription<T>
{
    private final Object subscriber;
    private final String identifier;
    private final KeySelector keySelector;
    private final boolean ignoreCancelled;
    private final Listener<T> listener;
    
    ImmutableSubscription(final Object subscriber, final String identifier, final KeySelector keySelector, final boolean ignoreCancelled, final Listener<T> listener) {
        this.subscriber = subscriber;
        this.identifier = identifier;
        this.keySelector = keySelector;
        this.ignoreCancelled = ignoreCancelled;
        this.listener = listener;
    }
    
    @Override
    public Object getSubscriber() {
        return this.subscriber;
    }
    
    @Override
    public Optional<String> getIdentifier() {
        return Optional.ofNullable(this.identifier);
    }
    
    @Override
    public KeySelector getKeySelector() {
        return this.keySelector;
    }
    
    @Override
    public boolean ignoreCancelled() {
        return this.ignoreCancelled;
    }
    
    @Override
    public void publish(final T event) throws PublicationException {
        this.listener.publish(event);
    }
}
