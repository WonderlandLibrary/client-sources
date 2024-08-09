/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.lib.MathLib;

public class LuaDouble
extends LuaNumber {
    public static final LuaDouble NAN = new LuaDouble(Double.NaN);
    public static final LuaDouble POSINF = new LuaDouble(Double.POSITIVE_INFINITY);
    public static final LuaDouble NEGINF = new LuaDouble(Double.NEGATIVE_INFINITY);
    public static final String JSTR_NAN = "nan";
    public static final String JSTR_POSINF = "inf";
    public static final String JSTR_NEGINF = "-inf";
    final double v;

    public static LuaNumber valueOf(double d) {
        int n = (int)d;
        return d == (double)n ? LuaInteger.valueOf(n) : new LuaDouble(d);
    }

    private LuaDouble(double d) {
        this.v = d;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.v + 1.0);
        return (int)(l >> 32) + (int)l;
    }

    @Override
    public boolean islong() {
        return this.v == (double)((long)this.v);
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
        return (float)this.v;
    }

    @Override
    public int toint() {
        return (int)this.v;
    }

    @Override
    public long tolong() {
        return (long)this.v;
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
        return (int)this.v;
    }

    @Override
    public LuaInteger optinteger(LuaInteger luaInteger) {
        return LuaInteger.valueOf((int)this.v);
    }

    @Override
    public long optlong(long l) {
        return (long)this.v;
    }

    @Override
    public LuaInteger checkinteger() {
        return LuaInteger.valueOf((int)this.v);
    }

    @Override
    public LuaValue neg() {
        return LuaDouble.valueOf(-this.v);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof LuaDouble && ((LuaDouble)object).v == this.v;
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
        return this.v == d;
    }

    @Override
    public boolean raweq(int n) {
        return this.v == (double)n;
    }

    @Override
    public LuaValue add(LuaValue luaValue) {
        return luaValue.add(this.v);
    }

    @Override
    public LuaValue add(double d) {
        return LuaDouble.valueOf(d + this.v);
    }

    @Override
    public LuaValue sub(LuaValue luaValue) {
        return luaValue.subFrom(this.v);
    }

    @Override
    public LuaValue sub(double d) {
        return LuaDouble.valueOf(this.v - d);
    }

    @Override
    public LuaValue sub(int n) {
        return LuaDouble.valueOf(this.v - (double)n);
    }

    @Override
    public LuaValue subFrom(double d) {
        return LuaDouble.valueOf(d - this.v);
    }

    @Override
    public LuaValue mul(LuaValue luaValue) {
        return luaValue.mul(this.v);
    }

    @Override
    public LuaValue mul(double d) {
        return LuaDouble.valueOf(d * this.v);
    }

    @Override
    public LuaValue mul(int n) {
        return LuaDouble.valueOf((double)n * this.v);
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

    public static LuaValue ddiv(double d, double d2) {
        return d2 != 0.0 ? LuaDouble.valueOf(d / d2) : (d > 0.0 ? POSINF : (d == 0.0 ? NAN : NEGINF));
    }

    public static double ddiv_d(double d, double d2) {
        return d2 != 0.0 ? d / d2 : (d > 0.0 ? Double.POSITIVE_INFINITY : (d == 0.0 ? Double.NaN : Double.NEGATIVE_INFINITY));
    }

    public static LuaValue dmod(double d, double d2) {
        if (d2 == 0.0 || d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY) {
            return NAN;
        }
        if (d2 == Double.POSITIVE_INFINITY) {
            return d < 0.0 ? POSINF : LuaDouble.valueOf(d);
        }
        if (d2 == Double.NEGATIVE_INFINITY) {
            return d > 0.0 ? NEGINF : LuaDouble.valueOf(d);
        }
        return LuaDouble.valueOf(d - d2 * Math.floor(d / d2));
    }

    public static double dmod_d(double d, double d2) {
        if (d2 == 0.0 || d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY) {
            return Double.NaN;
        }
        if (d2 == Double.POSITIVE_INFINITY) {
            return d < 0.0 ? Double.POSITIVE_INFINITY : d;
        }
        if (d2 == Double.NEGATIVE_INFINITY) {
            return d > 0.0 ? Double.NEGATIVE_INFINITY : d;
        }
        return d - d2 * Math.floor(d / d2);
    }

    @Override
    public LuaValue lt(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.gt_b(this.v) ? TRUE : FALSE) : super.lt(luaValue);
    }

    @Override
    public LuaValue lt(double d) {
        return this.v < d ? TRUE : FALSE;
    }

    @Override
    public LuaValue lt(int n) {
        return this.v < (double)n ? TRUE : FALSE;
    }

    @Override
    public boolean lt_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.gt_b(this.v) : super.lt_b(luaValue);
    }

    @Override
    public boolean lt_b(int n) {
        return this.v < (double)n;
    }

    @Override
    public boolean lt_b(double d) {
        return this.v < d;
    }

    @Override
    public LuaValue lteq(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.gteq_b(this.v) ? TRUE : FALSE) : super.lteq(luaValue);
    }

    @Override
    public LuaValue lteq(double d) {
        return this.v <= d ? TRUE : FALSE;
    }

    @Override
    public LuaValue lteq(int n) {
        return this.v <= (double)n ? TRUE : FALSE;
    }

    @Override
    public boolean lteq_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.gteq_b(this.v) : super.lteq_b(luaValue);
    }

    @Override
    public boolean lteq_b(int n) {
        return this.v <= (double)n;
    }

    @Override
    public boolean lteq_b(double d) {
        return this.v <= d;
    }

    @Override
    public LuaValue gt(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.lt_b(this.v) ? TRUE : FALSE) : super.gt(luaValue);
    }

    @Override
    public LuaValue gt(double d) {
        return this.v > d ? TRUE : FALSE;
    }

    @Override
    public LuaValue gt(int n) {
        return this.v > (double)n ? TRUE : FALSE;
    }

    @Override
    public boolean gt_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.lt_b(this.v) : super.gt_b(luaValue);
    }

    @Override
    public boolean gt_b(int n) {
        return this.v > (double)n;
    }

    @Override
    public boolean gt_b(double d) {
        return this.v > d;
    }

    @Override
    public LuaValue gteq(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? (luaValue.lteq_b(this.v) ? TRUE : FALSE) : super.gteq(luaValue);
    }

    @Override
    public LuaValue gteq(double d) {
        return this.v >= d ? TRUE : FALSE;
    }

    @Override
    public LuaValue gteq(int n) {
        return this.v >= (double)n ? TRUE : FALSE;
    }

    @Override
    public boolean gteq_b(LuaValue luaValue) {
        return luaValue instanceof LuaNumber ? luaValue.lteq_b(this.v) : super.gteq_b(luaValue);
    }

    @Override
    public boolean gteq_b(int n) {
        return this.v >= (double)n;
    }

    @Override
    public boolean gteq_b(double d) {
        return this.v >= d;
    }

    @Override
    public int strcmp(LuaString luaString) {
        this.typerror("attempt to compare number with string");
        return 1;
    }

    @Override
    public String tojstring() {
        long l = (long)this.v;
        if ((double)l == this.v) {
            return Long.toString(l);
        }
        if (Double.isNaN(this.v)) {
            return JSTR_NAN;
        }
        if (Double.isInfinite(this.v)) {
            return this.v < 0.0 ? JSTR_NEGINF : JSTR_POSINF;
        }
        return Float.toString((float)this.v);
    }

    @Override
    public LuaString strvalue() {
        return LuaString.valueOf(this.tojstring());
    }

    @Override
    public LuaString optstring(LuaString luaString) {
        return LuaString.valueOf(this.tojstring());
    }

    @Override
    public LuaValue tostring() {
        return LuaString.valueOf(this.tojstring());
    }

    @Override
    public String optjstring(String string) {
        return this.tojstring();
    }

    @Override
    public LuaNumber optnumber(LuaNumber luaNumber) {
        return this;
    }

    @Override
    public boolean isnumber() {
        return false;
    }

    @Override
    public boolean isstring() {
        return false;
    }

    @Override
    public LuaValue tonumber() {
        return this;
    }

    @Override
    public int checkint() {
        return (int)this.v;
    }

    @Override
    public long checklong() {
        return (long)this.v;
    }

    @Override
    public LuaNumber checknumber() {
        return this;
    }

    @Override
    public double checkdouble() {
        return this.v;
    }

    @Override
    public String checkjstring() {
        return this.tojstring();
    }

    @Override
    public LuaString checkstring() {
        return LuaString.valueOf(this.tojstring());
    }

    @Override
    public boolean isvalidkey() {
        return !Double.isNaN(this.v);
    }
}

