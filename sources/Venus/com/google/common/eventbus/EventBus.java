/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Dispatcher;
import com.google.common.eventbus.Subscriber;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.google.common.eventbus.SubscriberRegistry;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
public class EventBus {
    private static final Logger logger = Logger.getLogger(EventBus.class.getName());
    private final String identifier;
    private final Executor executor;
    private final SubscriberExceptionHandler exceptionHandler;
    private final SubscriberRegistry subscribers = new SubscriberRegistry(this);
    private final Dispatcher dispatcher;

    public EventBus() {
        this("default");
    }

    public EventBus(String string) {
        this(string, MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), LoggingHandler.INSTANCE);
    }

    public EventBus(SubscriberExceptionHandler subscriberExceptionHandler) {
        this("default", MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), subscriberExceptionHandler);
    }

    EventBus(String string, Executor executor, Dispatcher dispatcher, SubscriberExceptionHandler subscriberExceptionHandler) {
        this.identifier = Preconditions.checkNotNull(string);
        this.executor = Preconditions.checkNotNull(executor);
        this.dispatcher = Preconditions.checkNotNull(dispatcher);
        this.exceptionHandler = Preconditions.checkNotNull(subscriberExceptionHandler);
    }

    public final String identifier() {
        return this.identifier;
    }

    final Executor executor() {
        return this.executor;
    }

    void handleSubscriberException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        Preconditions.checkNotNull(throwable);
        Preconditions.checkNotNull(subscriberExceptionContext);
        try {
            this.exceptionHandler.handleException(throwable, subscriberExceptionContext);
        } catch (Throwable throwable2) {
            logger.log(Level.SEVERE, String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", throwable2, throwable), throwable2);
        }
    }

    public void register(Object object) {
        this.subscribers.register(object);
    }

    public void unregister(Object object) {
        this.subscribers.unregister(object);
    }

    public void post(Object object) {
        Iterator<Subscriber> iterator2 = this.subscribers.getSubscribers(object);
        if (iterator2.hasNext()) {
            this.dispatcher.dispatch(object, iterator2);
        } else if (!(object instanceof DeadEvent)) {
            this.post(new DeadEvent(this, object));
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(this.identifier).toString();
    }

    static final class LoggingHandler
    implements SubscriberExceptionHandler {
        static final LoggingHandler INSTANCE = new LoggingHandler();

        LoggingHandler() {
        }

        @Override
        public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
            Logger logger = LoggingHandler.logger(subscriberExceptionContext);
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, LoggingHandler.message(subscriberExceptionContext), throwable);
            }
        }

        private static Logger logger(SubscriberExceptionContext subscriberExceptionContext) {
            return Logger.getLogger(EventBus.class.getName() + "." + subscriberExceptionContext.getEventBus().identifier());
        }

        private static String message(SubscriberExceptionContext subscriberExceptionContext) {
            Method method = subscriberExceptionContext.getSubscriberMethod();
            return "Exception thrown by subscriber method " + method.getName() + '(' + method.getParameterTypes()[0].getName() + ')' + " on subscriber " + subscriberExceptionContext.getSubscriber() + " when dispatching event: " + subscriberExceptionContext.getEvent();
        }
    }
}

