/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class SOFTLoopback {
    public static final int ALC_BYTE_SOFT = 5120;
    public static final int ALC_UNSIGNED_BYTE_SOFT = 5121;
    public static final int ALC_SHORT_SOFT = 5122;
    public static final int ALC_UNSIGNED_SHORT_SOFT = 5123;
    public static final int ALC_INT_SOFT = 5124;
    public static final int ALC_UNSIGNED_INT_SOFT = 5125;
    public static final int ALC_FLOAT_SOFT = 5126;
    public static final int ALC_MONO_SOFT = 5376;
    public static final int ALC_STEREO_SOFT = 5377;
    public static final int ALC_QUAD_SOFT = 5379;
    public static final int ALC_5POINT1_SOFT = 5380;
    public static final int ALC_6POINT1_SOFT = 5381;
    public static final int ALC_7POINT1_SOFT = 5382;
    public static final int ALC_FORMAT_CHANNELS_SOFT = 6544;
    public static final int ALC_FORMAT_TYPE_SOFT = 6545;

    protected SOFTLoopback() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcLoopbackOpenDeviceSOFT, aLCCapabilities.alcIsRenderFormatSupportedSOFT, aLCCapabilities.alcRenderSamplesSOFT);
    }

    public static long nalcLoopbackOpenDeviceSOFT(long l) {
        long l2 = ALC.getICD().alcLoopbackOpenDeviceSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="ALCdevice *")
    public static long alcLoopbackOpenDeviceSOFT(@Nullable @NativeType(value="ALCchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
        }
        return SOFTLoopback.nalcLoopbackOpenDeviceSOFT(MemoryUtil.memAddressSafe(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCdevice *")
    public static long alcLoopbackOpenDeviceSOFT(@Nullable @NativeType(value="ALCchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            long l2 = SOFTLoopback.nalcLoopbackOpenDeviceSOFT(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="ALCboolean")
    public static boolean alcIsRenderFormatSupportedSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCsizei") int n, @NativeType(value="ALCenum") int n2, @NativeType(value="ALCenum") int n3) {
        long l2 = ALC.getICD().alcIsRenderFormatSupportedSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.invokePZ(l, n, n2, n3, l2);
    }

    public static void nalcRenderSamplesSOFT(long l, long l2, int n) {
        long l3 = ALC.getICD().alcRenderSamplesSOFT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        JNI.invokePPV(l, l2, n, l3);
    }

    @NativeType(value="ALCvoid")
    public static void alcRenderSamplesSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") ByteBuffer byteBuffer, @NativeType(value="ALCsizei") int n) {
        SOFTLoopback.nalcRenderSamplesSOFT(l, MemoryUtil.memAddress(byteBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcRenderSamplesSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") ShortBuffer shortBuffer, @NativeType(value="ALCsizei") int n) {
        SOFTLoopback.nalcRenderSamplesSOFT(l, MemoryUtil.memAddress(shortBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcRenderSamplesSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") IntBuffer intBuffer, @NativeType(value="ALCsizei") int n) {
        SOFTLoopback.nalcRenderSamplesSOFT(l, MemoryUtil.memAddress(intBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcRenderSamplesSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") FloatBuffer floatBuffer, @NativeType(value="ALCsizei") int n) {
        SOFTLoopback.nalcRenderSamplesSOFT(l, MemoryUtil.memAddress(floatBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcRenderSamplesSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") short[] sArray, @NativeType(value="ALCsizei") int n) {
        long l2 = ALC.getICD().alcRenderSamplesSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePPV(l, sArray, n, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcRenderSamplesSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") int[] nArray, @NativeType(value="ALCsizei") int n) {
        long l2 = ALC.getICD().alcRenderSamplesSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePPV(l, nArray, n, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcRenderSamplesSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") float[] fArray, @NativeType(value="ALCsizei") int n) {
        long l2 = ALC.getICD().alcRenderSamplesSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePPV(l, fArray, n, l2);
    }
}

