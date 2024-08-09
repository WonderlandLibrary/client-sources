/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.lib.TableLibFunction;
import mpp.venusfr.scripts.interpreter.lib.TwoArgFunction;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class TableLib
extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        LuaTable luaTable = new LuaTable();
        luaTable.set("concat", (LuaValue)new concat());
        luaTable.set("insert", (LuaValue)new insert());
        luaTable.set("pack", (LuaValue)new pack());
        luaTable.set("remove", (LuaValue)new remove());
        luaTable.set("sort", (LuaValue)new sort());
        luaTable.set("unpack", (LuaValue)new unpack());
        luaValue2.set("table", (LuaValue)luaTable);
        if (!luaValue2.get("package").isnil()) {
            luaValue2.get("package").get("loaded").set("table", (LuaValue)luaTable);
        }
        return NIL;
    }

    static class concat
    extends TableLibFunction {
        concat() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return luaValue.checktable().concat(EMPTYSTRING, 1, luaValue.length());
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return luaValue.checktable().concat(luaValue2.checkstring(), 1, luaValue.length());
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
            return luaValue.checktable().concat(luaValue2.checkstring(), luaValue3.checkint(), luaValue.length());
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3, LuaValue luaValue4) {
            return luaValue.checktable().concat(luaValue2.checkstring(), luaValue3.checkint(), luaValue4.checkint());
        }
    }

    static class insert
    extends VarArgFunction {
        insert() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            switch (varargs.narg()) {
                case 2: {
                    LuaTable luaTable = varargs.checktable(1);
                    luaTable.insert(luaTable.length() + 1, varargs.arg(2));
                    return NONE;
                }
                case 3: {
                    LuaTable luaTable = varargs.checktable(1);
                    int n = varargs.checkint(2);
                    int n2 = luaTable.length() + 1;
                    if (n < 1 || n > n2) {
                        insert.argerror(2, "position out of bounds: " + n + " not between 1 and " + n2);
                    }
                    luaTable.insert(n, varargs.arg(3));
                    return NONE;
                }
            }
            return insert.error("wrong number of arguments to 'table.insert': " + varargs.narg() + " (must be 2 or 3)");
        }
    }

    static class pack
    extends VarArgFunction {
        pack() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaTable luaTable = pack.tableOf(varargs, 1);
            luaTable.set("n", varargs.narg());
            return luaTable;
        }
    }

    static class remove
    extends VarArgFunction {
        remove() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaTable luaTable = varargs.checktable(1);
            int n = luaTable.length();
            int n2 = varargs.optint(2, n);
            if (n2 != n && (n2 < 1 || n2 > n + 1)) {
                remove.argerror(2, "position out of bounds: " + n2 + " not between 1 and " + (n + 1));
            }
            return luaTable.remove(n2);
        }
    }

    static class sort
    extends VarArgFunction {
        sort() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            varargs.checktable(1).sort(varargs.isnil(1) ? NIL : varargs.checkfunction(2));
            return NONE;
        }
    }

    static class unpack
    extends VarArgFunction {
        unpack() {
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            LuaTable luaTable = varargs.checktable(1);
            int n = varargs.arg(3).isnil() ? luaTable.length() : 0;
            return luaTable.unpack(varargs.optint(2, 1), varargs.optint(3, n));
        }
    }
}

