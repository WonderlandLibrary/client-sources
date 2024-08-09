/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.ToNativeContext;

public interface ToNativeConverter {
    public Object toNative(Object var1, ToNativeContext var2);

    public Class<?> nativeType();
}

