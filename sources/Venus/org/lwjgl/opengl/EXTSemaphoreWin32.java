/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTSemaphoreWin32 {
    public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_EXT = 38279;
    public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_KMT_EXT = 38280;
    public static final int GL_DEVICE_LUID_EXT = 38297;
    public static final int GL_DEVICE_NODE_MASK_EXT = 38298;
    public static final int GL_LUID_SIZE_EXT = 8;
    public static final int GL_HANDLE_TYPE_D3D12_FENCE_EXT = 38292;
    public static final int GL_D3D12_FENCE_VALUE_EXT = 38293;

    protected EXTSemaphoreWin32() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glImportSemaphoreWin32HandleEXT, gLCapabilities.glImportSemaphoreWin32NameEXT);
    }

    public static native void nglImportSemaphoreWin32HandleEXT(int var0, int var1, long var2);

    public static void glImportSemaphoreWin32HandleEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        EXTSemaphoreWin32.nglImportSemaphoreWin32HandleEXT(n, n2, l);
    }

    public static native void nglImportSemaphoreWin32NameEXT(int var0, int var1, long var2);

    public static void glImportSemaphoreWin32NameEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        EXTSemaphoreWin32.nglImportSemaphoreWin32NameEXT(n, n2, l);
    }

    static {
        GL.initialize();
    }
}

