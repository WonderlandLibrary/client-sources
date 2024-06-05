/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.reflect;

import com.viaversion.viaversion.libs.gson.internal.$Gson$Preconditions;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public class TypeToken<T> {
    final Class<? super T> rawType;
    final Type type;
    final int hashCode;

    protected TypeToken() {
        this.type = TypeToken.getSuperclassTypeParameter(this.getClass());
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    TypeToken(Type type) {
        this.type = $Gson$Types.canonicalize($Gson$Preconditions.checkNotNull(type));
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType)superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public final Class<? super T> getRawType() {
        return this.rawType;
    }

    public final Type getType() {
        return this.type;
    }

    @Deprecated
    public boolean isAssignableFrom(Class<?> cls) {
        return this.isAssignableFrom((Type)cls);
    }

    @Deprecated
    public boolean isAssignableFrom(Type from) {
        if (from == null) {
            return false;
        }
        if (this.type.equals(from)) {
            return true;
        }
        if (this.type instanceof Class) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(from));
        }
        if (this.type instanceof ParameterizedType) {
            return TypeToken.isAssignableFrom(from, (ParameterizedType)this.type, new HashMap<String, Type>());
        }
        if (!(this.type instanceof GenericArrayType)) {
            throw TypeToken.buildUnexpectedTypeError(this.type, Class.class, ParameterizedType.class, GenericArrayType.class);
        }
        if (!this.rawType.isAssignableFrom($Gson$Types.getRawType(from))) return false;
        if (!TypeToken.isAssignableFrom(from, (GenericArrayType)this.type)) return false;
        return true;
    }

    @Deprecated
    public boolean isAssignableFrom(TypeToken<?> token) {
        return this.isAssignableFrom(token.getType());
    }

    private static boolean isAssignableFrom(Type from, GenericArrayType to) {
        Type toGenericComponentType = to.getGenericComponentType();
        if (!(toGenericComponentType instanceof ParameterizedType)) return true;
        Type t = from;
        if (from instanceof GenericArrayType) {
            t = ((GenericArrayType)from).getGenericComponentType();
            return TypeToken.isAssignableFrom(t, (ParameterizedType)toGenericComponentType, new HashMap<String, Type>());
        }
        if (!(from instanceof Class)) return TypeToken.isAssignableFrom(t, (ParameterizedType)toGenericComponentType, new HashMap<String, Type>());
        Class<?> classType = (Class<?>)from;
        while (true) {
            if (!classType.isArray()) {
                t = classType;
                return TypeToken.isAssignableFrom(t, (ParameterizedType)toGenericComponentType, new HashMap<String, Type>());
            }
            classType = classType.getComponentType();
        }
    }

    private static boolean isAssignableFrom(Type from, ParameterizedType to, Map<String, Type> typeVarMap) {
        Type[] tArgs;
        if (from == null) {
            return false;
        }
        if (to.equals(from)) {
            return true;
        }
        Class<?> clazz = $Gson$Types.getRawType(from);
        ParameterizedType ptype = null;
        if (from instanceof ParameterizedType) {
            ptype = (ParameterizedType)from;
        }
        if (ptype != null) {
            tArgs = ptype.getActualTypeArguments();
            TypeVariable<Class<?>>[] tParams = clazz.getTypeParameters();
            for (int i = 0; i < tArgs.length; ++i) {
                Type arg = tArgs[i];
                TypeVariable<Class<?>> var = tParams[i];
                while (arg instanceof TypeVariable) {
                    TypeVariable v = (TypeVariable)arg;
                    arg = typeVarMap.get(v.getName());
                }
                typeVarMap.put(var.getName(), arg);
            }
            if (TypeToken.typeEquals(ptype, to, typeVarMap)) {
                return true;
            }
        }
        tArgs = clazz.getGenericInterfaces();
        int n = tArgs.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                Type sType = clazz.getGenericSuperclass();
                return TypeToken.isAssignableFrom(sType, to, new HashMap<String, Type>(typeVarMap));
            }
            Type itype = tArgs[n2];
            if (TypeToken.isAssignableFrom(itype, to, new HashMap<String, Type>(typeVarMap))) {
                return true;
            }
            ++n2;
        }
    }

    private static boolean typeEquals(ParameterizedType from, ParameterizedType to, Map<String, Type> typeVarMap) {
        if (!from.getRawType().equals(to.getRawType())) return false;
        Type[] fromArgs = from.getActualTypeArguments();
        Type[] toArgs = to.getActualTypeArguments();
        int i = 0;
        while (i < fromArgs.length) {
            if (!TypeToken.matches(fromArgs[i], toArgs[i], typeVarMap)) {
                return false;
            }
            ++i;
        }
        return true;
    }

    private static AssertionError buildUnexpectedTypeError(Type token, Class<?> ... expected) {
        StringBuilder exceptionMessage = new StringBuilder("Unexpected type. Expected one of: ");
        Class<?>[] classArray = expected;
        int n = classArray.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                exceptionMessage.append("but got: ").append(token.getClass().getName()).append(", for type token: ").append(token.toString()).append('.');
                return new AssertionError((Object)exceptionMessage.toString());
            }
            Class<?> clazz = classArray[n2];
            exceptionMessage.append(clazz.getName()).append(", ");
            ++n2;
        }
    }

    private static boolean matches(Type from, Type to, Map<String, Type> typeMap) {
        if (to.equals(from)) return true;
        if (!(from instanceof TypeVariable)) return false;
        if (!to.equals(typeMap.get(((TypeVariable)from).getName()))) return false;
        return true;
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof TypeToken)) return false;
        if (!$Gson$Types.equals(this.type, ((TypeToken)o).type)) return false;
        return true;
    }

    public final String toString() {
        return $Gson$Types.typeToString(this.type);
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken(type);
    }

    public static <T> TypeToken<T> get(Class<T> type) {
        return new TypeToken<T>(type);
    }

    public static TypeToken<?> getParameterized(Type rawType, Type ... typeArguments) {
        return new TypeToken($Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments));
    }

    public static TypeToken<?> getArray(Type componentType) {
        return new TypeToken($Gson$Types.arrayOf(componentType));
    }
}

