package pw.stamina.causam.scan.method;

import pw.stamina.causam.scan.*;
import pw.stamina.causam.scan.method.factory.*;
import java.util.*;
import pw.stamina.causam.scan.result.*;
import pw.stamina.causam.scan.method.validate.*;
import java.lang.reflect.*;
import pw.stamina.causam.subscribe.*;
import pw.stamina.causam.scan.method.model.*;
import java.lang.annotation.*;

public final class MethodSubscriberScanningStrategy implements SubscriberScanningStrategy
{
    private static final MethodSubscriberScanningStrategy STANDARD;
    private final SubscriberMethodValidator validator;
    private final MethodBasedSubscriptionFactory subscriptionFactory;
    
    private MethodSubscriberScanningStrategy(final SubscriberMethodValidator validator, final MethodBasedSubscriptionFactory subscriptionFactory) {
        this.validator = validator;
        this.subscriptionFactory = subscriptionFactory;
    }
    
    @Override
    public ScanResult scan(final Object subscriber) {
        Objects.requireNonNull(subscriber, "subscriber input cannot be null");
        final Method[] methods = subscriber.getClass().getDeclaredMethods();
        final ScanResultBuilder resultBuilder = new ScanResultBuilder();
        for (final Method method : methods) {
            Label_0116: {
                if (this.isSubscriberMethod(method)) {
                    try {
                        this.validator.validate(method);
                    }
                    catch (IllegalSubscriberMethodException e) {
                        e.printStackTrace();
                        break Label_0116;
                    }
                    final Class<?> eventType = method.getParameterTypes()[0];
                    final Subscription<?> subscription = this.subscriptionFactory.createSubscription(subscriber, method, eventType);
                    resultBuilder.addSubscription(subscription);
                }
            }
        }
        return resultBuilder.build();
    }
    
    private boolean isSubscriberMethod(final Method method) {
        return method.isAnnotationPresent(Subscriber.class);
    }
    
    public static MethodSubscriberScanningStrategy standard() {
        return MethodSubscriberScanningStrategy.STANDARD;
    }
    
    static {
        STANDARD = new MethodSubscriberScanningStrategy(SubscriberMethodValidator.standard(), MethodBasedSubscriptionFactory.standard());
    }
}
