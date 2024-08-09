/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.PropertyReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class PropertyReference1
extends PropertyReference
implements KProperty1 {
    public PropertyReference1() {
    }

    @SinceKotlin(version="1.1")
    public PropertyReference1(Object object) {
        super(object);
    }

    @SinceKotlin(version="1.4")
    public PropertyReference1(Object object, Class clazz, String string, String string2, int n) {
        super(object, clazz, string, string2, n);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.property1(this);
    }

    @Override
    public Object invoke(Object object) {
        return this.get(object);
    }

    public KProperty1.Getter getGetter() {
        return ((KProperty1)this.getReflected()).getGetter();
    }

    @SinceKotlin(version="1.1")
    public Object getDelegate(Object object) {
        return ((KProperty1)this.getReflected()).getDelegate(object);
    }

    @Override
    public KProperty.Getter getGetter() {
        return this.getGetter();
    }
}

