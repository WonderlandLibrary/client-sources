/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.client;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.globals.Standarts;
import mpp.venusfr.scripts.lua.classes.ModuleClass;
import mpp.venusfr.scripts.lua.classes.events.UpdateClass;

public class MCScript {
    private final String fileName;
    private String code;
    Globals globals;
    LuaValue chunk;
    ModuleClass moduleClass;
    private Function function;

    public MCScript(String string) {
        this.fileName = string;
    }

    public MCScript(String string, boolean bl) {
        this.fileName = "";
        this.code = string;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void compile() {
        this.globals = Standarts.standardGlobals();
        this.chunk = this.code == null ? this.globals.loadfile(this.fileName) : this.globals.load(this.code);
        this.chunk.call();
        Object object = this.globals.get("module").checkuserdata();
        if (object instanceof ModuleClass) {
            ModuleClass moduleClass;
            this.moduleClass = moduleClass = (ModuleClass)object;
            this.function = new Function(this, this.moduleClass.getModuleName()){
                final MCScript this$0;
                {
                    this.this$0 = mCScript;
                    super(string);
                }

                @Override
                public void onEnable() {
                    LuaValue luaValue = this.this$0.globals.get("onEnable");
                    if (luaValue != LuaValue.NIL) {
                        luaValue.call();
                    }
                }

                @Subscribe
                public void onUpdate(EventUpdate eventUpdate) {
                    LuaValue luaValue = this.this$0.globals.get("onEvent");
                    if (luaValue != LuaValue.NIL) {
                        luaValue.call(CoerceJavaToLua.coerce(new UpdateClass()));
                    }
                }
            };
        }
    }

    public void call(String string) {
        this.globals.get(string).call();
    }

    public Function getFunction() {
        return this.function;
    }
}

