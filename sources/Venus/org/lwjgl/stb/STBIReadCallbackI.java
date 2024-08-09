/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType(value="int (*) (void *, char *, int)")
public interface STBIReadCallbackI
extends CallbackI.I {
    public static final String SIGNATURE = "(ppi)i";

    @Override
    default public String getSignature() {
        return SIGNATURE;
    }

    @Override
    default public int callback(long l) {
        return this.invoke(DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgInt(l));
    }

    public int invoke(@NativeType(value="void *") long var1, @NativeType(value="char *") long var3, int var5);
}

