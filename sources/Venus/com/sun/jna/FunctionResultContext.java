/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Function;

public class FunctionResultContext
extends FromNativeContext {
    private Function function;
    private Object[] args;

    FunctionResultContext(Class<?> clazz, Function function, Object[] objectArray) {
        super(clazz);
        this.function = function;
        this.args = objectArray;
    }

    public Function getFunction() {
        return this.function;
    }

    public Object[] getArguments() {
        return this.args;
    }
}

