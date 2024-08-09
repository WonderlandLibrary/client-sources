/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeCapture;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeResolver;
import com.google.common.reflect.TypeVisitor;
import com.google.common.reflect.Types;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public abstract class TypeToken<T>
extends TypeCapture<T>
implements Serializable {
    private final Type runtimeType;
    private transient TypeResolver typeResolver;

    protected TypeToken() {
        this.runtimeType = this.capture();
        Preconditions.checkState(!(this.runtimeType instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", (Object)this.runtimeType);
    }

    protected TypeToken(Class<?> clazz) {
        Type type = super.capture();
        this.runtimeType = type instanceof Class ? type : TypeToken.of(clazz).resolveType((Type)type).runtimeType;
    }

    private TypeToken(Type type) {
        this.runtimeType = Preconditions.checkNotNull(type);
    }

    public static <T> TypeToken<T> of(Class<T> clazz) {
        return new SimpleTypeToken((Type)clazz);
    }

    public static TypeToken<?> of(Type type) {
        return new SimpleTypeToken(type);
    }

    public final Class<? super T> getRawType() {
        Class clazz;
        Class clazz2 = clazz = (Class)this.getRawTypes().iterator().next();
        return clazz2;
    }

    public final Type getType() {
        return this.runtimeType;
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, TypeToken<X> typeToken) {
        TypeResolver typeResolver = new TypeResolver().where(ImmutableMap.of(new TypeResolver.TypeVariableKey(typeParameter.typeVariable), typeToken.runtimeType));
        return new SimpleTypeToken(typeResolver.resolveType(this.runtimeType));
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, Class<X> clazz) {
        return this.where(typeParameter, TypeToken.of(clazz));
    }

    public final TypeToken<?> resolveType(Type type) {
        Preconditions.checkNotNull(type);
        TypeResolver typeResolver = this.typeResolver;
        if (typeResolver == null) {
            typeResolver = this.typeResolver = TypeResolver.accordingTo(this.runtimeType);
        }
        return TypeToken.of(typeResolver.resolveType(type));
    }

    private Type[] resolveInPlace(Type[] typeArray) {
        for (int i = 0; i < typeArray.length; ++i) {
            typeArray[i] = this.resolveType(typeArray[i]).getType();
        }
        return typeArray;
    }

    private TypeToken<?> resolveSupertype(Type type) {
        TypeToken<?> typeToken = this.resolveType(type);
        typeToken.typeResolver = this.typeResolver;
        return typeToken;
    }

    @Nullable
    final TypeToken<? super T> getGenericSuperclass() {
        if (this.runtimeType instanceof TypeVariable) {
            return this.boundAsSuperclass(((TypeVariable)this.runtimeType).getBounds()[0]);
        }
        if (this.runtimeType instanceof WildcardType) {
            return this.boundAsSuperclass(((WildcardType)this.runtimeType).getUpperBounds()[0]);
        }
        Type type = this.getRawType().getGenericSuperclass();
        if (type == null) {
            return null;
        }
        TypeToken<?> typeToken = this.resolveSupertype(type);
        return typeToken;
    }

    @Nullable
    private TypeToken<? super T> boundAsSuperclass(Type type) {
        TypeToken<?> typeToken = TypeToken.of(type);
        if (typeToken.getRawType().isInterface()) {
            return null;
        }
        TypeToken<?> typeToken2 = typeToken;
        return typeToken2;
    }

    final ImmutableList<TypeToken<? super T>> getGenericInterfaces() {
        if (this.runtimeType instanceof TypeVariable) {
            return this.boundsAsInterfaces(((TypeVariable)this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return this.boundsAsInterfaces(((WildcardType)this.runtimeType).getUpperBounds());
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Type type : this.getRawType().getGenericInterfaces()) {
            TypeToken<?> typeToken = this.resolveSupertype(type);
            builder.add(typeToken);
        }
        return builder.build();
    }

    private ImmutableList<TypeToken<? super T>> boundsAsInterfaces(Type[] typeArray) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Type type : typeArray) {
            TypeToken<?> typeToken = TypeToken.of(type);
            if (!typeToken.getRawType().isInterface()) continue;
            builder.add(typeToken);
        }
        return builder.build();
    }

    public final TypeSet getTypes() {
        return new TypeSet(this);
    }

    public final TypeToken<? super T> getSupertype(Class<? super T> clazz) {
        Preconditions.checkArgument(this.someRawTypeIsSubclassOf(clazz), "%s is not a super class of %s", clazz, (Object)this);
        if (this.runtimeType instanceof TypeVariable) {
            return this.getSupertypeFromUpperBounds(clazz, ((TypeVariable)this.runtimeType).getBounds());
        }
        if (this.runtimeType instanceof WildcardType) {
            return this.getSupertypeFromUpperBounds(clazz, ((WildcardType)this.runtimeType).getUpperBounds());
        }
        if (clazz.isArray()) {
            return this.getArraySupertype(clazz);
        }
        TypeToken<?> typeToken = this.resolveSupertype(TypeToken.toGenericType(clazz).runtimeType);
        return typeToken;
    }

    public final TypeToken<? extends T> getSubtype(Class<?> clazz) {
        Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", (Object)this);
        if (this.runtimeType instanceof WildcardType) {
            return this.getSubtypeFromLowerBounds(clazz, ((WildcardType)this.runtimeType).getLowerBounds());
        }
        if (this.isArray()) {
            return this.getArraySubtype(clazz);
        }
        Preconditions.checkArgument(this.getRawType().isAssignableFrom(clazz), "%s isn't a subclass of %s", clazz, (Object)this);
        Type type = this.resolveTypeArgsForSubclass(clazz);
        TypeToken<?> typeToken = TypeToken.of(type);
        return typeToken;
    }

    public final boolean isSupertypeOf(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(this.getType());
    }

    public final boolean isSupertypeOf(Type type) {
        return TypeToken.of(type).isSubtypeOf(this.getType());
    }

    public final boolean isSubtypeOf(TypeToken<?> typeToken) {
        return this.isSubtypeOf(typeToken.getType());
    }

    public final boolean isSubtypeOf(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof WildcardType) {
            return TypeToken.any(((WildcardType)type).getLowerBounds()).isSupertypeOf(this.runtimeType);
        }
        if (this.runtimeType instanceof WildcardType) {
            return TypeToken.any(((WildcardType)this.runtimeType).getUpperBounds()).isSubtypeOf(type);
        }
        if (this.runtimeType instanceof TypeVariable) {
            return this.runtimeType.equals(type) || TypeToken.any(((TypeVariable)this.runtimeType).getBounds()).isSubtypeOf(type);
        }
        if (this.runtimeType instanceof GenericArrayType) {
            return super.isSupertypeOfArray((GenericArrayType)this.runtimeType);
        }
        if (type instanceof Class) {
            return this.someRawTypeIsSubclassOf((Class)type);
        }
        if (type instanceof ParameterizedType) {
            return this.isSubtypeOfParameterizedType((ParameterizedType)type);
        }
        if (type instanceof GenericArrayType) {
            return this.isSubtypeOfArrayType((GenericArrayType)type);
        }
        return true;
    }

    public final boolean isArray() {
        return this.getComponentType() != null;
    }

    public final boolean isPrimitive() {
        return this.runtimeType instanceof Class && ((Class)this.runtimeType).isPrimitive();
    }

    public final TypeToken<T> wrap() {
        if (this.isPrimitive()) {
            Class clazz = (Class)this.runtimeType;
            return TypeToken.of(Primitives.wrap(clazz));
        }
        return this;
    }

    private boolean isWrapper() {
        return Primitives.allWrapperTypes().contains(this.runtimeType);
    }

    public final TypeToken<T> unwrap() {
        if (this.isWrapper()) {
            Class clazz = (Class)this.runtimeType;
            return TypeToken.of(Primitives.unwrap(clazz));
        }
        return this;
    }

    @Nullable
    public final TypeToken<?> getComponentType() {
        Type type = Types.getComponentType(this.runtimeType);
        if (type == null) {
            return null;
        }
        return TypeToken.of(type);
    }

    public final Invokable<T, Object> method(Method method) {
        Preconditions.checkArgument(this.someRawTypeIsSubclassOf(method.getDeclaringClass()), "%s not declared by %s", (Object)method, (Object)this);
        return new Invokable.MethodInvokable<T>(this, method){
            final TypeToken this$0;
            {
                this.this$0 = typeToken;
                super(method);
            }

            @Override
            Type getGenericReturnType() {
                return this.this$0.resolveType(super.getGenericReturnType()).getType();
            }

            @Override
            Type[] getGenericParameterTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericParameterTypes());
            }

            @Override
            Type[] getGenericExceptionTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericExceptionTypes());
            }

            @Override
            public TypeToken<T> getOwnerType() {
                return this.this$0;
            }

            @Override
            public String toString() {
                return this.getOwnerType() + "." + super.toString();
            }
        };
    }

    public final Invokable<T, T> constructor(Constructor<?> constructor) {
        Preconditions.checkArgument(constructor.getDeclaringClass() == this.getRawType(), "%s not declared by %s", constructor, this.getRawType());
        return new Invokable.ConstructorInvokable<T>(this, constructor){
            final TypeToken this$0;
            {
                this.this$0 = typeToken;
                super(constructor);
            }

            @Override
            Type getGenericReturnType() {
                return this.this$0.resolveType(super.getGenericReturnType()).getType();
            }

            @Override
            Type[] getGenericParameterTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericParameterTypes());
            }

            @Override
            Type[] getGenericExceptionTypes() {
                return TypeToken.access$000(this.this$0, super.getGenericExceptionTypes());
            }

            @Override
            public TypeToken<T> getOwnerType() {
                return this.this$0;
            }

            @Override
            public String toString() {
                return this.getOwnerType() + "(" + Joiner.on(", ").join(this.getGenericParameterTypes()) + ")";
            }
        };
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof TypeToken) {
            TypeToken typeToken = (TypeToken)object;
            return this.runtimeType.equals(typeToken.runtimeType);
        }
        return true;
    }

    public int hashCode() {
        return this.runtimeType.hashCode();
    }

    public String toString() {
        return Types.toString(this.runtimeType);
    }

    protected Object writeReplace() {
        return TypeToken.of(new TypeResolver().resolveType(this.runtimeType));
    }

    @CanIgnoreReturnValue
    final TypeToken<T> rejectTypeVariables() {
        new TypeVisitor(this){
            final TypeToken this$0;
            {
                this.this$0 = typeToken;
            }

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                throw new IllegalArgumentException(TypeToken.access$400(this.this$0) + "contains a type variable and is not safe for the operation");
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                this.visit(wildcardType.getLowerBounds());
                this.visit(wildcardType.getUpperBounds());
            }

            @Override
            void visitParameterizedType(ParameterizedType parameterizedType) {
                this.visit(parameterizedType.getActualTypeArguments());
                this.visit(parameterizedType.getOwnerType());
            }

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                this.visit(genericArrayType.getGenericComponentType());
            }
        }.visit(this.runtimeType);
        return this;
    }

    private boolean someRawTypeIsSubclassOf(Class<?> clazz) {
        for (Class clazz2 : this.getRawTypes()) {
            if (!clazz.isAssignableFrom(clazz2)) continue;
            return false;
        }
        return true;
    }

    private boolean isSubtypeOfParameterizedType(ParameterizedType parameterizedType) {
        Class<?> clazz = TypeToken.of(parameterizedType).getRawType();
        if (!this.someRawTypeIsSubclassOf(clazz)) {
            return true;
        }
        TypeVariable<Class<?>>[] typeVariableArray = clazz.getTypeParameters();
        Type[] typeArray = parameterizedType.getActualTypeArguments();
        for (int i = 0; i < typeVariableArray.length; ++i) {
            if (super.is(typeArray[i])) continue;
            return true;
        }
        return Modifier.isStatic(((Class)parameterizedType.getRawType()).getModifiers()) || parameterizedType.getOwnerType() == null || this.isOwnedBySubtypeOf(parameterizedType.getOwnerType());
    }

    private boolean isSubtypeOfArrayType(GenericArrayType genericArrayType) {
        if (this.runtimeType instanceof Class) {
            Class clazz = (Class)this.runtimeType;
            if (!clazz.isArray()) {
                return true;
            }
            return TypeToken.of(clazz.getComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
        }
        if (this.runtimeType instanceof GenericArrayType) {
            GenericArrayType genericArrayType2 = (GenericArrayType)this.runtimeType;
            return TypeToken.of(genericArrayType2.getGenericComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
        }
        return true;
    }

    private boolean isSupertypeOfArray(GenericArrayType genericArrayType) {
        if (this.runtimeType instanceof Class) {
            Class clazz = (Class)this.runtimeType;
            if (!clazz.isArray()) {
                return clazz.isAssignableFrom(Object[].class);
            }
            return TypeToken.of(genericArrayType.getGenericComponentType()).isSubtypeOf(clazz.getComponentType());
        }
        if (this.runtimeType instanceof GenericArrayType) {
            return TypeToken.of(genericArrayType.getGenericComponentType()).isSubtypeOf(((GenericArrayType)this.runtimeType).getGenericComponentType());
        }
        return true;
    }

    private boolean is(Type type) {
        if (this.runtimeType.equals(type)) {
            return false;
        }
        if (type instanceof WildcardType) {
            return TypeToken.every(((WildcardType)type).getUpperBounds()).isSupertypeOf(this.runtimeType) && TypeToken.every(((WildcardType)type).getLowerBounds()).isSubtypeOf(this.runtimeType);
        }
        return true;
    }

    private static Bounds every(Type[] typeArray) {
        return new Bounds(typeArray, false);
    }

    private static Bounds any(Type[] typeArray) {
        return new Bounds(typeArray, true);
    }

    private ImmutableSet<Class<? super T>> getRawTypes() {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        new TypeVisitor(this, builder){
            final ImmutableSet.Builder val$builder;
            final TypeToken this$0;
            {
                this.this$0 = typeToken;
                this.val$builder = builder;
            }

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                this.visit(typeVariable.getBounds());
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                this.visit(wildcardType.getUpperBounds());
            }

            @Override
            void visitParameterizedType(ParameterizedType parameterizedType) {
                this.val$builder.add((Class)parameterizedType.getRawType());
            }

            @Override
            void visitClass(Class<?> clazz) {
                this.val$builder.add(clazz);
            }

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                this.val$builder.add(Types.getArrayClass(TypeToken.of(genericArrayType.getGenericComponentType()).getRawType()));
            }
        }.visit(this.runtimeType);
        ImmutableCollection immutableCollection = builder.build();
        return immutableCollection;
    }

    private boolean isOwnedBySubtypeOf(Type type) {
        for (TypeToken typeToken : this.getTypes()) {
            Type type2 = typeToken.getOwnerTypeIfPresent();
            if (type2 == null || !TypeToken.of(type2).isSubtypeOf(type)) continue;
            return false;
        }
        return true;
    }

    @Nullable
    private Type getOwnerTypeIfPresent() {
        if (this.runtimeType instanceof ParameterizedType) {
            return ((ParameterizedType)this.runtimeType).getOwnerType();
        }
        if (this.runtimeType instanceof Class) {
            return ((Class)this.runtimeType).getEnclosingClass();
        }
        return null;
    }

    @VisibleForTesting
    static <T> TypeToken<? extends T> toGenericType(Class<T> clazz) {
        Type type;
        if (clazz.isArray()) {
            Type type2 = Types.newArrayType(TypeToken.toGenericType(clazz.getComponentType()).runtimeType);
            TypeToken<?> typeToken = TypeToken.of(type2);
            return typeToken;
        }
        Type[] typeArray = clazz.getTypeParameters();
        Type type3 = type = clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers()) ? TypeToken.toGenericType(clazz.getEnclosingClass()).runtimeType : null;
        if (typeArray.length > 0 || type != null && type != clazz.getEnclosingClass()) {
            TypeToken<?> typeToken = TypeToken.of(Types.newParameterizedTypeWithOwner(type, clazz, typeArray));
            return typeToken;
        }
        return TypeToken.of(clazz);
    }

    private TypeToken<? super T> getSupertypeFromUpperBounds(Class<? super T> clazz, Type[] typeArray) {
        for (Type type : typeArray) {
            TypeToken<?> typeToken = TypeToken.of(type);
            if (!typeToken.isSubtypeOf(clazz)) continue;
            TypeToken<? super T> typeToken2 = typeToken.getSupertype(clazz);
            return typeToken2;
        }
        throw new IllegalArgumentException(clazz + " isn't a super type of " + this);
    }

    private TypeToken<? extends T> getSubtypeFromLowerBounds(Class<?> clazz, Type[] typeArray) {
        int n = 0;
        Type[] typeArray2 = typeArray;
        int n2 = typeArray2.length;
        if (n < n2) {
            Type type = typeArray2[n];
            TypeToken<?> typeToken = TypeToken.of(type);
            return typeToken.getSubtype(clazz);
        }
        throw new IllegalArgumentException(clazz + " isn't a subclass of " + this);
    }

    private TypeToken<? super T> getArraySupertype(Class<? super T> clazz) {
        TypeToken<?> typeToken = Preconditions.checkNotNull(this.getComponentType(), "%s isn't a super type of %s", clazz, (Object)this);
        TypeToken<?> typeToken2 = typeToken.getSupertype(clazz.getComponentType());
        TypeToken<?> typeToken3 = TypeToken.of(TypeToken.newArrayClassOrGenericArrayType(typeToken2.runtimeType));
        return typeToken3;
    }

    private TypeToken<? extends T> getArraySubtype(Class<?> clazz) {
        TypeToken<?> typeToken = this.getComponentType().getSubtype(clazz.getComponentType());
        TypeToken<?> typeToken2 = TypeToken.of(TypeToken.newArrayClassOrGenericArrayType(typeToken.runtimeType));
        return typeToken2;
    }

    private Type resolveTypeArgsForSubclass(Class<?> clazz) {
        if (this.runtimeType instanceof Class && (clazz.getTypeParameters().length == 0 || this.getRawType().getTypeParameters().length != 0)) {
            return clazz;
        }
        TypeToken<?> typeToken = TypeToken.toGenericType(clazz);
        Type type = typeToken.getSupertype(this.getRawType()).runtimeType;
        return new TypeResolver().where(type, this.runtimeType).resolveType(typeToken.runtimeType);
    }

    private static Type newArrayClassOrGenericArrayType(Type type) {
        return Types.JavaVersion.JAVA7.newArrayType(type);
    }

    static Type[] access$000(TypeToken typeToken, Type[] typeArray) {
        return typeToken.resolveInPlace(typeArray);
    }

    static ImmutableSet access$200(TypeToken typeToken) {
        return typeToken.getRawTypes();
    }

    static Type access$400(TypeToken typeToken) {
        return typeToken.runtimeType;
    }

    TypeToken(Type type, 1 var2_2) {
        this(type);
    }

    private static abstract class TypeCollector<K> {
        static final TypeCollector<TypeToken<?>> FOR_GENERIC_TYPE = new TypeCollector<TypeToken<?>>(){

            @Override
            Class<?> getRawType(TypeToken<?> typeToken) {
                return typeToken.getRawType();
            }

            @Override
            Iterable<? extends TypeToken<?>> getInterfaces(TypeToken<?> typeToken) {
                return typeToken.getGenericInterfaces();
            }

            @Override
            @Nullable
            TypeToken<?> getSuperclass(TypeToken<?> typeToken) {
                return typeToken.getGenericSuperclass();
            }

            @Override
            @Nullable
            Object getSuperclass(Object object) {
                return this.getSuperclass((TypeToken)object);
            }

            @Override
            Iterable getInterfaces(Object object) {
                return this.getInterfaces((TypeToken)object);
            }

            @Override
            Class getRawType(Object object) {
                return this.getRawType((TypeToken)object);
            }
        };
        static final TypeCollector<Class<?>> FOR_RAW_TYPE = new TypeCollector<Class<?>>(){

            @Override
            Class<?> getRawType(Class<?> clazz) {
                return clazz;
            }

            @Override
            Iterable<? extends Class<?>> getInterfaces(Class<?> clazz) {
                return Arrays.asList(clazz.getInterfaces());
            }

            @Override
            @Nullable
            Class<?> getSuperclass(Class<?> clazz) {
                return clazz.getSuperclass();
            }

            @Override
            @Nullable
            Object getSuperclass(Object object) {
                return this.getSuperclass((Class)object);
            }

            @Override
            Iterable getInterfaces(Object object) {
                return this.getInterfaces((Class)object);
            }

            @Override
            Class getRawType(Object object) {
                return this.getRawType((Class)object);
            }
        };

        private TypeCollector() {
        }

        final TypeCollector<K> classesOnly() {
            return new ForwardingTypeCollector<K>(this, this){
                final TypeCollector this$0;
                {
                    this.this$0 = typeCollector;
                    super(typeCollector2);
                }

                @Override
                Iterable<? extends K> getInterfaces(K k) {
                    return ImmutableSet.of();
                }

                @Override
                ImmutableList<K> collectTypes(Iterable<? extends K> iterable) {
                    ImmutableList.Builder builder = ImmutableList.builder();
                    for (Object k : iterable) {
                        if (this.getRawType(k).isInterface()) continue;
                        builder.add(k);
                    }
                    return super.collectTypes(builder.build());
                }
            };
        }

        final ImmutableList<K> collectTypes(K k) {
            return this.collectTypes((Iterable<? extends K>)ImmutableList.of(k));
        }

        ImmutableList<K> collectTypes(Iterable<? extends K> iterable) {
            HashMap hashMap = Maps.newHashMap();
            for (K k : iterable) {
                this.collectTypes(k, hashMap);
            }
            return TypeCollector.sortKeysByValue(hashMap, Ordering.natural().reverse());
        }

        @CanIgnoreReturnValue
        private int collectTypes(K k, Map<? super K, Integer> map) {
            Integer n = map.get(k);
            if (n != null) {
                return n;
            }
            int n2 = this.getRawType(k).isInterface() ? 1 : 0;
            for (K k2 : this.getInterfaces(k)) {
                n2 = Math.max(n2, this.collectTypes(k2, map));
            }
            Iterator<K> iterator2 = this.getSuperclass(k);
            if (iterator2 != null) {
                n2 = Math.max(n2, this.collectTypes(iterator2, map));
            }
            map.put(k, n2 + 1);
            return n2 + 1;
        }

        private static <K, V> ImmutableList<K> sortKeysByValue(Map<K, V> map, Comparator<? super V> comparator) {
            Ordering ordering = new Ordering<K>(comparator, map){
                final Comparator val$valueComparator;
                final Map val$map;
                {
                    this.val$valueComparator = comparator;
                    this.val$map = map;
                }

                @Override
                public int compare(K k, K k2) {
                    return this.val$valueComparator.compare(this.val$map.get(k), this.val$map.get(k2));
                }
            };
            return ordering.immutableSortedCopy(map.keySet());
        }

        abstract Class<?> getRawType(K var1);

        abstract Iterable<? extends K> getInterfaces(K var1);

        @Nullable
        abstract K getSuperclass(K var1);

        TypeCollector(1 var1_1) {
            this();
        }

        private static class ForwardingTypeCollector<K>
        extends TypeCollector<K> {
            private final TypeCollector<K> delegate;

            ForwardingTypeCollector(TypeCollector<K> typeCollector) {
                super(null);
                this.delegate = typeCollector;
            }

            @Override
            Class<?> getRawType(K k) {
                return this.delegate.getRawType(k);
            }

            @Override
            Iterable<? extends K> getInterfaces(K k) {
                return this.delegate.getInterfaces(k);
            }

            @Override
            K getSuperclass(K k) {
                return this.delegate.getSuperclass(k);
            }
        }
    }

    private static final class SimpleTypeToken<T>
    extends TypeToken<T> {
        private static final long serialVersionUID = 0L;

        SimpleTypeToken(Type type) {
            super(type, null);
        }
    }

    private static class Bounds {
        private final Type[] bounds;
        private final boolean target;

        Bounds(Type[] typeArray, boolean bl) {
            this.bounds = typeArray;
            this.target = bl;
        }

        boolean isSubtypeOf(Type type) {
            for (Type type2 : this.bounds) {
                if (TypeToken.of(type2).isSubtypeOf(type) != this.target) continue;
                return this.target;
            }
            return !this.target;
        }

        boolean isSupertypeOf(Type type) {
            TypeToken<?> typeToken = TypeToken.of(type);
            for (Type type2 : this.bounds) {
                if (typeToken.isSubtypeOf(type2) != this.target) continue;
                return this.target;
            }
            return !this.target;
        }
    }

    private static enum TypeFilter implements Predicate<TypeToken<?>>
    {
        IGNORE_TYPE_VARIABLE_OR_WILDCARD{

            @Override
            public boolean apply(TypeToken<?> typeToken) {
                return !(TypeToken.access$400(typeToken) instanceof TypeVariable) && !(TypeToken.access$400(typeToken) instanceof WildcardType);
            }

            @Override
            public boolean apply(Object object) {
                return this.apply((TypeToken)object);
            }
        }
        ,
        INTERFACE_ONLY{

            @Override
            public boolean apply(TypeToken<?> typeToken) {
                return typeToken.getRawType().isInterface();
            }

            @Override
            public boolean apply(Object object) {
                return this.apply((TypeToken)object);
            }
        };


        private TypeFilter() {
        }

        TypeFilter(1 var3_3) {
            this();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ClassSet
    extends TypeSet {
        private transient ImmutableSet<TypeToken<? super T>> classes;
        private static final long serialVersionUID = 0L;
        final TypeToken this$0;

        private ClassSet(TypeToken typeToken) {
            this.this$0 = typeToken;
            super(typeToken);
        }

        @Override
        protected Set<TypeToken<? super T>> delegate() {
            ImmutableSet immutableSet = this.classes;
            if (immutableSet == null) {
                ImmutableList<TypeToken> immutableList = TypeCollector.FOR_GENERIC_TYPE.classesOnly().collectTypes(this.this$0);
                this.classes = FluentIterable.from(immutableList).filter(TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
                return this.classes;
            }
            return immutableSet;
        }

        @Override
        public TypeSet classes() {
            return this;
        }

        @Override
        public Set<Class<? super T>> rawTypes() {
            ImmutableList<Class<?>> immutableList = TypeCollector.FOR_RAW_TYPE.classesOnly().collectTypes(TypeToken.access$200(this.this$0));
            return ImmutableSet.copyOf(immutableList);
        }

        @Override
        public TypeSet interfaces() {
            throw new UnsupportedOperationException("classes().interfaces() not supported.");
        }

        private Object readResolve() {
            return this.this$0.getTypes().classes();
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }

        ClassSet(TypeToken typeToken, 1 var2_2) {
            this(typeToken);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class InterfaceSet
    extends TypeSet {
        private final transient TypeSet allTypes;
        private transient ImmutableSet<TypeToken<? super T>> interfaces;
        private static final long serialVersionUID = 0L;
        final TypeToken this$0;

        InterfaceSet(TypeToken typeToken, TypeSet typeSet) {
            this.this$0 = typeToken;
            super(typeToken);
            this.allTypes = typeSet;
        }

        @Override
        protected Set<TypeToken<? super T>> delegate() {
            ImmutableSet immutableSet = this.interfaces;
            if (immutableSet == null) {
                this.interfaces = FluentIterable.from(this.allTypes).filter(TypeFilter.INTERFACE_ONLY).toSet();
                return this.interfaces;
            }
            return immutableSet;
        }

        @Override
        public TypeSet interfaces() {
            return this;
        }

        @Override
        public Set<Class<? super T>> rawTypes() {
            ImmutableList<Class<?>> immutableList = TypeCollector.FOR_RAW_TYPE.collectTypes(TypeToken.access$200(this.this$0));
            return FluentIterable.from(immutableList).filter(new Predicate<Class<?>>(this){
                final InterfaceSet this$1;
                {
                    this.this$1 = interfaceSet;
                }

                @Override
                public boolean apply(Class<?> clazz) {
                    return clazz.isInterface();
                }

                @Override
                public boolean apply(Object object) {
                    return this.apply((Class)object);
                }
            }).toSet();
        }

        @Override
        public TypeSet classes() {
            throw new UnsupportedOperationException("interfaces().classes() not supported.");
        }

        private Object readResolve() {
            return this.this$0.getTypes().interfaces();
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public class TypeSet
    extends ForwardingSet<TypeToken<? super T>>
    implements Serializable {
        private transient ImmutableSet<TypeToken<? super T>> types;
        private static final long serialVersionUID = 0L;
        final TypeToken this$0;

        TypeSet(TypeToken typeToken) {
            this.this$0 = typeToken;
        }

        public TypeSet interfaces() {
            return new InterfaceSet(this.this$0, this);
        }

        public TypeSet classes() {
            return new ClassSet(this.this$0, null);
        }

        @Override
        protected Set<TypeToken<? super T>> delegate() {
            ImmutableSet immutableSet = this.types;
            if (immutableSet == null) {
                ImmutableList<TypeToken> immutableList = TypeCollector.FOR_GENERIC_TYPE.collectTypes(this.this$0);
                this.types = FluentIterable.from(immutableList).filter(TypeFilter.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
                return this.types;
            }
            return immutableSet;
        }

        public Set<Class<? super T>> rawTypes() {
            ImmutableList<Class<?>> immutableList = TypeCollector.FOR_RAW_TYPE.collectTypes(TypeToken.access$200(this.this$0));
            return ImmutableSet.copyOf(immutableList);
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }
}

