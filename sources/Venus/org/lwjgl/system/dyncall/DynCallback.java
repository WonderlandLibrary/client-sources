/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.dyncall;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class DynCallback {
    public static final char DCB_SIGCHAR_CC_PREFIX = '_';
    public static final char DCB_SIGCHAR_CC_ELLIPSIS = 'e';
    public static final char DCB_SIGCHAR_CC_STDCALL = 's';
    public static final char DCB_SIGCHAR_CC_FASTCALL_GNU = 'f';
    public static final char DCB_SIGCHAR_CC_FASTCALL_MS = 'F';
    public static final char DCB_SIGCHAR_CC_THISCALL_MS = '+';

    protected DynCallback() {
        throw new UnsupportedOperationException();
    }

    public static native long ndcbNewCallback(long var0, long var2, long var4);

    @NativeType(value="DCCallback *")
    public static long dcbNewCallback(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="DCCallbackHandler *") long l, @NativeType(value="void *") long l2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(l);
            Checks.check(l2);
        }
        return DynCallback.ndcbNewCallback(MemoryUtil.memAddress(byteBuffer), l, l2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="DCCallback *")
    public static long dcbNewCallback(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="DCCallbackHandler *") long l, @NativeType(value="void *") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l3 = memoryStack.getPointerAddress();
            long l4 = DynCallback.ndcbNewCallback(l3, l, l2);
            return l4;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void ndcbInitCallback(long var0, long var2, long var4, long var6);

    public static void dcbInitCallback(@NativeType(value="DCCallback *") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="DCCallbackHandler *") long l2, @NativeType(value="void *") long l3) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNT1(byteBuffer);
            Checks.check(l2);
            Checks.check(l3);
        }
        DynCallback.ndcbInitCallback(l, MemoryUtil.memAddress(byteBuffer), l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void dcbInitCallback(@NativeType(value="DCCallback *") long l, @NativeType(value="char const *") CharSequence charSequence, @NativeType(value="DCCallbackHandler *") long l2, @NativeType(value="void *") long l3) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l4 = memoryStack.getPointerAddress();
            DynCallback.ndcbInitCallback(l, l4, l2, l3);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void ndcbFreeCallback(long var0);

    public static void dcbFreeCallback(@NativeType(value="DCCallback *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        DynCallback.ndcbFreeCallback(l);
    }

    public static native long ndcbGetUserData(long var0);

    @NativeType(value="void *")
    public static long dcbGetUserData(@NativeType(value="DCCallback *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbGetUserData(l);
    }

    public static native int ndcbArgBool(long var0);

    @NativeType(value="DCbool")
    public static boolean dcbArgBool(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgBool(l) != 0;
    }

    public static native byte ndcbArgChar(long var0);

    @NativeType(value="DCchar")
    public static byte dcbArgChar(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgChar(l);
    }

    public static native short ndcbArgShort(long var0);

    @NativeType(value="DCshort")
    public static short dcbArgShort(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgShort(l);
    }

    public static native int ndcbArgInt(long var0);

    @NativeType(value="DCint")
    public static int dcbArgInt(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgInt(l);
    }

    public static native int ndcbArgLong(long var0);

    @NativeType(value="DClong")
    public static int dcbArgLong(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgLong(l);
    }

    public static native long ndcbArgLongLong(long var0);

    @NativeType(value="DClonglong")
    public static long dcbArgLongLong(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgLongLong(l);
    }

    public static native byte ndcbArgUChar(long var0);

    @NativeType(value="DCchar")
    public static byte dcbArgUChar(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgUChar(l);
    }

    public static native short ndcbArgUShort(long var0);

    @NativeType(value="DCshort")
    public static short dcbArgUShort(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgUShort(l);
    }

    public static native int ndcbArgUInt(long var0);

    @NativeType(value="DCint")
    public static int dcbArgUInt(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgUInt(l);
    }

    public static native int ndcbArgULong(long var0);

    @NativeType(value="DClong")
    public static int dcbArgULong(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgULong(l);
    }

    public static native long ndcbArgULongLong(long var0);

    @NativeType(value="DClonglong")
    public static long dcbArgULongLong(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgULongLong(l);
    }

    public static native float ndcbArgFloat(long var0);

    @NativeType(value="DCfloat")
    public static float dcbArgFloat(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgFloat(l);
    }

    public static native double ndcbArgDouble(long var0);

    @NativeType(value="DCdouble")
    public static double dcbArgDouble(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgDouble(l);
    }

    public static native long ndcbArgPointer(long var0);

    @NativeType(value="DCpointer")
    public static long dcbArgPointer(@NativeType(value="DCArgs *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return DynCallback.ndcbArgPointer(l);
    }

    static {
        Library.initialize();
    }
}

