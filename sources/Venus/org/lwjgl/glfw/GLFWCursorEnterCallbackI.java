/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.dyncall.DynCallback;

@FunctionalInterface
@NativeType(value="GLFWcursorenterfun")
public interface GLFWCursorEnterCallbackI
extends CallbackI.V {
    public static final String SIGNATURE = "(pi)v";

    @Override
    default public String getSignature() {
        return SIGNATURE;
    }

    @Override
    default public void callback(long l) {
        this.invoke(DynCallback.dcbArgPointer(l), DynCallback.dcbArgInt(l) != 0);
    }

    public void invoke(@NativeType(value="GLFWwindow *") long var1, @NativeType(value="int") boolean var3);
}

