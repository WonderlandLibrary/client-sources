/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaClosure;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaNumber;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaThread;
import mpp.venusfr.scripts.interpreter.LuaValue;

public class LuaNil
extends LuaValue {
    static final LuaNil _NIL = new LuaNil();
    public static LuaValue s_metatable;

    LuaNil() {
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public String toString() {
        return "nil";
    }

    @Override
    public String typename() {
        return "nil";
    }

    @Override
    public String tojstring() {
        return "nil";
    }

    @Override
    public LuaValue not() {
        return LuaValue.TRUE;
    }

    @Override
    public boolean toboolean() {
        return true;
    }

    @Override
    public boolean isnil() {
        return false;
    }

    @Override
    public LuaValue getmetatable() {
        return s_metatable;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof LuaNil;
    }

    @Override
    public LuaValue checknotnil() {
        return this.argerror("value");
    }

    @Override
    public boolean isvalidkey() {
        return true;
    }

    @Override
    public boolean optboolean(boolean bl) {
        return bl;
    }

    @Override
    public LuaClosure optclosure(LuaClosure luaClosure) {
        return luaClosure;
    }

    @Override
    public double optdouble(double d) {
        return d;
    }

    @Override
    public LuaFunction optfunction(LuaFunction luaFunction) {
        return luaFunction;
    }

    @Override
    public int optint(int n) {
        return n;
    }

    @Override
    public LuaInteger optinteger(LuaInteger luaInteger) {
        return luaInteger;
    }

    @Override
    public long optlong(long l) {
        return l;
    }

    @Override
    public LuaNumber optnumber(LuaNumber luaNumber) {
        return luaNumber;
    }

    @Override
    public LuaTable opttable(LuaTable luaTable) {
        return luaTable;
    }

    @Override
    public LuaThread optthread(LuaThread luaThread) {
        return luaThread;
    }

    @Override
    public String optjstring(String string) {
        return string;
    }

    @Override
    public LuaString optstring(LuaString luaString) {
        return luaString;
    }

    @Override
    public Object optuserdata(Object object) {
        return object;
    }

    @Override
    public Object optuserdata(Class clazz, Object object) {
        return object;
    }

    @Override
    public LuaValue optvalue(LuaValue luaValue) {
        return luaValue;
    }
}

