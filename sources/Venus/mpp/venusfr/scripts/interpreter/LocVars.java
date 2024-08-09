/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaString;

public class LocVars {
    public LuaString varname;
    public int startpc;
    public int endpc;

    public LocVars(LuaString luaString, int n, int n2) {
        this.varname = luaString;
        this.startpc = n;
        this.endpc = n2;
    }

    public String tojstring() {
        return this.varname + " " + this.startpc + "-" + this.endpc;
    }
}

