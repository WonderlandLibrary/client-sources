/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class StringTagConverter
implements TagConverter<StringTag, String> {
    @Override
    public String convert(StringTag stringTag) {
        return stringTag.getValue();
    }

    @Override
    public StringTag convert(String string) {
        return new StringTag(string);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((String)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((StringTag)tag);
    }
}

