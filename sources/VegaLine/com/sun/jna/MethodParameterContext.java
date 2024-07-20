/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Function;
import com.sun.jna.FunctionParameterContext;
import java.lang.reflect.Method;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class MethodParameterContext
extends FunctionParameterContext {
    private Method method;

    MethodParameterContext(Function f, Object[] args, int index, Method m) {
        super(f, args, index);
        this.method = m;
    }

    public Method getMethod() {
        return this.method;
    }
}

