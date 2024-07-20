/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import ru.govno.client.event.ArrayHelper;
import ru.govno.client.event.Data;
import ru.govno.client.event.Event;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.Priority;

public class EventManager {
    private static final Map<Class<? extends Event>, ArrayHelper<Data>> REGISTRY_MAP = new HashMap<Class<? extends Event>, ArrayHelper<Data>>();

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

    private static void register(Method method, Object o) {
        Class<?> clazz = method.getParameterTypes()[0];
        final Data methodData = new Data(o, method, method.getAnnotation(EventTarget.class).value());
        if (!methodData.target.isAccessible()) {
            methodData.target.setAccessible(true);
        }
        if (REGISTRY_MAP.containsKey(clazz)) {
            if (!REGISTRY_MAP.get(clazz).contains(methodData)) {
                REGISTRY_MAP.get(clazz).add(methodData);
                EventManager.sortListValue(clazz);
            }
        } else {
            REGISTRY_MAP.put(clazz, new ArrayHelper<Data>(){
                {
                    this.add(methodData);
                }
            });
        }
    }

    public static void unregister(Object o) {
        for (ArrayHelper<Data> flexibalArray : REGISTRY_MAP.values()) {
            for (Data methodData : flexibalArray) {
                if (!methodData.source.equals(o)) continue;
                flexibalArray.remove(methodData);
            }
        }
        EventManager.cleanMap(true);
    }

    public static void unregister(Object o, Class<? extends Event> clazz) {
        if (REGISTRY_MAP.containsKey(clazz)) {
            for (Data methodData : REGISTRY_MAP.get(clazz)) {
                if (!methodData.source.equals(o)) continue;
                REGISTRY_MAP.get(clazz).remove(methodData);
            }
            EventManager.cleanMap(true);
        }
    }

    public static void cleanMap(boolean b) {
        Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (b && !iterator.next().getValue().isEmpty()) continue;
            iterator.remove();
        }
    }

    public static void removeEnty(Class<? extends Event> clazz) {
        Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getKey().equals(clazz)) continue;
            iterator.remove();
            break;
        }
    }

    private static void sortListValue(Class<? extends Event> clazz) {
        ArrayHelper<Data> flexibleArray = new ArrayHelper<Data>();
        for (byte b : Priority.VALUE_ARRAY) {
            for (Data methodData : REGISTRY_MAP.get(clazz)) {
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
        return EventManager.isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
    }

    public static ArrayHelper<Data> get(Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

    public static void shutdown() {
        REGISTRY_MAP.clear();
    }

    private static final class MethodData {
        private final Object source;
        private final Method target;
        private final byte priority;

        public MethodData(Object source, Method target, byte priority) {
            this.source = source;
            this.target = target;
            this.priority = priority;
        }

        public Object getSource() {
            return this.source;
        }

        public Method getTarget() {
            return this.target;
        }

        public byte getPriority() {
            return this.priority;
        }
    }
}

