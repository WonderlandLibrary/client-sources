/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.LongSupplier;
import net.minecraft.client.renderer.texture.NativeImage;
import net.optifine.Config;

public class NativeMemory {
    private static long imageAllocated = 0L;
    private static LongSupplier bufferAllocatedSupplier = NativeMemory.makeLongSupplier(new String[][]{{"sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"}, {"jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed"}});
    private static LongSupplier bufferMaximumSupplier = NativeMemory.makeLongSupplier(new String[][]{{"sun.misc.VM", "maxDirectMemory"}, {"jdk.internal.misc.VM", "maxDirectMemory"}});

    public static long getBufferAllocated() {
        return bufferAllocatedSupplier == null ? -1L : bufferAllocatedSupplier.getAsLong();
    }

    public static long getBufferMaximum() {
        return bufferMaximumSupplier == null ? -1L : bufferMaximumSupplier.getAsLong();
    }

    public static synchronized void imageAllocated(NativeImage nativeImage) {
        imageAllocated += nativeImage.getSize();
    }

    public static synchronized void imageFreed(NativeImage nativeImage) {
        imageAllocated -= nativeImage.getSize();
    }

    public static long getImageAllocated() {
        return imageAllocated;
    }

    private static LongSupplier makeLongSupplier(String[][] stringArray) {
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>();
        for (int i = 0; i < stringArray.length; ++i) {
            String[] object = stringArray[i];
            try {
                return NativeMemory.makeLongSupplier(object);
            } catch (Throwable throwable) {
                arrayList.add(throwable);
                continue;
            }
        }
        for (Throwable throwable : arrayList) {
            Config.warn(throwable.getClass().getName() + ": " + throwable.getMessage());
        }
        return null;
    }

    private static LongSupplier makeLongSupplier(String[] stringArray) throws Exception {
        String string;
        if (stringArray.length < 2) {
            return null;
        }
        Class<?> clazz = Class.forName(stringArray[0]);
        Method method = clazz.getMethod(stringArray[5], new Class[0]);
        method.setAccessible(false);
        Object object = null;
        for (int i = 2; i < stringArray.length; ++i) {
            string = stringArray[i];
            object = method.invoke(object, new Object[0]);
            method = object.getClass().getMethod(string, new Class[0]);
            method.setAccessible(false);
        }
        Method method2 = method;
        string = object;
        return new LongSupplier(){
            private boolean disabled;
            final Method val$method1;
            final Object val$object1;
            {
                this.val$method1 = method;
                this.val$object1 = object;
                this.disabled = false;
            }

            @Override
            public long getAsLong() {
                if (this.disabled) {
                    return -1L;
                }
                try {
                    return (Long)this.val$method1.invoke(this.val$object1, new Object[0]);
                } catch (Throwable throwable) {
                    Config.warn(throwable.getClass().getName() + ": " + throwable.getMessage());
                    this.disabled = true;
                    return -1L;
                }
            }
        };
    }
}

