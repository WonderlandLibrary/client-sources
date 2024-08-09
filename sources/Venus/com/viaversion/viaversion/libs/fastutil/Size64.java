/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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

    public static long sizeOf(Collection<?> collection) {
        return collection instanceof Size64 ? ((Size64)((Object)collection)).size64() : (long)collection.size();
    }

    public static long sizeOf(Map<?, ?> map) {
        return map instanceof Size64 ? ((Size64)((Object)map)).size64() : (long)map.size();
    }
}

