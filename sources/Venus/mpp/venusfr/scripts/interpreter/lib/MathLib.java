/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import java.util.Random;
import mpp.venusfr.scripts.interpreter.LuaDouble;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;
import mpp.venusfr.scripts.interpreter.lib.OneArgFunction;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class MathLib
extends TwoArgFunction {
    public static MathLib MATHLIB = null;

    public MathLib() {
        MATHLIB = this;
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = new LuaTable(0, 30);
        luaTable.set("abs", (LuaValue)new abs());
        luaTable.set("ceil", (LuaValue)new ceil());
        luaTable.set("cos", (LuaValue)new cos());
        luaTable.set("deg", (LuaValue)new deg());
        luaTable.set("exp", (LuaValue)new exp(this));
        luaTable.set("floor", (LuaValue)new floor());
        luaTable.set("fmod", (LuaValue)new fmod());
        luaTable.set("frexp", (LuaValue)new frexp());
        luaTable.set("huge", (LuaValue)LuaDouble.POSINF);
        luaTable.set("ldexp", (LuaValue)new ldexp());
        luaTable.set("max", (LuaValue)new max());
        luaTable.set("min", (LuaValue)new min());
        luaTable.set("modf", (LuaValue)new modf());
        luaTable.set("pi", Math.PI);
        luaTable.set("pow", (LuaValue)new pow());
        luaTable.set("hypot", (LuaValue)new hypot());
        luaTable.set("atan2", (LuaValue)new atan2());
        random random2 = new random();
        luaTable.set("random", (LuaValue)random2);
        luaTable.set("randomseed", (LuaValue)new randomseed(random2));
        luaTable.set("rad", (LuaValue)new rad());
        luaTable.set("sin", (LuaValue)new sin());
        luaTable.set("sqrt", (LuaValue)new sqrt());
        luaTable.set("tan", (LuaValue)new tan());
        luaValue2.set("math", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("math", (LuaValue)luaTable);
        }
        return luaTable;
    }

    public static LuaValue dpow(double d, double d2) {
        return LuaDouble.valueOf(MATHLIB != null ? MATHLIB.dpow_lib(d, d2) : MathLib.dpow_default(d, d2));
    }

    public static double dpow_d(double d, double d2) {
        return MATHLIB != null ? MATHLIB.dpow_lib(d, d2) : MathLib.dpow_default(d, d2);
    }

    public double dpow_lib(double d, double d2) {
        return MathLib.dpow_default(d, d2);
    }

    protected static double dpow_default(double d, double d2) {
        double d3;
        if (d2 < 0.0) {
            return 1.0 / MathLib.dpow_default(d, -d2);
        }
        double d4 = 1.0;
        int n = (int)d2;
        double d5 = d;
        while (n > 0) {
            if ((n & 1) != 0) {
                d4 *= d5;
            }
            n >>= 1;
            d5 *= d5;
        }
        d2 -= (double)n;
        if (d3 > 0.0) {
            int n2 = (int)(65536.0 * d2);
            while ((n2 & 0xFFFF) != 0) {
                d = Math.sqrt(d);
                if ((n2 & 0x8000) != 0) {
                    d4 *= d;
                }
                n2 <<= 1;
            }
        }
        return d4;
    }

    static final class abs
    extends UnaryOp {
        abs() {
        }

        @Override
        protected double call(double d) {
            return Math.abs(d);
        }
    }

    static final class ceil
    extends UnaryOp {
        ceil() {
        }

        @Override
        protected double call(double d) {
            return Math.ceil(d);
        }
    }

    static final class cos
    extends UnaryOp {
        cos() {
        }

        @Override
        protected double call(double d) {
            return Math.cos(d);
        }
    }

    static final class deg
    extends UnaryOp {
        deg() {
        }

        @Override
        protected double call(double d) {
            return Math.toDegrees(d);
        }
    }

    static final class exp
    extends UnaryOp {
        final MathLib mathlib;

        exp(MathLib mathLib) {
            this.mathlib = mathLib;
        }

        @Override
        protected double call(double d) {
            return this.mathlib.dpow_lib(Math.E, d);
        }
    }

    static final class floor
    extends UnaryOp {
        floor() {
        }

        @Override
        protected double call(double d) {
            return Math.floor(d);
        }
    }

    static final class fmod
    extends TwoArgFunction {
        fmod() {
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            if (luaValue.islong() && luaValue2.islong()) {
                return fmod.valueOf(luaValue.tolong() % luaValue2.tolong());
            }
            return fmod.valueOf(luaValue.checkdouble() % luaValue2.checkdouble());
        }
    }

    static class frexp
    extends VarArgFunction {
        frexp() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            double d = varargs.checkdouble(1);
            if (d == 0.0) {
                return frexp.varargsOf(ZERO, (Varargs)ZERO);
            }
            long l = Double.doubleToLongBits(d);
            double d2 = (double)((l & 0xFFFFFFFFFFFFFL) + 0x10000000000000L) * (l >= 0L ? (double)1.110223E-16f : (double)-1.110223E-16f);
            double d3 = ((int)(l >> 52) & 0x7FF) - 1022;
            return frexp.varargsOf(frexp.valueOf(d2), (Varargs)frexp.valueOf(d3));
        }
    }

    static final class ldexp
    extends BinaryOp {
        ldexp() {
        }

        @Override
        protected double call(double d, double d2) {
            return d * Double.longBitsToDouble((long)d2 + 1023L << 52);
        }
    }

    static class max
    extends VarArgFunction {
        max() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaValue luaValue = varargs.checkvalue(1);
            int n = varargs.narg();
            for (int i = 2; i <= n; ++i) {
                LuaValue luaValue2 = varargs.checkvalue(i);
                if (!luaValue.lt_b(luaValue2)) continue;
                luaValue = luaValue2;
            }
            return luaValue;
        }
    }

    static class min
    extends VarArgFunction {
        min() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaValue luaValue = varargs.checkvalue(1);
            int n = varargs.narg();
            for (int i = 2; i <= n; ++i) {
                LuaValue luaValue2 = varargs.checkvalue(i);
                if (!luaValue2.lt_b(luaValue)) continue;
                luaValue = luaValue2;
            }
            return luaValue;
        }
    }

    static class modf
    extends VarArgFunction {
        modf() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaValue luaValue = varargs.arg1();
            if (luaValue.islong()) {
                return modf.varargsOf(luaValue, (Varargs)modf.valueOf(0.0));
            }
            double d = luaValue.checkdouble();
            double d2 = d > 0.0 ? Math.floor(d) : Math.ceil(d);
            double d3 = d == d2 ? 0.0 : d - d2;
            return modf.varargsOf(modf.valueOf(d2), (Varargs)modf.valueOf(d3));
        }
    }

    static final class pow
    extends BinaryOp {
        pow() {
        }

        @Override
        protected double call(double d, double d2) {
            return MathLib.dpow_default(d, d2);
        }
    }

    static final class hypot
    extends BinaryOp {
        hypot() {
        }

        @Override
        protected double call(double d, double d2) {
            return Math.hypot(d, d2);
        }
    }

    static final class atan2
    extends BinaryOp {
        atan2() {
        }

        @Override
        protected double call(double d, double d2) {
            return Math.atan2(d, d2);
        }
    }

    static class random
    extends LibFunction {
        Random random = new Random();

        random() {
        }

        @Override
        public LuaValue call() {
            return mpp.venusfr.scripts.interpreter.lib.MathLib$random.valueOf(this.random.nextDouble());
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            int n = luaValue.checkint();
            if (n < 1) {
                mpp.venusfr.scripts.interpreter.lib.MathLib$random.argerror(1, "interval is empty");
            }
            return mpp.venusfr.scripts.interpreter.lib.MathLib$random.valueOf(1 + this.random.nextInt(n));
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            int n = luaValue.checkint();
            int n2 = luaValue2.checkint();
            if (n2 < n) {
                mpp.venusfr.scripts.interpreter.lib.MathLib$random.argerror(2, "interval is empty");
            }
            return mpp.venusfr.scripts.interpreter.lib.MathLib$random.valueOf(n + this.random.nextInt(n2 + 1 - n));
        }
    }

    static class randomseed
    extends OneArgFunction {
        final random random;

        randomseed(random random2) {
            this.random = random2;
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            long l = luaValue.checklong();
            this.random.random = new Random(l);
            return NONE;
        }
    }

    static final class rad
    extends UnaryOp {
        rad() {
        }

        @Override
        protected double call(double d) {
            return Math.toRadians(d);
        }
    }

    static final class sin
    extends UnaryOp {
        sin() {
        }

        @Override
        protected double call(double d) {
            return Math.sin(d);
        }
    }

    static final class sqrt
    extends UnaryOp {
        sqrt() {
        }

        @Override
        protected double call(double d) {
            return Math.sqrt(d);
        }
    }

    static final class tan
    extends UnaryOp {
        tan() {
        }

        @Override
        protected double call(double d) {
            return Math.tan(d);
        }
    }

    protected static abstract class BinaryOp
    extends TwoArgFunction {
        protected BinaryOp() {
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return BinaryOp.valueOf(this.call(luaValue.checkdouble(), luaValue2.checkdouble()));
        }

        protected abstract double call(double var1, double var3);
    }

    protected static abstract class UnaryOp
    extends OneArgFunction {
        protected UnaryOp() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return UnaryOp.valueOf(this.call(luaValue.checkdouble()));
        }

        protected abstract double call(double var1);
    }
}

