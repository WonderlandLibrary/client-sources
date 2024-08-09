/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.scripts.interpreter.LuaDouble;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaArray;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaClass;
import mpp.venusfr.scripts.interpreter.compiler.jse.JavaInstance;

public class CoerceJavaToLua {
    static final Map COERCIONS = Collections.synchronizedMap(new HashMap());
    static final Coercion instanceCoercion;
    static final Coercion arrayCoercion;
    static final Coercion luaCoercion;

    public static LuaValue coerce(Object object) {
        if (object == null) {
            return LuaValue.NIL;
        }
        Class<?> clazz = object.getClass();
        Coercion coercion = (Coercion)COERCIONS.get(clazz);
        if (coercion == null) {
            coercion = clazz.isArray() ? arrayCoercion : (object instanceof LuaValue ? luaCoercion : instanceCoercion);
            COERCIONS.put(clazz, coercion);
        }
        return coercion.coerce(object);
    }

    static {
        BoolCoercion boolCoercion = new BoolCoercion();
        IntCoercion intCoercion = new IntCoercion();
        CharCoercion charCoercion = new CharCoercion();
        DoubleCoercion doubleCoercion = new DoubleCoercion();
        StringCoercion stringCoercion = new StringCoercion();
        BytesCoercion bytesCoercion = new BytesCoercion();
        ClassCoercion classCoercion = new ClassCoercion();
        COERCIONS.put(Boolean.class, boolCoercion);
        COERCIONS.put(Byte.class, intCoercion);
        COERCIONS.put(Character.class, charCoercion);
        COERCIONS.put(Short.class, intCoercion);
        COERCIONS.put(Integer.class, intCoercion);
        COERCIONS.put(Long.class, doubleCoercion);
        COERCIONS.put(Float.class, doubleCoercion);
        COERCIONS.put(Double.class, doubleCoercion);
        COERCIONS.put(String.class, stringCoercion);
        COERCIONS.put(byte[].class, bytesCoercion);
        COERCIONS.put(Class.class, classCoercion);
        instanceCoercion = new InstanceCoercion();
        arrayCoercion = new ArrayCoercion();
        luaCoercion = new LuaCoercion();
    }

    static interface Coercion {
        public LuaValue coerce(Object var1);
    }

    private static final class BoolCoercion
    implements Coercion {
        private BoolCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            Boolean bl = (Boolean)object;
            return bl != false ? LuaValue.TRUE : LuaValue.FALSE;
        }
    }

    private static final class IntCoercion
    implements Coercion {
        private IntCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            Number number = (Number)object;
            return LuaInteger.valueOf(number.intValue());
        }
    }

    private static final class CharCoercion
    implements Coercion {
        private CharCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            Character c = (Character)object;
            return LuaInteger.valueOf(c.charValue());
        }
    }

    private static final class DoubleCoercion
    implements Coercion {
        private DoubleCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            Number number = (Number)object;
            return LuaDouble.valueOf(number.doubleValue());
        }
    }

    private static final class StringCoercion
    implements Coercion {
        private StringCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            return LuaString.valueOf(object.toString());
        }
    }

    private static final class BytesCoercion
    implements Coercion {
        private BytesCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            return LuaValue.valueOf((byte[])object);
        }
    }

    private static final class ClassCoercion
    implements Coercion {
        private ClassCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            return JavaClass.forClass((Class)object);
        }
    }

    private static final class InstanceCoercion
    implements Coercion {
        private InstanceCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            return new JavaInstance(object);
        }
    }

    private static final class ArrayCoercion
    implements Coercion {
        private ArrayCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            return new JavaArray(object);
        }
    }

    private static final class LuaCoercion
    implements Coercion {
        private LuaCoercion() {
        }

        @Override
        public LuaValue coerce(Object object) {
            return (LuaValue)object;
        }
    }
}

