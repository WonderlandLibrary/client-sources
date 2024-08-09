/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLNVSwapGroup {
    protected WGLNVSwapGroup() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglJoinSwapGroupNV, wGLCapabilities.wglBindSwapBarrierNV, wGLCapabilities.wglQuerySwapGroupNV, wGLCapabilities.wglQueryMaxSwapGroupsNV, wGLCapabilities.wglQueryFrameCountNV, wGLCapabilities.wglResetFrameCountNV);
    }

    @NativeType(value="BOOL")
    public static boolean wglJoinSwapGroupNV(@NativeType(value="HDC") long l, @NativeType(value="GLuint") int n) {
        long l2 = GL.getCapabilitiesWGL().wglJoinSwapGroupNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglBindSwapBarrierNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        long l = GL.getCapabilitiesWGL().wglBindSwapBarrierNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callI(n, n2, l) != 0;
    }

    public static int nwglQuerySwapGroupNV(long l, long l2, long l3) {
        long l4 = GL.getCapabilitiesWGL().wglQuerySwapGroupNV;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        return JNI.callPPPI(l, l2, l3, l4);
    }

    @NativeType(value="BOOL")
    public static boolean wglQuerySwapGroupNV(@NativeType(value="HDC") long l, @NativeType(value="GLuint *") IntBuffer intBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        return WGLNVSwapGroup.nwglQuerySwapGroupNV(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2)) != 0;
    }

    public static int nwglQueryMaxSwapGroupsNV(long l, long l2, long l3) {
        long l4 = GL.getCapabilitiesWGL().wglQueryMaxSwapGroupsNV;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        return JNI.callPPPI(l, l2, l3, l4);
    }

    @NativeType(value="BOOL")
    public static boolean wglQueryMaxSwapGroupsNV(@NativeType(value="HDC") long l, @NativeType(value="GLuint *") IntBuffer intBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        return WGLNVSwapGroup.nwglQueryMaxSwapGroupsNV(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2)) != 0;
    }

    public static int nwglQueryFrameCountNV(long l, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglQueryFrameCountNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglQueryFrameCountNV(@NativeType(value="HDC") long l, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return WGLNVSwapGroup.nwglQueryFrameCountNV(l, MemoryUtil.memAddress(intBuffer)) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglResetFrameCountNV(@NativeType(value="HDC") long l) {
        long l2 = GL.getCapabilitiesWGL().wglResetFrameCountNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglQuerySwapGroupNV(@NativeType(value="HDC") long l, @NativeType(value="GLuint *") int[] nArray, @NativeType(value="GLuint *") int[] nArray2) {
        long l2 = GL.getCapabilitiesWGL().wglQuerySwapGroupNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        return JNI.callPPPI(l, nArray, nArray2, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglQueryMaxSwapGroupsNV(@NativeType(value="HDC") long l, @NativeType(value="GLuint *") int[] nArray, @NativeType(value="GLuint *") int[] nArray2) {
        long l2 = GL.getCapabilitiesWGL().wglQueryMaxSwapGroupsNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        return JNI.callPPPI(l, nArray, nArray2, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglQueryFrameCountNV(@NativeType(value="HDC") long l, @NativeType(value="GLuint *") int[] nArray) {
        long l2 = GL.getCapabilitiesWGL().wglQueryFrameCountNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        return JNI.callPPI(l, nArray, l2) != 0;
    }
}

