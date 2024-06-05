package com.ibm.icu.impl;

import java.nio.*;
import java.io.*;
import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.util.*;

public final class UBiDiProps
{
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
    private static final int MAX_JG_MASK = 16711680;
    private static final int ESC_MIRROR_DELTA = -4;
    private static final int MIRROR_INDEX_SHIFT = 21;
    public static final UBiDiProps INSTANCE;
    
    private UBiDiProps() throws IOException {
        final ByteBuffer bytes = ICUBinary.getData("ubidi.icu");
        this.readData(bytes);
    }
    
    private void readData(final ByteBuffer bytes) throws IOException {
        ICUBinary.readHeader(bytes, 1114195049, new IsAcceptable());
        int count = bytes.getInt();
        if (count < 16) {
            throw new IOException("indexes[0] too small in ubidi.icu");
        }
        (this.indexes = new int[count])[0] = count;
        for (int i = 1; i < count; ++i) {
            this.indexes[i] = bytes.getInt();
        }
        this.trie = Trie2_16.createFromSerialized(bytes);
        final int expectedTrieLength = this.indexes[2];
        final int trieLength = this.trie.getSerializedLength();
        if (trieLength > expectedTrieLength) {
            throw new IOException("ubidi.icu: not enough bytes for the trie");
        }
        ICUBinary.skipBytes(bytes, expectedTrieLength - trieLength);
        count = this.indexes[3];
        if (count > 0) {
            this.mirrors = ICUBinary.getInts(bytes, count, 0);
        }
        count = this.indexes[5] - this.indexes[4];
        bytes.get(this.jgArray = new byte[count]);
        count = this.indexes[7] - this.indexes[6];
        bytes.get(this.jgArray2 = new byte[count]);
    }
    
    public final void addPropertyStarts(final UnicodeSet set) {
        final Iterator<Trie2.Range> trieIterator = this.trie.iterator();
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
        for (int length = this.indexes[3], i = 0; i < length; ++i) {
            final int c = getMirrorCodePoint(this.mirrors[i]);
            set.add(c, c + 1);
        }
        int start = this.indexes[4];
        int limit = this.indexes[5];
        byte[] jga = this.jgArray;
        while (true) {
            final int length = limit - start;
            byte prev = 0;
            for (final byte jg : jga) {
                if (jg != prev) {
                    set.add(start);
                    prev = jg;
                }
                ++start;
            }
            if (prev != 0) {
                set.add(limit);
            }
            if (limit != this.indexes[5]) {
                break;
            }
            start = this.indexes[6];
            limit = this.indexes[7];
            jga = this.jgArray2;
        }
    }
    
    public final int getMaxValue(final int which) {
        final int max = this.indexes[15];
        switch (which) {
            case 4096: {
                return max & 0x1F;
            }
            case 4102: {
                return (max & 0xFF0000) >> 16;
            }
            case 4103: {
                return (max & 0xE0) >> 5;
            }
            case 4117: {
                return (max & 0x300) >> 8;
            }
            default: {
                return -1;
            }
        }
    }
    
    public final int getClass(final int c) {
        return getClassFromProps(this.trie.get(c));
    }
    
    public final boolean isMirrored(final int c) {
        return getFlagFromProps(this.trie.get(c), 12);
    }
    
    private final int getMirror(final int c, final int props) {
        final int delta = getMirrorDeltaFromProps(props);
        if (delta != -4) {
            return c + delta;
        }
        for (int length = this.indexes[3], i = 0; i < length; ++i) {
            final int m = this.mirrors[i];
            final int c2 = getMirrorCodePoint(m);
            if (c == c2) {
                return getMirrorCodePoint(this.mirrors[getMirrorIndex(m)]);
            }
            if (c < c2) {
                break;
            }
        }
        return c;
    }
    
    public final int getMirror(final int c) {
        final int props = this.trie.get(c);
        return this.getMirror(c, props);
    }
    
    public final boolean isBidiControl(final int c) {
        return getFlagFromProps(this.trie.get(c), 11);
    }
    
    public final boolean isJoinControl(final int c) {
        return getFlagFromProps(this.trie.get(c), 10);
    }
    
    public final int getJoiningType(final int c) {
        return (this.trie.get(c) & 0xE0) >> 5;
    }
    
    public final int getJoiningGroup(final int c) {
        int start = this.indexes[4];
        int limit = this.indexes[5];
        if (start <= c && c < limit) {
            return this.jgArray[c - start] & 0xFF;
        }
        start = this.indexes[6];
        limit = this.indexes[7];
        if (start <= c && c < limit) {
            return this.jgArray2[c - start] & 0xFF;
        }
        return 0;
    }
    
    public final int getPairedBracketType(final int c) {
        return (this.trie.get(c) & 0x300) >> 8;
    }
    
    public final int getPairedBracket(final int c) {
        final int props = this.trie.get(c);
        if ((props & 0x300) == 0x0) {
            return c;
        }
        return this.getMirror(c, props);
    }
    
    private static final int getClassFromProps(final int props) {
        return props & 0x1F;
    }
    
    private static final boolean getFlagFromProps(final int props, final int shift) {
        return (props >> shift & 0x1) != 0x0;
    }
    
    private static final int getMirrorDeltaFromProps(final int props) {
        return (short)props >> 13;
    }
    
    private static final int getMirrorCodePoint(final int m) {
        return m & 0x1FFFFF;
    }
    
    private static final int getMirrorIndex(final int m) {
        return m >>> 21;
    }
    
    static {
        try {
            INSTANCE = new UBiDiProps();
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        @Override
        public boolean isDataVersionAcceptable(final byte[] version) {
            return version[0] == 2;
        }
    }
}
