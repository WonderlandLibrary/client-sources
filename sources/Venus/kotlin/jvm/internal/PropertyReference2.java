/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.PropertyReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty2;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class PropertyReference2
extends PropertyReference
implements KProperty2 {
    public PropertyReference2() {
    }

    @SinceKotlin(version="1.4")
    public PropertyReference2(Class clazz, String string, String string2, int n) {
        super(NO_RECEIVER, clazz, string, string2, n);
    }

    @Override
    protected KCallable computeReflected() {
        return Reflection.property2(this);
    }

    @Override
    public Object invoke(Object object, Object object2) {
        return this.get(object, object2);
    }

    public KProperty2.Getter getGetter() {
        return ((KProperty2)this.getReflected()).getGetter();
    }

    @SinceKotlin(version="1.1")
    public Object getDelegate(Object object, Object object2) {
        return ((KProperty2)this.getReflected()).getDelegate(object, object2);
    }

    @Override
    public KProperty.Getter getGetter() {
        return this.getGetter();
    }
}

