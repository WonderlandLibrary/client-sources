/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event;

import java.lang.reflect.Method;

public class MethodData {
    public final Object source;
    public final Method target;
    public final byte priority;

    MethodData(Object source, Method target, byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }
}

