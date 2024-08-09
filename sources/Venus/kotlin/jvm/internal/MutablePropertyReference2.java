/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.MutablePropertyReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty2;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class MutablePropertyReference2
extends MutablePropertyReference
implements KMutableProperty2 {
    public MutablePropertyReference2() {
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference2(Class clazz, String string, String string2, int n) {
        super(NO_RECEIVER, clazz, string, string2, n);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.mutableProperty2(this);
    }

    @Override
    public Object invoke(Object object, Object object2) {
        return this.get(object, object2);
    }

    @Override
    public KProperty2.Getter getGetter() {
        return ((KMutableProperty2)this.getReflected()).getGetter();
    }

    public KMutableProperty2.Setter getSetter() {
        return ((KMutableProperty2)this.getReflected()).getSetter();
    }

    @Override
    @SinceKotlin(version="1.1")
    public Object getDelegate(Object object, Object object2) {
        return ((KMutableProperty2)this.getReflected()).getDelegate(object, object2);
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

