/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLNVDXInterop {
    public static final int WGL_ACCESS_READ_ONLY_NV = 0;
    public static final int WGL_ACCESS_READ_WRITE_NV = 1;
    public static final int WGL_ACCESS_WRITE_DISCARD_NV = 2;

    protected WGLNVDXInterop() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglDXSetResourceShareHandleNV, wGLCapabilities.wglDXOpenDeviceNV, wGLCapabilities.wglDXCloseDeviceNV, wGLCapabilities.wglDXRegisterObjectNV, wGLCapabilities.wglDXUnregisterObjectNV, wGLCapabilities.wglDXObjectAccessNV, wGLCapabilities.wglDXLockObjectsNV, wGLCapabilities.wglDXUnlockObjectsNV);
    }

    @NativeType(value="BOOL")
    public static boolean wglDXSetResourceShareHandleNV(@NativeType(value="void *") long l, @NativeType(value="HANDLE") long l2) {
        long l3 = GL.getCapabilitiesWGL().wglDXSetResourceShareHandleNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPI(l, l2, l3) != 0;
    }

    @NativeType(value="HANDLE")
    public static long wglDXOpenDeviceNV(@NativeType(value="void *") long l) {
        long l2 = GL.getCapabilitiesWGL().wglDXOpenDeviceNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, l2);
    }

    @NativeType(value="BOOL")
    public static boolean wglDXCloseDeviceNV(@NativeType(value="HANDLE") long l) {
        long l2 = GL.getCapabilitiesWGL().wglDXCloseDeviceNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    @NativeType(value="HANDLE")
    public static long wglDXRegisterObjectNV(@NativeType(value="HANDLE") long l, @NativeType(value="void *") long l2, @NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        long l3 = GL.getCapabilitiesWGL().wglDXRegisterObjectNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPP(l, l2, n, n2, n3, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglDXUnregisterObjectNV(@NativeType(value="HANDLE") long l, @NativeType(value="HANDLE") long l2) {
        long l3 = GL.getCapabilitiesWGL().wglDXUnregisterObjectNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPI(l, l2, l3) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglDXObjectAccessNV(@NativeType(value="HANDLE") long l, @NativeType(value="GLenum") int n) {
        long l2 = GL.getCapabilitiesWGL().wglDXObjectAccessNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, l2) != 0;
    }

    public static int nwglDXLockObjectsNV(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglDXLockObjectsNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, n, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglDXLockObjectsNV(@NativeType(value="HANDLE") long l, @NativeType(value="HANDLE *") PointerBuffer pointerBuffer) {
        return WGLNVDXInterop.nwglDXLockObjectsNV(l, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer)) != 0;
    }

    public static int nwglDXUnlockObjectsNV(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglDXUnlockObjectsNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, n, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglDXUnlockObjectsNV(@NativeType(value="HANDLE") long l, @NativeType(value="HANDLE *") PointerBuffer pointerBuffer) {
        return WGLNVDXInterop.nwglDXUnlockObjectsNV(l, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer)) != 0;
    }
}

