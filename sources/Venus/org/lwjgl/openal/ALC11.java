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
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ALC11
extends ALC10 {
    public static final int ALC_MONO_SOURCES = 4112;
    public static final int ALC_STEREO_SOURCES = 4113;
    public static final int ALC_DEFAULT_ALL_DEVICES_SPECIFIER = 4114;
    public static final int ALC_ALL_DEVICES_SPECIFIER = 4115;
    public static final int ALC_CAPTURE_DEVICE_SPECIFIER = 784;
    public static final int ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER = 785;
    public static final int ALC_CAPTURE_SAMPLES = 786;

    protected ALC11() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcCaptureOpenDevice, aLCCapabilities.alcCaptureCloseDevice, aLCCapabilities.alcCaptureStart, aLCCapabilities.alcCaptureStop, aLCCapabilities.alcCaptureSamples);
    }

    public static long nalcCaptureOpenDevice(long l, int n, int n2, int n3) {
        long l2 = ALC.getICD().alcCaptureOpenDevice;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return JNI.invokePP(l, n, n2, n3, l2);
    }

    @NativeType(value="ALCdevice *")
    public static long alcCaptureOpenDevice(@Nullable @NativeType(value="ALCchar const *") ByteBuffer byteBuffer, @NativeType(value="ALCuint") int n, @NativeType(value="ALCenum") int n2, @NativeType(value="ALCsizei") int n3) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
        }
        return ALC11.nalcCaptureOpenDevice(MemoryUtil.memAddressSafe(byteBuffer), n, n2, n3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCdevice *")
    public static long alcCaptureOpenDevice(@Nullable @NativeType(value="ALCchar const *") CharSequence charSequence, @NativeType(value="ALCuint") int n, @NativeType(value="ALCenum") int n2, @NativeType(value="ALCsizei") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            long l2 = ALC11.nalcCaptureOpenDevice(l, n, n2, n3);
            return l2;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    @NativeType(value="ALCboolean")
    public static boolean alcCaptureCloseDevice(@NativeType(value="ALCdevice *") long l) {
        long l2 = ALC.getICD().alcCaptureCloseDevice;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.invokePZ(l, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureStart(@NativeType(value="ALCdevice *") long l) {
        long l2 = ALC.getICD().alcCaptureStart;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureStop(@NativeType(value="ALCdevice *") long l) {
        long l2 = ALC.getICD().alcCaptureStop;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    public static void nalcCaptureSamples(long l, long l2, int n) {
        long l3 = ALC.getICD().alcCaptureSamples;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        JNI.invokePPV(l, l2, n, l3);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") ByteBuffer byteBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.nalcCaptureSamples(l, MemoryUtil.memAddress(byteBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") ShortBuffer shortBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.nalcCaptureSamples(l, MemoryUtil.memAddress(shortBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") IntBuffer intBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.nalcCaptureSamples(l, MemoryUtil.memAddress(intBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") FloatBuffer floatBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.nalcCaptureSamples(l, MemoryUtil.memAddress(floatBuffer), n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") short[] sArray, @NativeType(value="ALCsizei") int n) {
        long l2 = ALC.getICD().alcCaptureSamples;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePPV(l, sArray, n, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") int[] nArray, @NativeType(value="ALCsizei") int n) {
        long l2 = ALC.getICD().alcCaptureSamples;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePPV(l, nArray, n, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") float[] fArray, @NativeType(value="ALCsizei") int n) {
        long l2 = ALC.getICD().alcCaptureSamples;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.invokePPV(l, fArray, n, l2);
    }
}

