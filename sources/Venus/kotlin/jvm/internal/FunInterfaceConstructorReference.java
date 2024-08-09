/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.FunctionReference;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@SinceKotlin(version="1.7")
public class FunInterfaceConstructorReference
extends FunctionReference
implements Serializable {
    private final Class funInterface;

    public FunInterfaceConstructorReference(Class clazz) {
        super(1);
        this.funInterface = clazz;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof FunInterfaceConstructorReference)) {
            return true;
        }
        FunInterfaceConstructorReference funInterfaceConstructorReference = (FunInterfaceConstructorReference)object;
        return this.funInterface.equals(funInterfaceConstructorReference.funInterface);
    }

    @Override
    public int hashCode() {
        return this.funInterface.hashCode();
    }

    @Override
    public String toString() {
        return "fun interface " + this.funInterface.getName();
    }

    @Override
    protected KFunction getReflected() {
        throw new UnsupportedOperationException("Functional interface constructor does not support reflection");
    }

    @Override
    protected KCallable getReflected() {
        return this.getReflected();
    }
}

