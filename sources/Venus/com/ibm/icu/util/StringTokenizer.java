/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public final class StringTokenizer
implements Enumeration<Object> {
    private int m_tokenOffset_;
    private int m_tokenSize_;
    private int[] m_tokenStart_;
    private int[] m_tokenLimit_;
    private UnicodeSet m_delimiters_;
    private String m_source_;
    private int m_length_;
    private int m_nextOffset_;
    private boolean m_returnDelimiters_;
    private boolean m_coalesceDelimiters_;
    private static final UnicodeSet DEFAULT_DELIMITERS_ = new UnicodeSet(9, 10, 12, 13, 32, 32);
    private static final int TOKEN_SIZE_ = 100;
    private static final UnicodeSet EMPTY_DELIMITER_ = UnicodeSet.EMPTY;
    private boolean[] delims;

    public StringTokenizer(String string, UnicodeSet unicodeSet, boolean bl) {
        this(string, unicodeSet, bl, false);
    }

    @Deprecated
    public StringTokenizer(String string, UnicodeSet unicodeSet, boolean bl, boolean bl2) {
        this.m_source_ = string;
        this.m_length_ = string.length();
        this.m_delimiters_ = unicodeSet == null ? EMPTY_DELIMITER_ : unicodeSet;
        this.m_returnDelimiters_ = bl;
        this.m_coalesceDelimiters_ = bl2;
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (this.m_length_ == 0) {
            this.m_nextOffset_ = -1;
        } else {
            this.m_nextOffset_ = 0;
            if (!bl) {
                this.m_nextOffset_ = this.getNextNonDelimiter(0);
            }
        }
    }

    public StringTokenizer(String string, UnicodeSet unicodeSet) {
        this(string, unicodeSet, false, false);
    }

    public StringTokenizer(String string, String string2, boolean bl) {
        this(string, string2, bl, false);
    }

    @Deprecated
    public StringTokenizer(String string, String string2, boolean bl, boolean bl2) {
        this.m_delimiters_ = EMPTY_DELIMITER_;
        if (string2 != null && string2.length() > 0) {
            this.m_delimiters_ = new UnicodeSet();
            this.m_delimiters_.addAll(string2);
            this.checkDelimiters();
        }
        this.m_coalesceDelimiters_ = bl2;
        this.m_source_ = string;
        this.m_length_ = string.length();
        this.m_returnDelimiters_ = bl;
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (this.m_length_ == 0) {
            this.m_nextOffset_ = -1;
        } else {
            this.m_nextOffset_ = 0;
            if (!bl) {
                this.m_nextOffset_ = this.getNextNonDelimiter(0);
            }
        }
    }

    public StringTokenizer(String string, String string2) {
        this(string, string2, false, false);
    }

    public StringTokenizer(String string) {
        this(string, DEFAULT_DELIMITERS_, false, false);
    }

    public boolean hasMoreTokens() {
        return this.m_nextOffset_ >= 0;
    }

    public String nextToken() {
        if (this.m_tokenOffset_ < 0) {
            String string;
            if (this.m_nextOffset_ < 0) {
                throw new NoSuchElementException("No more tokens in String");
            }
            if (this.m_returnDelimiters_) {
                boolean bl;
                int n = 0;
                int n2 = UTF16.charAt(this.m_source_, this.m_nextOffset_);
                boolean bl2 = this.delims == null ? this.m_delimiters_.contains(n2) : (bl = n2 < this.delims.length && this.delims[n2]);
                if (bl) {
                    if (this.m_coalesceDelimiters_) {
                        n = this.getNextNonDelimiter(this.m_nextOffset_);
                    } else {
                        n = this.m_nextOffset_ + UTF16.getCharCount(n2);
                        if (n == this.m_length_) {
                            n = -1;
                        }
                    }
                } else {
                    n = this.getNextDelimiter(this.m_nextOffset_);
                }
                String string2 = n < 0 ? this.m_source_.substring(this.m_nextOffset_) : this.m_source_.substring(this.m_nextOffset_, n);
                this.m_nextOffset_ = n;
                return string2;
            }
            int n = this.getNextDelimiter(this.m_nextOffset_);
            if (n < 0) {
                string = this.m_source_.substring(this.m_nextOffset_);
                this.m_nextOffset_ = n;
            } else {
                string = this.m_source_.substring(this.m_nextOffset_, n);
                this.m_nextOffset_ = this.getNextNonDelimiter(n);
            }
            return string;
        }
        if (this.m_tokenOffset_ >= this.m_tokenSize_) {
            throw new NoSuchElementException("No more tokens in String");
        }
        String string = this.m_tokenLimit_[this.m_tokenOffset_] >= 0 ? this.m_source_.substring(this.m_tokenStart_[this.m_tokenOffset_], this.m_tokenLimit_[this.m_tokenOffset_]) : this.m_source_.substring(this.m_tokenStart_[this.m_tokenOffset_]);
        ++this.m_tokenOffset_;
        this.m_nextOffset_ = -1;
        if (this.m_tokenOffset_ < this.m_tokenSize_) {
            this.m_nextOffset_ = this.m_tokenStart_[this.m_tokenOffset_];
        }
        return string;
    }

    public String nextToken(String string) {
        this.m_delimiters_ = EMPTY_DELIMITER_;
        if (string != null && string.length() > 0) {
            this.m_delimiters_ = new UnicodeSet();
            this.m_delimiters_.addAll(string);
        }
        return this.nextToken(this.m_delimiters_);
    }

    public String nextToken(UnicodeSet unicodeSet) {
        this.m_delimiters_ = unicodeSet;
        this.checkDelimiters();
        this.m_tokenOffset_ = -1;
        this.m_tokenSize_ = -1;
        if (!this.m_returnDelimiters_) {
            this.m_nextOffset_ = this.getNextNonDelimiter(this.m_nextOffset_);
        }
        return this.nextToken();
    }

    @Override
    public boolean hasMoreElements() {
        return this.hasMoreTokens();
    }

    @Override
    public Object nextElement() {
        return this.nextToken();
    }

    public int countTokens() {
        int n = 0;
        if (this.hasMoreTokens()) {
            if (this.m_tokenOffset_ >= 0) {
                return this.m_tokenSize_ - this.m_tokenOffset_;
            }
            if (this.m_tokenStart_ == null) {
                this.m_tokenStart_ = new int[100];
                this.m_tokenLimit_ = new int[100];
            }
            do {
                int n2;
                if (this.m_tokenStart_.length == n) {
                    int[] nArray = this.m_tokenStart_;
                    int[] nArray2 = this.m_tokenLimit_;
                    n2 = nArray.length;
                    int n3 = n2 + 100;
                    this.m_tokenStart_ = new int[n3];
                    this.m_tokenLimit_ = new int[n3];
                    System.arraycopy(nArray, 0, this.m_tokenStart_, 0, n2);
                    System.arraycopy(nArray2, 0, this.m_tokenLimit_, 0, n2);
                }
                this.m_tokenStart_[n] = this.m_nextOffset_;
                if (this.m_returnDelimiters_) {
                    boolean bl;
                    int n4 = UTF16.charAt(this.m_source_, this.m_nextOffset_);
                    boolean bl2 = this.delims == null ? this.m_delimiters_.contains(n4) : (bl = n4 < this.delims.length && this.delims[n4]);
                    if (bl) {
                        if (this.m_coalesceDelimiters_) {
                            this.m_tokenLimit_[n] = this.getNextNonDelimiter(this.m_nextOffset_);
                        } else {
                            n2 = this.m_nextOffset_ + 1;
                            if (n2 == this.m_length_) {
                                n2 = -1;
                            }
                            this.m_tokenLimit_[n] = n2;
                        }
                    } else {
                        this.m_tokenLimit_[n] = this.getNextDelimiter(this.m_nextOffset_);
                    }
                    this.m_nextOffset_ = this.m_tokenLimit_[n];
                } else {
                    this.m_tokenLimit_[n] = this.getNextDelimiter(this.m_nextOffset_);
                    this.m_nextOffset_ = this.getNextNonDelimiter(this.m_tokenLimit_[n]);
                }
                ++n;
            } while (this.m_nextOffset_ >= 0);
            this.m_tokenOffset_ = 0;
            this.m_tokenSize_ = n;
            this.m_nextOffset_ = this.m_tokenStart_[0];
        }
        return n;
    }

    private int getNextDelimiter(int n) {
        if (n >= 0) {
            int n2 = n;
            int n3 = 0;
            if (this.delims == null) {
                while (!this.m_delimiters_.contains(n3 = UTF16.charAt(this.m_source_, n2)) && ++n2 < this.m_length_) {
                }
            } else {
                while (!((n3 = UTF16.charAt(this.m_source_, n2)) < this.delims.length && this.delims[n3] || ++n2 >= this.m_length_)) {
                }
            }
            if (n2 < this.m_length_) {
                return n2;
            }
        }
        return -1 - this.m_length_;
    }

    private int getNextNonDelimiter(int n) {
        if (n >= 0) {
            int n2 = n;
            int n3 = 0;
            if (this.delims == null) {
                while (this.m_delimiters_.contains(n3 = UTF16.charAt(this.m_source_, n2)) && ++n2 < this.m_length_) {
                }
            } else {
                while ((n3 = UTF16.charAt(this.m_source_, n2)) < this.delims.length && this.delims[n3] && ++n2 < this.m_length_) {
                }
            }
            if (n2 < this.m_length_) {
                return n2;
            }
        }
        return -1 - this.m_length_;
    }

    void checkDelimiters() {
        if (this.m_delimiters_ == null || this.m_delimiters_.size() == 0) {
            this.delims = new boolean[0];
        } else {
            int n = this.m_delimiters_.getRangeEnd(this.m_delimiters_.getRangeCount() - 1);
            if (n < 127) {
                int n2;
                this.delims = new boolean[n + 1];
                int n3 = 0;
                while (-1 != (n2 = this.m_delimiters_.charAt(n3))) {
                    this.delims[n2] = true;
                    ++n3;
                }
            } else {
                this.delims = null;
            }
        }
    }
}

