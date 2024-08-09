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
public class FloatTag
extends NumberTag {
    public static final int ID = 5;
    private float value;

    public FloatTag() {
        this(0.0f);
    }

    public FloatTag(float f) {
        this.value = f;
    }

    @Override
    @Deprecated
    public Float getValue() {
        return Float.valueOf(this.value);
    }

    public void setValue(float f) {
        this.value = f;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        tagLimiter.countFloat();
        this.value = dataInput.readFloat();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.value);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        FloatTag floatTag = (FloatTag)object;
        return this.value == floatTag.value;
    }

    public int hashCode() {
        return Float.hashCode(this.value);
    }

    @Override
    public final FloatTag clone() {
        return new FloatTag(this.value);
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
        return this.value;
    }

    @Override
    public double asDouble() {
        return this.value;
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
    @Deprecated
    public Object getValue() {
        return this.getValue();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

