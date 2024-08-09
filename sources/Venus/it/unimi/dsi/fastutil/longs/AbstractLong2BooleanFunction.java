/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
import java.io.Serializable;

public abstract class AbstractLong2BooleanFunction
implements Long2BooleanFunction,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected boolean defRetValue;

    protected AbstractLong2BooleanFunction() {
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

