/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.ToNativeContext;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public interface ToNativeConverter {
    public Object toNative(Object var1, ToNativeContext var2);

    public Class<?> nativeType();
}

