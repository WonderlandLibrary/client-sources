package pw.stamina.causam;

import pw.stamina.causam.event.*;
import pw.stamina.causam.registry.*;
import java.util.*;

public interface EventBus
{
    boolean register(final Object p0);
    
    boolean unregister(final Object p0);
    
     <T> boolean emit(final T p0);
    
    EventEmitter getEmitter();
    
    SubscriptionRegistry getRegistry();
    
    default EventBus standard(final SubscriptionRegistry registry, final EventEmitter emitter) {
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(emitter, "emitter");
        return new StandardEventBus(registry, emitter);
    }
}
