/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
import java.io.Serializable;

public abstract class AbstractShort2FloatFunction
implements Short2FloatFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected float defRetValue;

    protected AbstractShort2FloatFunction() {
    }

    @Override
    public void defaultReturnValue(float f) {
        this.defRetValue = f;
    }

    @Override
    public float defaultReturnValue() {
        return this.defRetValue;
    }
}

