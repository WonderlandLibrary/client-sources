/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import com.google.gson.internal.$Gson$Preconditions;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;

public final class $Gson$Types {
    static final Type[] EMPTY_TYPE_ARRAY;
    static final boolean $assertionsDisabled;

    private $Gson$Types() {
        throw new UnsupportedOperationException();
    }

    public static ParameterizedType newParameterizedTypeWithOwner(Type type, Type type2, Type ... typeArray) {
        return new ParameterizedTypeImpl(type, type2, typeArray);
    }

    public static GenericArrayType arrayOf(Type type) {
        return new GenericArrayTypeImpl(type);
    }

    public static WildcardType subtypeOf(Type type) {
        Type[] typeArray = type instanceof WildcardType ? ((WildcardType)type).getUpperBounds() : new Type[]{type};
        return new WildcardTypeImpl(typeArray, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType supertypeOf(Type type) {
        Type[] typeArray = type instanceof WildcardType ? ((WildcardType)type).getLowerBounds() : new Type[]{type};
        return new WildcardTypeImpl(new Type[]{Object.class}, typeArray);
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Class clazz = (Class)type;
            return clazz.isArray() ? new GenericArrayTypeImpl($Gson$Types.canonicalize(clazz.getComponentType())) : clazz;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            return new ParameterizedTypeImpl(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType)type;
            return new GenericArrayTypeImpl(genericArrayType.getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType)type;
            return new WildcardTypeImpl(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
        }
        return type;
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type type2 = parameterizedType.getRawType();
            $Gson$Preconditions.checkArgument(type2 instanceof Class);
            return (Class)type2;
        }
        if (type instanceof GenericArrayType) {
            Type type3 = ((GenericArrayType)type).getGenericComponentType();
            return Array.newInstance($Gson$Types.getRawType(type3), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            Type[] typeArray = ((WildcardType)type).getUpperBounds();
            if (!$assertionsDisabled && typeArray.length != 1) {
                throw new AssertionError();
            }
            return $Gson$Types.getRawType(typeArray[0]);
        }
        String string = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + string);
    }

    private static boolean equal(Object object, Object object2) {
        return Objects.equals(object, object2);
    }

    public static boolean equals(Type type, Type type2) {
        if (type == type2) {
            return false;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            if (!(type2 instanceof ParameterizedType)) {
                return true;
            }
            ParameterizedType parameterizedType = (ParameterizedType)type;
            ParameterizedType parameterizedType2 = (ParameterizedType)type2;
            return $Gson$Types.equal(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            if (!(type2 instanceof GenericArrayType)) {
                return true;
            }
            GenericArrayType genericArrayType = (GenericArrayType)type;
            GenericArrayType genericArrayType2 = (GenericArrayType)type2;
            return $Gson$Types.equals(genericArrayType.getGenericComponentType(), genericArrayType2.getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            if (!(type2 instanceof WildcardType)) {
                return true;
            }
            WildcardType wildcardType = (WildcardType)type;
            WildcardType wildcardType2 = (WildcardType)type2;
            return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
        }
        if (type instanceof TypeVariable) {
            if (!(type2 instanceof TypeVariable)) {
                return true;
            }
            TypeVariable typeVariable = (TypeVariable)type;
            TypeVariable typeVariable2 = (TypeVariable)type2;
            return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
        }
        return true;
    }

    public static String typeToString(Type type) {
        return type instanceof Class ? ((Class)type).getName() : type.toString();
    }

    private static Type getGenericSupertype(Type type, Class<?> object, Class<?> clazz) {
        Object object2;
        if (clazz == object) {
            return type;
        }
        if (clazz.isInterface()) {
            object2 = ((Class)object).getInterfaces();
            int n = ((Class<?>[])object2).length;
            for (int i = 0; i < n; ++i) {
                if (object2[i] == clazz) {
                    return ((Class)object).getGenericInterfaces()[i];
                }
                if (!clazz.isAssignableFrom(object2[i])) continue;
                return $Gson$Types.getGenericSupertype(((Class)object).getGenericInterfaces()[i], object2[i], clazz);
            }
        }
        if (!((Class)object).isInterface()) {
            while (object != Object.class) {
                object2 = ((Class)object).getSuperclass();
                if (object2 == clazz) {
                    return ((Class)object).getGenericSuperclass();
                }
                if (clazz.isAssignableFrom((Class<?>)object2)) {
                    return $Gson$Types.getGenericSupertype(((Class)object).getGenericSuperclass(), object2, clazz);
                }
                object = object2;
            }
        }
        return clazz;
    }

    private static Type getSupertype(Type type, Class<?> clazz, Class<?> clazz2) {
        if (type instanceof WildcardType) {
            Type[] typeArray = ((WildcardType)type).getUpperBounds();
            if (!$assertionsDisabled && typeArray.length != 1) {
                throw new AssertionError();
            }
            type = typeArray[0];
        }
        $Gson$Preconditions.checkArgument(clazz2.isAssignableFrom(clazz));
        return $Gson$Types.resolve(type, clazz, $Gson$Types.getGenericSupertype(type, clazz, clazz2));
    }

    public static Type getArrayComponentType(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType)type).getGenericComponentType() : ((Class)type).getComponentType();
    }

    public static Type getCollectionElementType(Type type, Class<?> clazz) {
        Type type2 = $Gson$Types.getSupertype(type, clazz, Collection.class);
        if (type2 instanceof ParameterizedType) {
            return ((ParameterizedType)type2).getActualTypeArguments()[0];
        }
        return Object.class;
    }

    public static Type[] getMapKeyAndValueTypes(Type type, Class<?> clazz) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type type2 = $Gson$Types.getSupertype(type, clazz, Map.class);
        if (type2 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type2;
            return parameterizedType.getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static Type resolve(Type type, Class<?> clazz, Type type2) {
        return $Gson$Types.resolve(type, clazz, type2, new HashMap());
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static Type resolve(Type var0, Class<?> var1_1, Type var2_2, Map<TypeVariable<?>, Type> var3_3) {
        block8: {
            block12: {
                block11: {
                    block10: {
                        block9: {
                            var4_4 = null;
                            while (var2_2 /* !! */  instanceof TypeVariable) {
                                var5_5 = (TypeVariable)var2_2 /* !! */ ;
                                var6_6 /* !! */  = var3_3.get(var5_5);
                                if (var6_6 /* !! */  != null) {
                                    return var6_6 /* !! */  == Void.TYPE ? var2_2 /* !! */  : var6_6 /* !! */ ;
                                }
                                var3_3.put((TypeVariable<?>)var5_5, Void.TYPE);
                                if (var4_4 == null) {
                                    var4_4 = var5_5;
                                }
                                if ((var2_2 /* !! */  = $Gson$Types.resolveTypeVariable(var0, var1_1, var5_5)) != var5_5) continue;
                                break block8;
                            }
                            if (!(var2_2 /* !! */  instanceof Class) || !((Class)var2_2 /* !! */ ).isArray()) break block9;
                            var5_5 = var2_2 /* !! */ ;
                            var6_6 /* !! */  = var5_5.getComponentType();
                            var2_2 /* !! */  = $Gson$Types.equal(var6_6 /* !! */ , var7_7 = $Gson$Types.resolve(var0, var1_1, var6_6 /* !! */ , var3_3)) != false ? var5_5 : $Gson$Types.arrayOf(var7_7);
                            break block8;
                        }
                        if (!(var2_2 /* !! */  instanceof GenericArrayType)) break block10;
                        var5_5 = (GenericArrayType)var2_2 /* !! */ ;
                        var6_6 /* !! */  = var5_5.getGenericComponentType();
                        var2_2 /* !! */  = $Gson$Types.equal(var6_6 /* !! */ , var7_8 = $Gson$Types.resolve(var0, var1_1, var6_6 /* !! */ , var3_3)) != false ? var5_5 : $Gson$Types.arrayOf(var7_8);
                        break block8;
                    }
                    if (!(var2_2 /* !! */  instanceof ParameterizedType)) break block11;
                    var5_5 = (ParameterizedType)var2_2 /* !! */ ;
                    var6_6 /* !! */  = var5_5.getOwnerType();
                    var7_9 = $Gson$Types.resolve(var0, var1_1, var6_6 /* !! */ , var3_3);
                    var8_11 = $Gson$Types.equal(var7_9, var6_6 /* !! */ ) == false;
                    var9_14 = var5_5.getActualTypeArguments();
                    var11_16 = var9_14.length;
                    for (var10_15 = 0; var10_15 < var11_16; ++var10_15) {
                        var12_17 = $Gson$Types.resolve(var0, var1_1, var9_14[var10_15], var3_3);
                        if ($Gson$Types.equal(var12_17, var9_14[var10_15])) continue;
                        if (!var8_11) {
                            var9_14 = (Type[])var9_14.clone();
                            var8_11 = true;
                        }
                        var9_14[var10_15] = var12_17;
                    }
                    var2_2 /* !! */  = var8_11 != false ? $Gson$Types.newParameterizedTypeWithOwner(var7_9, var5_5.getRawType(), var9_14) : var5_5;
                    break block8;
                }
                if (!(var2_2 /* !! */  instanceof WildcardType)) break block8;
                var5_5 = (WildcardType)var2_2 /* !! */ ;
                var6_6 /* !! */  = var5_5.getLowerBounds();
                var7_10 = var5_5.getUpperBounds();
                if (var6_6 /* !! */ .length != 1) break block12;
                var8_12 = $Gson$Types.resolve(var0, var1_1, var6_6 /* !! */ [0], var3_3);
                if (var8_12 == var6_6 /* !! */ [0]) ** GOTO lbl-1000
                var2_2 /* !! */  = $Gson$Types.supertypeOf(var8_12);
                break block8;
            }
            if (var7_10.length == 1 && (var8_13 = $Gson$Types.resolve(var0, var1_1, var7_10[0], var3_3)) != var7_10[0]) {
                var2_2 /* !! */  = $Gson$Types.subtypeOf(var8_13);
            } else lbl-1000:
            // 2 sources

            {
                var2_2 /* !! */  = var5_5;
            }
        }
        if (var4_4 != null) {
            var3_3.put((TypeVariable<?>)var4_4, var2_2 /* !! */ );
        }
        return var2_2 /* !! */ ;
    }

    private static Type resolveTypeVariable(Type type, Class<?> clazz, TypeVariable<?> typeVariable) {
        Class<?> clazz2 = $Gson$Types.declaringClassOf(typeVariable);
        if (clazz2 == null) {
            return typeVariable;
        }
        Type type2 = $Gson$Types.getGenericSupertype(type, clazz, clazz2);
        if (type2 instanceof ParameterizedType) {
            int n = $Gson$Types.indexOf(clazz2.getTypeParameters(), typeVariable);
            return ((ParameterizedType)type2).getActualTypeArguments()[n];
        }
        return typeVariable;
    }

    private static int indexOf(Object[] objectArray, Object object) {
        int n = objectArray.length;
        for (int i = 0; i < n; ++i) {
            if (!object.equals(objectArray[i])) continue;
            return i;
        }
        throw new NoSuchElementException();
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        Object obj = typeVariable.getGenericDeclaration();
        return obj instanceof Class ? (Class)obj : null;
    }

    static void checkNotPrimitive(Type type) {
        $Gson$Preconditions.checkArgument(!(type instanceof Class) || !((Class)type).isPrimitive());
    }

    static {
        $assertionsDisabled = !$Gson$Types.class.desiredAssertionStatus();
        EMPTY_TYPE_ARRAY = new Type[0];
    }

    private static final class WildcardTypeImpl
    implements WildcardType,
    Serializable {
        private final Type upperBound;
        private final Type lowerBound;
        private static final long serialVersionUID = 0L;

        public WildcardTypeImpl(Type[] typeArray, Type[] typeArray2) {
            $Gson$Preconditions.checkArgument(typeArray2.length <= 1);
            $Gson$Preconditions.checkArgument(typeArray.length == 1);
            if (typeArray2.length == 1) {
                Objects.requireNonNull(typeArray2[0]);
                $Gson$Types.checkNotPrimitive(typeArray2[0]);
                $Gson$Preconditions.checkArgument(typeArray[0] == Object.class);
                this.lowerBound = $Gson$Types.canonicalize(typeArray2[0]);
                this.upperBound = Object.class;
            } else {
                Objects.requireNonNull(typeArray[0]);
                $Gson$Types.checkNotPrimitive(typeArray[0]);
                this.lowerBound = null;
                this.upperBound = $Gson$Types.canonicalize(typeArray[0]);
            }
        }

        @Override
        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        @Override
        public Type[] getLowerBounds() {
            Type[] typeArray;
            if (this.lowerBound != null) {
                Type[] typeArray2 = new Type[1];
                typeArray = typeArray2;
                typeArray2[0] = this.lowerBound;
            } else {
                typeArray = EMPTY_TYPE_ARRAY;
            }
            return typeArray;
        }

        public boolean equals(Object object) {
            return object instanceof WildcardType && $Gson$Types.equals(this, (WildcardType)object);
        }

        public int hashCode() {
            return (this.lowerBound != null ? 31 + this.lowerBound.hashCode() : 1) ^ 31 + this.upperBound.hashCode();
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + $Gson$Types.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + $Gson$Types.typeToString(this.upperBound);
        }
    }

    private static final class GenericArrayTypeImpl
    implements GenericArrayType,
    Serializable {
        private final Type componentType;
        private static final long serialVersionUID = 0L;

        public GenericArrayTypeImpl(Type type) {
            Objects.requireNonNull(type);
            this.componentType = $Gson$Types.canonicalize(type);
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public boolean equals(Object object) {
            return object instanceof GenericArrayType && $Gson$Types.equals(this, (GenericArrayType)object);
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return $Gson$Types.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl
    implements ParameterizedType,
    Serializable {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;
        private static final long serialVersionUID = 0L;

        public ParameterizedTypeImpl(Type type, Type type2, Type ... typeArray) {
            int n;
            Objects.requireNonNull(type2);
            if (type2 instanceof Class) {
                Class clazz = (Class)type2;
                n = Modifier.isStatic(clazz.getModifiers()) || clazz.getEnclosingClass() == null ? 1 : 0;
                $Gson$Preconditions.checkArgument(type != null || n != 0);
            }
            this.ownerType = type == null ? null : $Gson$Types.canonicalize(type);
            this.rawType = $Gson$Types.canonicalize(type2);
            this.typeArguments = (Type[])typeArray.clone();
            n = this.typeArguments.length;
            for (int i = 0; i < n; ++i) {
                Objects.requireNonNull(this.typeArguments[i]);
                $Gson$Types.checkNotPrimitive(this.typeArguments[i]);
                this.typeArguments[i] = $Gson$Types.canonicalize(this.typeArguments[i]);
            }
        }

        @Override
        public Type[] getActualTypeArguments() {
            return (Type[])this.typeArguments.clone();
        }

        @Override
        public Type getRawType() {
            return this.rawType;
        }

        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }

        public boolean equals(Object object) {
            return object instanceof ParameterizedType && $Gson$Types.equals(this, (ParameterizedType)object);
        }

        private static int hashCodeOrZero(Object object) {
            return object != null ? object.hashCode() : 0;
        }

        public int hashCode() {
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ ParameterizedTypeImpl.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            int n = this.typeArguments.length;
            if (n == 0) {
                return $Gson$Types.typeToString(this.rawType);
            }
            StringBuilder stringBuilder = new StringBuilder(30 * (n + 1));
            stringBuilder.append($Gson$Types.typeToString(this.rawType)).append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
            for (int i = 1; i < n; ++i) {
                stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }
}

