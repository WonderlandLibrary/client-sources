/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import java.lang.reflect.Method;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class CallbackParameterContext
extends FromNativeContext {
    private Method method;
    private Object[] args;
    private int index;

    CallbackParameterContext(Class<?> javaType, Method m, Object[] args, int index) {
        super(javaType);
        this.method = m;
        this.args = args;
        this.index = index;
    }

    public Method getMethod() {
        return this.method;
    }

    public Object[] getArguments() {
        return this.args;
    }

    public int getIndex() {
        return this.index;
    }
}

