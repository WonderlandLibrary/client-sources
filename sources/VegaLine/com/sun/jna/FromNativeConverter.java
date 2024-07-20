/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.FromNativeContext;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public interface FromNativeConverter {
    public Object fromNative(Object var1, FromNativeContext var2);

    public Class<?> nativeType();
}

