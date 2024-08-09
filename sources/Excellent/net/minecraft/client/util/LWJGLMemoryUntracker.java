package net.minecraft.client.util;

import net.mojang.blaze3d.platform.GLX;
import org.lwjgl.system.Pointer;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LWJGLMemoryUntracker
{
    @Nullable
    private static final MethodHandle HANDLE = GLX.make(() ->
    {
        try {
            Lookup lookup = MethodHandles.lookup();
            Class<?> oclass = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
            Method method = oclass.getDeclaredMethod("untrack", Long.TYPE);
            method.setAccessible(true);
            Field field = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
            field.setAccessible(true);
            Object object = field.get((Object)null);
            return oclass.isInstance(object) ? lookup.unreflect(method) : null;
        }
        catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | ClassNotFoundException classnotfoundexception)
        {
            throw new RuntimeException(classnotfoundexception);
        }
    });

    public static void untrack(long memAddr)
    {
        if (HANDLE != null)
        {
            try
            {
                HANDLE.invoke(memAddr);
            }
            catch (Throwable throwable)
            {
                throw new RuntimeException(throwable);
            }
        }
    }

    public static void untrack(Pointer pointer)
    {
        untrack(pointer.address());
    }
}
