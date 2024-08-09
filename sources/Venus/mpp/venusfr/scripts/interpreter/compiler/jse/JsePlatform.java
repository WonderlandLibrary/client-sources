/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LoadState;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.compiler.LuaC;
import mpp.venusfr.scripts.interpreter.compiler.jse.JseBaseLib;
import mpp.venusfr.scripts.interpreter.compiler.jse.JseIoLib;
import mpp.venusfr.scripts.interpreter.compiler.jse.JseMathLib;
import mpp.venusfr.scripts.interpreter.compiler.jse.JseOsLib;
import mpp.venusfr.scripts.interpreter.compiler.jse.JseStringLib;
import mpp.venusfr.scripts.interpreter.compiler.jse.LuajavaLib;
import mpp.venusfr.scripts.interpreter.lib.Bit32Lib;
import mpp.venusfr.scripts.interpreter.lib.CoroutineLib;
import mpp.venusfr.scripts.interpreter.lib.DebugLib;
import mpp.venusfr.scripts.interpreter.lib.PackageLib;
import mpp.venusfr.scripts.interpreter.lib.TableLib;

public class JsePlatform {
    public static Globals standardGlobals() {
        Globals globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new JseStringLib());
        globals.load(new CoroutineLib());
        globals.load(new JseMathLib());
        globals.load(new JseIoLib());
        globals.load(new JseOsLib());
        globals.load(new LuajavaLib());
        LoadState.install(globals);
        LuaC.install(globals);
        return globals;
    }

    public static Globals debugGlobals() {
        Globals globals = JsePlatform.standardGlobals();
        globals.load(new DebugLib());
        return globals;
    }

    public static Varargs luaMain(LuaValue luaValue, String[] stringArray) {
        Globals globals = JsePlatform.standardGlobals();
        int n = stringArray.length;
        LuaValue[] luaValueArray = new LuaValue[stringArray.length];
        for (int i = 0; i < n; ++i) {
            luaValueArray[i] = LuaValue.valueOf(stringArray[i]);
        }
        LuaTable luaTable = LuaValue.listOf(luaValueArray);
        luaTable.set("n", n);
        globals.set("arg", (LuaValue)luaTable);
        luaValue.initupvalue1(globals);
        return luaValue.invoke(LuaValue.varargsOf(luaValueArray));
    }
}

