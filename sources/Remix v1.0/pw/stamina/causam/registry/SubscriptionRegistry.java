package pw.stamina.causam.registry;

import pw.stamina.causam.subscribe.*;
import pw.stamina.causam.scan.*;
import java.util.stream.*;
import java.util.*;

public interface SubscriptionRegistry
{
    boolean register(final Subscription<?> p0);
    
    boolean registerAll(final Collection<Subscription> p0);
    
    boolean registerWith(final Object p0, final SubscriberScanningStrategy p1);
    
    boolean unregister(final Subscription<?> p0);
    
    boolean unregisterAll(final Object p0);
    
    Stream<Subscription> findSubscriptions(final Object p0);
    
    Stream<Subscription> findAllSubscriptions();
    
     <T> Collection<Subscription<T>> selectSubscriptions(final Class<T> p0);
    
    default SubscriptionRegistry nullRejecting(final SubscriptionRegistry registry) {
        Objects.requireNonNull(registry, "registry");
        return new NullRejectingSubscriptionRegistryDecorator(registry);
    }
}
