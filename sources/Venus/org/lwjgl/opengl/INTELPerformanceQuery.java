/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class INTELPerformanceQuery {
    public static final int GL_PERFQUERY_SINGLE_CONTEXT_INTEL = 0;
    public static final int GL_PERFQUERY_GLOBAL_CONTEXT_INTEL = 1;
    public static final int GL_PERFQUERY_WAIT_INTEL = 33787;
    public static final int GL_PERFQUERY_FLUSH_INTEL = 33786;
    public static final int GL_PERFQUERY_DONOT_FLUSH_INTEL = 33785;
    public static final int GL_PERFQUERY_COUNTER_EVENT_INTEL = 38128;
    public static final int GL_PERFQUERY_COUNTER_DURATION_NORM_INTEL = 38129;
    public static final int GL_PERFQUERY_COUNTER_DURATION_RAW_INTEL = 38130;
    public static final int GL_PERFQUERY_COUNTER_THROUGHPUT_INTEL = 38131;
    public static final int GL_PERFQUERY_COUNTER_RAW_INTEL = 38132;
    public static final int GL_PERFQUERY_COUNTER_TIMESTAMP_INTEL = 38133;
    public static final int GL_PERFQUERY_COUNTER_DATA_UINT32_INTEL = 38136;
    public static final int GL_PERFQUERY_COUNTER_DATA_UINT64_INTEL = 38137;
    public static final int GL_PERFQUERY_COUNTER_DATA_FLOAT_INTEL = 38138;
    public static final int GL_PERFQUERY_COUNTER_DATA_DOUBLE_INTEL = 38139;
    public static final int GL_PERFQUERY_COUNTER_DATA_BOOL32_INTEL = 38140;
    public static final int GL_PERFQUERY_QUERY_NAME_LENGTH_MAX_INTEL = 38141;
    public static final int GL_PERFQUERY_COUNTER_NAME_LENGTH_MAX_INTEL = 38142;
    public static final int GL_PERFQUERY_COUNTER_DESC_LENGTH_MAX_INTEL = 38143;
    public static final int GL_PERFQUERY_GPA_EXTENDED_COUNTERS_INTEL = 38144;

    protected INTELPerformanceQuery() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBeginPerfQueryINTEL, gLCapabilities.glCreatePerfQueryINTEL, gLCapabilities.glDeletePerfQueryINTEL, gLCapabilities.glEndPerfQueryINTEL, gLCapabilities.glGetFirstPerfQueryIdINTEL, gLCapabilities.glGetNextPerfQueryIdINTEL, gLCapabilities.glGetPerfCounterInfoINTEL, gLCapabilities.glGetPerfQueryDataINTEL, gLCapabilities.glGetPerfQueryIdByNameINTEL, gLCapabilities.glGetPerfQueryInfoINTEL);
    }

    public static native void glBeginPerfQueryINTEL(@NativeType(value="GLuint") int var0);

    public static native void nglCreatePerfQueryINTEL(int var0, long var1);

    public static void glCreatePerfQueryINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        INTELPerformanceQuery.nglCreatePerfQueryINTEL(n, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glCreatePerfQueryINTEL(@NativeType(value="GLuint") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            INTELPerformanceQuery.nglCreatePerfQueryINTEL(n, MemoryUtil.memAddress(intBuffer));
            int n3 = intBuffer.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void glDeletePerfQueryINTEL(@NativeType(value="GLuint") int var0);

    public static native void glEndPerfQueryINTEL(@NativeType(value="GLuint") int var0);

    public static native void nglGetFirstPerfQueryIdINTEL(long var0);

    public static void glGetFirstPerfQueryIdINTEL(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        INTELPerformanceQuery.nglGetFirstPerfQueryIdINTEL(MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetFirstPerfQueryIdINTEL() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            INTELPerformanceQuery.nglGetFirstPerfQueryIdINTEL(MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglGetNextPerfQueryIdINTEL(int var0, long var1);

    public static void glGetNextPerfQueryIdINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        INTELPerformanceQuery.nglGetNextPerfQueryIdINTEL(n, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetNextPerfQueryIdINTEL(@NativeType(value="GLuint") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            INTELPerformanceQuery.nglGetNextPerfQueryIdINTEL(n, MemoryUtil.memAddress(intBuffer));
            int n3 = intBuffer.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetPerfCounterInfoINTEL(int var0, int var1, int var2, long var3, int var5, long var6, long var8, long var10, long var12, long var14, long var16);

    public static void glGetPerfCounterInfoINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar *") ByteBuffer byteBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer2, @NativeType(value="GLuint *") IntBuffer intBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer2, @NativeType(value="GLuint *") IntBuffer intBuffer3, @NativeType(value="GLuint *") IntBuffer intBuffer4, @NativeType(value="GLuint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            Checks.check((Buffer)intBuffer4, 1);
            Checks.check((Buffer)longBuffer, 1);
        }
        INTELPerformanceQuery.nglGetPerfCounterInfoINTEL(n, n2, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.memAddress(byteBuffer2), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer4), MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglGetPerfQueryDataINTEL(int var0, int var1, int var2, long var3, long var5);

    public static void glGetPerfQueryDataINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        INTELPerformanceQuery.nglGetPerfQueryDataINTEL(n, n2, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetPerfQueryIdByNameINTEL(long var0, long var2);

    public static void glGetPerfQueryIdByNameINTEL(@NativeType(value="GLchar *") ByteBuffer byteBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer, 1);
        }
        INTELPerformanceQuery.nglGetPerfQueryIdByNameINTEL(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glGetPerfQueryIdByNameINTEL(@NativeType(value="GLchar *") CharSequence charSequence, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            INTELPerformanceQuery.nglGetPerfQueryIdByNameINTEL(l, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetPerfQueryIdByNameINTEL(@NativeType(value="GLchar *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            IntBuffer intBuffer = memoryStack.callocInt(1);
            INTELPerformanceQuery.nglGetPerfQueryIdByNameINTEL(l, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglGetPerfQueryInfoINTEL(int var0, int var1, long var2, long var4, long var6, long var8, long var10);

    public static void glGetPerfQueryInfoINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLchar *") ByteBuffer byteBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer2, @NativeType(value="GLuint *") IntBuffer intBuffer3, @NativeType(value="GLuint *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            Checks.check((Buffer)intBuffer4, 1);
        }
        INTELPerformanceQuery.nglGetPerfQueryInfoINTEL(n, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer4));
    }

    public static void glCreatePerfQueryINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glCreatePerfQueryINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glGetFirstPerfQueryIdINTEL(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetFirstPerfQueryIdINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(nArray, l);
    }

    public static void glGetNextPerfQueryIdINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetNextPerfQueryIdINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glGetPerfCounterInfoINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar *") ByteBuffer byteBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer2, @NativeType(value="GLuint *") int[] nArray, @NativeType(value="GLuint *") int[] nArray2, @NativeType(value="GLuint *") int[] nArray3, @NativeType(value="GLuint *") int[] nArray4, @NativeType(value="GLuint64 *") long[] lArray) {
        long l = GL.getICD().glGetPerfCounterInfoINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            Checks.check(nArray4, 1);
            Checks.check(lArray, 1);
        }
        JNI.callPPPPPPPV(n, n2, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.memAddress(byteBuffer2), nArray, nArray2, nArray3, nArray4, lArray, l);
    }

    public static void glGetPerfQueryDataINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetPerfQueryDataINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPPV(n, n2, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), nArray, l);
    }

    public static void glGetPerfQueryIdByNameINTEL(@NativeType(value="GLchar *") ByteBuffer byteBuffer, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetPerfQueryIdByNameINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray, 1);
        }
        JNI.callPPV(MemoryUtil.memAddress(byteBuffer), nArray, l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glGetPerfQueryIdByNameINTEL(@NativeType(value="GLchar *") CharSequence charSequence, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetPerfQueryIdByNameINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            JNI.callPPV(l2, nArray, l);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void glGetPerfQueryInfoINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLchar *") ByteBuffer byteBuffer, @NativeType(value="GLuint *") int[] nArray, @NativeType(value="GLuint *") int[] nArray2, @NativeType(value="GLuint *") int[] nArray3, @NativeType(value="GLuint *") int[] nArray4) {
        long l = GL.getICD().glGetPerfQueryInfoINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            Checks.check(nArray4, 1);
        }
        JNI.callPPPPPV(n, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), nArray, nArray2, nArray3, nArray4, l);
    }

    static {
        GL.initialize();
    }
}

