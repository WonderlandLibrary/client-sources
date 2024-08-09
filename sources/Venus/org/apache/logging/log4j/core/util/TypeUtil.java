/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TypeUtil {
    private TypeUtil() {
    }

    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        ArrayList<Field> arrayList = new ArrayList<Field>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                arrayList.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return arrayList;
    }

    public static boolean isAssignable(Type type, Type type2) {
        Type type3;
        Type type4;
        Objects.requireNonNull(type, "No left hand side type provided");
        Objects.requireNonNull(type2, "No right hand side type provided");
        if (type.equals(type2)) {
            return false;
        }
        if (Object.class.equals((Object)type)) {
            return false;
        }
        if (type instanceof Class) {
            type4 = (Class)type;
            if (type2 instanceof Class) {
                Class clazz = (Class)type2;
                return ((Class)type4).isAssignableFrom(clazz);
            }
            if (type2 instanceof ParameterizedType && (type3 = ((ParameterizedType)type2).getRawType()) instanceof Class) {
                return ((Class)type4).isAssignableFrom((Class)type3);
            }
            if (((Class)type4).isArray() && type2 instanceof GenericArrayType) {
                return TypeUtil.isAssignable(((Class)type4).getComponentType(), ((GenericArrayType)type2).getGenericComponentType());
            }
        }
        if (type instanceof ParameterizedType) {
            type4 = (ParameterizedType)type;
            if (type2 instanceof Class) {
                type3 = type4.getRawType();
                if (type3 instanceof Class) {
                    return ((Class)type3).isAssignableFrom((Class)type2);
                }
            } else if (type2 instanceof ParameterizedType) {
                type3 = (ParameterizedType)type2;
                return TypeUtil.isParameterizedAssignable((ParameterizedType)type4, (ParameterizedType)type3);
            }
        }
        if (type instanceof GenericArrayType) {
            type4 = ((GenericArrayType)type).getGenericComponentType();
            if (type2 instanceof Class) {
                type3 = (Class)type2;
                if (((Class)type3).isArray()) {
                    return TypeUtil.isAssignable(type4, ((Class)type3).getComponentType());
                }
            } else if (type2 instanceof GenericArrayType) {
                return TypeUtil.isAssignable(type4, ((GenericArrayType)type2).getGenericComponentType());
            }
        }
        if (type instanceof WildcardType) {
            return TypeUtil.isWildcardAssignable((WildcardType)type, type2);
        }
        return true;
    }

    private static boolean isParameterizedAssignable(ParameterizedType parameterizedType, ParameterizedType parameterizedType2) {
        int n;
        if (parameterizedType.equals(parameterizedType2)) {
            return false;
        }
        Type[] typeArray = parameterizedType.getActualTypeArguments();
        Type[] typeArray2 = parameterizedType2.getActualTypeArguments();
        if (typeArray2.length != (n = typeArray.length)) {
            return true;
        }
        for (int i = 0; i < n; ++i) {
            Type type = typeArray[i];
            Type type2 = typeArray2[i];
            if (type.equals(type2) || type instanceof WildcardType && TypeUtil.isWildcardAssignable((WildcardType)type, type2)) continue;
            return true;
        }
        return false;
    }

    private static boolean isWildcardAssignable(WildcardType wildcardType, Type type) {
        Type[] typeArray = TypeUtil.getEffectiveUpperBounds(wildcardType);
        Type[] typeArray2 = TypeUtil.getEffectiveLowerBounds(wildcardType);
        if (type instanceof WildcardType) {
            WildcardType wildcardType2 = (WildcardType)type;
            Type[] typeArray3 = TypeUtil.getEffectiveUpperBounds(wildcardType2);
            Type[] typeArray4 = TypeUtil.getEffectiveLowerBounds(wildcardType2);
            for (Type type2 : typeArray) {
                for (Type type3 : typeArray3) {
                    if (TypeUtil.isBoundAssignable(type2, type3)) continue;
                    return true;
                }
                for (Type type3 : typeArray4) {
                    if (TypeUtil.isBoundAssignable(type2, type3)) continue;
                    return true;
                }
            }
            for (Type type2 : typeArray2) {
                for (Type type3 : typeArray3) {
                    if (TypeUtil.isBoundAssignable(type3, type2)) continue;
                    return true;
                }
                for (Type type3 : typeArray4) {
                    if (TypeUtil.isBoundAssignable(type3, type2)) continue;
                    return true;
                }
            }
        } else {
            for (Type type4 : typeArray) {
                if (TypeUtil.isBoundAssignable(type4, type)) continue;
                return true;
            }
            for (Type type4 : typeArray2) {
                if (TypeUtil.isBoundAssignable(type4, type)) continue;
                return true;
            }
        }
        return false;
    }

    private static Type[] getEffectiveUpperBounds(WildcardType wildcardType) {
        Type[] typeArray;
        Type[] typeArray2 = wildcardType.getUpperBounds();
        if (typeArray2.length == 0) {
            Type[] typeArray3 = new Type[1];
            typeArray = typeArray3;
            typeArray3[0] = Object.class;
        } else {
            typeArray = typeArray2;
        }
        return typeArray;
    }

    private static Type[] getEffectiveLowerBounds(WildcardType wildcardType) {
        Type[] typeArray;
        Type[] typeArray2 = wildcardType.getLowerBounds();
        if (typeArray2.length == 0) {
            Type[] typeArray3 = new Type[1];
            typeArray = typeArray3;
            typeArray3[0] = null;
        } else {
            typeArray = typeArray2;
        }
        return typeArray;
    }

    private static boolean isBoundAssignable(Type type, Type type2) {
        return type2 == null || type != null && TypeUtil.isAssignable(type, type2);
    }
}

