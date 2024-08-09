/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;

public abstract class OneArgFunction
extends LibFunction {
    @Override
    public abstract LuaValue call(LuaValue var1);

    @Override
    public final LuaValue call() {
        return this.call(NIL);
    }

    @Override
    public final LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return this.call(luaValue);
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return this.call(luaValue);
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        return this.call(varargs.arg1());
    }
}

