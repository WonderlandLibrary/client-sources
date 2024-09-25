/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class IntTag
extends Tag {
    private int value;

    public IntTag(String name) {
        this(name, 0);
    }

    public IntTag(String name, int value) {
        super(name);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.value = in.readInt();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value);
    }

    @Override
    public IntTag clone() {
        return new IntTag(this.getName(), this.getValue());
    }
}

