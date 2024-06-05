// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event;

import java.util.Iterator;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Type;
import java.util.Map;

public final class EventDispatcher<T>
{
    private final Map<Type, List<EventListenerStorage<T>>> storageMap;
    private final Map<Type, List<EventListener<T>>> callbackMap;
    
    public EventDispatcher() {
        this.storageMap = new HashMap<Type, List<EventListenerStorage<T>>>();
        this.callbackMap = new HashMap<Type, List<EventListener<T>>>();
    }
    
    public void register(final Object listeningObject) {
        for (final Field declaredField : listeningObject.getClass().getDeclaredFields()) {
            try {
                if (declaredField.getType() == EventListener.class) {
                    final boolean accessible = declaredField.isAccessible();
                    if (!accessible) {
                        declaredField.setAccessible(true);
                    }
                    final Type type = ((ParameterizedType)declaredField.getGenericType()).getActualTypeArguments()[0];
                    final EventListener<T> callback = (EventListener<T>)declaredField.get(listeningObject);
                    declaredField.setAccessible(accessible);
                    if (this.storageMap.containsKey(type)) {
                        final List<EventListenerStorage<T>> storages = this.storageMap.get(type);
                        storages.add(new EventListenerStorage<T>(listeningObject, callback));
                    }
                    else {
                        this.storageMap.put(type, new ArrayList<EventListenerStorage<T>>(Collections.singletonList(new EventListenerStorage(listeningObject, callback))));
                    }
                }
            }
            catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.updateCallbacks();
    }
    
    public void unregister(final Object listeningObject) {
        for (final List<EventListenerStorage<T>> value : this.storageMap.values()) {
            value.removeIf(eventListenerStorage -> eventListenerStorage.owner() == listeningObject);
        }
        this.updateCallbacks();
    }
    
    public void dispatch(final T t) {
        final List<EventListener<T>> callbacks = this.callbackMap.get(t.getClass());
        if (callbacks != null) {
            for (final EventListener<T> callback : callbacks) {
                callback.call(t);
            }
        }
    }
    
    private void updateCallbacks() {
        for (final Type type : this.storageMap.keySet()) {
            final List<EventListenerStorage<T>> storages = this.storageMap.get(type);
            final List<EventListener<T>> callbacks = new ArrayList<EventListener<T>>(storages.size());
            for (final EventListenerStorage<T> storage : storages) {
                callbacks.add(storage.callback());
            }
            this.callbackMap.put(type, callbacks);
        }
    }
}
