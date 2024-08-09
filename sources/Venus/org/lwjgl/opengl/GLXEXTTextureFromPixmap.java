/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXEXTTextureFromPixmap {
    public static final int GLX_BIND_TO_TEXTURE_RGB_EXT = 8400;
    public static final int GLX_BIND_TO_TEXTURE_RGBA_EXT = 8401;
    public static final int GLX_BIND_TO_MIPMAP_TEXTURE_EXT = 8402;
    public static final int GLX_BIND_TO_TEXTURE_TARGETS_EXT = 8403;
    public static final int GLX_Y_INVERTED_EXT = 8404;
    public static final int GLX_TEXTURE_FORMAT_EXT = 8405;
    public static final int GLX_TEXTURE_TARGET_EXT = 8406;
    public static final int GLX_MIPMAP_TEXTURE_EXT = 8407;
    public static final int GLX_TEXTURE_FORMAT_NONE_EXT = 8408;
    public static final int GLX_TEXTURE_FORMAT_RGB_EXT = 8409;
    public static final int GLX_TEXTURE_FORMAT_RGBA_EXT = 8410;
    public static final int GLX_TEXTURE_1D_BIT_EXT = 1;
    public static final int GLX_TEXTURE_2D_BIT_EXT = 2;
    public static final int GLX_TEXTURE_RECTANGLE_BIT_EXT = 4;
    public static final int GLX_TEXTURE_1D_EXT = 8411;
    public static final int GLX_TEXTURE_2D_EXT = 8412;
    public static final int GLX_TEXTURE_RECTANGLE_EXT = 8413;
    public static final int GLX_FRONT_LEFT_EXT = 8414;
    public static final int GLX_FRONT_RIGHT_EXT = 8415;
    public static final int GLX_BACK_LEFT_EXT = 8416;
    public static final int GLX_BACK_RIGHT_EXT = 8417;
    public static final int GLX_FRONT_EXT = 8414;
    public static final int GLX_BACK_EXT = 8416;
    public static final int GLX_AUX0_EXT = 8418;
    public static final int GLX_AUX1_EXT = 8419;
    public static final int GLX_AUX2_EXT = 8420;
    public static final int GLX_AUX3_EXT = 8421;
    public static final int GLX_AUX4_EXT = 8422;
    public static final int GLX_AUX5_EXT = 8423;
    public static final int GLX_AUX6_EXT = 8424;
    public static final int GLX_AUX7_EXT = 8425;
    public static final int GLX_AUX8_EXT = 8426;
    public static final int GLX_AUX9_EXT = 8427;

    protected GLXEXTTextureFromPixmap() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXBindTexImageEXT, gLXCapabilities.glXReleaseTexImageEXT);
    }

    public static void nglXBindTexImageEXT(long l, long l2, int n, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXBindTexImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPPV(l, l2, n, l3, l4);
    }

    public static void glXBindTexImageEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        GLXEXTTextureFromPixmap.nglXBindTexImageEXT(l, l2, n, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glXReleaseTexImageEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n) {
        long l3 = GL.getCapabilitiesGLXClient().glXReleaseTexImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPV(l, l2, n, l3);
    }

    public static void glXBindTexImageEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXBindTexImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.checkNTSafe(nArray);
        }
        JNI.callPPPV(l, l2, n, nArray, l3);
    }
}

