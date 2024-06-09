/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.thekirkayt.event.EventManager;
import me.thekirkayt.event.FlexibleArray;
import me.thekirkayt.event.MethodData;

public abstract class Event {
    private boolean cancelled;

    public Event call() {
        this.cancelled = false;
        Event.call(this);
        return this;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean state) {
        this.cancelled = state;
    }

    private static final void call(Event event) {
        FlexibleArray<MethodData> dataList = EventManager.get(event.getClass());
        if (dataList != null) {
            for (MethodData data : dataList) {
                try {
                    data.target.invoke(data.source, event);
                }
                catch (IllegalAccessException e2) {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because it's not accessible.");
                }
                catch (IllegalArgumentException e3) {
                    System.out.println("Can't invoke '" + data.target.getName() + "' because the parameter/s don't match.");
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static enum State {
        PRE("PRE", 0),
        POST("POST", 1);
        

        private State(String s, int n2) {
        }
    }

}

