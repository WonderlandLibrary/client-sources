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
public class ByteTag
extends NumberTag {
    public static final int ID = 1;
    private byte value;

    public ByteTag() {
        this(0);
    }

    public ByteTag(byte by) {
        this.value = by;
    }

    @Override
    @Deprecated
    public Byte getValue() {
        return this.value;
    }

    public void setValue(byte by) {
        this.value = by;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        tagLimiter.countByte();
        this.value = dataInput.readByte();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.value);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ByteTag byteTag = (ByteTag)object;
        return this.value == byteTag.value;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public final ByteTag clone() {
        return new ByteTag(this.value);
    }

    @Override
    public byte asByte() {
        return this.value;
    }

    @Override
    public short asShort() {
        return this.value;
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

