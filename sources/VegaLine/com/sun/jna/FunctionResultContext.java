/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Function;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class FunctionResultContext
extends FromNativeContext {
    private Function function;
    private Object[] args;

    FunctionResultContext(Class<?> resultClass, Function function, Object[] args) {
        super(resultClass);
        this.function = function;
        this.args = args;
    }

    public Function getFunction() {
        return this.function;
    }

    public Object[] getArguments() {
        return this.args;
    }
}

