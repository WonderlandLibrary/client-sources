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
public class IntArrayTag
extends Tag {
    public static final int ID = 11;
    private static final int[] EMPTY_ARRAY = new int[0];
    private int[] value;

    public IntArrayTag() {
        this(EMPTY_ARRAY);
    }

    public IntArrayTag(int[] nArray) {
        if (nArray == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = nArray;
    }

    public int[] getValue() {
        return this.value;
    }

    public void setValue(int[] nArray) {
        if (nArray == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = nArray;
    }

    public int getValue(int n) {
        return this.value[n];
    }

    public void setValue(int n, int n2) {
        this.value[n] = n2;
    }

    public int length() {
        return this.value.length;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        tagLimiter.countInt();
        this.value = new int[dataInput.readInt()];
        tagLimiter.countBytes(4 * this.value.length);
        for (int i = 0; i < this.value.length; ++i) {
            this.value[i] = dataInput.readInt();
        }
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value.length);
        for (int n : this.value) {
            dataOutput.writeInt(n);
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        IntArrayTag intArrayTag = (IntArrayTag)object;
        return Arrays.equals(this.value, intArrayTag.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public final IntArrayTag clone() {
        return new IntArrayTag((int[])this.value.clone());
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

