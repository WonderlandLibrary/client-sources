/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2ShortFunction;
import java.io.Serializable;

public abstract class AbstractShort2ShortFunction
implements Short2ShortFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected short defRetValue;

    protected AbstractShort2ShortFunction() {
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

