package ez.h.event;

import java.lang.reflect.*;
import java.lang.annotation.*;
import java.util.*;

public class EventManager
{
    private final Map<Class<? extends Event>, ArrayHelper<Data>> REGISTRY_MAP;
    
    public void shutdown() {
        this.REGISTRY_MAP.clear();
    }
    
    private boolean isMethodBad(final Method method, final Class<? extends Event> clazz) {
        return this.isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
    }
    
    public void register(final Object o) {
        for (final Method method : o.getClass().getDeclaredMethods()) {
            if (!this.isMethodBad(method)) {
                this.register(method, o);
            }
        }
    }
    
    private boolean isMethodBad(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }
    
    public void cleanMap(final boolean b) {
        final Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = this.REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!b || iterator.next().getValue().isEmpty()) {
                iterator.remove();
            }
        }
    }
    
    private void sortListValue(final Class<? extends Event> clazz) {
        final ArrayHelper<Data> arrayHelper = new ArrayHelper<Data>();
        for (final byte b : Priority.VALUE_ARRAY) {
            for (final Data data : this.REGISTRY_MAP.get(clazz)) {
                if (data.priority == b) {
                    arrayHelper.add(data);
                }
            }
        }
        this.REGISTRY_MAP.put(clazz, arrayHelper);
    }
    
    public void register(final Object o, final Class<? extends Event> clazz) {
        for (final Method method : o.getClass().getDeclaredMethods()) {
            if (!this.isMethodBad(method, clazz)) {
                this.register(method, o);
            }
        }
    }
    
    public ArrayHelper<Data> get(final Class<? extends Event> clazz) {
        return this.REGISTRY_MAP.get(clazz);
    }
    
    public EventManager() {
        this.REGISTRY_MAP = new HashMap<Class<? extends Event>, ArrayHelper<Data>>();
    }
    
    public void removeEnty(final Class<? extends Event> clazz) {
        final Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = this.REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey().equals(clazz)) {
                iterator.remove();
                break;
            }
        }
    }
    
    public void unregister(final Object o) {
        for (final ArrayHelper<Data> arrayHelper : this.REGISTRY_MAP.values()) {
            for (final Data data : arrayHelper) {
                if (data.source.equals(o)) {
                    arrayHelper.remove(data);
                }
            }
        }
        this.cleanMap(true);
    }
    
    private void register(final Method method, final Object o) {
        final Class<?> clazz = method.getParameterTypes()[0];
        final Data data = new Data(o, method, method.getAnnotation(EventTarget.class).value());
        if (!data.target.isAccessible()) {
            data.target.setAccessible(true);
        }
        if (this.REGISTRY_MAP.containsKey(clazz)) {
            if (!this.REGISTRY_MAP.get(clazz).contains(data)) {
                this.REGISTRY_MAP.get(clazz).add(data);
                this.sortListValue((Class<? extends Event>)clazz);
            }
        }
        else {
            this.REGISTRY_MAP.put((Class<? extends Event>)clazz, new ArrayHelper<Data>() {
                {
                    this.add(data);
                }
            });
        }
    }
    
    public void unregister(final Object o, final Class<? extends Event> clazz) {
        if (this.REGISTRY_MAP.containsKey(clazz)) {
            for (final Data data : this.REGISTRY_MAP.get(clazz)) {
                if (data.source.equals(o)) {
                    this.REGISTRY_MAP.get(clazz).remove(data);
                }
            }
            this.cleanMap(true);
        }
    }
}
