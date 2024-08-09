/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import mpp.venusfr.scripts.interpreter.LuaValue;

public class LuaUserdata
extends LuaValue {
    public Object m_instance;
    public LuaValue m_metatable;

    public LuaUserdata(Object object) {
        this.m_instance = object;
    }

    public LuaUserdata(Object object, LuaValue luaValue) {
        this.m_instance = object;
        this.m_metatable = luaValue;
    }

    @Override
    public String tojstring() {
        return String.valueOf(this.m_instance);
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public String typename() {
        return "userdata";
    }

    public int hashCode() {
        return this.m_instance.hashCode();
    }

    public Object userdata() {
        return this.m_instance;
    }

    @Override
    public boolean isuserdata() {
        return false;
    }

    @Override
    public boolean isuserdata(Class clazz) {
        return clazz.isAssignableFrom(this.m_instance.getClass());
    }

    @Override
    public Object touserdata() {
        return this.m_instance;
    }

    @Override
    public Object touserdata(Class clazz) {
        return clazz.isAssignableFrom(this.m_instance.getClass()) ? this.m_instance : null;
    }

    @Override
    public Object optuserdata(Object object) {
        return this.m_instance;
    }

    @Override
    public Object optuserdata(Class clazz, Object object) {
        if (!clazz.isAssignableFrom(this.m_instance.getClass())) {
            this.typerror(clazz.getName());
        }
        return this.m_instance;
    }

    @Override
    public LuaValue getmetatable() {
        return this.m_metatable;
    }

    @Override
    public LuaValue setmetatable(LuaValue luaValue) {
        this.m_metatable = luaValue;
        return this;
    }

    @Override
    public Object checkuserdata() {
        return this.m_instance;
    }

    @Override
    public Object checkuserdata(Class clazz) {
        if (clazz.isAssignableFrom(this.m_instance.getClass())) {
            return this.m_instance;
        }
        return this.typerror(clazz.getName());
    }

    @Override
    public LuaValue get(LuaValue luaValue) {
        return this.m_metatable != null ? LuaUserdata.gettable(this, luaValue) : NIL;
    }

    @Override
    public void set(LuaValue luaValue, LuaValue luaValue2) {
        if (this.m_metatable == null || !LuaUserdata.settable(this, luaValue, luaValue2)) {
            LuaUserdata.error("cannot set " + luaValue + " for userdata");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof LuaUserdata)) {
            return true;
        }
        LuaUserdata luaUserdata = (LuaUserdata)object;
        return this.m_instance.equals(luaUserdata.m_instance);
    }

    @Override
    public LuaValue eq(LuaValue luaValue) {
        return this.eq_b(luaValue) ? TRUE : FALSE;
    }

    @Override
    public boolean eq_b(LuaValue luaValue) {
        if (luaValue.raweq(this)) {
            return false;
        }
        if (this.m_metatable == null || !luaValue.isuserdata()) {
            return true;
        }
        LuaValue luaValue2 = luaValue.getmetatable();
        return luaValue2 != null && LuaValue.eqmtcall(this, this.m_metatable, luaValue, luaValue2);
    }

    @Override
    public boolean raweq(LuaValue luaValue) {
        return luaValue.raweq(this);
    }

    @Override
    public boolean raweq(LuaUserdata luaUserdata) {
        return this == luaUserdata || this.m_metatable == luaUserdata.m_metatable && this.m_instance.equals(luaUserdata.m_instance);
    }

    public boolean eqmt(LuaValue luaValue) {
        return this.m_metatable != null && luaValue.isuserdata() && LuaValue.eqmtcall(this, this.m_metatable, luaValue, luaValue.getmetatable());
    }
}

