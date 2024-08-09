/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.PropertyReference;
import kotlin.reflect.KMutableProperty;

public abstract class MutablePropertyReference
extends PropertyReference
implements KMutableProperty {
    public MutablePropertyReference() {
    }

    @SinceKotlin(version="1.1")
    public MutablePropertyReference(Object object) {
        super(object);
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference(Object object, Class clazz, String string, String string2, int n) {
        super(object, clazz, string, string2, n);
    }
}

