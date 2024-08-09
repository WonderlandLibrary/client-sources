/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AMDPerformanceMonitor {
    public static final int GL_COUNTER_TYPE_AMD = 35776;
    public static final int GL_COUNTER_RANGE_AMD = 35777;
    public static final int GL_UNSIGNED_INT64_AMD = 35778;
    public static final int GL_PERCENTAGE_AMD = 35779;
    public static final int GL_PERFMON_RESULT_AVAILABLE_AMD = 35780;
    public static final int GL_PERFMON_RESULT_SIZE_AMD = 35781;
    public static final int GL_PERFMON_RESULT_AMD = 35782;

    protected AMDPerformanceMonitor() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetPerfMonitorGroupsAMD, gLCapabilities.glGetPerfMonitorCountersAMD, gLCapabilities.glGetPerfMonitorGroupStringAMD, gLCapabilities.glGetPerfMonitorCounterStringAMD, gLCapabilities.glGetPerfMonitorCounterInfoAMD, gLCapabilities.glGenPerfMonitorsAMD, gLCapabilities.glDeletePerfMonitorsAMD, gLCapabilities.glSelectPerfMonitorCountersAMD, gLCapabilities.glBeginPerfMonitorAMD, gLCapabilities.glEndPerfMonitorAMD, gLCapabilities.glGetPerfMonitorCounterDataAMD);
    }

    public static native void nglGetPerfMonitorGroupsAMD(long var0, int var2, long var3);

    public static void glGetPerfMonitorGroupsAMD(@Nullable @NativeType(value="GLint *") IntBuffer intBuffer, @Nullable @NativeType(value="GLuint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorGroupsAMD(MemoryUtil.memAddressSafe(intBuffer), Checks.remainingSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer2));
    }

    public static native void nglGetPerfMonitorCountersAMD(int var0, long var1, long var3, int var5, long var6);

    public static void glGetPerfMonitorCountersAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2, @NativeType(value="GLuint *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorCountersAMD(n, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), intBuffer3.remaining(), MemoryUtil.memAddress(intBuffer3));
    }

    public static native void nglGetPerfMonitorGroupStringAMD(int var0, int var1, long var2, long var4);

    public static void glGetPerfMonitorGroupStringAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorGroupStringAMD(n, byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetPerfMonitorCounterStringAMD(int var0, int var1, int var2, long var3, long var5);

    public static void glGetPerfMonitorCounterStringAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorCounterStringAMD(n, n2, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void nglGetPerfMonitorCounterInfoAMD(int var0, int var1, int var2, long var3);

    public static void glGetPerfMonitorCounterInfoAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorCounterInfoAMD(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetPerfMonitorCounterInfoAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorCounterInfoAMD(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetPerfMonitorCounterInfoAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorCounterInfoAMD(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGenPerfMonitorsAMD(int var0, long var1);

    public static void glGenPerfMonitorsAMD(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        AMDPerformanceMonitor.nglGenPerfMonitorsAMD(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenPerfMonitorsAMD() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            AMDPerformanceMonitor.nglGenPerfMonitorsAMD(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglDeletePerfMonitorsAMD(int var0, long var1);

    public static void glDeletePerfMonitorsAMD(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        AMDPerformanceMonitor.nglDeletePerfMonitorsAMD(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeletePerfMonitorsAMD(@NativeType(value="GLuint *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            AMDPerformanceMonitor.nglDeletePerfMonitorsAMD(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglSelectPerfMonitorCountersAMD(int var0, boolean var1, int var2, int var3, long var4);

    public static void glSelectPerfMonitorCountersAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        AMDPerformanceMonitor.nglSelectPerfMonitorCountersAMD(n, bl, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void glBeginPerfMonitorAMD(@NativeType(value="GLuint") int var0);

    public static native void glEndPerfMonitorAMD(@NativeType(value="GLuint") int var0);

    public static native void nglGetPerfMonitorCounterDataAMD(int var0, int var1, int var2, long var3, long var5);

    public static void glGetPerfMonitorCounterDataAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer, @Nullable @NativeType(value="GLint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer2, 1);
        }
        AMDPerformanceMonitor.nglGetPerfMonitorCounterDataAMD(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddressSafe(intBuffer2));
    }

    public static void glGetPerfMonitorGroupsAMD(@Nullable @NativeType(value="GLint *") int[] nArray, @Nullable @NativeType(value="GLuint *") int[] nArray2) {
        long l = GL.getICD().glGetPerfMonitorGroupsAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(nArray, Checks.lengthSafe(nArray2), nArray2, l);
    }

    public static void glGetPerfMonitorCountersAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLint *") int[] nArray, @NativeType(value="GLint *") int[] nArray2, @NativeType(value="GLuint *") int[] nArray3) {
        long l = GL.getICD().glGetPerfMonitorCountersAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        JNI.callPPPV(n, nArray, nArray2, nArray3.length, nArray3, l);
    }

    public static void glGetPerfMonitorGroupStringAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetPerfMonitorGroupStringAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPPV(n, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetPerfMonitorCounterStringAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetPerfMonitorCounterStringAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, n2, Checks.remainingSafe(byteBuffer), nArray, MemoryUtil.memAddressSafe(byteBuffer), l);
    }

    public static void glGetPerfMonitorCounterInfoAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glGetPerfMonitorCounterInfoAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetPerfMonitorCounterInfoAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glGetPerfMonitorCounterInfoAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGenPerfMonitorsAMD(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenPerfMonitorsAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDeletePerfMonitorsAMD(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glDeletePerfMonitorsAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glSelectPerfMonitorCountersAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glSelectPerfMonitorCountersAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, bl, n2, nArray.length, nArray, l);
    }

    public static void glGetPerfMonitorCounterDataAMD(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray, @Nullable @NativeType(value="GLint *") int[] nArray2) {
        long l = GL.getICD().glGetPerfMonitorCounterDataAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray2, 1);
        }
        JNI.callPPV(n, n2, nArray.length, nArray, nArray2, l);
    }

    static {
        GL.initialize();
    }
}

