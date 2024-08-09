/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import java.io.Serializable;

public abstract class AbstractInt2LongFunction
implements Int2LongFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractInt2LongFunction() {
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

