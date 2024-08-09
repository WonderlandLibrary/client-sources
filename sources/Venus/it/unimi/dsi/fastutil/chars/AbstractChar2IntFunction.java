/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2IntFunction;
import java.io.Serializable;

public abstract class AbstractChar2IntFunction
implements Char2IntFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractChar2IntFunction() {
    }

    @Override
    public void defaultReturnValue(int n) {
        this.defRetValue = n;
    }

    @Override
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}

