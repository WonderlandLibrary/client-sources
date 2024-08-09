/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Trie;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.util.RangeValueIterator;

public class TrieIterator
implements RangeValueIterator {
    private static final int BMP_INDEX_LENGTH_ = 2048;
    private static final int LEAD_SURROGATE_MIN_VALUE_ = 55296;
    private static final int TRAIL_SURROGATE_MIN_VALUE_ = 56320;
    private static final int TRAIL_SURROGATE_COUNT_ = 1024;
    private static final int TRAIL_SURROGATE_INDEX_BLOCK_LENGTH_ = 32;
    private static final int DATA_BLOCK_LENGTH_ = 32;
    private Trie m_trie_;
    private int m_initialValue_;
    private int m_currentCodepoint_;
    private int m_nextCodepoint_;
    private int m_nextValue_;
    private int m_nextIndex_;
    private int m_nextBlock_;
    private int m_nextBlockIndex_;
    private int m_nextTrailIndexOffset_;

    public TrieIterator(Trie trie) {
        if (trie == null) {
            throw new IllegalArgumentException("Argument trie cannot be null");
        }
        this.m_trie_ = trie;
        this.m_initialValue_ = this.extract(this.m_trie_.getInitialValue());
        this.reset();
    }

    @Override
    public final boolean next(RangeValueIterator.Element element) {
        if (this.m_nextCodepoint_ > 0x10FFFF) {
            return true;
        }
        if (this.m_nextCodepoint_ < 65536 && this.calculateNextBMPElement(element)) {
            return false;
        }
        this.calculateNextSupplementaryElement(element);
        return false;
    }

    @Override
    public final void reset() {
        this.m_currentCodepoint_ = 0;
        this.m_nextCodepoint_ = 0;
        this.m_nextIndex_ = 0;
        this.m_nextBlock_ = this.m_trie_.m_index_[0] << 2;
        this.m_nextValue_ = this.m_nextBlock_ == this.m_trie_.m_dataOffset_ ? this.m_initialValue_ : this.extract(this.m_trie_.getValue(this.m_nextBlock_));
        this.m_nextBlockIndex_ = 0;
        this.m_nextTrailIndexOffset_ = 32;
    }

    protected int extract(int n) {
        return n;
    }

    private final void setResult(RangeValueIterator.Element element, int n, int n2, int n3) {
        element.start = n;
        element.limit = n2;
        element.value = n3;
    }

    private final boolean calculateNextBMPElement(RangeValueIterator.Element element) {
        int n = this.m_nextValue_;
        this.m_currentCodepoint_ = this.m_nextCodepoint_++;
        ++this.m_nextBlockIndex_;
        if (!this.checkBlockDetail(n)) {
            this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
            return false;
        }
        while (this.m_nextCodepoint_ < 65536) {
            this.m_nextIndex_ = this.m_nextCodepoint_ == 55296 ? 2048 : (this.m_nextCodepoint_ == 56320 ? this.m_nextCodepoint_ >> 5 : ++this.m_nextIndex_);
            this.m_nextBlockIndex_ = 0;
            if (this.checkBlock(n)) continue;
            this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
            return false;
        }
        --this.m_nextCodepoint_;
        --this.m_nextBlockIndex_;
        return true;
    }

    private final void calculateNextSupplementaryElement(RangeValueIterator.Element element) {
        int n = this.m_nextValue_;
        ++this.m_nextCodepoint_;
        ++this.m_nextBlockIndex_;
        if (UTF16.getTrailSurrogate(this.m_nextCodepoint_) != '\udc00') {
            if (!this.checkNullNextTrailIndex() && !this.checkBlockDetail(n)) {
                this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                this.m_currentCodepoint_ = this.m_nextCodepoint_;
                return;
            }
            ++this.m_nextIndex_;
            ++this.m_nextTrailIndexOffset_;
            if (!this.checkTrailBlock(n)) {
                this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                this.m_currentCodepoint_ = this.m_nextCodepoint_;
                return;
            }
        }
        int n2 = UTF16.getLeadSurrogate(this.m_nextCodepoint_);
        while (n2 < 56320) {
            int n3 = this.m_trie_.m_index_[n2 >> 5] << 2;
            if (n3 == this.m_trie_.m_dataOffset_) {
                if (n != this.m_initialValue_) {
                    this.m_nextValue_ = this.m_initialValue_;
                    this.m_nextBlock_ = n3;
                    this.m_nextBlockIndex_ = 0;
                    this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                    this.m_currentCodepoint_ = this.m_nextCodepoint_;
                    return;
                }
                this.m_nextCodepoint_ = Character.toCodePoint((char)(n2 += 32), '\udc00');
                continue;
            }
            if (this.m_trie_.m_dataManipulate_ == null) {
                throw new NullPointerException("The field DataManipulate in this Trie is null");
            }
            this.m_nextIndex_ = this.m_trie_.m_dataManipulate_.getFoldingOffset(this.m_trie_.getValue(n3 + (n2 & 0x1F)));
            if (this.m_nextIndex_ <= 0) {
                if (n != this.m_initialValue_) {
                    this.m_nextValue_ = this.m_initialValue_;
                    this.m_nextBlock_ = this.m_trie_.m_dataOffset_;
                    this.m_nextBlockIndex_ = 0;
                    this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                    this.m_currentCodepoint_ = this.m_nextCodepoint_;
                    return;
                }
                this.m_nextCodepoint_ += 1024;
            } else {
                this.m_nextTrailIndexOffset_ = 0;
                if (!this.checkTrailBlock(n)) {
                    this.setResult(element, this.m_currentCodepoint_, this.m_nextCodepoint_, n);
                    this.m_currentCodepoint_ = this.m_nextCodepoint_;
                    return;
                }
            }
            ++n2;
        }
        this.setResult(element, this.m_currentCodepoint_, 0x110000, n);
    }

    private final boolean checkBlockDetail(int n) {
        while (this.m_nextBlockIndex_ < 32) {
            this.m_nextValue_ = this.extract(this.m_trie_.getValue(this.m_nextBlock_ + this.m_nextBlockIndex_));
            if (this.m_nextValue_ != n) {
                return true;
            }
            ++this.m_nextBlockIndex_;
            ++this.m_nextCodepoint_;
        }
        return false;
    }

    private final boolean checkBlock(int n) {
        int n2 = this.m_nextBlock_;
        this.m_nextBlock_ = this.m_trie_.m_index_[this.m_nextIndex_] << 2;
        if (this.m_nextBlock_ == n2 && this.m_nextCodepoint_ - this.m_currentCodepoint_ >= 32) {
            this.m_nextCodepoint_ += 32;
        } else if (this.m_nextBlock_ == this.m_trie_.m_dataOffset_) {
            if (n != this.m_initialValue_) {
                this.m_nextValue_ = this.m_initialValue_;
                this.m_nextBlockIndex_ = 0;
                return true;
            }
            this.m_nextCodepoint_ += 32;
        } else if (!this.checkBlockDetail(n)) {
            return true;
        }
        return false;
    }

    private final boolean checkTrailBlock(int n) {
        while (this.m_nextTrailIndexOffset_ < 32) {
            this.m_nextBlockIndex_ = 0;
            if (!this.checkBlock(n)) {
                return true;
            }
            ++this.m_nextTrailIndexOffset_;
            ++this.m_nextIndex_;
        }
        return false;
    }

    private final boolean checkNullNextTrailIndex() {
        if (this.m_nextIndex_ <= 0) {
            this.m_nextCodepoint_ += 1023;
            char c = UTF16.getLeadSurrogate(this.m_nextCodepoint_);
            int n = this.m_trie_.m_index_[c >> 5] << 2;
            if (this.m_trie_.m_dataManipulate_ == null) {
                throw new NullPointerException("The field DataManipulate in this Trie is null");
            }
            this.m_nextIndex_ = this.m_trie_.m_dataManipulate_.getFoldingOffset(this.m_trie_.getValue(n + (c & 0x1F)));
            --this.m_nextIndex_;
            this.m_nextBlockIndex_ = 32;
            return false;
        }
        return true;
    }
}

