/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.lua.libraries;

import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.ZeroArgFunction;
import net.minecraft.client.Minecraft;

public class PlayerLibrary
extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = PlayerLibrary.tableOf();
        luaTable.set("entity", (LuaValue)new entity(this));
        luaValue2.set("player", (LuaValue)luaTable);
        return luaTable;
    }

    public class entity
    extends ZeroArgFunction {
        final PlayerLibrary this$0;

        public entity(PlayerLibrary playerLibrary) {
            this.this$0 = playerLibrary;
        }

        @Override
        public LuaValue call() {
            return CoerceJavaToLua.coerce(Minecraft.getInstance().player.getLuaClass());
        }
    }
}

