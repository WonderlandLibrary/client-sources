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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LongArrayTag
extends Tag {
    public static final int ID = 12;
    private static final long[] EMPTY_ARRAY = new long[0];
    private long[] value;

    public LongArrayTag() {
        this(EMPTY_ARRAY);
    }

    public LongArrayTag(long[] lArray) {
        if (lArray == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = lArray;
    }

    public long[] getValue() {
        return this.value;
    }

    public void setValue(long[] lArray) {
        if (lArray == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = lArray;
    }

    public long getValue(int n) {
        return this.value[n];
    }

    public void setValue(int n, long l) {
        this.value[n] = l;
    }

    public int length() {
        return this.value.length;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        tagLimiter.countInt();
        this.value = new long[dataInput.readInt()];
        tagLimiter.countBytes(8 * this.value.length);
        for (int i = 0; i < this.value.length; ++i) {
            this.value[i] = dataInput.readLong();
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value.length);
        for (long l : this.value) {
            dataOutput.writeLong(l);
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        LongArrayTag longArrayTag = (LongArrayTag)object;
        return Arrays.equals(this.value, longArrayTag.value);
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
        return 1;
    }

    @Override
    public Tag clone() {
        return this.clone();
    }

    @Override
    public Object getValue() {
        return this.getValue();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

