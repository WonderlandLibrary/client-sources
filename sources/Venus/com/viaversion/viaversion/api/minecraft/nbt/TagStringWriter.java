/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.api.minecraft.nbt.Tokens;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.DoubleTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.FloatTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ShortTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

final class TagStringWriter
implements AutoCloseable {
    private final Appendable out;
    private int level;
    private boolean needsSeparator;

    public TagStringWriter(Appendable appendable) {
        this.out = appendable;
    }

    public TagStringWriter writeTag(Tag tag) throws IOException {
        if (tag instanceof CompoundTag) {
            return this.writeCompound((CompoundTag)tag);
        }
        if (tag instanceof ListTag) {
            return this.writeList((ListTag)tag);
        }
        if (tag instanceof ByteArrayTag) {
            return this.writeByteArray((ByteArrayTag)tag);
        }
        if (tag instanceof IntArrayTag) {
            return this.writeIntArray((IntArrayTag)tag);
        }
        if (tag instanceof LongArrayTag) {
            return this.writeLongArray((LongArrayTag)tag);
        }
        if (tag instanceof StringTag) {
            return this.value(((StringTag)tag).getValue(), '\u0000');
        }
        if (tag instanceof ByteTag) {
            return this.value(Byte.toString(((NumberTag)tag).asByte()), 'b');
        }
        if (tag instanceof ShortTag) {
            return this.value(Short.toString(((NumberTag)tag).asShort()), 's');
        }
        if (tag instanceof IntTag) {
            return this.value(Integer.toString(((NumberTag)tag).asInt()), 'i');
        }
        if (tag instanceof LongTag) {
            return this.value(Long.toString(((NumberTag)tag).asLong()), Character.toUpperCase('l'));
        }
        if (tag instanceof FloatTag) {
            return this.value(Float.toString(((NumberTag)tag).asFloat()), 'f');
        }
        if (tag instanceof DoubleTag) {
            return this.value(Double.toString(((NumberTag)tag).asDouble()), 'd');
        }
        throw new IOException("Unknown tag type: " + tag.getClass().getSimpleName());
    }

    private TagStringWriter writeCompound(CompoundTag compoundTag) throws IOException {
        this.beginCompound();
        for (Map.Entry<String, Tag> entry : compoundTag.entrySet()) {
            this.key(entry.getKey());
            this.writeTag(entry.getValue());
        }
        this.endCompound();
        return this;
    }

    private TagStringWriter writeList(ListTag listTag) throws IOException {
        this.beginList();
        for (Tag tag : listTag) {
            this.printAndResetSeparator();
            this.writeTag(tag);
        }
        this.endList();
        return this;
    }

    private TagStringWriter writeByteArray(ByteArrayTag byteArrayTag) throws IOException {
        this.beginArray('b');
        byte[] byArray = byteArrayTag.getValue();
        int n = byArray.length;
        for (int i = 0; i < n; ++i) {
            this.printAndResetSeparator();
            this.value(Byte.toString(byArray[i]), 'b');
        }
        this.endArray();
        return this;
    }

    private TagStringWriter writeIntArray(IntArrayTag intArrayTag) throws IOException {
        this.beginArray('i');
        int[] nArray = intArrayTag.getValue();
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            this.printAndResetSeparator();
            this.value(Integer.toString(nArray[i]), 'i');
        }
        this.endArray();
        return this;
    }

    private TagStringWriter writeLongArray(LongArrayTag longArrayTag) throws IOException {
        this.beginArray('l');
        long[] lArray = longArrayTag.getValue();
        int n = lArray.length;
        for (int i = 0; i < n; ++i) {
            this.printAndResetSeparator();
            this.value(Long.toString(lArray[i]), 'l');
        }
        this.endArray();
        return this;
    }

    public TagStringWriter beginCompound() throws IOException {
        this.printAndResetSeparator();
        ++this.level;
        this.out.append('{');
        return this;
    }

    public TagStringWriter endCompound() throws IOException {
        this.out.append('}');
        --this.level;
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter key(String string) throws IOException {
        this.printAndResetSeparator();
        this.writeMaybeQuoted(string, false);
        this.out.append(':');
        return this;
    }

    public TagStringWriter value(String string, char c) throws IOException {
        if (c == '\u0000') {
            this.writeMaybeQuoted(string, true);
        } else {
            this.out.append(string);
            if (c != 'i') {
                this.out.append(c);
            }
        }
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter beginList() throws IOException {
        this.printAndResetSeparator();
        ++this.level;
        this.out.append('[');
        return this;
    }

    public TagStringWriter endList() throws IOException {
        this.out.append(']');
        --this.level;
        this.needsSeparator = true;
        return this;
    }

    private TagStringWriter beginArray(char c) throws IOException {
        this.beginList().out.append(c).append(';');
        return this;
    }

    private TagStringWriter endArray() throws IOException {
        return this.endList();
    }

    private void writeMaybeQuoted(String string, boolean bl) throws IOException {
        if (!bl) {
            for (int i = 0; i < string.length(); ++i) {
                if (Tokens.id(string.charAt(i))) continue;
                bl = true;
                break;
            }
        }
        if (bl) {
            this.out.append('\"');
            this.out.append(TagStringWriter.escape(string, '\"'));
            this.out.append('\"');
        } else {
            this.out.append(string);
        }
    }

    private static String escape(String string, char c) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c2 = string.charAt(i);
            if (c2 == c || c2 == '\\') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
    }

    private void printAndResetSeparator() throws IOException {
        if (this.needsSeparator) {
            this.out.append(',');
            this.needsSeparator = false;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.level != 0) {
            throw new IllegalStateException("Document finished with unbalanced start and end objects");
        }
        if (this.out instanceof Writer) {
            ((Writer)this.out).flush();
        }
    }
}

