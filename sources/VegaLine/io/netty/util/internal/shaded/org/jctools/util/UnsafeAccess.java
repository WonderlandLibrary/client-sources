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
        Unsafe instance;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            instance = (Unsafe)field.get(null);
        } catch (Exception ignored) {
            try {
                Constructor c = Unsafe.class.getDeclaredConstructor(new Class[0]);
                c.setAccessible(true);
                instance = (Unsafe)c.newInstance(new Object[0]);
            } catch (Exception e) {
                SUPPORTS_GET_AND_SET = false;
                throw new RuntimeException(e);
            }
        }
        boolean getAndSetSupport = false;
        try {
            Unsafe.class.getMethod("getAndSetObject", Object.class, Long.TYPE, Object.class);
            getAndSetSupport = true;
        } catch (Exception exception) {
            // empty catch block
        }
        UNSAFE = instance;
        SUPPORTS_GET_AND_SET = getAndSetSupport;
    }
}

