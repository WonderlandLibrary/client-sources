/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Trie2_16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class UBiDiProps {
    private int[] indexes;
    private int[] mirrors;
    private byte[] jgArray;
    private byte[] jgArray2;
    private Trie2_16 trie;
    private static final String DATA_NAME = "ubidi";
    private static final String DATA_TYPE = "icu";
    private static final String DATA_FILE_NAME = "ubidi.icu";
    private static final int FMT = 1114195049;
    private static final int IX_TRIE_SIZE = 2;
    private static final int IX_MIRROR_LENGTH = 3;
    private static final int IX_JG_START = 4;
    private static final int IX_JG_LIMIT = 5;
    private static final int IX_JG_START2 = 6;
    private static final int IX_JG_LIMIT2 = 7;
    private static final int IX_MAX_VALUES = 15;
    private static final int IX_TOP = 16;
    private static final int JT_SHIFT = 5;
    private static final int BPT_SHIFT = 8;
    private static final int JOIN_CONTROL_SHIFT = 10;
    private static final int BIDI_CONTROL_SHIFT = 11;
    private static final int IS_MIRRORED_SHIFT = 12;
    private static final int MIRROR_DELTA_SHIFT = 13;
    private static final int MAX_JG_SHIFT = 16;
    private static final int CLASS_MASK = 31;
    private static final int JT_MASK = 224;
    private static final int BPT_MASK = 768;
    private static final int MAX_JG_MASK = 0xFF0000;
    private static final int ESC_MIRROR_DELTA = -4;
    private static final int MIRROR_INDEX_SHIFT = 21;
    public static final UBiDiProps INSTANCE;

    private UBiDiProps() throws IOException {
        ByteBuffer byteBuffer = ICUBinary.getData(DATA_FILE_NAME);
        this.readData(byteBuffer);
    }

    private void readData(ByteBuffer byteBuffer) throws IOException {
        ICUBinary.readHeader(byteBuffer, 1114195049, new IsAcceptable(null));
        int n = byteBuffer.getInt();
        if (n < 16) {
            throw new IOException("indexes[0] too small in ubidi.icu");
        }
        this.indexes = new int[n];
        this.indexes[0] = n;
        for (int i = 1; i < n; ++i) {
            this.indexes[i] = byteBuffer.getInt();
        }
        this.trie = Trie2_16.createFromSerialized(byteBuffer);
        int n2 = this.indexes[2];
        int n3 = this.trie.getSerializedLength();
        if (n3 > n2) {
            throw new IOException("ubidi.icu: not enough bytes for the trie");
        }
        ICUBinary.skipBytes(byteBuffer, n2 - n3);
        n = this.indexes[3];
        if (n > 0) {
            this.mirrors = ICUBinary.getInts(byteBuffer, n, 0);
        }
        n = this.indexes[5] - this.indexes[4];
        this.jgArray = new byte[n];
        byteBuffer.get(this.jgArray);
        n = this.indexes[7] - this.indexes[6];
        this.jgArray2 = new byte[n];
        byteBuffer.get(this.jgArray2);
    }

    public final void addPropertyStarts(UnicodeSet unicodeSet) {
        int n;
        for (Trie2.Range range : this.trie) {
            if (range.leadSurrogate) break;
            unicodeSet.add(range.startCodePoint);
        }
        int n2 = this.indexes[3];
        for (n = 0; n < n2; ++n) {
            int n3 = UBiDiProps.getMirrorCodePoint(this.mirrors[n]);
            unicodeSet.add(n3, n3 + 1);
        }
        int n4 = this.indexes[4];
        int n5 = this.indexes[5];
        byte[] byArray = this.jgArray;
        while (true) {
            n2 = n5 - n4;
            byte by = 0;
            for (n = 0; n < n2; ++n) {
                byte by2 = byArray[n];
                if (by2 != by) {
                    unicodeSet.add(n4);
                    by = by2;
                }
                ++n4;
            }
            if (by != 0) {
                unicodeSet.add(n5);
            }
            if (n5 != this.indexes[5]) break;
            n4 = this.indexes[6];
            n5 = this.indexes[7];
            byArray = this.jgArray2;
        }
    }

    public final int getMaxValue(int n) {
        int n2 = this.indexes[15];
        switch (n) {
            case 4096: {
                return n2 & 0x1F;
            }
            case 4102: {
                return (n2 & 0xFF0000) >> 16;
            }
            case 4103: {
                return (n2 & 0xE0) >> 5;
            }
            case 4117: {
                return (n2 & 0x300) >> 8;
            }
        }
        return 1;
    }

    public final int getClass(int n) {
        return UBiDiProps.getClassFromProps(this.trie.get(n));
    }

    public final boolean isMirrored(int n) {
        return UBiDiProps.getFlagFromProps(this.trie.get(n), 12);
    }

    private final int getMirror(int n, int n2) {
        int n3 = UBiDiProps.getMirrorDeltaFromProps(n2);
        if (n3 != -4) {
            return n + n3;
        }
        int n4 = this.indexes[3];
        for (int i = 0; i < n4; ++i) {
            int n5 = this.mirrors[i];
            int n6 = UBiDiProps.getMirrorCodePoint(n5);
            if (n == n6) {
                return UBiDiProps.getMirrorCodePoint(this.mirrors[UBiDiProps.getMirrorIndex(n5)]);
            }
            if (n < n6) break;
        }
        return n;
    }

    public final int getMirror(int n) {
        int n2 = this.trie.get(n);
        return this.getMirror(n, n2);
    }

    public final boolean isBidiControl(int n) {
        return UBiDiProps.getFlagFromProps(this.trie.get(n), 11);
    }

    public final boolean isJoinControl(int n) {
        return UBiDiProps.getFlagFromProps(this.trie.get(n), 10);
    }

    public final int getJoiningType(int n) {
        return (this.trie.get(n) & 0xE0) >> 5;
    }

    public final int getJoiningGroup(int n) {
        int n2 = this.indexes[4];
        int n3 = this.indexes[5];
        if (n2 <= n && n < n3) {
            return this.jgArray[n - n2] & 0xFF;
        }
        n2 = this.indexes[6];
        n3 = this.indexes[7];
        if (n2 <= n && n < n3) {
            return this.jgArray2[n - n2] & 0xFF;
        }
        return 1;
    }

    public final int getPairedBracketType(int n) {
        return (this.trie.get(n) & 0x300) >> 8;
    }

    public final int getPairedBracket(int n) {
        int n2 = this.trie.get(n);
        if ((n2 & 0x300) == 0) {
            return n;
        }
        return this.getMirror(n, n2);
    }

    private static final int getClassFromProps(int n) {
        return n & 0x1F;
    }

    private static final boolean getFlagFromProps(int n, int n2) {
        return (n >> n2 & 1) != 0;
    }

    private static final int getMirrorDeltaFromProps(int n) {
        return (short)n >> 13;
    }

    private static final int getMirrorCodePoint(int n) {
        return n & 0x1FFFFF;
    }

    private static final int getMirrorIndex(int n) {
        return n >>> 21;
    }

    static {
        try {
            INSTANCE = new UBiDiProps();
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] byArray) {
            return byArray[0] == 2;
        }

        IsAcceptable(1 var1_1) {
            this();
        }
    }
}

