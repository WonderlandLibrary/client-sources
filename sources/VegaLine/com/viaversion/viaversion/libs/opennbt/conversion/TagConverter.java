/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public interface TagConverter<T extends Tag, V> {
    public V convert(T var1);

    public T convert(V var1);
}

