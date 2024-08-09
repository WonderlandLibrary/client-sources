/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2ShortFunction;
import java.io.Serializable;

public abstract class AbstractObject2ShortFunction<K>
implements Object2ShortFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected short defRetValue;

    protected AbstractObject2ShortFunction() {
    }

    @Override
    public void defaultReturnValue(short s) {
        this.defRetValue = s;
    }

    @Override
    public short defaultReturnValue() {
        return this.defRetValue;
    }
}

