package pw.stamina.causam.select;

import pw.stamina.causam.subscribe.*;
import java.util.*;

public interface SubscriptionCacheInvalidator
{
    void invalidate(final Subscription<?> p0);
    
    void invalidateAll(final Collection<Subscription> p0);
    
    void invalidate(final Object p0);
}
