/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class LongArrayTag
extends Tag {
    public static final int ID = 12;
    private static final long[] EMPTY_ARRAY = new long[0];
    private long[] value;

    public LongArrayTag() {
        this(EMPTY_ARRAY);
    }

    public LongArrayTag(long[] value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    public long[] getValue() {
        return this.value;
    }

    public void setValue(long[] value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    public long getValue(int index) {
        return this.value[index];
    }

    public void setValue(int index, long value) {
        this.value[index] = value;
    }

    public int length() {
        return this.value.length;
    }

    @Override
    public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
        tagLimiter.countInt();
        this.value = new long[in.readInt()];
        tagLimiter.countBytes(8 * this.value.length);
        for (int index = 0; index < this.value.length; ++index) {
            this.value[index] = in.readLong();
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        for (long l : this.value) {
            out.writeLong(l);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        LongArrayTag that = (LongArrayTag)o;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public final LongArrayTag clone() {
        return new LongArrayTag((long[])this.value.clone());
    }

    @Override
    public int getTagId() {
        return 12;
    }
}

