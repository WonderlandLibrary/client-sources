/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
import java.io.Serializable;

public abstract class AbstractReference2BooleanFunction<K>
implements Reference2BooleanFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected boolean defRetValue;

    protected AbstractReference2BooleanFunction() {
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

