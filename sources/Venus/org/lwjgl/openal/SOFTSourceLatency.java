/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.LongBuffer;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class SOFTSourceLatency {
    public static final int AL_SAMPLE_OFFSET_LATENCY_SOFT = 4608;
    public static final int AL_SEC_OFFSET_LATENCY_SOFT = 4609;

    protected SOFTSourceLatency() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCapabilities aLCapabilities) {
        return Checks.checkFunctions(aLCapabilities.alSourcedSOFT, aLCapabilities.alSource3dSOFT, aLCapabilities.alSourcedvSOFT, aLCapabilities.alGetSourcedSOFT, aLCapabilities.alGetSource3dSOFT, aLCapabilities.alGetSourcedvSOFT, aLCapabilities.alSourcei64SOFT, aLCapabilities.alSource3i64SOFT, aLCapabilities.alSourcei64vSOFT, aLCapabilities.alGetSourcei64SOFT, aLCapabilities.alGetSource3i64SOFT, aLCapabilities.alGetSourcei64vSOFT);
    }

    @NativeType(value="ALvoid")
    public static void alSourcedSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble") double d) {
        long l = AL.getICD().alSourcedSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, d, l);
    }

    @NativeType(value="ALvoid")
    public static void alSource3dSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble") double d, @NativeType(value="ALdouble") double d2, @NativeType(value="ALdouble") double d3) {
        long l = AL.getICD().alSource3dSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, d, d2, d3, l);
    }

    public static void nalSourcedvSOFT(int n, int n2, long l) {
        long l2 = AL.getICD().alSourcedvSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourcedvSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        SOFTSourceLatency.nalSourcedvSOFT(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static void nalGetSourcedSOFT(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourcedSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcedSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        SOFTSourceLatency.nalGetSourcedSOFT(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static double alGetSourcedSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            SOFTSourceLatency.nalGetSourcedSOFT(n, n2, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetSource3dSOFT(int n, int n2, long l, long l2, long l3) {
        long l4 = AL.getICD().alGetSource3dSOFT;
        if (Checks.CHECKS) {
            Checks.check(l4);
        }
        JNI.invokePPPV(n, n2, l, l2, l3, l4);
    }

    @NativeType(value="ALvoid")
    public static void alGetSource3dSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble *") DoubleBuffer doubleBuffer, @NativeType(value="ALdouble *") DoubleBuffer doubleBuffer2, @NativeType(value="ALdouble *") DoubleBuffer doubleBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
            Checks.check((Buffer)doubleBuffer2, 1);
            Checks.check((Buffer)doubleBuffer3, 1);
        }
        SOFTSourceLatency.nalGetSource3dSOFT(n, n2, MemoryUtil.memAddress(doubleBuffer), MemoryUtil.memAddress(doubleBuffer2), MemoryUtil.memAddress(doubleBuffer3));
    }

    public static void nalGetSourcedvSOFT(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourcedvSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcedvSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        SOFTSourceLatency.nalGetSourcedvSOFT(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alSourcei64SOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT") long l) {
        long l2 = AL.getICD().alSourcei64SOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokeJV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSource3i64SOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT") long l, @NativeType(value="ALint64SOFT") long l2, @NativeType(value="ALint64SOFT") long l3) {
        long l4 = AL.getICD().alSource3i64SOFT;
        if (Checks.CHECKS) {
            Checks.check(l4);
        }
        JNI.invokeJJJV(n, n2, l, l2, l3, l4);
    }

    public static void nalSourcei64vSOFT(int n, int n2, long l) {
        long l2 = AL.getICD().alSourcei64vSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourcei64vSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        SOFTSourceLatency.nalSourcei64vSOFT(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    public static void nalGetSourcei64SOFT(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourcei64SOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcei64SOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        SOFTSourceLatency.nalGetSourcei64SOFT(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static long alGetSourcei64SOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            SOFTSourceLatency.nalGetSourcei64SOFT(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetSource3i64SOFT(int n, int n2, long l, long l2, long l3) {
        long l4 = AL.getICD().alGetSource3i64SOFT;
        if (Checks.CHECKS) {
            Checks.check(l4);
        }
        JNI.invokePPPV(n, n2, l, l2, l3, l4);
    }

    @NativeType(value="ALvoid")
    public static void alGetSource3i64SOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT *") LongBuffer longBuffer, @NativeType(value="ALint64SOFT *") LongBuffer longBuffer2, @NativeType(value="ALint64SOFT *") LongBuffer longBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
            Checks.check((Buffer)longBuffer2, 1);
            Checks.check((Buffer)longBuffer3, 1);
        }
        SOFTSourceLatency.nalGetSource3i64SOFT(n, n2, MemoryUtil.memAddress(longBuffer), MemoryUtil.memAddress(longBuffer2), MemoryUtil.memAddress(longBuffer3));
    }

    public static void nalGetSourcei64vSOFT(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourcei64vSOFT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcei64vSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        SOFTSourceLatency.nalGetSourcei64vSOFT(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alSourcedvSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble const *") double[] dArray) {
        long l = AL.getICD().alSourcedvSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.invokePV(n, n2, dArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcedSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble *") double[] dArray) {
        long l = AL.getICD().alGetSourcedSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.invokePV(n, n2, dArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSource3dSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble *") double[] dArray, @NativeType(value="ALdouble *") double[] dArray2, @NativeType(value="ALdouble *") double[] dArray3) {
        long l = AL.getICD().alGetSource3dSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
            Checks.check(dArray2, 1);
            Checks.check(dArray3, 1);
        }
        JNI.invokePPPV(n, n2, dArray, dArray2, dArray3, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcedvSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALdouble *") double[] dArray) {
        long l = AL.getICD().alGetSourcedvSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.invokePV(n, n2, dArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourcei64vSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT const *") long[] lArray) {
        long l = AL.getICD().alSourcei64vSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.invokePV(n, n2, lArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcei64SOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT *") long[] lArray) {
        long l = AL.getICD().alGetSourcei64SOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.invokePV(n, n2, lArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSource3i64SOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT *") long[] lArray, @NativeType(value="ALint64SOFT *") long[] lArray2, @NativeType(value="ALint64SOFT *") long[] lArray3) {
        long l = AL.getICD().alGetSource3i64SOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
            Checks.check(lArray2, 1);
            Checks.check(lArray3, 1);
        }
        JNI.invokePPPV(n, n2, lArray, lArray2, lArray3, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcei64vSOFT(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint64SOFT *") long[] lArray) {
        long l = AL.getICD().alGetSourcei64vSOFT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.invokePV(n, n2, lArray, l);
    }
}

