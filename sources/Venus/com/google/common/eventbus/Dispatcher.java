/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.common.eventbus.Subscriber;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class Dispatcher {
    Dispatcher() {
    }

    static Dispatcher perThreadDispatchQueue() {
        return new PerThreadQueuedDispatcher(null);
    }

    static Dispatcher legacyAsync() {
        return new LegacyAsyncDispatcher(null);
    }

    static Dispatcher immediate() {
        return ImmediateDispatcher.access$200();
    }

    abstract void dispatch(Object var1, Iterator<Subscriber> var2);

    private static final class ImmediateDispatcher
    extends Dispatcher {
        private static final ImmediateDispatcher INSTANCE = new ImmediateDispatcher();

        private ImmediateDispatcher() {
        }

        @Override
        void dispatch(Object object, Iterator<Subscriber> iterator2) {
            Preconditions.checkNotNull(object);
            while (iterator2.hasNext()) {
                iterator2.next().dispatchEvent(object);
            }
        }

        static ImmediateDispatcher access$200() {
            return INSTANCE;
        }
    }

    private static final class LegacyAsyncDispatcher
    extends Dispatcher {
        private final ConcurrentLinkedQueue<EventWithSubscriber> queue = Queues.newConcurrentLinkedQueue();

        private LegacyAsyncDispatcher() {
        }

        @Override
        void dispatch(Object object, Iterator<Subscriber> iterator2) {
            EventWithSubscriber eventWithSubscriber;
            Preconditions.checkNotNull(object);
            while (iterator2.hasNext()) {
                this.queue.add(new EventWithSubscriber(object, iterator2.next(), null));
            }
            while ((eventWithSubscriber = this.queue.poll()) != null) {
                EventWithSubscriber.access$800(eventWithSubscriber).dispatchEvent(EventWithSubscriber.access$700(eventWithSubscriber));
            }
        }

        LegacyAsyncDispatcher(1 var1_1) {
            this();
        }

        private static final class EventWithSubscriber {
            private final Object event;
            private final Subscriber subscriber;

            private EventWithSubscriber(Object object, Subscriber subscriber) {
                this.event = object;
                this.subscriber = subscriber;
            }

            EventWithSubscriber(Object object, Subscriber subscriber, 1 var3_3) {
                this(object, subscriber);
            }

            static Object access$700(EventWithSubscriber eventWithSubscriber) {
                return eventWithSubscriber.event;
            }

            static Subscriber access$800(EventWithSubscriber eventWithSubscriber) {
                return eventWithSubscriber.subscriber;
            }
        }
    }

    private static final class PerThreadQueuedDispatcher
    extends Dispatcher {
        private final ThreadLocal<Queue<Event>> queue = new ThreadLocal<Queue<Event>>(this){
            final PerThreadQueuedDispatcher this$0;
            {
                this.this$0 = perThreadQueuedDispatcher;
            }

            @Override
            protected Queue<Event> initialValue() {
                return Queues.newArrayDeque();
            }

            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };
        private final ThreadLocal<Boolean> dispatching = new ThreadLocal<Boolean>(this){
            final PerThreadQueuedDispatcher this$0;
            {
                this.this$0 = perThreadQueuedDispatcher;
            }

            @Override
            protected Boolean initialValue() {
                return false;
            }

            @Override
            protected Object initialValue() {
                return this.initialValue();
            }
        };

        private PerThreadQueuedDispatcher() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void dispatch(Object object, Iterator<Subscriber> iterator2) {
            Preconditions.checkNotNull(object);
            Preconditions.checkNotNull(iterator2);
            Queue<Event> queue = this.queue.get();
            queue.offer(new Event(object, iterator2, null));
            if (!this.dispatching.get().booleanValue()) {
                this.dispatching.set(true);
                try {
                    Event event;
                    while ((event = queue.poll()) != null) {
                        while (Event.access$400(event).hasNext()) {
                            ((Subscriber)Event.access$400(event).next()).dispatchEvent(Event.access$500(event));
                        }
                    }
                } finally {
                    this.dispatching.remove();
                    this.queue.remove();
                }
            }
        }

        PerThreadQueuedDispatcher(1 var1_1) {
            this();
        }

        private static final class Event {
            private final Object event;
            private final Iterator<Subscriber> subscribers;

            private Event(Object object, Iterator<Subscriber> iterator2) {
                this.event = object;
                this.subscribers = iterator2;
            }

            Event(Object object, Iterator iterator2, 1 var3_3) {
                this(object, iterator2);
            }

            static Iterator access$400(Event event) {
                return event.subscribers;
            }

            static Object access$500(Event event) {
                return event.event;
            }
        }
    }
}

