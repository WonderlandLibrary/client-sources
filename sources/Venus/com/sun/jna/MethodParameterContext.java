/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.FunctionParameterContext;
import java.lang.reflect.Method;

public class MethodParameterContext
extends FunctionParameterContext {
    private Method method;

    MethodParameterContext(Function function, Object[] objectArray, int n, Method method) {
        super(function, objectArray, n);
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }
}

