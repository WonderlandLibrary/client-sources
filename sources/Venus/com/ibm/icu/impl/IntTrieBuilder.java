/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.IntTrie;
import com.ibm.icu.impl.Trie;
import com.ibm.icu.impl.TrieBuilder;
import com.ibm.icu.text.UTF16;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class IntTrieBuilder
extends TrieBuilder {
    protected int[] m_data_;
    protected int m_initialValue_;
    private int m_leadUnitValue_;

    public IntTrieBuilder(IntTrieBuilder intTrieBuilder) {
        super(intTrieBuilder);
        this.m_data_ = new int[this.m_dataCapacity_];
        System.arraycopy(intTrieBuilder.m_data_, 0, this.m_data_, 0, this.m_dataLength_);
        this.m_initialValue_ = intTrieBuilder.m_initialValue_;
        this.m_leadUnitValue_ = intTrieBuilder.m_leadUnitValue_;
    }

    public IntTrieBuilder(int[] nArray, int n, int n2, int n3, boolean bl) {
        if (n < 32 || bl && n < 1024) {
            throw new IllegalArgumentException("Argument maxdatalength is too small");
        }
        this.m_data_ = nArray != null ? nArray : new int[n];
        int n4 = 32;
        if (bl) {
            int n5 = 0;
            do {
                this.m_index_[n5++] = n4;
                n4 += 32;
            } while (n5 < 8);
        }
        this.m_dataLength_ = n4;
        Arrays.fill(this.m_data_, 0, this.m_dataLength_, n2);
        this.m_initialValue_ = n2;
        this.m_leadUnitValue_ = n3;
        this.m_dataCapacity_ = n;
        this.m_isLatin1Linear_ = bl;
        this.m_isCompacted_ = false;
    }

    public int getValue(int n) {
        if (this.m_isCompacted_ || n > 0x10FFFF || n < 0) {
            return 1;
        }
        int n2 = this.m_index_[n >> 5];
        return this.m_data_[Math.abs(n2) + (n & 0x1F)];
    }

    public int getValue(int n, boolean[] blArray) {
        if (this.m_isCompacted_ || n > 0x10FFFF || n < 0) {
            if (blArray != null) {
                blArray[0] = true;
            }
            return 1;
        }
        int n2 = this.m_index_[n >> 5];
        if (blArray != null) {
            blArray[0] = n2 == 0;
        }
        return this.m_data_[Math.abs(n2) + (n & 0x1F)];
    }

    public boolean setValue(int n, int n2) {
        if (this.m_isCompacted_ || n > 0x10FFFF || n < 0) {
            return true;
        }
        int n3 = this.getDataBlock(n);
        if (n3 < 0) {
            return true;
        }
        this.m_data_[n3 + (n & 0x1F)] = n2;
        return false;
    }

    public IntTrie serialize(TrieBuilder.DataManipulate dataManipulate, Trie.DataManipulate dataManipulate2) {
        int n;
        if (dataManipulate == null) {
            throw new IllegalArgumentException("Parameters can not be null");
        }
        if (!this.m_isCompacted_) {
            this.compact(false);
            this.fold(dataManipulate);
            this.compact(true);
            this.m_isCompacted_ = true;
        }
        if (this.m_dataLength_ >= 262144) {
            throw new ArrayIndexOutOfBoundsException("Data length too small");
        }
        char[] cArray = new char[this.m_indexLength_];
        int[] nArray = new int[this.m_dataLength_];
        for (n = 0; n < this.m_indexLength_; ++n) {
            cArray[n] = (char)(this.m_index_[n] >>> 2);
        }
        System.arraycopy(this.m_data_, 0, nArray, 0, this.m_dataLength_);
        n = 37;
        n |= 0x100;
        if (this.m_isLatin1Linear_) {
            n |= 0x200;
        }
        return new IntTrie(cArray, nArray, this.m_initialValue_, n, dataManipulate2);
    }

    public int serialize(OutputStream outputStream, boolean bl, TrieBuilder.DataManipulate dataManipulate) throws IOException {
        int n;
        if (dataManipulate == null) {
            throw new IllegalArgumentException("Parameters can not be null");
        }
        if (!this.m_isCompacted_) {
            this.compact(false);
            this.fold(dataManipulate);
            this.compact(true);
            this.m_isCompacted_ = true;
        }
        if ((n = bl ? this.m_dataLength_ + this.m_indexLength_ : this.m_dataLength_) >= 262144) {
            throw new ArrayIndexOutOfBoundsException("Data length too small");
        }
        n = 16 + 2 * this.m_indexLength_;
        n = bl ? (n += 2 * this.m_dataLength_) : (n += 4 * this.m_dataLength_);
        if (outputStream == null) {
            return n;
        }
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(1416784229);
        int n2 = 37;
        if (!bl) {
            n2 |= 0x100;
        }
        if (this.m_isLatin1Linear_) {
            n2 |= 0x200;
        }
        dataOutputStream.writeInt(n2);
        dataOutputStream.writeInt(this.m_indexLength_);
        dataOutputStream.writeInt(this.m_dataLength_);
        if (bl) {
            int n3;
            int n4;
            for (n4 = 0; n4 < this.m_indexLength_; ++n4) {
                n3 = this.m_index_[n4] + this.m_indexLength_ >>> 2;
                dataOutputStream.writeChar(n3);
            }
            for (n4 = 0; n4 < this.m_dataLength_; ++n4) {
                n3 = this.m_data_[n4] & 0xFFFF;
                dataOutputStream.writeChar(n3);
            }
        } else {
            int n5;
            for (n5 = 0; n5 < this.m_indexLength_; ++n5) {
                int n6 = this.m_index_[n5] >>> 2;
                dataOutputStream.writeChar(n6);
            }
            for (n5 = 0; n5 < this.m_dataLength_; ++n5) {
                dataOutputStream.writeInt(this.m_data_[n5]);
            }
        }
        return n;
    }

    public boolean setRange(int n, int n2, int n3, boolean bl) {
        int n4;
        int n5;
        int n6;
        if (this.m_isCompacted_ || n < 0 || n > 0x10FFFF || n2 < 0 || n2 > 0x110000 || n > n2) {
            return true;
        }
        if (n == n2) {
            return false;
        }
        if ((n & 0x1F) != 0) {
            n6 = this.getDataBlock(n);
            if (n6 < 0) {
                return true;
            }
            n5 = n + 32 & 0xFFFFFFE0;
            if (n5 <= n2) {
                this.fillBlock(n6, n & 0x1F, 32, n3, bl);
                n = n5;
            } else {
                this.fillBlock(n6, n & 0x1F, n2 & 0x1F, n3, bl);
                return false;
            }
        }
        n6 = n2 & 0x1F;
        n2 &= 0xFFFFFFE0;
        n5 = 0;
        if (n3 != this.m_initialValue_) {
            n5 = -1;
        }
        while (n < n2) {
            n4 = this.m_index_[n >> 5];
            if (n4 > 0) {
                this.fillBlock(n4, 0, 32, n3, bl);
            } else if (this.m_data_[-n4] != n3 && (n4 == 0 || bl)) {
                if (n5 >= 0) {
                    this.m_index_[n >> 5] = -n5;
                } else {
                    n5 = this.getDataBlock(n);
                    if (n5 < 0) {
                        return true;
                    }
                    this.m_index_[n >> 5] = -n5;
                    this.fillBlock(n5, 0, 32, n3, true);
                }
            }
            n += 32;
        }
        if (n6 > 0) {
            n4 = this.getDataBlock(n);
            if (n4 < 0) {
                return true;
            }
            this.fillBlock(n4, 0, n6, n3, bl);
        }
        return false;
    }

    private int allocDataBlock() {
        int n = this.m_dataLength_;
        int n2 = n + 32;
        if (n2 > this.m_dataCapacity_) {
            return 1;
        }
        this.m_dataLength_ = n2;
        return n;
    }

    private int getDataBlock(int n) {
        int n2 = this.m_index_[n >>= 5];
        if (n2 > 0) {
            return n2;
        }
        int n3 = this.allocDataBlock();
        if (n3 < 0) {
            return 1;
        }
        this.m_index_[n] = n3;
        System.arraycopy(this.m_data_, Math.abs(n2), this.m_data_, n3, 128);
        return n3;
    }

    private void compact(boolean bl) {
        int n;
        int n2;
        if (this.m_isCompacted_) {
            return;
        }
        this.findUnusedBlocks();
        int n3 = 32;
        if (this.m_isLatin1Linear_) {
            n3 += 256;
        }
        int n4 = n2 = 32;
        while (n4 < this.m_dataLength_) {
            if (this.m_map_[n4 >>> 5] < 0) {
                n4 += 32;
                continue;
            }
            if (n4 >= n3 && (n = IntTrieBuilder.findSameDataBlock(this.m_data_, n2, n4, bl ? 4 : 32)) >= 0) {
                this.m_map_[n4 >>> 5] = n;
                n4 += 32;
                continue;
            }
            if (bl && n4 >= n3) {
                for (n = 28; n > 0 && !IntTrieBuilder.equal_int(this.m_data_, n2 - n, n4, n); n -= 4) {
                }
            } else {
                n = 0;
            }
            if (n > 0) {
                this.m_map_[n4 >>> 5] = n2 - n;
                n4 += n;
                for (n = 32 - n; n > 0; --n) {
                    this.m_data_[n2++] = this.m_data_[n4++];
                }
                continue;
            }
            if (n2 < n4) {
                this.m_map_[n4 >>> 5] = n2;
                for (n = 32; n > 0; --n) {
                    this.m_data_[n2++] = this.m_data_[n4++];
                }
                continue;
            }
            this.m_map_[n4 >>> 5] = n4;
            n4 = n2 += 32;
        }
        for (n = 0; n < this.m_indexLength_; ++n) {
            this.m_index_[n] = this.m_map_[Math.abs(this.m_index_[n]) >>> 5];
        }
        this.m_dataLength_ = n2;
    }

    private static final int findSameDataBlock(int[] nArray, int n, int n2, int n3) {
        n -= 32;
        for (int i = 0; i <= n; i += n3) {
            if (!IntTrieBuilder.equal_int(nArray, i, n2, 32)) continue;
            return i;
        }
        return 1;
    }

    private final void fold(TrieBuilder.DataManipulate dataManipulate) {
        int n;
        int[] nArray = new int[32];
        int[] nArray2 = this.m_index_;
        System.arraycopy(nArray2, 1728, nArray, 0, 32);
        int n2 = 0;
        if (this.m_leadUnitValue_ != this.m_initialValue_) {
            n2 = this.allocDataBlock();
            if (n2 < 0) {
                throw new IllegalStateException("Internal error: Out of memory space");
            }
            this.fillBlock(n2, 0, 32, this.m_leadUnitValue_, true);
            n2 = -n2;
        }
        for (n = 1728; n < 1760; ++n) {
            this.m_index_[n] = n2;
        }
        n = 2048;
        int n3 = 65536;
        while (n3 < 0x110000) {
            if (nArray2[n3 >> 5] != 0) {
                int n4;
                if ((n4 = dataManipulate.getFoldedValue(n3 &= 0xFFFFFC00, (n2 = IntTrieBuilder.findSameIndexBlock(nArray2, n, n3 >> 5)) + 32)) != this.getValue(UTF16.getLeadSurrogate(n3))) {
                    if (!this.setValue(UTF16.getLeadSurrogate(n3), n4)) {
                        throw new ArrayIndexOutOfBoundsException("Data table overflow");
                    }
                    if (n2 == n) {
                        System.arraycopy(nArray2, n3 >> 5, nArray2, n, 32);
                        n += 32;
                    }
                }
                n3 += 1024;
                continue;
            }
            n3 += 32;
        }
        if (n >= 34816) {
            throw new ArrayIndexOutOfBoundsException("Index table overflow");
        }
        System.arraycopy(nArray2, 2048, nArray2, 2080, n - 2048);
        System.arraycopy(nArray, 0, nArray2, 2048, 32);
        this.m_indexLength_ = n += 32;
    }

    private void fillBlock(int n, int n2, int n3, int n4, boolean bl) {
        n3 += n;
        n += n2;
        if (bl) {
            while (n < n3) {
                this.m_data_[n++] = n4;
            }
        } else {
            while (n < n3) {
                if (this.m_data_[n] == this.m_initialValue_) {
                    this.m_data_[n] = n4;
                }
                ++n;
            }
        }
    }
}

