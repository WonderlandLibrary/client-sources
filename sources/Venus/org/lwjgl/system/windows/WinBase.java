/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WinBase {
    public static final int FALSE = 0;
    public static final int TRUE = 1;

    protected WinBase() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="DWORD")
    public static native int GetLastError();

    @NativeType(value="DWORD")
    public static native int getLastError();

    public static native long nGetModuleHandle(long var0);

    @NativeType(value="HMODULE")
    public static long GetModuleHandle(@Nullable @NativeType(value="LPCTSTR") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT2Safe(byteBuffer);
        }
        return WinBase.nGetModuleHandle(MemoryUtil.memAddressSafe(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="HMODULE")
    public static long GetModuleHandle(@Nullable @NativeType(value="LPCTSTR") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF16Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            long l2 = WinBase.nGetModuleHandle(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long nLoadLibrary(long var0);

    @NativeType(value="HMODULE")
    public static long LoadLibrary(@NativeType(value="LPCTSTR") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT2(byteBuffer);
        }
        return WinBase.nLoadLibrary(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="HMODULE")
    public static long LoadLibrary(@NativeType(value="LPCTSTR") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF16(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = WinBase.nLoadLibrary(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native long nGetProcAddress(long var0, long var2);

    @NativeType(value="FARPROC")
    public static long GetProcAddress(@NativeType(value="HMODULE") long l, @NativeType(value="LPCSTR") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNT1(byteBuffer);
        }
        return WinBase.nGetProcAddress(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="FARPROC")
    public static long GetProcAddress(@NativeType(value="HMODULE") long l, @NativeType(value="LPCSTR") CharSequence charSequence) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = WinBase.nGetProcAddress(l, l2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nFreeLibrary(long var0);

    @NativeType(value="BOOL")
    public static boolean FreeLibrary(@NativeType(value="HMODULE") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return WinBase.nFreeLibrary(l) != 0;
    }

    static {
        Library.initialize();
    }
}

