/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil;

import java.util.Collection;
import java.util.Map;

public interface Size64 {
    public long size64();

    @Deprecated
    default public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }

    public static long sizeOf(Collection<?> c) {
        long l;
        if (c instanceof Size64) {
            l = ((Size64)((Object)c)).size64();
            return l;
        }
        l = c.size();
        return l;
    }

    public static long sizeOf(Map<?, ?> m) {
        long l;
        if (m instanceof Size64) {
            l = ((Size64)((Object)m)).size64();
            return l;
        }
        l = m.size();
        return l;
    }
}

