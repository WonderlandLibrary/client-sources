/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FunctionReference
extends CallableReference
implements FunctionBase,
KFunction {
    private final int arity;
    @SinceKotlin(version="1.4")
    private final int flags;

    public FunctionReference(int n) {
        this(n, NO_RECEIVER, null, null, null, 0);
    }

    @SinceKotlin(version="1.1")
    public FunctionReference(int n, Object object) {
        this(n, object, null, null, null, 0);
    }

    @SinceKotlin(version="1.4")
    public FunctionReference(int n, Object object, Class clazz, String string, String string2, int n2) {
        super(object, clazz, string, string2, (n2 & 1) == 1);
        this.arity = n;
        this.flags = n2 >> 1;
    }

    @Override
    public int getArity() {
        return this.arity;
    }

    @Override
    @SinceKotlin(version="1.1")
    protected KFunction getReflected() {
        return (KFunction)super.getReflected();
    }

    @Override
    @SinceKotlin(version="1.1")
    protected KCallable computeReflected() {
        return Reflection.function(this);
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isInline() {
        return this.getReflected().isInline();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isExternal() {
        return this.getReflected().isExternal();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isOperator() {
        return this.getReflected().isOperator();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isInfix() {
        return this.getReflected().isInfix();
    }

    @Override
    @SinceKotlin(version="1.1")
    public boolean isSuspend() {
        return this.getReflected().isSuspend();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof FunctionReference) {
            FunctionReference functionReference = (FunctionReference)object;
            return this.getName().equals(functionReference.getName()) && this.getSignature().equals(functionReference.getSignature()) && this.flags == functionReference.flags && this.arity == functionReference.arity && Intrinsics.areEqual(this.getBoundReceiver(), functionReference.getBoundReceiver()) && Intrinsics.areEqual(this.getOwner(), functionReference.getOwner());
        }
        if (object instanceof KFunction) {
            return object.equals(this.compute());
        }
        return true;
    }

    public int hashCode() {
        return ((this.getOwner() == null ? 0 : this.getOwner().hashCode() * 31) + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
    }

    public String toString() {
        KCallable kCallable = this.compute();
        if (kCallable != this) {
            return kCallable.toString();
        }
        return "<init>".equals(this.getName()) ? "constructor (Kotlin reflection is not available)" : "function " + this.getName() + " (Kotlin reflection is not available)";
    }

    @Override
    @SinceKotlin(version="1.1")
    protected KCallable getReflected() {
        return this.getReflected();
    }
}

