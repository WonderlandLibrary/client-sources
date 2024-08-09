/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

import org.lwjgl.system.Callback;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType(value="WNDPROC")
public interface WindowProcI
extends CallbackI.P {
    public static final String SIGNATURE = Callback.__stdcall("(pipp)p");

    @Override
    default public String getSignature() {
        return SIGNATURE;
    }

    @Override
    default public long callback(long l) {
        return this.invoke(DynCallback.dcbArgPointer(l), DynCallback.dcbArgInt(l), DynCallback.dcbArgPointer(l), DynCallback.dcbArgPointer(l));
    }

    @NativeType(value="LRESULT")
    public long invoke(@NativeType(value="HWND") long var1, @NativeType(value="UINT") int var3, @NativeType(value="WPARAM") long var4, @NativeType(value="LPARAM") long var6);
}

