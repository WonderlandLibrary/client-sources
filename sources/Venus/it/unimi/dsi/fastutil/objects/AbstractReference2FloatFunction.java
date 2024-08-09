/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
import java.io.Serializable;

public abstract class AbstractReference2FloatFunction<K>
implements Reference2FloatFunction<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected float defRetValue;

    protected AbstractReference2FloatFunction() {
    }

    @Override
    public void defaultReturnValue(float f) {
        this.defRetValue = f;
    }

    @Override
    public float defaultReturnValue() {
        return this.defRetValue;
    }
}

