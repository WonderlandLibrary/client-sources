/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.event;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import pw.vertexcode.util.event.Event;

public class ClassData {
    private final Object classInstance;
    private final Method eventMethod;
    private final Event event;

    public ClassData(Object classInstance, Method eventMethod, Event event) {
        this.classInstance = classInstance;
        this.eventMethod = eventMethod;
        this.event = event;
    }

    public Object getClassInstance() {
        return this.classInstance;
    }

    public Method getMethod() {
        return this.eventMethod;
    }

    public Event getEvent() {
        return this.event;
    }

    public void fire() {
        try {
            this.getMethod().invoke(this.getClassInstance(), this.getEvent());
        }
        catch (IllegalAccessException e) {
            System.out.println("Something went wrong while calling the event " + this.getEvent().getClass().getSimpleName());
            System.out.println("Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
        catch (InvocationTargetException e) {
            System.out.println("Something went wrong while calling the event " + this.getEvent().getClass().getSimpleName());
            System.out.println("Exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
    }
}

