/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTCapture {
    public static final int ALC_CAPTURE_DEVICE_SPECIFIER = 784;
    public static final int ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER = 785;
    public static final int ALC_CAPTURE_SAMPLES = 786;

    protected EXTCapture() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcCaptureOpenDevice, aLCCapabilities.alcCaptureCloseDevice, aLCCapabilities.alcCaptureStart, aLCCapabilities.alcCaptureStop, aLCCapabilities.alcCaptureSamples);
    }

    public static long nalcCaptureOpenDevice(long l, int n, int n2, int n3) {
        return ALC11.nalcCaptureOpenDevice(l, n, n2, n3);
    }

    @NativeType(value="ALCdevice *")
    public static long alcCaptureOpenDevice(@Nullable @NativeType(value="ALCchar const *") ByteBuffer byteBuffer, @NativeType(value="ALCuint") int n, @NativeType(value="ALCenum") int n2, @NativeType(value="ALCsizei") int n3) {
        return ALC11.alcCaptureOpenDevice(byteBuffer, n, n2, n3);
    }

    @NativeType(value="ALCdevice *")
    public static long alcCaptureOpenDevice(@Nullable @NativeType(value="ALCchar const *") CharSequence charSequence, @NativeType(value="ALCuint") int n, @NativeType(value="ALCenum") int n2, @NativeType(value="ALCsizei") int n3) {
        return ALC11.alcCaptureOpenDevice(charSequence, n, n2, n3);
    }

    @NativeType(value="ALCboolean")
    public static boolean alcCaptureCloseDevice(@NativeType(value="ALCdevice *") long l) {
        return ALC11.alcCaptureCloseDevice(l);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureStart(@NativeType(value="ALCdevice *") long l) {
        ALC11.alcCaptureStart(l);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureStop(@NativeType(value="ALCdevice *") long l) {
        ALC11.alcCaptureStop(l);
    }

    public static void nalcCaptureSamples(long l, long l2, int n) {
        ALC11.nalcCaptureSamples(l, l2, n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") ByteBuffer byteBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.alcCaptureSamples(l, byteBuffer, n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") ShortBuffer shortBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.alcCaptureSamples(l, shortBuffer, n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") IntBuffer intBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.alcCaptureSamples(l, intBuffer, n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") FloatBuffer floatBuffer, @NativeType(value="ALCsizei") int n) {
        ALC11.alcCaptureSamples(l, floatBuffer, n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") short[] sArray, @NativeType(value="ALCsizei") int n) {
        ALC11.alcCaptureSamples(l, sArray, n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") int[] nArray, @NativeType(value="ALCsizei") int n) {
        ALC11.alcCaptureSamples(l, nArray, n);
    }

    @NativeType(value="ALCvoid")
    public static void alcCaptureSamples(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCvoid *") float[] fArray, @NativeType(value="ALCsizei") int n) {
        ALC11.alcCaptureSamples(l, fArray, n);
    }
}

