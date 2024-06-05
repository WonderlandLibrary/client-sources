package pw.stamina.causam.scan.method.factory;

import java.lang.reflect.*;
import pw.stamina.causam.subscribe.*;
import pw.stamina.causam.publish.listen.decorate.*;
import pw.stamina.causam.select.*;
import java.lang.annotation.*;
import pw.stamina.causam.publish.listen.*;
import pw.stamina.causam.scan.method.model.*;

final class StandardMethodBasedSubscriptionFactory implements MethodBasedSubscriptionFactory
{
    @Override
    public <T> Subscription<T> createSubscription(final Object subscriber, final Method method, final Class<T> eventType) {
        assureAccessible(method);
        final SubscriptionBuilder<T> builder = new SubscriptionBuilder<T>();
        if (shouldSynchronizeListener(method)) {
            builder.decorate(ListenerDecorator.synchronizing());
        }
        builder.subscriber(subscriber).selector(this.createSelectorFromMethod(method, eventType)).listener(createListener(subscriber, method));
        if (this.shouldListenerIgnoreCancelled(method)) {
            builder.ignoreCancelled();
        }
        createDecorateAndSetListener(builder, subscriber, method);
        return builder.build();
    }
    
    private static void assureAccessible(final Method method) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
    }
    
    private <T> KeySelector createSelectorFromMethod(final Method method, final Class<T> eventType) {
        if (this.shouldListenerRejectSubtypes(method)) {
            return KeySelector.exact(eventType);
        }
        return KeySelector.acceptsSubtypes(eventType);
    }
    
    private boolean shouldListenerRejectSubtypes(final Method method) {
        return method.isAnnotationPresent(RejectSubtypes.class);
    }
    
    private boolean shouldListenerIgnoreCancelled(final Method method) {
        return method.isAnnotationPresent(IgnoreCancelled.class);
    }
    
    private static <T> void createDecorateAndSetListener(final SubscriptionBuilder<T> builder, final Object subscriber, final Method method) {
        builder.listener(createListener(subscriber, method));
    }
    
    private static <T> Listener<T> createListener(final Object subscriber, final Method target) {
        return new MethodInvokingListener<T>(subscriber, target);
    }
    
    private static boolean shouldSynchronizeListener(final Method method) {
        return method.isAnnotationPresent(Synchronize.class);
    }
}
