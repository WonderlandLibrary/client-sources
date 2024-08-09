/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

@SinceKotlin(version="1.4")
public class AdaptedFunctionReference
implements FunctionBase,
Serializable {
    protected final Object receiver;
    private final Class owner;
    private final String name;
    private final String signature;
    private final boolean isTopLevel;
    private final int arity;
    private final int flags;

    public AdaptedFunctionReference(int n, Class clazz, String string, String string2, int n2) {
        this(n, CallableReference.NO_RECEIVER, clazz, string, string2, n2);
    }

    public AdaptedFunctionReference(int n, Object object, Class clazz, String string, String string2, int n2) {
        this.receiver = object;
        this.owner = clazz;
        this.name = string;
        this.signature = string2;
        this.isTopLevel = (n2 & 1) == 1;
        this.arity = n;
        this.flags = n2 >> 1;
    }

    @Override
    public int getArity() {
        return this.arity;
    }

    public KDeclarationContainer getOwner() {
        return this.owner == null ? null : (this.isTopLevel ? Reflection.getOrCreateKotlinPackage(this.owner) : Reflection.getOrCreateKotlinClass(this.owner));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof AdaptedFunctionReference)) {
            return true;
        }
        AdaptedFunctionReference adaptedFunctionReference = (AdaptedFunctionReference)object;
        return this.isTopLevel == adaptedFunctionReference.isTopLevel && this.arity == adaptedFunctionReference.arity && this.flags == adaptedFunctionReference.flags && Intrinsics.areEqual(this.receiver, adaptedFunctionReference.receiver) && Intrinsics.areEqual(this.owner, adaptedFunctionReference.owner) && this.name.equals(adaptedFunctionReference.name) && this.signature.equals(adaptedFunctionReference.signature);
    }

    public int hashCode() {
        int n = this.receiver != null ? this.receiver.hashCode() : 0;
        n = n * 31 + (this.owner != null ? this.owner.hashCode() : 0);
        n = n * 31 + this.name.hashCode();
        n = n * 31 + this.signature.hashCode();
        n = n * 31 + (this.isTopLevel ? 1231 : 1237);
        n = n * 31 + this.arity;
        n = n * 31 + this.flags;
        return n;
    }

    public String toString() {
        return Reflection.renderLambdaToString(this);
    }
}

