package src.Wiksi.scripts.interpreter.globals;

import src.Wiksi.scripts.interpreter.compiler.LuaC;
import src.Wiksi.scripts.interpreter.Globals;
import src.Wiksi.scripts.interpreter.LoadState;
import src.Wiksi.scripts.interpreter.lib.*;
import src.Wiksi.scripts.lua.libraries.ModuleLibrary;
import src.Wiksi.scripts.lua.libraries.PlayerLibrary;

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
