/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;

public class StringTagConverter
implements TagConverter<StringTag, String> {
    @Override
    public String convert(StringTag tag) {
        return tag.getValue();
    }

    @Override
    public StringTag convert(String value) {
        return new StringTag(value);
    }
}

