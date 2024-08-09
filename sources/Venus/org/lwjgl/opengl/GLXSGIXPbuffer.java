/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXSGIXPbuffer {
    public static final int GLX_MAX_PBUFFER_WIDTH_SGIX = 32790;
    public static final int GLX_MAX_PBUFFER_HEIGHT_SGIX = 32791;
    public static final int GLX_MAX_PBUFFER_PIXELS_SGIX = 32792;
    public static final int GLX_OPTIMAL_PBUFFER_WIDTH_SGIX = 32793;
    public static final int GLX_OPTIMAL_PBUFFER_HEIGHT_SGIX = 32794;
    public static final int GLX_PBUFFER_BIT_SGIX = 4;
    public static final int GLX_PRESERVED_CONTENTS_SGIX = 32795;
    public static final int GLX_LARGEST_PBUFFER_SGIX = 32796;
    public static final int GLX_WIDTH_SGIX = 32797;
    public static final int GLX_HEIGHT_SGIX = 32798;
    public static final int GLX_EVENT_MASK_SGIX = 32799;
    public static final int GLX_BUFFER_CLOBBER_MASK_SGIX = 0x8000000;
    public static final int GLX_DAMAGED_SGIX = 32800;
    public static final int GLX_SAVED_SGIX = 32801;
    public static final int GLX_WINDOW_SGIX = 32802;
    public static final int GLX_PBUFFER_SGIX = 32803;
    public static final int GLX_FRONT_LEFT_BUFFER_BIT_SGIX = 1;
    public static final int GLX_FRONT_RIGHT_BUFFER_BIT_SGIX = 2;
    public static final int GLX_BACK_LEFT_BUFFER_BIT_SGIX = 4;
    public static final int GLX_BACK_RIGHT_BUFFER_BIT_SGIX = 8;
    public static final int GLX_AUX_BUFFERS_BIT_SGIX = 16;
    public static final int GLX_DEPTH_BUFFER_BIT_SGIX = 32;
    public static final int GLX_STENCIL_BUFFER_BIT_SGIX = 64;
    public static final int GLX_ACCUM_BUFFER_BIT_SGIX = 128;
    public static final int GLX_SAMPLE_BUFFERS_BIT_SGIX = 256;

    protected GLXSGIXPbuffer() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXCreateGLXPbufferSGIX, gLXCapabilities.glXDestroyGLXPbufferSGIX, gLXCapabilities.glXQueryGLXPbufferSGIX, gLXCapabilities.glXSelectEventSGIX, gLXCapabilities.glXGetSelectedEventSGIX);
    }

    public static long nglXCreateGLXPbufferSGIX(long l, long l2, int n, int n2, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreateGLXPbufferSGIX;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPP(l, l2, n, n2, l3, l4);
    }

    @NativeType(value="GLXPbuffer")
    public static long glXCreateGLXPbufferSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="unsigned int") int n, @NativeType(value="unsigned int") int n2, @Nullable @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return GLXSGIXPbuffer.nglXCreateGLXPbufferSGIX(l, l2, n, n2, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glXDestroyGLXPbufferSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXPbuffer") long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXDestroyGLXPbufferSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPV(l, l2, l3);
    }

    public static void nglXQueryGLXPbufferSGIX(long l, long l2, int n, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXQueryGLXPbufferSGIX;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPPV(l, l2, n, l3, l4);
    }

    public static void glXQueryGLXPbufferSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXPbuffer") long l2, int n, @NativeType(value="unsigned int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GLXSGIXPbuffer.nglXQueryGLXPbufferSGIX(l, l2, n, MemoryUtil.memAddress(intBuffer));
    }

    public static void glXSelectEventSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="unsigned long") long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXSelectEventSGIX;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPPV(l, l2, l3, l4);
    }

    public static void nglXGetSelectedEventSGIX(long l, long l2, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXGetSelectedEventSGIX;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPPV(l, l2, l3, l4);
    }

    public static void glXGetSelectedEventSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="unsigned long *") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        GLXSGIXPbuffer.nglXGetSelectedEventSGIX(l, l2, MemoryUtil.memAddress(pointerBuffer));
    }

    @NativeType(value="GLXPbuffer")
    public static long glXCreateGLXPbufferSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="unsigned int") int n, @NativeType(value="unsigned int") int n2, @Nullable @NativeType(value="int *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXCreateGLXPbufferSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPPP(l, l2, n, n2, nArray, l3);
    }

    public static void glXQueryGLXPbufferSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXPbuffer") long l2, int n, @NativeType(value="unsigned int *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXQueryGLXPbufferSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(nArray, 1);
        }
        JNI.callPPPV(l, l2, n, nArray, l3);
    }
}

