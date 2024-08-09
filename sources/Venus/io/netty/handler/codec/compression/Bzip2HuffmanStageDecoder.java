/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.handler.codec.compression.Bzip2BitReader;
import io.netty.handler.codec.compression.Bzip2MoveToFrontTable;
import io.netty.handler.codec.compression.DecompressionException;

final class Bzip2HuffmanStageDecoder {
    private final Bzip2BitReader reader;
    byte[] selectors;
    private final int[] minimumLengths;
    private final int[][] codeBases;
    private final int[][] codeLimits;
    private final int[][] codeSymbols;
    private int currentTable;
    private int groupIndex = -1;
    private int groupPosition = -1;
    final int totalTables;
    final int alphabetSize;
    final Bzip2MoveToFrontTable tableMTF = new Bzip2MoveToFrontTable();
    int currentSelector;
    final byte[][] tableCodeLengths;
    int currentGroup;
    int currentLength = -1;
    int currentAlpha;
    boolean modifyLength;

    Bzip2HuffmanStageDecoder(Bzip2BitReader bzip2BitReader, int n, int n2) {
        this.reader = bzip2BitReader;
        this.totalTables = n;
        this.alphabetSize = n2;
        this.minimumLengths = new int[n];
        this.codeBases = new int[n][25];
        this.codeLimits = new int[n][24];
        this.codeSymbols = new int[n][258];
        this.tableCodeLengths = new byte[n][258];
    }

    void createHuffmanDecodingTables() {
        int n = this.alphabetSize;
        for (int i = 0; i < this.tableCodeLengths.length; ++i) {
            int n2;
            int n3;
            int n4;
            int[] nArray = this.codeBases[i];
            int[] nArray2 = this.codeLimits[i];
            int[] nArray3 = this.codeSymbols[i];
            byte[] byArray = this.tableCodeLengths[i];
            int n5 = 23;
            int n6 = 0;
            for (n4 = 0; n4 < n; ++n4) {
                n3 = byArray[n4];
                n6 = Math.max(n3, n6);
                n5 = Math.min(n3, n5);
            }
            this.minimumLengths[i] = n5;
            for (n4 = 0; n4 < n; ++n4) {
                int n7 = byArray[n4] + 1;
                nArray[n7] = nArray[n7] + 1;
            }
            n3 = nArray[0];
            for (n4 = 1; n4 < 25; ++n4) {
                nArray[n4] = n3 += nArray[n4];
            }
            n3 = 0;
            for (n4 = n5; n4 <= n6; ++n4) {
                n2 = n3;
                nArray[n4] = n2 - nArray[n4];
                nArray2[n4] = (n3 += nArray[n4 + 1] - nArray[n4]) - 1;
                n3 <<= 1;
            }
            n3 = 0;
            for (n4 = n5; n4 <= n6; ++n4) {
                for (n2 = 0; n2 < n; ++n2) {
                    if (byArray[n2] != n4) continue;
                    nArray3[n3++] = n2;
                }
            }
        }
        this.currentTable = this.selectors[0];
    }

    int nextSymbol() {
        int n;
        if (++this.groupPosition % 50 == 0) {
            ++this.groupIndex;
            if (this.groupIndex == this.selectors.length) {
                throw new DecompressionException("error decoding block");
            }
            this.currentTable = this.selectors[this.groupIndex] & 0xFF;
        }
        Bzip2BitReader bzip2BitReader = this.reader;
        int n2 = this.currentTable;
        int[] nArray = this.codeLimits[n2];
        int[] nArray2 = this.codeBases[n2];
        int[] nArray3 = this.codeSymbols[n2];
        int n3 = bzip2BitReader.readBits(n);
        for (n = this.minimumLengths[n2]; n <= 23; ++n) {
            if (n3 <= nArray[n]) {
                return nArray3[n3 - nArray2[n]];
            }
            n3 = n3 << 1 | bzip2BitReader.readBits(1);
        }
        throw new DecompressionException("a valid code was not recognised");
    }
}

