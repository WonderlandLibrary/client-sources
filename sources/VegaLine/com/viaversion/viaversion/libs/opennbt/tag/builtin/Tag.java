/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

public abstract class Tag
implements Cloneable {
    public abstract Object getValue();

    public final void read(DataInput in) throws IOException {
        this.read(in, TagLimiter.noop(), 0);
    }

    public final void read(DataInput in, TagLimiter tagLimiter) throws IOException {
        this.read(in, tagLimiter, 0);
    }

    public abstract void read(DataInput var1, TagLimiter var2, int var3) throws IOException;

    public abstract void write(DataOutput var1) throws IOException;

    public abstract int getTagId();

    public abstract Tag clone();

    public String toString() {
        String value = "";
        if (this.getValue() != null) {
            value = this.getValue().toString();
            if (this.getValue().getClass().isArray()) {
                StringBuilder build = new StringBuilder();
                build.append("[");
                for (int index = 0; index < Array.getLength(this.getValue()); ++index) {
                    if (index > 0) {
                        build.append(", ");
                    }
                    build.append(Array.get(this.getValue(), index));
                }
                build.append("]");
                value = build.toString();
            }
        }
        return this.getClass().getSimpleName() + " { " + value + " }";
    }
}

