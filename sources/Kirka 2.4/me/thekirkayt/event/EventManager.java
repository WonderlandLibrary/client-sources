/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.FlexibleArray;
import me.thekirkayt.event.MethodData;
import me.thekirkayt.event.Priority;

public final class EventManager {
    private static final Map<Class<? extends Event>, FlexibleArray<MethodData>> REGISTRY_MAP = new HashMap<Class<? extends Event>, FlexibleArray<MethodData>>();

    public static void register(Object o) {
        for (Method method : o.getClass().getDeclaredMethods()) {
            if (EventManager.isMethodBad(method)) continue;
            EventManager.register(method, o);
        }
    }

    public static void register(Object o, Class<? extends Event> clazz) {
        for (Method method : o.getClass().getDeclaredMethods()) {
            if (EventManager.isMethodBad(method, clazz)) continue;
            EventManager.register(method, o);
        }
    }

    public static void unregister(Object o) {
        for (FlexibleArray<MethodData> flexibleArray : REGISTRY_MAP.values()) {
            for (MethodData methodData : flexibleArray) {
                if (!methodData.source.equals(o)) continue;
                flexibleArray.remove(methodData);
            }
        }
        EventManager.cleanMap(true);
    }

    public static void unregister(Object o, Class<? extends Event> clazz) {
        if (REGISTRY_MAP.containsKey(clazz)) {
            for (MethodData methodData : REGISTRY_MAP.get(clazz)) {
                if (!methodData.source.equals(o)) continue;
                REGISTRY_MAP.get(clazz).remove(methodData);
            }
            EventManager.cleanMap(true);
        }
    }

    private static void register(Method method, Object o) {
        Class<?> clazz = method.getParameterTypes()[0];
        MethodData methodData = new MethodData(o, method, method.getAnnotation(EventTarget.class).value());
        if (!methodData.target.isAccessible()) {
            methodData.target.setAccessible(true);
        }
        if (REGISTRY_MAP.containsKey(clazz)) {
            if (!REGISTRY_MAP.get(clazz).contains(methodData)) {
                REGISTRY_MAP.get(clazz).add(methodData);
                EventManager.sortListValue(clazz);
            }
        } else {
            REGISTRY_MAP.put(clazz, new FlexibleArray<MethodData>(methodData){
                {
                    this.add(methodData);
                }
            });
        }
    }

    public static void removeEntry(Class<? extends Event> clazz) {
        Iterator<Map.Entry<Class<? extends Event>, FlexibleArray<MethodData>>> iterator = REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getKey().equals(clazz)) continue;
            iterator.remove();
            break;
        }
    }

    public static void cleanMap(boolean b) {
        Iterator<Map.Entry<Class<? extends Event>, FlexibleArray<MethodData>>> iterator = REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (b && !iterator.next().getValue().isEmpty()) continue;
            iterator.remove();
        }
    }

    private static void sortListValue(Class<? extends Event> clazz) {
        FlexibleArray<MethodData> flexibleArray = new FlexibleArray<MethodData>();
        for (byte b : Priority.VALUE_ARRAY) {
            for (MethodData methodData : REGISTRY_MAP.get(clazz)) {
                if (methodData.priority != b) continue;
                flexibleArray.add(methodData);
            }
        }
        REGISTRY_MAP.put(clazz, flexibleArray);
    }

    private static boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    private static boolean isMethodBad(Method method, Class<? extends Event> clazz) {
        return EventManager.isMethodBad(method) || !method.getParameterTypes()[0].equals(clazz);
    }

    public static FlexibleArray<MethodData> get(Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

    public static void shutdown() {
        REGISTRY_MAP.clear();
    }

}

