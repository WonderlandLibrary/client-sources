/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
import java.io.Serializable;

public abstract class AbstractReference2LongFunction<K>
implements Reference2LongFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractReference2LongFunction() {
    }

    @Override
    public void defaultReturnValue(long l) {
        this.defRetValue = l;
    }

    @Override
    public long defaultReturnValue() {
        return this.defRetValue;
    }
}

