/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
import java.io.Serializable;

public abstract class AbstractObject2DoubleFunction<K>
implements Object2DoubleFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected double defRetValue;

    protected AbstractObject2DoubleFunction() {
    }

    @Override
    public void defaultReturnValue(double d) {
        this.defRetValue = d;
    }

    @Override
    public double defaultReturnValue() {
        return this.defRetValue;
    }
}

