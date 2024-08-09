/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Arrays;

public class TrieBuilder {
    public static final int DATA_BLOCK_LENGTH = 32;
    protected int[] m_index_ = new int[34816];
    protected int m_indexLength_;
    protected int m_dataCapacity_;
    protected int m_dataLength_;
    protected boolean m_isLatin1Linear_;
    protected boolean m_isCompacted_;
    protected int[] m_map_;
    protected static final int SHIFT_ = 5;
    protected static final int MAX_INDEX_LENGTH_ = 34816;
    protected static final int BMP_INDEX_LENGTH_ = 2048;
    protected static final int SURROGATE_BLOCK_COUNT_ = 32;
    protected static final int MASK_ = 31;
    protected static final int INDEX_SHIFT_ = 2;
    protected static final int MAX_DATA_LENGTH_ = 262144;
    protected static final int OPTIONS_INDEX_SHIFT_ = 4;
    protected static final int OPTIONS_DATA_IS_32_BIT_ = 256;
    protected static final int OPTIONS_LATIN1_IS_LINEAR_ = 512;
    protected static final int DATA_GRANULARITY_ = 4;
    private static final int MAX_BUILD_TIME_DATA_LENGTH_ = 1115168;

    public boolean isInZeroBlock(int n) {
        if (this.m_isCompacted_ || n > 0x10FFFF || n < 0) {
            return false;
        }
        return this.m_index_[n >> 5] == 0;
    }

    protected TrieBuilder() {
        this.m_map_ = new int[34849];
        this.m_isLatin1Linear_ = false;
        this.m_isCompacted_ = false;
        this.m_indexLength_ = 34816;
    }

    protected TrieBuilder(TrieBuilder trieBuilder) {
        this.m_indexLength_ = trieBuilder.m_indexLength_;
        System.arraycopy(trieBuilder.m_index_, 0, this.m_index_, 0, this.m_indexLength_);
        this.m_dataCapacity_ = trieBuilder.m_dataCapacity_;
        this.m_dataLength_ = trieBuilder.m_dataLength_;
        this.m_map_ = new int[trieBuilder.m_map_.length];
        System.arraycopy(trieBuilder.m_map_, 0, this.m_map_, 0, this.m_map_.length);
        this.m_isLatin1Linear_ = trieBuilder.m_isLatin1Linear_;
        this.m_isCompacted_ = trieBuilder.m_isCompacted_;
    }

    protected static final boolean equal_int(int[] nArray, int n, int n2, int n3) {
        while (n3 > 0 && nArray[n] == nArray[n2]) {
            ++n;
            ++n2;
            --n3;
        }
        return n3 == 0;
    }

    protected void findUnusedBlocks() {
        Arrays.fill(this.m_map_, 255);
        for (int i = 0; i < this.m_indexLength_; ++i) {
            this.m_map_[Math.abs((int)this.m_index_[i]) >> 5] = 0;
        }
        this.m_map_[0] = 0;
    }

    protected static final int findSameIndexBlock(int[] nArray, int n, int n2) {
        for (int i = 2048; i < n; i += 32) {
            if (!TrieBuilder.equal_int(nArray, i, n2, 32)) continue;
            return i;
        }
        return n;
    }

    public static interface DataManipulate {
        public int getFoldedValue(int var1, int var2);
    }
}

