/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.lib.MathLib;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;

public class JseMathLib
extends MathLib {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        super.call(luaValue, luaValue2);
        LuaValue luaValue3 = luaValue2.get("math");
        luaValue3.set("acos", (LuaValue)new acos());
        luaValue3.set("asin", (LuaValue)new asin());
        atan2 atan22 = new atan2();
        luaValue3.set("atan", (LuaValue)atan22);
        luaValue3.set("atan2", (LuaValue)atan22);
        luaValue3.set("cosh", (LuaValue)new cosh());
        luaValue3.set("exp", (LuaValue)new exp());
        luaValue3.set("log", (LuaValue)new log());
        luaValue3.set("pow", (LuaValue)new pow());
        luaValue3.set("sinh", (LuaValue)new sinh());
        luaValue3.set("tanh", (LuaValue)new tanh());
        return luaValue3;
    }

    @Override
    public double dpow_lib(double d, double d2) {
        return Math.pow(d, d2);
    }

    static final class acos
    extends MathLib.UnaryOp {
        acos() {
        }

        @Override
        protected double call(double d) {
            return Math.acos(d);
        }
    }

    static final class asin
    extends MathLib.UnaryOp {
        asin() {
        }

        @Override
        protected double call(double d) {
            return Math.asin(d);
        }
    }

    static final class atan2
    extends TwoArgFunction {
        atan2() {
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return atan2.valueOf(Math.atan2(luaValue.checkdouble(), luaValue2.optdouble(1.0)));
        }
    }

    static final class cosh
    extends MathLib.UnaryOp {
        cosh() {
        }

        @Override
        protected double call(double d) {
            return Math.cosh(d);
        }
    }

    static final class exp
    extends MathLib.UnaryOp {
        exp() {
        }

        @Override
        protected double call(double d) {
            return Math.exp(d);
        }
    }

    static final class log
    extends TwoArgFunction {
        log() {
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            double d = Math.log(luaValue.checkdouble());
            double d2 = luaValue2.optdouble(Math.E);
            if (d2 != Math.E) {
                d /= Math.log(d2);
            }
            return log.valueOf(d);
        }
    }

    static final class pow
    extends MathLib.BinaryOp {
        pow() {
        }

        @Override
        protected double call(double d, double d2) {
            return Math.pow(d, d2);
        }
    }

    static final class sinh
    extends MathLib.UnaryOp {
        sinh() {
        }

        @Override
        protected double call(double d) {
            return Math.sinh(d);
        }
    }

    static final class tanh
    extends MathLib.UnaryOp {
        tanh() {
        }

        @Override
        protected double call(double d) {
            return Math.tanh(d);
        }
    }
}

