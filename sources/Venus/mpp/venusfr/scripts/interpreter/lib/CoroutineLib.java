/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaThread;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class CoroutineLib
extends TwoArgFunction {
    static int coroutine_count = 0;
    Globals globals;

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        this.globals = luaValue2.checkglobals();
        LuaTable luaTable = new LuaTable();
        luaTable.set("create", (LuaValue)new create(this));
        luaTable.set("resume", (LuaValue)new resume());
        luaTable.set("running", (LuaValue)new running(this));
        luaTable.set("status", (LuaValue)new status());
        luaTable.set("wrap", (LuaValue)new wrap(this));
        luaValue2.set("coroutine", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("coroutine", (LuaValue)luaTable);
        }
        return luaTable;
    }

    final class create
    extends LibFunction {
        final CoroutineLib this$0;

        create(CoroutineLib coroutineLib) {
            this.this$0 = coroutineLib;
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return new LuaThread(this.this$0.globals, luaValue.checkfunction());
        }
    }

    static final class resume
    extends VarArgFunction {
        resume() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaThread luaThread = varargs.checkthread(1);
            return luaThread.resume(varargs.subargs(2));
        }
    }

    final class running
    extends VarArgFunction {
        final CoroutineLib this$0;

        running(CoroutineLib coroutineLib) {
            this.this$0 = coroutineLib;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaThread luaThread = this.this$0.globals.running;
            return running.varargsOf(luaThread, (Varargs)running.valueOf(luaThread.isMainThread()));
        }
    }

    static final class status
    extends LibFunction {
        status() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            LuaThread luaThread = luaValue.checkthread();
            return status.valueOf(luaThread.getStatus());
        }
    }

    final class wrap
    extends LibFunction {
        final CoroutineLib this$0;

        wrap(CoroutineLib coroutineLib) {
            this.this$0 = coroutineLib;
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            LuaFunction luaFunction = luaValue.checkfunction();
            LuaThread luaThread = new LuaThread(this.this$0.globals, luaFunction);
            return new wrapper(luaThread);
        }
    }

    static final class wrapper
    extends VarArgFunction {
        final LuaThread luathread;

        wrapper(LuaThread luaThread) {
            this.luathread = luaThread;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            Varargs varargs2 = this.luathread.resume(varargs);
            if (varargs2.arg1().toboolean()) {
                return varargs2.subargs(2);
            }
            return wrapper.error(varargs2.arg(2).tojstring());
        }
    }
}

