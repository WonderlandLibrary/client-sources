/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.MutablePropertyReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class MutablePropertyReference1
extends MutablePropertyReference
implements KMutableProperty1 {
    public MutablePropertyReference1() {
    }

    @SinceKotlin(version="1.1")
    public MutablePropertyReference1(Object object) {
        super(object);
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference1(Object object, Class clazz, String string, String string2, int n) {
        super(object, clazz, string, string2, n);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.mutableProperty1(this);
    }

    @Override
    public Object invoke(Object object) {
        return this.get(object);
    }

    @Override
    public KProperty1.Getter getGetter() {
        return ((KMutableProperty1)this.getReflected()).getGetter();
    }

    public KMutableProperty1.Setter getSetter() {
        return ((KMutableProperty1)this.getReflected()).getSetter();
    }

    @Override
    @SinceKotlin(version="1.1")
    public Object getDelegate(Object object) {
        return ((KMutableProperty1)this.getReflected()).getDelegate(object);
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

