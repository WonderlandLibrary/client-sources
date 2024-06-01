package best.actinium.event.api;

import best.actinium.util.IAccess;
import best.actinium.event.impl.render.BloomEvent;
import best.actinium.event.impl.render.BlurEvent;
import best.actinium.event.impl.render.Render2DEvent;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventManager<Event> implements IAccess {

    private static final Object GLOBAL_LISTENERS_BACKING_OBJECT = new Object();
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final MethodType LISTENER_INVOKE_TYPE = MethodType.methodType(void.class, Object.class);

    private final Map<Class<?>, List<EventCallback<Event>>> eventTypeListenerCache = new HashMap<>();
    private final Map<Object, List<TypedListener>> subscriberMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends Event> void subscribe(Class<T> event, EventCallback<T> eventCallback) {
        this.eventTypeListenerCache.computeIfAbsent(event, key -> new ArrayList<>()).add((EventCallback<Event>) eventCallback);
        this.subscriberMap.computeIfAbsent(GLOBAL_LISTENERS_BACKING_OBJECT, key -> new ArrayList<>())
            .add(new TypedListener(event, (EventCallback<Event>) eventCallback));
    }

    @SuppressWarnings("unchecked")
    public void subscribe(Object subscriber) {
        MethodType factoryType = MethodType.methodType(EventCallback.class, subscriber.getClass());

        for (Method method : subscriber.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Callback.class)) {
                int modifiers = method.getModifiers();
                Class<?>[] paramTypes = method.getParameterTypes();

                if (!Modifier.isPublic(modifiers) || Modifier.isAbstract(modifiers) || method.getReturnType() != void.class || paramTypes.length != 1) {
                    continue;
                }

                Class<?> event = paramTypes[0];

                try {
                    MethodHandle handle = LOOKUP.unreflect(method);
                    CallSite site = LambdaMetafactory.metafactory(LOOKUP, "invoke",
                                                                  factoryType,
                                                                  LISTENER_INVOKE_TYPE,
                                                                  handle,
                                                                  MethodType.methodType(void.class, event));
                    EventCallback<Event> eventCallback = (EventCallback<Event>) site.getTarget().invoke(subscriber);
                    this.eventTypeListenerCache.computeIfAbsent(event, key -> new ArrayList<>()).add(eventCallback);
                    this.subscriberMap.computeIfAbsent(subscriber, key -> new ArrayList<>()).add(new TypedListener(event, eventCallback));
                } catch (Throwable ignored) {
                    ;
                }
            }
        }
    }

    public void unsubscribe(Object subscriber) {
        this.subscriberMap.remove(subscriber);
        this.eventTypeListenerCache.clear();
        this.subscriberMap.values()
                .forEach(typedListeners -> typedListeners.forEach(typedListener -> this.eventTypeListenerCache.computeIfAbsent(typedListener.type, key -> new ArrayList<>())
                .add(typedListener.eventCallback)));
    }

    public void publish(Event event) {
        if (
                (event instanceof BloomEvent || event instanceof BlurEvent || event instanceof Render2DEvent)
                || (mc.thePlayer != null || mc.theWorld != null)
        ) {
            List<EventCallback<Event>> eventCallbacks = this.eventTypeListenerCache.get(event.getClass());

            if (eventCallbacks == null)
                return;

            int listenersSize = eventCallbacks.size();

            while (listenersSize > 0) {
                eventCallbacks.get(--listenersSize).invoke(event);
            }
        }
    }

    public void clear() {
        this.subscriberMap.clear();
        this.eventTypeListenerCache.clear();
    }

    private class TypedListener {
        private final Class<?> type;
        private final EventCallback<Event> eventCallback;

        public TypedListener(Class<?> type, EventCallback<Event> eventCallback) {
            this.type = type;
            this.eventCallback = eventCallback;
        }

        @Override
        public String toString() {
            return String.format("TypedListener { type: %s, listener: %s }", this.type.getSimpleName(), this.eventCallback);
        }
    }
}