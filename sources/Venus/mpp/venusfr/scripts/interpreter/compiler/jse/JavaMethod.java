/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaFunction;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceLuaToJava;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaMember;

class JavaMethod
extends JavaMember {
    static final Map methods = Collections.synchronizedMap(new HashMap());
    final Method method;

    static JavaMethod forMethod(Method method) {
        JavaMethod javaMethod = (JavaMethod)methods.get(method);
        if (javaMethod == null) {
            javaMethod = new JavaMethod(method);
            methods.put(method, javaMethod);
        }
        return javaMethod;
    }

    static LuaFunction forMethods(JavaMethod[] javaMethodArray) {
        return new Overload(javaMethodArray);
    }

    private JavaMethod(Method method) {
        super(method.getParameterTypes(), method.getModifiers());
        this.method = method;
        try {
            if (!method.isAccessible()) {
                method.setAccessible(false);
            }
        } catch (SecurityException securityException) {
            // empty catch block
        }
    }

    @Override
    public LuaValue call() {
        return JavaMethod.error("method cannot be called without instance");
    }

    @Override
    public LuaValue call(LuaValue luaValue) {
        return this.invokeMethod(luaValue.checkuserdata(), NONE);
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
        return this.invokeMethod(luaValue.checkuserdata(), luaValue2);
    }

    @Override
    public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
        return this.invokeMethod(luaValue.checkuserdata(), JavaMethod.varargsOf(luaValue2, (Varargs)luaValue3));
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        return this.invokeMethod(varargs.checkuserdata(1), varargs.subargs(2));
    }

    LuaValue invokeMethod(Object object, Varargs varargs) {
        Object[] objectArray = this.convertArgs(varargs);
        try {
            return CoerceJavaToLua.coerce(this.method.invoke(object, objectArray));
        } catch (InvocationTargetException invocationTargetException) {
            throw new LuaError(invocationTargetException.getTargetException());
        } catch (Exception exception) {
            return JavaMethod.error("coercion error " + exception);
        }
    }

    static class Overload
    extends LuaFunction {
        final JavaMethod[] methods;

        Overload(JavaMethod[] javaMethodArray) {
            this.methods = javaMethodArray;
        }

        @Override
        public LuaValue call() {
            return Overload.error("method cannot be called without instance");
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            return this.invokeBestMethod(luaValue.checkuserdata(), LuaValue.NONE);
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2) {
            return this.invokeBestMethod(luaValue.checkuserdata(), luaValue2);
        }

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue2, LuaValue luaValue3) {
            return this.invokeBestMethod(luaValue.checkuserdata(), LuaValue.varargsOf(luaValue2, (Varargs)luaValue3));
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            return this.invokeBestMethod(varargs.checkuserdata(1), varargs.subargs(2));
        }

        private LuaValue invokeBestMethod(Object object, Varargs varargs) {
            JavaMethod javaMethod = null;
            int n = CoerceLuaToJava.SCORE_UNCOERCIBLE;
            for (int i = 0; i < this.methods.length; ++i) {
                int n2 = this.methods[i].score(varargs);
                if (n2 >= n) continue;
                n = n2;
                javaMethod = this.methods[i];
                if (n == 0) break;
            }
            if (javaMethod == null) {
                LuaValue.error("no coercible public method");
            }
            return javaMethod.invokeMethod(object, varargs);
        }
    }
}

