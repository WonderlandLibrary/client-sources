package src.Wiksi.scripts.client;

import com.google.common.eventbus.Subscribe;

import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Function;
import src.Wiksi.scripts.interpreter.Globals;
import src.Wiksi.scripts.interpreter.LuaValue;
import src.Wiksi.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import src.Wiksi.scripts.interpreter.globals.Standarts;
import src.Wiksi.scripts.lua.classes.ModuleClass;
import src.Wiksi.scripts.lua.classes.events.UpdateClass;
import lombok.Getter;

public class MCScript {

    private final String fileName;
    private String code;

    public MCScript(String fileName) {
        this.fileName = fileName;
    }

    public MCScript(String code, boolean empty) {
        this.fileName = "";
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    Globals globals;
    LuaValue chunk;
    ModuleClass moduleClass;

    @Getter
    private Function function;

    public void compile() {
        globals = Standarts.standardGlobals();
        if (code == null)
            chunk = globals.loadfile(fileName);
        else {
            chunk = globals.load(code);
        }
        chunk.call();

        if (globals.get("module").checkuserdata() instanceof ModuleClass mod) {
            moduleClass = mod;

             this.function = new Function(moduleClass.getModuleName()) {
                 @Override
                 public void onEnable() {
                     LuaValue val = globals.get("onEnable");
                     if (val != LuaValue.NIL) {
                         val.call();
                     }
                 }

                 @Subscribe
                 public void onUpdate(EventUpdate e) {
                     LuaValue val = globals.get("onEvent");
                     if (val != LuaValue.NIL) {
                         val.call(CoerceJavaToLua.coerce(new UpdateClass()));
                     }
                 }

             };
        }
    }

    public void call(String method) {
        globals.get(method).call();
    }

}
