package com.google.common.eventbus;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@Beta
public class AsyncEventBus extends EventBus
{
    private final Executor executor;
    private final ConcurrentLinkedQueue<EventWithSubscriber> eventsToDispatch;
    
    public AsyncEventBus(final String identifier, final Executor executor) {
        super(identifier);
        this.eventsToDispatch = new ConcurrentLinkedQueue<EventWithSubscriber>();
        this.executor = Preconditions.checkNotNull(executor);
    }
    
    public AsyncEventBus(final Executor executor, final SubscriberExceptionHandler subscriberExceptionHandler) {
        super(subscriberExceptionHandler);
        this.eventsToDispatch = new ConcurrentLinkedQueue<EventWithSubscriber>();
        this.executor = Preconditions.checkNotNull(executor);
    }
    
    public AsyncEventBus(final Executor executor) {
        super("default");
        this.eventsToDispatch = new ConcurrentLinkedQueue<EventWithSubscriber>();
        this.executor = Preconditions.checkNotNull(executor);
    }
    
    @Override
    void enqueueEvent(final Object event, final EventSubscriber subscriber) {
        this.eventsToDispatch.offer(new EventWithSubscriber(event, subscriber));
    }
    
    protected void dispatchQueuedEvents() {
        while (true) {
            final EventWithSubscriber eventWithSubscriber = this.eventsToDispatch.poll();
            if (eventWithSubscriber == null) {
                break;
            }
            this.dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
        }
    }
    
    @Override
    void dispatch(final Object event, final EventSubscriber subscriber) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(subscriber);
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                EventBus.this.dispatch(event, subscriber);
            }
        });
    }
}
