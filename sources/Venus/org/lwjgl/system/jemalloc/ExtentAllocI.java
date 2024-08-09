/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType(value="extent_alloc_t")
public interface ExtentAllocI
extends CallbackI.P {
    public static final String SIGNATURE = "(ppppppi)p";

    @Override
    default public String getSignature() {
        return SIGNATURE;
    }

    @Override
    default public long callback(long l) {
        return this.invoke(DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgInt(l));
    }

    @NativeType(value="void *")
    public long invoke(@NativeType(value="extent_hooks_t *") long var1, @NativeType(value="void *") long var3, @NativeType(value="size_t") long var5, @NativeType(value="size_t") long var7, @NativeType(value="bool *") long var9, @NativeType(value="bool *") long var11, @NativeType(value="unsigned int") int var13);
}

