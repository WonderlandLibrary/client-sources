/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType(value="stbi_write_func *")
public interface STBIWriteCallbackI
extends CallbackI.V {
    public static final String SIGNATURE = "(ppi)v";

    @Override
    default public String getSignature() {
        return SIGNATURE;
    }

    @Override
    default public void callback(long l) {
        this.invoke(DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgInt(l));
    }

    public void invoke(@NativeType(value="void *") long var1, @NativeType(value="void *") long var3, int var5);
}

