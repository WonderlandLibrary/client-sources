package dev.vertic.event.api;

import dev.vertic.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {

    private final ArrayList<Object> registeredObjects = new ArrayList<>();
    private final CopyOnWriteArrayList<RegisterableMethod> registeredMethods = new CopyOnWriteArrayList<>();

    public void register(final Object instance) {
        if (!registeredObjects.contains(instance)) {
            registeredObjects.add(instance);
        }
        updateRegisteredMethods();
    }

    public void unregister(final Object instance) {
        if (registeredObjects.contains(instance)) {
            registeredObjects.remove(instance);
        }
        updateRegisteredMethods();
    }

    public void call(final Event event) {
        registeredMethods.forEach(
                m -> Arrays.stream(
                        m.method.getParameters()
                ).filter(
                        p -> p.getType().equals(event.getClass())
                ).forEach(
                        p -> {
                            try {
                                m.method.invoke(m.instance, event);
                            } catch (InvocationTargetException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
        );
    }

    private void updateRegisteredMethods() {
        registeredMethods.clear();
        registeredObjects.forEach(o -> Arrays.stream(o.getClass().getMethods()).filter(m -> m.isAnnotationPresent(EventLink.class) && m.getParameters().length == 1).forEach(m -> registeredMethods.add(new RegisterableMethod(m, o))));
        registeredMethods.sort(Comparator.comparingInt(m -> m.method.getAnnotation(EventLink.class).value()));
    }


    private class RegisterableMethod {
        private final Method method;
        private final Object instance;

        public RegisterableMethod(final Method method, final Object instance) {
            this.method = method;
            this.instance = instance;
        }

    }

}