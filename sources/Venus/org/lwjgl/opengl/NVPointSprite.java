/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVPointSprite {
    public static final int GL_POINT_SPRITE_NV = 34913;
    public static final int GL_COORD_REPLACE_NV = 34914;
    public static final int GL_POINT_SPRITE_R_MODE_NV = 34915;

    protected NVPointSprite() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glPointParameteriNV, gLCapabilities.glPointParameterivNV);
    }

    public static native void glPointParameteriNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    public static native void nglPointParameterivNV(int var0, long var1);

    public static void glPointParameterivNV(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        NVPointSprite.nglPointParameterivNV(n, MemoryUtil.memAddress(intBuffer));
    }

    public static void glPointParameterivNV(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glPointParameterivNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    static {
        GL.initialize();
    }
}

