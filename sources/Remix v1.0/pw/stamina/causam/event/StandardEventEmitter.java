package pw.stamina.causam.event;

import pw.stamina.causam.registry.*;
import pw.stamina.causam.publish.*;
import pw.stamina.causam.subscribe.*;
import java.util.*;

public final class StandardEventEmitter implements EventEmitter
{
    private final SubscriptionRegistry registry;
    private final Publisher publisher;
    
    StandardEventEmitter(final SubscriptionRegistry registry, final Publisher publisher) {
        this.registry = registry;
        this.publisher = publisher;
    }
    
    @Override
    public <T> boolean emit(final T event) {
        final Class<T> key = (Class<T>)event.getClass();
        final Collection<Subscription<T>> subscriptions = this.registry.selectSubscriptions(key);
        if (subscriptions.isEmpty()) {
            return false;
        }
        this.publisher.publish(event, subscriptions);
        return true;
    }
    
    @Override
    public <T extends Cancellable> boolean emitCancellable(final T event) {
        final Class<T> key = (Class<T>)event.getClass();
        final Collection<Subscription<T>> subscriptions = this.registry.selectSubscriptions(key);
        if (subscriptions.isEmpty()) {
            return false;
        }
        this.publisher.publishCancellable(event, subscriptions);
        return true;
    }
}
