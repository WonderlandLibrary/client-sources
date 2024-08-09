/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.globals;

import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LoadState;
import mpp.venusfr.scripts.interpreter.compiler.LuaC;
import mpp.venusfr.scripts.interpreter.lib.BaseLib;
import mpp.venusfr.scripts.interpreter.lib.Bit32Lib;
import mpp.venusfr.scripts.interpreter.lib.MathLib;
import mpp.venusfr.scripts.interpreter.lib.StringLib;
import mpp.venusfr.scripts.interpreter.lib.TableLib;
import mpp.venusfr.scripts.lua.libraries.ModuleLibrary;
import mpp.venusfr.scripts.lua.libraries.PlayerLibrary;

public class Standarts {
    public static Globals standardGlobals() {
        Globals globals = new Globals();
        globals.load(new BaseLib());
        globals.load(new Bit32Lib());
        globals.load(new MathLib());
        globals.load(new TableLib());
        globals.load(new StringLib());
        globals.load(new PlayerLibrary());
        globals.load(new ModuleLibrary());
        LoadState.install(globals);
        LuaC.install(globals);
        return globals;
    }
}

