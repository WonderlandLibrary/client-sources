/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Varargs;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceJavaToLua;
import mpp.venusfr.scripts.interpreter.compiler.jse.CoerceLuaToJava;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaMember;
import mpp.venusfr.scripts.interpreter.lib.VarArgFunction;

class JavaConstructor
extends JavaMember {
    static final Map constructors = Collections.synchronizedMap(new HashMap());
    final Constructor constructor;

    static JavaConstructor forConstructor(Constructor constructor) {
        JavaConstructor javaConstructor = (JavaConstructor)constructors.get(constructor);
        if (javaConstructor == null) {
            javaConstructor = new JavaConstructor(constructor);
            constructors.put(constructor, javaConstructor);
        }
        return javaConstructor;
    }

    public static LuaValue forConstructors(JavaConstructor[] javaConstructorArray) {
        return new Overload(javaConstructorArray);
    }

    private JavaConstructor(Constructor constructor) {
        super(constructor.getParameterTypes(), constructor.getModifiers());
        this.constructor = constructor;
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        Object[] objectArray = this.convertArgs(varargs);
        try {
            return CoerceJavaToLua.coerce(this.constructor.newInstance(objectArray));
        } catch (InvocationTargetException invocationTargetException) {
            throw new LuaError(invocationTargetException.getTargetException());
        } catch (Exception exception) {
            return JavaConstructor.error("coercion error " + exception);
        }
    }

    static class Overload
    extends VarArgFunction {
        final JavaConstructor[] constructors;

        public Overload(JavaConstructor[] javaConstructorArray) {
            this.constructors = javaConstructorArray;
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            JavaConstructor javaConstructor = null;
            int n = CoerceLuaToJava.SCORE_UNCOERCIBLE;
            for (int i = 0; i < this.constructors.length; ++i) {
                int n2 = this.constructors[i].score(varargs);
                if (n2 >= n) continue;
                n = n2;
                javaConstructor = this.constructors[i];
                if (n == 0) break;
            }
            if (javaConstructor == null) {
                LuaValue.error("no coercible public method");
            }
            return javaConstructor.invoke(varargs);
        }
    }
}

