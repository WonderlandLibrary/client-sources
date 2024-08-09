/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class ShortTagConverter
implements TagConverter<ShortTag, Short> {
    @Override
    public Short convert(ShortTag shortTag) {
        return shortTag.getValue();
    }

    @Override
    public ShortTag convert(Short s) {
        return new ShortTag(s);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((Short)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((ShortTag)tag);
    }
}

