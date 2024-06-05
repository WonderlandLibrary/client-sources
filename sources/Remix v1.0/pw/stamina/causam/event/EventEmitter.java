package pw.stamina.causam.event;

import pw.stamina.causam.registry.*;
import pw.stamina.causam.publish.*;
import java.util.*;

public interface EventEmitter
{
     <T> boolean emit(final T p0);
    
     <T extends Cancellable> boolean emitCancellable(final T p0);
    
    default EventEmitter standard(final SubscriptionRegistry registry, final Publisher publisher) {
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(publisher, "publisher");
        return new StandardEventEmitter(registry, publisher);
    }
}
