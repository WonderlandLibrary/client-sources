/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.conversion;

import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public interface TagConverter<T extends Tag, V> {
    public V convert(T var1);

    public T convert(String var1, V var2);
}

