/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.DoubleBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.FloatBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ShortBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.Tokens;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

final class TagStringWriter
implements AutoCloseable {
    private final Appendable out;
    private final String indent;
    private int level;
    private boolean needsSeparator;
    private boolean legacy;

    TagStringWriter(Appendable appendable, String string) {
        this.out = appendable;
        this.indent = string;
    }

    public TagStringWriter legacy(boolean bl) {
        this.legacy = bl;
        return this;
    }

    public TagStringWriter writeTag(BinaryTag binaryTag) throws IOException {
        BinaryTagType<? extends BinaryTag> binaryTagType = binaryTag.type();
        if (binaryTagType == BinaryTagTypes.COMPOUND) {
            return this.writeCompound((CompoundBinaryTag)binaryTag);
        }
        if (binaryTagType == BinaryTagTypes.LIST) {
            return this.writeList((ListBinaryTag)binaryTag);
        }
        if (binaryTagType == BinaryTagTypes.BYTE_ARRAY) {
            return this.writeByteArray((ByteArrayBinaryTag)binaryTag);
        }
        if (binaryTagType == BinaryTagTypes.INT_ARRAY) {
            return this.writeIntArray((IntArrayBinaryTag)binaryTag);
        }
        if (binaryTagType == BinaryTagTypes.LONG_ARRAY) {
            return this.writeLongArray((LongArrayBinaryTag)binaryTag);
        }
        if (binaryTagType == BinaryTagTypes.STRING) {
            return this.value(((StringBinaryTag)binaryTag).value(), '\u0000');
        }
        if (binaryTagType == BinaryTagTypes.BYTE) {
            return this.value(Byte.toString(((ByteBinaryTag)binaryTag).value()), 'b');
        }
        if (binaryTagType == BinaryTagTypes.SHORT) {
            return this.value(Short.toString(((ShortBinaryTag)binaryTag).value()), 's');
        }
        if (binaryTagType == BinaryTagTypes.INT) {
            return this.value(Integer.toString(((IntBinaryTag)binaryTag).value()), 'i');
        }
        if (binaryTagType == BinaryTagTypes.LONG) {
            return this.value(Long.toString(((LongBinaryTag)binaryTag).value()), Character.toUpperCase('l'));
        }
        if (binaryTagType == BinaryTagTypes.FLOAT) {
            return this.value(Float.toString(((FloatBinaryTag)binaryTag).value()), 'f');
        }
        if (binaryTagType == BinaryTagTypes.DOUBLE) {
            return this.value(Double.toString(((DoubleBinaryTag)binaryTag).value()), 'd');
        }
        throw new IOException("Unknown tag type: " + binaryTagType);
    }

    private TagStringWriter writeCompound(CompoundBinaryTag compoundBinaryTag) throws IOException {
        this.beginCompound();
        for (Map.Entry entry : compoundBinaryTag) {
            this.key((String)entry.getKey());
            this.writeTag((BinaryTag)entry.getValue());
        }
        this.endCompound();
        return this;
    }

    private TagStringWriter writeList(ListBinaryTag listBinaryTag) throws IOException {
        this.beginList();
        int n = 0;
        boolean bl = this.prettyPrinting() && this.breakListElement(listBinaryTag.elementType());
        for (BinaryTag binaryTag : listBinaryTag) {
            this.printAndResetSeparator(!bl);
            if (bl) {
                this.newlineIndent();
            }
            if (this.legacy) {
                this.out.append(String.valueOf(n++));
                this.appendSeparator(':');
            }
            this.writeTag(binaryTag);
        }
        this.endList(bl);
        return this;
    }

    private TagStringWriter writeByteArray(ByteArrayBinaryTag byteArrayBinaryTag) throws IOException {
        if (this.legacy) {
            throw new IOException("Legacy Mojangson only supports integer arrays!");
        }
        this.beginArray('b');
        char c = Character.toUpperCase('b');
        byte[] byArray = ByteArrayBinaryTagImpl.value(byteArrayBinaryTag);
        int n = byArray.length;
        for (int i = 0; i < n; ++i) {
            this.printAndResetSeparator(true);
            this.value(Byte.toString(byArray[i]), c);
        }
        this.endArray();
        return this;
    }

    private TagStringWriter writeIntArray(IntArrayBinaryTag intArrayBinaryTag) throws IOException {
        if (this.legacy) {
            this.beginList();
        } else {
            this.beginArray('i');
        }
        int[] nArray = IntArrayBinaryTagImpl.value(intArrayBinaryTag);
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            this.printAndResetSeparator(true);
            this.value(Integer.toString(nArray[i]), 'i');
        }
        this.endArray();
        return this;
    }

    private TagStringWriter writeLongArray(LongArrayBinaryTag longArrayBinaryTag) throws IOException {
        if (this.legacy) {
            throw new IOException("Legacy Mojangson only supports integer arrays!");
        }
        this.beginArray('l');
        long[] lArray = LongArrayBinaryTagImpl.value(longArrayBinaryTag);
        int n = lArray.length;
        for (int i = 0; i < n; ++i) {
            this.printAndResetSeparator(true);
            this.value(Long.toString(lArray[i]), 'l');
        }
        this.endArray();
        return this;
    }

    public TagStringWriter beginCompound() throws IOException {
        this.printAndResetSeparator(false);
        ++this.level;
        this.out.append('{');
        return this;
    }

    public TagStringWriter endCompound() throws IOException {
        --this.level;
        this.newlineIndent();
        this.out.append('}');
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter key(String string) throws IOException {
        this.printAndResetSeparator(false);
        this.newlineIndent();
        this.writeMaybeQuoted(string, false);
        this.appendSeparator(':');
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
        this.printAndResetSeparator(false);
        ++this.level;
        this.out.append('[');
        return this;
    }

    public TagStringWriter endList(boolean bl) throws IOException {
        --this.level;
        if (bl) {
            this.newlineIndent();
        }
        this.out.append(']');
        this.needsSeparator = true;
        return this;
    }

    private TagStringWriter beginArray(char c) throws IOException {
        this.beginList().out.append(Character.toUpperCase(c)).append(';');
        if (this.prettyPrinting()) {
            this.out.append(' ');
        }
        return this;
    }

    private TagStringWriter endArray() throws IOException {
        return this.endList(true);
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

    private void printAndResetSeparator(boolean bl) throws IOException {
        if (this.needsSeparator) {
            this.out.append(',');
            if (bl && this.prettyPrinting()) {
                this.out.append(' ');
            }
            this.needsSeparator = false;
        }
    }

    private boolean breakListElement(BinaryTagType<?> binaryTagType) {
        return binaryTagType == BinaryTagTypes.COMPOUND || binaryTagType == BinaryTagTypes.LIST || binaryTagType == BinaryTagTypes.BYTE_ARRAY || binaryTagType == BinaryTagTypes.INT_ARRAY || binaryTagType == BinaryTagTypes.LONG_ARRAY;
    }

    private boolean prettyPrinting() {
        return this.indent.length() > 0;
    }

    private void newlineIndent() throws IOException {
        if (this.prettyPrinting()) {
            this.out.append(Tokens.NEWLINE);
            for (int i = 0; i < this.level; ++i) {
                this.out.append(this.indent);
            }
        }
    }

    private Appendable appendSeparator(char c) throws IOException {
        this.out.append(c);
        if (this.prettyPrinting()) {
            this.out.append(' ');
        }
        return this.out;
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

