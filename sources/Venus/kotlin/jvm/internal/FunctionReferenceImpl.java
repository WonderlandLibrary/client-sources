/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.FunctionReference;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl
extends FunctionReference {
    public FunctionReferenceImpl(int n, KDeclarationContainer kDeclarationContainer, String string, String string2) {
        super(n, NO_RECEIVER, ((ClassBasedDeclarationContainer)kDeclarationContainer).getJClass(), string, string2, kDeclarationContainer instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version="1.4")
    public FunctionReferenceImpl(int n, Class clazz, String string, String string2, int n2) {
        super(n, NO_RECEIVER, clazz, string, string2, n2);
    }

    @SinceKotlin(version="1.4")
    public FunctionReferenceImpl(int n, Object object, Class clazz, String string, String string2, int n2) {
        super(n, object, clazz, string, string2, n2);
    }
}

