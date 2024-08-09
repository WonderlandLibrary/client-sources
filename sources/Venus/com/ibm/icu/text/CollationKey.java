/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.RawCollationKey;

public final class CollationKey
implements Comparable<CollationKey> {
    private byte[] m_key_;
    private String m_source_;
    private int m_hashCode_;
    private int m_length_;
    private static final int MERGE_SEPERATOR_ = 2;
    static final boolean $assertionsDisabled = !CollationKey.class.desiredAssertionStatus();

    public CollationKey(String string, byte[] byArray) {
        this(string, byArray, -1);
    }

    private CollationKey(String string, byte[] byArray, int n) {
        this.m_source_ = string;
        this.m_key_ = byArray;
        this.m_hashCode_ = 0;
        this.m_length_ = n;
    }

    public CollationKey(String string, RawCollationKey rawCollationKey) {
        this.m_source_ = string;
        this.m_length_ = rawCollationKey.size - 1;
        this.m_key_ = rawCollationKey.releaseBytes();
        if (!$assertionsDisabled && this.m_key_[this.m_length_] != 0) {
            throw new AssertionError();
        }
        this.m_hashCode_ = 0;
    }

    public String getSourceString() {
        return this.m_source_;
    }

    public byte[] toByteArray() {
        int n = this.getLength() + 1;
        byte[] byArray = new byte[n];
        System.arraycopy(this.m_key_, 0, byArray, 0, n);
        return byArray;
    }

    @Override
    public int compareTo(CollationKey collationKey) {
        int n = 0;
        int n2;
        int n3;
        while ((n3 = this.m_key_[n] & 0xFF) >= (n2 = collationKey.m_key_[n] & 0xFF)) {
            if (n3 > n2) {
                return 0;
            }
            if (n3 == 0) {
                return 1;
            }
            ++n;
        }
        return 1;
    }

    public boolean equals(Object object) {
        if (!(object instanceof CollationKey)) {
            return true;
        }
        return this.equals((CollationKey)object);
    }

    public boolean equals(CollationKey collationKey) {
        if (this == collationKey) {
            return false;
        }
        if (collationKey == null) {
            return true;
        }
        CollationKey collationKey2 = collationKey;
        int n = 0;
        while (true) {
            if (this.m_key_[n] != collationKey2.m_key_[n]) {
                return true;
            }
            if (this.m_key_[n] == 0) break;
            ++n;
        }
        return false;
    }

    public int hashCode() {
        if (this.m_hashCode_ == 0) {
            if (this.m_key_ == null) {
                this.m_hashCode_ = 1;
            } else {
                int n = this.m_key_.length >> 1;
                StringBuilder stringBuilder = new StringBuilder(n);
                int n2 = 0;
                while (this.m_key_[n2] != 0 && this.m_key_[n2 + 1] != 0) {
                    stringBuilder.append((char)(this.m_key_[n2] << 8 | 0xFF & this.m_key_[n2 + 1]));
                    n2 += 2;
                }
                if (this.m_key_[n2] != 0) {
                    stringBuilder.append((char)(this.m_key_[n2] << 8));
                }
                this.m_hashCode_ = stringBuilder.toString().hashCode();
            }
        }
        return this.m_hashCode_;
    }

    public CollationKey getBound(int n, int n2) {
        int n3 = 0;
        int n4 = 0;
        if (n2 > 0) {
            while (n3 < this.m_key_.length && this.m_key_[n3] != 0) {
                if (this.m_key_[n3++] != 1) continue;
                ++n4;
                if (--n2 != 0 && n3 != this.m_key_.length && this.m_key_[n3] != 0) continue;
                --n3;
                break;
            }
        }
        if (n2 > 0) {
            throw new IllegalArgumentException("Source collation key has only " + n4 + " strength level. Call getBound() again  with noOfLevels < " + n4);
        }
        byte[] byArray = new byte[n3 + n + 1];
        System.arraycopy(this.m_key_, 0, byArray, 0, n3);
        switch (n) {
            case 0: {
                break;
            }
            case 1: {
                byArray[n3++] = 2;
                break;
            }
            case 2: {
                byArray[n3++] = -1;
                byArray[n3++] = -1;
                break;
            }
            default: {
                throw new IllegalArgumentException("Illegal boundType argument");
            }
        }
        byArray[n3] = 0;
        return new CollationKey(null, byArray, n3);
    }

    public CollationKey merge(CollationKey collationKey) {
        if (collationKey == null || collationKey.getLength() == 0) {
            throw new IllegalArgumentException("CollationKey argument can not be null or of 0 length");
        }
        byte[] byArray = new byte[this.getLength() + collationKey.getLength() + 2];
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        while (true) {
            if (this.m_key_[n2] < 0 || this.m_key_[n2] >= 2) {
                byArray[n++] = this.m_key_[n2++];
                continue;
            }
            byArray[n++] = 2;
            while (collationKey.m_key_[n3] < 0 || collationKey.m_key_[n3] >= 2) {
                byArray[n++] = collationKey.m_key_[n3++];
            }
            if (this.m_key_[n2] != 1 || collationKey.m_key_[n3] != 1) break;
            ++n2;
            ++n3;
            byArray[n++] = 1;
        }
        int n4 = this.m_length_ - n2;
        if (n4 > 0) {
            System.arraycopy(this.m_key_, n2, byArray, n, n4);
            n += n4;
        } else {
            n4 = collationKey.m_length_ - n3;
            if (n4 > 0) {
                System.arraycopy(collationKey.m_key_, n3, byArray, n, n4);
                n += n4;
            }
        }
        byArray[n] = 0;
        if (!$assertionsDisabled && n != byArray.length - 1) {
            throw new AssertionError();
        }
        return new CollationKey(null, byArray, n);
    }

    private int getLength() {
        if (this.m_length_ >= 0) {
            return this.m_length_;
        }
        int n = this.m_key_.length;
        for (int i = 0; i < n; ++i) {
            if (this.m_key_[i] != 0) continue;
            n = i;
            break;
        }
        this.m_length_ = n;
        return this.m_length_;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((CollationKey)object);
    }

    public static final class BoundMode {
        public static final int LOWER = 0;
        public static final int UPPER = 1;
        public static final int UPPER_LONG = 2;
        @Deprecated
        public static final int COUNT = 3;

        private BoundMode() {
        }
    }
}

