/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTMemoryObjectWin32 {
    public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_EXT = 38279;
    public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_KMT_EXT = 38280;
    public static final int GL_DEVICE_LUID_EXT = 38297;
    public static final int GL_DEVICE_NODE_MASK_EXT = 38298;
    public static final int GL_LUID_SIZE_EXT = 8;
    public static final int GL_HANDLE_TYPE_D3D12_TILEPOOL_EXT = 38281;
    public static final int GL_HANDLE_TYPE_D3D12_RESOURCE_EXT = 38282;
    public static final int GL_HANDLE_TYPE_D3D11_IMAGE_EXT = 38283;
    public static final int GL_HANDLE_TYPE_D3D11_IMAGE_KMT_EXT = 38284;

    protected EXTMemoryObjectWin32() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glImportMemoryWin32HandleEXT, gLCapabilities.glImportMemoryWin32NameEXT);
    }

    public static native void nglImportMemoryWin32HandleEXT(int var0, long var1, int var3, long var4);

    public static void glImportMemoryWin32HandleEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64") long l, @NativeType(value="GLenum") int n2, @NativeType(value="void *") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        EXTMemoryObjectWin32.nglImportMemoryWin32HandleEXT(n, l, n2, l2);
    }

    public static native void nglImportMemoryWin32NameEXT(int var0, long var1, int var3, long var4);

    public static void glImportMemoryWin32NameEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64") long l, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        EXTMemoryObjectWin32.nglImportMemoryWin32NameEXT(n, l, n2, l2);
    }

    static {
        GL.initialize();
    }
}

