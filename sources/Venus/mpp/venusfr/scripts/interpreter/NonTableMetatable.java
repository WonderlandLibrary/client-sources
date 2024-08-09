/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Metatable;

class NonTableMetatable
implements Metatable {
    private final LuaValue value;

    public NonTableMetatable(LuaValue luaValue) {
        this.value = luaValue;
    }

    @Override
    public boolean useWeakKeys() {
        return true;
    }

    @Override
    public boolean useWeakValues() {
        return true;
    }

    @Override
    public LuaValue toLuaValue() {
        return this.value;
    }

    @Override
    public LuaTable.Slot entry(LuaValue luaValue, LuaValue luaValue2) {
        return LuaTable.defaultEntry(luaValue, luaValue2);
    }

    @Override
    public LuaValue wrap(LuaValue luaValue) {
        return luaValue;
    }

    @Override
    public LuaValue arrayget(LuaValue[] luaValueArray, int n) {
        return luaValueArray[n];
    }
}

