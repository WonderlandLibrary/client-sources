package fun.ellant.scripts.lua.libraries;

import fun.ellant.scripts.interpreter.LuaValue;
import fun.ellant.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import fun.ellant.scripts.interpreter.lib.OneArgFunction;
import fun.ellant.scripts.interpreter.lib.TwoArgFunction;
import fun.ellant.scripts.lua.classes.ModuleClass;

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
