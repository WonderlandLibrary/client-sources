/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.libc;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class LibCStdio {
    public static final long sscanf;
    public static final long sprintf;
    public static final long snprintf;

    protected LibCStdio() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="void *")
    private static native long sscanf();

    public static native int nvsscanf(long var0, long var2, long var4);

    public static int vsscanf(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="char const *") ByteBuffer byteBuffer2, @NativeType(value="va_list") long l) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.checkNT1(byteBuffer2);
            Checks.check(l);
        }
        return LibCStdio.nvsscanf(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2), l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int vsscanf(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="char const *") CharSequence charSequence2, @NativeType(value="va_list") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            memoryStack.nASCII(charSequence2, false);
            long l3 = memoryStack.getPointerAddress();
            int n2 = LibCStdio.nvsscanf(l2, l3, l);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="void *")
    private static native long sprintf();

    @NativeType(value="void *")
    private static native long snprintf();

    public static native int nvsnprintf(long var0, long var2, long var4, long var6);

    public static int vsnprintf(@Nullable @NativeType(value="char *") ByteBuffer byteBuffer, @NativeType(value="char const *") ByteBuffer byteBuffer2, @NativeType(value="va_list") long l) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer2);
            Checks.check(l);
        }
        return LibCStdio.nvsnprintf(MemoryUtil.memAddressSafe(byteBuffer), Checks.remainingSafe(byteBuffer), MemoryUtil.memAddress(byteBuffer2), l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int vsnprintf(@Nullable @NativeType(value="char *") ByteBuffer byteBuffer, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="va_list") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            int n2 = LibCStdio.nvsnprintf(MemoryUtil.memAddressSafe(byteBuffer), Checks.remainingSafe(byteBuffer), l2, l);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    static {
        Library.initialize();
        sscanf = LibCStdio.sscanf();
        sprintf = LibCStdio.sprintf();
        snprintf = LibCStdio.snprintf();
    }
}

