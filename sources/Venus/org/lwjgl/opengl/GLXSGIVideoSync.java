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

public class GLXSGIVideoSync {
    protected GLXSGIVideoSync() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXGetVideoSyncSGI, gLXCapabilities.glXWaitVideoSyncSGI);
    }

    public static int nglXGetVideoSyncSGI(long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXGetVideoSyncSGI;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.callPI(l, l2);
    }

    @NativeType(value="GLint")
    public static int glXGetVideoSyncSGI(@NativeType(value="unsigned int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLXSGIVideoSync.nglXGetVideoSyncSGI(MemoryUtil.memAddress(intBuffer));
    }

    public static int nglXWaitVideoSyncSGI(int n, int n2, long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXWaitVideoSyncSGI;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.callPI(n, n2, l, l2);
    }

    @NativeType(value="GLint")
    public static int glXWaitVideoSyncSGI(int n, int n2, @NativeType(value="unsigned int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLXSGIVideoSync.nglXWaitVideoSyncSGI(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="GLint")
    public static int glXGetVideoSyncSGI(@NativeType(value="unsigned int *") int[] nArray) {
        long l = GL.getCapabilitiesGLXClient().glXGetVideoSyncSGI;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        return JNI.callPI(nArray, l);
    }

    @NativeType(value="GLint")
    public static int glXWaitVideoSyncSGI(int n, int n2, @NativeType(value="unsigned int *") int[] nArray) {
        long l = GL.getCapabilitiesGLXClient().glXWaitVideoSyncSGI;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        return JNI.callPI(n, n2, nArray, l);
    }
}

