/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.MutablePropertyReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty0;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class MutablePropertyReference0
extends MutablePropertyReference
implements KMutableProperty0 {
    public MutablePropertyReference0() {
    }

    @SinceKotlin(version="1.1")
    public MutablePropertyReference0(Object object) {
        super(object);
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference0(Object object, Class clazz, String string, String string2, int n) {
        super(object, clazz, string, string2, n);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.mutableProperty0(this);
    }

    @Override
    public Object invoke() {
        return this.get();
    }

    @Override
    public KProperty0.Getter getGetter() {
        return ((KMutableProperty0)this.getReflected()).getGetter();
    }

    public KMutableProperty0.Setter getSetter() {
        return ((KMutableProperty0)this.getReflected()).getSetter();
    }

    @Override
    @SinceKotlin(version="1.1")
    public Object getDelegate() {
        return ((KMutableProperty0)this.getReflected()).getDelegate();
    }

    @Override
    public KMutableProperty.Setter getSetter() {
        return this.getSetter();
    }

    @Override
    public KProperty.Getter getGetter() {
        return this.getGetter();
    }
}

