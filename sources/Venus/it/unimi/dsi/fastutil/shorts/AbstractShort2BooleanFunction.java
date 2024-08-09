/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
import java.io.Serializable;

public abstract class AbstractShort2BooleanFunction
implements Short2BooleanFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected boolean defRetValue;

    protected AbstractShort2BooleanFunction() {
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

