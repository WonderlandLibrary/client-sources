/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringTag
extends Tag {
    public static final int ID = 8;
    private String value;

    public StringTag() {
        this("");
    }

    public StringTag(String value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = value;
    }

    @Override
    public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
        this.value = in.readUTF();
        tagLimiter.countBytes(2 * this.value.length());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StringTag stringTag = (StringTag)o;
        return this.value.equals(stringTag.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public final StringTag clone() {
        return new StringTag(this.value);
    }

    @Override
    public int getTagId() {
        return 8;
    }
}

