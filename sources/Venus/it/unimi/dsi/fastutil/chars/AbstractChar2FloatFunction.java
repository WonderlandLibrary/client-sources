/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
import java.io.Serializable;

public abstract class AbstractChar2FloatFunction
implements Char2FloatFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected float defRetValue;

    protected AbstractChar2FloatFunction() {
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

