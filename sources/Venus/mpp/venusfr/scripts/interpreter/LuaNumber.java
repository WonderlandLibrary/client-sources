/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.Buffer;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;

public abstract class LuaNumber
extends LuaValue {
    public static LuaValue s_metatable;

    @Override
    public int type() {
        return 0;
    }

    @Override
    public String typename() {
        return "number";
    }

    @Override
    public LuaNumber checknumber() {
        return this;
    }

    @Override
    public LuaNumber checknumber(String string) {
        return this;
    }

    @Override
    public LuaNumber optnumber(LuaNumber luaNumber) {
        return this;
    }

    @Override
    public LuaValue tonumber() {
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
    public LuaValue getmetatable() {
        return s_metatable;
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
        return this.strvalue().concatTo(luaNumber.strvalue());
    }

    @Override
    public LuaValue concatTo(LuaString luaString) {
        return this.strvalue().concatTo(luaString);
    }
}

