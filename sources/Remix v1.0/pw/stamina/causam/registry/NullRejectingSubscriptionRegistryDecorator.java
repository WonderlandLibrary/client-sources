package pw.stamina.causam.registry;

import pw.stamina.causam.subscribe.*;
import java.util.*;
import java.util.function.*;
import pw.stamina.causam.scan.*;
import java.util.stream.*;

public final class NullRejectingSubscriptionRegistryDecorator implements SubscriptionRegistry
{
    private final SubscriptionRegistry registry;
    
    NullRejectingSubscriptionRegistryDecorator(final SubscriptionRegistry registry) {
        this.registry = registry;
    }
    
    @Override
    public boolean register(final Subscription<?> subscription) {
        Objects.requireNonNull(subscription, "subscription");
        return this.registry.register(subscription);
    }
    
    @Override
    public boolean registerAll(final Collection<Subscription> subscriptions) {
        this.validateSubscriptionsInput(subscriptions);
        return this.registry.registerAll(subscriptions);
    }
    
    private void validateSubscriptionsInput(final Collection<Subscription> subscriptions) {
        Objects.requireNonNull(subscriptions, "subscriptions");
        subscriptions.forEach(Objects::requireNonNull);
    }
    
    @Override
    public boolean registerWith(final Object subscriber, final SubscriberScanningStrategy strategy) {
        Objects.requireNonNull(subscriber, "subscriber");
        Objects.requireNonNull(strategy, "strategy");
        return this.registry.registerWith(subscriber, strategy);
    }
    
    @Override
    public boolean unregister(final Subscription<?> subscription) {
        Objects.requireNonNull(subscription, "subscription");
        return this.registry.unregister(subscription);
    }
    
    @Override
    public boolean unregisterAll(final Object subscriber) {
        Objects.requireNonNull(subscriber, "subscriber");
        return this.registry.unregisterAll(subscriber);
    }
    
    @Override
    public Stream<Subscription> findSubscriptions(final Object subscriber) {
        Objects.requireNonNull(subscriber, "subscriber");
        return this.registry.findSubscriptions(subscriber);
    }
    
    @Override
    public Stream<Subscription> findAllSubscriptions() {
        return this.registry.findAllSubscriptions();
    }
    
    @Override
    public <T> Collection<Subscription<T>> selectSubscriptions(final Class<T> key) {
        return this.registry.selectSubscriptions(key);
    }
}
