/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;

public abstract class VarArgFunction
extends LibFunction {
    @Override
    public LuaValue call() {
        return this.invoke(NONE).arg1();
    }

    @Override
    public LuaValue call(LuaValue luaValue) {
        return this.invoke(luaValue).arg1();
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return this.invoke(VarArgFunction.varargsOf(luaValue, (Varargs)luaValue2)).arg1();
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return this.invoke(VarArgFunction.varargsOf(luaValue, luaValue2, luaValue3)).arg1();
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        return this.onInvoke(varargs).eval();
    }

    @Override
    public Varargs onInvoke(Varargs varargs) {
        return this.invoke(varargs);
    }
}

