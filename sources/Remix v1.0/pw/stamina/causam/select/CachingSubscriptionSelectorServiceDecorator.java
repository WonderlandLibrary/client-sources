package pw.stamina.causam.select;

import pw.stamina.causam.subscribe.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;

public final class CachingSubscriptionSelectorServiceDecorator implements SubscriptionSelectorService
{
    private final SubscriptionSelectorService backingSelectorService;
    private final SubscriptionCacheInvalidatorImpl cacheInvalidator;
    private final Map<Class<?>, Collection<Subscription>> cachedSubscriptions;
    
    CachingSubscriptionSelectorServiceDecorator(final SubscriptionSelectorService backingSelectorService, final Map<Class<?>, Collection<Subscription>> cachedSubscriptions) {
        this.backingSelectorService = backingSelectorService;
        this.cachedSubscriptions = cachedSubscriptions;
        this.cacheInvalidator = new SubscriptionCacheInvalidatorImpl();
    }
    
    @Override
    public <T> Collection<Subscription<T>> selectSubscriptions(final Class<T> key, final Supplier<Stream<Subscription>> subscriptions) {
        return this.uncheckedCast(this.cachedSubscriptions.computeIfAbsent(key, this.selectSubscriptions(subscriptions)));
    }
    
    private Function<Class<?>, Collection<Subscription>> selectSubscriptions(final Supplier<Stream<Subscription>> subscriptions) {
        return (Function<Class<?>, Collection<Subscription>>)(key -> (Collection<?>)this.uncheckedCast(this.backingSelectorService.selectSubscriptions(key, subscriptions)));
    }
    
    private <T> T uncheckedCast(final Object obj) {
        return (T)obj;
    }
    
    @Override
    public Optional<SubscriptionCacheInvalidator> getCacheInvalidator() {
        return (Optional<SubscriptionCacheInvalidator>)Optional.of(this.cacheInvalidator);
    }
    
    public static SubscriptionSelectorService concurrent(final SubscriptionSelectorService backingSelectorService) {
        return new CachingSubscriptionSelectorServiceDecorator(backingSelectorService, new ConcurrentHashMap<Class<?>, Collection<Subscription>>());
    }
    
    public static SubscriptionSelectorService standard(final SubscriptionSelectorService backingSelectorService) {
        return new CachingSubscriptionSelectorServiceDecorator(backingSelectorService, new HashMap<Class<?>, Collection<Subscription>>());
    }
    
    private final class SubscriptionCacheInvalidatorImpl implements SubscriptionCacheInvalidator
    {
        @Override
        public void invalidate(final Subscription<?> subscription) {
            this.valuesRemoveIf(this.contains(subscription));
        }
        
        @Override
        public void invalidateAll(final Collection<Subscription> subscriptions) {
            CachingSubscriptionSelectorServiceDecorator.this.cachedSubscriptions.keySet().removeIf(this.canAnySubscriptionSelect(subscriptions));
        }
        
        @Override
        public void invalidate(final Object subscriber) {
            this.valuesRemoveIf(this.anyMatch(this.isSubscriberSameAs(subscriber)));
        }
        
        private void valuesRemoveIf(final Predicate<Collection<Subscription>> filter) {
            CachingSubscriptionSelectorServiceDecorator.this.cachedSubscriptions.values().removeIf(filter);
        }
        
        private Predicate<Subscription> isSubscriberSameAs(final Object subscriber) {
            return subscription -> subscription.getSubscriber() == subscriber;
        }
        
        private Predicate<Collection<Subscription>> contains(final Subscription<?> subscription) {
            return subscriptions -> subscriptions.contains(subscription);
        }
        
        private Predicate<Class<?>> canAnySubscriptionSelect(final Collection<Subscription> subscriptions) {
            return key -> subscriptions.stream().map((Function<? super Subscription, ?>)Subscription::getKeySelector).anyMatch(selector -> selector.canSelect(key));
        }
        
        private Predicate<Collection<Subscription>> anyMatch(final Predicate<Subscription> filter) {
            return subscriptions -> subscriptions.stream().anyMatch(filter);
        }
    }
}
