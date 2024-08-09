/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
import java.io.Serializable;

public abstract class AbstractFloat2ByteFunction
implements Float2ByteFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected byte defRetValue;

    protected AbstractFloat2ByteFunction() {
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

