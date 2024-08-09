/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2LongFunction;
import java.io.Serializable;

public abstract class AbstractChar2LongFunction
implements Char2LongFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractChar2LongFunction() {
    }

    @Override
    public void defaultReturnValue(long l) {
        this.defRetValue = l;
    }

    @Override
    public long defaultReturnValue() {
        return this.defRetValue;
    }
}

