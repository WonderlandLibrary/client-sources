/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.TagConverter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class ByteTagConverter
implements TagConverter<ByteTag, Byte> {
    @Override
    public Byte convert(ByteTag byteTag) {
        return byteTag.getValue();
    }

    @Override
    public ByteTag convert(Byte by) {
        return new ByteTag(by);
    }

    @Override
    public Tag convert(Object object) {
        return this.convert((Byte)object);
    }

    @Override
    public Object convert(Tag tag) {
        return this.convert((ByteTag)tag);
    }
}

