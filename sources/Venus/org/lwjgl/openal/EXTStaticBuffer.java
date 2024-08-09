/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTStaticBuffer {
    protected EXTStaticBuffer() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(ALCapabilities aLCapabilities) {
        return Checks.checkFunctions(aLCapabilities.alBufferDataStatic);
    }

    public static void nalBufferDataStatic(int n, int n2, long l, int n3, int n4) {
        long l2 = AL.getICD().alBufferDataStatic;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.invokePV(n, n2, l, n3, n4, l2);
    }

    @NativeType(value="ALvoid")
    public static void alBufferDataStatic(@NativeType(value="ALint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid *") ByteBuffer byteBuffer, @NativeType(value="ALsizei") int n3) {
        EXTStaticBuffer.nalBufferDataStatic(n, n2, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), n3);
    }

    @NativeType(value="ALvoid")
    public static void alBufferDataStatic(@NativeType(value="ALint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid *") ShortBuffer shortBuffer, @NativeType(value="ALsizei") int n3) {
        EXTStaticBuffer.nalBufferDataStatic(n, n2, MemoryUtil.memAddress(shortBuffer), shortBuffer.remaining() << 1, n3);
    }

    @NativeType(value="ALvoid")
    public static void alBufferDataStatic(@NativeType(value="ALint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid *") IntBuffer intBuffer, @NativeType(value="ALsizei") int n3) {
        EXTStaticBuffer.nalBufferDataStatic(n, n2, MemoryUtil.memAddress(intBuffer), intBuffer.remaining() << 2, n3);
    }

    @NativeType(value="ALvoid")
    public static void alBufferDataStatic(@NativeType(value="ALint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid *") FloatBuffer floatBuffer, @NativeType(value="ALsizei") int n3) {
        EXTStaticBuffer.nalBufferDataStatic(n, n2, MemoryUtil.memAddress(floatBuffer), floatBuffer.remaining() << 2, n3);
    }

    @NativeType(value="ALvoid")
    public static void alBufferDataStatic(@NativeType(value="ALint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid *") short[] sArray, @NativeType(value="ALsizei") int n3) {
        long l = AL.getICD().alBufferDataStatic;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(n, n2, sArray, sArray.length << 1, n3, l);
    }

    @NativeType(value="ALvoid")
    public static void alBufferDataStatic(@NativeType(value="ALint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid *") int[] nArray, @NativeType(value="ALsizei") int n3) {
        long l = AL.getICD().alBufferDataStatic;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(n, n2, nArray, nArray.length << 2, n3, l);
    }

    @NativeType(value="ALvoid")
    public static void alBufferDataStatic(@NativeType(value="ALint") int n, @NativeType(value="ALenum") int n2, @NativeType(value="ALvoid *") float[] fArray, @NativeType(value="ALsizei") int n3) {
        long l = AL.getICD().alBufferDataStatic;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.invokePV(n, n2, fArray, fArray.length << 2, n3, l);
    }
}

