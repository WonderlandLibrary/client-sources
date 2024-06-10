package me.sleepyfish.smok.rats.event;

import java.lang.reflect.Method;

// Class from SMok Client by SleepyFish
public class EventHelp {
    public final Object obj;
    public final Method meth;
    public final byte byta;

    public EventHelp(Object obj, Method meth, byte byta) {
        this.obj = obj;
        this.meth = meth;
        this.byta = byta;
    }
}
