/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

import com.google.gson.internal.ConstructorConstructor;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class UnsafeAllocator {
    public static final UnsafeAllocator INSTANCE = UnsafeAllocator.create();

    public abstract <T> T newInstance(Class<T> var1) throws Exception;

    private static void assertInstantiable(Class<?> clazz) {
        String string = ConstructorConstructor.checkInstantiable(clazz);
        if (string != null) {
            throw new AssertionError((Object)("UnsafeAllocator is used for non-instantiable type: " + string));
        }
    }

    private static UnsafeAllocator create() {
        try {
            Class<?> clazz = Class.forName("sun.misc.Unsafe");
            Field field = clazz.getDeclaredField("theUnsafe");
            field.setAccessible(false);
            Object object = field.get(null);
            Method method = clazz.getMethod("allocateInstance", Class.class);
            return new UnsafeAllocator(method, object){
                final Method val$allocateInstance;
                final Object val$unsafe;
                {
                    this.val$allocateInstance = method;
                    this.val$unsafe = object;
                }

                @Override
                public <T> T newInstance(Class<T> clazz) throws Exception {
                    UnsafeAllocator.access$000(clazz);
                    return (T)this.val$allocateInstance.invoke(this.val$unsafe, clazz);
                }
            };
        } catch (Exception exception) {
            try {
                Method method = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
                method.setAccessible(false);
                int n = (Integer)method.invoke(null, Object.class);
                Method method2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
                method2.setAccessible(false);
                return new UnsafeAllocator(method2, n){
                    final Method val$newInstance;
                    final int val$constructorId;
                    {
                        this.val$newInstance = method;
                        this.val$constructorId = n;
                    }

                    @Override
                    public <T> T newInstance(Class<T> clazz) throws Exception {
                        UnsafeAllocator.access$000(clazz);
                        return (T)this.val$newInstance.invoke(null, clazz, this.val$constructorId);
                    }
                };
            } catch (Exception exception2) {
                try {
                    Method method = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
                    method.setAccessible(false);
                    return new UnsafeAllocator(method){
                        final Method val$newInstance;
                        {
                            this.val$newInstance = method;
                        }

                        @Override
                        public <T> T newInstance(Class<T> clazz) throws Exception {
                            UnsafeAllocator.access$000(clazz);
                            return (T)this.val$newInstance.invoke(null, clazz, Object.class);
                        }
                    };
                } catch (Exception exception3) {
                    return new UnsafeAllocator(){

                        @Override
                        public <T> T newInstance(Class<T> clazz) {
                            throw new UnsupportedOperationException("Cannot allocate " + clazz + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
                        }
                    };
                }
            }
        }
    }

    static void access$000(Class clazz) {
        UnsafeAllocator.assertInstantiable(clazz);
    }
}

