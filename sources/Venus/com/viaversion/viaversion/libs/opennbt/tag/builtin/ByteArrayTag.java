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
public class ByteArrayTag
extends Tag {
    public static final int ID = 7;
    private static final byte[] EMPTY_ARRAY = new byte[0];
    private byte[] value;

    public ByteArrayTag() {
        this(EMPTY_ARRAY);
    }

    public ByteArrayTag(byte[] byArray) {
        this.value = byArray;
    }

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] byArray) {
        if (byArray == null) {
            return;
        }
        this.value = byArray;
    }

    public byte getValue(int n) {
        return this.value[n];
    }

    public void setValue(int n, byte by) {
        this.value[n] = by;
    }

    public int length() {
        return this.value.length;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        tagLimiter.countInt();
        this.value = new byte[dataInput.readInt()];
        tagLimiter.countBytes(this.value.length);
        dataInput.readFully(this.value);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value.length);
        dataOutput.write(this.value);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ByteArrayTag byteArrayTag = (ByteArrayTag)object;
        return Arrays.equals(this.value, byteArrayTag.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public final ByteArrayTag clone() {
        return new ByteArrayTag(this.value);
    }

    @Override
    public int getTagId() {
        return 0;
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

