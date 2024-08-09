/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXAMDGPUAssociation {
    public static final int GLX_GPU_VENDOR_AMD = 7936;
    public static final int GLX_GPU_RENDERER_STRING_AMD = 7937;
    public static final int GLX_GPU_OPENGL_VERSION_STRING_AMD = 7938;
    public static final int GLX_GPU_FASTEST_TARGET_GPUS_AMD = 8610;
    public static final int GLX_GPU_RAM_AMD = 8611;
    public static final int GLX_GPU_CLOCK_AMD = 8612;
    public static final int GLX_GPU_NUM_PIPES_AMD = 8613;
    public static final int GLX_GPU_NUM_SIMD_AMD = 8614;
    public static final int GLX_GPU_NUM_RB_AMD = 8615;
    public static final int GLX_GPU_NUM_SPI_AMD = 8616;

    protected GLXAMDGPUAssociation() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXBlitContextFramebufferAMD, gLXCapabilities.glXCreateAssociatedContextAMD, gLXCapabilities.glXCreateAssociatedContextAttribsAMD, gLXCapabilities.glXDeleteAssociatedContextAMD, gLXCapabilities.glXGetContextGPUIDAMD, gLXCapabilities.glXGetCurrentAssociatedContextAMD, gLXCapabilities.glXGetGPUIDsAMD, gLXCapabilities.glXGetGPUInfoAMD, gLXCapabilities.glXMakeAssociatedContextCurrentAMD);
    }

    public static void glXBlitContextFramebufferAMD(@NativeType(value="GLXContext") long l, @NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLbitfield") int n9, @NativeType(value="GLenum") int n10) {
        long l2 = GL.getCapabilitiesGLXClient().glXBlitContextFramebufferAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.callPV(l, n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l2);
    }

    @NativeType(value="GLXContext")
    public static long glXCreateAssociatedContextAMD(@NativeType(value="unsigned int") int n, @NativeType(value="GLXContext") long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXCreateAssociatedContextAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(n, l, l2);
    }

    public static long nglXCreateAssociatedContextAttribsAMD(int n, long l, long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXCreateAssociatedContextAttribsAMD;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPP(n, l, l2, l3);
    }

    @NativeType(value="GLXContext")
    public static long glXCreateAssociatedContextAttribsAMD(@NativeType(value="unsigned int") int n, @NativeType(value="GLXContext") long l, @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT(intBuffer);
        }
        return GLXAMDGPUAssociation.nglXCreateAssociatedContextAttribsAMD(n, l, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="Bool")
    public static boolean glXDeleteAssociatedContextAMD(@NativeType(value="GLXContext") long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXDeleteAssociatedContextAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    @NativeType(value="unsigned int")
    public static int glXGetContextGPUIDAMD(@NativeType(value="GLXContext") long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXGetContextGPUIDAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2);
    }

    @NativeType(value="GLXContext")
    public static long glXGetCurrentAssociatedContextAMD() {
        long l = GL.getCapabilitiesGLXClient().glXGetCurrentAssociatedContextAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callP(l);
    }

    @NativeType(value="unsigned int")
    public static int glXGetGPUIDsAMD(@NativeType(value="unsigned int") int n, @NativeType(value="unsigned int") int n2) {
        long l = GL.getCapabilitiesGLXClient().glXGetGPUIDsAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callI(n, n2, l);
    }

    public static int nglXGetGPUInfoAMD(int n, int n2, int n3, int n4, long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXGetGPUInfoAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.callPI(n, n2, n3, n4, l, l2);
    }

    public static int glXGetGPUInfoAMD(@NativeType(value="unsigned int") int n, int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        return GLXAMDGPUAssociation.nglXGetGPUInfoAMD(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    @NativeType(value="Bool")
    public static boolean glXMakeAssociatedContextCurrentAMD(@NativeType(value="GLXContext") long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXMakeAssociatedContextCurrentAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    @NativeType(value="GLXContext")
    public static long glXCreateAssociatedContextAttribsAMD(@NativeType(value="unsigned int") int n, @NativeType(value="GLXContext") long l, @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getCapabilitiesGLXClient().glXCreateAssociatedContextAttribsAMD;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNT(nArray);
        }
        return JNI.callPPP(n, l, nArray, l2);
    }
}

