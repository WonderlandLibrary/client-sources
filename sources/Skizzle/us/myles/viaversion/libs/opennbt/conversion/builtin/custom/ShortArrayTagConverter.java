/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.conversion.builtin.custom;

import us.myles.viaversion.libs.opennbt.conversion.TagConverter;
import us.myles.viaversion.libs.opennbt.tag.builtin.custom.ShortArrayTag;

public class ShortArrayTagConverter
implements TagConverter<ShortArrayTag, short[]> {
    @Override
    public short[] convert(ShortArrayTag tag) {
        return tag.getValue();
    }

    @Override
    public ShortArrayTag convert(String name, short[] value) {
        return new ShortArrayTag(name, value);
    }
}

