/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class FloatTag
extends Tag {
    private float value;

    public FloatTag(String name) {
        this(name, 0.0f);
    }

    public FloatTag(String name, float value) {
        super(name);
        this.value = value;
    }

    @Override
    public Float getValue() {
        return Float.valueOf(this.value);
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.value = in.readFloat();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeFloat(this.value);
    }

    @Override
    public FloatTag clone() {
        return new FloatTag(this.getName(), this.getValue().floatValue());
    }
}

