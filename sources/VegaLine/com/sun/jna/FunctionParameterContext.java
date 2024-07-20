/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.ToNativeContext;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class FunctionParameterContext
extends ToNativeContext {
    private Function function;
    private Object[] args;
    private int index;

    FunctionParameterContext(Function f, Object[] args, int index) {
        this.function = f;
        this.args = args;
        this.index = index;
    }

    public Function getFunction() {
        return this.function;
    }

    public Object[] getParameters() {
        return this.args;
    }

    public int getParameterIndex() {
        return this.index;
    }
}

