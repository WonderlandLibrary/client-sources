package io.github.liticane.electron.event.api.handler;

import io.github.liticane.electron.event.Event;

import java.lang.reflect.Method;

public final class MethodHandler {
    private final Object parent;
    private Method method;

    public MethodHandler(Method method, Object parent) {
        this.method = method;
        this.parent = parent;
        if (!this.isValid(this.method)) {
            this.method = null;
            return;
        }
        this.method.setAccessible(true);
    }

    private boolean isValid(Method method) {
        if (method.getParameterCount() == 0) return false;

        Class<?> parameterTypes = method.getParameterTypes()[0];
        Class<?>[] interfaces = parameterTypes.getInterfaces();
        Class<?> superclass = parameterTypes.getSuperclass();

        if (interfaces.length != 0)
            return this.isValid(interfaces[0]);
        if (this.isValid(parameterTypes) || this.isValid(superclass))
            return true;
        return this.isValid(superclass.getInterfaces()[0]);
    }

    private boolean isValid(Class<?> clazz) {
        return clazz.equals(Event.class);
    }


    public void call(final Event event) {
        if (this.method == null) return;
        try {
            this.method.invoke(parent, event);
        } catch (Exception ignored) {}
    }

}