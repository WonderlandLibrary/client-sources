/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.reflect;

import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TypeToken<T> {
    private final Class<? super T> rawType;
    private final Type type;
    private final int hashCode;

    protected TypeToken() {
        this.type = this.getTypeTokenTypeArgument();
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    private TypeToken(Type type) {
        this.type = $Gson$Types.canonicalize(Objects.requireNonNull(type));
        this.rawType = $Gson$Types.getRawType(this.type);
        this.hashCode = this.type.hashCode();
    }

    private Type getTypeTokenTypeArgument() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            if (parameterizedType.getRawType() == TypeToken.class) {
                return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
            }
        } else if (type == TypeToken.class) {
            throw new IllegalStateException("TypeToken must be created with a type argument: new TypeToken<...>() {}; When using code shrinkers (ProGuard, R8, ...) make sure that generic signatures are preserved.");
        }
        throw new IllegalStateException("Must only create direct subclasses of TypeToken");
    }

    public final Class<? super T> getRawType() {
        return this.rawType;
    }

    public final Type getType() {
        return this.type;
    }

    @Deprecated
    public boolean isAssignableFrom(Class<?> clazz) {
        return this.isAssignableFrom((Type)clazz);
    }

    @Deprecated
    public boolean isAssignableFrom(Type type) {
        if (type == null) {
            return true;
        }
        if (this.type.equals(type)) {
            return false;
        }
        if (this.type instanceof Class) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(type));
        }
        if (this.type instanceof ParameterizedType) {
            return TypeToken.isAssignableFrom(type, (ParameterizedType)this.type, new HashMap<String, Type>());
        }
        if (this.type instanceof GenericArrayType) {
            return this.rawType.isAssignableFrom($Gson$Types.getRawType(type)) && TypeToken.isAssignableFrom(type, (GenericArrayType)this.type);
        }
        throw TypeToken.buildUnexpectedTypeError(this.type, Class.class, ParameterizedType.class, GenericArrayType.class);
    }

    @Deprecated
    public boolean isAssignableFrom(TypeToken<?> typeToken) {
        return this.isAssignableFrom(typeToken.getType());
    }

    private static boolean isAssignableFrom(Type type, GenericArrayType genericArrayType) {
        Type type2 = genericArrayType.getGenericComponentType();
        if (type2 instanceof ParameterizedType) {
            Type type3 = type;
            if (type instanceof GenericArrayType) {
                type3 = ((GenericArrayType)type).getGenericComponentType();
            } else if (type instanceof Class) {
                Class<?> clazz = (Class<?>)type;
                while (clazz.isArray()) {
                    clazz = clazz.getComponentType();
                }
                type3 = clazz;
            }
            return TypeToken.isAssignableFrom(type3, (ParameterizedType)type2, new HashMap<String, Type>());
        }
        return false;
    }

    private static boolean isAssignableFrom(Type type, ParameterizedType parameterizedType, Map<String, Type> map) {
        if (type == null) {
            return true;
        }
        if (parameterizedType.equals(type)) {
            return false;
        }
        Class<?> clazz = $Gson$Types.getRawType(type);
        ParameterizedType parameterizedType2 = null;
        if (type instanceof ParameterizedType) {
            parameterizedType2 = (ParameterizedType)type;
        }
        if (parameterizedType2 != null) {
            Type[] object = parameterizedType2.getActualTypeArguments();
            TypeVariable<Class<?>>[] typeVariableArray = clazz.getTypeParameters();
            for (int i = 0; i < object.length; ++i) {
                Type type2 = object[i];
                TypeVariable<Class<?>> typeVariable = typeVariableArray[i];
                while (type2 instanceof TypeVariable) {
                    TypeVariable typeVariable2 = (TypeVariable)type2;
                    type2 = map.get(typeVariable2.getName());
                }
                map.put(typeVariable.getName(), type2);
            }
            if (TypeToken.typeEquals(parameterizedType2, parameterizedType, map)) {
                return false;
            }
        }
        for (Type type2 : clazz.getGenericInterfaces()) {
            if (!TypeToken.isAssignableFrom(type2, parameterizedType, new HashMap<String, Type>(map))) continue;
            return false;
        }
        Type type3 = clazz.getGenericSuperclass();
        return TypeToken.isAssignableFrom(type3, parameterizedType, new HashMap<String, Type>(map));
    }

    private static boolean typeEquals(ParameterizedType parameterizedType, ParameterizedType parameterizedType2, Map<String, Type> map) {
        if (parameterizedType.getRawType().equals(parameterizedType2.getRawType())) {
            Type[] typeArray = parameterizedType.getActualTypeArguments();
            Type[] typeArray2 = parameterizedType2.getActualTypeArguments();
            for (int i = 0; i < typeArray.length; ++i) {
                if (TypeToken.matches(typeArray[i], typeArray2[i], map)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    private static AssertionError buildUnexpectedTypeError(Type type, Class<?> ... classArray) {
        StringBuilder stringBuilder = new StringBuilder("Unexpected type. Expected one of: ");
        for (Class<?> clazz : classArray) {
            stringBuilder.append(clazz.getName()).append(", ");
        }
        stringBuilder.append("but got: ").append(type.getClass().getName()).append(", for type token: ").append(type.toString()).append('.');
        return new AssertionError((Object)stringBuilder.toString());
    }

    private static boolean matches(Type type, Type type2, Map<String, Type> map) {
        return type2.equals(type) || type instanceof TypeVariable && type2.equals(map.get(((TypeVariable)type).getName()));
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public final boolean equals(Object object) {
        return object instanceof TypeToken && $Gson$Types.equals(this.type, ((TypeToken)object).type);
    }

    public final String toString() {
        return $Gson$Types.typeToString(this.type);
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken(type);
    }

    public static <T> TypeToken<T> get(Class<T> clazz) {
        return new TypeToken<T>(clazz);
    }

    public static TypeToken<?> getParameterized(Type type, Type ... typeArray) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(typeArray);
        if (!(type instanceof Class)) {
            throw new IllegalArgumentException("rawType must be of type Class, but was " + type);
        }
        int n = typeArray.length;
        Class clazz = (Class)type;
        TypeVariable<Class<T>>[] typeVariableArray = clazz.getTypeParameters();
        int n2 = typeVariableArray.length;
        if (n != n2) {
            throw new IllegalArgumentException(clazz.getName() + " requires " + n2 + " type arguments, but got " + n);
        }
        for (int i = 0; i < n2; ++i) {
            Type type2 = typeArray[i];
            Class<?> clazz2 = $Gson$Types.getRawType(type2);
            TypeVariable typeVariable = typeVariableArray[i];
            for (Type type3 : typeVariable.getBounds()) {
                Class<?> clazz3 = $Gson$Types.getRawType(type3);
                if (clazz3.isAssignableFrom(clazz2)) continue;
                throw new IllegalArgumentException("Type argument " + type2 + " does not satisfy bounds for type variable " + typeVariable + " declared by " + type);
            }
        }
        return new TypeToken($Gson$Types.newParameterizedTypeWithOwner(null, type, typeArray));
    }

    public static TypeToken<?> getArray(Type type) {
        return new TypeToken($Gson$Types.arrayOf(type));
    }
}

