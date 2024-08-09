/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType(value="GLFWerrorfun")
public interface GLFWErrorCallbackI
extends CallbackI.V {
    public static final String SIGNATURE = "(ip)v";

    @Override
    default public String getSignature() {
        return SIGNATURE;
    }

    @Override
    default public void callback(long l) {
        this.invoke(DynCallback.dcbArgInt(l), DynCallback.dcbArgPointer(l));
    }

    public void invoke(int var1, @NativeType(value="char *") long var2);
}

