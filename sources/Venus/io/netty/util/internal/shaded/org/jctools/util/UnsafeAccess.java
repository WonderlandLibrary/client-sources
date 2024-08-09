/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.shaded.org.jctools.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeAccess {
    public static final boolean SUPPORTS_GET_AND_SET;
    public static final Unsafe UNSAFE;

    static {
        Unsafe unsafe;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(false);
            unsafe = (Unsafe)field.get(null);
        } catch (Exception exception) {
            try {
                Constructor constructor = Unsafe.class.getDeclaredConstructor(new Class[0]);
                constructor.setAccessible(false);
                unsafe = (Unsafe)constructor.newInstance(new Object[0]);
            } catch (Exception exception2) {
                SUPPORTS_GET_AND_SET = false;
                throw new RuntimeException(exception2);
            }
        }
        boolean bl = false;
        try {
            Unsafe.class.getMethod("getAndSetObject", Object.class, Long.TYPE, Object.class);
            bl = true;
        } catch (Exception exception) {
            // empty catch block
        }
        UNSAFE = unsafe;
        SUPPORTS_GET_AND_SET = bl;
    }
}

