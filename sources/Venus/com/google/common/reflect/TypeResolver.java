/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeVisitor;
import com.google.common.reflect.Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@Beta
public final class TypeResolver {
    private final TypeTable typeTable;

    public TypeResolver() {
        this.typeTable = new TypeTable();
    }

    private TypeResolver(TypeTable typeTable) {
        this.typeTable = typeTable;
    }

    static TypeResolver accordingTo(Type type) {
        return new TypeResolver().where(TypeMappingIntrospector.getTypeMappings(type));
    }

    public TypeResolver where(Type type, Type type2) {
        HashMap<TypeVariableKey, Type> hashMap = Maps.newHashMap();
        TypeResolver.populateTypeMappings(hashMap, Preconditions.checkNotNull(type), Preconditions.checkNotNull(type2));
        return this.where(hashMap);
    }

    TypeResolver where(Map<TypeVariableKey, ? extends Type> map) {
        return new TypeResolver(this.typeTable.where(map));
    }

    private static void populateTypeMappings(Map<TypeVariableKey, Type> map, Type type, Type type2) {
        if (type.equals(type2)) {
            return;
        }
        new TypeVisitor(map, type2){
            final Map val$mappings;
            final Type val$to;
            {
                this.val$mappings = map;
                this.val$to = type;
            }

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                this.val$mappings.put(new TypeVariableKey(typeVariable), this.val$to);
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                int n;
                if (!(this.val$to instanceof WildcardType)) {
                    return;
                }
                WildcardType wildcardType2 = (WildcardType)this.val$to;
                Type[] typeArray = wildcardType.getUpperBounds();
                Type[] typeArray2 = wildcardType2.getUpperBounds();
                Type[] typeArray3 = wildcardType.getLowerBounds();
                Type[] typeArray4 = wildcardType2.getLowerBounds();
                Preconditions.checkArgument(typeArray.length == typeArray2.length && typeArray3.length == typeArray4.length, "Incompatible type: %s vs. %s", (Object)wildcardType, (Object)this.val$to);
                for (n = 0; n < typeArray.length; ++n) {
                    TypeResolver.access$000(this.val$mappings, typeArray[n], typeArray2[n]);
                }
                for (n = 0; n < typeArray3.length; ++n) {
                    TypeResolver.access$000(this.val$mappings, typeArray3[n], typeArray4[n]);
                }
            }

            @Override
            void visitParameterizedType(ParameterizedType parameterizedType) {
                if (this.val$to instanceof WildcardType) {
                    return;
                }
                ParameterizedType parameterizedType2 = (ParameterizedType)TypeResolver.access$100(ParameterizedType.class, this.val$to);
                if (parameterizedType.getOwnerType() != null && parameterizedType2.getOwnerType() != null) {
                    TypeResolver.access$000(this.val$mappings, parameterizedType.getOwnerType(), parameterizedType2.getOwnerType());
                }
                Preconditions.checkArgument(parameterizedType.getRawType().equals(parameterizedType2.getRawType()), "Inconsistent raw type: %s vs. %s", (Object)parameterizedType, (Object)this.val$to);
                Type[] typeArray = parameterizedType.getActualTypeArguments();
                Type[] typeArray2 = parameterizedType2.getActualTypeArguments();
                Preconditions.checkArgument(typeArray.length == typeArray2.length, "%s not compatible with %s", (Object)parameterizedType, (Object)parameterizedType2);
                for (int i = 0; i < typeArray.length; ++i) {
                    TypeResolver.access$000(this.val$mappings, typeArray[i], typeArray2[i]);
                }
            }

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                if (this.val$to instanceof WildcardType) {
                    return;
                }
                Type type = Types.getComponentType(this.val$to);
                Preconditions.checkArgument(type != null, "%s is not an array type.", (Object)this.val$to);
                TypeResolver.access$000(this.val$mappings, genericArrayType.getGenericComponentType(), type);
            }

            @Override
            void visitClass(Class<?> clazz) {
                if (this.val$to instanceof WildcardType) {
                    return;
                }
                throw new IllegalArgumentException("No type mapping from " + clazz + " to " + this.val$to);
            }
        }.visit(type);
    }

    public Type resolveType(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof TypeVariable) {
            return this.typeTable.resolve((TypeVariable)type);
        }
        if (type instanceof ParameterizedType) {
            return this.resolveParameterizedType((ParameterizedType)type);
        }
        if (type instanceof GenericArrayType) {
            return this.resolveGenericArrayType((GenericArrayType)type);
        }
        if (type instanceof WildcardType) {
            return this.resolveWildcardType((WildcardType)type);
        }
        return type;
    }

    private Type[] resolveTypes(Type[] typeArray) {
        Type[] typeArray2 = new Type[typeArray.length];
        for (int i = 0; i < typeArray.length; ++i) {
            typeArray2[i] = this.resolveType(typeArray[i]);
        }
        return typeArray2;
    }

    private WildcardType resolveWildcardType(WildcardType wildcardType) {
        Type[] typeArray = wildcardType.getLowerBounds();
        Type[] typeArray2 = wildcardType.getUpperBounds();
        return new Types.WildcardTypeImpl(this.resolveTypes(typeArray), this.resolveTypes(typeArray2));
    }

    private Type resolveGenericArrayType(GenericArrayType genericArrayType) {
        Type type = genericArrayType.getGenericComponentType();
        Type type2 = this.resolveType(type);
        return Types.newArrayType(type2);
    }

    private ParameterizedType resolveParameterizedType(ParameterizedType parameterizedType) {
        Type type = parameterizedType.getOwnerType();
        Type type2 = type == null ? null : this.resolveType(type);
        Type type3 = this.resolveType(parameterizedType.getRawType());
        Type[] typeArray = parameterizedType.getActualTypeArguments();
        Type[] typeArray2 = this.resolveTypes(typeArray);
        return Types.newParameterizedTypeWithOwner(type2, (Class)type3, typeArray2);
    }

    private static <T> T expectArgument(Class<T> clazz, Object object) {
        try {
            return clazz.cast(object);
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException(object + " is not a " + clazz.getSimpleName());
        }
    }

    static void access$000(Map map, Type type, Type type2) {
        TypeResolver.populateTypeMappings(map, type, type2);
    }

    static Object access$100(Class clazz, Object object) {
        return TypeResolver.expectArgument(clazz, object);
    }

    TypeResolver(TypeTable typeTable, 1 var2_2) {
        this(typeTable);
    }

    static Type[] access$300(TypeResolver typeResolver, Type[] typeArray) {
        return typeResolver.resolveTypes(typeArray);
    }

    static final class TypeVariableKey {
        private final TypeVariable<?> var;

        TypeVariableKey(TypeVariable<?> typeVariable) {
            this.var = Preconditions.checkNotNull(typeVariable);
        }

        public int hashCode() {
            return Objects.hashCode(this.var.getGenericDeclaration(), this.var.getName());
        }

        public boolean equals(Object object) {
            if (object instanceof TypeVariableKey) {
                TypeVariableKey typeVariableKey = (TypeVariableKey)object;
                return this.equalsTypeVariable(typeVariableKey.var);
            }
            return true;
        }

        public String toString() {
            return this.var.toString();
        }

        static TypeVariableKey forLookup(Type type) {
            if (type instanceof TypeVariable) {
                return new TypeVariableKey((TypeVariable)type);
            }
            return null;
        }

        boolean equalsType(Type type) {
            if (type instanceof TypeVariable) {
                return this.equalsTypeVariable((TypeVariable)type);
            }
            return true;
        }

        private boolean equalsTypeVariable(TypeVariable<?> typeVariable) {
            return this.var.getGenericDeclaration().equals(typeVariable.getGenericDeclaration()) && this.var.getName().equals(typeVariable.getName());
        }
    }

    private static final class WildcardCapturer {
        private final AtomicInteger id = new AtomicInteger();

        private WildcardCapturer() {
        }

        Type capture(Type type) {
            Preconditions.checkNotNull(type);
            if (type instanceof Class) {
                return type;
            }
            if (type instanceof TypeVariable) {
                return type;
            }
            if (type instanceof GenericArrayType) {
                GenericArrayType genericArrayType = (GenericArrayType)type;
                return Types.newArrayType(this.capture(genericArrayType.getGenericComponentType()));
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)type;
                return Types.newParameterizedTypeWithOwner(this.captureNullable(parameterizedType.getOwnerType()), (Class)parameterizedType.getRawType(), this.capture(parameterizedType.getActualTypeArguments()));
            }
            if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType)type;
                Type[] typeArray = wildcardType.getLowerBounds();
                if (typeArray.length == 0) {
                    Object[] objectArray = wildcardType.getUpperBounds();
                    String string = "capture#" + this.id.incrementAndGet() + "-of ? extends " + Joiner.on('&').join(objectArray);
                    return Types.newArtificialTypeVariable(WildcardCapturer.class, string, wildcardType.getUpperBounds());
                }
                return type;
            }
            throw new AssertionError((Object)"must have been one of the known types");
        }

        private Type captureNullable(@Nullable Type type) {
            if (type == null) {
                return null;
            }
            return this.capture(type);
        }

        private Type[] capture(Type[] typeArray) {
            Type[] typeArray2 = new Type[typeArray.length];
            for (int i = 0; i < typeArray.length; ++i) {
                typeArray2[i] = this.capture(typeArray[i]);
            }
            return typeArray2;
        }

        WildcardCapturer(1 var1_1) {
            this();
        }
    }

    private static final class TypeMappingIntrospector
    extends TypeVisitor {
        private static final WildcardCapturer wildcardCapturer = new WildcardCapturer(null);
        private final Map<TypeVariableKey, Type> mappings = Maps.newHashMap();

        private TypeMappingIntrospector() {
        }

        static ImmutableMap<TypeVariableKey, Type> getTypeMappings(Type type) {
            TypeMappingIntrospector typeMappingIntrospector = new TypeMappingIntrospector();
            typeMappingIntrospector.visit(wildcardCapturer.capture(type));
            return ImmutableMap.copyOf(typeMappingIntrospector.mappings);
        }

        @Override
        void visitClass(Class<?> clazz) {
            this.visit(clazz.getGenericSuperclass());
            this.visit(clazz.getGenericInterfaces());
        }

        @Override
        void visitParameterizedType(ParameterizedType parameterizedType) {
            Type[] typeArray;
            Class clazz = (Class)parameterizedType.getRawType();
            TypeVariable<Class<T>>[] typeVariableArray = clazz.getTypeParameters();
            Preconditions.checkState(typeVariableArray.length == (typeArray = parameterizedType.getActualTypeArguments()).length);
            for (int i = 0; i < typeVariableArray.length; ++i) {
                this.map(new TypeVariableKey(typeVariableArray[i]), typeArray[i]);
            }
            this.visit(clazz);
            this.visit(parameterizedType.getOwnerType());
        }

        @Override
        void visitTypeVariable(TypeVariable<?> typeVariable) {
            this.visit(typeVariable.getBounds());
        }

        @Override
        void visitWildcardType(WildcardType wildcardType) {
            this.visit(wildcardType.getUpperBounds());
        }

        private void map(TypeVariableKey typeVariableKey, Type type) {
            if (this.mappings.containsKey(typeVariableKey)) {
                return;
            }
            Type type2 = type;
            while (type2 != null) {
                if (typeVariableKey.equalsType(type2)) {
                    Type type3 = type;
                    while (type3 != null) {
                        type3 = this.mappings.remove(TypeVariableKey.forLookup(type3));
                    }
                    return;
                }
                type2 = this.mappings.get(TypeVariableKey.forLookup(type2));
            }
            this.mappings.put(typeVariableKey, type);
        }
    }

    private static class TypeTable {
        private final ImmutableMap<TypeVariableKey, Type> map;

        TypeTable() {
            this.map = ImmutableMap.of();
        }

        private TypeTable(ImmutableMap<TypeVariableKey, Type> immutableMap) {
            this.map = immutableMap;
        }

        final TypeTable where(Map<TypeVariableKey, ? extends Type> map) {
            ImmutableMap.Builder<TypeVariableKey, Type> builder = ImmutableMap.builder();
            builder.putAll(this.map);
            for (Map.Entry<TypeVariableKey, ? extends Type> entry : map.entrySet()) {
                Type type;
                TypeVariableKey typeVariableKey = entry.getKey();
                Preconditions.checkArgument(!typeVariableKey.equalsType(type = entry.getValue()), "Type variable %s bound to itself", (Object)typeVariableKey);
                builder.put(typeVariableKey, type);
            }
            return new TypeTable(builder.build());
        }

        final Type resolve(TypeVariable<?> typeVariable) {
            TypeTable typeTable = this;
            TypeTable typeTable2 = new TypeTable(this, typeVariable, typeTable){
                final TypeVariable val$var;
                final TypeTable val$unguarded;
                final TypeTable this$0;
                {
                    this.this$0 = typeTable;
                    this.val$var = typeVariable;
                    this.val$unguarded = typeTable2;
                }

                @Override
                public Type resolveInternal(TypeVariable<?> typeVariable, TypeTable typeTable) {
                    if (typeVariable.getGenericDeclaration().equals(this.val$var.getGenericDeclaration())) {
                        return typeVariable;
                    }
                    return this.val$unguarded.resolveInternal(typeVariable, typeTable);
                }
            };
            return this.resolveInternal(typeVariable, typeTable2);
        }

        Type resolveInternal(TypeVariable<?> typeVariable, TypeTable typeTable) {
            Type type = this.map.get(new TypeVariableKey(typeVariable));
            if (type == null) {
                Object[] objectArray = typeVariable.getBounds();
                if (objectArray.length == 0) {
                    return typeVariable;
                }
                Object[] objectArray2 = TypeResolver.access$300(new TypeResolver(typeTable, null), (Type[])objectArray);
                if (Types.NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY && Arrays.equals(objectArray, objectArray2)) {
                    return typeVariable;
                }
                return Types.newArtificialTypeVariable(typeVariable.getGenericDeclaration(), typeVariable.getName(), (Type[])objectArray2);
            }
            return new TypeResolver(typeTable, null).resolveType(type);
        }
    }
}

