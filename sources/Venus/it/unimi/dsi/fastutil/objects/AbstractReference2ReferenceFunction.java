/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.Reference2ReferenceFunction;
import java.io.Serializable;

public abstract class AbstractReference2ReferenceFunction<K, V>
implements Reference2ReferenceFunction<K, V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractReference2ReferenceFunction() {
    }

    @Override
    public void defaultReturnValue(V v) {
        this.defRetValue = v;
    }

    @Override
    public V defaultReturnValue() {
        return this.defRetValue;
    }
}

