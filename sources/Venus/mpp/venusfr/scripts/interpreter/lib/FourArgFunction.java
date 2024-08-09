/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;

public abstract class FourArgFunction
extends LibFunction {
    @Override
    public abstract LuaValue call(LuaValue var1, LuaValue var2, LuaValue var3, LuaValue var4);

    @Override
    public final LuaValue call() {
        return this.call(NIL, NIL, NIL, NIL);
    }

    @Override
    public final LuaValue call(LuaValue luaValue) {
        return this.call(luaValue, NIL, NIL, NIL);
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return this.call(luaValue, luaValue2, NIL, NIL);
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return this.call(luaValue, luaValue2, luaValue3, NIL);
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        return this.call(varargs.arg1(), varargs.arg(2), varargs.arg(3), varargs.arg(4));
    }
}

