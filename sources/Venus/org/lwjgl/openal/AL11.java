/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AL11
extends AL10 {
    public static final int AL_SEC_OFFSET = 4132;
    public static final int AL_SAMPLE_OFFSET = 4133;
    public static final int AL_BYTE_OFFSET = 4134;
    public static final int AL_STATIC = 4136;
    public static final int AL_STREAMING = 4137;
    public static final int AL_UNDETERMINED = 4144;
    public static final int AL_ILLEGAL_COMMAND = 40964;
    public static final int AL_SPEED_OF_SOUND = 49155;
    public static final int AL_LINEAR_DISTANCE = 53251;
    public static final int AL_LINEAR_DISTANCE_CLAMPED = 53252;
    public static final int AL_EXPONENT_DISTANCE = 53253;
    public static final int AL_EXPONENT_DISTANCE_CLAMPED = 53254;

    protected AL11() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCapabilities aLCapabilities) {
        return Checks.checkFunctions(aLCapabilities.alListener3i, aLCapabilities.alGetListeneriv, aLCapabilities.alSource3i, aLCapabilities.alListeneriv, aLCapabilities.alSourceiv, aLCapabilities.alBufferf, aLCapabilities.alBuffer3f, aLCapabilities.alBufferfv, aLCapabilities.alBufferi, aLCapabilities.alBuffer3i, aLCapabilities.alBufferiv, aLCapabilities.alGetBufferiv, aLCapabilities.alGetBufferfv, aLCapabilities.alSpeedOfSound);
    }

    @NativeType(value="ALvoid")
    public static void alListener3i(@NativeType(value="ALenum") int n, @NativeType(value="ALint") int n2, @NativeType(value="ALint") int n3, @NativeType(value="ALint") int n4) {
        long l = AL.getICD().alListener3i;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, n3, n4, l);
    }

    public static void nalGetListeneriv(int n, long l) {
        long l2 = AL.getICD().alGetListeneriv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetListeneriv(@NativeType(value="ALenum") int n, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL11.nalGetListeneriv(n, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alSource3i(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint") int n3, @NativeType(value="ALint") int n4, @NativeType(value="ALint") int n5) {
        long l = AL.getICD().alSource3i;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, n3, n4, n5, l);
    }

    public static void nalListeneriv(int n, long l) {
        long l2 = AL.getICD().alListeneriv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alListeneriv(@NativeType(value="ALenum") int n, @NativeType(value="ALint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL11.nalListeneriv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalSourceiv(int n, int n2, long l) {
        long l2 = AL.getICD().alSourceiv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourceiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL11.nalSourceiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alBufferf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alBufferf;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, f, l);
    }

    @NativeType(value="ALvoid")
    public static void alBuffer3f(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat") float f, @NativeType(value="ALfloat") float f2, @NativeType(value="ALfloat") float f3) {
        long l = AL.getICD().alBuffer3f;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, f, f2, f3, l);
    }

    public static void nalBufferfv(int n, int n2, long l) {
        long l2 = AL.getICD().alBufferfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alBufferfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL11.nalBufferfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alBufferi(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint") int n3) {
        long l = AL.getICD().alBufferi;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, n3, l);
    }

    @NativeType(value="ALvoid")
    public static void alBuffer3i(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint") int n3, @NativeType(value="ALint") int n4, @NativeType(value="ALint") int n5) {
        long l = AL.getICD().alBuffer3i;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(n, n2, n3, n4, n5, l);
    }

    public static void nalBufferiv(int n, int n2, long l) {
        long l2 = AL.getICD().alBufferiv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alBufferiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL11.nalBufferiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalGetBufferiv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetBufferiv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL11.nalGetBufferiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalGetBufferfv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetBufferfv;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL11.nalGetBufferfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alSpeedOfSound(@NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alSpeedOfSound;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokeV(f, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetListeneriv(@NativeType(value="ALenum") int n, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetListeneriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alListeneriv(@NativeType(value="ALenum") int n, @NativeType(value="ALint const *") int[] nArray) {
        long l = AL.getICD().alListeneriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourceiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") int[] nArray) {
        long l = AL.getICD().alSourceiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alBufferfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") float[] fArray) {
        long l = AL.getICD().alBufferfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alBufferiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint const *") int[] nArray) {
        long l = AL.getICD().alBufferiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetBufferiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferfv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetBufferfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }
}

