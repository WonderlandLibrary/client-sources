/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AL10 {
    public static final int AL_INVALID = -1;
    public static final int AL_NONE = 0;
    public static final int AL_FALSE = 0;
    public static final int AL_TRUE = 1;
    public static final int AL_NO_ERROR = 0;
    public static final int AL_INVALID_NAME = 40961;
    public static final int AL_INVALID_ENUM = 40962;
    public static final int AL_INVALID_VALUE = 40963;
    public static final int AL_INVALID_OPERATION = 40964;
    public static final int AL_OUT_OF_MEMORY = 40965;
    public static final int AL_DOPPLER_FACTOR = 49152;
    public static final int AL_DISTANCE_MODEL = 53248;
    public static final int AL_VENDOR = 45057;
    public static final int AL_VERSION = 45058;
    public static final int AL_RENDERER = 45059;
    public static final int AL_EXTENSIONS = 45060;
    public static final int AL_INVERSE_DISTANCE = 53249;
    public static final int AL_INVERSE_DISTANCE_CLAMPED = 53250;
    public static final int AL_SOURCE_ABSOLUTE = 513;
    public static final int AL_SOURCE_RELATIVE = 514;
    public static final int AL_POSITION = 4100;
    public static final int AL_VELOCITY = 4102;
    public static final int AL_GAIN = 4106;
    public static final int AL_CONE_INNER_ANGLE = 4097;
    public static final int AL_CONE_OUTER_ANGLE = 4098;
    public static final int AL_PITCH = 4099;
    public static final int AL_DIRECTION = 4101;
    public static final int AL_LOOPING = 4103;
    public static final int AL_BUFFER = 4105;
    public static final int AL_SOURCE_STATE = 4112;
    public static final int AL_CONE_OUTER_GAIN = 4130;
    public static final int AL_SOURCE_TYPE = 4135;
    public static final int AL_INITIAL = 4113;
    public static final int AL_PLAYING = 4114;
    public static final int AL_PAUSED = 4115;
    public static final int AL_STOPPED = 4116;
    public static final int AL_ORIENTATION = 4111;
    public static final int AL_BUFFERS_QUEUED = 4117;
    public static final int AL_BUFFERS_PROCESSED = 4118;
    public static final int AL_MIN_GAIN = 4109;
    public static final int AL_MAX_GAIN = 4110;
    public static final int AL_REFERENCE_DISTANCE = 4128;
    public static final int AL_ROLLOFF_FACTOR = 4129;
    public static final int AL_MAX_DISTANCE = 4131;
    public static final int AL_FREQUENCY = 8193;
    public static final int AL_BITS = 8194;
    public static final int AL_CHANNELS = 8195;
    public static final int AL_SIZE = 8196;
    public static final int AL_FORMAT_MONO8 = 4352;
    public static final int AL_FORMAT_MONO16 = 4353;
    public static final int AL_FORMAT_STEREO8 = 4354;
    public static final int AL_FORMAT_STEREO16 = 4355;
    public static final int AL_UNUSED = 8208;
    public static final int AL_PENDING = 8209;
    public static final int AL_PROCESSED = 8210;

    protected AL10() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCapabilities aLCapabilities) {
        return Checks.checkFunctions(aLCapabilities.alGetError, aLCapabilities.alEnable, aLCapabilities.alDisable, aLCapabilities.alIsEnabled, aLCapabilities.alGetBoolean, aLCapabilities.alGetInteger, aLCapabilities.alGetFloat, aLCapabilities.alGetDouble, aLCapabilities.alGetBooleanv, aLCapabilities.alGetIntegerv, aLCapabilities.alGetFloatv, aLCapabilities.alGetDoublev, aLCapabilities.alGetString, aLCapabilities.alDistanceModel, aLCapabilities.alDopplerFactor, aLCapabilities.alDopplerVelocity, aLCapabilities.alListenerf, aLCapabilities.alListeneri, aLCapabilities.alListener3f, aLCapabilities.alListenerfv, aLCapabilities.alGetListenerf, aLCapabilities.alGetListeneri, aLCapabilities.alGetListener3f, aLCapabilities.alGetListenerfv, aLCapabilities.alGenSources, aLCapabilities.alDeleteSources, aLCapabilities.alIsSource, aLCapabilities.alSourcef, aLCapabilities.alSource3f, aLCapabilities.alSourcefv, aLCapabilities.alSourcei, aLCapabilities.alGetSourcef, aLCapabilities.alGetSource3f, aLCapabilities.alGetSourcefv, aLCapabilities.alGetSourcei, aLCapabilities.alGetSourceiv, aLCapabilities.alSourceQueueBuffers, aLCapabilities.alSourceUnqueueBuffers, aLCapabilities.alSourcePlay, aLCapabilities.alSourcePause, aLCapabilities.alSourceStop, aLCapabilities.alSourceRewind, aLCapabilities.alSourcePlayv, aLCapabilities.alSourcePausev, aLCapabilities.alSourceStopv, aLCapabilities.alSourceRewindv, aLCapabilities.alGenBuffers, aLCapabilities.alDeleteBuffers, aLCapabilities.alIsBuffer, aLCapabilities.alGetBufferf, aLCapabilities.alGetBufferi, aLCapabilities.alBufferData, aLCapabilities.alGetEnumValue, aLCapabilities.alGetProcAddress, aLCapabilities.alIsExtensionPresent);
    }

    @NativeType(value="ALenum")
    public static int alGetError() {
        long l = AL.getICD().alGetError;
        return JNI.invokeI(l);
    }

    @NativeType(value="ALvoid")
    public static void alEnable(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alEnable;
        JNI.invokeV(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alDisable(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alDisable;
        JNI.invokeV(n, l);
    }

    @NativeType(value="ALboolean")
    public static boolean alIsEnabled(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alIsEnabled;
        return JNI.invokeZ(n, l);
    }

    @NativeType(value="ALboolean")
    public static boolean alGetBoolean(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alGetBoolean;
        return JNI.invokeZ(n, l);
    }

    @NativeType(value="ALint")
    public static int alGetInteger(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alGetInteger;
        return JNI.invokeI(n, l);
    }

    @NativeType(value="ALfloat")
    public static float alGetFloat(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alGetFloat;
        return JNI.invokeF(n, l);
    }

    @NativeType(value="ALdouble")
    public static double alGetDouble(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alGetDouble;
        return JNI.invokeD(n, l);
    }

    public static void nalGetBooleanv(int n, long l) {
        long l2 = AL.getICD().alGetBooleanv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetBooleanv(@NativeType(value="ALenum") int n, @NativeType(value="ALboolean *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 1);
        }
        AL10.nalGetBooleanv(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static void nalGetIntegerv(int n, long l) {
        long l2 = AL.getICD().alGetIntegerv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetIntegerv(@NativeType(value="ALenum") int n, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL10.nalGetIntegerv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalGetFloatv(int n, long l) {
        long l2 = AL.getICD().alGetFloatv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetFloatv(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalGetFloatv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGetDoublev(int n, long l) {
        long l2 = AL.getICD().alGetDoublev;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetDoublev(@NativeType(value="ALenum") int n, @NativeType(value="ALdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        AL10.nalGetDoublev(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static long nalGetString(int n) {
        long l = AL.getICD().alGetString;
        return JNI.invokeP(n, l);
    }

    @Nullable
    @NativeType(value="ALchar const *")
    public static String alGetString(@NativeType(value="ALenum") int n) {
        long l = AL10.nalGetString(n);
        return MemoryUtil.memUTF8Safe(l);
    }

    @NativeType(value="ALvoid")
    public static void alDistanceModel(@NativeType(value="ALenum") int n) {
        long l = AL.getICD().alDistanceModel;
        JNI.invokeV(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alDopplerFactor(@NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alDopplerFactor;
        JNI.invokeV(f, l);
    }

    @NativeType(value="ALvoid")
    public static void alDopplerVelocity(@NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alDopplerVelocity;
        JNI.invokeV(f, l);
    }

    @NativeType(value="ALvoid")
    public static void alListenerf(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alListenerf;
        JNI.invokeV(n, f, l);
    }

    @NativeType(value="ALvoid")
    public static void alListeneri(@NativeType(value="ALenum") int n, @NativeType(value="ALint") int n2) {
        long l = AL.getICD().alListeneri;
        JNI.invokeV(n, n2, l);
    }

    @NativeType(value="ALvoid")
    public static void alListener3f(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat") float f, @NativeType(value="ALfloat") float f2, @NativeType(value="ALfloat") float f3) {
        long l = AL.getICD().alListener3f;
        JNI.invokeV(n, f, f2, f3, l);
    }

    public static void nalListenerfv(int n, long l) {
        long l2 = AL.getICD().alListenerfv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alListenerfv(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalListenerfv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGetListenerf(int n, long l) {
        long l2 = AL.getICD().alGetListenerf;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetListenerf(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalGetListenerf(n, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static float alGetListenerf(@NativeType(value="ALenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            AL10.nalGetListenerf(n, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static void nalGetListeneri(int n, long l) {
        long l2 = AL.getICD().alGetListeneri;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetListeneri(@NativeType(value="ALenum") int n, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL10.nalGetListeneri(n, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGetListeneri(@NativeType(value="ALenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            AL10.nalGetListeneri(n, MemoryUtil.memAddress(intBuffer));
            int n3 = intBuffer.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static void nalGetListener3f(int n, long l, long l2, long l3) {
        long l4 = AL.getICD().alGetListener3f;
        JNI.invokePPPV(n, l, l2, l3, l4);
    }

    @NativeType(value="ALvoid")
    public static void alGetListener3f(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") FloatBuffer floatBuffer, @NativeType(value="ALfloat *") FloatBuffer floatBuffer2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
            Checks.check((Buffer)floatBuffer2, 1);
            Checks.check((Buffer)floatBuffer3, 1);
        }
        AL10.nalGetListener3f(n, MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), MemoryUtil.memAddress(floatBuffer3));
    }

    public static void nalGetListenerfv(int n, long l) {
        long l2 = AL.getICD().alGetListenerfv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetListenerfv(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalGetListenerfv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGenSources(int n, long l) {
        long l2 = AL.getICD().alGenSources;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGenSources(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        AL10.nalGenSources(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGenSources() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            AL10.nalGenSources(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nalDeleteSources(int n, long l) {
        long l2 = AL.getICD().alDeleteSources;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteSources(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        AL10.nalDeleteSources(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static void alDeleteSources(@NativeType(value="ALuint *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            AL10.nalDeleteSources(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALboolean")
    public static boolean alIsSource(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alIsSource;
        return JNI.invokeZ(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourcef(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat") float f) {
        long l = AL.getICD().alSourcef;
        JNI.invokeV(n, n2, f, l);
    }

    @NativeType(value="ALvoid")
    public static void alSource3f(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat") float f, @NativeType(value="ALfloat") float f2, @NativeType(value="ALfloat") float f3) {
        long l = AL.getICD().alSource3f;
        JNI.invokeV(n, n2, f, f2, f3, l);
    }

    public static void nalSourcefv(int n, int n2, long l) {
        long l2 = AL.getICD().alSourcefv;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourcefv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalSourcefv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    @NativeType(value="ALvoid")
    public static void alSourcei(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint") int n3) {
        long l = AL.getICD().alSourcei;
        JNI.invokeV(n, n2, n3, l);
    }

    public static void nalGetSourcef(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourcef;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcef(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalGetSourcef(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static float alGetSourcef(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            AL10.nalGetSourcef(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetSource3f(int n, int n2, long l, long l2, long l3) {
        long l4 = AL.getICD().alGetSource3f;
        JNI.invokePPPV(n, n2, l, l2, l3, l4);
    }

    @NativeType(value="ALvoid")
    public static void alGetSource3f(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer, @NativeType(value="ALfloat *") FloatBuffer floatBuffer2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
            Checks.check((Buffer)floatBuffer2, 1);
            Checks.check((Buffer)floatBuffer3, 1);
        }
        AL10.nalGetSource3f(n, n2, MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), MemoryUtil.memAddress(floatBuffer3));
    }

    public static void nalGetSourcefv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourcefv;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcefv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalGetSourcefv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nalGetSourcei(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourcei;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcei(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL10.nalGetSourcei(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGetSourcei(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            AL10.nalGetSourcei(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetSourceiv(int n, int n2, long l) {
        long l2 = AL.getICD().alGetSourceiv;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourceiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL10.nalGetSourceiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void nalSourceQueueBuffers(int n, int n2, long l) {
        long l2 = AL.getICD().alSourceQueueBuffers;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourceQueueBuffers(@NativeType(value="ALuint") int n, @NativeType(value="ALuint *") IntBuffer intBuffer) {
        AL10.nalSourceQueueBuffers(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static void alSourceQueueBuffers(@NativeType(value="ALuint") int n, @NativeType(value="ALuint *") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n2);
            AL10.nalSourceQueueBuffers(n, 1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalSourceUnqueueBuffers(int n, int n2, long l) {
        long l2 = AL.getICD().alSourceUnqueueBuffers;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourceUnqueueBuffers(@NativeType(value="ALuint") int n, @NativeType(value="ALuint *") IntBuffer intBuffer) {
        AL10.nalSourceUnqueueBuffers(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alSourceUnqueueBuffers(@NativeType(value="ALuint") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            AL10.nalSourceUnqueueBuffers(n, 1, MemoryUtil.memAddress(intBuffer));
            int n3 = intBuffer.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALvoid")
    public static void alSourcePlay(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alSourcePlay;
        JNI.invokeV(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourcePause(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alSourcePause;
        JNI.invokeV(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourceStop(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alSourceStop;
        JNI.invokeV(n, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourceRewind(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alSourceRewind;
        JNI.invokeV(n, l);
    }

    public static void nalSourcePlayv(int n, long l) {
        long l2 = AL.getICD().alSourcePlayv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourcePlayv(@NativeType(value="ALuint const *") IntBuffer intBuffer) {
        AL10.nalSourcePlayv(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static void nalSourcePausev(int n, long l) {
        long l2 = AL.getICD().alSourcePausev;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourcePausev(@NativeType(value="ALuint const *") IntBuffer intBuffer) {
        AL10.nalSourcePausev(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static void nalSourceStopv(int n, long l) {
        long l2 = AL.getICD().alSourceStopv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourceStopv(@NativeType(value="ALuint const *") IntBuffer intBuffer) {
        AL10.nalSourceStopv(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static void nalSourceRewindv(int n, long l) {
        long l2 = AL.getICD().alSourceRewindv;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alSourceRewindv(@NativeType(value="ALuint const *") IntBuffer intBuffer) {
        AL10.nalSourceRewindv(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static void nalGenBuffers(int n, long l) {
        long l2 = AL.getICD().alGenBuffers;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGenBuffers(@NativeType(value="ALuint *") IntBuffer intBuffer) {
        AL10.nalGenBuffers(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGenBuffers() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            AL10.nalGenBuffers(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static void nalDeleteBuffers(int n, long l) {
        long l2 = AL.getICD().alDeleteBuffers;
        JNI.invokePV(n, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteBuffers(@NativeType(value="ALuint const *") IntBuffer intBuffer) {
        AL10.nalDeleteBuffers(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static void alDeleteBuffers(@NativeType(value="ALuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            AL10.nalDeleteBuffers(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="ALboolean")
    public static boolean alIsBuffer(@NativeType(value="ALuint") int n) {
        long l = AL.getICD().alIsBuffer;
        return JNI.invokeZ(n, l);
    }

    public static void nalGetBufferf(int n, int n2, long l) {
        long l2 = AL.getICD().alGetBufferf;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        AL10.nalGetBufferf(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static float alGetBufferf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            AL10.nalGetBufferf(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalGetBufferi(int n, int n2, long l) {
        long l2 = AL.getICD().alGetBufferi;
        JNI.invokePV(n, n2, l, l2);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferi(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        AL10.nalGetBufferi(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALvoid")
    public static int alGetBufferi(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            AL10.nalGetBufferi(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nalBufferData(int n, int n2, long l, int n3, int n4) {
        long l2 = AL.getICD().alBufferData;
        JNI.invokePV(n, n2, l, n3, n4, l2);
    }

    @NativeType(value="ALvoid")
    public static void alBufferData(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid const *") ByteBuffer byteBuffer, @NativeType(value="ALsizei") int n3) {
        AL10.nalBufferData(n, n2, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), n3);
    }

    @NativeType(value="ALvoid")
    public static void alBufferData(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid const *") ShortBuffer shortBuffer, @NativeType(value="ALsizei") int n3) {
        AL10.nalBufferData(n, n2, MemoryUtil.memAddress(shortBuffer), shortBuffer.remaining() << 1, n3);
    }

    @NativeType(value="ALvoid")
    public static void alBufferData(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid const *") IntBuffer intBuffer, @NativeType(value="ALsizei") int n3) {
        AL10.nalBufferData(n, n2, MemoryUtil.memAddress(intBuffer), intBuffer.remaining() << 2, n3);
    }

    @NativeType(value="ALvoid")
    public static void alBufferData(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid const *") FloatBuffer floatBuffer, @NativeType(value="ALsizei") int n3) {
        AL10.nalBufferData(n, n2, MemoryUtil.memAddress(floatBuffer), floatBuffer.remaining() << 2, n3);
    }

    public static int nalGetEnumValue(long l) {
        long l2 = AL.getICD().alGetEnumValue;
        return JNI.invokePI(l, l2);
    }

    @NativeType(value="ALuint")
    public static int alGetEnumValue(@NativeType(value="ALchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return AL10.nalGetEnumValue(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALuint")
    public static int alGetEnumValue(@NativeType(value="ALchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = AL10.nalGetEnumValue(l);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nalGetProcAddress(long l) {
        long l2 = AL.getICD().alGetProcAddress;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="void *")
    public static long alGetProcAddress(@NativeType(value="ALchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return AL10.nalGetProcAddress(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void *")
    public static long alGetProcAddress(@NativeType(value="ALchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = AL10.nalGetProcAddress(l);
            return l2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static boolean nalIsExtensionPresent(long l) {
        long l2 = AL.getICD().alIsExtensionPresent;
        return JNI.invokePZ(l, l2);
    }

    @NativeType(value="ALCboolean")
    public static boolean alIsExtensionPresent(@NativeType(value="ALchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return AL10.nalIsExtensionPresent(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="ALCboolean")
    public static boolean alIsExtensionPresent(@NativeType(value="ALchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            boolean bl = AL10.nalIsExtensionPresent(l);
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="ALvoid")
    public static void alGetIntegerv(@NativeType(value="ALenum") int n, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetIntegerv;
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetFloatv(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetFloatv;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetDoublev(@NativeType(value="ALenum") int n, @NativeType(value="ALdouble *") double[] dArray) {
        long l = AL.getICD().alGetDoublev;
        if (Checks.CHECKS) {
            Checks.check(dArray, 1);
        }
        JNI.invokePV(n, dArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alListenerfv(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat const *") float[] fArray) {
        long l = AL.getICD().alListenerfv;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetListenerf(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetListenerf;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetListeneri(@NativeType(value="ALenum") int n, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetListeneri;
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetListener3f(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") float[] fArray, @NativeType(value="ALfloat *") float[] fArray2, @NativeType(value="ALfloat *") float[] fArray3) {
        long l = AL.getICD().alGetListener3f;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
            Checks.check(fArray2, 1);
            Checks.check(fArray3, 1);
        }
        JNI.invokePPPV(n, fArray, fArray2, fArray3, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetListenerfv(@NativeType(value="ALenum") int n, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetListenerfv;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGenSources(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alGenSources;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteSources(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alDeleteSources;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourcefv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat const *") float[] fArray) {
        long l = AL.getICD().alSourcefv;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcef(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetSourcef;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSource3f(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray, @NativeType(value="ALfloat *") float[] fArray2, @NativeType(value="ALfloat *") float[] fArray3) {
        long l = AL.getICD().alGetSource3f;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
            Checks.check(fArray2, 1);
            Checks.check(fArray3, 1);
        }
        JNI.invokePPPV(n, n2, fArray, fArray2, fArray3, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcefv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetSourcefv;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourcei(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetSourcei;
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetSourceiv(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetSourceiv;
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourceQueueBuffers(@NativeType(value="ALuint") int n, @NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alSourceQueueBuffers;
        JNI.invokePV(n, nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourceUnqueueBuffers(@NativeType(value="ALuint") int n, @NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alSourceUnqueueBuffers;
        JNI.invokePV(n, nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourcePlayv(@NativeType(value="ALuint const *") int[] nArray) {
        long l = AL.getICD().alSourcePlayv;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourcePausev(@NativeType(value="ALuint const *") int[] nArray) {
        long l = AL.getICD().alSourcePausev;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourceStopv(@NativeType(value="ALuint const *") int[] nArray) {
        long l = AL.getICD().alSourceStopv;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alSourceRewindv(@NativeType(value="ALuint const *") int[] nArray) {
        long l = AL.getICD().alSourceRewindv;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGenBuffers(@NativeType(value="ALuint *") int[] nArray) {
        long l = AL.getICD().alGenBuffers;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alDeleteBuffers(@NativeType(value="ALuint const *") int[] nArray) {
        long l = AL.getICD().alDeleteBuffers;
        JNI.invokePV(nArray.length, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferf(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALfloat *") float[] fArray) {
        long l = AL.getICD().alGetBufferf;
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
        }
        JNI.invokePV(n, n2, fArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alGetBufferi(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALint *") int[] nArray) {
        long l = AL.getICD().alGetBufferi;
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
        }
        JNI.invokePV(n, n2, nArray, l);
    }

    @NativeType(value="ALvoid")
    public static void alBufferData(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid const *") short[] sArray, @NativeType(value="ALsizei") int n3) {
        long l = AL.getICD().alBufferData;
        JNI.invokePV(n, n2, sArray, sArray.length << 1, n3, l);
    }

    @NativeType(value="ALvoid")
    public static void alBufferData(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid const *") int[] nArray, @NativeType(value="ALsizei") int n3) {
        long l = AL.getICD().alBufferData;
        JNI.invokePV(n, n2, nArray, nArray.length << 2, n3, l);
    }

    @NativeType(value="ALvoid")
    public static void alBufferData(@NativeType(value="ALuint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid const *") float[] fArray, @NativeType(value="ALsizei") int n3) {
        long l = AL.getICD().alBufferData;
        JNI.invokePV(n, n2, fArray, fArray.length << 2, n3, l);
    }
}

