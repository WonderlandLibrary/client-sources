/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.bzip2;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;
import org.apache.commons.compress.compressors.bzip2.BlockSort;
import org.apache.commons.compress.compressors.bzip2.CRC;

public class BZip2CompressorOutputStream
extends CompressorOutputStream
implements BZip2Constants {
    public static final int MIN_BLOCKSIZE = 1;
    public static final int MAX_BLOCKSIZE = 9;
    private static final int GREATER_ICOST = 15;
    private static final int LESSER_ICOST = 0;
    private int last;
    private final int blockSize100k;
    private int bsBuff;
    private int bsLive;
    private final CRC crc = new CRC();
    private int nInUse;
    private int nMTF;
    private int currentChar = -1;
    private int runLength = 0;
    private int blockCRC;
    private int combinedCRC;
    private final int allowableBlockSize;
    private Data data;
    private BlockSort blockSorter;
    private OutputStream out;

    private static void hbMakeCodeLengths(byte[] byArray, int[] nArray, Data data, int n, int n2) {
        int[] nArray2 = data.heap;
        int[] nArray3 = data.weight;
        int[] nArray4 = data.parent;
        int n3 = n;
        while (--n3 >= 0) {
            nArray3[n3 + 1] = (nArray[n3] == 0 ? 1 : nArray[n3]) << 8;
        }
        n3 = 1;
        while (n3 != 0) {
            int n4;
            int n5;
            int n6;
            int n7;
            n3 = 0;
            int n8 = n;
            int n9 = 0;
            nArray2[0] = 0;
            nArray3[0] = 0;
            nArray4[0] = -2;
            for (n7 = 1; n7 <= n; ++n7) {
                nArray4[n7] = -1;
                nArray2[++n9] = n7;
                n6 = n9;
                n5 = nArray2[n6];
                while (nArray3[n5] < nArray3[nArray2[n6 >> 1]]) {
                    nArray2[n6] = nArray2[n6 >> 1];
                    n6 >>= 1;
                }
                nArray2[n6] = n5;
            }
            while (n9 > 1) {
                int n10;
                int n11;
                n7 = nArray2[1];
                nArray2[1] = nArray2[n9];
                --n9;
                n6 = 0;
                n5 = 1;
                n4 = nArray2[1];
                while ((n6 = n5 << 1) <= n9) {
                    if (n6 < n9 && nArray3[nArray2[n6 + 1]] < nArray3[nArray2[n6]]) {
                        ++n6;
                    }
                    if (nArray3[n4] < nArray3[nArray2[n6]]) break;
                    nArray2[n5] = nArray2[n6];
                    n5 = n6;
                }
                nArray2[n5] = n4;
                int n12 = nArray2[1];
                nArray2[1] = nArray2[n9];
                --n9;
                n6 = 0;
                n5 = 1;
                n4 = nArray2[1];
                while ((n6 = n5 << 1) <= n9) {
                    if (n6 < n9 && nArray3[nArray2[n6 + 1]] < nArray3[nArray2[n6]]) {
                        ++n6;
                    }
                    if (nArray3[n4] < nArray3[nArray2[n6]]) break;
                    nArray2[n5] = nArray2[n6];
                    n5 = n6;
                }
                nArray2[n5] = n4;
                nArray4[n7] = nArray4[n12] = ++n8;
                nArray3[n8] = (n11 & 0xFFFFFF00) + (n10 & 0xFFFFFF00) | 1 + (((n11 = nArray3[n7]) & 0xFF) > ((n10 = nArray3[n12]) & 0xFF) ? n11 & 0xFF : n10 & 0xFF);
                nArray4[n8] = -1;
                nArray2[++n9] = n8;
                n4 = 0;
                n5 = n9;
                n4 = nArray2[n5];
                int n13 = nArray3[n4];
                while (n13 < nArray3[nArray2[n5 >> 1]]) {
                    nArray2[n5] = nArray2[n5 >> 1];
                    n5 >>= 1;
                }
                nArray2[n5] = n4;
            }
            for (n7 = 1; n7 <= n; ++n7) {
                n6 = 0;
                n5 = n7;
                while ((n4 = nArray4[n5]) >= 0) {
                    n5 = n4;
                    ++n6;
                }
                byArray[n7 - 1] = (byte)n6;
                if (n6 <= n2) continue;
                n3 = 1;
            }
            if (n3 == 0) continue;
            for (n7 = 1; n7 < n; ++n7) {
                n6 = nArray3[n7] >> 8;
                n6 = 1 + (n6 >> 1);
                nArray3[n7] = n6 << 8;
            }
        }
    }

    public static int chooseBlockSize(long l) {
        return l > 0L ? (int)Math.min(l / 132000L + 1L, 9L) : 9;
    }

    public BZip2CompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, 9);
    }

    public BZip2CompressorOutputStream(OutputStream outputStream, int n) throws IOException {
        if (n < 1) {
            throw new IllegalArgumentException("blockSize(" + n + ") < 1");
        }
        if (n > 9) {
            throw new IllegalArgumentException("blockSize(" + n + ") > 9");
        }
        this.blockSize100k = n;
        this.out = outputStream;
        this.allowableBlockSize = this.blockSize100k * 100000 - 20;
        this.init();
    }

    public void write(int n) throws IOException {
        if (this.out == null) {
            throw new IOException("closed");
        }
        this.write0(n);
    }

    private void writeRun() throws IOException {
        int n = this.last;
        if (n < this.allowableBlockSize) {
            int n2 = this.currentChar;
            Data data = this.data;
            data.inUse[n2] = true;
            byte by = (byte)n2;
            int n3 = this.runLength;
            this.crc.updateCRC(n2, n3);
            switch (n3) {
                case 1: {
                    data.block[n + 2] = by;
                    this.last = n + 1;
                    break;
                }
                case 2: {
                    data.block[n + 2] = by;
                    data.block[n + 3] = by;
                    this.last = n + 2;
                    break;
                }
                case 3: {
                    byte[] byArray = data.block;
                    byArray[n + 2] = by;
                    byArray[n + 3] = by;
                    byArray[n + 4] = by;
                    this.last = n + 3;
                    break;
                }
                default: {
                    data.inUse[n3 -= 4] = true;
                    byte[] byArray = data.block;
                    byArray[n + 2] = by;
                    byArray[n + 3] = by;
                    byArray[n + 4] = by;
                    byArray[n + 5] = by;
                    byArray[n + 6] = (byte)n3;
                    this.last = n + 5;
                    break;
                }
            }
        } else {
            this.endBlock();
            this.initBlock();
            this.writeRun();
        }
    }

    protected void finalize() throws Throwable {
        this.finish();
        super.finalize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void finish() throws IOException {
        if (this.out != null) {
            try {
                if (this.runLength > 0) {
                    this.writeRun();
                }
                this.currentChar = -1;
                this.endBlock();
                this.endCompression();
            } finally {
                this.out = null;
                this.data = null;
                this.blockSorter = null;
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            OutputStream outputStream = this.out;
            this.finish();
            outputStream.close();
        }
    }

    public void flush() throws IOException {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    private void init() throws IOException {
        this.bsPutUByte(66);
        this.bsPutUByte(90);
        this.data = new Data(this.blockSize100k);
        this.blockSorter = new BlockSort(this.data);
        this.bsPutUByte(104);
        this.bsPutUByte(48 + this.blockSize100k);
        this.combinedCRC = 0;
        this.initBlock();
    }

    private void initBlock() {
        this.crc.initialiseCRC();
        this.last = -1;
        boolean[] blArray = this.data.inUse;
        int n = 256;
        while (--n >= 0) {
            blArray[n] = false;
        }
    }

    private void endBlock() throws IOException {
        this.blockCRC = this.crc.getFinalCRC();
        this.combinedCRC = this.combinedCRC << 1 | this.combinedCRC >>> 31;
        this.combinedCRC ^= this.blockCRC;
        if (this.last == -1) {
            return;
        }
        this.blockSort();
        this.bsPutUByte(49);
        this.bsPutUByte(65);
        this.bsPutUByte(89);
        this.bsPutUByte(38);
        this.bsPutUByte(83);
        this.bsPutUByte(89);
        this.bsPutInt(this.blockCRC);
        this.bsW(1, 0);
        this.moveToFrontCodeAndSend();
    }

    private void endCompression() throws IOException {
        this.bsPutUByte(23);
        this.bsPutUByte(114);
        this.bsPutUByte(69);
        this.bsPutUByte(56);
        this.bsPutUByte(80);
        this.bsPutUByte(144);
        this.bsPutInt(this.combinedCRC);
        this.bsFinishedWithStream();
    }

    public final int getBlockSize() {
        return this.blockSize100k;
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (n < 0) {
            throw new IndexOutOfBoundsException("offs(" + n + ") < 0.");
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("len(" + n2 + ") < 0.");
        }
        if (n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException("offs(" + n + ") + len(" + n2 + ") > buf.length(" + byArray.length + ").");
        }
        if (this.out == null) {
            throw new IOException("stream closed");
        }
        int n3 = n + n2;
        while (n < n3) {
            this.write0(byArray[n++]);
        }
    }

    private void write0(int n) throws IOException {
        if (this.currentChar != -1) {
            if (this.currentChar == (n &= 0xFF)) {
                if (++this.runLength > 254) {
                    this.writeRun();
                    this.currentChar = -1;
                    this.runLength = 0;
                }
            } else {
                this.writeRun();
                this.runLength = 1;
                this.currentChar = n;
            }
        } else {
            this.currentChar = n & 0xFF;
            ++this.runLength;
        }
    }

    private static void hbAssignCodes(int[] nArray, byte[] byArray, int n, int n2, int n3) {
        int n4 = 0;
        for (int i = n; i <= n2; ++i) {
            for (int j = 0; j < n3; ++j) {
                if ((byArray[j] & 0xFF) != i) continue;
                nArray[j] = n4++;
            }
            n4 <<= 1;
        }
    }

    private void bsFinishedWithStream() throws IOException {
        while (this.bsLive > 0) {
            int n = this.bsBuff >> 24;
            this.out.write(n);
            this.bsBuff <<= 8;
            this.bsLive -= 8;
        }
    }

    private void bsW(int n, int n2) throws IOException {
        int n3;
        OutputStream outputStream = this.out;
        int n4 = this.bsBuff;
        for (n3 = this.bsLive; n3 >= 8; n3 -= 8) {
            outputStream.write(n4 >> 24);
            n4 <<= 8;
        }
        this.bsBuff = n4 | n2 << 32 - n3 - n;
        this.bsLive = n3 + n;
    }

    private void bsPutUByte(int n) throws IOException {
        this.bsW(8, n);
    }

    private void bsPutInt(int n) throws IOException {
        this.bsW(8, n >> 24 & 0xFF);
        this.bsW(8, n >> 16 & 0xFF);
        this.bsW(8, n >> 8 & 0xFF);
        this.bsW(8, n & 0xFF);
    }

    private void sendMTFValues() throws IOException {
        byte[][] byArray = this.data.sendMTFValues_len;
        int n = this.nInUse + 2;
        int n2 = 6;
        while (--n2 >= 0) {
            byte[] byArray2 = byArray[n2];
            int n3 = n;
            while (--n3 >= 0) {
                byArray2[n3] = 15;
            }
        }
        n2 = this.nMTF < 200 ? 2 : (this.nMTF < 600 ? 3 : (this.nMTF < 1200 ? 4 : (this.nMTF < 2400 ? 5 : 6)));
        this.sendMTFValues0(n2, n);
        int n4 = this.sendMTFValues1(n2, n);
        this.sendMTFValues2(n2, n4);
        this.sendMTFValues3(n2, n);
        this.sendMTFValues4();
        this.sendMTFValues5(n2, n4);
        this.sendMTFValues6(n2, n);
        this.sendMTFValues7();
    }

    private void sendMTFValues0(int n, int n2) {
        byte[][] byArray = this.data.sendMTFValues_len;
        int[] nArray = this.data.mtfFreq;
        int n3 = this.nMTF;
        int n4 = 0;
        for (int i = n; i > 0; --i) {
            int n5;
            int n6 = n3 / i;
            int n7 = n4 - 1;
            int n8 = n2 - 1;
            for (n5 = 0; n5 < n6 && n7 < n8; n5 += nArray[++n7]) {
            }
            if (n7 > n4 && i != n && i != 1 && (n - i & 1) != 0) {
                n5 -= nArray[n7--];
            }
            byte[] byArray2 = byArray[i - 1];
            int n9 = n2;
            while (--n9 >= 0) {
                if (n9 >= n4 && n9 <= n7) {
                    byArray2[n9] = 0;
                    continue;
                }
                byArray2[n9] = 15;
            }
            n4 = n7 + 1;
            n3 -= n5;
        }
    }

    private int sendMTFValues1(int n, int n2) {
        Data data = this.data;
        int[][] nArray = data.sendMTFValues_rfreq;
        int[] nArray2 = data.sendMTFValues_fave;
        short[] sArray = data.sendMTFValues_cost;
        char[] cArray = data.sfmap;
        byte[] byArray = data.selector;
        byte[][] byArray2 = data.sendMTFValues_len;
        byte[] byArray3 = byArray2[0];
        byte[] byArray4 = byArray2[5];
        byte[] byArray5 = byArray2[5];
        byte[] byArray6 = byArray2[5];
        byte[] byArray7 = byArray2[5];
        byte[] byArray8 = byArray2[5];
        int n3 = this.nMTF;
        int n4 = 0;
        for (int i = 0; i < 4; ++i) {
            int n5;
            int n6 = n;
            while (--n6 >= 0) {
                nArray2[n6] = 0;
                int[] nArray3 = nArray[n6];
                n5 = n2;
                while (--n5 >= 0) {
                    nArray3[n5] = 0;
                }
            }
            n4 = 0;
            n6 = 0;
            while (n6 < this.nMTF) {
                int n7;
                int n8;
                int n9;
                int n10 = Math.min(n6 + 50 - 1, n3 - 1);
                if (n == 6) {
                    n5 = 0;
                    n9 = 0;
                    n8 = 0;
                    n7 = 0;
                    short s = 0;
                    short s2 = 0;
                    for (int j = n6; j <= n10; ++j) {
                        char c = cArray[j];
                        n5 = (short)(n5 + (byArray3[c] & 0xFF));
                        n9 = (short)(n9 + (byArray4[c] & 0xFF));
                        n8 = (short)(n8 + (byArray5[c] & 0xFF));
                        n7 = (short)(n7 + (byArray6[c] & 0xFF));
                        s = (short)(s + (byArray7[c] & 0xFF));
                        s2 = (short)(s2 + (byArray8[c] & 0xFF));
                    }
                    sArray[0] = n5;
                    sArray[1] = n9;
                    sArray[2] = n8;
                    sArray[3] = n7;
                    sArray[4] = s;
                    sArray[5] = s2;
                } else {
                    n5 = n;
                    while (--n5 >= 0) {
                        sArray[n5] = 0;
                    }
                    for (n5 = n6; n5 <= n10; ++n5) {
                        char c = cArray[n5];
                        n8 = n;
                        while (--n8 >= 0) {
                            int n11 = n8;
                            sArray[n11] = (short)(sArray[n11] + (byArray2[n8][c] & 0xFF));
                        }
                    }
                }
                n5 = -1;
                n9 = n;
                n8 = 999999999;
                while (--n9 >= 0) {
                    n7 = sArray[n9];
                    if (n7 >= n8) continue;
                    n8 = n7;
                    n5 = n9;
                }
                int n12 = n5;
                nArray2[n12] = nArray2[n12] + 1;
                byArray[n4] = (byte)n5;
                ++n4;
                int[] nArray4 = nArray[n5];
                for (n8 = n6; n8 <= n10; ++n8) {
                    char c = cArray[n8];
                    nArray4[c] = nArray4[c] + 1;
                }
                n6 = n10 + 1;
            }
            for (n6 = 0; n6 < n; ++n6) {
                BZip2CompressorOutputStream.hbMakeCodeLengths(byArray2[n6], nArray[n6], this.data, n2, 20);
            }
        }
        return n4;
    }

    private void sendMTFValues2(int n, int n2) {
        Data data = this.data;
        byte[] byArray = data.sendMTFValues2_pos;
        int n3 = n;
        while (--n3 >= 0) {
            byArray[n3] = (byte)n3;
        }
        for (n3 = 0; n3 < n2; ++n3) {
            byte by = data.selector[n3];
            byte by2 = byArray[0];
            int n4 = 0;
            while (by != by2) {
                byte by3 = by2;
                by2 = byArray[++n4];
                byArray[n4] = by3;
            }
            byArray[0] = by2;
            data.selectorMtf[n3] = (byte)n4;
        }
    }

    private void sendMTFValues3(int n, int n2) {
        int[][] nArray = this.data.sendMTFValues_code;
        byte[][] byArray = this.data.sendMTFValues_len;
        for (int i = 0; i < n; ++i) {
            int n3 = 32;
            int n4 = 0;
            byte[] byArray2 = byArray[i];
            int n5 = n2;
            while (--n5 >= 0) {
                int n6 = byArray2[n5] & 0xFF;
                if (n6 > n4) {
                    n4 = n6;
                }
                if (n6 >= n3) continue;
                n3 = n6;
            }
            BZip2CompressorOutputStream.hbAssignCodes(nArray[i], byArray[i], n3, n4, n2);
        }
    }

    private void sendMTFValues4() throws IOException {
        int n;
        int n2;
        boolean[] blArray = this.data.inUse;
        boolean[] blArray2 = this.data.sentMTFValues4_inUse16;
        int n3 = 16;
        while (--n3 >= 0) {
            blArray2[n3] = false;
            n2 = n3 * 16;
            n = 16;
            while (--n >= 0) {
                if (!blArray[n2 + n]) continue;
                blArray2[n3] = true;
            }
        }
        for (n3 = 0; n3 < 16; ++n3) {
            this.bsW(1, blArray2[n3] ? 1 : 0);
        }
        OutputStream outputStream = this.out;
        n2 = this.bsLive;
        n = this.bsBuff;
        for (int i = 0; i < 16; ++i) {
            if (!blArray2[i]) continue;
            int n4 = i * 16;
            for (int j = 0; j < 16; ++j) {
                while (n2 >= 8) {
                    outputStream.write(n >> 24);
                    n <<= 8;
                    n2 -= 8;
                }
                if (blArray[n4 + j]) {
                    n |= 1 << 32 - n2 - 1;
                }
                ++n2;
            }
        }
        this.bsBuff = n;
        this.bsLive = n2;
    }

    private void sendMTFValues5(int n, int n2) throws IOException {
        this.bsW(3, n);
        this.bsW(15, n2);
        OutputStream outputStream = this.out;
        byte[] byArray = this.data.selectorMtf;
        int n3 = this.bsLive;
        int n4 = this.bsBuff;
        for (int i = 0; i < n2; ++i) {
            int n5 = byArray[i] & 0xFF;
            for (int j = 0; j < n5; ++j) {
                while (n3 >= 8) {
                    outputStream.write(n4 >> 24);
                    n4 <<= 8;
                    n3 -= 8;
                }
                n4 |= 1 << 32 - n3 - 1;
                ++n3;
            }
            while (n3 >= 8) {
                outputStream.write(n4 >> 24);
                n4 <<= 8;
                n3 -= 8;
            }
            ++n3;
        }
        this.bsBuff = n4;
        this.bsLive = n3;
    }

    private void sendMTFValues6(int n, int n2) throws IOException {
        byte[][] byArray = this.data.sendMTFValues_len;
        OutputStream outputStream = this.out;
        int n3 = this.bsLive;
        int n4 = this.bsBuff;
        for (int i = 0; i < n; ++i) {
            byte[] byArray2 = byArray[i];
            int n5 = byArray2[0] & 0xFF;
            while (n3 >= 8) {
                outputStream.write(n4 >> 24);
                n4 <<= 8;
                n3 -= 8;
            }
            n4 |= n5 << 32 - n3 - 5;
            n3 += 5;
            for (int j = 0; j < n2; ++j) {
                int n6 = byArray2[j] & 0xFF;
                while (n5 < n6) {
                    while (n3 >= 8) {
                        outputStream.write(n4 >> 24);
                        n4 <<= 8;
                        n3 -= 8;
                    }
                    n4 |= 2 << 32 - n3 - 2;
                    n3 += 2;
                    ++n5;
                }
                while (n5 > n6) {
                    while (n3 >= 8) {
                        outputStream.write(n4 >> 24);
                        n4 <<= 8;
                        n3 -= 8;
                    }
                    n4 |= 3 << 32 - n3 - 2;
                    n3 += 2;
                    --n5;
                }
                while (n3 >= 8) {
                    outputStream.write(n4 >> 24);
                    n4 <<= 8;
                    n3 -= 8;
                }
                ++n3;
            }
        }
        this.bsBuff = n4;
        this.bsLive = n3;
    }

    private void sendMTFValues7() throws IOException {
        Data data = this.data;
        byte[][] byArray = data.sendMTFValues_len;
        int[][] nArray = data.sendMTFValues_code;
        OutputStream outputStream = this.out;
        byte[] byArray2 = data.selector;
        char[] cArray = data.sfmap;
        int n = this.nMTF;
        int n2 = 0;
        int n3 = this.bsLive;
        int n4 = this.bsBuff;
        int n5 = 0;
        while (n5 < n) {
            int n6 = Math.min(n5 + 50 - 1, n - 1);
            int n7 = byArray2[n2] & 0xFF;
            int[] nArray2 = nArray[n7];
            byte[] byArray3 = byArray[n7];
            while (n5 <= n6) {
                char c = cArray[n5];
                while (n3 >= 8) {
                    outputStream.write(n4 >> 24);
                    n4 <<= 8;
                    n3 -= 8;
                }
                int n8 = byArray3[c] & 0xFF;
                n4 |= nArray2[c] << 32 - n3 - n8;
                n3 += n8;
                ++n5;
            }
            n5 = n6 + 1;
            ++n2;
        }
        this.bsBuff = n4;
        this.bsLive = n3;
    }

    private void moveToFrontCodeAndSend() throws IOException {
        this.bsW(24, this.data.origPtr);
        this.generateMTFValues();
        this.sendMTFValues();
    }

    private void blockSort() {
        this.blockSorter.blockSort(this.data, this.last);
    }

    private void generateMTFValues() {
        int n;
        int n2;
        int n3 = this.last;
        Data data = this.data;
        boolean[] blArray = data.inUse;
        byte[] byArray = data.block;
        int[] nArray = data.fmap;
        char[] cArray = data.sfmap;
        int[] nArray2 = data.mtfFreq;
        byte[] byArray2 = data.unseqToSeq;
        byte[] byArray3 = data.generateMTFValues_yy;
        int n4 = 0;
        for (n2 = 0; n2 < 256; ++n2) {
            if (!blArray[n2]) continue;
            byArray2[n2] = (byte)n4;
            ++n4;
        }
        this.nInUse = n4;
        for (n = n2 = n4 + 1; n >= 0; --n) {
            nArray2[n] = 0;
        }
        n = n4;
        while (--n >= 0) {
            byArray3[n] = (byte)n;
        }
        n = 0;
        int n5 = 0;
        for (int i = 0; i <= n3; ++i) {
            byte by = byArray2[byArray[nArray[i]] & 0xFF];
            byte by2 = byArray3[0];
            int n6 = 0;
            while (by != by2) {
                byte by3 = by2;
                by2 = byArray3[++n6];
                byArray3[n6] = by3;
            }
            byArray3[0] = by2;
            if (n6 == 0) {
                ++n5;
                continue;
            }
            if (n5 > 0) {
                --n5;
                while (true) {
                    if ((n5 & 1) == 0) {
                        cArray[n] = '\u0000';
                        ++n;
                        nArray2[0] = nArray2[0] + 1;
                    } else {
                        cArray[n] = '\u0001';
                        ++n;
                        nArray2[1] = nArray2[1] + 1;
                    }
                    if (n5 < 2) break;
                    n5 = n5 - 2 >> 1;
                }
                n5 = 0;
            }
            cArray[n] = (char)(n6 + 1);
            ++n;
            int n7 = n6 + 1;
            nArray2[n7] = nArray2[n7] + 1;
        }
        if (n5 > 0) {
            --n5;
            while (true) {
                if ((n5 & 1) == 0) {
                    cArray[n] = '\u0000';
                    ++n;
                    nArray2[0] = nArray2[0] + 1;
                } else {
                    cArray[n] = '\u0001';
                    ++n;
                    nArray2[1] = nArray2[1] + 1;
                }
                if (n5 < 2) break;
                n5 = n5 - 2 >> 1;
            }
        }
        cArray[n] = (char)n2;
        int n8 = n2;
        nArray2[n8] = nArray2[n8] + 1;
        this.nMTF = n + 1;
    }

    static final class Data {
        final boolean[] inUse = new boolean[256];
        final byte[] unseqToSeq = new byte[256];
        final int[] mtfFreq = new int[258];
        final byte[] selector = new byte[18002];
        final byte[] selectorMtf = new byte[18002];
        final byte[] generateMTFValues_yy = new byte[256];
        final byte[][] sendMTFValues_len = new byte[6][258];
        final int[][] sendMTFValues_rfreq = new int[6][258];
        final int[] sendMTFValues_fave = new int[6];
        final short[] sendMTFValues_cost = new short[6];
        final int[][] sendMTFValues_code = new int[6][258];
        final byte[] sendMTFValues2_pos = new byte[6];
        final boolean[] sentMTFValues4_inUse16 = new boolean[16];
        final int[] heap = new int[260];
        final int[] weight = new int[516];
        final int[] parent = new int[516];
        final byte[] block;
        final int[] fmap;
        final char[] sfmap;
        int origPtr;

        Data(int n) {
            int n2 = n * 100000;
            this.block = new byte[n2 + 1 + 20];
            this.fmap = new int[n2];
            this.sfmap = new char[2 * n2];
        }
    }
}

