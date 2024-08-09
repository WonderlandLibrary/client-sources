/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.TranslationTextComponent;

public class JsonToNBT {
    public static final SimpleCommandExceptionType ERROR_TRAILING_DATA = new SimpleCommandExceptionType(new TranslationTextComponent("argument.nbt.trailing"));
    public static final SimpleCommandExceptionType ERROR_EXPECTED_KEY = new SimpleCommandExceptionType(new TranslationTextComponent("argument.nbt.expected.key"));
    public static final SimpleCommandExceptionType ERROR_EXPECTED_VALUE = new SimpleCommandExceptionType(new TranslationTextComponent("argument.nbt.expected.value"));
    public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_LIST = new Dynamic2CommandExceptionType(JsonToNBT::lambda$static$0);
    public static final Dynamic2CommandExceptionType ERROR_INSERT_MIXED_ARRAY = new Dynamic2CommandExceptionType(JsonToNBT::lambda$static$1);
    public static final DynamicCommandExceptionType ERROR_INVALID_ARRAY = new DynamicCommandExceptionType(JsonToNBT::lambda$static$2);
    private static final Pattern DOUBLE_PATTERN_NOSUFFIX = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
    private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
    private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
    private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
    private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
    private final StringReader reader;

    public static CompoundNBT getTagFromJson(String string) throws CommandSyntaxException {
        return new JsonToNBT(new StringReader(string)).readSingleStruct();
    }

    @VisibleForTesting
    CompoundNBT readSingleStruct() throws CommandSyntaxException {
        CompoundNBT compoundNBT = this.readStruct();
        this.reader.skipWhitespace();
        if (this.reader.canRead()) {
            throw ERROR_TRAILING_DATA.createWithContext(this.reader);
        }
        return compoundNBT;
    }

    public JsonToNBT(StringReader stringReader) {
        this.reader = stringReader;
    }

    protected String readKey() throws CommandSyntaxException {
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
        }
        return this.reader.readString();
    }

    protected INBT readTypedValue() throws CommandSyntaxException {
        this.reader.skipWhitespace();
        int n = this.reader.getCursor();
        if (StringReader.isQuotedStringStart(this.reader.peek())) {
            return StringNBT.valueOf(this.reader.readQuotedString());
        }
        String string = this.reader.readUnquotedString();
        if (string.isEmpty()) {
            this.reader.setCursor(n);
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
        }
        return this.type(string);
    }

    private INBT type(String string) {
        try {
            if (FLOAT_PATTERN.matcher(string).matches()) {
                return FloatNBT.valueOf(Float.parseFloat(string.substring(0, string.length() - 1)));
            }
            if (BYTE_PATTERN.matcher(string).matches()) {
                return ByteNBT.valueOf(Byte.parseByte(string.substring(0, string.length() - 1)));
            }
            if (LONG_PATTERN.matcher(string).matches()) {
                return LongNBT.valueOf(Long.parseLong(string.substring(0, string.length() - 1)));
            }
            if (SHORT_PATTERN.matcher(string).matches()) {
                return ShortNBT.valueOf(Short.parseShort(string.substring(0, string.length() - 1)));
            }
            if (INT_PATTERN.matcher(string).matches()) {
                return IntNBT.valueOf(Integer.parseInt(string));
            }
            if (DOUBLE_PATTERN.matcher(string).matches()) {
                return DoubleNBT.valueOf(Double.parseDouble(string.substring(0, string.length() - 1)));
            }
            if (DOUBLE_PATTERN_NOSUFFIX.matcher(string).matches()) {
                return DoubleNBT.valueOf(Double.parseDouble(string));
            }
            if ("true".equalsIgnoreCase(string)) {
                return ByteNBT.ONE;
            }
            if ("false".equalsIgnoreCase(string)) {
                return ByteNBT.ZERO;
            }
        } catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        return StringNBT.valueOf(string);
    }

    public INBT readValue() throws CommandSyntaxException {
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
        }
        char c = this.reader.peek();
        if (c == '{') {
            return this.readStruct();
        }
        return c == '[' ? this.readList() : this.readTypedValue();
    }

    protected INBT readList() throws CommandSyntaxException {
        return this.reader.canRead(0) && !StringReader.isQuotedStringStart(this.reader.peek(1)) && this.reader.peek(2) == ';' ? this.readArrayTag() : this.readListTag();
    }

    public CompoundNBT readStruct() throws CommandSyntaxException {
        this.expect('{');
        CompoundNBT compoundNBT = new CompoundNBT();
        this.reader.skipWhitespace();
        while (this.reader.canRead() && this.reader.peek() != '}') {
            int n = this.reader.getCursor();
            String string = this.readKey();
            if (string.isEmpty()) {
                this.reader.setCursor(n);
                throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
            }
            this.expect(':');
            compoundNBT.put(string, this.readValue());
            if (!this.hasElementSeparator()) break;
            if (this.reader.canRead()) continue;
            throw ERROR_EXPECTED_KEY.createWithContext(this.reader);
        }
        this.expect('}');
        return compoundNBT;
    }

    private INBT readListTag() throws CommandSyntaxException {
        this.expect('[');
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
        }
        ListNBT listNBT = new ListNBT();
        INBTType<?> iNBTType = null;
        while (this.reader.peek() != ']') {
            int n = this.reader.getCursor();
            INBT iNBT = this.readValue();
            INBTType<?> iNBTType2 = iNBT.getType();
            if (iNBTType == null) {
                iNBTType = iNBTType2;
            } else if (iNBTType2 != iNBTType) {
                this.reader.setCursor(n);
                throw ERROR_INSERT_MIXED_LIST.createWithContext(this.reader, iNBTType2.getTagName(), iNBTType.getTagName());
            }
            listNBT.add(iNBT);
            if (!this.hasElementSeparator()) break;
            if (this.reader.canRead()) continue;
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
        }
        this.expect(']');
        return listNBT;
    }

    private INBT readArrayTag() throws CommandSyntaxException {
        this.expect('[');
        int n = this.reader.getCursor();
        char c = this.reader.read();
        this.reader.read();
        this.reader.skipWhitespace();
        if (!this.reader.canRead()) {
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
        }
        if (c == 'B') {
            return new ByteArrayNBT(this.getNumberList(ByteArrayNBT.TYPE, ByteNBT.TYPE));
        }
        if (c == 'L') {
            return new LongArrayNBT(this.getNumberList(LongArrayNBT.TYPE, LongNBT.TYPE));
        }
        if (c == 'I') {
            return new IntArrayNBT(this.getNumberList(IntArrayNBT.TYPE, IntNBT.TYPE));
        }
        this.reader.setCursor(n);
        throw ERROR_INVALID_ARRAY.createWithContext(this.reader, String.valueOf(c));
    }

    private <T extends Number> List<T> getNumberList(INBTType<?> iNBTType, INBTType<?> iNBTType2) throws CommandSyntaxException {
        ArrayList<Number> arrayList = Lists.newArrayList();
        while (this.reader.peek() != ']') {
            int n = this.reader.getCursor();
            INBT iNBT = this.readValue();
            INBTType<?> iNBTType3 = iNBT.getType();
            if (iNBTType3 != iNBTType2) {
                this.reader.setCursor(n);
                throw ERROR_INSERT_MIXED_ARRAY.createWithContext(this.reader, iNBTType3.getTagName(), iNBTType.getTagName());
            }
            if (iNBTType2 == ByteNBT.TYPE) {
                arrayList.add(((NumberNBT)iNBT).getByte());
            } else if (iNBTType2 == LongNBT.TYPE) {
                arrayList.add(((NumberNBT)iNBT).getLong());
            } else {
                arrayList.add(((NumberNBT)iNBT).getInt());
            }
            if (!this.hasElementSeparator()) break;
            if (this.reader.canRead()) continue;
            throw ERROR_EXPECTED_VALUE.createWithContext(this.reader);
        }
        this.expect(']');
        return arrayList;
    }

    private boolean hasElementSeparator() {
        this.reader.skipWhitespace();
        if (this.reader.canRead() && this.reader.peek() == ',') {
            this.reader.skip();
            this.reader.skipWhitespace();
            return false;
        }
        return true;
    }

    private void expect(char c) throws CommandSyntaxException {
        this.reader.skipWhitespace();
        this.reader.expect(c);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("argument.nbt.array.invalid", object);
    }

    private static Message lambda$static$1(Object object, Object object2) {
        return new TranslationTextComponent("argument.nbt.array.mixed", object, object2);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("argument.nbt.list.mixed", object, object2);
    }
}

