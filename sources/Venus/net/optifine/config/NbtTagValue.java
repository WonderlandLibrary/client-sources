/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import java.util.Arrays;
import java.util.regex.Pattern;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.optifine.Config;
import net.optifine.util.StrUtils;
import org.apache.commons.lang3.StringEscapeUtils;

public class NbtTagValue {
    private String[] parents = null;
    private String name = null;
    private boolean negative = false;
    private int type = 0;
    private String value = null;
    private int valueFormat = 0;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_PATTERN = 1;
    private static final int TYPE_IPATTERN = 2;
    private static final int TYPE_REGEX = 3;
    private static final int TYPE_IREGEX = 4;
    private static final String PREFIX_PATTERN = "pattern:";
    private static final String PREFIX_IPATTERN = "ipattern:";
    private static final String PREFIX_REGEX = "regex:";
    private static final String PREFIX_IREGEX = "iregex:";
    private static final int FORMAT_DEFAULT = 0;
    private static final int FORMAT_HEX_COLOR = 1;
    private static final String PREFIX_HEX_COLOR = "#";
    private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");

    public NbtTagValue(String string, String string2) {
        String[] stringArray = Config.tokenize(string, ".");
        this.parents = Arrays.copyOfRange(stringArray, 0, stringArray.length - 1);
        this.name = stringArray[stringArray.length - 1];
        if (string2.startsWith("!")) {
            this.negative = true;
            string2 = string2.substring(1);
        }
        if (string2.startsWith(PREFIX_PATTERN)) {
            this.type = 1;
            string2 = string2.substring(8);
        } else if (string2.startsWith(PREFIX_IPATTERN)) {
            this.type = 2;
            string2 = string2.substring(9).toLowerCase();
        } else if (string2.startsWith(PREFIX_REGEX)) {
            this.type = 3;
            string2 = string2.substring(6);
        } else if (string2.startsWith(PREFIX_IREGEX)) {
            this.type = 4;
            string2 = string2.substring(7).toLowerCase();
        } else {
            this.type = 0;
        }
        string2 = StringEscapeUtils.unescapeJava(string2);
        if (this.type == 0 && PATTERN_HEX_COLOR.matcher(string2).matches()) {
            this.valueFormat = 1;
        }
        this.value = string2;
    }

    public boolean matches(CompoundNBT compoundNBT) {
        if (this.negative) {
            return !this.matchesCompound(compoundNBT);
        }
        return this.matchesCompound(compoundNBT);
    }

    public boolean matchesCompound(CompoundNBT compoundNBT) {
        if (compoundNBT == null) {
            return true;
        }
        INBT iNBT = compoundNBT;
        for (int i = 0; i < this.parents.length; ++i) {
            String string = this.parents[i];
            if ((iNBT = NbtTagValue.getChildTag(iNBT, string)) != null) continue;
            return true;
        }
        if (this.name.equals("*")) {
            return this.matchesAnyChild(iNBT);
        }
        INBT iNBT2 = NbtTagValue.getChildTag(iNBT, this.name);
        if (iNBT2 == null) {
            return true;
        }
        return this.matchesBase(iNBT2);
    }

    private boolean matchesAnyChild(INBT iNBT) {
        INBT iNBT2;
        INBT iNBT3;
        if (iNBT instanceof CompoundNBT) {
            iNBT3 = (CompoundNBT)iNBT;
            for (String string : ((CompoundNBT)iNBT3).keySet()) {
                iNBT2 = ((CompoundNBT)iNBT3).get(string);
                if (!this.matchesBase(iNBT2)) continue;
                return false;
            }
        }
        if (iNBT instanceof ListNBT) {
            iNBT3 = (ListNBT)iNBT;
            int n = ((ListNBT)iNBT3).size();
            for (int i = 0; i < n; ++i) {
                iNBT2 = ((ListNBT)iNBT3).get(i);
                if (!this.matchesBase(iNBT2)) continue;
                return false;
            }
        }
        return true;
    }

    private static INBT getChildTag(INBT iNBT, String string) {
        if (iNBT instanceof CompoundNBT) {
            CompoundNBT compoundNBT = (CompoundNBT)iNBT;
            return compoundNBT.get(string);
        }
        if (iNBT instanceof ListNBT) {
            ListNBT listNBT = (ListNBT)iNBT;
            if (string.equals("count")) {
                return IntNBT.valueOf(listNBT.size());
            }
            int n = Config.parseInt(string, -1);
            return n >= 0 && n < listNBT.size() ? listNBT.get(n) : null;
        }
        return null;
    }

    public boolean matchesBase(INBT iNBT) {
        if (iNBT == null) {
            return true;
        }
        String string = NbtTagValue.getNbtString(iNBT, this.valueFormat);
        return this.matchesValue(string);
    }

    public boolean matchesValue(String string) {
        if (string == null) {
            return true;
        }
        switch (this.type) {
            case 0: {
                return string.equals(this.value);
            }
            case 1: {
                return this.matchesPattern(string, this.value);
            }
            case 2: {
                return this.matchesPattern(string.toLowerCase(), this.value);
            }
            case 3: {
                return this.matchesRegex(string, this.value);
            }
            case 4: {
                return this.matchesRegex(string.toLowerCase(), this.value);
            }
        }
        throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
    }

    private boolean matchesPattern(String string, String string2) {
        return StrUtils.equalsMask(string, string2, '*', '?');
    }

    private boolean matchesRegex(String string, String string2) {
        return string.matches(string2);
    }

    private static String getNbtString(INBT iNBT, int n) {
        if (iNBT == null) {
            return null;
        }
        if (!(iNBT instanceof StringNBT)) {
            if (iNBT instanceof IntNBT) {
                IntNBT intNBT = (IntNBT)iNBT;
                return n == 1 ? PREFIX_HEX_COLOR + StrUtils.fillLeft(Integer.toHexString(intNBT.getInt()), 6, '0') : Integer.toString(intNBT.getInt());
            }
            if (iNBT instanceof ByteNBT) {
                ByteNBT byteNBT = (ByteNBT)iNBT;
                return Byte.toString(byteNBT.getByte());
            }
            if (iNBT instanceof ShortNBT) {
                ShortNBT shortNBT = (ShortNBT)iNBT;
                return Short.toString(shortNBT.getShort());
            }
            if (iNBT instanceof LongNBT) {
                LongNBT longNBT = (LongNBT)iNBT;
                return Long.toString(longNBT.getLong());
            }
            if (iNBT instanceof FloatNBT) {
                FloatNBT floatNBT = (FloatNBT)iNBT;
                return Float.toString(floatNBT.getFloat());
            }
            if (iNBT instanceof DoubleNBT) {
                DoubleNBT doubleNBT = (DoubleNBT)iNBT;
                return Double.toString(doubleNBT.getDouble());
            }
            return iNBT.toString();
        }
        StringNBT stringNBT = (StringNBT)iNBT;
        String string = stringNBT.getString();
        if (string.startsWith("{") && string.endsWith("}")) {
            string = NbtTagValue.getMergedJsonText(string);
        } else if (string.startsWith("[{") && string.endsWith("}]")) {
            string = NbtTagValue.getMergedJsonText(string);
        }
        return string;
    }

    private static String getMergedJsonText(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = "\"text\":\"";
        int n = -1;
        while ((n = string.indexOf(string2, n + 1)) >= 0) {
            String string3 = NbtTagValue.parseString(string, n + string2.length());
            if (string3 == null) continue;
            stringBuilder.append(string3);
        }
        return stringBuilder.toString();
    }

    private static String parseString(String string, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = n; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (bl) {
                if (c == 'b') {
                    stringBuilder.append('\b');
                } else if (c == 'f') {
                    stringBuilder.append('\f');
                } else if (c == 'n') {
                    stringBuilder.append('\n');
                } else if (c == 'r') {
                    stringBuilder.append('\r');
                } else if (c == 't') {
                    stringBuilder.append('\t');
                } else {
                    stringBuilder.append(c);
                }
                bl = false;
                continue;
            }
            if (c == '\\') {
                bl = true;
                continue;
            }
            if (c == '\"') break;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.parents.length; ++i) {
            String string = this.parents[i];
            if (i > 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append(string);
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.append(".");
        }
        stringBuffer.append(this.name);
        stringBuffer.append(" = ");
        stringBuffer.append(this.value);
        return stringBuffer.toString();
    }
}

