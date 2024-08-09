/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceLuaToJava;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaClass;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

public class LuajavaLib
extends VarArgFunction {
    static final int INIT = 0;
    static final int BINDCLASS = 1;
    static final int NEWINSTANCE = 2;
    static final int NEW = 3;
    static final int CREATEPROXY = 4;
    static final int LOADLIB = 5;
    static final String[] NAMES = new String[]{"bindClass", "newInstance", "new", "createProxy", "loadLib"};
    static final int METHOD_MODIFIERS_VARARGS = 128;

    @Override
    public Varargs invoke(Varargs varargs) {
        try {
            switch (this.opcode) {
                case 0: {
                    LuaValue luaValue = varargs.arg(2);
                    LuaTable luaTable = new LuaTable();
                    this.bind(luaTable, this.getClass(), NAMES, 1);
                    luaValue.set("luajava", (LuaValue)luaTable);
                    if (!luaValue.get("package").isnil()) {
                        luaValue.get("package").get("loaded").set("luajava", (LuaValue)luaTable);
                    }
                    return luaTable;
                }
                case 1: {
                    Class clazz = this.classForName(varargs.checkjstring(1));
                    return JavaClass.forClass(clazz);
                }
                case 2: 
                case 3: {
                    LuaValue luaValue = varargs.checkvalue(1);
                    Class clazz = this.opcode == 2 ? this.classForName(luaValue.tojstring()) : (Class)luaValue.checkuserdata(Class.class);
                    Varargs varargs2 = varargs.subargs(2);
                    return JavaClass.forClass(clazz).getConstructor().invoke(varargs2);
                }
                case 4: {
                    int n = varargs.narg() - 1;
                    if (n <= 0) {
                        throw new LuaError("no interfaces");
                    }
                    LuaTable luaTable = varargs.checktable(n + 1);
                    Class[] classArray = new Class[n];
                    for (int i = 0; i < n; ++i) {
                        classArray[i] = this.classForName(varargs.checkjstring(i + 1));
                    }
                    ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler(luaTable);
                    Object object = Proxy.newProxyInstance(this.getClass().getClassLoader(), classArray, proxyInvocationHandler);
                    return LuaValue.userdataOf(object);
                }
                case 5: {
                    String string = varargs.checkjstring(1);
                    String string2 = varargs.checkjstring(2);
                    Class clazz = this.classForName(string);
                    Method method = clazz.getMethod(string2, new Class[0]);
                    Object object = method.invoke(clazz, new Object[0]);
                    if (object instanceof LuaValue) {
                        return (LuaValue)object;
                    }
                    return NIL;
                }
            }
            throw new LuaError("not yet supported: " + this);
        } catch (LuaError luaError) {
            throw luaError;
        } catch (InvocationTargetException invocationTargetException) {
            throw new LuaError(invocationTargetException.getTargetException());
        } catch (Exception exception) {
            throw new LuaError(exception);
        }
    }

    protected Class classForName(String string) throws ClassNotFoundException {
        return Class.forName(string, true, ClassLoader.getSystemClassLoader());
    }

    private static final class ProxyInvocationHandler
    implements InvocationHandler {
        private final LuaValue lobj;

        private ProxyInvocationHandler(LuaValue luaValue) {
            this.lobj = luaValue;
        }

        @Override
        public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
            LuaValue[] luaValueArray;
            Object object2;
            int n;
            String string = method.getName();
            LuaValue luaValue = this.lobj.get(string);
            if (luaValue.isnil()) {
                return null;
            }
            boolean bl = (method.getModifiers() & 0x80) != 0;
            int n2 = n = objectArray != null ? objectArray.length : 0;
            if (bl) {
                int n3;
                object2 = objectArray[--n];
                int n4 = Array.getLength(object2);
                luaValueArray = new LuaValue[n + n4];
                for (n3 = 0; n3 < n; ++n3) {
                    luaValueArray[n3] = CoerceJavaToLua.coerce(objectArray[n3]);
                }
                for (n3 = 0; n3 < n4; ++n3) {
                    luaValueArray[n3 + n] = CoerceJavaToLua.coerce(Array.get(object2, n3));
                }
            } else {
                luaValueArray = new LuaValue[n];
                for (int i = 0; i < n; ++i) {
                    luaValueArray[i] = CoerceJavaToLua.coerce(objectArray[i]);
                }
            }
            object2 = luaValue.invoke(luaValueArray).arg1();
            return CoerceLuaToJava.coerce((LuaValue)object2, method.getReturnType());
        }
    }
}

