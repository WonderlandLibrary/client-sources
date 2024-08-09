/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
import java.io.Serializable;

public abstract class AbstractDouble2LongFunction
implements Double2LongFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractDouble2LongFunction() {
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

