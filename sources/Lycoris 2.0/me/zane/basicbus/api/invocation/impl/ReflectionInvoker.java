/*
 * Decompiled with CFR 0.150.
 */
package me.zane.basicbus.api.invocation.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.zane.basicbus.api.invocation.Invoker;

public final class ReflectionInvoker
implements Invoker {
    @Override
    public void invoke(Object instance, Method method, Object ... parameters) {
        try {
            method.invoke(instance, parameters);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // empty catch block
        }
    }
}

