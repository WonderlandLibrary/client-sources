/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;

public abstract class LuaFunction
extends LuaValue {
    public static LuaValue s_metatable;

    @Override
    public int type() {
        return 1;
    }

    @Override
    public String typename() {
        return "function";
    }

    @Override
    public boolean isfunction() {
        return false;
    }

    @Override
    public LuaFunction checkfunction() {
        return this;
    }

    @Override
    public LuaFunction optfunction(LuaFunction luaFunction) {
        return this;
    }

    @Override
    public LuaValue getmetatable() {
        return s_metatable;
    }

    @Override
    public String tojstring() {
        return "function: " + this.classnamestub();
    }

    @Override
    public LuaString strvalue() {
        return LuaFunction.valueOf(this.tojstring());
    }

    public String classnamestub() {
        int n;
        String string = this.getClass().getName();
        if (string.charAt(n = Math.max(string.lastIndexOf(46), string.lastIndexOf(36)) + 1) == '_') {
            ++n;
        }
        return string.substring(n);
    }

    public String name() {
        return this.classnamestub();
    }
}

