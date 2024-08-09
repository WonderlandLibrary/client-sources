/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.CheckIntrinsics;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.CustomBuffer;

public final class Checks {
    public static final boolean CHECKS = Configuration.DISABLE_CHECKS.get(false) == false;
    public static final boolean DEBUG = Configuration.DEBUG.get(false);
    public static final boolean DEBUG_FUNCTIONS = Configuration.DEBUG_FUNCTIONS.get(false);

    private Checks() {
    }

    public static int lengthSafe(@Nullable short[] sArray) {
        return sArray == null ? 0 : sArray.length;
    }

    public static int lengthSafe(@Nullable int[] nArray) {
        return nArray == null ? 0 : nArray.length;
    }

    public static int lengthSafe(@Nullable long[] lArray) {
        return lArray == null ? 0 : lArray.length;
    }

    public static int lengthSafe(@Nullable float[] fArray) {
        return fArray == null ? 0 : fArray.length;
    }

    public static int lengthSafe(@Nullable double[] dArray) {
        return dArray == null ? 0 : dArray.length;
    }

    public static int remainingSafe(@Nullable Buffer buffer) {
        return buffer == null ? 0 : buffer.remaining();
    }

    public static int remainingSafe(@Nullable CustomBuffer<?> customBuffer) {
        return customBuffer == null ? 0 : customBuffer.remaining();
    }

    public static boolean checkFunctions(long ... lArray) {
        for (long l : lArray) {
            if (l != 0L) continue;
            return true;
        }
        return false;
    }

    public static long check(long l) {
        if (l == 0L) {
            throw new NullPointerException();
        }
        return l;
    }

    private static void assertNT(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException("Missing termination");
        }
    }

    public static void checkNT(int[] nArray) {
        Checks.checkBuffer(nArray.length, 1);
        Checks.assertNT(nArray[nArray.length - 1] == 0);
    }

    public static void checkNT(int[] nArray, int n) {
        Checks.checkBuffer(nArray.length, 1);
        Checks.assertNT(nArray[nArray.length - 1] == n);
    }

    public static void checkNT(long[] lArray) {
        Checks.checkBuffer(lArray.length, 1);
        Checks.assertNT(lArray[lArray.length - 1] == 0L);
    }

    public static void checkNT(float[] fArray) {
        Checks.checkBuffer(fArray.length, 1);
        Checks.assertNT(fArray[fArray.length - 1] == 0.0f);
    }

    public static void checkNT1(ByteBuffer byteBuffer) {
        Checks.checkBuffer(byteBuffer.remaining(), 1);
        Checks.assertNT(byteBuffer.get(byteBuffer.limit() - 1) == 0);
    }

    public static void checkNT2(ByteBuffer byteBuffer) {
        Checks.checkBuffer(byteBuffer.remaining(), 2);
        Checks.assertNT(byteBuffer.get(byteBuffer.limit() - 2) == 0);
    }

    public static void checkNT(IntBuffer intBuffer) {
        Checks.checkBuffer(intBuffer.remaining(), 1);
        Checks.assertNT(intBuffer.get(intBuffer.limit() - 1) == 0);
    }

    public static void checkNT(IntBuffer intBuffer, int n) {
        Checks.checkBuffer(intBuffer.remaining(), 1);
        Checks.assertNT(intBuffer.get(intBuffer.limit() - 1) == n);
    }

    public static void checkNT(LongBuffer longBuffer) {
        Checks.checkBuffer(longBuffer.remaining(), 1);
        Checks.assertNT(longBuffer.get(longBuffer.limit() - 1) == 0L);
    }

    public static void checkNT(FloatBuffer floatBuffer) {
        Checks.checkBuffer(floatBuffer.remaining(), 1);
        Checks.assertNT(floatBuffer.get(floatBuffer.limit() - 1) == 0.0f);
    }

    public static void checkNT(PointerBuffer pointerBuffer) {
        Checks.checkBuffer(pointerBuffer.remaining(), 1);
        Checks.assertNT(pointerBuffer.get(pointerBuffer.limit() - 1) == 0L);
    }

    public static void checkNT(PointerBuffer pointerBuffer, long l) {
        Checks.checkBuffer(pointerBuffer.remaining(), 1);
        Checks.assertNT(pointerBuffer.get(pointerBuffer.limit() - 1) == l);
    }

    public static void checkNTSafe(@Nullable int[] nArray) {
        if (nArray != null) {
            Checks.checkBuffer(nArray.length, 1);
            Checks.assertNT(nArray[nArray.length - 1] == 0);
        }
    }

    public static void checkNTSafe(@Nullable int[] nArray, int n) {
        if (nArray != null) {
            Checks.checkBuffer(nArray.length, 1);
            Checks.assertNT(nArray[nArray.length - 1] == n);
        }
    }

    public static void checkNTSafe(@Nullable long[] lArray) {
        if (lArray != null) {
            Checks.checkBuffer(lArray.length, 1);
            Checks.assertNT(lArray[lArray.length - 1] == 0L);
        }
    }

    public static void checkNTSafe(@Nullable float[] fArray) {
        if (fArray != null) {
            Checks.checkBuffer(fArray.length, 1);
            Checks.assertNT(fArray[fArray.length - 1] == 0.0f);
        }
    }

    public static void checkNT1Safe(@Nullable ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            Checks.checkBuffer(byteBuffer.remaining(), 1);
            Checks.assertNT(byteBuffer.get(byteBuffer.limit() - 1) == 0);
        }
    }

    public static void checkNT2Safe(@Nullable ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            Checks.checkBuffer(byteBuffer.remaining(), 2);
            Checks.assertNT(byteBuffer.get(byteBuffer.limit() - 2) == 0);
        }
    }

    public static void checkNTSafe(@Nullable IntBuffer intBuffer) {
        if (intBuffer != null) {
            Checks.checkBuffer(intBuffer.remaining(), 1);
            Checks.assertNT(intBuffer.get(intBuffer.limit() - 1) == 0);
        }
    }

    public static void checkNTSafe(@Nullable IntBuffer intBuffer, int n) {
        if (intBuffer != null) {
            Checks.checkBuffer(intBuffer.remaining(), 1);
            Checks.assertNT(intBuffer.get(intBuffer.limit() - 1) == n);
        }
    }

    public static void checkNTSafe(@Nullable LongBuffer longBuffer) {
        if (longBuffer != null) {
            Checks.checkBuffer(longBuffer.remaining(), 1);
            Checks.assertNT(longBuffer.get(longBuffer.limit() - 1) == 0L);
        }
    }

    public static void checkNTSafe(@Nullable FloatBuffer floatBuffer) {
        if (floatBuffer != null) {
            Checks.checkBuffer(floatBuffer.remaining(), 1);
            Checks.assertNT(floatBuffer.get(floatBuffer.limit() - 1) == 0.0f);
        }
    }

    public static void checkNTSafe(@Nullable PointerBuffer pointerBuffer) {
        if (pointerBuffer != null) {
            Checks.checkBuffer(pointerBuffer.remaining(), 1);
            Checks.assertNT(pointerBuffer.get(pointerBuffer.limit() - 1) == 0L);
        }
    }

    public static void checkNTSafe(@Nullable PointerBuffer pointerBuffer, long l) {
        if (pointerBuffer != null) {
            Checks.checkBuffer(pointerBuffer.remaining(), 1);
            Checks.assertNT(pointerBuffer.get(pointerBuffer.limit() - 1) == l);
        }
    }

    private static void checkBuffer(int n, int n2) {
        if (n < n2) {
            Checks.throwIAE(n, n2);
        }
    }

    public static void check(byte[] byArray, int n) {
        Checks.checkBuffer(byArray.length, n);
    }

    public static void check(short[] sArray, int n) {
        Checks.checkBuffer(sArray.length, n);
    }

    public static void check(int[] nArray, int n) {
        Checks.checkBuffer(nArray.length, n);
    }

    public static void check(long[] lArray, int n) {
        Checks.checkBuffer(lArray.length, n);
    }

    public static void check(float[] fArray, int n) {
        Checks.checkBuffer(fArray.length, n);
    }

    public static void check(double[] dArray, int n) {
        Checks.checkBuffer(dArray.length, n);
    }

    public static void check(CharSequence charSequence, int n) {
        Checks.checkBuffer(charSequence.length(), n);
    }

    public static void check(Buffer buffer, int n) {
        Checks.checkBuffer(buffer.remaining(), n);
    }

    public static void check(Buffer buffer, long l) {
        Checks.checkBuffer(buffer.remaining(), (int)l);
    }

    public static void check(CustomBuffer<?> customBuffer, int n) {
        Checks.checkBuffer(customBuffer.remaining(), n);
    }

    public static void check(CustomBuffer<?> customBuffer, long l) {
        Checks.checkBuffer(customBuffer.remaining(), (int)l);
    }

    public static void checkSafe(@Nullable short[] sArray, int n) {
        if (sArray != null) {
            Checks.checkBuffer(sArray.length, n);
        }
    }

    public static void checkSafe(@Nullable int[] nArray, int n) {
        if (nArray != null) {
            Checks.checkBuffer(nArray.length, n);
        }
    }

    public static void checkSafe(@Nullable long[] lArray, int n) {
        if (lArray != null) {
            Checks.checkBuffer(lArray.length, n);
        }
    }

    public static void checkSafe(@Nullable float[] fArray, int n) {
        if (fArray != null) {
            Checks.checkBuffer(fArray.length, n);
        }
    }

    public static void checkSafe(@Nullable double[] dArray, int n) {
        if (dArray != null) {
            Checks.checkBuffer(dArray.length, n);
        }
    }

    public static void checkSafe(@Nullable Buffer buffer, int n) {
        if (buffer != null) {
            Checks.checkBuffer(buffer.remaining(), n);
        }
    }

    public static void checkSafe(@Nullable Buffer buffer, long l) {
        if (buffer != null) {
            Checks.checkBuffer(buffer.remaining(), (int)l);
        }
    }

    public static void checkSafe(@Nullable CustomBuffer<?> customBuffer, int n) {
        if (customBuffer != null) {
            Checks.checkBuffer(customBuffer.remaining(), n);
        }
    }

    public static void checkSafe(@Nullable CustomBuffer<?> customBuffer, long l) {
        if (customBuffer != null) {
            Checks.checkBuffer(customBuffer.remaining(), (int)l);
        }
    }

    public static void check(Object[] objectArray, int n) {
        Checks.checkBuffer(objectArray.length, n);
    }

    private static void checkBufferGT(int n, int n2) {
        if (n2 < n) {
            Checks.throwIAEGT(n, n2);
        }
    }

    public static void checkGT(Buffer buffer, int n) {
        Checks.checkBufferGT(buffer.remaining(), n);
    }

    public static void checkGT(CustomBuffer<?> customBuffer, int n) {
        Checks.checkBufferGT(customBuffer.remaining(), n);
    }

    public static long check(int n, int n2) {
        if (CHECKS) {
            CheckIntrinsics.checkIndex(n, n2);
        }
        return Integer.toUnsignedLong(n);
    }

    private static void throwIAE(int n, int n2) {
        throw new IllegalArgumentException("Number of remaining elements is " + n + ", must be at least " + n2);
    }

    private static void throwIAEGT(int n, int n2) {
        throw new IllegalArgumentException("Number of remaining buffer elements is " + n + ", must be at most " + n2);
    }

    static {
        if (DEBUG_FUNCTIONS && !DEBUG) {
            APIUtil.DEBUG_STREAM.println("[LWJGL] The DEBUG_FUNCTIONS option requires DEBUG to produce output.");
        }
    }
}

