/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Beta
public final class Reflection {
    public static String getPackageName(Class<?> clazz) {
        return Reflection.getPackageName(clazz.getName());
    }

    public static String getPackageName(String string) {
        int n = string.lastIndexOf(46);
        return n < 0 ? "" : string.substring(0, n);
    }

    public static void initialize(Class<?> ... classArray) {
        for (Class<?> clazz : classArray) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            } catch (ClassNotFoundException classNotFoundException) {
                throw new AssertionError((Object)classNotFoundException);
            }
        }
    }

    public static <T> T newProxy(Class<T> clazz, InvocationHandler invocationHandler) {
        Preconditions.checkNotNull(invocationHandler);
        Preconditions.checkArgument(clazz.isInterface(), "%s is not an interface", clazz);
        Object object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invocationHandler);
        return clazz.cast(object);
    }

    private Reflection() {
    }
}

