/*
 * Decompiled with CFR 0.150.
 */
package me.zane.basicbus.api.bus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.bus.CallLocation;
import me.zane.basicbus.api.invocation.Invoker;

public interface Bus<T> {
    public Invoker invoker();

    public Map<Class<?>, List<CallLocation>> map();

    default public void subscribe(Object subscriber) {
        Method[] methods = subscriber.getClass().getDeclaredMethods();
        Map<Class<?>, List<CallLocation>> eventClassMethodMapRef = this.map();
        for (int i = methods.length - 1; i >= 0; --i) {
            Class<?>[] params;
            int paramsLength;
            Method method = methods[i];
            Listener listener = method.getAnnotation(Listener.class);
            if (listener == null || (paramsLength = (params = method.getParameterTypes()).length) > 1) continue;
            Class<?> eventClass = listener.value();
            if (paramsLength == 1 && eventClass != params[0]) continue;
            CallLocation callLoc = new CallLocation(subscriber, method);
            if (eventClassMethodMapRef.containsKey(eventClass)) {
                eventClassMethodMapRef.get(eventClass).add(callLoc);
                continue;
            }
            eventClassMethodMapRef.put(eventClass, new CopyOnWriteArrayList<CallLocation>(Arrays.asList(callLoc)));
        }
    }

    default public void unsubscribe(Object subscriber) {
        Collection<List<CallLocation>> callLocationsRef = this.map().values();
        for (List<CallLocation> callLocations : callLocationsRef) {
            for (CallLocation callLocation : callLocations) {
                if (callLocation.subscriber != subscriber) continue;
                callLocations.remove(callLocation);
            }
        }
    }

    default public void post(T event) {
        List<CallLocation> callLocations = this.map().get(event.getClass());
        if (callLocations != null) {
            for (CallLocation callLocation : callLocations) {
                Method method = callLocation.method;
                Object sub = callLocation.subscriber;
                if (callLocation.noParams) {
                    this.invoker().invoke(sub, method, new Object[0]);
                    continue;
                }
                this.invoker().invoke(sub, method, event);
            }
        }
    }
}

