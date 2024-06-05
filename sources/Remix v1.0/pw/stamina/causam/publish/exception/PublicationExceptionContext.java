package pw.stamina.causam.publish.exception;

import pw.stamina.causam.subscribe.*;
import java.util.*;

public final class PublicationExceptionContext<T>
{
    private final Subscription<T> subscription;
    private final T event;
    
    public PublicationExceptionContext(final Subscription<T> subscription, final T event) {
        Objects.requireNonNull(subscription, "subscription");
        Objects.requireNonNull(event, "event");
        this.subscription = subscription;
        this.event = event;
    }
    
    public Subscription<T> getSubscription() {
        return this.subscription;
    }
    
    public T getEvent() {
        return this.event;
    }
}
