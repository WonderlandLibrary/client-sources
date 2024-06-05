package pw.stamina.causam.scan.method.factory;

import java.lang.reflect.*;
import pw.stamina.causam.subscribe.*;

public interface MethodBasedSubscriptionFactory
{
     <T> Subscription<T> createSubscription(final Object p0, final Method p1, final Class<T> p2);
    
    default MethodBasedSubscriptionFactory standard() {
        return new StandardMethodBasedSubscriptionFactory();
    }
}
