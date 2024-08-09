/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringTag
extends Tag {
    public static final int ID = 8;
    private String value;

    public StringTag() {
        this("");
    }

    public StringTag(String string) {
        if (string == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = string;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(String string) {
        if (string == null) {
            throw new NullPointerException("value cannot be null");
        }
        this.value = string;
    }

    @Override
    public void read(DataInput dataInput, TagLimiter tagLimiter, int n) throws IOException {
        this.value = dataInput.readUTF();
        tagLimiter.countBytes(2 * this.value.length());
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.value);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        StringTag stringTag = (StringTag)object;
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

