/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.handler.codec.compression.Bzip2MoveToFrontTable;

final class Bzip2MTFAndRLE2StageEncoder {
    private final int[] bwtBlock;
    private final int bwtLength;
    private final boolean[] bwtValuesPresent;
    private final char[] mtfBlock;
    private int mtfLength;
    private final int[] mtfSymbolFrequencies = new int[258];
    private int alphabetSize;

    Bzip2MTFAndRLE2StageEncoder(int[] nArray, int n, boolean[] blArray) {
        this.bwtBlock = nArray;
        this.bwtLength = n;
        this.bwtValuesPresent = blArray;
        this.mtfBlock = new char[n + 1];
    }

    void encode() {
        int n;
        int n2 = this.bwtLength;
        boolean[] blArray = this.bwtValuesPresent;
        int[] nArray = this.bwtBlock;
        char[] cArray = this.mtfBlock;
        int[] nArray2 = this.mtfSymbolFrequencies;
        byte[] byArray = new byte[256];
        Bzip2MoveToFrontTable bzip2MoveToFrontTable = new Bzip2MoveToFrontTable();
        int n3 = 0;
        for (n = 0; n < byArray.length; ++n) {
            if (!blArray[n]) continue;
            byArray[n] = (byte)n3++;
        }
        n = n3 + 1;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        for (int i = 0; i < n2; ++i) {
            int n8 = bzip2MoveToFrontTable.valueToFront(byArray[nArray[i] & 0xFF]);
            if (n8 == 0) {
                ++n5;
                continue;
            }
            if (n5 > 0) {
                --n5;
                while (true) {
                    if ((n5 & 1) == 0) {
                        cArray[n4++] = '\u0000';
                        ++n6;
                    } else {
                        cArray[n4++] = '\u0001';
                        ++n7;
                    }
                    if (n5 <= 1) break;
                    n5 = n5 - 2 >>> 1;
                }
                n5 = 0;
            }
            cArray[n4++] = (char)(n8 + 1);
            int n9 = n8 + 1;
            nArray2[n9] = nArray2[n9] + 1;
        }
        if (n5 > 0) {
            --n5;
            while (true) {
                if ((n5 & 1) == 0) {
                    cArray[n4++] = '\u0000';
                    ++n6;
                } else {
                    cArray[n4++] = '\u0001';
                    ++n7;
                }
                if (n5 <= 1) break;
                n5 = n5 - 2 >>> 1;
            }
        }
        cArray[n4] = (char)n;
        int n10 = n;
        nArray2[n10] = nArray2[n10] + 1;
        nArray2[0] = nArray2[0] + n6;
        nArray2[1] = nArray2[1] + n7;
        this.mtfLength = n4 + 1;
        this.alphabetSize = n + 1;
    }

    char[] mtfBlock() {
        return this.mtfBlock;
    }

    int mtfLength() {
        return this.mtfLength;
    }

    int mtfAlphabetSize() {
        return this.alphabetSize;
    }

    int[] mtfSymbolFrequencies() {
        return this.mtfSymbolFrequencies;
    }
}

