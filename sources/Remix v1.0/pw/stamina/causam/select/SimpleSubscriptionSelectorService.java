package pw.stamina.causam.select;

import pw.stamina.causam.subscribe.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class SimpleSubscriptionSelectorService implements SubscriptionSelectorService
{
    SimpleSubscriptionSelectorService() {
    }
    
    @Override
    public <T> Collection<Subscription<T>> selectSubscriptions(final Class<T> key, final Supplier<Stream<Subscription>> subscriptions) {
        final List<?> selected = subscriptions.get().filter(this.canSubscriberSelect(key)).collect((Collector<? super Subscription, ?, List<? super Subscription>>)Collectors.toList());
        return this.castSelected(selected);
    }
    
    private Predicate<Subscription> canSubscriberSelect(final Class<?> key) {
        return subscription -> subscription.getKeySelector().canSelect(key);
    }
    
    private <T> Collection<Subscription<T>> castSelected(final List<?> selected) {
        return (Collection<Subscription<T>>)selected;
    }
}
