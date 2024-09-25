/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.conversion.builtin.custom;

import us.myles.viaversion.libs.opennbt.conversion.TagConverter;
import us.myles.viaversion.libs.opennbt.tag.builtin.custom.DoubleArrayTag;

public class DoubleArrayTagConverter
implements TagConverter<DoubleArrayTag, double[]> {
    @Override
    public double[] convert(DoubleArrayTag tag) {
        return tag.getValue();
    }

    @Override
    public DoubleArrayTag convert(String name, double[] value) {
        return new DoubleArrayTag(name, value);
    }
}

