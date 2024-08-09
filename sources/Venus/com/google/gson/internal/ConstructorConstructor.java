/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.ReflectionAccessFilter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.ReflectionAccessFilterHelper;
import com.google.gson.internal.UnsafeAllocator;
import com.google.gson.internal.reflect.ReflectionHelper;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {
    private final Map<Type, InstanceCreator<?>> instanceCreators;
    private final boolean useJdkUnsafe;
    private final List<ReflectionAccessFilter> reflectionFilters;

    public ConstructorConstructor(Map<Type, InstanceCreator<?>> map, boolean bl, List<ReflectionAccessFilter> list) {
        this.instanceCreators = map;
        this.useJdkUnsafe = bl;
        this.reflectionFilters = list;
    }

    static String checkInstantiable(Class<?> clazz) {
        int n = clazz.getModifiers();
        if (Modifier.isInterface(n)) {
            return "Interfaces can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Interface name: " + clazz.getName();
        }
        if (Modifier.isAbstract(n)) {
            return "Abstract classes can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type. Class name: " + clazz.getName();
        }
        return null;
    }

    public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<T> clazz = typeToken.getRawType();
        InstanceCreator<?> instanceCreator = this.instanceCreators.get(type);
        if (instanceCreator != null) {
            return new ObjectConstructor<T>(this, instanceCreator, type){
                final InstanceCreator val$typeCreator;
                final Type val$type;
                final ConstructorConstructor this$0;
                {
                    this.this$0 = constructorConstructor;
                    this.val$typeCreator = instanceCreator;
                    this.val$type = type;
                }

                @Override
                public T construct() {
                    return this.val$typeCreator.createInstance(this.val$type);
                }
            };
        }
        InstanceCreator<?> instanceCreator2 = this.instanceCreators.get(clazz);
        if (instanceCreator2 != null) {
            return new ObjectConstructor<T>(this, instanceCreator2, type){
                final InstanceCreator val$rawTypeCreator;
                final Type val$type;
                final ConstructorConstructor this$0;
                {
                    this.this$0 = constructorConstructor;
                    this.val$rawTypeCreator = instanceCreator;
                    this.val$type = type;
                }

                @Override
                public T construct() {
                    return this.val$rawTypeCreator.createInstance(this.val$type);
                }
            };
        }
        ObjectConstructor<T> objectConstructor = ConstructorConstructor.newSpecialCollectionConstructor(type, clazz);
        if (objectConstructor != null) {
            return objectConstructor;
        }
        ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, clazz);
        ObjectConstructor<T> objectConstructor2 = ConstructorConstructor.newDefaultConstructor(clazz, filterResult);
        if (objectConstructor2 != null) {
            return objectConstructor2;
        }
        ObjectConstructor<T> objectConstructor3 = ConstructorConstructor.newDefaultImplementationConstructor(type, clazz);
        if (objectConstructor3 != null) {
            return objectConstructor3;
        }
        String string = ConstructorConstructor.checkInstantiable(clazz);
        if (string != null) {
            return new ObjectConstructor<T>(this, string){
                final String val$exceptionMessage;
                final ConstructorConstructor this$0;
                {
                    this.this$0 = constructorConstructor;
                    this.val$exceptionMessage = string;
                }

                @Override
                public T construct() {
                    throw new JsonIOException(this.val$exceptionMessage);
                }
            };
        }
        if (filterResult == ReflectionAccessFilter.FilterResult.ALLOW) {
            return this.newUnsafeAllocator(clazz);
        }
        String string2 = "Unable to create instance of " + clazz + "; ReflectionAccessFilter does not permit using reflection or Unsafe. Register an InstanceCreator or a TypeAdapter for this type or adjust the access filter to allow using reflection.";
        return new ObjectConstructor<T>(this, string2){
            final String val$message;
            final ConstructorConstructor this$0;
            {
                this.this$0 = constructorConstructor;
                this.val$message = string;
            }

            @Override
            public T construct() {
                throw new JsonIOException(this.val$message);
            }
        };
    }

    private static <T> ObjectConstructor<T> newSpecialCollectionConstructor(Type type, Class<? super T> clazz) {
        if (EnumSet.class.isAssignableFrom(clazz)) {
            return new ObjectConstructor<T>(type){
                final Type val$type;
                {
                    this.val$type = type;
                }

                @Override
                public T construct() {
                    if (this.val$type instanceof ParameterizedType) {
                        Type type = ((ParameterizedType)this.val$type).getActualTypeArguments()[0];
                        if (type instanceof Class) {
                            EnumSet enumSet = EnumSet.noneOf((Class)type);
                            return enumSet;
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + this.val$type.toString());
                    }
                    throw new JsonIOException("Invalid EnumSet type: " + this.val$type.toString());
                }
            };
        }
        if (clazz == EnumMap.class) {
            return new ObjectConstructor<T>(type){
                final Type val$type;
                {
                    this.val$type = type;
                }

                @Override
                public T construct() {
                    if (this.val$type instanceof ParameterizedType) {
                        Type type = ((ParameterizedType)this.val$type).getActualTypeArguments()[0];
                        if (type instanceof Class) {
                            EnumMap enumMap = new EnumMap((Class)type);
                            return enumMap;
                        }
                        throw new JsonIOException("Invalid EnumMap type: " + this.val$type.toString());
                    }
                    throw new JsonIOException("Invalid EnumMap type: " + this.val$type.toString());
                }
            };
        }
        return null;
    }

    private static <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> clazz, ReflectionAccessFilter.FilterResult filterResult) {
        String string;
        boolean bl;
        Constructor<T> constructor;
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }
        try {
            constructor = clazz.getDeclaredConstructor(new Class[0]);
        } catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
        boolean bl2 = bl = filterResult == ReflectionAccessFilter.FilterResult.ALLOW || ReflectionAccessFilterHelper.canAccess(constructor, null) && (filterResult != ReflectionAccessFilter.FilterResult.BLOCK_ALL || Modifier.isPublic(constructor.getModifiers()));
        if (!bl) {
            String string2 = "Unable to invoke no-args constructor of " + clazz + "; constructor is not accessible and ReflectionAccessFilter does not permit making it accessible. Register an InstanceCreator or a TypeAdapter for this type, change the visibility of the constructor or adjust the access filter.";
            return new ObjectConstructor<T>(string2){
                final String val$message;
                {
                    this.val$message = string;
                }

                @Override
                public T construct() {
                    throw new JsonIOException(this.val$message);
                }
            };
        }
        if (filterResult == ReflectionAccessFilter.FilterResult.ALLOW && (string = ReflectionHelper.tryMakeAccessible(constructor)) != null) {
            return new ObjectConstructor<T>(string){
                final String val$exceptionMessage;
                {
                    this.val$exceptionMessage = string;
                }

                @Override
                public T construct() {
                    throw new JsonIOException(this.val$exceptionMessage);
                }
            };
        }
        return new ObjectConstructor<T>(constructor){
            final Constructor val$constructor;
            {
                this.val$constructor = constructor;
            }

            @Override
            public T construct() {
                try {
                    Object t = this.val$constructor.newInstance(new Object[0]);
                    return t;
                } catch (InstantiationException instantiationException) {
                    throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.val$constructor) + "' with no args", instantiationException);
                } catch (InvocationTargetException invocationTargetException) {
                    throw new RuntimeException("Failed to invoke constructor '" + ReflectionHelper.constructorToString(this.val$constructor) + "' with no args", invocationTargetException.getCause());
                } catch (IllegalAccessException illegalAccessException) {
                    throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(illegalAccessException);
                }
            }
        };
    }

    private static <T> ObjectConstructor<T> newDefaultImplementationConstructor(Type type, Class<? super T> clazz) {
        if (Collection.class.isAssignableFrom(clazz)) {
            if (SortedSet.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new TreeSet();
                    }
                };
            }
            if (Set.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new ArrayDeque();
                    }
                };
            }
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return new ArrayList();
                }
            };
        }
        if (Map.class.isAssignableFrom(clazz)) {
            if (ConcurrentNavigableMap.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new ConcurrentSkipListMap();
                    }
                };
            }
            if (ConcurrentMap.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom(clazz)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new TreeMap();
                    }
                };
            }
            if (type instanceof ParameterizedType && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type).getActualTypeArguments()[0]).getRawType())) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new LinkedHashMap();
                    }
                };
            }
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return new LinkedTreeMap();
                }
            };
        }
        return null;
    }

    private <T> ObjectConstructor<T> newUnsafeAllocator(Class<? super T> clazz) {
        if (this.useJdkUnsafe) {
            return new ObjectConstructor<T>(this, clazz){
                final Class val$rawType;
                final ConstructorConstructor this$0;
                {
                    this.this$0 = constructorConstructor;
                    this.val$rawType = clazz;
                }

                @Override
                public T construct() {
                    try {
                        Object t = UnsafeAllocator.INSTANCE.newInstance(this.val$rawType);
                        return t;
                    } catch (Exception exception) {
                        throw new RuntimeException("Unable to create instance of " + this.val$rawType + ". Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.", exception);
                    }
                }
            };
        }
        String string = "Unable to create instance of " + clazz + "; usage of JDK Unsafe is disabled. Registering an InstanceCreator or a TypeAdapter for this type, adding a no-args constructor, or enabling usage of JDK Unsafe may fix this problem.";
        return new ObjectConstructor<T>(this, string){
            final String val$exceptionMessage;
            final ConstructorConstructor this$0;
            {
                this.this$0 = constructorConstructor;
                this.val$exceptionMessage = string;
            }

            @Override
            public T construct() {
                throw new JsonIOException(this.val$exceptionMessage);
            }
        };
    }

    public String toString() {
        return this.instanceCreators.toString();
    }
}

