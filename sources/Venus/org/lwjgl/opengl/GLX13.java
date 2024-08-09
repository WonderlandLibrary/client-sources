/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLX12;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.linux.XVisualInfo;

public class GLX13
extends GLX12 {
    public static final int GLX_WINDOW_BIT = 1;
    public static final int GLX_PIXMAP_BIT = 2;
    public static final int GLX_PBUFFER_BIT = 4;
    public static final int GLX_RGBA_BIT = 1;
    public static final int GLX_COLOR_INDEX_BIT = 2;
    public static final int GLX_PBUFFER_CLOBBER_MASK = 0x8000000;
    public static final int GLX_FRONT_LEFT_BUFFER_BIT = 1;
    public static final int GLX_FRONT_RIGHT_BUFFER_BIT = 2;
    public static final int GLX_BACK_LEFT_BUFFER_BIT = 4;
    public static final int GLX_BACK_RIGHT_BUFFER_BIT = 8;
    public static final int GLX_AUX_BUFFERS_BIT = 16;
    public static final int GLX_DEPTH_BUFFER_BIT = 32;
    public static final int GLX_STENCIL_BUFFER_BIT = 64;
    public static final int GLX_ACCUM_BUFFER_BIT = 128;
    public static final int GLX_CONFIG_CAVEAT = 32;
    public static final int GLX_X_VISUAL_TYPE = 34;
    public static final int GLX_TRANSPARENT_TYPE = 35;
    public static final int GLX_TRANSPARENT_INDEX_VALUE = 36;
    public static final int GLX_TRANSPARENT_RED_VALUE = 37;
    public static final int GLX_TRANSPARENT_GREEN_VALUE = 38;
    public static final int GLX_TRANSPARENT_BLUE_VALUE = 39;
    public static final int GLX_TRANSPARENT_ALPHA_VALUE = 40;
    public static final int GLX_DONT_CARE = -1;
    public static final int GLX_NONE = 32768;
    public static final int GLX_SLOW_CONFIG = 32769;
    public static final int GLX_TRUE_COLOR = 32770;
    public static final int GLX_DIRECT_COLOR = 32771;
    public static final int GLX_PSEUDO_COLOR = 32772;
    public static final int GLX_STATIC_COLOR = 32773;
    public static final int GLX_GRAY_SCALE = 32774;
    public static final int GLX_STATIC_GRAY = 32775;
    public static final int GLX_TRANSPARENT_RGB = 32776;
    public static final int GLX_TRANSPARENT_INDEX = 32777;
    public static final int GLX_VISUAL_ID = 32779;
    public static final int GLX_SCREEN = 32780;
    public static final int GLX_NON_CONFORMANT_CONFIG = 32781;
    public static final int GLX_DRAWABLE_TYPE = 32784;
    public static final int GLX_RENDER_TYPE = 32785;
    public static final int GLX_X_RENDERABLE = 32786;
    public static final int GLX_FBCONFIG_ID = 32787;
    public static final int GLX_RGBA_TYPE = 32788;
    public static final int GLX_COLOR_INDEX_TYPE = 32789;
    public static final int GLX_MAX_PBUFFER_WIDTH = 32790;
    public static final int GLX_MAX_PBUFFER_HEIGHT = 32791;
    public static final int GLX_MAX_PBUFFER_PIXELS = 32792;
    public static final int GLX_PRESERVED_CONTENTS = 32795;
    public static final int GLX_LARGEST_PBUFFER = 32796;
    public static final int GLX_WIDTH = 32797;
    public static final int GLX_HEIGHT = 32798;
    public static final int GLX_EVENT_MASK = 32799;
    public static final int GLX_DAMAGED = 32800;
    public static final int GLX_SAVED = 32801;
    public static final int GLX_WINDOW = 32802;
    public static final int GLX_PBUFFER = 32803;
    public static final int GLX_PBUFFER_HEIGHT = 32832;
    public static final int GLX_PBUFFER_WIDTH = 32833;

    protected GLX13() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXGetFBConfigs, gLXCapabilities.glXChooseFBConfig, gLXCapabilities.glXGetFBConfigAttrib, gLXCapabilities.glXGetVisualFromFBConfig, gLXCapabilities.glXCreateWindow, gLXCapabilities.glXCreatePixmap, gLXCapabilities.glXDestroyPixmap, gLXCapabilities.glXCreatePbuffer, gLXCapabilities.glXDestroyPbuffer, gLXCapabilities.glXQueryDrawable, gLXCapabilities.glXCreateNewContext, gLXCapabilities.glXMakeContextCurrent, gLXCapabilities.glXGetCurrentReadDrawable, gLXCapabilities.glXQueryContext, gLXCapabilities.glXSelectEvent, gLXCapabilities.glXGetSelectedEvent);
    }

    public static long nglXGetFBConfigs(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXGetFBConfigs;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPP(l, n, l2, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="GLXFBConfig *")
    public static PointerBuffer glXGetFBConfigs(@NativeType(value="Display *") long l, int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l2 = GLX13.nglXGetFBConfigs(l, n, MemoryUtil.memAddress(intBuffer));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static long nglXChooseFBConfig(long l, int n, long l2, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXChooseFBConfig;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        return JNI.callPPPP(l, n, l2, l3, l4);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="GLXFBConfig *")
    public static PointerBuffer glXChooseFBConfig(@NativeType(value="Display *") long l, int n, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        IntBuffer intBuffer2 = memoryStack.callocInt(1);
        try {
            long l2 = GLX13.nglXChooseFBConfig(l, n, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer2.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static int nglXGetFBConfigAttrib(long l, long l2, int n, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXGetFBConfigAttrib;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPI(l, l2, n, l3, l4);
    }

    public static int glXGetFBConfigAttrib(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, int n, @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLX13.nglXGetFBConfigAttrib(l, l2, n, MemoryUtil.memAddress(intBuffer));
    }

    public static long nglXGetVisualFromFBConfig(long l, long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXGetVisualFromFBConfig;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPP(l, l2, l3);
    }

    @Nullable
    @NativeType(value="XVisualInfo *")
    public static XVisualInfo glXGetVisualFromFBConfig(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2) {
        long l3 = GLX13.nglXGetVisualFromFBConfig(l, l2);
        return XVisualInfo.createSafe(l3);
    }

    public static long nglXCreateWindow(long l, long l2, long l3, long l4) {
        long l5 = GL.getCapabilitiesGLXClient().glXCreateWindow;
        if (Checks.CHECKS) {
            Checks.check(l5);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPPP(l, l2, l3, l4, l5);
    }

    @NativeType(value="GLXWindow")
    public static long glXCreateWindow(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="Window") long l3, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return GLX13.nglXCreateWindow(l, l2, l3, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static long nglXCreatePixmap(long l, long l2, long l3, long l4) {
        long l5 = GL.getCapabilitiesGLXClient().glXCreatePixmap;
        if (Checks.CHECKS) {
            Checks.check(l5);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPPP(l, l2, l3, l4, l5);
    }

    @NativeType(value="GLXPixmap")
    public static long glXCreatePixmap(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="Pixmap") long l3, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return GLX13.nglXCreatePixmap(l, l2, l3, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glXDestroyPixmap(@NativeType(value="Display *") long l, @NativeType(value="GLXPixmap") long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXDestroyPixmap;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPV(l, l2, l3);
    }

    public static long nglXCreatePbuffer(long l, long l2, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreatePbuffer;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPP(l, l2, l3, l4);
    }

    @NativeType(value="GLXPbuffer")
    public static long glXCreatePbuffer(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return GLX13.nglXCreatePbuffer(l, l2, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glXDestroyPbuffer(@NativeType(value="Display *") long l, @NativeType(value="GLXPbuffer") long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXDestroyPbuffer;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPV(l, l2, l3);
    }

    public static void nglXQueryDrawable(long l, long l2, int n, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXQueryDrawable;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPPV(l, l2, n, l3, l4);
    }

    public static void glXQueryDrawable(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n, @NativeType(value="unsigned int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GLX13.nglXQueryDrawable(l, l2, n, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glXQueryDrawable(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GLX13.nglXQueryDrawable(l, l2, n, MemoryUtil.memAddress(intBuffer));
            int n3 = intBuffer.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLXContext")
    public static long glXCreateNewContext(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, int n, @NativeType(value="GLXContext") long l3, @NativeType(value="Bool") boolean bl) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreateNewContext;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPP(l, l2, n, l3, bl ? 1 : 0, l4);
    }

    @NativeType(value="Bool")
    public static boolean glXMakeContextCurrent(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="GLXDrawable") long l3, @NativeType(value="GLXContext") long l4) {
        long l5 = GL.getCapabilitiesGLXClient().glXMakeContextCurrent;
        if (Checks.CHECKS) {
            Checks.check(l5);
            Checks.check(l);
        }
        return JNI.callPPPPI(l, l2, l3, l4, l5) != 0;
    }

    @NativeType(value="GLXDrawable")
    public static long glXGetCurrentReadDrawable() {
        long l = GL.getCapabilitiesGLXClient().glXGetCurrentReadDrawable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callP(l);
    }

    public static int nglXQueryContext(long l, long l2, int n, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXQueryContext;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPI(l, l2, n, l3, l4);
    }

    public static int glXQueryContext(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2, int n, @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLX13.nglXQueryContext(l, l2, n, MemoryUtil.memAddress(intBuffer));
    }

    public static void glXSelectEvent(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="unsigned long") long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXSelectEvent;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPPV(l, l2, l3, l4);
    }

    public static void nglXGetSelectedEvent(long l, long l2, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXGetSelectedEvent;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPPV(l, l2, l3, l4);
    }

    public static void glXGetSelectedEvent(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="unsigned long *") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        GLX13.nglXGetSelectedEvent(l, l2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="GLXFBConfig *")
    public static PointerBuffer glXChooseFBConfig(@NativeType(value="Display *") long l, int n, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getCapabilitiesGLXClient().glXChooseFBConfig;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l3 = JNI.callPPPP(l, n, nArray, MemoryUtil.memAddress(intBuffer), l2);
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l3, intBuffer.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static int glXGetFBConfigAttrib(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, int n, @NativeType(value="int *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXGetFBConfigAttrib;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(nArray, 1);
        }
        return JNI.callPPPI(l, l2, n, nArray, l3);
    }

    @NativeType(value="GLXWindow")
    public static long glXCreateWindow(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="Window") long l3, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreateWindow;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPPPP(l, l2, l3, nArray, l4);
    }

    @NativeType(value="GLXPixmap")
    public static long glXCreatePixmap(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="Pixmap") long l3, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreatePixmap;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPPPP(l, l2, l3, nArray, l4);
    }

    @NativeType(value="GLXPbuffer")
    public static long glXCreatePbuffer(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXCreatePbuffer;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPPP(l, l2, nArray, l3);
    }

    public static void glXQueryDrawable(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n, @NativeType(value="unsigned int *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXQueryDrawable;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(nArray, 1);
        }
        JNI.callPPPV(l, l2, n, nArray, l3);
    }

    public static int glXQueryContext(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2, int n, @NativeType(value="int *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXQueryContext;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(nArray, 1);
        }
        return JNI.callPPPI(l, l2, n, nArray, l3);
    }
}

