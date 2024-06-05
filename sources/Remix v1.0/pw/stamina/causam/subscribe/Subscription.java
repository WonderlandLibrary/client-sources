package pw.stamina.causam.subscribe;

import java.util.*;
import pw.stamina.causam.select.*;
import pw.stamina.causam.publish.exception.*;

public interface Subscription<T>
{
    Object getSubscriber();
    
    Optional<String> getIdentifier();
    
    KeySelector getKeySelector();
    
    boolean ignoreCancelled();
    
    void publish(final T p0) throws PublicationException;
    
    default <T> SubscriptionBuilder<T> builder() {
        return new SubscriptionBuilder<T>();
    }
}
