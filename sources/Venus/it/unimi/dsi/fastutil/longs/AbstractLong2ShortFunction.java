/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
import java.io.Serializable;

public abstract class AbstractLong2ShortFunction
implements Long2ShortFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected short defRetValue;

    protected AbstractLong2ShortFunction() {
    }

    @Override
    public void defaultReturnValue(short s) {
        this.defRetValue = s;
    }

    @Override
    public short defaultReturnValue() {
        return this.defRetValue;
    }
}

