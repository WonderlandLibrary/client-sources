/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LocVars;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Upvaldesc;

public class Prototype {
    public LuaValue[] k;
    public int[] code;
    public Prototype[] p = NOSUBPROTOS;
    public int[] lineinfo;
    public LocVars[] locvars;
    public Upvaldesc[] upvalues;
    public LuaString source;
    public int linedefined;
    public int lastlinedefined;
    public int numparams;
    public int is_vararg;
    public int maxstacksize;
    private static final Upvaldesc[] NOUPVALUES = new Upvaldesc[0];
    private static final Prototype[] NOSUBPROTOS = new Prototype[0];

    public Prototype() {
        this.upvalues = NOUPVALUES;
    }

    public Prototype(int n) {
        this.upvalues = new Upvaldesc[n];
    }

    public String toString() {
        return this.source + ":" + this.linedefined + "-" + this.lastlinedefined;
    }

    public LuaString getlocalname(int n, int n2) {
        for (int i = 0; i < this.locvars.length && this.locvars[i].startpc <= n2; ++i) {
            if (n2 >= this.locvars[i].endpc || --n != 0) continue;
            return this.locvars[i].varname;
        }
        return null;
    }

    public String shortsource() {
        String string = this.source.tojstring();
        if (string.startsWith("@") || string.startsWith("=")) {
            string = string.substring(1);
        } else if (string.startsWith("\u001b")) {
            string = "binary string";
        }
        return string;
    }
}

