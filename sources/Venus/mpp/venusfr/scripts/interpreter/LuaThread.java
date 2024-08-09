/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import java.lang.ref.WeakReference;
import mpp.venusfr.scripts.interpreter.Globals;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.OrphanedThread;
import mpp.venusfr.scripts.interpreter.Varargs;

public class LuaThread
extends LuaValue {
    public static LuaValue s_metatable;
    public static int coroutine_count;
    public static long thread_orphan_check_interval;
    public static final int STATUS_INITIAL = 0;
    public static final int STATUS_SUSPENDED = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_NORMAL = 3;
    public static final int STATUS_DEAD = 4;
    public static final String[] STATUS_NAMES;
    public final State state;
    public static final int MAX_CALLSTACK = 256;
    public Object callstack;
    public final Globals globals;
    public LuaValue errorfunc;

    public LuaThread(Globals globals) {
        this.state = new State(globals, this, null);
        this.state.status = 2;
        this.globals = globals;
    }

    public LuaThread(Globals globals, LuaValue luaValue) {
        LuaValue.assert_(luaValue != null, "function cannot be null");
        this.state = new State(globals, this, luaValue);
        this.globals = globals;
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public String typename() {
        return "thread";
    }

    @Override
    public boolean isthread() {
        return false;
    }

    @Override
    public LuaThread optthread(LuaThread luaThread) {
        return this;
    }

    @Override
    public LuaThread checkthread() {
        return this;
    }

    @Override
    public LuaValue getmetatable() {
        return s_metatable;
    }

    public String getStatus() {
        return STATUS_NAMES[this.state.status];
    }

    public boolean isMainThread() {
        return this.state.function == null;
    }

    public Varargs resume(Varargs varargs) {
        State state = this.state;
        if (state.status > 1) {
            return LuaValue.varargsOf(LuaValue.FALSE, (Varargs)LuaValue.valueOf("cannot resume " + (state.status == 4 ? "dead" : "non-suspended") + " coroutine"));
        }
        return state.lua_resume(this, varargs);
    }

    static {
        coroutine_count = 0;
        thread_orphan_check_interval = 5000L;
        STATUS_NAMES = new String[]{"suspended", "suspended", "running", "normal", "dead"};
    }

    public static class State
    implements Runnable {
        private final Globals globals;
        final WeakReference lua_thread;
        public final LuaValue function;
        Varargs args = LuaValue.NONE;
        Varargs result = LuaValue.NONE;
        String error = null;
        public LuaValue hookfunc;
        public boolean hookline;
        public boolean hookcall;
        public boolean hookrtrn;
        public int hookcount;
        public boolean inhook;
        public int lastline;
        public int bytecodes;
        public int status = 0;

        State(Globals globals, LuaThread luaThread, LuaValue luaValue) {
            this.globals = globals;
            this.lua_thread = new WeakReference<LuaThread>(luaThread);
            this.function = luaValue;
        }

        @Override
        public synchronized void run() {
            try {
                Varargs varargs = this.args;
                this.args = LuaValue.NONE;
                this.result = this.function.invoke(varargs);
            } catch (Throwable throwable) {
                this.error = throwable.getMessage();
            } finally {
                this.status = 4;
                this.notify();
            }
        }

        public synchronized Varargs lua_resume(LuaThread luaThread, Varargs varargs) {
            LuaThread luaThread2 = this.globals.running;
            try {
                this.globals.running = luaThread;
                this.args = varargs;
                if (this.status == 0) {
                    this.status = 2;
                    new Thread((Runnable)this, "Coroutine-" + ++coroutine_count).start();
                } else {
                    this.notify();
                }
                if (luaThread2 != null) {
                    luaThread2.state.status = 3;
                }
                this.status = 2;
                this.wait();
                Varargs varargs2 = this.error != null ? LuaValue.varargsOf(LuaValue.FALSE, (Varargs)LuaValue.valueOf(this.error)) : LuaValue.varargsOf(LuaValue.TRUE, this.result);
                return varargs2;
            } catch (InterruptedException interruptedException) {
                throw new OrphanedThread();
            } finally {
                this.args = LuaValue.NONE;
                this.result = LuaValue.NONE;
                this.error = null;
                this.globals.running = luaThread2;
                if (luaThread2 != null) {
                    this.globals.running.state.status = 2;
                }
            }
        }

        public synchronized Varargs lua_yield(Varargs varargs) {
            try {
                this.result = varargs;
                this.status = 1;
                this.notify();
                do {
                    this.wait(thread_orphan_check_interval);
                    if (this.lua_thread.get() != null) continue;
                    this.status = 4;
                    throw new OrphanedThread();
                } while (this.status == 1);
                Varargs varargs2 = this.args;
                return varargs2;
            } catch (InterruptedException interruptedException) {
                this.status = 4;
                throw new OrphanedThread();
            } finally {
                this.args = LuaValue.NONE;
                this.result = LuaValue.NONE;
            }
        }
    }
}

