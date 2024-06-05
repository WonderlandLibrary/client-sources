package pw.stamina.causam.select;

import java.util.function.*;
import java.util.stream.*;
import pw.stamina.causam.subscribe.*;
import java.util.*;

public interface SubscriptionSelectorService
{
     <T> Collection<Subscription<T>> selectSubscriptions(final Class<T> p0, final Supplier<Stream<Subscription>> p1);
    
    default Optional<SubscriptionCacheInvalidator> getCacheInvalidator() {
        return Optional.empty();
    }
    
    default SubscriptionSelectorService simple() {
        return new SimpleSubscriptionSelectorService();
    }
}
