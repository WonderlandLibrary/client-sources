/*
 * Decompiled with CFR 0.152.
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

    TagStringReader(CharBuffer buffer) {
        this.buffer = buffer;
    }

    public CompoundTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        CompoundTag compoundTag = new CompoundTag();
        if (this.buffer.takeIf('}')) {
            return compoundTag;
        }
        do {
            if (!this.buffer.hasMore()) throw this.buffer.makeError("Unterminated compound tag!");
            compoundTag.put(this.key(), this.tag());
        } while (!this.separatorOrCompleteWith('}'));
        return compoundTag;
    }

    public ListTag list() throws StringTagParseException {
        boolean prefixedIndex;
        ListTag listTag = new ListTag();
        this.buffer.expect('[');
        boolean bl = prefixedIndex = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!prefixedIndex && this.buffer.takeIf(']')) {
            return listTag;
        }
        do {
            if (!this.buffer.hasMore()) throw this.buffer.makeError("Reached end of file without end of list tag!");
            if (prefixedIndex) {
                this.buffer.takeUntil(':');
            }
            Tag next = this.tag();
            listTag.add(next);
        } while (!this.separatorOrCompleteWith(']'));
        return listTag;
    }

    public Tag array(char elementType) throws StringTagParseException {
        this.buffer.expect('[').expect(elementType).expect(';');
        elementType = Character.toLowerCase(elementType);
        if (elementType == 'b') {
            return new ByteArrayTag(this.byteArray());
        }
        if (elementType == 'i') {
            return new IntArrayTag(this.intArray());
        }
        if (elementType != 'l') throw this.buffer.makeError("Type " + elementType + " is not a valid element type in an array!");
        return new LongArrayTag(this.longArray());
    }

    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_BYTE_ARRAY;
        }
        IntArrayList bytes = new IntArrayList();
        do {
            if (!this.buffer.hasMore()) throw this.buffer.makeError("Reached end of document without array close");
            CharSequence value = this.buffer.skipWhitespace().takeUntil('b');
            try {
                bytes.add(Byte.parseByte(value.toString()));
            }
            catch (NumberFormatException ex) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
        } while (!this.separatorOrCompleteWith(']'));
        byte[] result = new byte[bytes.size()];
        int i = 0;
        while (i < bytes.size()) {
            result[i] = (byte)bytes.getInt(i);
            ++i;
        }
        return result;
    }

    private int[] intArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_INT_ARRAY;
        }
        IntStream.Builder builder = IntStream.builder();
        do {
            if (!this.buffer.hasMore()) throw this.buffer.makeError("Reached end of document without array close");
            Tag value = this.tag();
            if (!(value instanceof IntTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((NumberTag)value).asInt());
        } while (!this.separatorOrCompleteWith(']'));
        return builder.build().toArray();
    }

    private long[] longArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_LONG_ARRAY;
        }
        LongStream.Builder longs = LongStream.builder();
        do {
            if (!this.buffer.hasMore()) throw this.buffer.makeError("Reached end of document without array close");
            CharSequence value = this.buffer.skipWhitespace().takeUntil('l');
            try {
                longs.add(Long.parseLong(value.toString()));
            }
            catch (NumberFormatException ex) {
                throw this.buffer.makeError("All elements of a long array must be longs!");
            }
        } while (!this.separatorOrCompleteWith(']'));
        return longs.build().toArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String key() throws StringTagParseException {
        this.buffer.skipWhitespace();
        char starChar = this.buffer.peek();
        try {
            if (starChar == '\'' || starChar == '\"') {
                String string = TagStringReader.unescape(this.buffer.takeUntil(this.buffer.take()).toString());
                return string;
            }
            StringBuilder builder = new StringBuilder();
            while (this.buffer.hasMore()) {
                char peek = this.buffer.peek();
                if (!Tokens.id(peek)) {
                    if (!this.acceptLegacy) break;
                    if (peek == '\\') {
                        this.buffer.take();
                        continue;
                    }
                    if (peek == ':') break;
                    builder.append(this.buffer.take());
                    continue;
                }
                builder.append(this.buffer.take());
            }
            String string = builder.toString();
            return string;
        }
        finally {
            this.buffer.expect(':');
        }
    }

    public Tag tag() throws StringTagParseException {
        if (this.depth++ > 512) {
            throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
        }
        try {
            char startToken = this.buffer.skipWhitespace().peek();
            switch (startToken) {
                case '{': {
                    CompoundTag compoundTag = this.compound();
                    return compoundTag;
                }
                case '[': {
                    if (this.buffer.hasMore(2) && this.buffer.peek(2) == ';') {
                        Tag tag = this.array(this.buffer.peek(1));
                        return tag;
                    }
                    ListTag listTag = this.list();
                    return listTag;
                }
                case '\"': 
                case '\'': {
                    this.buffer.advance();
                    StringTag stringTag = new StringTag(TagStringReader.unescape(this.buffer.takeUntil(startToken).toString()));
                    return stringTag;
                }
            }
            Tag tag = this.scalar();
            return tag;
        }
        finally {
            --this.depth;
        }
    }

    private Tag scalar() {
        String built;
        block23: {
            StringBuilder builder = new StringBuilder();
            int noLongerNumericAt = -1;
            while (this.buffer.hasMore()) {
                char current = this.buffer.peek();
                if (current == '\\') {
                    this.buffer.advance();
                    current = this.buffer.take();
                } else {
                    if (!Tokens.id(current)) break;
                    this.buffer.advance();
                }
                builder.append(current);
                if (noLongerNumericAt != -1 || Tokens.numeric(current)) continue;
                noLongerNumericAt = builder.length();
            }
            int length = builder.length();
            built = builder.toString();
            if (noLongerNumericAt == length) {
                char last = built.charAt(length - 1);
                try {
                    switch (Character.toLowerCase(last)) {
                        case 'b': {
                            return new ByteTag(Byte.parseByte(built.substring(0, length - 1)));
                        }
                        case 's': {
                            return new ShortTag(Short.parseShort(built.substring(0, length - 1)));
                        }
                        case 'i': {
                            return new IntTag(Integer.parseInt(built.substring(0, length - 1)));
                        }
                        case 'l': {
                            return new LongTag(Long.parseLong(built.substring(0, length - 1)));
                        }
                        case 'f': {
                            float floatValue = Float.parseFloat(built.substring(0, length - 1));
                            if (Float.isFinite(floatValue)) {
                                return new FloatTag(floatValue);
                            }
                            break;
                        }
                        case 'd': {
                            double doubleValue = Double.parseDouble(built.substring(0, length - 1));
                            if (Double.isFinite(doubleValue)) {
                                return new DoubleTag(doubleValue);
                            }
                            break;
                        }
                    }
                }
                catch (NumberFormatException numberFormatException) {}
            } else if (noLongerNumericAt == -1) {
                try {
                    return new IntTag(Integer.parseInt(built));
                }
                catch (NumberFormatException ex) {
                    if (built.indexOf(46) == -1) break block23;
                    try {
                        return new DoubleTag(Double.parseDouble(built));
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }
        }
        if (built.equalsIgnoreCase("true")) {
            return new ByteTag(1);
        }
        if (!built.equalsIgnoreCase("false")) return new StringTag(built);
        return new ByteTag(0);
    }

    private boolean separatorOrCompleteWith(char endCharacter) throws StringTagParseException {
        if (this.buffer.takeIf(endCharacter)) {
            return true;
        }
        this.buffer.expect(',');
        return this.buffer.takeIf(endCharacter);
    }

    private static String unescape(String withEscapes) {
        int escapeIdx = withEscapes.indexOf(92);
        if (escapeIdx == -1) {
            return withEscapes;
        }
        int lastEscape = 0;
        StringBuilder output = new StringBuilder(withEscapes.length());
        do {
            output.append(withEscapes, lastEscape, escapeIdx);
        } while ((escapeIdx = withEscapes.indexOf(92, (lastEscape = escapeIdx + 1) + 1)) != -1);
        output.append(withEscapes.substring(lastEscape));
        return output.toString();
    }

    public void legacy(boolean acceptLegacy) {
        this.acceptLegacy = acceptLegacy;
    }
}

