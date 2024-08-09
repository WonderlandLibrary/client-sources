/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class LongArrayTagConverter
implements TagConverter<LongArrayTag, long[]> {
    @Override
    public long[] convert(LongArrayTag longArrayTag) {
        return longArrayTag.getValue();
    }

    @Override
    public LongArrayTag convert(long[] lArray) {
        return new LongArrayTag(lArray);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((long[])object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((LongArrayTag)tag);
    }
}

