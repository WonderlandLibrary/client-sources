/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaValue;

public final class LuaBoolean
extends LuaValue {
    static final LuaBoolean _TRUE = new LuaBoolean(true);
    static final LuaBoolean _FALSE = new LuaBoolean(false);
    public static LuaValue s_metatable;
    public final boolean v;

    LuaBoolean(boolean bl) {
        this.v = bl;
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public String typename() {
        return "boolean";
    }

    @Override
    public boolean isboolean() {
        return false;
    }

    @Override
    public LuaValue not() {
        return this.v ? FALSE : LuaValue.TRUE;
    }

    public boolean booleanValue() {
        return this.v;
    }

    @Override
    public boolean toboolean() {
        return this.v;
    }

    @Override
    public String tojstring() {
        return this.v ? "true" : "false";
    }

    @Override
    public boolean optboolean(boolean bl) {
        return this.v;
    }

    @Override
    public boolean checkboolean() {
        return this.v;
    }

    @Override
    public LuaValue getmetatable() {
        return s_metatable;
    }
}

