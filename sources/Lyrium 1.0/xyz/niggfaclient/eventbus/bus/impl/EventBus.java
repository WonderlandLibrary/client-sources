// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.eventbus.bus.impl;

import java.util.Collections;
import java.util.Iterator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.lang.reflect.ParameterizedType;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import java.util.HashMap;
import java.lang.invoke.MethodHandles;
import xyz.niggfaclient.eventbus.Listener;
import java.util.List;
import java.lang.reflect.Type;
import java.util.Map;
import xyz.niggfaclient.eventbus.bus.Bus;

public final class EventBus<Event> implements Bus<Event>
{
    private final Map<Type, List<CallSite<Event>>> callSiteMap;
    private final Map<Type, List<Listener<Event>>> listenerCache;
    private static final MethodHandles.Lookup LOOKUP;
    
    public EventBus() {
        this.callSiteMap = new HashMap<Type, List<CallSite<Event>>>();
        this.listenerCache = new HashMap<Type, List<Listener<Event>>>();
    }
    
    @Override
    public void subscribe(final Object subscriber) {
        for (final Field field : subscriber.getClass().getDeclaredFields()) {
            final EventLink annotation = field.getAnnotation(EventLink.class);
            if (annotation != null) {
                final Type eventType = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    final Listener<Event> listener = (Listener<Event>)EventBus.LOOKUP.unreflectGetter(field).invokeWithArguments(subscriber);
                    final byte priority = annotation.value();
                    final CallSite<Event> callSite = new CallSite<Event>(subscriber, listener, priority);
                    if (this.callSiteMap.containsKey(eventType)) {
                        final List<CallSite<Event>> callSites = this.callSiteMap.get(eventType);
                        callSites.add(callSite);
                        callSites.sort(Comparator.comparingInt(o -> o.priority));
                    }
                    else {
                        final List<CallSite<Event>> callSites = new ArrayList<CallSite<Event>>(1);
                        callSites.add(callSite);
                        this.callSiteMap.put(eventType, callSites);
                    }
                }
                catch (Throwable t) {}
            }
        }
        this.populateListenerCache();
    }
    
    private void populateListenerCache() {
        final Map<Type, List<CallSite<Event>>> callSiteMap = this.callSiteMap;
        final Map<Type, List<Listener<Event>>> listenerCache = this.listenerCache;
        for (final Type type : callSiteMap.keySet()) {
            final List<CallSite<Event>> callSites = callSiteMap.get(type);
            final int size = callSites.size();
            final List<Listener<Event>> listeners = new ArrayList<Listener<Event>>(size);
            for (int i = 0; i < size; ++i) {
                listeners.add(((CallSite<Object>)callSites.get(i)).listener);
            }
            listenerCache.put(type, listeners);
        }
    }
    
    @Override
    public void unsubscribe(final Object subscriber) {
        for (final List<CallSite<Event>> callSites : this.callSiteMap.values()) {
            callSites.removeIf(eventCallSite -> eventCallSite.owner == subscriber);
        }
        this.populateListenerCache();
    }
    
    @Override
    public void post(final Event event) {
        final List<Listener<Event>> listeners = this.listenerCache.getOrDefault(event.getClass(), Collections.emptyList());
        int listenersSize = listeners.size();
        while (listenersSize > 0) {
            listeners.get(--listenersSize).call(event);
        }
    }
    
    static {
        LOOKUP = MethodHandles.lookup();
    }
    
    private static class CallSite<Event>
    {
        private final Object owner;
        private final Listener<Event> listener;
        private final byte priority;
        
        public CallSite(final Object owner, final Listener<Event> listener, final byte priority) {
            this.owner = owner;
            this.listener = listener;
            this.priority = priority;
        }
    }
}
