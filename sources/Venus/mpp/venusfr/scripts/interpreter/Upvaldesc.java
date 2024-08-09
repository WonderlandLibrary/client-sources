/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaString;

public class Upvaldesc {
    public LuaString name;
    public final boolean instack;
    public final short idx;

    public Upvaldesc(LuaString luaString, boolean bl, int n) {
        this.name = luaString;
        this.instack = bl;
        this.idx = (short)n;
    }

    public String toString() {
        return this.idx + (this.instack ? " instack " : " closed ") + this.name;
    }
}

