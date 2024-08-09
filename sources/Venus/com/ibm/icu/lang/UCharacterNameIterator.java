/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.lang;

import com.ibm.icu.impl.UCharacterName;
import com.ibm.icu.util.ValueIterator;

class UCharacterNameIterator
implements ValueIterator {
    private UCharacterName m_name_;
    private int m_choice_;
    private int m_start_;
    private int m_limit_;
    private int m_current_;
    private int m_groupIndex_ = -1;
    private int m_algorithmIndex_ = -1;
    private static char[] GROUP_OFFSETS_ = new char[33];
    private static char[] GROUP_LENGTHS_ = new char[33];

    @Override
    public boolean next(ValueIterator.Element element) {
        int n;
        if (this.m_current_ >= this.m_limit_) {
            return true;
        }
        if ((this.m_choice_ == 0 || this.m_choice_ == 2) && this.m_algorithmIndex_ < (n = this.m_name_.getAlgorithmLength())) {
            while (this.m_algorithmIndex_ < n && (this.m_algorithmIndex_ < 0 || this.m_name_.getAlgorithmEnd(this.m_algorithmIndex_) < this.m_current_)) {
                ++this.m_algorithmIndex_;
            }
            if (this.m_algorithmIndex_ < n) {
                int n2 = this.m_name_.getAlgorithmStart(this.m_algorithmIndex_);
                if (this.m_current_ < n2) {
                    int n3 = n2;
                    if (this.m_limit_ <= n2) {
                        n3 = this.m_limit_;
                    }
                    if (!this.iterateGroup(element, n3)) {
                        ++this.m_current_;
                        return false;
                    }
                }
                if (this.m_current_ >= this.m_limit_) {
                    return true;
                }
                element.integer = this.m_current_;
                element.value = this.m_name_.getAlgorithmName(this.m_algorithmIndex_, this.m_current_);
                this.m_groupIndex_ = -1;
                ++this.m_current_;
                return false;
            }
        }
        if (!this.iterateGroup(element, this.m_limit_)) {
            ++this.m_current_;
            return false;
        }
        if (this.m_choice_ == 2 && !this.iterateExtended(element, this.m_limit_)) {
            ++this.m_current_;
            return false;
        }
        return true;
    }

    @Override
    public void reset() {
        this.m_current_ = this.m_start_;
        this.m_groupIndex_ = -1;
        this.m_algorithmIndex_ = -1;
    }

    @Override
    public void setRange(int n, int n2) {
        if (n >= n2) {
            throw new IllegalArgumentException("start or limit has to be valid Unicode codepoints and start < limit");
        }
        this.m_start_ = n < 0 ? 0 : n;
        this.m_limit_ = n2 > 0x110000 ? 0x110000 : n2;
        this.m_current_ = this.m_start_;
    }

    protected UCharacterNameIterator(UCharacterName uCharacterName, int n) {
        if (uCharacterName == null) {
            throw new IllegalArgumentException("UCharacterName name argument cannot be null. Missing unames.icu?");
        }
        this.m_name_ = uCharacterName;
        this.m_choice_ = n;
        this.m_start_ = 0;
        this.m_limit_ = 0x110000;
        this.m_current_ = this.m_start_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean iterateSingleGroup(ValueIterator.Element element, int n) {
        char[] cArray = GROUP_OFFSETS_;
        synchronized (GROUP_OFFSETS_) {
            char[] cArray2 = GROUP_LENGTHS_;
            synchronized (GROUP_LENGTHS_) {
                int n2 = this.m_name_.getGroupLengths(this.m_groupIndex_, GROUP_OFFSETS_, GROUP_LENGTHS_);
                while (this.m_current_ < n) {
                    int n3 = UCharacterName.getGroupOffset(this.m_current_);
                    String string = this.m_name_.getGroupName(n2 + GROUP_OFFSETS_[n3], GROUP_LENGTHS_[n3], this.m_choice_);
                    if ((string == null || string.length() == 0) && this.m_choice_ == 2) {
                        string = this.m_name_.getExtendedName(this.m_current_);
                    }
                    if (string != null && string.length() > 0) {
                        element.integer = this.m_current_;
                        element.value = string;
                        // ** MonitorExit[var4_4] (shouldn't be in output)
                        // ** MonitorExit[var3_3] (shouldn't be in output)
                        return false;
                    }
                    ++this.m_current_;
                }
                // ** MonitorExit[var4_4] (shouldn't be in output)
            }
            return false;
        }
    }

    private boolean iterateGroup(ValueIterator.Element element, int n) {
        if (this.m_groupIndex_ < 0) {
            this.m_groupIndex_ = this.m_name_.getGroup(this.m_current_);
        }
        while (this.m_groupIndex_ < this.m_name_.m_groupcount_ && this.m_current_ < n) {
            int n2;
            int n3 = UCharacterName.getCodepointMSB(this.m_current_);
            if (n3 == (n2 = this.m_name_.getGroupMSB(this.m_groupIndex_))) {
                if (n3 == UCharacterName.getCodepointMSB(n - 1)) {
                    return this.iterateSingleGroup(element, n);
                }
                if (!this.iterateSingleGroup(element, UCharacterName.getGroupLimit(n2))) {
                    return true;
                }
                ++this.m_groupIndex_;
                continue;
            }
            if (n3 > n2) {
                ++this.m_groupIndex_;
                continue;
            }
            int n4 = UCharacterName.getGroupMin(n2);
            if (n4 > n) {
                n4 = n;
            }
            if (this.m_choice_ == 2 && !this.iterateExtended(element, n4)) {
                return true;
            }
            this.m_current_ = n4;
        }
        return false;
    }

    private boolean iterateExtended(ValueIterator.Element element, int n) {
        while (this.m_current_ < n) {
            String string = this.m_name_.getExtendedOr10Name(this.m_current_);
            if (string != null && string.length() > 0) {
                element.integer = this.m_current_;
                element.value = string;
                return true;
            }
            ++this.m_current_;
        }
        return false;
    }
}

