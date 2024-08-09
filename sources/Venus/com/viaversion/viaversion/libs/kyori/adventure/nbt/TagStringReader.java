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

    TagStringReader(CharBuffer charBuffer) {
        this.buffer = charBuffer;
    }

    public CompoundBinaryTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        if (this.buffer.takeIf('\u0000')) {
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
        boolean bl;
        ListBinaryTag.Builder<BinaryTag> builder = ListBinaryTag.builder();
        this.buffer.expect('[');
        boolean bl2 = bl = this.acceptLegacy && this.buffer.peek() == '0' && this.buffer.peek(1) == ':';
        if (!bl && this.buffer.takeIf('\u0000')) {
            return ListBinaryTag.empty();
        }
        while (this.buffer.hasMore()) {
            if (bl) {
                this.buffer.takeUntil(':');
            }
            BinaryTag binaryTag = this.tag();
            builder.add(binaryTag);
            if (!this.separatorOrCompleteWith(']')) continue;
            return builder.build();
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }

    public BinaryTag array(char c) throws StringTagParseException {
        this.buffer.expect('[').expect(c).expect(';');
        c = Character.toLowerCase(c);
        if (c == 'b') {
            return ByteArrayBinaryTag.of(this.byteArray());
        }
        if (c == 'i') {
            return IntArrayBinaryTag.of(this.intArray());
        }
        if (c == 'l') {
            return LongArrayBinaryTag.of(this.longArray());
        }
        throw this.buffer.makeError("Type " + c + " is not a valid element type in an array!");
    }

    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf('\u0000')) {
            return EMPTY_BYTE_ARRAY;
        }
        ArrayList<Byte> arrayList = new ArrayList<Byte>();
        while (this.buffer.hasMore()) {
            CharSequence charSequence = this.buffer.skipWhitespace().takeUntil('b');
            try {
                arrayList.add(Byte.valueOf(charSequence.toString()));
            } catch (NumberFormatException numberFormatException) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
            if (!this.separatorOrCompleteWith(']')) continue;
            byte[] byArray = new byte[arrayList.size()];
            for (int i = 0; i < arrayList.size(); ++i) {
                byArray[i] = (Byte)arrayList.get(i);
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
            BinaryTag binaryTag = this.tag();
            if (!(binaryTag instanceof IntBinaryTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder.add(((IntBinaryTag)binaryTag).intValue());
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

    public BinaryTag tag() throws StringTagParseException {
        if (this.depth++ > 512) {
            throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
        }
        try {
            char c = this.buffer.skipWhitespace().peek();
            switch (c) {
                case '{': {
                    CompoundBinaryTag compoundBinaryTag = this.compound();
                    return compoundBinaryTag;
                }
                case '[': {
                    if (this.buffer.hasMore(1) && this.buffer.peek(2) == ';') {
                        BinaryTag binaryTag = this.array(this.buffer.peek(1));
                        return binaryTag;
                    }
                    ListBinaryTag listBinaryTag = this.list();
                    return listBinaryTag;
                }
                case '\"': 
                case '\'': {
                    this.buffer.advance();
                    StringBinaryTag stringBinaryTag = StringBinaryTag.of(TagStringReader.unescape(this.buffer.takeUntil(c).toString()));
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
            if (n2 == n && n > 1) {
                char c = string.charAt(n - '\u0001');
                try {
                    switch (Character.toLowerCase(c)) {
                        case 'b': {
                            return ByteBinaryTag.of(Byte.parseByte(string.substring(0, n - 1)));
                        }
                        case 's': {
                            return ShortBinaryTag.of(Short.parseShort(string.substring(0, n - 1)));
                        }
                        case 'i': {
                            return IntBinaryTag.of(Integer.parseInt(string.substring(0, n - 1)));
                        }
                        case 'l': {
                            return LongBinaryTag.of(Long.parseLong(string.substring(0, n - 1)));
                        }
                        case 'f': {
                            float f = Float.parseFloat(string.substring(0, n - 1));
                            if (!Float.isFinite(f)) break;
                            return FloatBinaryTag.of(f);
                        }
                        case 'd': {
                            double d = Double.parseDouble(string.substring(0, n - 1));
                            if (!Double.isFinite(d)) break;
                            return DoubleBinaryTag.of(d);
                        }
                    }
                } catch (NumberFormatException numberFormatException) {}
            } else if (n2 == -1) {
                try {
                    return IntBinaryTag.of(Integer.parseInt(string));
                } catch (NumberFormatException numberFormatException) {
                    if (string.indexOf(46) == -1) break block22;
                    try {
                        return DoubleBinaryTag.of(Double.parseDouble(string));
                    } catch (NumberFormatException numberFormatException2) {
                        // empty catch block
                    }
                }
            }
        }
        if (string.equalsIgnoreCase("true")) {
            return ByteBinaryTag.ONE;
        }
        if (string.equalsIgnoreCase("false")) {
            return ByteBinaryTag.ZERO;
        }
        return StringBinaryTag.of(string);
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

