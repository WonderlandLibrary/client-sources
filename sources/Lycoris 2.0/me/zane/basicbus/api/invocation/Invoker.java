/*
 * Decompiled with CFR 0.150.
 */
package me.zane.basicbus.api.invocation;

import java.lang.reflect.Method;

@FunctionalInterface
public interface Invoker {
    public void invoke(Object var1, Method var2, Object ... var3);
}

