package fr.dog.event;

import fr.dog.event.annotations.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventBus {
    private static final Map<Class<? extends Event>, List<MethodData>> LISTENERS = new HashMap<>();

    public void register(Object object) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method))
                continue;

            register(method, object);
        }
    }

    public void register(Object object, Class<? extends Event> eventClass) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method, eventClass))
                continue;

            register(method, object);
        }
    }

    public void unregister(Object object) {
        for (final List<MethodData> dataList : LISTENERS.values())
            dataList.removeIf(data -> data.source().equals(object));

        cleanMap(true);
    }

    @SuppressWarnings("unchecked")
    private void register(Method method, Object object) {
        Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];

        final MethodData data = new MethodData(object, method, method.getAnnotation(SubscribeEvent.class).value());

        if (!data.target().isAccessible())
            data.target().setAccessible(true);

        if (LISTENERS.containsKey(indexClass)) {
            if (!LISTENERS.get(indexClass).contains(data)) {
                LISTENERS.get(indexClass).add(data);
                sortListValue(indexClass);
            }
        } else {
            LISTENERS.put(indexClass, new CopyOnWriteArrayList<>() {{
                add(data);
            }});
        }
    }

    public void cleanMap(boolean onlyEmptyEntries) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = LISTENERS.entrySet().iterator();

        while (mapIterator.hasNext())
            if (!onlyEmptyEntries || mapIterator.next().getValue().isEmpty())
                mapIterator.remove();
    }

    private void sortListValue(Class<? extends Event> indexClass) {
        List<MethodData> sortedList = new CopyOnWriteArrayList<>();

        for (final byte priority : EventPriority.VALUE_ARRAY)
            for (final MethodData data : LISTENERS.get(indexClass))
                if (data.priority() == priority)
                    sortedList.add(data);

        LISTENERS.put(indexClass, sortedList);
    }

    private boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(SubscribeEvent.class);
    }

    private boolean isMethodBad(Method method, Class<? extends Event> eventClass) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(eventClass);
    }

    public Event post(final Event event) {
        List<MethodData> dataList = LISTENERS.get(event.getClass());

        if (dataList != null)
            for (final MethodData data : dataList)
                invoke(data, event);

        return event;
    }

    private void invoke(MethodData data, Event argument) {
        try {
            data.target().invoke(data.source(), argument);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
            /* */
        }
    }

    private record MethodData(Object source, Method target, byte priority) { /* */ }
}