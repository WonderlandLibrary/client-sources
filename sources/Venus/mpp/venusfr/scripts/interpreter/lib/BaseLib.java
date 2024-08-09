/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import java.io.InputStream;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;
import mpp.venusfr.scripts.interpreter.lib.ResourceFinder;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class BaseLib
extends TwoArgFunction
implements ResourceFinder {
    Globals globals;

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        this.globals.finder = this;
        this.globals.baselib = this;
        luaValue2.set("print", (LuaValue)new print(this, this));
        luaValue2.set("tonumber", (LuaValue)new tonumber());
        luaValue2.set("tostring", (LuaValue)new tostring());
        luaValue2.set("error", (LuaValue)new error());
        return luaValue2;
    }

    @Override
    public InputStream findResource(String string) {
        return this.getClass().getResourceAsStream((String)(string.startsWith("/") ? string : "/" + string));
    }

    public Varargs loadStream(InputStream inputStream, String string, String string2, LuaValue luaValue) {
        try {
            if (inputStream == null) {
                return BaseLib.varargsOf(NIL, (Varargs)BaseLib.valueOf("not found: " + string));
            }
            return this.globals.load(inputStream, string, string2, luaValue);
        } catch (Exception exception) {
            return BaseLib.varargsOf(NIL, (Varargs)BaseLib.valueOf(exception.getMessage()));
        }
    }

    final class print
    extends VarArgFunction {
        final BaseLib baselib;
        final BaseLib this$0;

        print(BaseLib baseLib, BaseLib baseLib2) {
            this.this$0 = baseLib;
            this.baselib = baseLib2;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaValue luaValue = this.this$0.globals.get("tostring");
            int n = varargs.narg();
            for (int i = 1; i <= n; ++i) {
                LuaString luaString = luaValue.call(varargs.arg(i)).strvalue();
                this.this$0.globals.STDOUT.print(luaString.tojstring());
            }
            return NONE;
        }
    }

    static final class tonumber
    extends LibFunction {
        tonumber() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return luaValue.tonumber();
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            if (luaValue2.isnil()) {
                return luaValue.tonumber();
            }
            int n = luaValue2.checkint();
            if (n < 2 || n > 36) {
                tonumber.argerror(2, "base out of range");
            }
            return luaValue.checkstring().tonumber(n);
        }
    }

    static final class tostring
    extends LibFunction {
        tostring() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            LuaValue luaValue2 = luaValue.metatag(TOSTRING);
            if (!luaValue2.isnil()) {
                return luaValue2.call(luaValue);
            }
            LuaValue luaValue3 = luaValue.tostring();
            if (!luaValue3.isnil()) {
                return luaValue3;
            }
            return tostring.valueOf(luaValue.tojstring());
        }
    }

    static final class error
    extends TwoArgFunction {
        error() {
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            if (luaValue.isnil()) {
                throw new LuaError(NIL);
            }
            if (!luaValue.isstring() || luaValue2.optint(1) == 0) {
                throw new LuaError(luaValue);
            }
            throw new LuaError(luaValue.tojstring(), luaValue2.optint(1));
        }
    }

    static final class collectgarbage
    extends VarArgFunction {
        collectgarbage() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            String string = varargs.optjstring(1, "collect");
            if ("collect".equals(string)) {
                System.gc();
                return ZERO;
            }
            if ("count".equals(string)) {
                Runtime runtime = Runtime.getRuntime();
                long l = runtime.totalMemory() - runtime.freeMemory();
                return collectgarbage.varargsOf(collectgarbage.valueOf((double)l / 1024.0), (Varargs)collectgarbage.valueOf(l % 1024L));
            }
            if ("step".equals(string)) {
                System.gc();
                return LuaValue.TRUE;
            }
            collectgarbage.argerror(1, "invalid option '" + string + "'");
            return NIL;
        }
    }

    static final class _assert
    extends VarArgFunction {
        _assert() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            if (!varargs.arg1().toboolean()) {
                _assert.error(varargs.narg() > 1 ? varargs.optjstring(2, "assertion failed!") : "assertion failed!");
            }
            return varargs;
        }
    }
}

