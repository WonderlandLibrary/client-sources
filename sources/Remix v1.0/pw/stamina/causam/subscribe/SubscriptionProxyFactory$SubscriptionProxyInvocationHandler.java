package pw.stamina.causam.subscribe;

import java.util.*;
import java.lang.reflect.*;

private static class SubscriptionProxyInvocationHandler<T> implements InvocationHandler
{
    private final Subscription<T> subscription;
    private final Map<Class<?>, ?> interfaceToInstanceAsMap;
    
    private SubscriptionProxyInvocationHandler(final Subscription<T> subscription, final Map<Class<?>, ?> interfaceToInstanceAsMap) {
        this.subscription = subscription;
        this.interfaceToInstanceAsMap = new IdentityHashMap<Class<?>, Object>(interfaceToInstanceAsMap);
    }
    
    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Class<?> declaringClass = method.getDeclaringClass();
        final Object handle = this.findHandleByDeclaringClass(declaringClass);
        assert handle != null;
        return method.invoke(handle, args);
    }
    
    private Object findHandleByDeclaringClass(final Class<?> declaringClass) {
        if (declaringClass == Subscription.class) {
            return this.subscription;
        }
        return this.interfaceToInstanceAsMap.get(declaringClass);
    }
}
