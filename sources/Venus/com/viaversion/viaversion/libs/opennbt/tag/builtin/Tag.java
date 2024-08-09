/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class Tag
implements Cloneable {
    public abstract Object getValue();

    public final void read(DataInput dataInput) throws IOException {
        this.read(dataInput, TagLimiter.noop(), 0);
    }

    public final void read(DataInput dataInput, TagLimiter tagLimiter) throws IOException {
        this.read(dataInput, tagLimiter, 0);
    }

    public abstract void read(DataInput var1, TagLimiter var2, int var3) throws IOException;

    public abstract void write(DataOutput var1) throws IOException;

    public abstract int getTagId();

    public abstract Tag clone();

    public String toString() {
        String string = "";
        if (this.getValue() != null) {
            string = this.getValue().toString();
            if (this.getValue().getClass().isArray()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
                for (int i = 0; i < Array.getLength(this.getValue()); ++i) {
                    if (i > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(Array.get(this.getValue(), i));
                }
                stringBuilder.append("]");
                string = stringBuilder.toString();
            }
        }
        return this.getClass().getSimpleName() + " { " + string + " }";
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}

