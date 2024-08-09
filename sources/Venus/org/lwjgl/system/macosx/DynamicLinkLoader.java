/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class DynamicLinkLoader {
    public static final int RTLD_LAZY = 1;
    public static final int RTLD_NOW = 2;
    public static final int RTLD_LOCAL = 4;
    public static final int RTLD_GLOBAL = 8;
    public static final long RTLD_NEXT = -1L;
    public static final long RTLD_DEFAULT = -2L;
    public static final long RTLD_SELF = -3L;
    public static final long RTLD_MAIN_ONLY = -5L;

    protected DynamicLinkLoader() {
        throw new UnsupportedOperationException();
    }

    public static native long ndlopen(long var0, int var2);

    @NativeType(value="void *")
    public static long dlopen(@Nullable @NativeType(value="char const *") ByteBuffer byteBuffer, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
        }
        return DynamicLinkLoader.ndlopen(MemoryUtil.memAddressSafe(byteBuffer), n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void *")
    public static long dlopen(@Nullable @NativeType(value="char const *") CharSequence charSequence, int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCIISafe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            long l2 = DynamicLinkLoader.ndlopen(l, n);
            return l2;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long ndlerror();

    @Nullable
    @NativeType(value="char const *")
    public static String dlerror() {
        long l = DynamicLinkLoader.ndlerror();
        return MemoryUtil.memASCIISafe(l);
    }

    public static native long ndlsym(long var0, long var2);

    @NativeType(value="void *")
    public static long dlsym(@NativeType(value="void *") long l, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNT1(byteBuffer);
        }
        return DynamicLinkLoader.ndlsym(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void *")
    public static long dlsym(@NativeType(value="void *") long l, @NativeType(value="char const *") CharSequence charSequence) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = DynamicLinkLoader.ndlsym(l, l2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int ndlclose(long var0);

    public static int dlclose(@NativeType(value="void *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynamicLinkLoader.ndlclose(l);
    }

    static {
        Library.initialize();
    }
}

