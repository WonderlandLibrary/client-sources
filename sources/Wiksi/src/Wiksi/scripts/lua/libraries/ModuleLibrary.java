package src.Wiksi.scripts.lua.libraries;

import src.Wiksi.scripts.interpreter.LuaValue;
import src.Wiksi.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import src.Wiksi.scripts.interpreter.lib.OneArgFunction;
import src.Wiksi.scripts.interpreter.lib.TwoArgFunction;
import src.Wiksi.scripts.lua.classes.ModuleClass;

public class ModuleLibrary extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("register", new register());

        env.set("module", library);
        return library;
    }

    public class register extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue arg) {
            return CoerceJavaToLua.coerce(new ModuleClass(arg.toString()));
        }

    }

}
