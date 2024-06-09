// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.bus;

import java.lang.annotation.Annotation;
import me.zero.alpine.listener.EventHandler;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Objects;
import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import me.zero.alpine.listener.Listener;
import java.util.List;
import me.zero.alpine.listener.Listenable;
import java.util.Map;

public class EventManager implements EventBus
{
    private final Map<Listenable, List<Listener>> SUBSCRIPTION_CACHE;
    private final Map<Class<?>, List<Listener>> SUBSCRIPTION_MAP;
    
    public EventManager() {
        this.SUBSCRIPTION_CACHE = new ConcurrentHashMap<Listenable, List<Listener>>();
        this.SUBSCRIPTION_MAP = new ConcurrentHashMap<Class<?>, List<Listener>>();
    }
    
    @Override
    public void subscribe(final Listenable listenable) {
        final List<Listener> listeners = this.SUBSCRIPTION_CACHE.computeIfAbsent(listenable, o -> Arrays.stream(o.getClass().getDeclaredFields()).filter(EventManager::isValidField).map(field -> asListener(o, field)).filter(Objects::nonNull).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        listeners.forEach(this::subscribe);
    }
    
    @Override
    public void subscribe(final Listener listener) {
        List<Listener> listeners;
        int index;
        for (listeners = this.SUBSCRIPTION_MAP.computeIfAbsent(listener.getTarget(), target -> new CopyOnWriteArrayList()), index = 0; index < listeners.size() && listener.getPriority() <= listeners.get(index).getPriority(); ++index) {}
        listeners.add(index, listener);
    }
    
    @Override
    public void unsubscribe(final Listenable listenable) {
        final List<Listener> objectListeners = this.SUBSCRIPTION_CACHE.get(listenable);
        if (objectListeners == null) {
            return;
        }
        this.SUBSCRIPTION_MAP.values().forEach(listeners -> listeners.removeIf(objectListeners::contains));
    }
    
    @Override
    public void unsubscribe(final Listener listener) {
        this.SUBSCRIPTION_MAP.get(listener.getTarget()).removeIf(l -> l.equals(listener));
    }
    
    @Override
    public void post(final Object event) {
        final List<Listener> listeners = this.SUBSCRIPTION_MAP.get(event.getClass());
        if (listeners != null) {
            listeners.forEach(listener -> listener.invoke(event));
        }
    }
    
    private static boolean isValidField(final Field field) {
        return field.isAnnotationPresent(EventHandler.class) && Listener.class.isAssignableFrom(field.getType());
    }
    
    private static Listener asListener(final Listenable listenable, final Field field) {
        try {
            final boolean accessible = field.isAccessible();
            field.setAccessible(true);
            final Listener listener = (Listener)field.get(listenable);
            field.setAccessible(accessible);
            if (listener == null) {
                return null;
            }
            return listener;
        }
        catch (IllegalAccessException e) {
            return null;
        }
    }
}
