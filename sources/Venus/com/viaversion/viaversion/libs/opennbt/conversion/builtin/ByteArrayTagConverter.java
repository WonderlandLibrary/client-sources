/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class ByteArrayTagConverter
implements TagConverter<ByteArrayTag, byte[]> {
    @Override
    public byte[] convert(ByteArrayTag byteArrayTag) {
        return byteArrayTag.getValue();
    }

    @Override
    public ByteArrayTag convert(byte[] byArray) {
        return new ByteArrayTag(byArray);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((byte[])object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((ByteArrayTag)tag);
    }
}

