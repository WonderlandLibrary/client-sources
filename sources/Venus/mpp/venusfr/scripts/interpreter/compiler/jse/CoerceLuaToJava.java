/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;

public class CoerceLuaToJava {
    static int SCORE_NULL_VALUE = 16;
    static int SCORE_WRONG_TYPE = 256;
    static int SCORE_UNCOERCIBLE = 65536;
    static final Map COERCIONS = Collections.synchronizedMap(new HashMap());

    public static Object coerce(LuaValue luaValue, Class clazz) {
        return CoerceLuaToJava.getCoercion(clazz).coerce(luaValue);
    }

    static final int inheritanceLevels(Class clazz, Class clazz2) {
        if (clazz2 == null) {
            return SCORE_UNCOERCIBLE;
        }
        if (clazz == clazz2) {
            return 1;
        }
        int n = Math.min(SCORE_UNCOERCIBLE, CoerceLuaToJava.inheritanceLevels(clazz, clazz2.getSuperclass()) + 1);
        Class<?>[] classArray = clazz2.getInterfaces();
        for (int i = 0; i < classArray.length; ++i) {
            n = Math.min(n, CoerceLuaToJava.inheritanceLevels(clazz, classArray[i]) + 1);
        }
        return n;
    }

    static Coercion getCoercion(Class clazz) {
        Coercion coercion = (Coercion)COERCIONS.get(clazz);
        if (coercion != null) {
            return coercion;
        }
        if (clazz.isArray()) {
            Class<?> clazz2 = clazz.getComponentType();
            coercion = new ArrayCoercion(clazz.getComponentType());
        } else {
            coercion = new ObjectCoercion(clazz);
        }
        COERCIONS.put(clazz, coercion);
        return coercion;
    }

    static {
        BoolCoercion boolCoercion = new BoolCoercion();
        NumericCoercion numericCoercion = new NumericCoercion(0);
        NumericCoercion numericCoercion2 = new NumericCoercion(1);
        NumericCoercion numericCoercion3 = new NumericCoercion(2);
        NumericCoercion numericCoercion4 = new NumericCoercion(3);
        NumericCoercion numericCoercion5 = new NumericCoercion(4);
        NumericCoercion numericCoercion6 = new NumericCoercion(5);
        NumericCoercion numericCoercion7 = new NumericCoercion(6);
        StringCoercion stringCoercion = new StringCoercion(0);
        StringCoercion stringCoercion2 = new StringCoercion(1);
        COERCIONS.put(Boolean.TYPE, boolCoercion);
        COERCIONS.put(Boolean.class, boolCoercion);
        COERCIONS.put(Byte.TYPE, numericCoercion);
        COERCIONS.put(Byte.class, numericCoercion);
        COERCIONS.put(Character.TYPE, numericCoercion2);
        COERCIONS.put(Character.class, numericCoercion2);
        COERCIONS.put(Short.TYPE, numericCoercion3);
        COERCIONS.put(Short.class, numericCoercion3);
        COERCIONS.put(Integer.TYPE, numericCoercion4);
        COERCIONS.put(Integer.class, numericCoercion4);
        COERCIONS.put(Long.TYPE, numericCoercion5);
        COERCIONS.put(Long.class, numericCoercion5);
        COERCIONS.put(Float.TYPE, numericCoercion6);
        COERCIONS.put(Float.class, numericCoercion6);
        COERCIONS.put(Double.TYPE, numericCoercion7);
        COERCIONS.put(Double.class, numericCoercion7);
        COERCIONS.put(String.class, stringCoercion);
        COERCIONS.put(byte[].class, stringCoercion2);
    }

    static interface Coercion {
        public int score(LuaValue var1);

        public Object coerce(LuaValue var1);
    }

    static final class ArrayCoercion
    implements Coercion {
        final Class componentType;
        final Coercion componentCoercion;

        public ArrayCoercion(Class clazz) {
            this.componentType = clazz;
            this.componentCoercion = CoerceLuaToJava.getCoercion(clazz);
        }

        public String toString() {
            return "ArrayCoercion(" + this.componentType.getName() + ")";
        }

        @Override
        public int score(LuaValue luaValue) {
            switch (luaValue.type()) {
                case 5: {
                    return luaValue.length() == 0 ? 0 : this.componentCoercion.score(luaValue.get(1));
                }
                case 7: {
                    return CoerceLuaToJava.inheritanceLevels(this.componentType, luaValue.touserdata().getClass().getComponentType());
                }
                case 0: {
                    return SCORE_NULL_VALUE;
                }
            }
            return SCORE_UNCOERCIBLE;
        }

        @Override
        public Object coerce(LuaValue luaValue) {
            switch (luaValue.type()) {
                case 5: {
                    int n = luaValue.length();
                    Object object = Array.newInstance(this.componentType, n);
                    for (int i = 0; i < n; ++i) {
                        Array.set(object, i, this.componentCoercion.coerce(luaValue.get(i + 1)));
                    }
                    return object;
                }
                case 7: {
                    return luaValue.touserdata();
                }
                case 0: {
                    return null;
                }
            }
            return null;
        }
    }

    static final class ObjectCoercion
    implements Coercion {
        final Class targetType;

        ObjectCoercion(Class clazz) {
            this.targetType = clazz;
        }

        public String toString() {
            return "ObjectCoercion(" + this.targetType.getName() + ")";
        }

        @Override
        public int score(LuaValue luaValue) {
            switch (luaValue.type()) {
                case 3: {
                    return CoerceLuaToJava.inheritanceLevels(this.targetType, luaValue.isint() ? Integer.class : Double.class);
                }
                case 1: {
                    return CoerceLuaToJava.inheritanceLevels(this.targetType, Boolean.class);
                }
                case 4: {
                    return CoerceLuaToJava.inheritanceLevels(this.targetType, String.class);
                }
                case 7: {
                    return CoerceLuaToJava.inheritanceLevels(this.targetType, luaValue.touserdata().getClass());
                }
                case 0: {
                    return SCORE_NULL_VALUE;
                }
            }
            return CoerceLuaToJava.inheritanceLevels(this.targetType, luaValue.getClass());
        }

        @Override
        public Object coerce(LuaValue luaValue) {
            switch (luaValue.type()) {
                case 3: {
                    return luaValue.isint() ? (Number)luaValue.toint() : (Number)new Double(luaValue.todouble());
                }
                case 1: {
                    return luaValue.toboolean() ? Boolean.TRUE : Boolean.FALSE;
                }
                case 4: {
                    return luaValue.tojstring();
                }
                case 7: {
                    return luaValue.optuserdata(this.targetType, null);
                }
                case 0: {
                    return null;
                }
            }
            return luaValue;
        }
    }

    static final class BoolCoercion
    implements Coercion {
        BoolCoercion() {
        }

        public String toString() {
            return "BoolCoercion()";
        }

        @Override
        public int score(LuaValue luaValue) {
            switch (luaValue.type()) {
                case 1: {
                    return 1;
                }
            }
            return 0;
        }

        @Override
        public Object coerce(LuaValue luaValue) {
            return luaValue.toboolean() ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    static final class NumericCoercion
    implements Coercion {
        static final int TARGET_TYPE_BYTE = 0;
        static final int TARGET_TYPE_CHAR = 1;
        static final int TARGET_TYPE_SHORT = 2;
        static final int TARGET_TYPE_INT = 3;
        static final int TARGET_TYPE_LONG = 4;
        static final int TARGET_TYPE_FLOAT = 5;
        static final int TARGET_TYPE_DOUBLE = 6;
        static final String[] TYPE_NAMES = new String[]{"byte", "char", "short", "int", "long", "float", "double"};
        final int targetType;

        public String toString() {
            return "NumericCoercion(" + TYPE_NAMES[this.targetType] + ")";
        }

        NumericCoercion(int n) {
            this.targetType = n;
        }

        @Override
        public int score(LuaValue luaValue) {
            int n = 0;
            if (luaValue.type() == 4) {
                if ((luaValue = luaValue.tonumber()).isnil()) {
                    return SCORE_UNCOERCIBLE;
                }
                n = 4;
            }
            if (luaValue.isint()) {
                switch (this.targetType) {
                    case 0: {
                        int n2 = luaValue.toint();
                        return n + (n2 == (byte)n2 ? 0 : SCORE_WRONG_TYPE);
                    }
                    case 1: {
                        int n3 = luaValue.toint();
                        return n + (n3 == (byte)n3 ? 1 : (n3 == (char)n3 ? 0 : SCORE_WRONG_TYPE));
                    }
                    case 2: {
                        int n4 = luaValue.toint();
                        return n + (n4 == (byte)n4 ? 1 : (n4 == (short)n4 ? 0 : SCORE_WRONG_TYPE));
                    }
                    case 3: {
                        int n5 = luaValue.toint();
                        return n + (n5 == (byte)n5 ? 2 : (n5 == (char)n5 || n5 == (short)n5 ? 1 : 0));
                    }
                    case 5: {
                        return n + 1;
                    }
                    case 4: {
                        return n + 1;
                    }
                    case 6: {
                        return n + 2;
                    }
                }
                return SCORE_WRONG_TYPE;
            }
            if (luaValue.isnumber()) {
                switch (this.targetType) {
                    case 0: {
                        return SCORE_WRONG_TYPE;
                    }
                    case 1: {
                        return SCORE_WRONG_TYPE;
                    }
                    case 2: {
                        return SCORE_WRONG_TYPE;
                    }
                    case 3: {
                        return SCORE_WRONG_TYPE;
                    }
                    case 4: {
                        double d = luaValue.todouble();
                        return n + (d == (double)((long)d) ? 0 : SCORE_WRONG_TYPE);
                    }
                    case 5: {
                        double d = luaValue.todouble();
                        return n + (d == (double)((float)d) ? 0 : SCORE_WRONG_TYPE);
                    }
                    case 6: {
                        double d = luaValue.todouble();
                        return n + (d == (double)((long)d) || d == (double)((float)d) ? 1 : 0);
                    }
                }
                return SCORE_WRONG_TYPE;
            }
            return SCORE_UNCOERCIBLE;
        }

        @Override
        public Object coerce(LuaValue luaValue) {
            switch (this.targetType) {
                case 0: {
                    return (byte)luaValue.toint();
                }
                case 1: {
                    return new Character((char)luaValue.toint());
                }
                case 2: {
                    return (short)luaValue.toint();
                }
                case 3: {
                    return luaValue.toint();
                }
                case 4: {
                    return (long)luaValue.todouble();
                }
                case 5: {
                    return new Float((float)luaValue.todouble());
                }
                case 6: {
                    return new Double(luaValue.todouble());
                }
            }
            return null;
        }
    }

    static final class StringCoercion
    implements Coercion {
        public static final int TARGET_TYPE_STRING = 0;
        public static final int TARGET_TYPE_BYTES = 1;
        final int targetType;

        public StringCoercion(int n) {
            this.targetType = n;
        }

        public String toString() {
            return "StringCoercion(" + (this.targetType == 0 ? "String" : "byte[]") + ")";
        }

        @Override
        public int score(LuaValue luaValue) {
            switch (luaValue.type()) {
                case 4: {
                    return luaValue.checkstring().isValidUtf8() ? (this.targetType == 0 ? 0 : 1) : (this.targetType == 1 ? 0 : SCORE_WRONG_TYPE);
                }
                case 0: {
                    return SCORE_NULL_VALUE;
                }
            }
            return this.targetType == 0 ? SCORE_WRONG_TYPE : SCORE_UNCOERCIBLE;
        }

        @Override
        public Object coerce(LuaValue luaValue) {
            if (luaValue.isnil()) {
                return null;
            }
            if (this.targetType == 0) {
                return luaValue.tojstring();
            }
            LuaString luaString = luaValue.checkstring();
            byte[] byArray = new byte[luaString.m_length];
            luaString.copyInto(0, byArray, 0, byArray.length);
            return byArray;
        }
    }
}

