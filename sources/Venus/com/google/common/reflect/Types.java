/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeCapture;
import com.google.common.reflect.TypeVisitor;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

final class Types {
    private static final Function<Type, String> TYPE_NAME = new Function<Type, String>(){

        @Override
        public String apply(Type type) {
            return JavaVersion.CURRENT.typeName(type);
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Type)object);
        }
    };
    private static final Joiner COMMA_JOINER = Joiner.on(", ").useForNull("null");

    static Type newArrayType(Type type) {
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType)type;
            Type[] typeArray = wildcardType.getLowerBounds();
            Preconditions.checkArgument(typeArray.length <= 1, "Wildcard cannot have more than one lower bounds.");
            if (typeArray.length == 1) {
                return Types.supertypeOf(Types.newArrayType(typeArray[0]));
            }
            Type[] typeArray2 = wildcardType.getUpperBounds();
            Preconditions.checkArgument(typeArray2.length == 1, "Wildcard should have only one upper bound.");
            return Types.subtypeOf(Types.newArrayType(typeArray2[0]));
        }
        return JavaVersion.CURRENT.newArrayType(type);
    }

    static ParameterizedType newParameterizedTypeWithOwner(@Nullable Type type, Class<?> clazz, Type ... typeArray) {
        if (type == null) {
            return Types.newParameterizedType(clazz, typeArray);
        }
        Preconditions.checkNotNull(typeArray);
        Preconditions.checkArgument(clazz.getEnclosingClass() != null, "Owner type for unenclosed %s", clazz);
        return new ParameterizedTypeImpl(type, clazz, typeArray);
    }

    static ParameterizedType newParameterizedType(Class<?> clazz, Type ... typeArray) {
        return new ParameterizedTypeImpl(ClassOwnership.JVM_BEHAVIOR.getOwnerType(clazz), clazz, typeArray);
    }

    static <D extends GenericDeclaration> TypeVariable<D> newArtificialTypeVariable(D d, String string, Type ... typeArray) {
        Type[] typeArray2;
        if (typeArray.length == 0) {
            Type[] typeArray3 = new Type[1];
            typeArray2 = typeArray3;
            typeArray3[0] = Object.class;
        } else {
            typeArray2 = typeArray;
        }
        return Types.newTypeVariableImpl(d, string, typeArray2);
    }

    @VisibleForTesting
    static WildcardType subtypeOf(Type type) {
        return new WildcardTypeImpl(new Type[0], new Type[]{type});
    }

    @VisibleForTesting
    static WildcardType supertypeOf(Type type) {
        return new WildcardTypeImpl(new Type[]{type}, new Type[]{Object.class});
    }

    static String toString(Type type) {
        return type instanceof Class ? ((Class)type).getName() : type.toString();
    }

    @Nullable
    static Type getComponentType(Type type) {
        Preconditions.checkNotNull(type);
        AtomicReference atomicReference = new AtomicReference();
        new TypeVisitor(atomicReference){
            final AtomicReference val$result;
            {
                this.val$result = atomicReference;
            }

            @Override
            void visitTypeVariable(TypeVariable<?> typeVariable) {
                this.val$result.set(Types.access$100(typeVariable.getBounds()));
            }

            @Override
            void visitWildcardType(WildcardType wildcardType) {
                this.val$result.set(Types.access$100(wildcardType.getUpperBounds()));
            }

            @Override
            void visitGenericArrayType(GenericArrayType genericArrayType) {
                this.val$result.set(genericArrayType.getGenericComponentType());
            }

            @Override
            void visitClass(Class<?> clazz) {
                this.val$result.set(clazz.getComponentType());
            }
        }.visit(type);
        return (Type)atomicReference.get();
    }

    @Nullable
    private static Type subtypeOfComponentType(Type[] typeArray) {
        for (Type type : typeArray) {
            Class clazz;
            Type type2 = Types.getComponentType(type);
            if (type2 == null) continue;
            if (type2 instanceof Class && (clazz = (Class)type2).isPrimitive()) {
                return clazz;
            }
            return Types.subtypeOf(type2);
        }
        return null;
    }

    private static <D extends GenericDeclaration> TypeVariable<D> newTypeVariableImpl(D d, String string, Type[] typeArray) {
        TypeVariableImpl<D> typeVariableImpl = new TypeVariableImpl<D>(d, string, typeArray);
        TypeVariable typeVariable = Reflection.newProxy(TypeVariable.class, new TypeVariableInvocationHandler(typeVariableImpl));
        return typeVariable;
    }

    private static Type[] toArray(Collection<Type> collection) {
        return collection.toArray(new Type[collection.size()]);
    }

    private static Iterable<Type> filterUpperBounds(Iterable<Type> iterable) {
        return Iterables.filter(iterable, Predicates.not(Predicates.equalTo(Object.class)));
    }

    private static void disallowPrimitiveType(Type[] typeArray, String string) {
        for (Type type : typeArray) {
            if (!(type instanceof Class)) continue;
            Class clazz = (Class)type;
            Preconditions.checkArgument(!clazz.isPrimitive(), "Primitive type '%s' used as %s", (Object)clazz, (Object)string);
        }
    }

    static Class<?> getArrayClass(Class<?> clazz) {
        return Array.newInstance(clazz, 0).getClass();
    }

    private Types() {
    }

    static Type access$100(Type[] typeArray) {
        return Types.subtypeOfComponentType(typeArray);
    }

    static void access$200(Type[] typeArray, String string) {
        Types.disallowPrimitiveType(typeArray, string);
    }

    static Type[] access$300(Collection collection) {
        return Types.toArray(collection);
    }

    static Function access$400() {
        return TYPE_NAME;
    }

    static Joiner access$500() {
        return COMMA_JOINER;
    }

    static Iterable access$700(Iterable iterable) {
        return Types.filterUpperBounds(iterable);
    }

    static final class NativeTypeVariableEquals<X> {
        static final boolean NATIVE_TYPE_VARIABLE_ONLY = !NativeTypeVariableEquals.class.getTypeParameters()[0].equals(Types.newArtificialTypeVariable(NativeTypeVariableEquals.class, "X", new Type[0]));

        NativeTypeVariableEquals() {
        }
    }

    static enum JavaVersion {
        JAVA6{

            @Override
            GenericArrayType newArrayType(Type type) {
                return new GenericArrayTypeImpl(type);
            }

            @Override
            Type usedInGenericType(Type type) {
                Class clazz;
                Preconditions.checkNotNull(type);
                if (type instanceof Class && (clazz = (Class)type).isArray()) {
                    return new GenericArrayTypeImpl(clazz.getComponentType());
                }
                return type;
            }

            @Override
            Type newArrayType(Type type) {
                return this.newArrayType(type);
            }
        }
        ,
        JAVA7{

            @Override
            Type newArrayType(Type type) {
                if (type instanceof Class) {
                    return Types.getArrayClass((Class)type);
                }
                return new GenericArrayTypeImpl(type);
            }

            @Override
            Type usedInGenericType(Type type) {
                return Preconditions.checkNotNull(type);
            }
        }
        ,
        JAVA8{

            @Override
            Type newArrayType(Type type) {
                return JAVA7.newArrayType(type);
            }

            @Override
            Type usedInGenericType(Type type) {
                return JAVA7.usedInGenericType(type);
            }

            @Override
            String typeName(Type type) {
                try {
                    Method method = Type.class.getMethod("getTypeName", new Class[0]);
                    return (String)method.invoke(type, new Object[0]);
                } catch (NoSuchMethodException noSuchMethodException) {
                    throw new AssertionError((Object)"Type.getTypeName should be available in Java 8");
                } catch (InvocationTargetException invocationTargetException) {
                    throw new RuntimeException(invocationTargetException);
                } catch (IllegalAccessException illegalAccessException) {
                    throw new RuntimeException(illegalAccessException);
                }
            }
        };

        static final JavaVersion CURRENT;

        private JavaVersion() {
        }

        abstract Type newArrayType(Type var1);

        abstract Type usedInGenericType(Type var1);

        String typeName(Type type) {
            return Types.toString(type);
        }

        final ImmutableList<Type> usedInGenericType(Type[] typeArray) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (Type type : typeArray) {
                builder.add(this.usedInGenericType(type));
            }
            return builder.build();
        }

        JavaVersion(1 var3_3) {
            this();
        }

        static {
            CURRENT = AnnotatedElement.class.isAssignableFrom(TypeVariable.class) ? JAVA8 : (new TypeCapture<int[]>(){}.capture() instanceof Class ? JAVA7 : JAVA6);
        }
    }

    static final class WildcardTypeImpl
    implements WildcardType,
    Serializable {
        private final ImmutableList<Type> lowerBounds;
        private final ImmutableList<Type> upperBounds;
        private static final long serialVersionUID = 0L;

        WildcardTypeImpl(Type[] typeArray, Type[] typeArray2) {
            Types.access$200(typeArray, "lower bound for wildcard");
            Types.access$200(typeArray2, "upper bound for wildcard");
            this.lowerBounds = JavaVersion.CURRENT.usedInGenericType(typeArray);
            this.upperBounds = JavaVersion.CURRENT.usedInGenericType(typeArray2);
        }

        @Override
        public Type[] getLowerBounds() {
            return Types.access$300(this.lowerBounds);
        }

        @Override
        public Type[] getUpperBounds() {
            return Types.access$300(this.upperBounds);
        }

        public boolean equals(Object object) {
            if (object instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType)object;
                return this.lowerBounds.equals(Arrays.asList(wildcardType.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(wildcardType.getUpperBounds()));
            }
            return true;
        }

        public int hashCode() {
            return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("?");
            for (Type type : this.lowerBounds) {
                stringBuilder.append(" super ").append(JavaVersion.CURRENT.typeName(type));
            }
            for (Type type : Types.access$700(this.upperBounds)) {
                stringBuilder.append(" extends ").append(JavaVersion.CURRENT.typeName(type));
            }
            return stringBuilder.toString();
        }
    }

    private static final class TypeVariableImpl<D extends GenericDeclaration> {
        private final D genericDeclaration;
        private final String name;
        private final ImmutableList<Type> bounds;

        TypeVariableImpl(D d, String string, Type[] typeArray) {
            Types.access$200(typeArray, "bound for type variable");
            this.genericDeclaration = (GenericDeclaration)Preconditions.checkNotNull(d);
            this.name = Preconditions.checkNotNull(string);
            this.bounds = ImmutableList.copyOf(typeArray);
        }

        public Type[] getBounds() {
            return Types.access$300(this.bounds);
        }

        public D getGenericDeclaration() {
            return this.genericDeclaration;
        }

        public String getName() {
            return this.name;
        }

        public String getTypeName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }

        public int hashCode() {
            return this.genericDeclaration.hashCode() ^ this.name.hashCode();
        }

        public boolean equals(Object object) {
            if (NativeTypeVariableEquals.NATIVE_TYPE_VARIABLE_ONLY) {
                if (object != null && Proxy.isProxyClass(object.getClass()) && Proxy.getInvocationHandler(object) instanceof TypeVariableInvocationHandler) {
                    TypeVariableInvocationHandler typeVariableInvocationHandler = (TypeVariableInvocationHandler)Proxy.getInvocationHandler(object);
                    TypeVariableImpl typeVariableImpl = TypeVariableInvocationHandler.access$600(typeVariableInvocationHandler);
                    return this.name.equals(typeVariableImpl.getName()) && this.genericDeclaration.equals(typeVariableImpl.getGenericDeclaration()) && this.bounds.equals(typeVariableImpl.bounds);
                }
                return true;
            }
            if (object instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable)object;
                return this.name.equals(typeVariable.getName()) && this.genericDeclaration.equals(typeVariable.getGenericDeclaration());
            }
            return true;
        }
    }

    private static final class TypeVariableInvocationHandler
    implements InvocationHandler {
        private static final ImmutableMap<String, Method> typeVariableMethods;
        private final TypeVariableImpl<?> typeVariableImpl;

        TypeVariableInvocationHandler(TypeVariableImpl<?> typeVariableImpl) {
            this.typeVariableImpl = typeVariableImpl;
        }

        @Override
        public Object invoke(Object object, Method method, Object[] objectArray) throws Throwable {
            String string = method.getName();
            Method method2 = typeVariableMethods.get(string);
            if (method2 == null) {
                throw new UnsupportedOperationException(string);
            }
            try {
                return method2.invoke(this.typeVariableImpl, objectArray);
            } catch (InvocationTargetException invocationTargetException) {
                throw invocationTargetException.getCause();
            }
        }

        static TypeVariableImpl access$600(TypeVariableInvocationHandler typeVariableInvocationHandler) {
            return typeVariableInvocationHandler.typeVariableImpl;
        }

        static {
            ImmutableMap.Builder<String, Method> builder = ImmutableMap.builder();
            for (Method method : TypeVariableImpl.class.getMethods()) {
                if (!method.getDeclaringClass().equals(TypeVariableImpl.class)) continue;
                try {
                    method.setAccessible(false);
                } catch (AccessControlException accessControlException) {
                    // empty catch block
                }
                builder.put(method.getName(), method);
            }
            typeVariableMethods = builder.build();
        }
    }

    private static final class ParameterizedTypeImpl
    implements ParameterizedType,
    Serializable {
        private final Type ownerType;
        private final ImmutableList<Type> argumentsList;
        private final Class<?> rawType;
        private static final long serialVersionUID = 0L;

        ParameterizedTypeImpl(@Nullable Type type, Class<?> clazz, Type[] typeArray) {
            Preconditions.checkNotNull(clazz);
            Preconditions.checkArgument(typeArray.length == clazz.getTypeParameters().length);
            Types.access$200(typeArray, "type parameter");
            this.ownerType = type;
            this.rawType = clazz;
            this.argumentsList = JavaVersion.CURRENT.usedInGenericType(typeArray);
        }

        @Override
        public Type[] getActualTypeArguments() {
            return Types.access$300(this.argumentsList);
        }

        @Override
        public Type getRawType() {
            return this.rawType;
        }

        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.ownerType != null) {
                stringBuilder.append(JavaVersion.CURRENT.typeName(this.ownerType)).append('.');
            }
            return stringBuilder.append(this.rawType.getName()).append('<').append(Types.access$500().join(Iterables.transform(this.argumentsList, Types.access$400()))).append('>').toString();
        }

        public int hashCode() {
            return (this.ownerType == null ? 0 : this.ownerType.hashCode()) ^ this.argumentsList.hashCode() ^ this.rawType.hashCode();
        }

        public boolean equals(Object object) {
            if (!(object instanceof ParameterizedType)) {
                return true;
            }
            ParameterizedType parameterizedType = (ParameterizedType)object;
            return this.getRawType().equals(parameterizedType.getRawType()) && Objects.equal(this.getOwnerType(), parameterizedType.getOwnerType()) && Arrays.equals(this.getActualTypeArguments(), parameterizedType.getActualTypeArguments());
        }
    }

    private static final class GenericArrayTypeImpl
    implements GenericArrayType,
    Serializable {
        private final Type componentType;
        private static final long serialVersionUID = 0L;

        GenericArrayTypeImpl(Type type) {
            this.componentType = JavaVersion.CURRENT.usedInGenericType(type);
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public String toString() {
            return Types.toString(this.componentType) + "[]";
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public boolean equals(Object object) {
            if (object instanceof GenericArrayType) {
                GenericArrayType genericArrayType = (GenericArrayType)object;
                return Objects.equal(this.getGenericComponentType(), genericArrayType.getGenericComponentType());
            }
            return true;
        }
    }

    private static enum ClassOwnership {
        OWNED_BY_ENCLOSING_CLASS{

            @Override
            @Nullable
            Class<?> getOwnerType(Class<?> clazz) {
                return clazz.getEnclosingClass();
            }
        }
        ,
        LOCAL_CLASS_HAS_NO_OWNER{

            @Override
            @Nullable
            Class<?> getOwnerType(Class<?> clazz) {
                if (clazz.isLocalClass()) {
                    return null;
                }
                return clazz.getEnclosingClass();
            }
        };

        static final ClassOwnership JVM_BEHAVIOR;

        private ClassOwnership() {
        }

        @Nullable
        abstract Class<?> getOwnerType(Class<?> var1);

        private static ClassOwnership detectJvmBehavior() {
            class LocalClass<T> {
                LocalClass() {
                }
            }
            Class<?> clazz = new LocalClass<String>(){
                {
                }
            }.getClass();
            ParameterizedType parameterizedType = (ParameterizedType)clazz.getGenericSuperclass();
            for (ClassOwnership classOwnership : ClassOwnership.values()) {
                if (classOwnership.getOwnerType(LocalClass.class) != parameterizedType.getOwnerType()) continue;
                return classOwnership;
            }
            throw new AssertionError();
        }

        ClassOwnership(1 var3_3) {
            this();
        }

        static {
            JVM_BEHAVIOR = ClassOwnership.detectJvmBehavior();
        }
    }
}

