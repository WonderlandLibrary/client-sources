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
public class IntTag
extends NumberTag {
    public static final int ID = 3;
    private int value;

    public IntTag() {
        this(0);
    }

    public IntTag(int n) {
        this.value = n;
    }

    @Override
    @Deprecated
    public Integer getValue() {
        return this.value;
    }

    public void setValue(int n) {
        this.value = n;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        tagLimiter.countInt();
        this.value = dataInput.readInt();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.value);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        IntTag intTag = (IntTag)object;
        return this.value == intTag.value;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public final IntTag clone() {
        return new IntTag(this.value);
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
        return this.value;
    }

    @Override
    public long asLong() {
        return this.value;
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

