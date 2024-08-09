/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.bzip2;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;
import org.apache.commons.compress.compressors.bzip2.CRC;
import org.apache.commons.compress.compressors.bzip2.Rand;

public class BZip2CompressorInputStream
extends CompressorInputStream
implements BZip2Constants {
    private int last;
    private int origPtr;
    private int blockSize100k;
    private boolean blockRandomised;
    private int bsBuff;
    private int bsLive;
    private final CRC crc = new CRC();
    private int nInUse;
    private InputStream in;
    private final boolean decompressConcatenated;
    private static final int EOF = 0;
    private static final int START_BLOCK_STATE = 1;
    private static final int RAND_PART_A_STATE = 2;
    private static final int RAND_PART_B_STATE = 3;
    private static final int RAND_PART_C_STATE = 4;
    private static final int NO_RAND_PART_A_STATE = 5;
    private static final int NO_RAND_PART_B_STATE = 6;
    private static final int NO_RAND_PART_C_STATE = 7;
    private int currentState = 1;
    private int storedBlockCRC;
    private int storedCombinedCRC;
    private int computedBlockCRC;
    private int computedCombinedCRC;
    private int su_count;
    private int su_ch2;
    private int su_chPrev;
    private int su_i2;
    private int su_j2;
    private int su_rNToGo;
    private int su_rTPos;
    private int su_tPos;
    private char su_z;
    private Data data;

    public BZip2CompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public BZip2CompressorInputStream(InputStream inputStream, boolean bl) throws IOException {
        this.in = inputStream;
        this.decompressConcatenated = bl;
        this.init(true);
        this.initBlock();
    }

    public int read() throws IOException {
        if (this.in != null) {
            int n = this.read0();
            this.count(n < 0 ? -1 : 1);
            return n;
        }
        throw new IOException("stream closed");
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (n < 0) {
            throw new IndexOutOfBoundsException("offs(" + n + ") < 0.");
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("len(" + n2 + ") < 0.");
        }
        if (n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException("offs(" + n + ") + len(" + n2 + ") > dest.length(" + byArray.length + ").");
        }
        if (this.in == null) {
            throw new IOException("stream closed");
        }
        int n4 = n + n2;
        int n5 = n;
        while (n5 < n4 && (n3 = this.read0()) >= 0) {
            byArray[n5++] = (byte)n3;
            this.count(1);
        }
        int n6 = n5 == n ? -1 : n5 - n;
        return n6;
    }

    private void makeMaps() {
        boolean[] blArray = this.data.inUse;
        byte[] byArray = this.data.seqToUnseq;
        int n = 0;
        for (int i = 0; i < 256; ++i) {
            if (!blArray[i]) continue;
            byArray[n++] = (byte)i;
        }
        this.nInUse = n;
    }

    private int read0() throws IOException {
        switch (this.currentState) {
            case 0: {
                return 1;
            }
            case 1: {
                return this.setupBlock();
            }
            case 2: {
                throw new IllegalStateException();
            }
            case 3: {
                return this.setupRandPartB();
            }
            case 4: {
                return this.setupRandPartC();
            }
            case 5: {
                throw new IllegalStateException();
            }
            case 6: {
                return this.setupNoRandPartB();
            }
            case 7: {
                return this.setupNoRandPartC();
            }
        }
        throw new IllegalStateException();
    }

    private boolean init(boolean bl) throws IOException {
        if (null == this.in) {
            throw new IOException("No InputStream");
        }
        int n = this.in.read();
        if (n == -1 && !bl) {
            return true;
        }
        int n2 = this.in.read();
        int n3 = this.in.read();
        if (n != 66 || n2 != 90 || n3 != 104) {
            throw new IOException(bl ? "Stream is not in the BZip2 format" : "Garbage after a valid BZip2 stream");
        }
        int n4 = this.in.read();
        if (n4 < 49 || n4 > 57) {
            throw new IOException("BZip2 block size is invalid");
        }
        this.blockSize100k = n4 - 48;
        this.bsLive = 0;
        this.computedCombinedCRC = 0;
        return false;
    }

    private void initBlock() throws IOException {
        char c;
        char c2;
        char c3;
        char c4;
        char c5;
        char c6;
        block3: {
            do {
                c6 = this.bsGetUByte();
                c5 = this.bsGetUByte();
                c4 = this.bsGetUByte();
                c3 = this.bsGetUByte();
                c2 = this.bsGetUByte();
                c = this.bsGetUByte();
                if (c6 != '\u0017' || c5 != 'r' || c4 != 'E' || c3 != '8' || c2 != 'P' || c != '\u0090') break block3;
            } while (!this.complete());
            return;
        }
        if (c6 != '1' || c5 != 'A' || c4 != 'Y' || c3 != '&' || c2 != 'S' || c != 'Y') {
            this.currentState = 0;
            throw new IOException("bad block header");
        }
        this.storedBlockCRC = this.bsGetInt();
        boolean bl = this.blockRandomised = this.bsR(1) == 1;
        if (this.data == null) {
            this.data = new Data(this.blockSize100k);
        }
        this.getAndMoveToFrontDecode();
        this.crc.initialiseCRC();
        this.currentState = 1;
    }

    private void endBlock() throws IOException {
        this.computedBlockCRC = this.crc.getFinalCRC();
        if (this.storedBlockCRC != this.computedBlockCRC) {
            this.computedCombinedCRC = this.storedCombinedCRC << 1 | this.storedCombinedCRC >>> 31;
            this.computedCombinedCRC ^= this.storedBlockCRC;
            throw new IOException("BZip2 CRC error");
        }
        this.computedCombinedCRC = this.computedCombinedCRC << 1 | this.computedCombinedCRC >>> 31;
        this.computedCombinedCRC ^= this.computedBlockCRC;
    }

    private boolean complete() throws IOException {
        this.storedCombinedCRC = this.bsGetInt();
        this.currentState = 0;
        this.data = null;
        if (this.storedCombinedCRC != this.computedCombinedCRC) {
            throw new IOException("BZip2 CRC error");
        }
        return !this.decompressConcatenated || !this.init(false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            try {
                if (inputStream != System.in) {
                    inputStream.close();
                }
            } finally {
                this.data = null;
                this.in = null;
            }
        }
    }

    private int bsR(int n) throws IOException {
        int n2 = this.bsLive;
        int n3 = this.bsBuff;
        if (n2 < n) {
            InputStream inputStream = this.in;
            do {
                int n4;
                if ((n4 = inputStream.read()) < 0) {
                    throw new IOException("unexpected end of stream");
                }
                n3 = n3 << 8 | n4;
            } while ((n2 += 8) < n);
            this.bsBuff = n3;
        }
        this.bsLive = n2 - n;
        return n3 >> n2 - n & (1 << n) - 1;
    }

    private boolean bsGetBit() throws IOException {
        int n = this.bsLive;
        int n2 = this.bsBuff;
        if (n < 1) {
            int n3 = this.in.read();
            if (n3 < 0) {
                throw new IOException("unexpected end of stream");
            }
            n2 = n2 << 8 | n3;
            n += 8;
            this.bsBuff = n2;
        }
        this.bsLive = n - 1;
        return (n2 >> n - 1 & 1) != 0;
    }

    private char bsGetUByte() throws IOException {
        return (char)this.bsR(8);
    }

    private int bsGetInt() throws IOException {
        return ((this.bsR(8) << 8 | this.bsR(8)) << 8 | this.bsR(8)) << 8 | this.bsR(8);
    }

    private static void hbCreateDecodeTables(int[] nArray, int[] nArray2, int[] nArray3, char[] cArray, int n, int n2, int n3) {
        int n4;
        int n5;
        int n6 = 0;
        for (n5 = n; n5 <= n2; ++n5) {
            for (n4 = 0; n4 < n3; ++n4) {
                if (cArray[n4] != n5) continue;
                nArray3[n6++] = n4;
            }
        }
        n5 = 23;
        while (--n5 > 0) {
            nArray2[n5] = 0;
            nArray[n5] = 0;
        }
        for (n5 = 0; n5 < n3; ++n5) {
            int n7 = cArray[n5] + '\u0001';
            nArray2[n7] = nArray2[n7] + 1;
        }
        n6 = nArray2[0];
        for (n5 = 1; n5 < 23; ++n5) {
            nArray2[n5] = n6 += nArray2[n5];
        }
        n6 = 0;
        n4 = nArray2[n5];
        for (n5 = n; n5 <= n2; ++n5) {
            int n8 = nArray2[n5 + 1];
            n4 = n8;
            nArray[n5] = (n6 += n8 - n4) - 1;
            n6 <<= 1;
        }
        for (n5 = n + 1; n5 <= n2; ++n5) {
            nArray2[n5] = (nArray[n5 - 1] + 1 << 1) - nArray2[n5];
        }
    }

    private void recvDecodingTables() throws IOException {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        Data data = this.data;
        boolean[] blArray = data.inUse;
        byte[] byArray = data.recvDecodingTables_pos;
        byte[] byArray2 = data.selector;
        byte[] byArray3 = data.selectorMtf;
        int n7 = 0;
        for (n6 = 0; n6 < 16; ++n6) {
            if (!this.bsGetBit()) continue;
            n7 |= 1 << n6;
        }
        n6 = 256;
        while (--n6 >= 0) {
            blArray[n6] = false;
        }
        for (n6 = 0; n6 < 16; ++n6) {
            if ((n7 & 1 << n6) == 0) continue;
            n5 = n6 << 4;
            for (n4 = 0; n4 < 16; ++n4) {
                if (!this.bsGetBit()) continue;
                blArray[n5 + n4] = true;
            }
        }
        this.makeMaps();
        n6 = this.nInUse + 2;
        n5 = this.bsR(3);
        n4 = this.bsR(15);
        for (n3 = 0; n3 < n4; ++n3) {
            n2 = 0;
            while (this.bsGetBit()) {
                ++n2;
            }
            byArray3[n3] = (byte)n2;
        }
        n3 = n5;
        while (--n3 >= 0) {
            byArray[n3] = (byte)n3;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            n = byArray[n2];
            for (n2 = byArray3[n3] & 0xFF; n2 > 0; --n2) {
                byArray[n2] = byArray[n2 - 1];
            }
            byArray[0] = n;
            byArray2[n3] = n;
        }
        char[][] cArray = data.temp_charArray2d;
        for (n2 = 0; n2 < n5; ++n2) {
            n = this.bsR(5);
            char[] cArray2 = cArray[n2];
            for (int i = 0; i < n6; ++i) {
                while (this.bsGetBit()) {
                    n += this.bsGetBit() ? -1 : 1;
                }
                cArray2[i] = (char)n;
            }
        }
        this.createHuffmanDecodingTables(n6, n5);
    }

    private void createHuffmanDecodingTables(int n, int n2) {
        Data data = this.data;
        char[][] cArray = data.temp_charArray2d;
        int[] nArray = data.minLens;
        int[][] nArray2 = data.limit;
        int[][] nArray3 = data.base;
        int[][] nArray4 = data.perm;
        for (int i = 0; i < n2; ++i) {
            int n3 = 32;
            int n4 = 0;
            char[] cArray2 = cArray[i];
            int n5 = n;
            while (--n5 >= 0) {
                int n6 = cArray2[n5];
                if (n6 > n4) {
                    n4 = n6;
                }
                if (n6 >= n3) continue;
                n3 = n6;
            }
            BZip2CompressorInputStream.hbCreateDecodeTables(nArray2[i], nArray3[i], nArray4[i], cArray[i], n3, n4, n);
            nArray[i] = n3;
        }
    }

    private void getAndMoveToFrontDecode() throws IOException {
        this.origPtr = this.bsR(24);
        this.recvDecodingTables();
        InputStream inputStream = this.in;
        Data data = this.data;
        byte[] byArray = data.ll8;
        int[] nArray = data.unzftab;
        byte[] byArray2 = data.selector;
        byte[] byArray3 = data.seqToUnseq;
        char[] cArray = data.getAndMoveToFrontDecode_yy;
        int[] nArray2 = data.minLens;
        int[][] nArray3 = data.limit;
        int[][] nArray4 = data.base;
        int[][] nArray5 = data.perm;
        int n = this.blockSize100k * 100000;
        int n2 = 256;
        while (--n2 >= 0) {
            cArray[n2] = (char)n2;
            nArray[n2] = 0;
        }
        n2 = 0;
        int n3 = 49;
        int n4 = this.nInUse + 1;
        int n5 = this.getAndMoveToFrontDecode0(0);
        int n6 = this.bsBuff;
        int n7 = this.bsLive;
        int n8 = -1;
        int n9 = byArray2[n2] & 0xFF;
        int[] nArray6 = nArray4[n9];
        int[] nArray7 = nArray3[n9];
        int[] nArray8 = nArray5[n9];
        int n10 = nArray2[n9];
        while (n5 != n4) {
            int n11;
            int n12;
            int n13;
            int n14;
            if (n5 == 0 || n5 == 1) {
                n14 = -1;
                n13 = 1;
                while (true) {
                    if (n5 == 0) {
                        n14 += n13;
                    } else {
                        if (n5 != 1) break;
                        n14 += n13 << 1;
                    }
                    if (n3 == 0) {
                        n3 = 49;
                        n9 = byArray2[++n2] & 0xFF;
                        nArray6 = nArray4[n9];
                        nArray7 = nArray3[n9];
                        nArray8 = nArray5[n9];
                        n10 = nArray2[n9];
                    } else {
                        --n3;
                    }
                    n12 = n10;
                    while (n7 < n12) {
                        n11 = inputStream.read();
                        if (n11 >= 0) {
                            n6 = n6 << 8 | n11;
                            n7 += 8;
                            continue;
                        }
                        throw new IOException("unexpected end of stream");
                    }
                    n11 = n6 >> n7 - n12 & (1 << n12) - 1;
                    n7 -= n12;
                    while (n11 > nArray7[n12]) {
                        ++n12;
                        while (n7 < 1) {
                            int n15 = inputStream.read();
                            if (n15 >= 0) {
                                n6 = n6 << 8 | n15;
                                n7 += 8;
                                continue;
                            }
                            throw new IOException("unexpected end of stream");
                        }
                        n11 = n11 << 1 | n6 >> --n7 & 1;
                    }
                    n5 = nArray8[n11 - nArray6[n12]];
                    n13 <<= 1;
                }
                n13 = byArray3[cArray[0]];
                int n16 = n13 & 0xFF;
                nArray[n16] = nArray[n16] + (n14 + 1);
                while (n14-- >= 0) {
                    byArray[++n8] = n13;
                }
                if (n8 < n) continue;
                throw new IOException("block overrun");
            }
            if (++n8 >= n) {
                throw new IOException("block overrun");
            }
            n14 = cArray[n5 - 1];
            int n17 = byArray3[n14] & 0xFF;
            nArray[n17] = nArray[n17] + 1;
            byArray[n8] = byArray3[n14];
            if (n5 <= 16) {
                n13 = n5 - 1;
                while (n13 > 0) {
                    cArray[n13--] = cArray[n13];
                }
            } else {
                System.arraycopy(cArray, 0, cArray, 1, n5 - 1);
            }
            cArray[0] = n14;
            if (n3 == 0) {
                n3 = 49;
                n9 = byArray2[++n2] & 0xFF;
                nArray6 = nArray4[n9];
                nArray7 = nArray3[n9];
                nArray8 = nArray5[n9];
                n10 = nArray2[n9];
            } else {
                --n3;
            }
            n13 = n10;
            while (n7 < n13) {
                n12 = inputStream.read();
                if (n12 >= 0) {
                    n6 = n6 << 8 | n12;
                    n7 += 8;
                    continue;
                }
                throw new IOException("unexpected end of stream");
            }
            n12 = n6 >> n7 - n13 & (1 << n13) - 1;
            n7 -= n13;
            while (n12 > nArray7[n13]) {
                ++n13;
                while (n7 < 1) {
                    n11 = inputStream.read();
                    if (n11 >= 0) {
                        n6 = n6 << 8 | n11;
                        n7 += 8;
                        continue;
                    }
                    throw new IOException("unexpected end of stream");
                }
                n12 = n12 << 1 | n6 >> --n7 & 1;
            }
            n5 = nArray8[n12 - nArray6[n13]];
        }
        this.last = n8;
        this.bsLive = n7;
        this.bsBuff = n6;
    }

    private int getAndMoveToFrontDecode0(int n) throws IOException {
        InputStream inputStream = this.in;
        Data data = this.data;
        int n2 = data.selector[n] & 0xFF;
        int[] nArray = data.limit[n2];
        int n3 = data.minLens[n2];
        int n4 = this.bsR(n3);
        int n5 = this.bsLive;
        int n6 = this.bsBuff;
        while (n4 > nArray[n3]) {
            ++n3;
            while (n5 < 1) {
                int n7 = inputStream.read();
                if (n7 >= 0) {
                    n6 = n6 << 8 | n7;
                    n5 += 8;
                    continue;
                }
                throw new IOException("unexpected end of stream");
            }
            n4 = n4 << 1 | n6 >> --n5 & 1;
        }
        this.bsLive = n5;
        this.bsBuff = n6;
        return data.perm[n2][n4 - data.base[n2][n3]];
    }

    private int setupBlock() throws IOException {
        int n;
        if (this.currentState == 0 || this.data == null) {
            return 1;
        }
        int[] nArray = this.data.cftab;
        int[] nArray2 = this.data.initTT(this.last + 1);
        byte[] byArray = this.data.ll8;
        nArray[0] = 0;
        System.arraycopy(this.data.unzftab, 0, nArray, 1, 256);
        int n2 = nArray[0];
        for (n = 1; n <= 256; ++n) {
            nArray[n] = n2 += nArray[n];
        }
        n = 0;
        n2 = this.last;
        while (n <= n2) {
            int n3 = byArray[n] & 0xFF;
            int n4 = nArray[n3];
            nArray[n3] = n4 + 1;
            nArray2[n4] = n++;
        }
        if (this.origPtr < 0 || this.origPtr >= nArray2.length) {
            throw new IOException("stream corrupted");
        }
        this.su_tPos = nArray2[this.origPtr];
        this.su_count = 0;
        this.su_i2 = 0;
        this.su_ch2 = 256;
        if (this.blockRandomised) {
            this.su_rNToGo = 0;
            this.su_rTPos = 0;
            return this.setupRandPartA();
        }
        return this.setupNoRandPartA();
    }

    private int setupRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            int n = this.data.ll8[this.su_tPos] & 0xFF;
            this.su_tPos = this.data.tt[this.su_tPos];
            if (this.su_rNToGo == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                if (++this.su_rTPos == 512) {
                    this.su_rTPos = 0;
                }
            } else {
                --this.su_rNToGo;
            }
            this.su_ch2 = n ^= this.su_rNToGo == 1 ? 1 : 0;
            ++this.su_i2;
            this.currentState = 3;
            this.crc.updateCRC(n);
            return n;
        }
        this.endBlock();
        this.initBlock();
        return this.setupBlock();
    }

    private int setupNoRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            int n;
            this.su_chPrev = this.su_ch2;
            this.su_ch2 = n = this.data.ll8[this.su_tPos] & 0xFF;
            this.su_tPos = this.data.tt[this.su_tPos];
            ++this.su_i2;
            this.currentState = 6;
            this.crc.updateCRC(n);
            return n;
        }
        this.currentState = 5;
        this.endBlock();
        this.initBlock();
        return this.setupBlock();
    }

    private int setupRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.currentState = 2;
            this.su_count = 1;
            return this.setupRandPartA();
        }
        if (++this.su_count >= 4) {
            this.su_z = (char)(this.data.ll8[this.su_tPos] & 0xFF);
            this.su_tPos = this.data.tt[this.su_tPos];
            if (this.su_rNToGo == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                if (++this.su_rTPos == 512) {
                    this.su_rTPos = 0;
                }
            } else {
                --this.su_rNToGo;
            }
            this.su_j2 = 0;
            this.currentState = 4;
            if (this.su_rNToGo == 1) {
                this.su_z = (char)(this.su_z ^ '\u0001');
            }
            return this.setupRandPartC();
        }
        this.currentState = 2;
        return this.setupRandPartA();
    }

    private int setupRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            this.crc.updateCRC(this.su_ch2);
            ++this.su_j2;
            return this.su_ch2;
        }
        this.currentState = 2;
        ++this.su_i2;
        this.su_count = 0;
        return this.setupRandPartA();
    }

    private int setupNoRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.su_count = 1;
            return this.setupNoRandPartA();
        }
        if (++this.su_count >= 4) {
            this.su_z = (char)(this.data.ll8[this.su_tPos] & 0xFF);
            this.su_tPos = this.data.tt[this.su_tPos];
            this.su_j2 = 0;
            return this.setupNoRandPartC();
        }
        return this.setupNoRandPartA();
    }

    private int setupNoRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            int n = this.su_ch2;
            this.crc.updateCRC(n);
            ++this.su_j2;
            this.currentState = 7;
            return n;
        }
        ++this.su_i2;
        this.su_count = 0;
        return this.setupNoRandPartA();
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < 3) {
            return true;
        }
        if (byArray[0] != 66) {
            return true;
        }
        if (byArray[1] != 90) {
            return true;
        }
        return byArray[2] != 104;
    }

    private static final class Data {
        final boolean[] inUse = new boolean[256];
        final byte[] seqToUnseq = new byte[256];
        final byte[] selector = new byte[18002];
        final byte[] selectorMtf = new byte[18002];
        final int[] unzftab = new int[256];
        final int[][] limit = new int[6][258];
        final int[][] base = new int[6][258];
        final int[][] perm = new int[6][258];
        final int[] minLens = new int[6];
        final int[] cftab = new int[257];
        final char[] getAndMoveToFrontDecode_yy = new char[256];
        final char[][] temp_charArray2d = new char[6][258];
        final byte[] recvDecodingTables_pos = new byte[6];
        int[] tt;
        byte[] ll8;

        Data(int n) {
            this.ll8 = new byte[n * 100000];
        }

        int[] initTT(int n) {
            int[] nArray = this.tt;
            if (nArray == null || nArray.length < n) {
                this.tt = nArray = new int[n];
            }
            return nArray;
        }
    }
}

