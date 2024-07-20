/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CharBuffer;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.DoubleBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.FloatBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ShortBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringTagParseException;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.Tokens;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

final class TagStringReader {
    private static final int MAX_DEPTH = 512;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private final CharBuffer buffer;
    private boolean acceptLegacy;
    private int depth;

    TagStringReader(CharBuffer buffer) {
        this.buffer = buffer;
    }

    public CompoundBinaryTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        if (this.buffer.takeIf('}')) {
            return CompoundBinaryTag.empty();
        }
        CompoundBinaryTag.Builder builder = CompoundBinaryTag.builder();
        while (this.buffer.hasMore()) {
            builder.put(this.key(), this.tag());
            if (!this.separatorOrCompleteWith('}')) continue;
            return builder.build();
        }
        throw this.buffer.makeError("Unterminated compound tag!");
    }

    public ListBinaryTag list() throws StringTagParseException {
        boolean prefixedIndex;
        ListBinaryTag.Builder<BinaryTag> builder = ListBinaryTag.builder();
        this.buffer.expect('[');
        boolean bl = prefixedIndex = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!prefixedIndex && this.buffer.takeIf(']')) {
            return ListBinaryTag.empty();
        }
        while (this.buffer.hasMore()) {
            if (prefixedIndex) {
                this.buffer.takeUntil(':');
            }
            BinaryTag next = this.tag();
            builder.add(next);
            if (!this.separatorOrCompleteWith(']')) continue;
            return builder.build();
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }

    public BinaryTag array(char elementType) throws StringTagParseException {
        this.buffer.expect('[').expect(elementType).expect(';');
        elementType = Character.toLowerCase(elementType);
        if (elementType == 'b') {
            return ByteArrayBinaryTag.byteArrayBinaryTag(this.byteArray());
        }
        if (elementType == 'i') {
            return IntArrayBinaryTag.intArrayBinaryTag(this.intArray());
        }
        if (elementType == 'l') {
            return LongArrayBinaryTag.longArrayBinaryTag(this.longArray());
        }
        throw this.buffer.makeError("Type " + elementType + " is not a valid element type in an array!");
    }

    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_BYTE_ARRAY;
        }
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        while (this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('b');
            try {
                bytes.add(Byte.valueOf(value.toString()));
            } catch (NumberFormatException ex) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
            if (!this.separatorOrCompleteWith(']')) continue;
            byte[] result = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); ++i) {
                result[i] = (Byte)bytes.get(i);
            }
            return result;
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private int[] intArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_INT_ARRAY;
        }
        IntStream.Builder builder = IntStream.builder();
        while (this.buffer.hasMore()) {
            BinaryTag value = this.tag();
            if (!(value instanceof IntBinaryTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((IntBinaryTag)value).intValue());
            if (!this.separatorOrCompleteWith(']')) continue;
            return builder.build().toArray();
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private long[] longArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_LONG_ARRAY;
        }
        LongStream.Builder longs = LongStream.builder();
        while (this.buffer.hasMore()) {
            CharSequence value = this.buffer.skipWhitespace().takeUntil('l');
            try {
                longs.add(Long.parseLong(value.toString()));
            } catch (NumberFormatException ex) {
                throw this.buffer.makeError("All elements of a long array must be longs!");
            }
            if (!this.separatorOrCompleteWith(']')) continue;
            return longs.build().toArray();
        }
        throw this.buffer.makeError("Reached end of document without array close");
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
        } finally {
            this.buffer.expect(':');
        }
    }

    public BinaryTag tag() throws StringTagParseException {
        if (this.depth++ > 512) {
            throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
        }
        try {
            char startToken = this.buffer.skipWhitespace().peek();
            switch (startToken) {
                case '{': {
                    CompoundBinaryTag compoundBinaryTag = this.compound();
                    return compoundBinaryTag;
                }
                case '[': {
                    if (this.buffer.hasMore(2) && this.buffer.peek(2) == ';') {
                        BinaryTag binaryTag = this.array(this.buffer.peek(1));
                        return binaryTag;
                    }
                    ListBinaryTag listBinaryTag = this.list();
                    return listBinaryTag;
                }
                case '\"': 
                case '\'': {
                    this.buffer.advance();
                    StringBinaryTag stringBinaryTag = StringBinaryTag.stringBinaryTag(TagStringReader.unescape(this.buffer.takeUntil(startToken).toString()));
                    return stringBinaryTag;
                }
            }
            BinaryTag binaryTag = this.scalar();
            return binaryTag;
        } finally {
            --this.depth;
        }
    }

    private BinaryTag scalar() {
        String built;
        block22: {
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
            if (noLongerNumericAt == length && length > 1) {
                char last = built.charAt(length - 1);
                try {
                    switch (Character.toLowerCase(last)) {
                        case 'b': {
                            return ByteBinaryTag.byteBinaryTag(Byte.parseByte(built.substring(0, length - 1)));
                        }
                        case 's': {
                            return ShortBinaryTag.shortBinaryTag(Short.parseShort(built.substring(0, length - 1)));
                        }
                        case 'i': {
                            return IntBinaryTag.intBinaryTag(Integer.parseInt(built.substring(0, length - 1)));
                        }
                        case 'l': {
                            return LongBinaryTag.longBinaryTag(Long.parseLong(built.substring(0, length - 1)));
                        }
                        case 'f': {
                            float floatValue = Float.parseFloat(built.substring(0, length - 1));
                            if (!Float.isFinite(floatValue)) break;
                            return FloatBinaryTag.floatBinaryTag(floatValue);
                        }
                        case 'd': {
                            double doubleValue = Double.parseDouble(built.substring(0, length - 1));
                            if (!Double.isFinite(doubleValue)) break;
                            return DoubleBinaryTag.doubleBinaryTag(doubleValue);
                        }
                    }
                } catch (NumberFormatException numberFormatException) {}
            } else if (noLongerNumericAt == -1) {
                try {
                    return IntBinaryTag.intBinaryTag(Integer.parseInt(built));
                } catch (NumberFormatException ex) {
                    if (built.indexOf(46) == -1) break block22;
                    try {
                        return DoubleBinaryTag.doubleBinaryTag(Double.parseDouble(built));
                    } catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
            }
        }
        if (built.equalsIgnoreCase("true")) {
            return ByteBinaryTag.ONE;
        }
        if (built.equalsIgnoreCase("false")) {
            return ByteBinaryTag.ZERO;
        }
        return StringBinaryTag.stringBinaryTag(built);
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

