/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.opennbt.tag.builtin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class LongTag
extends Tag {
    private long value;

    public LongTag(String name) {
        this(name, 0L);
    }

    public LongTag(String name, long value) {
        super(name);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public void read(DataInput in) throws IOException {
        this.value = in.readLong();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(this.value);
    }

    @Override
    public LongTag clone() {
        return new LongTag(this.getName(), this.getValue());
    }
}

