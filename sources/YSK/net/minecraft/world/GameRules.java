package net.minecraft.world;

import net.minecraft.nbt.*;
import java.util.*;

public class GameRules
{
    private static final String[] I;
    private TreeMap theGameRules;
    private static final String __OBFID;
    
    public void addGameRule(final String s, final String s2, final ValueType valueType) {
        this.theGameRules.put(s, new Value(s2, valueType));
    }
    
    public String getString(final String s) {
        final Value value = this.theGameRules.get(s);
        String string;
        if (value != null) {
            string = value.getString();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            string = GameRules.I[0x3F ^ 0x21];
        }
        return string;
    }
    
    public int getInt(final String s) {
        final Value value = this.theGameRules.get(s);
        int n;
        if (value != null) {
            n = value.getInt();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean getBoolean(final String s) {
        final Value value = this.theGameRules.get(s);
        int n;
        if (value != null) {
            n = (value.getBoolean() ? 1 : 0);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public NBTTagCompound writeToNBT() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final Iterator<String> iterator = this.theGameRules.keySet().iterator();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String next = iterator.next();
            nbtTagCompound.setString(next, ((Value)this.theGameRules.get(next)).getString());
        }
        return nbtTagCompound;
    }
    
    public GameRules() {
        this.theGameRules = new TreeMap();
        this.addGameRule(GameRules.I["".length()], GameRules.I[" ".length()], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I["  ".length()], GameRules.I["   ".length()], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x95 ^ 0x91], GameRules.I[0x96 ^ 0x93], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x30 ^ 0x36], GameRules.I[0x63 ^ 0x64], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x95 ^ 0x9D], GameRules.I[0x72 ^ 0x7B], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x1E ^ 0x14], GameRules.I[0x55 ^ 0x5E], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x96 ^ 0x9A], GameRules.I[0x2 ^ 0xF], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0xB1 ^ 0xBF], GameRules.I[0xB7 ^ 0xB8], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x59 ^ 0x49], GameRules.I[0x5D ^ 0x4C], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0xE ^ 0x1C], GameRules.I[0xD7 ^ 0xC4], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x68 ^ 0x7C], GameRules.I[0x2A ^ 0x3F], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x9B ^ 0x8D], GameRules.I[0x64 ^ 0x73], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x80 ^ 0x98], GameRules.I[0xA9 ^ 0xB0], ValueType.NUMERICAL_VALUE);
        this.addGameRule(GameRules.I[0x3E ^ 0x24], GameRules.I[0x9E ^ 0x85], ValueType.BOOLEAN_VALUE);
        this.addGameRule(GameRules.I[0x36 ^ 0x2A], GameRules.I[0xDE ^ 0xC3], ValueType.BOOLEAN_VALUE);
    }
    
    public boolean areSameType(final String s, final ValueType valueType) {
        final Value value = this.theGameRules.get(s);
        if (value != null && (value.getType() == valueType || valueType == ValueType.ANY_VALUE)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x93 ^ 0xB3])["".length()] = I(" \u0001\u0014\b\u0004!:;\u0002\u001d", "DnRav");
        GameRules.I[" ".length()] = I("\r\u0000\u0014\u0014", "yraqt");
        GameRules.I["  ".length()] = I("\u0003=\u0001\u000e\b\u00077\u0005 \u0014\t", "nRcIz");
        GameRules.I["   ".length()] = I("<:\u00186", "HHmSt");
        GameRules.I[0xB3 ^ 0xB7] = I("\u000e<#2\u0011\u000b/#,,\n+?", "eYFBX");
        GameRules.I[0x0 ^ 0x5] = I("64/\u00077", "PUCtR");
        GameRules.I[0x8E ^ 0x88] = I("=\u0005\u0015?)\n\u001a9'%0\u0004?", "YjXPK");
        GameRules.I[0xE ^ 0x9] = I("?\"\u0011\"", "KPdGH");
        GameRules.I[0x4F ^ 0x47] = I("( =\u001d\u001b\u0000 \u001f\u0006", "LOpry");
        GameRules.I[0xA ^ 0x3] = I("3\u0006\u0016\u001d", "Gtcxd");
        GameRules.I[0xBF ^ 0xB5] = I("\u0011\u001d#\u001c'\u00106\u0005\u001a;\u0006", "urwuK");
        GameRules.I[0x70 ^ 0x7B] = I("\u0004>6)", "pLCLi");
        GameRules.I[0x4B ^ 0x47] = I("\f%\u000b\u0001%\u0001>7+#\u0007:=", "hJNoQ");
        GameRules.I[0x4F ^ 0x42] = I("\u0002\u0014\u0001\t", "vftlf");
        GameRules.I[0x97 ^ 0x99] = I("\u0016 5,\r\u001b+\u001a-\u0003\u0016$\u00174\u0018\u0005:,", "uOXAl");
        GameRules.I[0xAF ^ 0xA0] = I("\u0017:07", "cHERj");
        GameRules.I[0x58 ^ 0x48] = I("\u000b%\u000081\u0004(&($\u0000*\u0011?\"\u0011-\u001b#", "eDtMC");
        GameRules.I[0x78 ^ 0x69] = I("\u001d%<\u0017", "iWIrR");
        GameRules.I[0x4B ^ 0x59] = I("\u000b\u001f\u0014&*\u0003\u00197/',\t3+6", "opPGS");
        GameRules.I[0xD6 ^ 0xC5] = I("\u0019\u0013$,", "maQIn");
        GameRules.I[0x28 ^ 0x3C] = I("=6\t\n\f<0\u0000\b\u0007<4\u000f%\f\"", "QYnKh");
        GameRules.I[0xBD ^ 0xA8] = I("\u001f\u001f8\u001c", "kmMya");
        GameRules.I[0xD ^ 0x1B] = I("\u0005=\u001a>%\u00134\u0001!,\u0013&\u0006(\u0006\u0013&", "vUuIa");
        GameRules.I[0x7E ^ 0x69] = I(" \u0010\u0018\r", "Tbmhy");
        GameRules.I[0x36 ^ 0x2E] = I("7\u0013\n&=(&\r!9\u0016\u0002\u0001'6", "ErdBR");
        GameRules.I[0x3F ^ 0x26] = I("G", "tmcxh");
        GameRules.I[0xAC ^ 0xB6] = I("8\u000b\u0005\u000b\u0019$\u0003\u0006\u000e4/(\u000e\n>)\u000f\b\u0004", "KnkoZ");
        GameRules.I[0x84 ^ 0x9F] = I("\u0015 \r<", "aRxYY");
        GameRules.I[0x1C ^ 0x0] = I(">\n0\u0017\u0007)\u000b\u0010\u0007\u00069\b\u001d\f\u0002#", "LoTbd");
        GameRules.I[0x60 ^ 0x7D] = I("\f;+\u0014\u001f", "jZGgz");
        GameRules.I[0x78 ^ 0x66] = I("", "TvQtG");
        GameRules.I[0xDF ^ 0xC0] = I("\b\u0018-sw{dBrt}", "KTrCG");
    }
    
    public boolean hasRule(final String s) {
        return this.theGameRules.containsKey(s);
    }
    
    static {
        I();
        __OBFID = GameRules.I[0xBB ^ 0xA4];
    }
    
    public String[] getRules() {
        final Set keySet = this.theGameRules.keySet();
        return (String[])keySet.toArray(new String[keySet.size()]);
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        final Iterator<String> iterator = nbtTagCompound.getKeySet().iterator();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final String s = iterator.next();
            this.setOrCreateGameRule(s, nbtTagCompound.getString(s));
        }
    }
    
    public void setOrCreateGameRule(final String s, final String value) {
        final Value value2 = this.theGameRules.get(s);
        if (value2 != null) {
            value2.setValue(value);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            this.addGameRule(s, value, ValueType.ANY_VALUE);
        }
    }
    
    public enum ValueType
    {
        private static final ValueType[] ENUM$VALUES;
        private static final ValueType[] $VALUES;
        private static final String __OBFID;
        
        BOOLEAN_VALUE(ValueType.I["   ".length()], " ".length(), ValueType.I[0x7 ^ 0x3], " ".length());
        
        private static final String[] I;
        
        ANY_VALUE(ValueType.I[" ".length()], "".length(), ValueType.I["  ".length()], "".length()), 
        NUMERICAL_VALUE(ValueType.I[0x83 ^ 0x86], "  ".length(), ValueType.I[0x27 ^ 0x21], "  ".length());
        
        private static void I() {
            (I = new String[0x5A ^ 0x5D])["".length()] = I("\u000b-4zCxQY{Fy", "HakJs");
            ValueType.I[" ".length()] = I("%9531%;9)", "dwllg");
            ValueType.I["  ".length()] = I("9#!>59!-$", "xmxac");
            ValueType.I["   ".length()] = I("\u00187\b?\f\u001b6\u0018%\b\u0016-\u0002", "ZxGsI");
            ValueType.I[0x77 ^ 0x73] = I(":\u0006\u0001>79\u0007\u0011$34\u001c\u000b", "xINrr");
            ValueType.I[0x30 ^ 0x35] = I("\u00059\u0015\u001d\"\u0002/\u0019\u0014/\u001d-\u0014\r5", "KlXXp");
            ValueType.I[0xC2 ^ 0xC4] = I("\u0006\";/3\u000147&>\u001e6:?$", "Hwvja");
        }
        
        private ValueType(final String s, final int n, final String s2, final int n2) {
        }
        
        static {
            I();
            __OBFID = ValueType.I["".length()];
            final ValueType[] enum$VALUES = new ValueType["   ".length()];
            enum$VALUES["".length()] = ValueType.ANY_VALUE;
            enum$VALUES[" ".length()] = ValueType.BOOLEAN_VALUE;
            enum$VALUES["  ".length()] = ValueType.NUMERICAL_VALUE;
            ENUM$VALUES = enum$VALUES;
            final ValueType[] $values = new ValueType["   ".length()];
            $values["".length()] = ValueType.ANY_VALUE;
            $values[" ".length()] = ValueType.BOOLEAN_VALUE;
            $values["  ".length()] = ValueType.NUMERICAL_VALUE;
            $VALUES = $values;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class Value
    {
        private String valueString;
        private int valueInteger;
        private final ValueType type;
        private static final String[] I;
        private static final String __OBFID;
        private boolean valueBoolean;
        private double valueDouble;
        
        public String getString() {
            return this.valueString;
        }
        
        public ValueType getType() {
            return this.type;
        }
        
        static {
            I();
            __OBFID = Value.I["  ".length()];
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean getBoolean() {
            return this.valueBoolean;
        }
        
        public void setValue(final String valueString) {
            this.valueString = valueString;
            if (valueString != null) {
                if (valueString.equals(Value.I["".length()])) {
                    this.valueBoolean = ("".length() != 0);
                    return;
                }
                if (valueString.equals(Value.I[" ".length()])) {
                    this.valueBoolean = (" ".length() != 0);
                    return;
                }
            }
            this.valueBoolean = Boolean.parseBoolean(valueString);
            int valueInteger;
            if (this.valueBoolean) {
                valueInteger = " ".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                valueInteger = "".length();
            }
            this.valueInteger = valueInteger;
            try {
                this.valueInteger = Integer.parseInt(valueString);
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            catch (NumberFormatException ex) {}
            try {
                this.valueDouble = Double.parseDouble(valueString);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            catch (NumberFormatException ex2) {}
        }
        
        public int getInt() {
            return this.valueInteger;
        }
        
        public Value(final String value, final ValueType type) {
            this.type = type;
            this.setValue(value);
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\f \u000f\u001e(", "jAcmM");
            Value.I[" ".length()] = I("\u0004\u0015&\u0000", "pgSeI");
            Value.I["  ".length()] = I("2\u0014\u0005UHAhjTKF", "qXZex");
        }
    }
}
