package pw.stamina.causam.subscribe;

import pw.stamina.causam.publish.listen.decorate.*;
import java.util.function.*;
import java.util.*;
import java.lang.reflect.*;

public final class SubscriptionProxyFactory
{
    private SubscriptionProxyFactory() {
    }
    
    public static <T> Subscription<T> createSubscriptionProxy(final Subscription<T> subscription, final ListenerDecoratorContainer<T> decorators) {
        final ClassLoader classLoader = Subscription.class.getClassLoader();
        final Class<?>[] interfaces = createInterfacesArray(decorators);
        final Map<Class<?>, ?> interfaceToInstanceAsMap = decorators.extractDecorationsInterfaceToInstanceAsMap();
        final SubscriptionProxyInvocationHandler<T> handler = new SubscriptionProxyInvocationHandler<T>((Subscription)subscription, (Map)interfaceToInstanceAsMap);
        final Object proxy = Proxy.newProxyInstance(classLoader, interfaces, handler);
        final Subscription<T> proxiedSubscription = (Subscription<T>)proxy;
        return proxiedSubscription;
    }
    
    private static <T> Class<?>[] createInterfacesArray(final ListenerDecoratorContainer<T> decorators) {
        final List<Class<?>> interfaces = new LinkedList<Class<?>>();
        interfaces.add(Subscription.class);
        decorators.extractInterfacesFromDecorators().forEach(interfaces::add);
        return interfaces.toArray(new Class[interfaces.size()]);
    }
    
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
}
