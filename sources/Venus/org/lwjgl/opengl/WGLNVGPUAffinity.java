/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GPU_DEVICE;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLNVGPUAffinity {
    public static final int ERROR_INCOMPATIBLE_AFFINITY_MASKS_NV = 8400;
    public static final int ERROR_MISSING_AFFINITY_MASK_NV = 8401;

    protected WGLNVGPUAffinity() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglEnumGpusNV, wGLCapabilities.wglEnumGpuDevicesNV, wGLCapabilities.wglCreateAffinityDCNV, wGLCapabilities.wglEnumGpusFromAffinityDCNV, wGLCapabilities.wglDeleteDCNV);
    }

    public static int nwglEnumGpusNV(int n, long l) {
        long l2 = GL.getCapabilitiesWGL().wglEnumGpusNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.callPI(n, l, l2);
    }

    @NativeType(value="BOOL")
    public static boolean wglEnumGpusNV(@NativeType(value="UINT") int n, @NativeType(value="HGPUNV *") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        return WGLNVGPUAffinity.nwglEnumGpusNV(n, MemoryUtil.memAddress(pointerBuffer)) != 0;
    }

    public static int nwglEnumGpuDevicesNV(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglEnumGpuDevicesNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, n, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglEnumGpuDevicesNV(@NativeType(value="HGPUNV") long l, @NativeType(value="UINT") int n, @NativeType(value="PGPU_DEVICE") GPU_DEVICE gPU_DEVICE) {
        return WGLNVGPUAffinity.nwglEnumGpuDevicesNV(l, n, gPU_DEVICE.address()) != 0;
    }

    public static long nwglCreateAffinityDCNV(long l) {
        long l2 = GL.getCapabilitiesWGL().wglCreateAffinityDCNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.callPP(l, l2);
    }

    @NativeType(value="HDC")
    public static long wglCreateAffinityDCNV(@NativeType(value="HGPUNV const *") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT(pointerBuffer);
        }
        return WGLNVGPUAffinity.nwglCreateAffinityDCNV(MemoryUtil.memAddress(pointerBuffer));
    }

    public static int nwglEnumGpusFromAffinityDCNV(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglEnumGpusFromAffinityDCNV;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, n, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglEnumGpusFromAffinityDCNV(@NativeType(value="HDC") long l, @NativeType(value="UINT") int n, @NativeType(value="HGPUNV *") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        return WGLNVGPUAffinity.nwglEnumGpusFromAffinityDCNV(l, n, MemoryUtil.memAddress(pointerBuffer)) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglDeleteDCNV(@NativeType(value="HDC") long l) {
        long l2 = GL.getCapabilitiesWGL().wglDeleteDCNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }
}

