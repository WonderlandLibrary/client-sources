/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
import java.io.Serializable;

public abstract class AbstractInt2ByteFunction
implements Int2ByteFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractInt2ByteFunction() {
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

