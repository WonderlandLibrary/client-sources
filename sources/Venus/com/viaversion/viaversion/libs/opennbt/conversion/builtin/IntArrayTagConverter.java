/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class IntArrayTagConverter
implements TagConverter<IntArrayTag, int[]> {
    @Override
    public int[] convert(IntArrayTag intArrayTag) {
        return intArrayTag.getValue();
    }

    @Override
    public IntArrayTag convert(int[] nArray) {
        return new IntArrayTag(nArray);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((int[])object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((IntArrayTag)tag);
    }
}

