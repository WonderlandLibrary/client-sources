/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event;

import java.lang.reflect.Method;

public class Data {
    public final Object source;
    public final Method target;
    public final byte priority;

    Data(Object source, Method target, byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }
}

