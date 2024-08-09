/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
import java.io.Serializable;

public abstract class AbstractFloat2BooleanFunction
implements Float2BooleanFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected boolean defRetValue;

    protected AbstractFloat2BooleanFunction() {
    }

    @Override
    public void defaultReturnValue(boolean bl) {
        this.defRetValue = bl;
    }

    @Override
    public boolean defaultReturnValue() {
        return this.defRetValue;
    }
}

