package pw.stamina.causam.subscribe;

import pw.stamina.causam.select.*;
import pw.stamina.causam.publish.listen.*;
import java.util.*;
import pw.stamina.causam.publish.listen.decorate.*;

public final class SubscriptionBuilder<T>
{
    private Object subscriber;
    private String identifier;
    private KeySelector keySelector;
    private boolean ignoreCancelled;
    private Listener<T> listener;
    private final ListenerDecoratorContainer<T> decorators;
    
    public SubscriptionBuilder() {
        this.decorators = new ListenerDecoratorContainer<T>();
    }
    
    public SubscriptionBuilder<T> subscriber(final Object subscriber) {
        Objects.requireNonNull(subscriber, "subscriber");
        this.subscriber = subscriber;
        return this;
    }
    
    public SubscriptionBuilder<T> identifier(final String identifier) {
        Objects.requireNonNull(identifier, "identifier");
        this.identifier = identifier;
        return this;
    }
    
    public SubscriptionBuilder<T> selector(final KeySelector keySelector) {
        Objects.requireNonNull(keySelector, "keySelector");
        this.keySelector = keySelector;
        return this;
    }
    
    public SubscriptionBuilder<T> ignoreCancelled() {
        this.ignoreCancelled = true;
        return this;
    }
    
    public SubscriptionBuilder<T> listener(final Listener<T> listener) {
        Objects.requireNonNull(listener, "listener");
        this.listener = listener;
        return this;
    }
    
    public SubscriptionBuilder<T> decorate(final ListenerDecorator<T> decorator) {
        Objects.requireNonNull(decorator, "decorator");
        this.decorators.add(decorator);
        return this;
    }
    
    public Subscription<T> build() {
        this.validateBuilderIsComplete();
        final Listener<T> decoratedListener = this.decorators.applyDecorationsToListener(this.listener);
        final Subscription<T> subscription = new ImmutableSubscription<T>(this.subscriber, this.identifier, this.keySelector, this.ignoreCancelled, decoratedListener);
        if (this.decorators.shouldCreateProxy()) {
            return SubscriptionProxyFactory.createSubscriptionProxy(subscription, this.decorators);
        }
        return subscription;
    }
    
    private void validateBuilderIsComplete() {
        if (this.subscriber == null) {
            throw new IllegalStateException("subscriber has not been set, please set it using the #subscriber method");
        }
        if (this.keySelector == null) {
            throw new IllegalStateException("keySelector has not been set, please set it using the #keySelector method");
        }
        this.validateListenerHasBeenSet();
    }
    
    private void validateListenerHasBeenSet() {
        if (this.listener == null) {
            throw new IllegalStateException("listener has not been set, please set it using the #listener method");
        }
    }
}
