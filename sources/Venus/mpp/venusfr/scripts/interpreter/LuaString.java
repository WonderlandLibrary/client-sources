/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import mpp.venusfr.scripts.interpreter.Buffer;
import mpp.venusfr.scripts.interpreter.LuaDouble;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.lib.MathLib;

public class LuaString
extends LuaValue {
    public static LuaValue s_metatable;
    public final byte[] m_bytes;
    public final int m_offset;
    public final int m_length;
    private final int m_hashcode;
    static final int RECENT_STRINGS_CACHE_SIZE = 128;
    static final int RECENT_STRINGS_MAX_LENGTH = 32;

    public static LuaString valueOf(String string) {
        char[] cArray = string.toCharArray();
        byte[] byArray = new byte[LuaString.lengthAsUtf8(cArray)];
        LuaString.encodeToUtf8(cArray, cArray.length, byArray, 0);
        return LuaString.valueUsing(byArray, 0, byArray.length);
    }

    public static LuaString valueOf(byte[] byArray, int n, int n2) {
        LuaString luaString;
        if (n2 > 32) {
            return LuaString.valueFromCopy(byArray, n, n2);
        }
        int n3 = LuaString.hashCode(byArray, n, n2);
        int n4 = n3 & 0x7F;
        LuaString luaString2 = RecentShortStrings.recent_short_strings[n4];
        if (luaString2 != null && luaString2.m_hashcode == n3 && luaString2.byteseq(byArray, n, n2)) {
            return luaString2;
        }
        RecentShortStrings.recent_short_strings[n4] = luaString = LuaString.valueFromCopy(byArray, n, n2);
        return luaString;
    }

    private static LuaString valueFromCopy(byte[] byArray, int n, int n2) {
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, n, byArray2, 0, n2);
        return new LuaString(byArray2, 0, n2);
    }

    public static LuaString valueUsing(byte[] byArray, int n, int n2) {
        LuaString luaString;
        if (byArray.length > 32) {
            return new LuaString(byArray, n, n2);
        }
        int n3 = LuaString.hashCode(byArray, n, n2);
        int n4 = n3 & 0x7F;
        LuaString luaString2 = RecentShortStrings.recent_short_strings[n4];
        if (luaString2 != null && luaString2.m_hashcode == n3 && luaString2.byteseq(byArray, n, n2)) {
            return luaString2;
        }
        RecentShortStrings.recent_short_strings[n4] = luaString = new LuaString(byArray, n, n2);
        return luaString;
    }

    public static LuaString valueOf(char[] cArray) {
        return LuaString.valueOf(cArray, 0, cArray.length);
    }

    public static LuaString valueOf(char[] cArray, int n, int n2) {
        byte[] byArray = new byte[n2];
        for (int i = 0; i < n2; ++i) {
            byArray[i] = (byte)cArray[i + n];
        }
        return LuaString.valueUsing(byArray, 0, n2);
    }

    public static LuaString valueOf(byte[] byArray) {
        return LuaString.valueOf(byArray, 0, byArray.length);
    }

    public static LuaString valueUsing(byte[] byArray) {
        return LuaString.valueUsing(byArray, 0, byArray.length);
    }

    private LuaString(byte[] byArray, int n, int n2) {
        this.m_bytes = byArray;
        this.m_offset = n;
        this.m_length = n2;
        this.m_hashcode = LuaString.hashCode(byArray, n, n2);
    }

    @Override
    public boolean isstring() {
        return false;
    }

    @Override
    public LuaValue getmetatable() {
        return s_metatable;
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public String typename() {
        return "string";
    }

    @Override
    public String tojstring() {
        return LuaString.decodeAsUtf8(this.m_bytes, this.m_offset, this.m_length);
    }

    @Override
    public LuaValue neg() {
        double d = this.scannumber();
        return Double.isNaN(d) ? super.neg() : LuaString.valueOf(-d);
    }

    @Override
    public LuaValue add(LuaValue luaValue) {
        double d = this.scannumber();
        return Double.isNaN(d) ? this.arithmt(ADD, luaValue) : luaValue.add(d);
    }

    @Override
    public LuaValue add(double d) {
        return LuaString.valueOf(this.checkarith() + d);
    }

    @Override
    public LuaValue add(int n) {
        return LuaString.valueOf(this.checkarith() + (double)n);
    }

    @Override
    public LuaValue sub(LuaValue luaValue) {
        double d = this.scannumber();
        return Double.isNaN(d) ? this.arithmt(SUB, luaValue) : luaValue.subFrom(d);
    }

    @Override
    public LuaValue sub(double d) {
        return LuaString.valueOf(this.checkarith() - d);
    }

    @Override
    public LuaValue sub(int n) {
        return LuaString.valueOf(this.checkarith() - (double)n);
    }

    @Override
    public LuaValue subFrom(double d) {
        return LuaString.valueOf(d - this.checkarith());
    }

    @Override
    public LuaValue mul(LuaValue luaValue) {
        double d = this.scannumber();
        return Double.isNaN(d) ? this.arithmt(MUL, luaValue) : luaValue.mul(d);
    }

    @Override
    public LuaValue mul(double d) {
        return LuaString.valueOf(this.checkarith() * d);
    }

    @Override
    public LuaValue mul(int n) {
        return LuaString.valueOf(this.checkarith() * (double)n);
    }

    @Override
    public LuaValue pow(LuaValue luaValue) {
        double d = this.scannumber();
        return Double.isNaN(d) ? this.arithmt(POW, luaValue) : luaValue.powWith(d);
    }

    @Override
    public LuaValue pow(double d) {
        return MathLib.dpow(this.checkarith(), d);
    }

    @Override
    public LuaValue pow(int n) {
        return MathLib.dpow(this.checkarith(), n);
    }

    @Override
    public LuaValue powWith(double d) {
        return MathLib.dpow(d, this.checkarith());
    }

    @Override
    public LuaValue powWith(int n) {
        return MathLib.dpow(n, this.checkarith());
    }

    @Override
    public LuaValue div(LuaValue luaValue) {
        double d = this.scannumber();
        return Double.isNaN(d) ? this.arithmt(DIV, luaValue) : luaValue.divInto(d);
    }

    @Override
    public LuaValue div(double d) {
        return LuaDouble.ddiv(this.checkarith(), d);
    }

    @Override
    public LuaValue div(int n) {
        return LuaDouble.ddiv(this.checkarith(), n);
    }

    @Override
    public LuaValue divInto(double d) {
        return LuaDouble.ddiv(d, this.checkarith());
    }

    @Override
    public LuaValue mod(LuaValue luaValue) {
        double d = this.scannumber();
        return Double.isNaN(d) ? this.arithmt(MOD, luaValue) : luaValue.modFrom(d);
    }

    @Override
    public LuaValue mod(double d) {
        return LuaDouble.dmod(this.checkarith(), d);
    }

    @Override
    public LuaValue mod(int n) {
        return LuaDouble.dmod(this.checkarith(), n);
    }

    @Override
    public LuaValue modFrom(double d) {
        return LuaDouble.dmod(d, this.checkarith());
    }

    @Override
    public LuaValue lt(LuaValue luaValue) {
        return luaValue.isstring() ? (luaValue.strcmp(this) > 0 ? LuaValue.TRUE : FALSE) : super.lt(luaValue);
    }

    @Override
    public boolean lt_b(LuaValue luaValue) {
        return luaValue.isstring() ? luaValue.strcmp(this) > 0 : super.lt_b(luaValue);
    }

    @Override
    public boolean lt_b(int n) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public boolean lt_b(double d) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public LuaValue lteq(LuaValue luaValue) {
        return luaValue.isstring() ? (luaValue.strcmp(this) >= 0 ? LuaValue.TRUE : FALSE) : super.lteq(luaValue);
    }

    @Override
    public boolean lteq_b(LuaValue luaValue) {
        return luaValue.isstring() ? luaValue.strcmp(this) >= 0 : super.lteq_b(luaValue);
    }

    @Override
    public boolean lteq_b(int n) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public boolean lteq_b(double d) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public LuaValue gt(LuaValue luaValue) {
        return luaValue.isstring() ? (luaValue.strcmp(this) < 0 ? LuaValue.TRUE : FALSE) : super.gt(luaValue);
    }

    @Override
    public boolean gt_b(LuaValue luaValue) {
        return luaValue.isstring() ? luaValue.strcmp(this) < 0 : super.gt_b(luaValue);
    }

    @Override
    public boolean gt_b(int n) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public boolean gt_b(double d) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public LuaValue gteq(LuaValue luaValue) {
        return luaValue.isstring() ? (luaValue.strcmp(this) <= 0 ? LuaValue.TRUE : FALSE) : super.gteq(luaValue);
    }

    @Override
    public boolean gteq_b(LuaValue luaValue) {
        return luaValue.isstring() ? luaValue.strcmp(this) <= 0 : super.gteq_b(luaValue);
    }

    @Override
    public boolean gteq_b(int n) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public boolean gteq_b(double d) {
        this.typerror("attempt to compare string with number");
        return true;
    }

    @Override
    public LuaValue concat(LuaValue luaValue) {
        return luaValue.concatTo(this);
    }

    @Override
    public Buffer concat(Buffer buffer) {
        return buffer.concatTo(this);
    }

    @Override
    public LuaValue concatTo(LuaNumber luaNumber) {
        return this.concatTo(luaNumber.strvalue());
    }

    @Override
    public LuaValue concatTo(LuaString luaString) {
        byte[] byArray = new byte[luaString.m_length + this.m_length];
        System.arraycopy(luaString.m_bytes, luaString.m_offset, byArray, 0, luaString.m_length);
        System.arraycopy(this.m_bytes, this.m_offset, byArray, luaString.m_length, this.m_length);
        return LuaString.valueUsing(byArray, 0, byArray.length);
    }

    @Override
    public int strcmp(LuaValue luaValue) {
        return -luaValue.strcmp(this);
    }

    @Override
    public int strcmp(LuaString luaString) {
        int n = 0;
        for (int i = 0; n < this.m_length && i < luaString.m_length; ++n, ++i) {
            if (this.m_bytes[this.m_offset + n] == luaString.m_bytes[luaString.m_offset + i]) continue;
            return this.m_bytes[this.m_offset + n] - luaString.m_bytes[luaString.m_offset + i];
        }
        return this.m_length - luaString.m_length;
    }

    private double checkarith() {
        double d = this.scannumber();
        if (Double.isNaN(d)) {
            this.aritherror();
        }
        return d;
    }

    @Override
    public int checkint() {
        return (int)this.checkdouble();
    }

    @Override
    public LuaInteger checkinteger() {
        return LuaString.valueOf(this.checkint());
    }

    @Override
    public long checklong() {
        return (long)this.checkdouble();
    }

    @Override
    public double checkdouble() {
        double d = this.scannumber();
        if (Double.isNaN(d)) {
            this.argerror("number");
        }
        return d;
    }

    @Override
    public LuaNumber checknumber() {
        return LuaString.valueOf(this.checkdouble());
    }

    @Override
    public LuaNumber checknumber(String string) {
        double d = this.scannumber();
        if (Double.isNaN(d)) {
            LuaString.error(string);
        }
        return LuaString.valueOf(d);
    }

    @Override
    public boolean isnumber() {
        double d = this.scannumber();
        return !Double.isNaN(d);
    }

    @Override
    public boolean isint() {
        double d = this.scannumber();
        if (Double.isNaN(d)) {
            return true;
        }
        int n = (int)d;
        return (double)n == d;
    }

    @Override
    public boolean islong() {
        double d = this.scannumber();
        if (Double.isNaN(d)) {
            return true;
        }
        long l = (long)d;
        return (double)l == d;
    }

    @Override
    public byte tobyte() {
        return (byte)this.toint();
    }

    @Override
    public char tochar() {
        return (char)this.toint();
    }

    @Override
    public double todouble() {
        double d = this.scannumber();
        return Double.isNaN(d) ? 0.0 : d;
    }

    @Override
    public float tofloat() {
        return (float)this.todouble();
    }

    @Override
    public int toint() {
        return (int)this.tolong();
    }

    @Override
    public long tolong() {
        return (long)this.todouble();
    }

    @Override
    public short toshort() {
        return (short)this.toint();
    }

    @Override
    public double optdouble(double d) {
        return this.checkdouble();
    }

    @Override
    public int optint(int n) {
        return this.checkint();
    }

    @Override
    public LuaInteger optinteger(LuaInteger luaInteger) {
        return this.checkinteger();
    }

    @Override
    public long optlong(long l) {
        return this.checklong();
    }

    @Override
    public LuaNumber optnumber(LuaNumber luaNumber) {
        return this.checknumber();
    }

    @Override
    public LuaString optstring(LuaString luaString) {
        return this;
    }

    @Override
    public LuaValue tostring() {
        return this;
    }

    @Override
    public String optjstring(String string) {
        return this.tojstring();
    }

    @Override
    public LuaString strvalue() {
        return this;
    }

    public LuaString substring(int n, int n2) {
        int n3 = this.m_offset + n;
        int n4 = n2 - n;
        return n4 >= this.m_length / 2 ? LuaString.valueUsing(this.m_bytes, n3, n4) : LuaString.valueOf(this.m_bytes, n3, n4);
    }

    public int hashCode() {
        return this.m_hashcode;
    }

    public static int hashCode(byte[] byArray, int n, int n2) {
        int n3 = n2;
        int n4 = (n2 >> 5) + 1;
        for (int i = n2; i >= n4; i -= n4) {
            n3 ^= (n3 << 5) + (n3 >> 2) + (byArray[n + i - 1] & 0xFF);
        }
        return n3;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof LuaString) {
            return this.raweq((LuaString)object);
        }
        return true;
    }

    @Override
    public LuaValue eq(LuaValue luaValue) {
        return luaValue.raweq(this) ? TRUE : FALSE;
    }

    @Override
    public boolean eq_b(LuaValue luaValue) {
        return luaValue.raweq(this);
    }

    @Override
    public boolean raweq(LuaValue luaValue) {
        return luaValue.raweq(this);
    }

    @Override
    public boolean raweq(LuaString luaString) {
        if (this == luaString) {
            return false;
        }
        if (luaString.m_length != this.m_length) {
            return true;
        }
        if (luaString.m_bytes == this.m_bytes && luaString.m_offset == this.m_offset) {
            return false;
        }
        if (luaString.hashCode() != this.hashCode()) {
            return true;
        }
        for (int i = 0; i < this.m_length; ++i) {
            if (luaString.m_bytes[luaString.m_offset + i] == this.m_bytes[this.m_offset + i]) continue;
            return true;
        }
        return false;
    }

    public static boolean equals(LuaString luaString, int n, LuaString luaString2, int n2, int n3) {
        return LuaString.equals(luaString.m_bytes, luaString.m_offset + n, luaString2.m_bytes, luaString2.m_offset + n2, n3);
    }

    private boolean byteseq(byte[] byArray, int n, int n2) {
        return this.m_length == n2 && LuaString.equals(this.m_bytes, this.m_offset, byArray, n, n2);
    }

    public static boolean equals(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        if (byArray.length < n + n3 || byArray2.length < n2 + n3) {
            return true;
        }
        while (--n3 >= 0) {
            if (byArray[n++] == byArray2[n2++]) continue;
            return true;
        }
        return false;
    }

    public void write(DataOutputStream dataOutputStream, int n, int n2) throws IOException {
        dataOutputStream.write(this.m_bytes, this.m_offset + n, n2);
    }

    @Override
    public LuaValue len() {
        return LuaInteger.valueOf(this.m_length);
    }

    @Override
    public int length() {
        return this.m_length;
    }

    @Override
    public int rawlen() {
        return this.m_length;
    }

    public int luaByte(int n) {
        return this.m_bytes[this.m_offset + n] & 0xFF;
    }

    public int charAt(int n) {
        if (n < 0 || n >= this.m_length) {
            throw new IndexOutOfBoundsException();
        }
        return this.luaByte(n);
    }

    @Override
    public String checkjstring() {
        return this.tojstring();
    }

    @Override
    public LuaString checkstring() {
        return this;
    }

    public InputStream toInputStream() {
        return new ByteArrayInputStream(this.m_bytes, this.m_offset, this.m_length);
    }

    public void copyInto(int n, byte[] byArray, int n2, int n3) {
        System.arraycopy(this.m_bytes, this.m_offset + n, byArray, n2, n3);
    }

    public int indexOfAny(LuaString luaString) {
        int n = this.m_offset + this.m_length;
        int n2 = luaString.m_offset + luaString.m_length;
        for (int i = this.m_offset; i < n; ++i) {
            for (int j = luaString.m_offset; j < n2; ++j) {
                if (this.m_bytes[i] != luaString.m_bytes[j]) continue;
                return i - this.m_offset;
            }
        }
        return 1;
    }

    public int indexOf(byte by, int n) {
        for (int i = n; i < this.m_length; ++i) {
            if (this.m_bytes[this.m_offset + i] != by) continue;
            return i;
        }
        return 1;
    }

    public int indexOf(LuaString luaString, int n) {
        int n2 = luaString.length();
        int n3 = this.m_length - n2;
        for (int i = n; i <= n3; ++i) {
            if (!LuaString.equals(this.m_bytes, this.m_offset + i, luaString.m_bytes, luaString.m_offset, n2)) continue;
            return i;
        }
        return 1;
    }

    public int lastIndexOf(LuaString luaString) {
        int n;
        int n2 = luaString.length();
        for (int i = n = this.m_length - n2; i >= 0; --i) {
            if (!LuaString.equals(this.m_bytes, this.m_offset + i, luaString.m_bytes, luaString.m_offset, n2)) continue;
            return i;
        }
        return 1;
    }

    public static String decodeAsUtf8(byte[] byArray, int n, int n2) {
        int n3 = n;
        int n4 = n + n2;
        int n5 = 0;
        while (n3 < n4) {
            switch (0xE0 & byArray[n3++]) {
                case 224: {
                    ++n3;
                }
                case 192: {
                    ++n3;
                }
            }
            ++n5;
        }
        char[] cArray = new char[n5];
        n3 = n;
        n4 = n + n2;
        n5 = 0;
        while (n3 < n4) {
            int n6;
            cArray[n5++] = (char)((n6 = byArray[n3++]) >= 0 || n3 >= n4 ? n6 : (n6 < -32 || n3 + 1 >= n4 ? (n6 & 0x3F) << 6 | byArray[n3++] & 0x3F : (n6 & 0xF) << 12 | (byArray[n3++] & 0x3F) << 6 | byArray[n3++] & 0x3F));
        }
        return new String(cArray);
    }

    public static int lengthAsUtf8(char[] cArray) {
        int n;
        int n2 = n = cArray.length;
        while (--n2 >= 0) {
            char c = cArray[n2];
            if (c < '\u0080') continue;
            n += c >= '\u0800' ? 2 : 1;
        }
        return n;
    }

    public static int encodeToUtf8(char[] cArray, int n, byte[] byArray, int n2) {
        int n3 = n2;
        for (int i = 0; i < n; ++i) {
            char c = cArray[i];
            if (c < '\u0080') {
                byArray[n3++] = (byte)c;
                continue;
            }
            if (c < '\u0800') {
                byArray[n3++] = (byte)(0xC0 | c >> 6 & 0x1F);
                byArray[n3++] = (byte)(0x80 | c & 0x3F);
                continue;
            }
            byArray[n3++] = (byte)(0xE0 | c >> 12 & 0xF);
            byArray[n3++] = (byte)(0x80 | c >> 6 & 0x3F);
            byArray[n3++] = (byte)(0x80 | c & 0x3F);
        }
        return n3 - n2;
    }

    public boolean isValidUtf8() {
        int n = this.m_offset;
        int n2 = this.m_offset + this.m_length;
        while (n < n2) {
            byte by;
            if ((by = this.m_bytes[n++]) >= 0 || (by & 0xE0) == 192 && n < n2 && (this.m_bytes[n++] & 0xC0) == 128 || (by & 0xF0) == 224 && n + 1 < n2 && (this.m_bytes[n++] & 0xC0) == 128 && (this.m_bytes[n++] & 0xC0) == 128) continue;
            return true;
        }
        return false;
    }

    @Override
    public LuaValue tonumber() {
        double d = this.scannumber();
        return Double.isNaN(d) ? NIL : LuaString.valueOf(d);
    }

    public LuaValue tonumber(int n) {
        double d = this.scannumber(n);
        return Double.isNaN(d) ? NIL : LuaString.valueOf(d);
    }

    public double scannumber() {
        int n;
        int n2 = this.m_offset + this.m_length;
        for (n = this.m_offset; n < n2 && this.m_bytes[n] == 32; ++n) {
        }
        while (n < n2 && this.m_bytes[n2 - 1] == 32) {
            --n2;
        }
        if (n >= n2) {
            return Double.NaN;
        }
        if (this.m_bytes[n] == 48 && n + 1 < n2 && (this.m_bytes[n + 1] == 120 || this.m_bytes[n + 1] == 88)) {
            return this.scanlong(16, n + 2, n2);
        }
        double d = this.scanlong(10, n, n2);
        return Double.isNaN(d) ? this.scandouble(n, n2) : d;
    }

    public double scannumber(int n) {
        int n2;
        if (n < 2 || n > 36) {
            return Double.NaN;
        }
        int n3 = this.m_offset + this.m_length;
        for (n2 = this.m_offset; n2 < n3 && this.m_bytes[n2] == 32; ++n2) {
        }
        while (n2 < n3 && this.m_bytes[n3 - 1] == 32) {
            --n3;
        }
        if (n2 >= n3) {
            return Double.NaN;
        }
        return this.scanlong(n, n2, n3);
    }

    private double scanlong(int n, int n2, int n3) {
        int n4;
        long l = 0L;
        boolean bl = this.m_bytes[n2] == 45;
        int n5 = n4 = bl ? n2 + 1 : n2;
        while (n4 < n3) {
            int n6 = this.m_bytes[n4] - (n <= 10 || this.m_bytes[n4] >= 48 && this.m_bytes[n4] <= 57 ? 48 : (this.m_bytes[n4] >= 65 && this.m_bytes[n4] <= 90 ? 55 : 87));
            if (n6 < 0 || n6 >= n) {
                return Double.NaN;
            }
            if ((l = l * (long)n + (long)n6) < 0L) {
                return Double.NaN;
            }
            ++n4;
        }
        return bl ? (double)(-l) : (double)l;
    }

    private double scandouble(int n, int n2) {
        if (n2 > n + 64) {
            n2 = n + 64;
        }
        block5: for (int i = n; i < n2; ++i) {
            switch (this.m_bytes[i]) {
                case 43: 
                case 45: 
                case 46: 
                case 48: 
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: 
                case 55: 
                case 56: 
                case 57: 
                case 69: 
                case 101: {
                    continue block5;
                }
                default: {
                    return Double.NaN;
                }
            }
        }
        char[] cArray = new char[n2 - n];
        for (int i = n; i < n2; ++i) {
            cArray[i - n] = (char)this.m_bytes[i];
        }
        try {
            return Double.parseDouble(new String(cArray));
        } catch (Exception exception) {
            return Double.NaN;
        }
    }

    public void printToStream(PrintStream printStream) {
        int n = this.m_length;
        for (int i = 0; i < n; ++i) {
            byte by = this.m_bytes[this.m_offset + i];
            printStream.print((char)by);
        }
    }

    private static final class RecentShortStrings {
        private static final LuaString[] recent_short_strings = new LuaString[128];

        private RecentShortStrings() {
        }
    }
}

