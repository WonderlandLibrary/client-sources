/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class IntTagConverter
implements TagConverter<IntTag, Integer> {
    @Override
    public Integer convert(IntTag intTag) {
        return intTag.getValue();
    }

    @Override
    public IntTag convert(Integer n) {
        return new IntTag(n);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((Integer)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((IntTag)tag);
    }
}

