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

    Bzip2MTFAndRLE2StageEncoder(int[] bwtBlock, int bwtLength, boolean[] bwtValuesPresent) {
        this.bwtBlock = bwtBlock;
        this.bwtLength = bwtLength;
        this.bwtValuesPresent = bwtValuesPresent;
        this.mtfBlock = new char[bwtLength + 1];
    }

    void encode() {
        int bwtLength = this.bwtLength;
        boolean[] bwtValuesPresent = this.bwtValuesPresent;
        int[] bwtBlock = this.bwtBlock;
        char[] mtfBlock = this.mtfBlock;
        int[] mtfSymbolFrequencies = this.mtfSymbolFrequencies;
        byte[] huffmanSymbolMap = new byte[256];
        Bzip2MoveToFrontTable symbolMTF = new Bzip2MoveToFrontTable();
        int totalUniqueValues = 0;
        for (int i = 0; i < huffmanSymbolMap.length; ++i) {
            if (!bwtValuesPresent[i]) continue;
            huffmanSymbolMap[i] = (byte)totalUniqueValues++;
        }
        int endOfBlockSymbol = totalUniqueValues + 1;
        int mtfIndex = 0;
        int repeatCount = 0;
        int totalRunAs = 0;
        int totalRunBs = 0;
        for (int i = 0; i < bwtLength; ++i) {
            int mtfPosition = symbolMTF.valueToFront(huffmanSymbolMap[bwtBlock[i] & 0xFF]);
            if (mtfPosition == 0) {
                ++repeatCount;
                continue;
            }
            if (repeatCount > 0) {
                --repeatCount;
                while (true) {
                    if ((repeatCount & 1) == 0) {
                        mtfBlock[mtfIndex++] = '\u0000';
                        ++totalRunAs;
                    } else {
                        mtfBlock[mtfIndex++] = '\u0001';
                        ++totalRunBs;
                    }
                    if (repeatCount <= 1) break;
                    repeatCount = repeatCount - 2 >>> 1;
                }
                repeatCount = 0;
            }
            mtfBlock[mtfIndex++] = (char)(mtfPosition + 1);
            int n = mtfPosition + 1;
            mtfSymbolFrequencies[n] = mtfSymbolFrequencies[n] + 1;
        }
        if (repeatCount > 0) {
            --repeatCount;
            while (true) {
                if ((repeatCount & 1) == 0) {
                    mtfBlock[mtfIndex++] = '\u0000';
                    ++totalRunAs;
                } else {
                    mtfBlock[mtfIndex++] = '\u0001';
                    ++totalRunBs;
                }
                if (repeatCount <= 1) break;
                repeatCount = repeatCount - 2 >>> 1;
            }
        }
        mtfBlock[mtfIndex] = (char)endOfBlockSymbol;
        int n = endOfBlockSymbol;
        mtfSymbolFrequencies[n] = mtfSymbolFrequencies[n] + 1;
        mtfSymbolFrequencies[0] = mtfSymbolFrequencies[0] + totalRunAs;
        mtfSymbolFrequencies[1] = mtfSymbolFrequencies[1] + totalRunBs;
        this.mtfLength = mtfIndex + 1;
        this.alphabetSize = endOfBlockSymbol + 1;
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

