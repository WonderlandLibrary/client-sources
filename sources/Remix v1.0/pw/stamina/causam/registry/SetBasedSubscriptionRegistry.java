package pw.stamina.causam.registry;

import pw.stamina.causam.subscribe.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.*;
import pw.stamina.causam.select.*;

public final class SetBasedSubscriptionRegistry extends AbstractSubscriptionRegistry
{
    private final Set<Subscription> subscriptions;
    
    SetBasedSubscriptionRegistry(final SubscriptionSelectorService selectorService, final Set<Subscription> subscriptions) {
        super(selectorService);
        this.subscriptions = subscriptions;
    }
    
    @Override
    public boolean register(final Subscription<?> subscription) {
        final boolean registered = this.subscriptions.add(subscription);
        this.invalidateCache(invalidator -> invalidator.invalidate(subscription));
        return registered;
    }
    
    @Override
    public boolean registerAll(final Collection<Subscription> subscriptions) {
        final boolean registeredAny = this.subscriptions.addAll(subscriptions);
        this.invalidateCache(invalidator -> invalidator.invalidateAll(subscriptions));
        return registeredAny;
    }
    
    @Override
    public boolean unregister(final Subscription<?> subscription) {
        final boolean unregistered = this.subscriptions.remove(subscription);
        this.invalidateCache(invalidator -> invalidator.invalidate(subscription));
        return unregistered;
    }
    
    @Override
    public boolean unregisterAll(final Object subscriber) {
        final boolean unregisteredAny = this.subscriptions.removeIf(subscription -> subscription.getSubscriber() == subscriber);
        this.invalidateCache(invalidator -> invalidator.invalidate(subscriber));
        return unregisteredAny;
    }
    
    @Override
    public Stream<Subscription> findAllSubscriptions() {
        return (Stream<Subscription>)this.subscriptions.stream();
    }
    
    public static SubscriptionRegistry copyOnWrite(final SubscriptionSelectorService selectorService) {
        return new SetBasedSubscriptionRegistry(selectorService, new CopyOnWriteArraySet<Subscription>());
    }
    
    public static SubscriptionRegistry concurrentHash(final SubscriptionSelectorService selectorService) {
        return new SetBasedSubscriptionRegistry(selectorService, (Set<Subscription>)ConcurrentHashMap.newKeySet());
    }
    
    public static SubscriptionRegistry hash(final SubscriptionSelectorService selectorService) {
        return new SetBasedSubscriptionRegistry(selectorService, new HashSet<Subscription>());
    }
}
