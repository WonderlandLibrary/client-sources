/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.lang.reflect.Field;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaUserdata;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceLuaToJava;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaClass;

class JavaInstance
extends LuaUserdata {
    JavaClass jclass;

    JavaInstance(Object object) {
        super(object);
    }

    @Override
    public LuaValue get(LuaValue luaValue) {
        Field field;
        if (this.jclass == null) {
            this.jclass = JavaClass.forClass(this.m_instance.getClass());
        }
        if ((field = this.jclass.getField(luaValue)) != null) {
            try {
                return CoerceJavaToLua.coerce(field.get(this.m_instance));
            } catch (Exception exception) {
                throw new LuaError(exception);
            }
        }
        LuaValue luaValue2 = this.jclass.getMethod(luaValue);
        if (luaValue2 != null) {
            return luaValue2;
        }
        Class clazz = this.jclass.getInnerClass(luaValue);
        if (clazz != null) {
            return JavaClass.forClass(clazz);
        }
        return super.get(luaValue);
    }

    @Override
    public void set(LuaValue luaValue, LuaValue luaValue2) {
        Field field;
        if (this.jclass == null) {
            this.jclass = JavaClass.forClass(this.m_instance.getClass());
        }
        if ((field = this.jclass.getField(luaValue)) != null) {
            try {
                field.set(this.m_instance, CoerceLuaToJava.coerce(luaValue2, field.getType()));
                return;
            } catch (Exception exception) {
                throw new LuaError(exception);
            }
        }
        super.set(luaValue, luaValue2);
    }
}

