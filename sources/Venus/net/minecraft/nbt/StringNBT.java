/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringNBT
implements INBT {
    public static final INBTType<StringNBT> TYPE = new INBTType<StringNBT>(){

        @Override
        public StringNBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            nBTSizeTracker.read(288L);
            String string = dataInput.readUTF();
            nBTSizeTracker.read(16 * string.length());
            return StringNBT.valueOf(string);
        }

        @Override
        public String getName() {
            return "STRING";
        }

        @Override
        public String getTagName() {
            return "TAG_String";
        }

        @Override
        public boolean isPrimitive() {
            return false;
        }

        @Override
        public INBT readNBT(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
            return this.readNBT(dataInput, n, nBTSizeTracker);
        }
    };
    private static final StringNBT EMPTY_STRING = new StringNBT("");
    private final String data;

    private StringNBT(String string) {
        Objects.requireNonNull(string, "Null string not allowed");
        this.data = string;
    }

    public static StringNBT valueOf(String string) {
        return string.isEmpty() ? EMPTY_STRING : new StringNBT(string);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.data);
    }

    @Override
    public byte getId() {
        return 1;
    }

    public INBTType<StringNBT> getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return StringNBT.quoteAndEscape(this.data);
    }

    @Override
    public StringNBT copy() {
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return object instanceof StringNBT && Objects.equals(this.data, ((StringNBT)object).data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    @Override
    public String getString() {
        return this.data;
    }

    @Override
    public ITextComponent toFormattedComponent(String string, int n) {
        String string2 = StringNBT.quoteAndEscape(this.data);
        String string3 = string2.substring(0, 1);
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent(string2.substring(1, string2.length() - 1)).mergeStyle(SYNTAX_HIGHLIGHTING_STRING);
        return new StringTextComponent(string3).append(iFormattableTextComponent).appendString(string3);
    }

    public static String quoteAndEscape(String string) {
        StringBuilder stringBuilder = new StringBuilder(" ");
        char c = '\u0000';
        for (int i = 0; i < string.length(); ++i) {
            char c2 = string.charAt(i);
            if (c2 == '\\') {
                stringBuilder.append('\\');
            } else if (c2 == '\"' || c2 == '\'') {
                if (c == '\u0000') {
                    c = (char)(c2 == '\"' ? 39 : 34);
                }
                if (c == c2) {
                    stringBuilder.append('\\');
                }
            }
            stringBuilder.append(c2);
        }
        if (c == '\u0000') {
            c = '\"';
        }
        stringBuilder.setCharAt(0, c);
        stringBuilder.append(c);
        return stringBuilder.toString();
    }

    @Override
    public INBT copy() {
        return this.copy();
    }
}

