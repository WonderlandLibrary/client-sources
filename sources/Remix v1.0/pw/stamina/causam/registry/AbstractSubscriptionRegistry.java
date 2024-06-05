package pw.stamina.causam.registry;

import pw.stamina.causam.scan.*;
import pw.stamina.causam.subscribe.*;
import pw.stamina.causam.scan.result.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import pw.stamina.causam.select.*;

public abstract class AbstractSubscriptionRegistry implements SubscriptionRegistry
{
    private final SubscriptionSelectorService selectorService;
    
    protected AbstractSubscriptionRegistry(final SubscriptionSelectorService selectorService) {
        Objects.requireNonNull(selectorService, "selectorService");
        this.selectorService = selectorService;
    }
    
    @Override
    public boolean registerWith(final Object subscriber, final SubscriberScanningStrategy strategy) {
        final ScanResult scan = strategy.scan(subscriber);
        final Set<Subscription> subscriptions = scan.getSubscriptions();
        return this.registerAll(subscriptions);
    }
    
    @Override
    public Stream<Subscription> findSubscriptions(final Object subscriber) {
        return (Stream<Subscription>)this.findAllSubscriptions().filter(doesSubscriberMatch(subscriber));
    }
    
    @Override
    public <T> Collection<Subscription<T>> selectSubscriptions(final Class<T> key) {
        return this.selectorService.selectSubscriptions(key, this::findAllSubscriptions);
    }
    
    private static Predicate<Subscription> doesSubscriberMatch(final Object subscriber) {
        return subscription -> subscription.getSubscriber() == subscriber;
    }
    
    protected void invalidateCache(final Consumer<SubscriptionCacheInvalidator> invalidatorAction) {
        this.selectorService.getCacheInvalidator().ifPresent(invalidatorAction);
    }
}
