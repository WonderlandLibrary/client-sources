/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLAMDGPUAssociation {
    public static final int WGL_GPU_VENDOR_AMD = 7936;
    public static final int WGL_GPU_RENDERER_STRING_AMD = 7937;
    public static final int WGL_GPU_OPENGL_VERSION_STRING_AMD = 7938;
    public static final int WGL_GPU_FASTEST_TARGET_GPUS_AMD = 8610;
    public static final int WGL_GPU_RAM_AMD = 8611;
    public static final int WGL_GPU_CLOCK_AMD = 8612;
    public static final int WGL_GPU_NUM_PIPES_AMD = 8613;
    public static final int WGL_GPU_NUM_SIMD_AMD = 8614;
    public static final int WGL_GPU_NUM_RB_AMD = 8615;
    public static final int WGL_GPU_NUM_SPI_AMD = 8616;

    protected WGLAMDGPUAssociation() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglGetGPUIDsAMD, wGLCapabilities.wglGetGPUInfoAMD, wGLCapabilities.wglGetContextGPUIDAMD, wGLCapabilities.wglCreateAssociatedContextAMD, wGLCapabilities.wglCreateAssociatedContextAttribsAMD, wGLCapabilities.wglDeleteAssociatedContextAMD, wGLCapabilities.wglMakeAssociatedContextCurrentAMD, wGLCapabilities.wglGetCurrentAssociatedContextAMD);
    }

    public static int nwglGetGPUIDsAMD(int n, long l) {
        long l2 = GL.getCapabilitiesWGL().wglGetGPUIDsAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.callPI(n, l, l2);
    }

    @NativeType(value="UINT")
    public static int wglGetGPUIDsAMD(@Nullable @NativeType(value="UINT *") IntBuffer intBuffer) {
        return WGLAMDGPUAssociation.nwglGetGPUIDsAMD(Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer));
    }

    public static int nwglGetGPUInfoAMD(int n, int n2, int n3, int n4, long l) {
        long l2 = GL.getCapabilitiesWGL().wglGetGPUInfoAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.callPI(n, n2, n3, n4, l, l2);
    }

    public static int wglGetGPUInfoAMD(@NativeType(value="UINT") int n, int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        return WGLAMDGPUAssociation.nwglGetGPUInfoAMD(n, n2, n3, byteBuffer.remaining() >> GLChecks.typeToByteShift(n3), MemoryUtil.memAddress(byteBuffer));
    }

    public static int wglGetGPUInfoAMD(@NativeType(value="UINT") int n, int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") IntBuffer intBuffer) {
        return WGLAMDGPUAssociation.nwglGetGPUInfoAMD(n, n2, n3, (int)((long)intBuffer.remaining() << 2 >> GLChecks.typeToByteShift(n3)), MemoryUtil.memAddress(intBuffer));
    }

    public static int wglGetGPUInfoAMD(@NativeType(value="UINT") int n, int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") FloatBuffer floatBuffer) {
        return WGLAMDGPUAssociation.nwglGetGPUInfoAMD(n, n2, n3, (int)((long)floatBuffer.remaining() << 2 >> GLChecks.typeToByteShift(n3)), MemoryUtil.memAddress(floatBuffer));
    }

    @NativeType(value="UINT")
    public static int wglGetContextGPUIDAMD(@NativeType(value="HGLRC") long l) {
        long l2 = GL.getCapabilitiesWGL().wglGetContextGPUIDAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2);
    }

    @NativeType(value="HGLRC")
    public static long wglCreateAssociatedContextAMD(@NativeType(value="UINT") int n) {
        long l = GL.getCapabilitiesWGL().wglCreateAssociatedContextAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callP(n, l);
    }

    public static long nwglCreateAssociatedContextAttribsAMD(int n, long l, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglCreateAssociatedContextAttribsAMD;
        if (Checks.CHECKS) {
            Checks.check(l3);
        }
        return JNI.callPPP(n, l, l2, l3);
    }

    @NativeType(value="HGLRC")
    public static long wglCreateAssociatedContextAttribsAMD(@NativeType(value="UINT") int n, @NativeType(value="HGLRC") long l, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return WGLAMDGPUAssociation.nwglCreateAssociatedContextAttribsAMD(n, l, MemoryUtil.memAddressSafe(intBuffer));
    }

    @NativeType(value="BOOL")
    public static boolean wglDeleteAssociatedContextAMD(@NativeType(value="HGLRC") long l) {
        long l2 = GL.getCapabilitiesWGL().wglDeleteAssociatedContextAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglMakeAssociatedContextCurrentAMD(@NativeType(value="HGLRC") long l) {
        long l2 = GL.getCapabilitiesWGL().wglMakeAssociatedContextCurrentAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    @NativeType(value="HGLRC")
    public static long wglGetCurrentAssociatedContextAMD() {
        long l = GL.getCapabilitiesWGL().wglGetCurrentAssociatedContextAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callP(l);
    }

    @NativeType(value="VOID")
    public static void wglBlitContextFramebufferAMD(@NativeType(value="HGLRC") long l, @NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLbitfield") int n9, @NativeType(value="GLenum") int n10) {
        long l2 = GL.getCapabilitiesWGL().wglBlitContextFramebufferAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.callPV(l, n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l2);
    }

    @NativeType(value="UINT")
    public static int wglGetGPUIDsAMD(@Nullable @NativeType(value="UINT *") int[] nArray) {
        long l = GL.getCapabilitiesWGL().wglGetGPUIDsAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callPI(Checks.lengthSafe(nArray), nArray, l);
    }

    public static int wglGetGPUInfoAMD(@NativeType(value="UINT") int n, int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") int[] nArray) {
        long l = GL.getCapabilitiesWGL().wglGetGPUInfoAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callPI(n, n2, n3, nArray.length, nArray, l);
    }

    public static int wglGetGPUInfoAMD(@NativeType(value="UINT") int n, int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") float[] fArray) {
        long l = GL.getCapabilitiesWGL().wglGetGPUInfoAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callPI(n, n2, n3, fArray.length, fArray, l);
    }

    @NativeType(value="HGLRC")
    public static long wglCreateAssociatedContextAttribsAMD(@NativeType(value="UINT") int n, @NativeType(value="HGLRC") long l, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getCapabilitiesWGL().wglCreateAssociatedContextAttribsAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPP(n, l, nArray, l2);
    }
}

