/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.objects;

import java.io.Serializable;
import us.myles.viaversion.libs.fastutil.objects.Object2IntFunction;

public abstract class AbstractObject2IntFunction<K>
implements Object2IntFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractObject2IntFunction() {
    }

    @Override
    public void defaultReturnValue(int rv) {
        this.defRetValue = rv;
    }

    @Override
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}

