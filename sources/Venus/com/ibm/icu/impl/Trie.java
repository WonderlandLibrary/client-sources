/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.text.UTF16;
import java.nio.ByteBuffer;
import java.util.Arrays;

public abstract class Trie {
    protected static final int LEAD_INDEX_OFFSET_ = 320;
    protected static final int INDEX_STAGE_1_SHIFT_ = 5;
    protected static final int INDEX_STAGE_2_SHIFT_ = 2;
    protected static final int DATA_BLOCK_LENGTH = 32;
    protected static final int INDEX_STAGE_3_MASK_ = 31;
    protected static final int SURROGATE_BLOCK_BITS = 5;
    protected static final int SURROGATE_BLOCK_COUNT = 32;
    protected static final int BMP_INDEX_LENGTH = 2048;
    protected static final int SURROGATE_MASK_ = 1023;
    protected char[] m_index_;
    protected DataManipulate m_dataManipulate_;
    protected int m_dataOffset_;
    protected int m_dataLength_;
    protected static final int HEADER_LENGTH_ = 16;
    protected static final int HEADER_OPTIONS_LATIN1_IS_LINEAR_MASK_ = 512;
    protected static final int HEADER_SIGNATURE_ = 1416784229;
    private static final int HEADER_OPTIONS_SHIFT_MASK_ = 15;
    protected static final int HEADER_OPTIONS_INDEX_SHIFT_ = 4;
    protected static final int HEADER_OPTIONS_DATA_IS_32_BIT_ = 256;
    private boolean m_isLatin1Linear_;
    private int m_options_;
    static final boolean $assertionsDisabled = !Trie.class.desiredAssertionStatus();

    public final boolean isLatin1Linear() {
        return this.m_isLatin1Linear_;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Trie)) {
            return true;
        }
        Trie trie = (Trie)object;
        return this.m_isLatin1Linear_ == trie.m_isLatin1Linear_ && this.m_options_ == trie.m_options_ && this.m_dataLength_ == trie.m_dataLength_ && Arrays.equals(this.m_index_, trie.m_index_);
    }

    public int hashCode() {
        if (!$assertionsDisabled) {
            throw new AssertionError((Object)"hashCode not designed");
        }
        return 1;
    }

    public int getSerializedDataSize() {
        int n = 16;
        n += this.m_dataOffset_ << 1;
        if (this.isCharTrie()) {
            n += this.m_dataLength_ << 1;
        } else if (this.isIntTrie()) {
            n += this.m_dataLength_ << 2;
        }
        return n;
    }

    protected Trie(ByteBuffer byteBuffer, DataManipulate dataManipulate) {
        int n = byteBuffer.getInt();
        this.m_options_ = byteBuffer.getInt();
        if (!this.checkHeader(n)) {
            throw new IllegalArgumentException("ICU data file error: Trie header authentication failed, please check if you have the most updated ICU data file");
        }
        this.m_dataManipulate_ = dataManipulate != null ? dataManipulate : new DefaultGetFoldingOffset(null);
        this.m_isLatin1Linear_ = (this.m_options_ & 0x200) != 0;
        this.m_dataOffset_ = byteBuffer.getInt();
        this.m_dataLength_ = byteBuffer.getInt();
        this.unserialize(byteBuffer);
    }

    protected Trie(char[] cArray, int n, DataManipulate dataManipulate) {
        this.m_options_ = n;
        this.m_dataManipulate_ = dataManipulate != null ? dataManipulate : new DefaultGetFoldingOffset(null);
        this.m_isLatin1Linear_ = (this.m_options_ & 0x200) != 0;
        this.m_index_ = cArray;
        this.m_dataOffset_ = this.m_index_.length;
    }

    protected abstract int getSurrogateOffset(char var1, char var2);

    protected abstract int getValue(int var1);

    protected abstract int getInitialValue();

    protected final int getRawOffset(int n, char c) {
        return (this.m_index_[n + (c >> 5)] << 2) + (c & 0x1F);
    }

    protected final int getBMPOffset(char c) {
        return c >= '\ud800' && c <= '\udbff' ? this.getRawOffset(320, c) : this.getRawOffset(0, c);
    }

    protected final int getLeadOffset(char c) {
        return this.getRawOffset(0, c);
    }

    protected final int getCodePointOffset(int n) {
        if (n < 0) {
            return 1;
        }
        if (n < 55296) {
            return this.getRawOffset(0, (char)n);
        }
        if (n < 65536) {
            return this.getBMPOffset((char)n);
        }
        if (n <= 0x10FFFF) {
            return this.getSurrogateOffset(UTF16.getLeadSurrogate(n), (char)(n & 0x3FF));
        }
        return 1;
    }

    protected void unserialize(ByteBuffer byteBuffer) {
        this.m_index_ = ICUBinary.getChars(byteBuffer, this.m_dataOffset_, 0);
    }

    protected final boolean isIntTrie() {
        return (this.m_options_ & 0x100) != 0;
    }

    protected final boolean isCharTrie() {
        return (this.m_options_ & 0x100) == 0;
    }

    private final boolean checkHeader(int n) {
        if (n != 1416784229) {
            return true;
        }
        return (this.m_options_ & 0xF) != 5 || (this.m_options_ >> 4 & 0xF) != 2;
    }

    private static class DefaultGetFoldingOffset
    implements DataManipulate {
        private DefaultGetFoldingOffset() {
        }

        @Override
        public int getFoldingOffset(int n) {
            return n;
        }

        DefaultGetFoldingOffset(1 var1_1) {
            this();
        }
    }

    public static interface DataManipulate {
        public int getFoldingOffset(int var1);
    }
}

