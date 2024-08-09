/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class DoubleTagConverter
implements TagConverter<DoubleTag, Double> {
    @Override
    public Double convert(DoubleTag doubleTag) {
        return doubleTag.getValue();
    }

    @Override
    public DoubleTag convert(Double d) {
        return new DoubleTag(d);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((Double)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((DoubleTag)tag);
    }
}

