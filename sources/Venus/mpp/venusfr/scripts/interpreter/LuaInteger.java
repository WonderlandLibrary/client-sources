/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaDouble;
import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.lib.MathLib;

public class LuaInteger
extends LuaNumber {
    private static final LuaInteger[] intValues = new LuaInteger[512];
    public final int v;

    public static LuaInteger valueOf(int n) {
        return n <= 255 && n >= -256 ? intValues[n + 256] : new LuaInteger(n);
    }

    public static LuaNumber valueOf(long l) {
        int n = (int)l;
        return l == (long)n ? (n <= 255 && n >= -256 ? intValues[n + 256] : new LuaInteger(n)) : LuaDouble.valueOf(l);
    }

    LuaInteger(int n) {
        this.v = n;
    }

    @Override
    public boolean isint() {
        return false;
    }

    @Override
    public boolean isinttype() {
        return false;
    }

    @Override
    public boolean islong() {
        return false;
    }

    @Override
    public byte tobyte() {
        return (byte)this.v;
    }

    @Override
    public char tochar() {
        return (char)this.v;
    }

    @Override
    public double todouble() {
        return this.v;
    }

    @Override
    public float tofloat() {
        return this.v;
    }

    @Override
    public int toint() {
        return this.v;
    }

    @Override
    public long tolong() {
        return this.v;
    }

    @Override
    public short toshort() {
        return (short)this.v;
    }

    @Override
    public double optdouble(double d) {
        return this.v;
    }

    @Override
    public int optint(int n) {
        return this.v;
    }

    @Override
    public LuaInteger optinteger(LuaInteger luaInteger) {
        return this;
    }

    @Override
    public long optlong(long l) {
        return this.v;
    }

    @Override
    public String tojstring() {
        return Integer.toString(this.v);
    }

    @Override
    public LuaString strvalue() {
        return LuaString.valueOf(Integer.toString(this.v));
    }

    @Override
    public LuaString optstring(LuaString luaString) {
        return LuaString.valueOf(Integer.toString(this.v));
    }

    @Override
    public LuaValue tostring() {
        return LuaString.valueOf(Integer.toString(this.v));
    }

    @Override
    public String optjstring(String string) {
        return Integer.toString(this.v);
    }

    @Override
    public LuaInteger checkinteger() {
        return this;
    }

    @Override
    public boolean isstring() {
        return false;
    }

    public int hashCode() {
        return this.v;
    }

    public static int hashCode(int n) {
        return n;
    }

    @Override
    public LuaValue neg() {
        return LuaInteger.valueOf(-((long)this.v));
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof LuaInteger && ((LuaInteger)object).v == this.v;
    }

    @Override
    public LuaValue eq(LuaValue luaValue) {
        return luaValue.raweq(this.v) ? TRUE : FALSE;
    }

    @Override
    public boolean eq_b(LuaValue luaValue) {
        return luaValue.raweq(this.v);
    }

    @Override
    public boolean raweq(LuaValue luaValue) {
        return luaValue.raweq(this.v);
    }

    @Override
    public boolean raweq(double d) {
        return (double)this.v == d;
    }

    @Override
    public boolean raweq(int n) {
        return this.v == n;
    }

    @Override
    public LuaValue add(LuaValue luaValue) {
        return luaValue.add(this.v);
    }

    @Override
    public LuaValue add(double d) {
        return LuaDouble.valueOf(d + (double)this.v);
    }

    @Override
    public LuaValue add(int n) {
        return LuaInteger.valueOf((long)n + (long)this.v);
    }

    @Override
    public LuaValue sub(LuaValue luaValue) {
        return luaValue.subFrom(this.v);
    }

    @Override
    public LuaValue sub(double d) {
        return LuaDouble.valueOf((double)this.v - d);
    }

    @Override
    public LuaValue sub(int n) {
        return LuaDouble.valueOf(this.v - n);
    }

    @Override
    public LuaValue subFrom(double d) {
        return LuaDouble.valueOf(d - (double)this.v);
    }

    @Override
    public LuaValue subFrom(int n) {
        return LuaInteger.valueOf((long)n - (long)this.v);
    }

    @Override
    public LuaValue mul(LuaValue luaValue) {
        return luaValue.mul(this.v);
    }

    @Override
    public LuaValue mul(double d) {
        return LuaDouble.valueOf(d * (double)this.v);
    }

    @Override
    public LuaValue mul(int n) {
        return LuaInteger.valueOf((long)n * (long)this.v);
    }

    @Override
    public LuaValue pow(LuaValue luaValue) {
        return luaValue.powWith(this.v);
    }

    @Override
    public LuaValue pow(double d) {
        return MathLib.dpow(this.v, d);
    }

    @Override
    public LuaValue pow(int n) {
        return MathLib.dpow(this.v, n);
    }

    @Override
    public LuaValue powWith(double d) {
        return MathLib.dpow(d, this.v);
    }

    @Override
    public LuaValue powWith(int n) {
        return MathLib.dpow(n, this.v);
    }

    @Override
    public LuaValue div(LuaValue luaValue) {
        return luaValue.divInto(this.v);
    }

    @Override
    public LuaValue div(double d) {
        return LuaDouble.ddiv(this.v, d);
    }

    @Override
    public LuaValue div(int n) {
        return LuaDouble.ddiv(this.v, n);
    }

    @Override
    public LuaValue divInto(double d) {
        return LuaDouble.ddiv(d, this.v);
    }

    @Override
    public LuaValue mod(LuaValue luaValue) {
        return luaValue.modFrom(this.v);
    }

    @Override
    public LuaValue mod(double d) {
        return LuaDouble.dmod(this.v, d);
    }

    @Override
    public LuaValue mod(int n) {
        return LuaDouble.dmod(this.v, n);
    }

    @Override
    public LuaValue modFrom(double d) {
        return LuaDouble.dmod(d, this.v);
    }

    @Override
    public LuaValue lt(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.gt_b(this.v) ? TRUE : FALSE) : super.lt(luaValue);
    }

    @Override
    public LuaValue lt(double d) {
        return (double)this.v < d ? TRUE : FALSE;
    }

    @Override
    public LuaValue lt(int n) {
        return this.v < n ? TRUE : FALSE;
    }

    @Override
    public boolean lt_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.gt_b(this.v) : super.lt_b(luaValue);
    }

    @Override
    public boolean lt_b(int n) {
        return this.v < n;
    }

    @Override
    public boolean lt_b(double d) {
        return (double)this.v < d;
    }

    @Override
    public LuaValue lteq(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.gteq_b(this.v) ? TRUE : FALSE) : super.lteq(luaValue);
    }

    @Override
    public LuaValue lteq(double d) {
        return (double)this.v <= d ? TRUE : FALSE;
    }

    @Override
    public LuaValue lteq(int n) {
        return this.v <= n ? TRUE : FALSE;
    }

    @Override
    public boolean lteq_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.gteq_b(this.v) : super.lteq_b(luaValue);
    }

    @Override
    public boolean lteq_b(int n) {
        return this.v <= n;
    }

    @Override
    public boolean lteq_b(double d) {
        return (double)this.v <= d;
    }

    @Override
    public LuaValue gt(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.lt_b(this.v) ? TRUE : FALSE) : super.gt(luaValue);
    }

    @Override
    public LuaValue gt(double d) {
        return (double)this.v > d ? TRUE : FALSE;
    }

    @Override
    public LuaValue gt(int n) {
        return this.v > n ? TRUE : FALSE;
    }

    @Override
    public boolean gt_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.lt_b(this.v) : super.gt_b(luaValue);
    }

    @Override
    public boolean gt_b(int n) {
        return this.v > n;
    }

    @Override
    public boolean gt_b(double d) {
        return (double)this.v > d;
    }

    @Override
    public LuaValue gteq(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.lteq_b(this.v) ? TRUE : FALSE) : super.gteq(luaValue);
    }

    @Override
    public LuaValue gteq(double d) {
        return (double)this.v >= d ? TRUE : FALSE;
    }

    @Override
    public LuaValue gteq(int n) {
        return this.v >= n ? TRUE : FALSE;
    }

    @Override
    public boolean gteq_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.lteq_b(this.v) : super.gteq_b(luaValue);
    }

    @Override
    public boolean gteq_b(int n) {
        return this.v >= n;
    }

    @Override
    public boolean gteq_b(double d) {
        return (double)this.v >= d;
    }

    @Override
    public int strcmp(LuaString luaString) {
        this.typerror("attempt to compare number with string");
        return 1;
    }

    @Override
    public int checkint() {
        return this.v;
    }

    @Override
    public long checklong() {
        return this.v;
    }

    @Override
    public double checkdouble() {
        return this.v;
    }

    @Override
    public String checkjstring() {
        return String.valueOf(this.v);
    }

    @Override
    public LuaString checkstring() {
        return LuaInteger.valueOf(String.valueOf(this.v));
    }

    static {
        for (int i = 0; i < 512; ++i) {
            LuaInteger.intValues[i] = new LuaInteger(i - 256);
        }
    }
}

