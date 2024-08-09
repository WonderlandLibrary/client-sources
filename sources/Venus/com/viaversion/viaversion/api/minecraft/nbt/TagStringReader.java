/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.api.minecraft.nbt.CharBuffer;
import com.viaversion.viaversion.api.minecraft.nbt.StringTagParseException;
import com.viaversion.viaversion.api.minecraft.nbt.Tokens;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
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
import java.util.stream.IntStream;
import java.util.stream.LongStream;

final class TagStringReader {
    private static final int MAX_DEPTH = 512;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private final CharBuffer buffer;
    private boolean acceptLegacy = true;
    private int depth;

    TagStringReader(CharBuffer charBuffer) {
        this.buffer = charBuffer;
    }

    public CompoundTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        CompoundTag compoundTag = new CompoundTag();
        if (this.buffer.takeIf('\u0000')) {
            return compoundTag;
        }
        while (this.buffer.hasMore()) {
            compoundTag.put(this.key(), this.tag());
            if (!this.separatorOrCompleteWith('}')) continue;
            return compoundTag;
        }
        throw this.buffer.makeError("Unterminated compound tag!");
    }

    public ListTag list() throws StringTagParseException {
        boolean bl;
        ListTag listTag = new ListTag();
        this.buffer.expect('[');
        boolean bl2 = bl = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!bl && this.buffer.takeIf('\u0000')) {
            return listTag;
        }
        while (this.buffer.hasMore()) {
            if (bl) {
                this.buffer.takeUntil(':');
            }
            Tag tag = this.tag();
            listTag.add(tag);
            if (!this.separatorOrCompleteWith(']')) continue;
            return listTag;
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }

    public Tag array(char c) throws StringTagParseException {
        this.buffer.expect('[').expect(c).expect(';');
        c = Character.toLowerCase(c);
        if (c == 'b') {
            return new ByteArrayTag(this.byteArray());
        }
        if (c == 'i') {
            return new IntArrayTag(this.intArray());
        }
        if (c == 'l') {
            return new LongArrayTag(this.longArray());
        }
        throw this.buffer.makeError("Type " + c + " is not a valid element type in an array!");
    }

    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf('\u0000')) {
            return EMPTY_BYTE_ARRAY;
        }
        IntArrayList intArrayList = new IntArrayList();
        while (this.buffer.hasMore()) {
            CharSequence charSequence = this.buffer.skipWhitespace().takeUntil('b');
            try {
                intArrayList.add(Byte.parseByte(charSequence.toString()));
            } catch (NumberFormatException numberFormatException) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
            if (!this.separatorOrCompleteWith(']')) continue;
            byte[] byArray = new byte[intArrayList.size()];
            for (int i = 0; i < intArrayList.size(); ++i) {
                byArray[i] = (byte)intArrayList.getInt(i);
            }
            return byArray;
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private int[] intArray() throws StringTagParseException {
        if (this.buffer.takeIf('\u0000')) {
            return EMPTY_INT_ARRAY;
        }
        IntStream.Builder builder = IntStream.builder();
        while (this.buffer.hasMore()) {
            Tag tag = this.tag();
            if (!(tag instanceof IntTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((NumberTag)tag).asInt());
            if (!this.separatorOrCompleteWith(']')) continue;
            return builder.build().toArray();
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private long[] longArray() throws StringTagParseException {
        if (this.buffer.takeIf('\u0000')) {
            return EMPTY_LONG_ARRAY;
        }
        LongStream.Builder builder = LongStream.builder();
        while (this.buffer.hasMore()) {
            CharSequence charSequence = this.buffer.skipWhitespace().takeUntil('l');
            try {
                builder.add(Long.parseLong(charSequence.toString()));
            } catch (NumberFormatException numberFormatException) {
                throw this.buffer.makeError("All elements of a long array must be longs!");
            }
            if (!this.separatorOrCompleteWith(']')) continue;
            return builder.build().toArray();
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String key() throws StringTagParseException {
        this.buffer.skipWhitespace();
        char c = this.buffer.peek();
        try {
            if (c == '\'' || c == '\"') {
                String string = TagStringReader.unescape(this.buffer.takeUntil(this.buffer.take()).toString());
                return string;
            }
            StringBuilder stringBuilder = new StringBuilder();
            while (this.buffer.hasMore()) {
                char c2 = this.buffer.peek();
                if (!Tokens.id(c2)) {
                    if (!this.acceptLegacy) break;
                    if (c2 == '\\') {
                        this.buffer.take();
                        continue;
                    }
                    if (c2 == ':') break;
                    stringBuilder.append(this.buffer.take());
                    continue;
                }
                stringBuilder.append(this.buffer.take());
            }
            String string = stringBuilder.toString();
            return string;
        } finally {
            this.buffer.expect(':');
        }
    }

    public Tag tag() throws StringTagParseException {
        if (this.depth++ > 512) {
            throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
        }
        try {
            char c = this.buffer.skipWhitespace().peek();
            switch (c) {
                case '{': {
                    CompoundTag compoundTag = this.compound();
                    return compoundTag;
                }
                case '[': {
                    if (this.buffer.hasMore(1) && this.buffer.peek(2) == ';') {
                        Tag tag = this.array(this.buffer.peek(1));
                        return tag;
                    }
                    ListTag listTag = this.list();
                    return listTag;
                }
                case '\"': 
                case '\'': {
                    this.buffer.advance();
                    StringTag stringTag = new StringTag(TagStringReader.unescape(this.buffer.takeUntil(c).toString()));
                    return stringTag;
                }
            }
            Tag tag = this.scalar();
            return tag;
        } finally {
            --this.depth;
        }
    }

    private Tag scalar() {
        String string;
        block22: {
            int n;
            StringBuilder stringBuilder = new StringBuilder();
            int n2 = -1;
            while (this.buffer.hasMore()) {
                n = this.buffer.peek();
                if (n == 92) {
                    this.buffer.advance();
                    n = this.buffer.take();
                } else {
                    if (!Tokens.id((char)n)) break;
                    this.buffer.advance();
                }
                stringBuilder.append((char)n);
                if (n2 != -1 || Tokens.numeric((char)n)) continue;
                n2 = stringBuilder.length();
            }
            n = stringBuilder.length();
            string = stringBuilder.toString();
            if (n2 == n) {
                char c = string.charAt(n - '\u0001');
                try {
                    switch (Character.toLowerCase(c)) {
                        case 'b': {
                            return new ByteTag(Byte.parseByte(string.substring(0, n - '\u0001')));
                        }
                        case 's': {
                            return new ShortTag(Short.parseShort(string.substring(0, n - '\u0001')));
                        }
                        case 'i': {
                            return new IntTag(Integer.parseInt(string.substring(0, n - '\u0001')));
                        }
                        case 'l': {
                            return new LongTag(Long.parseLong(string.substring(0, n - '\u0001')));
                        }
                        case 'f': {
                            float f = Float.parseFloat(string.substring(0, n - '\u0001'));
                            if (!Float.isFinite(f)) break;
                            return new FloatTag(f);
                        }
                        case 'd': {
                            double d = Double.parseDouble(string.substring(0, n - '\u0001'));
                            if (!Double.isFinite(d)) break;
                            return new DoubleTag(d);
                        }
                    }
                } catch (NumberFormatException numberFormatException) {}
            } else if (n2 == -1) {
                try {
                    return new IntTag(Integer.parseInt(string));
                } catch (NumberFormatException numberFormatException) {
                    if (string.indexOf(46) == -1) break block22;
                    try {
                        return new DoubleTag(Double.parseDouble(string));
                    } catch (NumberFormatException numberFormatException2) {
                        // empty catch block
                    }
                }
            }
        }
        if (string.equalsIgnoreCase("true")) {
            return new ByteTag(1);
        }
        if (string.equalsIgnoreCase("false")) {
            return new ByteTag(0);
        }
        return new StringTag(string);
    }

    private boolean separatorOrCompleteWith(char c) throws StringTagParseException {
        if (this.buffer.takeIf(c)) {
            return false;
        }
        this.buffer.expect(',');
        return this.buffer.takeIf(c);
    }

    private static String unescape(String string) {
        int n = string.indexOf(92);
        if (n == -1) {
            return string;
        }
        int n2 = 0;
        StringBuilder stringBuilder = new StringBuilder(string.length());
        do {
            stringBuilder.append(string, n2, n);
        } while ((n = string.indexOf(92, (n2 = n + 1) + 1)) != -1);
        stringBuilder.append(string.substring(n2));
        return stringBuilder.toString();
    }

    public void legacy(boolean bl) {
        this.acceptLegacy = bl;
    }
}

