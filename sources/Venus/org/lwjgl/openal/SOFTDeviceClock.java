/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.LongBuffer;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class SOFTDeviceClock {
    public static final int ALC_DEVICE_CLOCK_SOFT = 5632;
    public static final int ALC_DEVICE_LATENCY_SOFT = 5633;
    public static final int ALC_DEVICE_CLOCK_LATENCY_SOFT = 5634;
    public static final int AL_SAMPLE_OFFSET_CLOCK_SOFT = 4610;
    public static final int AL_SEC_OFFSET_CLOCK_SOFT = 4611;

    protected SOFTDeviceClock() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCCapabilities aLCCapabilities) {
        return Checks.checkFunctions(aLCCapabilities.alcGetInteger64vSOFT);
    }

    public static void nalcGetInteger64vSOFT(long l, int n, int n2, long l2) {
        long l3 = ALC.getICD().alcGetInteger64vSOFT;
        if (Checks.CHECKS) {
            Checks.check(l3);
        }
        JNI.invokePPV(l, n, n2, l2, l3);
    }

    @NativeType(value="ALCvoid")
    public static void alcGetInteger64vSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n, @NativeType(value="ALCint64SOFT *") LongBuffer longBuffer) {
        SOFTDeviceClock.nalcGetInteger64vSOFT(l, n, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCvoid")
    public static long alcGetInteger64vSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            SOFTDeviceClock.nalcGetInteger64vSOFT(l, n, 1, MemoryUtil.memAddress(longBuffer));
            long l2 = longBuffer.get(0);
            return l2;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALCvoid")
    public static void alcGetInteger64vSOFT(@NativeType(value="ALCdevice *") long l, @NativeType(value="ALCenum") int n, @NativeType(value="ALCint64SOFT *") long[] lArray) {
        long l2 = ALC.getICD().alcGetInteger64vSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePPV(l, n, lArray.length, lArray, l2);
    }
}

