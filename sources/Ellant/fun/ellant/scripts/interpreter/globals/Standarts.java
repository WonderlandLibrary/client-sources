package fun.ellant.scripts.interpreter.globals;

import fun.ellant.scripts.interpreter.compiler.LuaC;
import fun.ellant.scripts.interpreter.Globals;
import fun.ellant.scripts.interpreter.LoadState;
import fun.ellant.scripts.interpreter.lib.*;
import fun.ellant.scripts.lua.libraries.ModuleLibrary;
import fun.ellant.scripts.lua.libraries.PlayerLibrary;

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
