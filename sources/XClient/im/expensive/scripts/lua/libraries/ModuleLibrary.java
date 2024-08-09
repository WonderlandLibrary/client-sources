package im.expensive.scripts.lua.libraries;

import im.expensive.scripts.interpreter.LuaValue;
import im.expensive.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import im.expensive.scripts.interpreter.lib.OneArgFunction;
import im.expensive.scripts.interpreter.lib.TwoArgFunction;
import im.expensive.scripts.lua.classes.ModuleClass;

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
