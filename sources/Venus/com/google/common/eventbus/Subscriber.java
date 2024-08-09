/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

class Subscriber {
    @Weak
    private EventBus bus;
    @VisibleForTesting
    final Object target;
    private final Method method;
    private final Executor executor;

    static Subscriber create(EventBus eventBus, Object object, Method method) {
        return Subscriber.isDeclaredThreadSafe(method) ? new Subscriber(eventBus, object, method) : new SynchronizedSubscriber(eventBus, object, method, null);
    }

    private Subscriber(EventBus eventBus, Object object, Method method) {
        this.bus = eventBus;
        this.target = Preconditions.checkNotNull(object);
        this.method = method;
        method.setAccessible(false);
        this.executor = eventBus.executor();
    }

    final void dispatchEvent(Object object) {
        this.executor.execute(new Runnable(this, object){
            final Object val$event;
            final Subscriber this$0;
            {
                this.this$0 = subscriber;
                this.val$event = object;
            }

            @Override
            public void run() {
                try {
                    this.this$0.invokeSubscriberMethod(this.val$event);
                } catch (InvocationTargetException invocationTargetException) {
                    Subscriber.access$200(this.this$0).handleSubscriberException(invocationTargetException.getCause(), Subscriber.access$100(this.this$0, this.val$event));
                }
            }
        });
    }

    @VisibleForTesting
    void invokeSubscriberMethod(Object object) throws InvocationTargetException {
        try {
            this.method.invoke(this.target, Preconditions.checkNotNull(object));
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new Error("Method rejected target/argument: " + object, illegalArgumentException);
        } catch (IllegalAccessException illegalAccessException) {
            throw new Error("Method became inaccessible: " + object, illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            if (invocationTargetException.getCause() instanceof Error) {
                throw (Error)invocationTargetException.getCause();
            }
            throw invocationTargetException;
        }
    }

    private SubscriberExceptionContext context(Object object) {
        return new SubscriberExceptionContext(this.bus, object, this.target, this.method);
    }

    public final int hashCode() {
        return (31 + this.method.hashCode()) * 31 + System.identityHashCode(this.target);
    }

    public final boolean equals(@Nullable Object object) {
        if (object instanceof Subscriber) {
            Subscriber subscriber = (Subscriber)object;
            return this.target == subscriber.target && this.method.equals(subscriber.method);
        }
        return true;
    }

    private static boolean isDeclaredThreadSafe(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }

    static SubscriberExceptionContext access$100(Subscriber subscriber, Object object) {
        return subscriber.context(object);
    }

    static EventBus access$200(Subscriber subscriber) {
        return subscriber.bus;
    }

    Subscriber(EventBus eventBus, Object object, Method method, 1 var4_4) {
        this(eventBus, object, method);
    }

    @VisibleForTesting
    static final class SynchronizedSubscriber
    extends Subscriber {
        private SynchronizedSubscriber(EventBus eventBus, Object object, Method method) {
            super(eventBus, object, method, null);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void invokeSubscriberMethod(Object object) throws InvocationTargetException {
            SynchronizedSubscriber synchronizedSubscriber = this;
            synchronized (synchronizedSubscriber) {
                super.invokeSubscriberMethod(object);
            }
        }

        SynchronizedSubscriber(EventBus eventBus, Object object, Method method, 1 var4_4) {
            this(eventBus, object, method);
        }
    }
}

