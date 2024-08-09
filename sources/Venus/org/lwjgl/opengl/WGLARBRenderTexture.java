/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLARBRenderTexture {
    public static final int WGL_BIND_TO_TEXTURE_RGB_ARB = 8304;
    public static final int WGL_BIND_TO_TEXTURE_RGBA_ARB = 8305;
    public static final int WGL_TEXTURE_FORMAT_ARB = 8306;
    public static final int WGL_TEXTURE_TARGET_ARB = 8307;
    public static final int WGL_MIPMAP_TEXTURE_ARB = 8308;
    public static final int WGL_TEXTURE_RGB_ARB = 8309;
    public static final int WGL_TEXTURE_RGBA_ARB = 8310;
    public static final int WGL_NO_TEXTURE_ARB = 8311;
    public static final int WGL_TEXTURE_CUBE_MAP_ARB = 8312;
    public static final int WGL_TEXTURE_1D_ARB = 8313;
    public static final int WGL_TEXTURE_2D_ARB = 8314;
    public static final int WGL_MIPMAP_LEVEL_ARB = 8315;
    public static final int WGL_CUBE_MAP_FACE_ARB = 8316;
    public static final int WGL_TEXTURE_CUBE_MAP_POSITIVE_X_ARB = 8317;
    public static final int WGL_TEXTURE_CUBE_MAP_NEGATIVE_X_ARB = 8318;
    public static final int WGL_TEXTURE_CUBE_MAP_POSITIVE_Y_ARB = 8319;
    public static final int WGL_TEXTURE_CUBE_MAP_NEGATIVE_Y_ARB = 8320;
    public static final int WGL_TEXTURE_CUBE_MAP_POSITIVE_Z_ARB = 8321;
    public static final int WGL_TEXTURE_CUBE_MAP_NEGATIVE_Z_ARB = 8322;
    public static final int WGL_FRONT_LEFT_ARB = 8323;
    public static final int WGL_FRONT_RIGHT_ARB = 8324;
    public static final int WGL_BACK_LEFT_ARB = 8325;
    public static final int WGL_BACK_RIGHT_ARB = 8326;
    public static final int WGL_AUX0_ARB = 8327;
    public static final int WGL_AUX1_ARB = 8328;
    public static final int WGL_AUX2_ARB = 8329;
    public static final int WGL_AUX3_ARB = 8330;
    public static final int WGL_AUX4_ARB = 8331;
    public static final int WGL_AUX5_ARB = 8332;
    public static final int WGL_AUX6_ARB = 8333;
    public static final int WGL_AUX7_ARB = 8334;
    public static final int WGL_AUX8_ARB = 8335;
    public static final int WGL_AUX9_ARB = 8336;

    protected WGLARBRenderTexture() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglBindTexImageARB, wGLCapabilities.wglReleaseTexImageARB, wGLCapabilities.wglSetPbufferAttribARB);
    }

    @NativeType(value="BOOL")
    public static boolean wglBindTexImageARB(@NativeType(value="HPBUFFERARB") long l, int n) {
        long l2 = GL.getCapabilitiesWGL().wglBindTexImageARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglReleaseTexImageARB(@NativeType(value="HPBUFFERARB") long l, int n) {
        long l2 = GL.getCapabilitiesWGL().wglReleaseTexImageARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, l2) != 0;
    }

    public static int nwglSetPbufferAttribARB(long l, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglSetPbufferAttribARB;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglSetPbufferAttribARB(@NativeType(value="HPBUFFERARB") long l, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return WGLARBRenderTexture.nwglSetPbufferAttribARB(l, MemoryUtil.memAddressSafe(intBuffer)) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglSetPbufferAttribARB(@NativeType(value="HPBUFFERARB") long l, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getCapabilitiesWGL().wglSetPbufferAttribARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPI(l, nArray, l2) != 0;
    }
}

