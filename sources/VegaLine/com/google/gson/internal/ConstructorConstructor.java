/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.UnsafeAllocator;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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

    public ConstructorConstructor(Map<Type, InstanceCreator<?>> instanceCreators) {
        this.instanceCreators = instanceCreators;
    }

    public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) {
        final Type type2 = typeToken.getType();
        Class<T> rawType = typeToken.getRawType();
        final InstanceCreator<?> typeCreator = this.instanceCreators.get(type2);
        if (typeCreator != null) {
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return typeCreator.createInstance(type2);
                }
            };
        }
        final InstanceCreator<?> rawTypeCreator = this.instanceCreators.get(rawType);
        if (rawTypeCreator != null) {
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    return rawTypeCreator.createInstance(type2);
                }
            };
        }
        ObjectConstructor<T> defaultConstructor = this.newDefaultConstructor(rawType);
        if (defaultConstructor != null) {
            return defaultConstructor;
        }
        ObjectConstructor<T> defaultImplementation = this.newDefaultImplementationConstructor(type2, rawType);
        if (defaultImplementation != null) {
            return defaultImplementation;
        }
        return this.newUnsafeAllocator(type2, rawType);
    }

    private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> rawType) {
        try {
            final Constructor<T> constructor = rawType.getDeclaredConstructor(new Class[0]);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return new ObjectConstructor<T>(){

                @Override
                public T construct() {
                    try {
                        Object[] args = null;
                        return constructor.newInstance(args);
                    } catch (InstantiationException e) {
                        throw new RuntimeException("Failed to invoke " + constructor + " with no args", e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException("Failed to invoke " + constructor + " with no args", e.getTargetException());
                    } catch (IllegalAccessException e) {
                        throw new AssertionError((Object)e);
                    }
                }
            };
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newDefaultImplementationConstructor(final Type type2, Class<? super T> rawType) {
        if (Collection.class.isAssignableFrom(rawType)) {
            if (SortedSet.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new TreeSet();
                    }
                };
            }
            if (EnumSet.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        if (type2 instanceof ParameterizedType) {
                            Type elementType = ((ParameterizedType)type2).getActualTypeArguments()[0];
                            if (elementType instanceof Class) {
                                return EnumSet.noneOf((Class)elementType);
                            }
                            throw new JsonIOException("Invalid EnumSet type: " + type2.toString());
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + type2.toString());
                    }
                };
            }
            if (Set.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new LinkedHashSet();
                    }
                };
            }
            if (Queue.class.isAssignableFrom(rawType)) {
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
        if (Map.class.isAssignableFrom(rawType)) {
            if (ConcurrentNavigableMap.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new ConcurrentSkipListMap();
                    }
                };
            }
            if (ConcurrentMap.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom(rawType)) {
                return new ObjectConstructor<T>(){

                    @Override
                    public T construct() {
                        return new TreeMap();
                    }
                };
            }
            if (type2 instanceof ParameterizedType && !String.class.isAssignableFrom(TypeToken.get(((ParameterizedType)type2).getActualTypeArguments()[0]).getRawType())) {
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

    private <T> ObjectConstructor<T> newUnsafeAllocator(final Type type2, final Class<? super T> rawType) {
        return new ObjectConstructor<T>(){
            private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

            @Override
            public T construct() {
                try {
                    Object newInstance = this.unsafeAllocator.newInstance(rawType);
                    return newInstance;
                } catch (Exception e) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + type2 + ". Register an InstanceCreator with Gson for this type may fix this problem.", e);
                }
            }
        };
    }

    public String toString() {
        return this.instanceCreators.toString();
    }
}

