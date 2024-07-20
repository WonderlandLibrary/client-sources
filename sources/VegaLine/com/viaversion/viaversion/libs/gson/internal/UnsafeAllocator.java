/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal;

import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class UnsafeAllocator {
    public static final UnsafeAllocator INSTANCE = UnsafeAllocator.create();

    public abstract <T> T newInstance(Class<T> var1) throws Exception;

    private static void assertInstantiable(Class<?> c) {
        String exceptionMessage = ConstructorConstructor.checkInstantiable(c);
        if (exceptionMessage != null) {
            throw new AssertionError((Object)("UnsafeAllocator is used for non-instantiable type: " + exceptionMessage));
        }
    }

    private static UnsafeAllocator create() {
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            final Object unsafe = f.get(null);
            final Method allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
            return new UnsafeAllocator(){

                @Override
                public <T> T newInstance(Class<T> c) throws Exception {
                    UnsafeAllocator.assertInstantiable(c);
                    return (T)allocateInstance.invoke(unsafe, c);
                }
            };
        } catch (Exception unsafeClass) {
            try {
                Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
                getConstructorId.setAccessible(true);
                final int constructorId = (Integer)getConstructorId.invoke(null, Object.class);
                final Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
                newInstance.setAccessible(true);
                return new UnsafeAllocator(){

                    @Override
                    public <T> T newInstance(Class<T> c) throws Exception {
                        UnsafeAllocator.assertInstantiable(c);
                        return (T)newInstance.invoke(null, c, constructorId);
                    }
                };
            } catch (Exception getConstructorId) {
                try {
                    final Method newInstance = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
                    newInstance.setAccessible(true);
                    return new UnsafeAllocator(){

                        @Override
                        public <T> T newInstance(Class<T> c) throws Exception {
                            UnsafeAllocator.assertInstantiable(c);
                            return (T)newInstance.invoke(null, c, Object.class);
                        }
                    };
                } catch (Exception exception) {
                    return new UnsafeAllocator(){

                        @Override
                        public <T> T newInstance(Class<T> c) {
                            throw new UnsupportedOperationException("Cannot allocate " + c + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
                        }
                    };
                }
            }
        }
    }
}

