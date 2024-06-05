/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.internal.$Gson$Preconditions;
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
import java.util.Properties;

public final class $Gson$Types {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    private $Gson$Types() {
        throw new UnsupportedOperationException();
    }

    public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Type rawType, Type ... typeArguments) {
        return new ParameterizedTypeImpl(ownerType, rawType, typeArguments);
    }

    public static GenericArrayType arrayOf(Type componentType) {
        return new GenericArrayTypeImpl(componentType);
    }

    public static WildcardType subtypeOf(Type bound) {
        Type[] upperBounds;
        if (bound instanceof WildcardType) {
            upperBounds = ((WildcardType)bound).getUpperBounds();
            return new WildcardTypeImpl(upperBounds, EMPTY_TYPE_ARRAY);
        }
        upperBounds = new Type[]{bound};
        return new WildcardTypeImpl(upperBounds, EMPTY_TYPE_ARRAY);
    }

    public static WildcardType supertypeOf(Type bound) {
        Type[] lowerBounds = bound instanceof WildcardType ? ((WildcardType)bound).getLowerBounds() : new Type[]{bound};
        return new WildcardTypeImpl(new Type[]{Object.class}, lowerBounds);
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            Type type2;
            Class c = (Class)type;
            if (c.isArray()) {
                type2 = new GenericArrayTypeImpl($Gson$Types.canonicalize(c.getComponentType()));
                return type2;
            }
            type2 = c;
            return type2;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType)type;
            return new ParameterizedTypeImpl(p.getOwnerType(), p.getRawType(), p.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            GenericArrayType g = (GenericArrayType)type;
            return new GenericArrayTypeImpl(g.getGenericComponentType());
        }
        if (!(type instanceof WildcardType)) return type;
        WildcardType w = (WildcardType)type;
        return new WildcardTypeImpl(w.getUpperBounds(), w.getLowerBounds());
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type rawType = parameterizedType.getRawType();
            $Gson$Preconditions.checkArgument(rawType instanceof Class);
            return (Class)rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType)type).getGenericComponentType();
            return Array.newInstance($Gson$Types.getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return $Gson$Types.getRawType(((WildcardType)type).getUpperBounds()[0]);
        }
        String className = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
    }

    static boolean equal(Object a, Object b) {
        if (a == b) return true;
        if (a == null) return false;
        if (!a.equals(b)) return false;
        return true;
    }

    public static boolean equals(Type a, Type b) {
        if (a == b) {
            return true;
        }
        if (a instanceof Class) {
            return a.equals(b);
        }
        if (a instanceof ParameterizedType) {
            if (!(b instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType pa = (ParameterizedType)a;
            ParameterizedType pb = (ParameterizedType)b;
            if (!$Gson$Types.equal(pa.getOwnerType(), pb.getOwnerType())) return false;
            if (!pa.getRawType().equals(pb.getRawType())) return false;
            if (!Arrays.equals(pa.getActualTypeArguments(), pb.getActualTypeArguments())) return false;
            return true;
        }
        if (a instanceof GenericArrayType) {
            if (!(b instanceof GenericArrayType)) {
                return false;
            }
            GenericArrayType ga = (GenericArrayType)a;
            GenericArrayType gb = (GenericArrayType)b;
            return $Gson$Types.equals(ga.getGenericComponentType(), gb.getGenericComponentType());
        }
        if (a instanceof WildcardType) {
            if (!(b instanceof WildcardType)) {
                return false;
            }
            WildcardType wa = (WildcardType)a;
            WildcardType wb = (WildcardType)b;
            if (!Arrays.equals(wa.getUpperBounds(), wb.getUpperBounds())) return false;
            if (!Arrays.equals(wa.getLowerBounds(), wb.getLowerBounds())) return false;
            return true;
        }
        if (!(a instanceof TypeVariable)) return false;
        if (!(b instanceof TypeVariable)) {
            return false;
        }
        TypeVariable va = (TypeVariable)a;
        TypeVariable vb = (TypeVariable)b;
        if (va.getGenericDeclaration() != vb.getGenericDeclaration()) return false;
        if (!va.getName().equals(vb.getName())) return false;
        return true;
    }

    static int hashCodeOrZero(Object o) {
        if (o == null) return 0;
        int n = o.hashCode();
        return n;
    }

    public static String typeToString(Type type) {
        String string;
        if (type instanceof Class) {
            string = ((Class)type).getName();
            return string;
        }
        string = type.toString();
        return string;
    }

    static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
        if (toResolve == rawType) {
            return context;
        }
        if (toResolve.isInterface()) {
            Class<?>[] interfaces = rawType.getInterfaces();
            int length = interfaces.length;
            for (int i = 0; i < length; ++i) {
                if (interfaces[i] == toResolve) {
                    return rawType.getGenericInterfaces()[i];
                }
                if (!toResolve.isAssignableFrom(interfaces[i])) continue;
                return $Gson$Types.getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
            }
        }
        if (rawType.isInterface()) return toResolve;
        while (rawType != Object.class) {
            Class<?> rawSupertype = rawType.getSuperclass();
            if (rawSupertype == toResolve) {
                return rawType.getGenericSuperclass();
            }
            if (toResolve.isAssignableFrom(rawSupertype)) {
                return $Gson$Types.getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, toResolve);
            }
            rawType = rawSupertype;
        }
        return toResolve;
    }

    static Type getSupertype(Type context, Class<?> contextRawType, Class<?> supertype) {
        if (context instanceof WildcardType) {
            context = ((WildcardType)context).getUpperBounds()[0];
        }
        $Gson$Preconditions.checkArgument(supertype.isAssignableFrom(contextRawType));
        return $Gson$Types.resolve(context, contextRawType, $Gson$Types.getGenericSupertype(context, contextRawType, supertype));
    }

    public static Type getArrayComponentType(Type array) {
        Class<?> clazz;
        if (array instanceof GenericArrayType) {
            clazz = ((GenericArrayType)array).getGenericComponentType();
            return clazz;
        }
        clazz = ((Class)array).getComponentType();
        return clazz;
    }

    public static Type getCollectionElementType(Type context, Class<?> contextRawType) {
        Type collectionType = $Gson$Types.getSupertype(context, contextRawType, Collection.class);
        if (collectionType instanceof WildcardType) {
            collectionType = ((WildcardType)collectionType).getUpperBounds()[0];
        }
        if (!(collectionType instanceof ParameterizedType)) return Object.class;
        return ((ParameterizedType)collectionType).getActualTypeArguments()[0];
    }

    public static Type[] getMapKeyAndValueTypes(Type context, Class<?> contextRawType) {
        if (context == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type mapType = $Gson$Types.getSupertype(context, contextRawType, Map.class);
        if (!(mapType instanceof ParameterizedType)) return new Type[]{Object.class, Object.class};
        ParameterizedType mapParameterizedType = (ParameterizedType)mapType;
        return mapParameterizedType.getActualTypeArguments();
    }

    public static Type resolve(Type context, Class<?> contextRawType, Type toResolve) {
        return $Gson$Types.resolve(context, contextRawType, toResolve, new HashMap());
    }

    /*
     * Unable to fully structure code
     */
    private static Type resolve(Type context, Class<?> contextRawType, Type toResolve, Map<TypeVariable<?>, Type> visitedTypeVariables) {
        block8: {
            block9: {
                block13: {
                    block12: {
                        block11: {
                            block10: {
                                resolving = null;
                                while (toResolve instanceof TypeVariable) {
                                    typeVariable = (TypeVariable)toResolve;
                                    previouslyResolved = visitedTypeVariables.get(typeVariable);
                                    if (previouslyResolved != null) {
                                        if (previouslyResolved == Void.TYPE) {
                                            v0 = toResolve;
                                            return v0;
                                        }
                                        break block8;
                                    }
                                    visitedTypeVariables.put(typeVariable, Void.TYPE);
                                    if (resolving == null) {
                                        resolving = typeVariable;
                                    }
                                    if ((toResolve = $Gson$Types.resolveTypeVariable(context, contextRawType, typeVariable)) != typeVariable) continue;
                                    break block9;
                                }
                                if (!(toResolve instanceof Class) || !((Class)toResolve).isArray()) break block10;
                                original = (Class)toResolve;
                                componentType = original.getComponentType();
                                toResolve = $Gson$Types.equal(componentType, newComponentType = $Gson$Types.resolve(context, contextRawType, componentType, visitedTypeVariables)) != false ? original : $Gson$Types.arrayOf(newComponentType);
                                break block9;
                            }
                            if (!(toResolve instanceof GenericArrayType)) break block11;
                            original = (GenericArrayType)toResolve;
                            componentType = original.getGenericComponentType();
                            toResolve = $Gson$Types.equal(componentType, newComponentType = $Gson$Types.resolve(context, contextRawType, componentType, visitedTypeVariables)) != false ? original : $Gson$Types.arrayOf(newComponentType);
                            break block9;
                        }
                        if (!(toResolve instanceof ParameterizedType)) break block12;
                        original = (ParameterizedType)toResolve;
                        ownerType = original.getOwnerType();
                        newOwnerType = $Gson$Types.resolve(context, contextRawType, ownerType, visitedTypeVariables);
                        changed = $Gson$Types.equal(newOwnerType, ownerType) == false;
                        args = original.getActualTypeArguments();
                        length = args.length;
                        for (t = 0; t < length; ++t) {
                            resolvedTypeArgument = $Gson$Types.resolve(context, contextRawType, args[t], visitedTypeVariables);
                            if ($Gson$Types.equal(resolvedTypeArgument, args[t])) continue;
                            if (!changed) {
                                args = (Type[])args.clone();
                                changed = true;
                            }
                            args[t] = resolvedTypeArgument;
                        }
                        toResolve = changed != false ? $Gson$Types.newParameterizedTypeWithOwner(newOwnerType, original.getRawType(), args) : original;
                        break block9;
                    }
                    if (!(toResolve instanceof WildcardType)) break block9;
                    original = (WildcardType)toResolve;
                    originalLowerBound = original.getLowerBounds();
                    originalUpperBound = original.getUpperBounds();
                    if (originalLowerBound.length != 1) break block13;
                    lowerBound = $Gson$Types.resolve(context, contextRawType, originalLowerBound[0], visitedTypeVariables);
                    if (lowerBound == originalLowerBound[0]) ** GOTO lbl-1000
                    toResolve = $Gson$Types.supertypeOf(lowerBound);
                    break block9;
                }
                if (originalUpperBound.length == 1 && (upperBound = $Gson$Types.resolve(context, contextRawType, originalUpperBound[0], visitedTypeVariables)) != originalUpperBound[0]) {
                    toResolve = $Gson$Types.subtypeOf(upperBound);
                } else lbl-1000:
                // 2 sources

                {
                    toResolve = original;
                }
            }
            if (resolving == null) return toResolve;
            visitedTypeVariables.put(resolving, toResolve);
            return toResolve;
        }
        v0 = previouslyResolved;
        return v0;
    }

    static Type resolveTypeVariable(Type context, Class<?> contextRawType, TypeVariable<?> unknown) {
        Class<?> declaredByRaw = $Gson$Types.declaringClassOf(unknown);
        if (declaredByRaw == null) {
            return unknown;
        }
        Type declaredBy = $Gson$Types.getGenericSupertype(context, contextRawType, declaredByRaw);
        if (!(declaredBy instanceof ParameterizedType)) return unknown;
        int index = $Gson$Types.indexOf(declaredByRaw.getTypeParameters(), unknown);
        return ((ParameterizedType)declaredBy).getActualTypeArguments()[index];
    }

    private static int indexOf(Object[] array, Object toFind) {
        int i = 0;
        int length = array.length;
        while (i < length) {
            if (toFind.equals(array[i])) {
                return i;
            }
            ++i;
        }
        throw new NoSuchElementException();
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        Object genericDeclaration = typeVariable.getGenericDeclaration();
        if (!(genericDeclaration instanceof Class)) return null;
        Class clazz = (Class)genericDeclaration;
        return clazz;
    }

    static void checkNotPrimitive(Type type) {
        $Gson$Preconditions.checkArgument(!(type instanceof Class) || !((Class)type).isPrimitive());
    }

    private static final class WildcardTypeImpl
    implements WildcardType,
    Serializable {
        private final Type upperBound;
        private final Type lowerBound;
        private static final long serialVersionUID = 0L;

        public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
            $Gson$Preconditions.checkArgument(lowerBounds.length <= 1);
            $Gson$Preconditions.checkArgument(upperBounds.length == 1);
            if (lowerBounds.length != 1) {
                $Gson$Preconditions.checkNotNull(upperBounds[0]);
                $Gson$Types.checkNotPrimitive(upperBounds[0]);
                this.lowerBound = null;
                this.upperBound = $Gson$Types.canonicalize(upperBounds[0]);
                return;
            }
            $Gson$Preconditions.checkNotNull(lowerBounds[0]);
            $Gson$Types.checkNotPrimitive(lowerBounds[0]);
            $Gson$Preconditions.checkArgument(upperBounds[0] == Object.class);
            this.lowerBound = $Gson$Types.canonicalize(lowerBounds[0]);
            this.upperBound = Object.class;
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
                return typeArray;
            }
            typeArray = EMPTY_TYPE_ARRAY;
            return typeArray;
        }

        public boolean equals(Object other) {
            if (!(other instanceof WildcardType)) return false;
            if (!$Gson$Types.equals(this, (WildcardType)other)) return false;
            return true;
        }

        public int hashCode() {
            int n;
            if (this.lowerBound != null) {
                n = 31 + this.lowerBound.hashCode();
                return n ^ 31 + this.upperBound.hashCode();
            }
            n = 1;
            return n ^ 31 + this.upperBound.hashCode();
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + $Gson$Types.typeToString(this.lowerBound);
            }
            if (this.upperBound != Object.class) return "? extends " + $Gson$Types.typeToString(this.upperBound);
            return "?";
        }
    }

    private static final class GenericArrayTypeImpl
    implements GenericArrayType,
    Serializable {
        private final Type componentType;
        private static final long serialVersionUID = 0L;

        public GenericArrayTypeImpl(Type componentType) {
            this.componentType = $Gson$Types.canonicalize(componentType);
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public boolean equals(Object o) {
            if (!(o instanceof GenericArrayType)) return false;
            if (!$Gson$Types.equals(this, (GenericArrayType)o)) return false;
            return true;
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

        public ParameterizedTypeImpl(Type ownerType, Type rawType, Type ... typeArguments) {
            if (rawType instanceof Class) {
                Class rawTypeAsClass = (Class)rawType;
                boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers()) || rawTypeAsClass.getEnclosingClass() == null;
                $Gson$Preconditions.checkArgument(ownerType != null || isStaticOrTopLevelClass);
            }
            this.ownerType = ownerType == null ? null : $Gson$Types.canonicalize(ownerType);
            this.rawType = $Gson$Types.canonicalize(rawType);
            this.typeArguments = (Type[])typeArguments.clone();
            int t = 0;
            int length = this.typeArguments.length;
            while (t < length) {
                $Gson$Preconditions.checkNotNull(this.typeArguments[t]);
                $Gson$Types.checkNotPrimitive(this.typeArguments[t]);
                this.typeArguments[t] = $Gson$Types.canonicalize(this.typeArguments[t]);
                ++t;
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

        public boolean equals(Object other) {
            if (!(other instanceof ParameterizedType)) return false;
            if (!$Gson$Types.equals(this, (ParameterizedType)other)) return false;
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            int length = this.typeArguments.length;
            if (length == 0) {
                return $Gson$Types.typeToString(this.rawType);
            }
            StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
            stringBuilder.append($Gson$Types.typeToString(this.rawType)).append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
            int i = 1;
            while (i < length) {
                stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i]));
                ++i;
            }
            return stringBuilder.append(">").toString();
        }
    }
}

