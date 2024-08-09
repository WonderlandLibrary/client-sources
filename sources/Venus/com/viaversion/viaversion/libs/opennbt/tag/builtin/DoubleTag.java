/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleTag
extends NumberTag {
    public static final int ID = 6;
    private double value;

    public DoubleTag() {
        this(0.0);
    }

    public DoubleTag(double d) {
        this.value = d;
    }

    @Override
    @Deprecated
    public Double getValue() {
        return this.value;
    }

    public void setValue(double d) {
        this.value = d;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        tagLimiter.countDouble();
        this.value = dataInput.readDouble();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.value);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        DoubleTag doubleTag = (DoubleTag)object;
        return this.value == doubleTag.value;
    }

    public int hashCode() {
        return Double.hashCode(this.value);
    }

    @Override
    public final DoubleTag clone() {
        return new DoubleTag(this.value);
    }

    @Override
    public byte asByte() {
        return (byte)this.value;
    }

    @Override
    public short asShort() {
        return (short)this.value;
    }

    @Override
    public int asInt() {
        return (int)this.value;
    }

    @Override
    public long asLong() {
        return (long)this.value;
    }

    @Override
    public float asFloat() {
        return (float)this.value;
    }

    @Override
    public double asDouble() {
        return this.value;
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
    @Deprecated
    public Object getValue() {
        return this.getValue();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

