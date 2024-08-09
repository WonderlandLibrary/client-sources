/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;

interface Metatable {
    public boolean useWeakKeys();

    public boolean useWeakValues();

    public LuaValue toLuaValue();

    public LuaTable.Slot entry(LuaValue var1, LuaValue var2);

    public LuaValue wrap(LuaValue var1);

    public LuaValue arrayget(LuaValue[] var1, int var2);
}

