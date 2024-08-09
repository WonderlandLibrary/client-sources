package dev.excellent.api.event;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.excellent.api.interfaces.event.IEventProxy;
import dev.excellent.api.interfaces.event.Listener;
import i.gishreloaded.protection.annotation.Native;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class EventBus<T> implements IEventProxy {

    // Добавим кэши для хранения результатов вызовов getEventType и getCallback
    private final Cache<Field, Type> eventTypeCache = Caffeine.newBuilder().build();
    private final Cache<Field, Listener<T>> callbackCache = Caffeine.newBuilder().build();

    private final Map<Type, Set<EventData<T>>> eventDataMap = new HashMap<>();
    private final Map<Type, Set<Listener<T>>> eventCallbackMap = new HashMap<>();

    @Native
    @Override
    public void register(Object subscriber) {
        Arrays.stream(subscriber.getClass().getDeclaredFields())
                .filter(field -> field.getType() == Listener.class)
                .forEach(field -> {
                    Type eventType = getEventType(field);
                    Listener<T> callback = getCallback(subscriber, field);

                    eventDataMap.computeIfAbsent(eventType, key -> new ObjectArraySet<>())
                            .add(new EventData<>(subscriber, callback));
                });

        updateCallbacks();
    }

    private Type getEventType(Field field) {
        // Попробуем получить значение из кэша
        return eventTypeCache.get(field, key -> ((ParameterizedType) key.getGenericType()).getActualTypeArguments()[0]);
    }

    private Listener<T> getCallback(Object subscriber, Field field) {
        // Попробуем получить значение из кэша
        return callbackCache.get(field, key -> {
            boolean accessible = key.canAccess(subscriber);
            if (!accessible) {
                key.setAccessible(true);
            }

            try {
                Object fieldValue = key.get(subscriber);
                if (fieldValue instanceof Listener<?> listener) {
                    return (Listener<T>) listener;
                } else {
                    throw new RuntimeException("Unexpected field type");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error getting field value", e);
            } finally {
                key.setAccessible(accessible);
            }
        });
    }

    @Native
    @Override
    public void unregister(Object subscriber) {
        eventDataMap.values().forEach(eventDataList -> eventDataList.removeIf(eventData -> eventData.getParent() == subscriber));
        updateCallbacks();
    }

    @Native
    @Override
    public void updateCallbacks() {
        eventCallbackMap.clear();
        eventDataMap.forEach((eventType, eventDataList) ->
                eventCallbackMap.put(eventType, eventDataList.stream()
                        .map(EventData::getCallback)
                        .collect(Collectors.toSet())));
    }

    public void handle(T event) {
        eventCallbackMap.getOrDefault(event.getClass(), Collections.emptySet())
                .forEach(callback -> callback.handle(event));
    }
}