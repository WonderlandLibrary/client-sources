/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.mojang.blaze3d.platform.GLX;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import org.lwjgl.system.Pointer;

public class LWJGLMemoryUntracker {
    @Nullable
    private static final MethodHandle HANDLE = GLX.make(LWJGLMemoryUntracker::lambda$static$0);

    public static void untrack(long l) {
        if (HANDLE != null) {
            try {
                HANDLE.invoke(l);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
    }

    public static void untrack(Pointer pointer) {
        LWJGLMemoryUntracker.untrack(pointer.address());
    }

    private static MethodHandle lambda$static$0() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Class<?> clazz = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
            Method method = clazz.getDeclaredMethod("untrack", Long.TYPE);
            method.setAccessible(false);
            Field field = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
            field.setAccessible(false);
            Object object = field.get(null);
            return clazz.isInstance(object) ? lookup.unreflect(method) : null;
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }
}

