/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaValue;

public class LuaError
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public int line = 0;
    public int level;
    public String fileline;
    public String traceback;
    public Throwable cause;
    public LuaValue object;

    @Override
    public String getMessage() {
        if (this.traceback != null) {
            return this.traceback;
        }
        String string = super.getMessage();
        if (string == null) {
            return null;
        }
        if (this.fileline != null) {
            return this.fileline + " " + string;
        }
        return string;
    }

    public LuaValue getMessageObject() {
        if (this.object != null) {
            return this.object;
        }
        String string = this.getMessage();
        return string != null ? LuaValue.valueOf(string) : null;
    }

    public LuaError(Throwable throwable) {
        super("vm error: " + throwable);
        this.cause = throwable;
        this.level = 1;
    }

    public LuaError(String string, int n, boolean bl) {
        super(string);
        this.line = n;
        this.level = 1;
    }

    public LuaError(String string) {
        super(string);
        this.level = 1;
    }

    public LuaError(String string, int n) {
        super(string);
        this.level = n;
    }

    public LuaError(LuaValue luaValue) {
        super(luaValue.tojstring());
        this.object = luaValue;
        this.level = 1;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}

