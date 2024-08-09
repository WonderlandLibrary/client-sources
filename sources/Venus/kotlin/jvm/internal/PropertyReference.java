/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class PropertyReference
extends CallableReference
implements KProperty {
    private final boolean syntheticJavaProperty;

    public PropertyReference() {
        this.syntheticJavaProperty = false;
    }

    @SinceKotlin(version="1.1")
    public PropertyReference(Object object) {
        super(object);
        this.syntheticJavaProperty = false;
    }

    @SinceKotlin(version="1.4")
    public PropertyReference(Object object, Class clazz, String string, String string2, int n) {
        super(object, clazz, string, string2, (n & 1) == 1);
        this.syntheticJavaProperty = (n & 2) == 2;
    }

    @Override
    @SinceKotlin(version="1.1")
    protected KProperty getReflected() {
        if (this.syntheticJavaProperty) {
            throw new UnsupportedOperationException("Kotlin reflection is not yet supported for synthetic Java properties");
        }
        return (KProperty)super.getReflected();
    }

    @Override
    public KCallable compute() {
        return this.syntheticJavaProperty ? this : super.compute();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isLateinit() {
        return this.getReflected().isLateinit();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isConst() {
        return this.getReflected().isConst();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof PropertyReference) {
            PropertyReference propertyReference = (PropertyReference)object;
            return this.getOwner().equals(propertyReference.getOwner()) && this.getName().equals(propertyReference.getName()) && this.getSignature().equals(propertyReference.getSignature()) && Intrinsics.areEqual(this.getBoundReceiver(), propertyReference.getBoundReceiver());
        }
        if (object instanceof KProperty) {
            return object.equals(this.compute());
        }
        return true;
    }

    public int hashCode() {
        return (this.getOwner().hashCode() * 31 + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
    }

    public String toString() {
        KCallable kCallable = this.compute();
        if (kCallable != this) {
            return kCallable.toString();
        }
        return "property " + this.getName() + " (Kotlin reflection is not available)";
    }

    @Override
    @SinceKotlin(version="1.1")
    protected KCallable getReflected() {
        return this.getReflected();
    }
}

