/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.ints;

import java.io.Serializable;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectFunction;

public abstract class AbstractInt2ObjectFunction<V>
implements Int2ObjectFunction<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractInt2ObjectFunction() {
    }

    @Override
    public void defaultReturnValue(V rv) {
        this.defRetValue = rv;
    }

    @Override
    public V defaultReturnValue() {
        return this.defRetValue;
    }
}

