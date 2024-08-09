/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType(value="unsigned char * (*) (unsigned char *, int, int *, int)")
public interface STBIZlibCompressI
extends CallbackI.P {
    public static final String SIGNATURE = "(pipi)p";

    @Override
    default public String getSignature() {
        return SIGNATURE;
    }

    @Override
    default public long callback(long l) {
        return this.invoke(DynCallback.dcbArgPointer(l), DynCallback.dcbArgInt(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgInt(l));
    }

    @NativeType(value="unsigned char *")
    public long invoke(@NativeType(value="unsigned char *") long var1, int var3, @NativeType(value="int *") long var4, int var6);
}

