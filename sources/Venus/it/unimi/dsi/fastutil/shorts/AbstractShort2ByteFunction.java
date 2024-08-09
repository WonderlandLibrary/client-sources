/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
import java.io.Serializable;

public abstract class AbstractShort2ByteFunction
implements Short2ByteFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractShort2ByteFunction() {
    }

    @Override
    public void defaultReturnValue(byte by) {
        this.defRetValue = by;
    }

    @Override
    public byte defaultReturnValue() {
        return this.defRetValue;
    }
}

