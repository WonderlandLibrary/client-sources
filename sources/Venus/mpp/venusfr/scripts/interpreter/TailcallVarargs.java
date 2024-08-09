/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;

public class TailcallVarargs
extends Varargs {
    private LuaValue func;
    private Varargs args;
    private Varargs result;

    public TailcallVarargs(LuaValue luaValue, Varargs varargs) {
        this.func = luaValue;
        this.args = varargs;
    }

    public TailcallVarargs(LuaValue luaValue, LuaValue luaValue2, Varargs varargs) {
        this.func = luaValue.get(luaValue2);
        this.args = LuaValue.varargsOf(luaValue, varargs);
    }

    @Override
    public boolean isTailcall() {
        return false;
    }

    @Override
    public Varargs eval() {
        while (this.result == null) {
            Varargs varargs = this.func.onInvoke(this.args);
            if (varargs.isTailcall()) {
                TailcallVarargs tailcallVarargs = (TailcallVarargs)varargs;
                this.func = tailcallVarargs.func;
                this.args = tailcallVarargs.args;
                continue;
            }
            this.result = varargs;
            this.func = null;
            this.args = null;
        }
        return this.result;
    }

    @Override
    public LuaValue arg(int n) {
        if (this.result == null) {
            this.eval();
        }
        return this.result.arg(n);
    }

    @Override
    public LuaValue arg1() {
        if (this.result == null) {
            this.eval();
        }
        return this.result.arg1();
    }

    @Override
    public int narg() {
        if (this.result == null) {
            this.eval();
        }
        return this.result.narg();
    }

    @Override
    public Varargs subargs(int n) {
        if (this.result == null) {
            this.eval();
        }
        return this.result.subargs(n);
    }
}

