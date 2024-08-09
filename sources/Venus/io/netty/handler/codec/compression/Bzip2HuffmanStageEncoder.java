/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.compression.Bzip2BitWriter;
import io.netty.handler.codec.compression.Bzip2HuffmanAllocator;
import io.netty.handler.codec.compression.Bzip2MoveToFrontTable;
import java.util.Arrays;

final class Bzip2HuffmanStageEncoder {
    private static final int HUFFMAN_HIGH_SYMBOL_COST = 15;
    private final Bzip2BitWriter writer;
    private final char[] mtfBlock;
    private final int mtfLength;
    private final int mtfAlphabetSize;
    private final int[] mtfSymbolFrequencies;
    private final int[][] huffmanCodeLengths;
    private final int[][] huffmanMergedCodeSymbols;
    private final byte[] selectors;

    Bzip2HuffmanStageEncoder(Bzip2BitWriter bzip2BitWriter, char[] cArray, int n, int n2, int[] nArray) {
        this.writer = bzip2BitWriter;
        this.mtfBlock = cArray;
        this.mtfLength = n;
        this.mtfAlphabetSize = n2;
        this.mtfSymbolFrequencies = nArray;
        int n3 = Bzip2HuffmanStageEncoder.selectTableCount(n);
        this.huffmanCodeLengths = new int[n3][n2];
        this.huffmanMergedCodeSymbols = new int[n3][n2];
        this.selectors = new byte[(n + 50 - 1) / 50];
    }

    private static int selectTableCount(int n) {
        if (n >= 2400) {
            return 1;
        }
        if (n >= 1200) {
            return 0;
        }
        if (n >= 600) {
            return 1;
        }
        if (n >= 200) {
            return 0;
        }
        return 1;
    }

    private static void generateHuffmanCodeLengths(int n, int[] nArray, int[] nArray2) {
        int n2;
        int[] nArray3 = new int[n];
        int[] nArray4 = new int[n];
        for (n2 = 0; n2 < n; ++n2) {
            nArray3[n2] = nArray[n2] << 9 | n2;
        }
        Arrays.sort(nArray3);
        for (n2 = 0; n2 < n; ++n2) {
            nArray4[n2] = nArray3[n2] >>> 9;
        }
        Bzip2HuffmanAllocator.allocateHuffmanCodeLengths(nArray4, 20);
        for (n2 = 0; n2 < n; ++n2) {
            nArray2[nArray3[n2] & 0x1FF] = nArray4[n2];
        }
    }

    private void generateHuffmanOptimisationSeeds() {
        int[][] nArray = this.huffmanCodeLengths;
        int[] nArray2 = this.mtfSymbolFrequencies;
        int n = this.mtfAlphabetSize;
        int n2 = nArray.length;
        int n3 = this.mtfLength;
        int n4 = -1;
        for (int i = 0; i < n2; ++i) {
            int n5;
            int n6 = n3 / (n2 - i);
            int n7 = n4 + 1;
            for (n5 = 0; n5 < n6 && n4 < n - 1; n5 += nArray2[++n4]) {
            }
            if (n4 > n7 && i != 0 && i != n2 - 1 && (n2 - i & 1) == 0) {
                n5 -= nArray2[n4--];
            }
            int[] nArray3 = nArray[i];
            for (int j = 0; j < n; ++j) {
                if (j >= n7 && j <= n4) continue;
                nArray3[j] = 15;
            }
            n3 -= n5;
        }
    }

    private void optimiseSelectorsAndHuffmanTables(boolean bl) {
        char[] cArray = this.mtfBlock;
        byte[] byArray = this.selectors;
        int[][] nArray = this.huffmanCodeLengths;
        int n = this.mtfLength;
        int n2 = this.mtfAlphabetSize;
        int n3 = nArray.length;
        int[][] nArray2 = new int[n3][n2];
        int n4 = 0;
        int n5 = 0;
        while (n5 < n) {
            int n6;
            int n7;
            int n8;
            int n9;
            int n10 = Math.min(n5 + 50, n) - 1;
            short[] sArray = new short[n3];
            for (n9 = n5; n9 <= n10; ++n9) {
                n8 = cArray[n9];
                for (n7 = 0; n7 < n3; ++n7) {
                    int n11 = n7;
                    sArray[n11] = (short)(sArray[n11] + nArray[n7][n8]);
                }
            }
            n9 = 0;
            n8 = sArray[0];
            for (n7 = 1; n7 < n3; n7 = (int)((byte)(n7 + 1))) {
                n6 = sArray[n7];
                if (n6 >= n8) continue;
                n8 = n6;
                n9 = n7;
            }
            int[] nArray3 = nArray2[n9];
            for (n6 = n5; n6 <= n10; ++n6) {
                char c = cArray[n6];
                nArray3[c] = nArray3[c] + 1;
            }
            if (bl) {
                byArray[n4++] = n9;
            }
            n5 = n10 + 1;
        }
        for (n5 = 0; n5 < n3; ++n5) {
            Bzip2HuffmanStageEncoder.generateHuffmanCodeLengths(n2, nArray2[n5], nArray[n5]);
        }
    }

    private void assignHuffmanCodeSymbols() {
        int[][] nArray = this.huffmanMergedCodeSymbols;
        int[][] nArray2 = this.huffmanCodeLengths;
        int n = this.mtfAlphabetSize;
        int n2 = nArray2.length;
        for (int i = 0; i < n2; ++i) {
            int n3;
            int n4;
            int[] nArray3 = nArray2[i];
            int n5 = 32;
            int n6 = 0;
            for (n4 = 0; n4 < n; ++n4) {
                n3 = nArray3[n4];
                if (n3 > n6) {
                    n6 = n3;
                }
                if (n3 >= n5) continue;
                n5 = n3;
            }
            n4 = 0;
            for (n3 = n5; n3 <= n6; ++n3) {
                for (int j = 0; j < n; ++j) {
                    if ((nArray2[i][j] & 0xFF) != n3) continue;
                    nArray[i][j] = n3 << 24 | n4;
                    ++n4;
                }
                n4 <<= 1;
            }
        }
    }

    private void writeSelectorsAndHuffmanTables(ByteBuf byteBuf) {
        Bzip2BitWriter bzip2BitWriter = this.writer;
        byte[] byArray = this.selectors;
        int n = byArray.length;
        int[][] nArray = this.huffmanCodeLengths;
        int n2 = nArray.length;
        int n3 = this.mtfAlphabetSize;
        bzip2BitWriter.writeBits(byteBuf, 3, n2);
        bzip2BitWriter.writeBits(byteBuf, 15, n);
        Bzip2MoveToFrontTable bzip2MoveToFrontTable = new Bzip2MoveToFrontTable();
        for (byte by : byArray) {
            bzip2BitWriter.writeUnary(byteBuf, bzip2MoveToFrontTable.valueToFront(by));
        }
        for (int[] nArray2 : nArray) {
            int n4 = nArray2[0];
            bzip2BitWriter.writeBits(byteBuf, 5, n4);
            for (int i = 0; i < n3; ++i) {
                int n5 = nArray2[i];
                int n6 = n4 < n5 ? 2 : 3;
                int n7 = Math.abs(n5 - n4);
                while (n7-- > 0) {
                    bzip2BitWriter.writeBits(byteBuf, 2, n6);
                }
                bzip2BitWriter.writeBoolean(byteBuf, true);
                n4 = n5;
            }
        }
    }

    private void writeBlockData(ByteBuf byteBuf) {
        Bzip2BitWriter bzip2BitWriter = this.writer;
        int[][] nArray = this.huffmanMergedCodeSymbols;
        byte[] byArray = this.selectors;
        char[] cArray = this.mtfBlock;
        int n = this.mtfLength;
        int n2 = 0;
        int n3 = 0;
        while (n3 < n) {
            int n4 = Math.min(n3 + 50, n) - 1;
            int[] nArray2 = nArray[byArray[n2++]];
            while (n3 <= n4) {
                int n5 = nArray2[cArray[n3++]];
                bzip2BitWriter.writeBits(byteBuf, n5 >>> 24, n5);
            }
        }
    }

    void encode(ByteBuf byteBuf) {
        this.generateHuffmanOptimisationSeeds();
        for (int i = 3; i >= 0; --i) {
            this.optimiseSelectorsAndHuffmanTables(i == 0);
        }
        this.assignHuffmanCodeSymbols();
        this.writeSelectorsAndHuffmanTables(byteBuf);
        this.writeBlockData(byteBuf);
    }
}

