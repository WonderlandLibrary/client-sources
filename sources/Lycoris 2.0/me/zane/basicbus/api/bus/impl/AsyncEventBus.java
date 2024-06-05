/*
 * Decompiled with CFR 0.150.
 */
package me.zane.basicbus.api.bus.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.bus.Bus;
import me.zane.basicbus.api.bus.CallLocation;
import me.zane.basicbus.api.invocation.Invoker;

public final class AsyncEventBus<T>
implements Bus<T> {
    private final Map<Class<?>, List<CallLocation>> eventClassMethodMap = new HashMap();
    private final Invoker invoker;

    public AsyncEventBus(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Invoker invoker() {
        return this.invoker;
    }

    @Override
    public Map<Class<?>, List<CallLocation>> map() {
        return this.eventClassMethodMap;
    }

    @Override
    public void subscribe(Object subscriber) {
        Method[] methods = subscriber.getClass().getDeclaredMethods();
        Map<Class<?>, List<CallLocation>> eventClassMethodMapRef = this.eventClassMethodMap;
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
            eventClassMethodMapRef.put(eventClass, Arrays.asList(callLoc));
        }
    }
}

