/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import java.io.Serializable;

public abstract class AbstractObject2IntFunction<K>
implements Object2IntFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractObject2IntFunction() {
    }

    @Override
    public void defaultReturnValue(int n) {
        this.defRetValue = n;
    }

    @Override
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}

