/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Element;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import com.google.common.reflect.Types;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import javax.annotation.Nullable;

@Beta
public abstract class Invokable<T, R>
extends Element
implements GenericDeclaration {
    <M extends AccessibleObject> Invokable(M m) {
        super(m);
    }

    public static Invokable<?, Object> from(Method method) {
        return new MethodInvokable(method);
    }

    public static <T> Invokable<T, T> from(Constructor<T> constructor) {
        return new ConstructorInvokable(constructor);
    }

    public abstract boolean isOverridable();

    public abstract boolean isVarArgs();

    @CanIgnoreReturnValue
    public final R invoke(@Nullable T t, Object ... objectArray) throws InvocationTargetException, IllegalAccessException {
        return (R)this.invokeInternal(t, Preconditions.checkNotNull(objectArray));
    }

    public final TypeToken<? extends R> getReturnType() {
        return TypeToken.of(this.getGenericReturnType());
    }

    public final ImmutableList<Parameter> getParameters() {
        Type[] typeArray = this.getGenericParameterTypes();
        Annotation[][] annotationArray = this.getParameterAnnotations();
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < typeArray.length; ++i) {
            builder.add(new Parameter(this, i, TypeToken.of(typeArray[i]), annotationArray[i]));
        }
        return builder.build();
    }

    public final ImmutableList<TypeToken<? extends Throwable>> getExceptionTypes() {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Type type : this.getGenericExceptionTypes()) {
            TypeToken<?> typeToken = TypeToken.of(type);
            builder.add(typeToken);
        }
        return builder.build();
    }

    public final <R1 extends R> Invokable<T, R1> returning(Class<R1> clazz) {
        return this.returning(TypeToken.of(clazz));
    }

    public final <R1 extends R> Invokable<T, R1> returning(TypeToken<R1> typeToken) {
        if (!typeToken.isSupertypeOf(this.getReturnType())) {
            throw new IllegalArgumentException("Invokable is known to return " + this.getReturnType() + ", not " + typeToken);
        }
        Invokable invokable = this;
        return invokable;
    }

    public final Class<? super T> getDeclaringClass() {
        return super.getDeclaringClass();
    }

    public TypeToken<T> getOwnerType() {
        return TypeToken.of(this.getDeclaringClass());
    }

    abstract Object invokeInternal(@Nullable Object var1, Object[] var2) throws InvocationTargetException, IllegalAccessException;

    abstract Type[] getGenericParameterTypes();

    abstract Type[] getGenericExceptionTypes();

    abstract Annotation[][] getParameterAnnotations();

    abstract Type getGenericReturnType();

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    static class ConstructorInvokable<T>
    extends Invokable<T, T> {
        final Constructor<?> constructor;

        ConstructorInvokable(Constructor<?> constructor) {
            super(constructor);
            this.constructor = constructor;
        }

        @Override
        final Object invokeInternal(@Nullable Object object, Object[] objectArray) throws InvocationTargetException, IllegalAccessException {
            try {
                return this.constructor.newInstance(objectArray);
            } catch (InstantiationException instantiationException) {
                throw new RuntimeException(this.constructor + " failed.", instantiationException);
            }
        }

        @Override
        Type getGenericReturnType() {
            Class clazz = this.getDeclaringClass();
            Type[] typeArray = clazz.getTypeParameters();
            if (typeArray.length > 0) {
                return Types.newParameterizedType(clazz, typeArray);
            }
            return clazz;
        }

        @Override
        Type[] getGenericParameterTypes() {
            Class<?>[] classArray;
            Type[] typeArray = this.constructor.getGenericParameterTypes();
            if (typeArray.length > 0 && this.mayNeedHiddenThis() && typeArray.length == (classArray = this.constructor.getParameterTypes()).length && classArray[0] == this.getDeclaringClass().getEnclosingClass()) {
                return Arrays.copyOfRange(typeArray, 1, typeArray.length);
            }
            return typeArray;
        }

        @Override
        Type[] getGenericExceptionTypes() {
            return this.constructor.getGenericExceptionTypes();
        }

        @Override
        final Annotation[][] getParameterAnnotations() {
            return this.constructor.getParameterAnnotations();
        }

        @Override
        public final TypeVariable<?>[] getTypeParameters() {
            TypeVariable<Class<T>>[] typeVariableArray = this.getDeclaringClass().getTypeParameters();
            TypeVariable<Constructor<?>>[] typeVariableArray2 = this.constructor.getTypeParameters();
            TypeVariable[] typeVariableArray3 = new TypeVariable[typeVariableArray.length + typeVariableArray2.length];
            System.arraycopy(typeVariableArray, 0, typeVariableArray3, 0, typeVariableArray.length);
            System.arraycopy(typeVariableArray2, 0, typeVariableArray3, typeVariableArray.length, typeVariableArray2.length);
            return typeVariableArray3;
        }

        @Override
        public final boolean isOverridable() {
            return true;
        }

        @Override
        public final boolean isVarArgs() {
            return this.constructor.isVarArgs();
        }

        private boolean mayNeedHiddenThis() {
            Class<?> clazz = this.constructor.getDeclaringClass();
            if (clazz.getEnclosingConstructor() != null) {
                return false;
            }
            Method method = clazz.getEnclosingMethod();
            if (method != null) {
                return !Modifier.isStatic(method.getModifiers());
            }
            return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
        }
    }

    static class MethodInvokable<T>
    extends Invokable<T, Object> {
        final Method method;

        MethodInvokable(Method method) {
            super(method);
            this.method = method;
        }

        @Override
        final Object invokeInternal(@Nullable Object object, Object[] objectArray) throws InvocationTargetException, IllegalAccessException {
            return this.method.invoke(object, objectArray);
        }

        @Override
        Type getGenericReturnType() {
            return this.method.getGenericReturnType();
        }

        @Override
        Type[] getGenericParameterTypes() {
            return this.method.getGenericParameterTypes();
        }

        @Override
        Type[] getGenericExceptionTypes() {
            return this.method.getGenericExceptionTypes();
        }

        @Override
        final Annotation[][] getParameterAnnotations() {
            return this.method.getParameterAnnotations();
        }

        @Override
        public final TypeVariable<?>[] getTypeParameters() {
            return this.method.getTypeParameters();
        }

        @Override
        public final boolean isOverridable() {
            return !this.isFinal() && !this.isPrivate() && !this.isStatic() && !Modifier.isFinal(this.getDeclaringClass().getModifiers());
        }

        @Override
        public final boolean isVarArgs() {
            return this.method.isVarArgs();
        }
    }
}

