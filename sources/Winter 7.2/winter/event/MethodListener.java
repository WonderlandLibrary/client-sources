/*
 * Decompiled with CFR 0_122.
 */
package winter.event;

import java.lang.reflect.Method;
import winter.event.Event;

public class MethodListener {
    private Object parent;
    private int priority = 2;
    private Class<? extends Event> eventClass;
    private Method method;

    public MethodListener(Object parent, Class<? extends Event> eventClass, Method method, int priority) {
        this.parent = parent;
        this.eventClass = eventClass;
        this.method = method;
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public Object getParent() {
        return this.parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Class<? extends Event> getEventClass() {
        return this.eventClass;
    }

    public void setEventClass(Class<? extends Event> eventClass) {
        this.eventClass = eventClass;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

