/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaClosure;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Prototype;
import mpp.venusfr.scripts.interpreter.compiler.Constants;
import mpp.venusfr.scripts.interpreter.compiler.FuncState;
import mpp.venusfr.scripts.interpreter.compiler.LexState;

public class LuaC
extends Constants
implements Globals.Compiler,
Globals.Loader {
    public static final LuaC instance = new LuaC();

    public static void install(Globals globals) {
        globals.compiler = instance;
        globals.loader = instance;
    }

    protected LuaC() {
    }

    @Override
    public Prototype compile(InputStream inputStream, String string) throws IOException {
        return new CompileState().luaY_parser(inputStream, string);
    }

    @Override
    public LuaFunction load(Prototype prototype, String string, LuaValue luaValue) throws IOException {
        return new LuaClosure(prototype, luaValue);
    }

    public LuaValue load(InputStream inputStream, String string, Globals globals) throws IOException {
        return new LuaClosure(this.compile(inputStream, string), globals);
    }

    static class CompileState {
        int nCcalls = 0;
        private final Hashtable strings = new Hashtable();

        protected CompileState() {
        }

        Prototype luaY_parser(InputStream inputStream, String string) throws IOException {
            FuncState funcState;
            LexState lexState = new LexState(this, inputStream);
            lexState.fs = funcState = new FuncState();
            lexState.setinput(this, inputStream.read(), inputStream, LuaValue.valueOf(string));
            funcState.f = new Prototype();
            funcState.f.source = LuaValue.valueOf(string);
            lexState.mainfunc(funcState);
            LuaC._assert(funcState.prev == null);
            LuaC._assert(lexState.dyd == null || lexState.dyd.n_actvar == 0 && lexState.dyd.n_gt == 0 && lexState.dyd.n_label == 0);
            return funcState.f;
        }

        public LuaString newTString(String string) {
            return this.cachedLuaString(LuaString.valueOf(string));
        }

        public LuaString newTString(LuaString luaString) {
            return this.cachedLuaString(luaString);
        }

        public LuaString cachedLuaString(LuaString luaString) {
            LuaString luaString2 = (LuaString)this.strings.get(luaString);
            if (luaString2 != null) {
                return luaString2;
            }
            this.strings.put(luaString, luaString);
            return luaString;
        }

        public String pushfstring(String string) {
            return string;
        }
    }
}

