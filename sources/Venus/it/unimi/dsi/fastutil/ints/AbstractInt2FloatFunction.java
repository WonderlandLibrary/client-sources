/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
import java.io.Serializable;

public abstract class AbstractInt2FloatFunction
implements Int2FloatFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected float defRetValue;

    protected AbstractInt2FloatFunction() {
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

