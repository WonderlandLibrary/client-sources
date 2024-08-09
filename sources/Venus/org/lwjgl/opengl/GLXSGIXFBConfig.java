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
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.linux.XVisualInfo;

public class GLXSGIXFBConfig {
    public static final int GLX_DRAWABLE_TYPE_SGIX = 32784;
    public static final int GLX_RENDER_TYPE_SGIX = 32785;
    public static final int GLX_X_RENDERABLE_SGIX = 32786;
    public static final int GLX_FBCONFIG_ID_SGIX = 32787;
    public static final int GLX_SCREEN_EXT = 32780;
    public static final int GLX_WINDOW_BIT_SGIX = 1;
    public static final int GLX_PIXMAP_BIT_SGIX = 2;
    public static final int GLX_RGBA_BIT_SGIX = 1;
    public static final int GLX_COLOR_INDEX_BIT_SGIX = 2;
    public static final int GLX_RGBA_TYPE_SGIX = 32788;
    public static final int GLX_COLOR_INDEX_TYPE_SGIX = 32789;

    protected GLXSGIXFBConfig() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXGetFBConfigAttribSGIX, gLXCapabilities.glXChooseFBConfigSGIX, gLXCapabilities.glXCreateGLXPixmapWithConfigSGIX, gLXCapabilities.glXCreateContextWithConfigSGIX, gLXCapabilities.glXGetVisualFromFBConfigSGIX, gLXCapabilities.glXGetFBConfigFromVisualSGIX);
    }

    public static int nglXGetFBConfigAttribSGIX(long l, long l2, int n, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXGetFBConfigAttribSGIX;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPI(l, l2, n, l3, l4);
    }

    public static int glXGetFBConfigAttribSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfigSGIX") long l2, int n, @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLXSGIXFBConfig.nglXGetFBConfigAttribSGIX(l, l2, n, MemoryUtil.memAddress(intBuffer));
    }

    public static long nglXChooseFBConfigSGIX(long l, int n, long l2, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXChooseFBConfigSGIX;
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
    @NativeType(value="GLXFBConfigSGIX *")
    public static PointerBuffer glXChooseFBConfigSGIX(@NativeType(value="Display *") long l, int n, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        IntBuffer intBuffer2 = memoryStack.callocInt(1);
        try {
            long l2 = GLXSGIXFBConfig.nglXChooseFBConfigSGIX(l, n, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2));
            PointerBuffer pointerBuffer = MemoryUtil.memPointerBufferSafe(l2, intBuffer2.get(0));
            return pointerBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLXPixmap")
    public static long glXCreateGLXPixmapWithConfigSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="Pixmap") long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreateGLXPixmapWithConfigSGIX;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPP(l, l2, l3, l4);
    }

    @NativeType(value="GLXContext")
    public static long glXCreateContextWithConfigSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, int n, @NativeType(value="GLXContext") long l3, @NativeType(value="Bool") boolean bl) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreateContextWithConfigSGIX;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        return JNI.callPPPP(l, l2, n, l3, bl ? 1 : 0, l4);
    }

    public static long nglXGetVisualFromFBConfigSGIX(long l, long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXGetVisualFromFBConfigSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPP(l, l2, l3);
    }

    @Nullable
    @NativeType(value="XVisualInfo *")
    public static XVisualInfo glXGetVisualFromFBConfigSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2) {
        long l3 = GLXSGIXFBConfig.nglXGetVisualFromFBConfigSGIX(l, l2);
        return XVisualInfo.createSafe(l3);
    }

    public static long nglXGetFBConfigFromVisualSGIX(long l, long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXGetFBConfigFromVisualSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            XVisualInfo.validate(l2);
        }
        return JNI.callPPP(l, l2, l3);
    }

    @NativeType(value="GLXFBConfigSGIX")
    public static long glXGetFBConfigFromVisualSGIX(@NativeType(value="Display *") long l, @NativeType(value="XVisualInfo *") XVisualInfo xVisualInfo) {
        return GLXSGIXFBConfig.nglXGetFBConfigFromVisualSGIX(l, xVisualInfo.address());
    }

    public static int glXGetFBConfigAttribSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfigSGIX") long l2, int n, @NativeType(value="int *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXGetFBConfigAttribSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(nArray, 1);
        }
        return JNI.callPPPI(l, l2, n, nArray, l3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="GLXFBConfigSGIX *")
    public static PointerBuffer glXChooseFBConfigSGIX(@NativeType(value="Display *") long l, int n, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getCapabilitiesGLXClient().glXChooseFBConfigSGIX;
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
}

