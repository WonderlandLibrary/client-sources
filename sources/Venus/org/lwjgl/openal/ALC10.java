/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ALC10 {
    public static final int ALC_INVALID = -1;
    public static final int ALC_FALSE = 0;
    public static final int ALC_TRUE = 1;
    public static final int ALC_FREQUENCY = 4103;
    public static final int ALC_REFRESH = 4104;
    public static final int ALC_SYNC = 4105;
    public static final int ALC_NO_ERROR = 0;
    public static final int ALC_INVALID_DEVICE = 40961;
    public static final int ALC_INVALID_CONTEXT = 40962;
    public static final int ALC_INVALID_ENUM = 40963;
    public static final int ALC_INVALID_VALUE = 40964;
    public static final int ALC_OUT_OF_MEMORY = 40965;
    public static final int ALC_DEFAULT_DEVICE_SPECIFIER = 4100;
    public static final int ALC_DEVICE_SPECIFIER = 4101;
    public static final int ALC_EXTENSIONS = 4102;
    public static final int ALC_MAJOR_VERSION = 4096;
    public static final int ALC_MINOR_VERSION = 4097;
    public static final int ALC_ATTRIBUTES_SIZE = 4098;
    public static final int ALC_ALL_ATTRIBUTES = 4099;

    protected ALC10() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcOpenDevice, aLCCapabilities.alcCloseDevice, aLCCapabilities.alcCreateContext, aLCCapabilities.alcMakeContextCurrent, aLCCapabilities.alcProcessContext, aLCCapabilities.alcSuspendContext, aLCCapabilities.alcDestroyContext, aLCCapabilities.alcGetCurrentContext, aLCCapabilities.alcGetContextsDevice, aLCCapabilities.alcIsExtensionPresent, aLCCapabilities.alcGetProcAddress, aLCCapabilities.alcGetEnumValue, aLCCapabilities.alcGetError, aLCCapabilities.alcGetString, aLCCapabilities.alcGetIntegerv);
    }

    public static long nalcOpenDevice(long l) {
        long l2 = ALC.getICD().alcOpenDevice;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="ALCdevice *")
    public static long alcOpenDevice(@Nullable @NativeType(value="ALCchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
        }
        return ALC10.nalcOpenDevice(MemoryUtil.memAddressSafe(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCdevice *")
    public static long alcOpenDevice(@Nullable @NativeType(value="ALCchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8Safe(charSequence, false);
            long l = charSequence == null ? 0L : memoryStack.getPointerAddress();
            long l2 = ALC10.nalcOpenDevice(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="ALCboolean")
    public static boolean alcCloseDevice(@NativeType(value="ALCdevice const *") long l) {
        long l2 = ALC.getICD().alcCloseDevice;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePZ(l, l2);
    }

    public static long nalcCreateContext(long l, long l2) {
        long l3 = ALC.getICD().alcCreateContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="ALCcontext *")
    public static long alcCreateContext(@NativeType(value="ALCdevice const *") long l, @Nullable @NativeType(value="ALCint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return ALC10.nalcCreateContext(l, MemoryUtil.memAddressSafe(intBuffer));
    }

    @NativeType(value="ALCboolean")
    public static boolean alcMakeContextCurrent(@NativeType(value="ALCcontext *") long l) {
        long l2 = ALC.getICD().alcMakeContextCurrent;
        return JNI.invokePZ(l, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcProcessContext(@NativeType(value="ALCcontext *") long l) {
        long l2 = ALC.getICD().alcProcessContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcSuspendContext(@NativeType(value="ALCcontext *") long l) {
        long l2 = ALC.getICD().alcSuspendContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcDestroyContext(@NativeType(value="ALCcontext *") long l) {
        long l2 = ALC.getICD().alcDestroyContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(l, l2);
    }

    @NativeType(value="ALCcontext *")
    public static long alcGetCurrentContext() {
        long l = ALC.getICD().alcGetCurrentContext;
        return JNI.invokeP(l);
    }

    @NativeType(value="ALCdevice *")
    public static long alcGetContextsDevice(@NativeType(value="ALCcontext *") long l) {
        long l2 = ALC.getICD().alcGetContextsDevice;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static boolean nalcIsExtensionPresent(long l, long l2) {
        long l3 = ALC.getICD().alcIsExtensionPresent;
        return JNI.invokePPZ(l, l2, l3);
    }

    @NativeType(value="ALCboolean")
    public static boolean alcIsExtensionPresent(@NativeType(value="ALCdevice const *") long l, @NativeType(value="ALCchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ALC10.nalcIsExtensionPresent(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCboolean")
    public static boolean alcIsExtensionPresent(@NativeType(value="ALCdevice const *") long l, @NativeType(value="ALCchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            boolean bl = ALC10.nalcIsExtensionPresent(l, l2);
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nalcGetProcAddress(long l, long l2) {
        long l3 = ALC.getICD().alcGetProcAddress;
        return JNI.invokePPP(l, l2, l3);
    }

    @NativeType(value="void *")
    public static long alcGetProcAddress(@NativeType(value="ALCdevice const *") long l, @NativeType(value="ALchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ALC10.nalcGetProcAddress(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void *")
    public static long alcGetProcAddress(@NativeType(value="ALCdevice const *") long l, @NativeType(value="ALchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            long l3 = ALC10.nalcGetProcAddress(l, l2);
            return l3;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static int nalcGetEnumValue(long l, long l2) {
        long l3 = ALC.getICD().alcGetEnumValue;
        return JNI.invokePPI(l, l2, l3);
    }

    @NativeType(value="ALCenum")
    public static int alcGetEnumValue(@NativeType(value="ALCdevice const *") long l, @NativeType(value="ALCchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ALC10.nalcGetEnumValue(l, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCenum")
    public static int alcGetEnumValue(@NativeType(value="ALCdevice const *") long l, @NativeType(value="ALCchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            int n2 = ALC10.nalcGetEnumValue(l, l2);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="ALCenum")
    public static int alcGetError(@NativeType(value="ALCdevice *") long l) {
        long l2 = ALC.getICD().alcGetError;
        return JNI.invokePI(l, l2);
    }

    public static long nalcGetString(long l, int n) {
        long l2 = ALC.getICD().alcGetString;
        return JNI.invokePP(l, n, l2);
    }

    @Nullable
    @NativeType(value="ALCchar const *")
    public static String alcGetString(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n) {
        long l2 = ALC10.nalcGetString(l, n);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static void nalcGetIntegerv(long l, int n, int n2, long l2) {
        long l3 = ALC.getICD().alcGetIntegerv;
        JNI.invokePPV(l, n, n2, l2, l3);
    }

    @NativeType(value="ALCvoid")
    public static void alcGetIntegerv(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n, @NativeType(value="ALCint *") IntBuffer intBuffer) {
        ALC10.nalcGetIntegerv(l, n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCvoid")
    public static int alcGetInteger(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ALC10.nalcGetIntegerv(l, n, 1, MemoryUtil.memAddress(intBuffer));
            int n3 = intBuffer.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALCcontext *")
    public static long alcCreateContext(@NativeType(value="ALCdevice const *") long l, @Nullable @NativeType(value="ALCint const *") int[] nArray) {
        long l2 = ALC.getICD().alcCreateContext;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        return JNI.invokePPP(l, nArray, l2);
    }

    @NativeType(value="ALCvoid")
    public static void alcGetIntegerv(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n, @NativeType(value="ALCint *") int[] nArray) {
        long l2 = ALC.getICD().alcGetIntegerv;
        JNI.invokePPV(l, n, nArray.length, nArray, l2);
    }
}

