/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.dyncall;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class DynLoad {
    protected DynLoad() {
        throw new UnsupportedOperationException();
    }

    public static native long ndlLoadLibrary(long var0);

    @NativeType(value="DLLib *")
    public static long dlLoadLibrary(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return DynLoad.ndlLoadLibrary(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="DLLib *")
    public static long dlLoadLibrary(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = DynLoad.ndlLoadLibrary(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void ndlFreeLibrary(long var0);

    public static void dlFreeLibrary(@NativeType(value="DLLib *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        DynLoad.ndlFreeLibrary(l);
    }

    public static native long ndlFindSymbol(long var0, long var2);

    @NativeType(value="void *")
    public static long dlFindSymbol(@NativeType(value="DLLib *") long l, @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNT1(byteBuffer);
        }
        return DynLoad.ndlFindSymbol(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void *")
    public static long dlFindSymbol(@NativeType(value="DLLib *") long l, @NativeType(value="char const *") CharSequence charSequence) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = DynLoad.ndlFindSymbol(l, l2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int ndlGetLibraryPath(long var0, long var2, int var4);

    public static int dlGetLibraryPath(@NativeType(value="DLLib *") long l, @NativeType(value="char *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynLoad.ndlGetLibraryPath(l, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="int")
    public static String dlGetLibraryPath(@NativeType(value="DLLib *") long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            ByteBuffer byteBuffer = memoryStack.malloc(n);
            int n3 = DynLoad.ndlGetLibraryPath(l, MemoryUtil.memAddress(byteBuffer), n);
            String string = MemoryUtil.memASCII(byteBuffer, n3 - 1);
            return string;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long ndlSymsInit(long var0);

    @NativeType(value="DLSyms *")
    public static long dlSymsInit(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return DynLoad.ndlSymsInit(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="DLSyms *")
    public static long dlSymsInit(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = DynLoad.ndlSymsInit(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void ndlSymsCleanup(long var0);

    public static void dlSymsCleanup(@NativeType(value="DLSyms *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        DynLoad.ndlSymsCleanup(l);
    }

    public static native int ndlSymsCount(long var0);

    public static int dlSymsCount(@NativeType(value="DLSyms *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynLoad.ndlSymsCount(l);
    }

    public static native long ndlSymsName(long var0, int var2);

    @Nullable
    @NativeType(value="char const *")
    public static String dlSymsName(@NativeType(value="DLSyms *") long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        long l2 = DynLoad.ndlSymsName(l, n);
        return MemoryUtil.memASCIISafe(l2);
    }

    public static native long ndlSymsNameFromValue(long var0, long var2);

    @Nullable
    @NativeType(value="char const *")
    public static String dlSymsNameFromValue(@NativeType(value="DLSyms *") long l, @NativeType(value="void *") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        long l3 = DynLoad.ndlSymsNameFromValue(l, l2);
        return MemoryUtil.memASCIISafe(l3);
    }

    static {
        Library.initialize();
    }
}

