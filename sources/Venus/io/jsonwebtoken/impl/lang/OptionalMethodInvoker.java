/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.ReflectionFunction;
import io.jsonwebtoken.lang.Classes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OptionalMethodInvoker<T, R>
extends ReflectionFunction<T, R> {
    private final Class<?> CLASS;
    private final Method METHOD;
    private final Class<?>[] PARAM_TYPES;
    private final boolean STATIC;

    public OptionalMethodInvoker(String string, String string2) {
        this(string, string2, null, false);
    }

    public OptionalMethodInvoker(String string, String string2, Class<?> clazz, boolean bl) {
        Class[] classArray;
        Class clazz2 = null;
        Method method = null;
        if (clazz != null) {
            Class[] classArray2 = new Class[1];
            classArray = classArray2;
            classArray2[0] = clazz;
        } else {
            classArray = null;
        }
        Class[] classArray3 = classArray;
        try {
            clazz2 = Classes.forName(string);
            method = clazz2.getMethod(string2, classArray3);
        } catch (Throwable throwable) {
            // empty catch block
        }
        this.CLASS = clazz2;
        this.METHOD = method;
        this.PARAM_TYPES = classArray3;
        this.STATIC = bl;
    }

    @Override
    protected boolean supports(T t) {
        Class<?> clazz = null;
        if (this.CLASS != null && this.METHOD != null) {
            clazz = this.STATIC && this.PARAM_TYPES != null ? this.PARAM_TYPES[0] : this.CLASS;
        }
        return clazz != null && clazz.isInstance(t);
    }

    @Override
    protected R invoke(T t) throws InvocationTargetException, IllegalAccessException {
        return (R)(this.STATIC ? this.METHOD.invoke(null, t) : this.METHOD.invoke(t, new Object[0]));
    }
}

