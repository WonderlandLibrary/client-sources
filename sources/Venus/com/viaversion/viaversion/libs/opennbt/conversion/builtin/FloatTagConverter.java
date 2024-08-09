/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class FloatTagConverter
implements TagConverter<FloatTag, Float> {
    @Override
    public Float convert(FloatTag floatTag) {
        return floatTag.getValue();
    }

    @Override
    public FloatTag convert(Float f) {
        return new FloatTag(f.floatValue());
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((Float)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((FloatTag)tag);
    }
}

