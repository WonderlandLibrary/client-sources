/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.io.Serializable;

public abstract class AbstractFloat2FloatFunction
implements Float2FloatFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected float defRetValue;

    protected AbstractFloat2FloatFunction() {
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

