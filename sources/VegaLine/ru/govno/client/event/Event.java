/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event;

import java.lang.reflect.InvocationTargetException;
import ru.govno.client.event.ArrayHelper;
import ru.govno.client.event.Data;
import ru.govno.client.event.EventManager;
import ru.govno.client.utils.Command.impl.Panic;

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

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    private static final void call(Event event) {
        if (Panic.stop) {
            return;
        }
        ArrayHelper<Data> dataList = EventManager.get(event.getClass());
        if (dataList != null) {
            for (Data data : dataList) {
                try {
                    data.target.invoke(data.source, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static enum State {
        PRE("PRE", 0),
        POST("POST", 1);


        private State(String string2, int number) {
        }
    }
}

