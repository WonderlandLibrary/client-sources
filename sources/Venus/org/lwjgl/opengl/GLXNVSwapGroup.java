/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXNVSwapGroup {
    protected GLXNVSwapGroup() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXJoinSwapGroupNV, gLXCapabilities.glXBindSwapBarrierNV, gLXCapabilities.glXQuerySwapGroupNV, gLXCapabilities.glXQueryMaxSwapGroupsNV, gLXCapabilities.glXQueryFrameCountNV, gLXCapabilities.glXResetFrameCountNV);
    }

    @NativeType(value="Bool")
    public static boolean glXJoinSwapGroupNV(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="GLuint") int n) {
        long l3 = GL.getCapabilitiesGLXClient().glXJoinSwapGroupNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPI(l, l2, n, l3) != 0;
    }

    @NativeType(value="Bool")
    public static boolean glXBindSwapBarrierNV(@NativeType(value="Display *") long l, @NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        long l2 = GL.getCapabilitiesGLXClient().glXBindSwapBarrierNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, n2, l2) != 0;
    }

    public static int nglXQuerySwapGroupNV(long l, long l2, long l3, long l4) {
        long l5 = GL.getCapabilitiesGLXClient().glXQuerySwapGroupNV;
        if (Checks.CHECKS) {
            Checks.check(l5);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPPI(l, l2, l3, l4, l5);
    }

    @NativeType(value="Bool")
    public static boolean glXQuerySwapGroupNV(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="GLuint *") IntBuffer intBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        return GLXNVSwapGroup.nglXQuerySwapGroupNV(l, l2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2)) != 0;
    }

    public static int nglXQueryMaxSwapGroupsNV(long l, int n, long l2, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXQueryMaxSwapGroupsNV;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        return JNI.callPPPI(l, n, l2, l3, l4);
    }

    @NativeType(value="Bool")
    public static boolean glXQueryMaxSwapGroupsNV(@NativeType(value="Display *") long l, int n, @NativeType(value="GLuint *") IntBuffer intBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        return GLXNVSwapGroup.nglXQueryMaxSwapGroupsNV(l, n, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2)) != 0;
    }

    public static int nglXQueryFrameCountNV(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXQueryFrameCountNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, n, l2, l3);
    }

    @NativeType(value="Bool")
    public static boolean glXQueryFrameCountNV(@NativeType(value="Display *") long l, int n, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLXNVSwapGroup.nglXQueryFrameCountNV(l, n, MemoryUtil.memAddress(intBuffer)) != 0;
    }

    @NativeType(value="Bool")
    public static boolean glXResetFrameCountNV(@NativeType(value="Display *") long l, int n) {
        long l2 = GL.getCapabilitiesGLXClient().glXResetFrameCountNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, l2) != 0;
    }

    @NativeType(value="Bool")
    public static boolean glXQuerySwapGroupNV(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="GLuint *") int[] nArray, @NativeType(value="GLuint *") int[] nArray2) {
        long l3 = GL.getCapabilitiesGLXClient().glXQuerySwapGroupNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        return JNI.callPPPPI(l, l2, nArray, nArray2, l3) != 0;
    }

    @NativeType(value="Bool")
    public static boolean glXQueryMaxSwapGroupsNV(@NativeType(value="Display *") long l, int n, @NativeType(value="GLuint *") int[] nArray, @NativeType(value="GLuint *") int[] nArray2) {
        long l2 = GL.getCapabilitiesGLXClient().glXQueryMaxSwapGroupsNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        return JNI.callPPPI(l, n, nArray, nArray2, l2) != 0;
    }

    @NativeType(value="Bool")
    public static boolean glXQueryFrameCountNV(@NativeType(value="Display *") long l, int n, @NativeType(value="GLuint *") int[] nArray) {
        long l2 = GL.getCapabilitiesGLXClient().glXQueryFrameCountNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        return JNI.callPPI(l, n, nArray, l2) != 0;
    }
}

