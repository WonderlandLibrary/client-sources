/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Callback;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public interface CallbackProxy
extends Callback {
    public Object callback(Object[] var1);

    public Class<?>[] getParameterTypes();

    public Class<?> getReturnType();
}

