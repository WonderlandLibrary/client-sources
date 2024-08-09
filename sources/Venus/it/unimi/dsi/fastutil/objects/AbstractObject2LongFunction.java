/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2LongFunction;
import java.io.Serializable;

public abstract class AbstractObject2LongFunction<K>
implements Object2LongFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractObject2LongFunction() {
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

