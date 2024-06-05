/*
 * Decompiled with CFR 0.152.
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
import java.util.Iterator;
import java.util.Map;

final class TagStringWriter
implements AutoCloseable {
    private final Appendable out;
    private int level;
    private boolean needsSeparator;

    public TagStringWriter(Appendable out) {
        this.out = out;
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
        if (!(tag instanceof DoubleTag)) throw new IOException("Unknown tag type: " + tag.getClass().getSimpleName());
        return this.value(Double.toString(((NumberTag)tag).asDouble()), 'd');
    }

    private TagStringWriter writeCompound(CompoundTag tag) throws IOException {
        this.beginCompound();
        Iterator<Map.Entry<String, Tag>> iterator = tag.entrySet().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.endCompound();
                return this;
            }
            Map.Entry<String, Tag> entry = iterator.next();
            this.key(entry.getKey());
            this.writeTag(entry.getValue());
        }
    }

    private TagStringWriter writeList(ListTag tag) throws IOException {
        this.beginList();
        Iterator<Tag> iterator = tag.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.endList();
                return this;
            }
            Tag el = iterator.next();
            this.printAndResetSeparator();
            this.writeTag(el);
        }
    }

    private TagStringWriter writeByteArray(ByteArrayTag tag) throws IOException {
        this.beginArray('b');
        byte[] value = tag.getValue();
        int i = 0;
        int length = value.length;
        while (true) {
            if (i >= length) {
                this.endArray();
                return this;
            }
            this.printAndResetSeparator();
            this.value(Byte.toString(value[i]), 'b');
            ++i;
        }
    }

    private TagStringWriter writeIntArray(IntArrayTag tag) throws IOException {
        this.beginArray('i');
        int[] value = tag.getValue();
        int i = 0;
        int length = value.length;
        while (true) {
            if (i >= length) {
                this.endArray();
                return this;
            }
            this.printAndResetSeparator();
            this.value(Integer.toString(value[i]), 'i');
            ++i;
        }
    }

    private TagStringWriter writeLongArray(LongArrayTag tag) throws IOException {
        this.beginArray('l');
        long[] value = tag.getValue();
        int i = 0;
        int length = value.length;
        while (true) {
            if (i >= length) {
                this.endArray();
                return this;
            }
            this.printAndResetSeparator();
            this.value(Long.toString(value[i]), 'l');
            ++i;
        }
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

    public TagStringWriter key(String key) throws IOException {
        this.printAndResetSeparator();
        this.writeMaybeQuoted(key, false);
        this.out.append(':');
        return this;
    }

    public TagStringWriter value(String value, char valueType) throws IOException {
        if (valueType == '\u0000') {
            this.writeMaybeQuoted(value, true);
        } else {
            this.out.append(value);
            if (valueType != 'i') {
                this.out.append(valueType);
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

    private TagStringWriter beginArray(char type) throws IOException {
        this.beginList().out.append(type).append(';');
        return this;
    }

    private TagStringWriter endArray() throws IOException {
        return this.endList();
    }

    private void writeMaybeQuoted(String content, boolean requireQuotes) throws IOException {
        if (!requireQuotes) {
            for (int i = 0; i < content.length(); ++i) {
                if (Tokens.id(content.charAt(i))) continue;
                requireQuotes = true;
                break;
            }
        }
        if (requireQuotes) {
            this.out.append('\"');
            this.out.append(TagStringWriter.escape(content, '\"'));
            this.out.append('\"');
            return;
        }
        this.out.append(content);
    }

    private static String escape(String content, char quoteChar) {
        StringBuilder output = new StringBuilder(content.length());
        int i = 0;
        while (i < content.length()) {
            char c = content.charAt(i);
            if (c == quoteChar || c == '\\') {
                output.append('\\');
            }
            output.append(c);
            ++i;
        }
        return output.toString();
    }

    private void printAndResetSeparator() throws IOException {
        if (!this.needsSeparator) return;
        this.out.append(',');
        this.needsSeparator = false;
    }

    @Override
    public void close() throws IOException {
        if (this.level != 0) {
            throw new IllegalStateException("Document finished with unbalanced start and end objects");
        }
        if (!(this.out instanceof Writer)) return;
        ((Writer)this.out).flush();
    }
}

