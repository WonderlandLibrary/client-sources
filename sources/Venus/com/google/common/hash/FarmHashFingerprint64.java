/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractNonStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.LittleEndianByteArray;

final class FarmHashFingerprint64
extends AbstractNonStreamingHashFunction {
    private static final long K0 = -4348849565147123417L;
    private static final long K1 = -5435081209227447693L;
    private static final long K2 = -7286425919675154353L;

    FarmHashFingerprint64() {
    }

    @Override
    public HashCode hashBytes(byte[] byArray, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
        return HashCode.fromLong(FarmHashFingerprint64.fingerprint(byArray, n, n2));
    }

    @Override
    public int bits() {
        return 1;
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }

    @VisibleForTesting
    static long fingerprint(byte[] byArray, int n, int n2) {
        if (n2 <= 32) {
            if (n2 <= 16) {
                return FarmHashFingerprint64.hashLength0to16(byArray, n, n2);
            }
            return FarmHashFingerprint64.hashLength17to32(byArray, n, n2);
        }
        if (n2 <= 64) {
            return FarmHashFingerprint64.hashLength33To64(byArray, n, n2);
        }
        return FarmHashFingerprint64.hashLength65Plus(byArray, n, n2);
    }

    private static long shiftMix(long l) {
        return l ^ l >>> 47;
    }

    private static long hashLength16(long l, long l2, long l3) {
        long l4 = (l ^ l2) * l3;
        l4 ^= l4 >>> 47;
        long l5 = (l2 ^ l4) * l3;
        l5 ^= l5 >>> 47;
        return l5 *= l3;
    }

    private static void weakHashLength32WithSeeds(byte[] byArray, int n, long l, long l2, long[] lArray) {
        long l3 = LittleEndianByteArray.load64(byArray, n);
        long l4 = LittleEndianByteArray.load64(byArray, n + 8);
        long l5 = LittleEndianByteArray.load64(byArray, n + 16);
        long l6 = LittleEndianByteArray.load64(byArray, n + 24);
        l2 = Long.rotateRight(l2 + (l += l3) + l6, 21);
        long l7 = l;
        l += l4;
        lArray[0] = l + l6;
        lArray[1] = (l2 += Long.rotateRight(l += l5, 44)) + l7;
    }

    private static long hashLength0to16(byte[] byArray, int n, int n2) {
        if (n2 >= 8) {
            long l = -7286425919675154353L + (long)(n2 * 2);
            long l2 = LittleEndianByteArray.load64(byArray, n) + -7286425919675154353L;
            long l3 = LittleEndianByteArray.load64(byArray, n + n2 - 8);
            long l4 = Long.rotateRight(l3, 37) * l + l2;
            long l5 = (Long.rotateRight(l2, 25) + l3) * l;
            return FarmHashFingerprint64.hashLength16(l4, l5, l);
        }
        if (n2 >= 4) {
            long l = -7286425919675154353L + (long)(n2 * 2);
            long l6 = (long)LittleEndianByteArray.load32(byArray, n) & 0xFFFFFFFFL;
            return FarmHashFingerprint64.hashLength16((long)n2 + (l6 << 3), (long)LittleEndianByteArray.load32(byArray, n + n2 - 4) & 0xFFFFFFFFL, l);
        }
        if (n2 > 0) {
            byte by = byArray[n];
            byte by2 = byArray[n + (n2 >> 1)];
            byte by3 = byArray[n + (n2 - 1)];
            int n3 = (by & 0xFF) + ((by2 & 0xFF) << 8);
            int n4 = n2 + ((by3 & 0xFF) << 2);
            return FarmHashFingerprint64.shiftMix((long)n3 * -7286425919675154353L ^ (long)n4 * -4348849565147123417L) * -7286425919675154353L;
        }
        return -7286425919675154353L;
    }

    private static long hashLength17to32(byte[] byArray, int n, int n2) {
        long l = -7286425919675154353L + (long)(n2 * 2);
        long l2 = LittleEndianByteArray.load64(byArray, n) * -5435081209227447693L;
        long l3 = LittleEndianByteArray.load64(byArray, n + 8);
        long l4 = LittleEndianByteArray.load64(byArray, n + n2 - 8) * l;
        long l5 = LittleEndianByteArray.load64(byArray, n + n2 - 16) * -7286425919675154353L;
        return FarmHashFingerprint64.hashLength16(Long.rotateRight(l2 + l3, 43) + Long.rotateRight(l4, 30) + l5, l2 + Long.rotateRight(l3 + -7286425919675154353L, 18) + l4, l);
    }

    private static long hashLength33To64(byte[] byArray, int n, int n2) {
        long l = -7286425919675154353L + (long)(n2 * 2);
        long l2 = LittleEndianByteArray.load64(byArray, n) * -7286425919675154353L;
        long l3 = LittleEndianByteArray.load64(byArray, n + 8);
        long l4 = LittleEndianByteArray.load64(byArray, n + n2 - 8) * l;
        long l5 = LittleEndianByteArray.load64(byArray, n + n2 - 16) * -7286425919675154353L;
        long l6 = Long.rotateRight(l2 + l3, 43) + Long.rotateRight(l4, 30) + l5;
        long l7 = FarmHashFingerprint64.hashLength16(l6, l2 + Long.rotateRight(l3 + -7286425919675154353L, 18) + l4, l);
        long l8 = LittleEndianByteArray.load64(byArray, n + 16) * l;
        long l9 = LittleEndianByteArray.load64(byArray, n + 24);
        long l10 = (l6 + LittleEndianByteArray.load64(byArray, n + n2 - 32)) * l;
        long l11 = (l7 + LittleEndianByteArray.load64(byArray, n + n2 - 24)) * l;
        return FarmHashFingerprint64.hashLength16(Long.rotateRight(l8 + l9, 43) + Long.rotateRight(l10, 30) + l11, l8 + Long.rotateRight(l9 + l2, 18) + l10, l);
    }

    private static long hashLength65Plus(byte[] byArray, int n, int n2) {
        long l;
        int n3 = 81;
        long l2 = 81L;
        long l3 = 2480279821605975764L;
        long l4 = FarmHashFingerprint64.shiftMix(l3 * -7286425919675154353L + 113L) * -7286425919675154353L;
        long[] lArray = new long[2];
        long[] lArray2 = new long[2];
        l2 = l2 * -7286425919675154353L + LittleEndianByteArray.load64(byArray, n);
        int n4 = n + (n2 - 1) / 64 * 64;
        int n5 = n4 + (n2 - 1 & 0x3F) - 63;
        do {
            l2 = Long.rotateRight(l2 + l3 + lArray[0] + LittleEndianByteArray.load64(byArray, n + 8), 37) * -5435081209227447693L;
            l3 = Long.rotateRight(l3 + lArray[1] + LittleEndianByteArray.load64(byArray, n + 48), 42) * -5435081209227447693L;
            l4 = Long.rotateRight(l4 + lArray2[0], 33) * -5435081209227447693L;
            FarmHashFingerprint64.weakHashLength32WithSeeds(byArray, n, lArray[1] * -5435081209227447693L, (l2 ^= lArray2[1]) + lArray2[0], lArray);
            FarmHashFingerprint64.weakHashLength32WithSeeds(byArray, n + 32, l4 + lArray2[1], (l3 += lArray[0] + LittleEndianByteArray.load64(byArray, n + 40)) + LittleEndianByteArray.load64(byArray, n + 16), lArray2);
            l = l2;
            l2 = l4;
            l4 = l;
        } while ((n += 64) != n4);
        l = -5435081209227447693L + ((l4 & 0xFFL) << 1);
        n = n5;
        lArray2[0] = lArray2[0] + (long)(n2 - 1 & 0x3F);
        lArray[0] = lArray[0] + lArray2[0];
        lArray2[0] = lArray2[0] + lArray[0];
        l2 = Long.rotateRight(l2 + l3 + lArray[0] + LittleEndianByteArray.load64(byArray, n + 8), 37) * l;
        l3 = Long.rotateRight(l3 + lArray[1] + LittleEndianByteArray.load64(byArray, n + 48), 42) * l;
        l4 = Long.rotateRight(l4 + lArray2[0], 33) * l;
        FarmHashFingerprint64.weakHashLength32WithSeeds(byArray, n, lArray[1] * l, (l2 ^= lArray2[1] * 9L) + lArray2[0], lArray);
        FarmHashFingerprint64.weakHashLength32WithSeeds(byArray, n + 32, l4 + lArray2[1], (l3 += lArray[0] * 9L + LittleEndianByteArray.load64(byArray, n + 40)) + LittleEndianByteArray.load64(byArray, n + 16), lArray2);
        return FarmHashFingerprint64.hashLength16(FarmHashFingerprint64.hashLength16(lArray[0], lArray2[0], l) + FarmHashFingerprint64.shiftMix(l3) * -4348849565147123417L + l2, FarmHashFingerprint64.hashLength16(lArray[1], lArray2[1], l) + l4, l);
    }
}

