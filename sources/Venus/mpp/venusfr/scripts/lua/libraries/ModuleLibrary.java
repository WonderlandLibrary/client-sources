/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.lua.libraries;

import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.lib.OneArgFunction;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.lua.classes.ModuleClass;

public class ModuleLibrary
extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = ModuleLibrary.tableOf();
        luaTable.set("register", (LuaValue)new register(this));
        luaValue2.set("module", (LuaValue)luaTable);
        return luaTable;
    }

    public class register
    extends OneArgFunction {
        final ModuleLibrary this$0;

        public register(ModuleLibrary moduleLibrary) {
            this.this$0 = moduleLibrary;
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return CoerceJavaToLua.coerce(new ModuleClass(luaValue.toString()));
        }
    }
}

