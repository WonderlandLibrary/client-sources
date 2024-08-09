/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class LongTagConverter
implements TagConverter<LongTag, Long> {
    @Override
    public Long convert(LongTag longTag) {
        return longTag.getValue();
    }

    @Override
    public LongTag convert(Long l) {
        return new LongTag(l);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((Long)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((LongTag)tag);
    }
}

