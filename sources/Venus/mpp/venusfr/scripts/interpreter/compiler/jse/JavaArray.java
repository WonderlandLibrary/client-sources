/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.lang.reflect.Array;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaUserdata;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceLuaToJava;
import mpp.venusfr.scripts.interpreter.lib.OneArgFunction;

class JavaArray
extends LuaUserdata {
    static final LuaValue LENGTH = JavaArray.valueOf("length");
    static final LuaTable array_metatable = new LuaTable();

    JavaArray(Object object) {
        super(object);
        this.setmetatable(array_metatable);
    }

    @Override
    public LuaValue get(LuaValue luaValue) {
        if (luaValue.equals(LENGTH)) {
            return JavaArray.valueOf(Array.getLength(this.m_instance));
        }
        if (luaValue.isint()) {
            int n = luaValue.toint() - 1;
            return n >= 0 && n < Array.getLength(this.m_instance) ? CoerceJavaToLua.coerce(Array.get(this.m_instance, luaValue.toint() - 1)) : NIL;
        }
        return super.get(luaValue);
    }

    @Override
    public void set(LuaValue luaValue, LuaValue luaValue2) {
        if (luaValue.isint()) {
            int n = luaValue.toint() - 1;
            if (n >= 0 && n < Array.getLength(this.m_instance)) {
                Array.set(this.m_instance, n, CoerceLuaToJava.coerce(luaValue2, this.m_instance.getClass().getComponentType()));
            } else if (this.m_metatable == null || !JavaArray.settable(this, luaValue, luaValue2)) {
                JavaArray.error("array index out of bounds");
            }
        } else {
            super.set(luaValue, luaValue2);
        }
    }

    static {
        array_metatable.rawset(LuaValue.LEN, (LuaValue)new LenFunction());
    }

    private static final class LenFunction
    extends OneArgFunction {
        private LenFunction() {
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return LuaValue.valueOf(Array.getLength(((LuaUserdata)luaValue).m_instance));
        }
    }
}

