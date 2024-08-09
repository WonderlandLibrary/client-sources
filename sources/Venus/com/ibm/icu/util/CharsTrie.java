/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.text.UTF16;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class CharsTrie
implements Cloneable,
Iterable<Entry> {
    private static BytesTrie.Result[] valueResults_;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMinLinearMatch = 48;
    static final int kMaxLinearMatchLength = 16;
    static final int kMinValueLead = 64;
    static final int kNodeTypeMask = 63;
    static final int kValueIsFinal = 32768;
    static final int kMaxOneUnitValue = 16383;
    static final int kMinTwoUnitValueLead = 16384;
    static final int kThreeUnitValueLead = Short.MAX_VALUE;
    static final int kMaxTwoUnitValue = 0x3FFEFFFF;
    static final int kMaxOneUnitNodeValue = 255;
    static final int kMinTwoUnitNodeValueLead = 16448;
    static final int kThreeUnitNodeValueLead = 32704;
    static final int kMaxTwoUnitNodeValue = 0xFDFFFF;
    static final int kMaxOneUnitDelta = 64511;
    static final int kMinTwoUnitDeltaLead = 64512;
    static final int kThreeUnitDeltaLead = 65535;
    static final int kMaxTwoUnitDelta = 0x3FEFFFF;
    private CharSequence chars_;
    private int root_;
    private int pos_;
    private int remainingMatchLength_;
    static final boolean $assertionsDisabled;

    public CharsTrie(CharSequence charSequence, int n) {
        this.chars_ = charSequence;
        this.pos_ = this.root_ = n;
        this.remainingMatchLength_ = -1;
    }

    public CharsTrie(CharsTrie charsTrie) {
        this.chars_ = charsTrie.chars_;
        this.root_ = charsTrie.root_;
        this.pos_ = charsTrie.pos_;
        this.remainingMatchLength_ = charsTrie.remainingMatchLength_;
    }

    public CharsTrie clone() throws CloneNotSupportedException {
        return (CharsTrie)super.clone();
    }

    public CharsTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }

    public long getState64() {
        return (long)this.remainingMatchLength_ << 32 | (long)this.pos_;
    }

    public CharsTrie resetToState64(long l) {
        this.remainingMatchLength_ = (int)(l >> 32);
        this.pos_ = (int)l;
        return this;
    }

    public CharsTrie saveState(State state) {
        State.access$002(state, this.chars_);
        State.access$102(state, this.root_);
        State.access$202(state, this.pos_);
        State.access$302(state, this.remainingMatchLength_);
        return this;
    }

    public CharsTrie resetToState(State state) {
        if (this.chars_ != State.access$000(state) || this.chars_ == null || this.root_ != State.access$100(state)) {
            throw new IllegalArgumentException("incompatible trie state");
        }
        this.pos_ = State.access$200(state);
        this.remainingMatchLength_ = State.access$300(state);
        return this;
    }

    public BytesTrie.Result current() {
        char c;
        int n = this.pos_;
        if (n < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        return this.remainingMatchLength_ < 0 && (c = this.chars_.charAt(n)) >= '@' ? valueResults_[c >> 15] : BytesTrie.Result.NO_VALUE;
    }

    public BytesTrie.Result first(int n) {
        this.remainingMatchLength_ = -1;
        return this.nextImpl(this.root_, n);
    }

    public BytesTrie.Result firstForCodePoint(int n) {
        return n <= 65535 ? this.first(n) : (this.first(UTF16.getLeadSurrogate(n)).hasNext() ? this.next(UTF16.getTrailSurrogate(n)) : BytesTrie.Result.NO_MATCH);
    }

    public BytesTrie.Result next(int n) {
        int n2 = this.pos_;
        if (n2 < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int n3 = this.remainingMatchLength_;
        if (n3 >= 0) {
            if (n == this.chars_.charAt(n2++)) {
                char c;
                this.remainingMatchLength_ = --n3;
                this.pos_ = n2;
                return n3 < 0 && (c = this.chars_.charAt(n2)) >= '@' ? valueResults_[c >> 15] : BytesTrie.Result.NO_VALUE;
            }
            this.stop();
            return BytesTrie.Result.NO_MATCH;
        }
        return this.nextImpl(n2, n);
    }

    public BytesTrie.Result nextForCodePoint(int n) {
        return n <= 65535 ? this.next(n) : (this.next(UTF16.getLeadSurrogate(n)).hasNext() ? this.next(UTF16.getTrailSurrogate(n)) : BytesTrie.Result.NO_MATCH);
    }

    public BytesTrie.Result next(CharSequence charSequence, int n, int n2) {
        if (n >= n2) {
            return this.current();
        }
        int n3 = this.pos_;
        if (n3 < 0) {
            return BytesTrie.Result.NO_MATCH;
        }
        int n4 = this.remainingMatchLength_;
        block0: while (true) {
            int n5;
            if (n == n2) {
                this.remainingMatchLength_ = n4;
                this.pos_ = n3;
                return n4 < 0 && (n5 = this.chars_.charAt(n3)) >= 64 ? valueResults_[n5 >> 15] : BytesTrie.Result.NO_VALUE;
            }
            char c = charSequence.charAt(n++);
            if (n4 >= 0) {
                if (c != this.chars_.charAt(n3)) {
                    this.stop();
                    return BytesTrie.Result.NO_MATCH;
                }
                ++n3;
                --n4;
                continue;
            }
            this.remainingMatchLength_ = n4;
            n5 = this.chars_.charAt(n3++);
            while (true) {
                if (n5 < 48) {
                    BytesTrie.Result result = this.branchNext(n3, n5, c);
                    if (result == BytesTrie.Result.NO_MATCH) {
                        return BytesTrie.Result.NO_MATCH;
                    }
                    if (n == n2) {
                        return result;
                    }
                    if (result == BytesTrie.Result.FINAL_VALUE) {
                        this.stop();
                        return BytesTrie.Result.NO_MATCH;
                    }
                    c = charSequence.charAt(n++);
                    n3 = this.pos_;
                    n5 = this.chars_.charAt(n3++);
                    continue;
                }
                if (n5 < 64) {
                    n4 = n5 - 48;
                    if (c != this.chars_.charAt(n3)) {
                        this.stop();
                        return BytesTrie.Result.NO_MATCH;
                    }
                    ++n3;
                    --n4;
                    continue block0;
                }
                if ((n5 & 0x8000) != 0) {
                    this.stop();
                    return BytesTrie.Result.NO_MATCH;
                }
                n3 = CharsTrie.skipNodeValue(n3, n5);
                n5 &= 0x3F;
            }
            break;
        }
    }

    public int getValue() {
        int n = this.pos_;
        char c = this.chars_.charAt(n++);
        if (!$assertionsDisabled && c < '@') {
            throw new AssertionError();
        }
        return (c & 0x8000) != 0 ? CharsTrie.readValue(this.chars_, n, c & Short.MAX_VALUE) : CharsTrie.readNodeValue(this.chars_, n, c);
    }

    public long getUniqueValue() {
        int n = this.pos_;
        if (n < 0) {
            return 0L;
        }
        long l = CharsTrie.findUniqueValue(this.chars_, n + this.remainingMatchLength_ + 1, 0L);
        return l << 31 >> 31;
    }

    public int getNextChars(Appendable appendable) {
        int n;
        int n2 = this.pos_;
        if (n2 < 0) {
            return 1;
        }
        if (this.remainingMatchLength_ >= 0) {
            CharsTrie.append(appendable, this.chars_.charAt(n2));
            return 0;
        }
        if ((n = this.chars_.charAt(n2++)) >= 64) {
            if ((n & 0x8000) != 0) {
                return 1;
            }
            n2 = CharsTrie.skipNodeValue(n2, n);
            n &= 0x3F;
        }
        if (n < 48) {
            if (n == 0) {
                n = this.chars_.charAt(n2++);
            }
            CharsTrie.getNextBranchChars(this.chars_, n2, ++n, appendable);
            return n;
        }
        CharsTrie.append(appendable, this.chars_.charAt(n2));
        return 0;
    }

    public Iterator iterator() {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, 0, null);
    }

    public Iterator iterator(int n) {
        return new Iterator(this.chars_, this.pos_, this.remainingMatchLength_, n, null);
    }

    public static Iterator iterator(CharSequence charSequence, int n, int n2) {
        return new Iterator(charSequence, n, -1, n2, null);
    }

    private void stop() {
        this.pos_ = -1;
    }

    private static int readValue(CharSequence charSequence, int n, int n2) {
        int n3 = n2 < 16384 ? n2 : (n2 < Short.MAX_VALUE ? n2 - 16384 << 16 | charSequence.charAt(n) : charSequence.charAt(n) << 16 | charSequence.charAt(n + 1));
        return n3;
    }

    private static int skipValue(int n, int n2) {
        if (n2 >= 16384) {
            n = n2 < Short.MAX_VALUE ? ++n : (n += 2);
        }
        return n;
    }

    private static int skipValue(CharSequence charSequence, int n) {
        char c = charSequence.charAt(n++);
        return CharsTrie.skipValue(n, c & Short.MAX_VALUE);
    }

    private static int readNodeValue(CharSequence charSequence, int n, int n2) {
        if (!($assertionsDisabled || 64 <= n2 && n2 < 32768)) {
            throw new AssertionError();
        }
        int n3 = n2 < 16448 ? (n2 >> 6) - 1 : (n2 < 32704 ? (n2 & 0x7FC0) - 16448 << 10 | charSequence.charAt(n) : charSequence.charAt(n) << 16 | charSequence.charAt(n + 1));
        return n3;
    }

    private static int skipNodeValue(int n, int n2) {
        if (!($assertionsDisabled || 64 <= n2 && n2 < 32768)) {
            throw new AssertionError();
        }
        if (n2 >= 16448) {
            n = n2 < 32704 ? ++n : (n += 2);
        }
        return n;
    }

    private static int jumpByDelta(CharSequence charSequence, int n) {
        int n2;
        if ((n2 = charSequence.charAt(n++)) >= 64512) {
            if (n2 == 65535) {
                n2 = charSequence.charAt(n) << 16 | charSequence.charAt(n + 1);
                n += 2;
            } else {
                n2 = n2 - 64512 << 16 | charSequence.charAt(n++);
            }
        }
        return n + n2;
    }

    private static int skipDelta(CharSequence charSequence, int n) {
        char c;
        if ((c = charSequence.charAt(n++)) >= '\ufc00') {
            n = c == '\uffff' ? (n += 2) : ++n;
        }
        return n;
    }

    private BytesTrie.Result branchNext(int n, int n2, int n3) {
        if (n2 == 0) {
            n2 = this.chars_.charAt(n++);
        }
        ++n2;
        while (n2 > 5) {
            if (n3 < this.chars_.charAt(n++)) {
                n2 >>= 1;
                n = CharsTrie.jumpByDelta(this.chars_, n);
                continue;
            }
            n2 -= n2 >> 1;
            n = CharsTrie.skipDelta(this.chars_, n);
        }
        do {
            if (n3 == this.chars_.charAt(n++)) {
                BytesTrie.Result result;
                int n4 = this.chars_.charAt(n);
                if ((n4 & 0x8000) != 0) {
                    result = BytesTrie.Result.FINAL_VALUE;
                } else {
                    int n5;
                    ++n;
                    if (n4 < 16384) {
                        n5 = n4;
                    } else if (n4 < Short.MAX_VALUE) {
                        n5 = n4 - 16384 << 16 | this.chars_.charAt(n++);
                    } else {
                        n5 = this.chars_.charAt(n) << 16 | this.chars_.charAt(n + 1);
                        n += 2;
                    }
                    n4 = this.chars_.charAt(n += n5);
                    result = n4 >= 64 ? valueResults_[n4 >> 15] : BytesTrie.Result.NO_VALUE;
                }
                this.pos_ = n;
                return result;
            }
            n = CharsTrie.skipValue(this.chars_, n);
        } while (--n2 > 1);
        if (n3 == this.chars_.charAt(n++)) {
            this.pos_ = n;
            char c = this.chars_.charAt(n);
            return c >= '@' ? valueResults_[c >> 15] : BytesTrie.Result.NO_VALUE;
        }
        this.stop();
        return BytesTrie.Result.NO_MATCH;
    }

    /*
     * Enabled aggressive block sorting
     */
    private BytesTrie.Result nextImpl(int n, int n2) {
        int n3 = this.chars_.charAt(n++);
        while (true) {
            if (n3 < 48) {
                return this.branchNext(n, n3, n2);
            }
            if (n3 < 64) {
                BytesTrie.Result result;
                int n4 = n3 - 48;
                if (n2 != this.chars_.charAt(n++)) break;
                this.remainingMatchLength_ = --n4;
                this.pos_ = n;
                if (n4 < 0) {
                    char c = this.chars_.charAt(n);
                    n3 = c;
                    if (c >= '@') {
                        result = valueResults_[n3 >> 15];
                        return result;
                    }
                }
                result = BytesTrie.Result.NO_VALUE;
                return result;
            }
            if ((n3 & 0x8000) != 0) break;
            n = CharsTrie.skipNodeValue(n, n3);
            n3 &= 0x3F;
        }
        this.stop();
        return BytesTrie.Result.NO_MATCH;
    }

    private static long findUniqueValueFromBranch(CharSequence charSequence, int n, int n2, long l) {
        while (n2 > 5) {
            if ((l = CharsTrie.findUniqueValueFromBranch(charSequence, CharsTrie.jumpByDelta(charSequence, ++n), n2 >> 1, l)) == 0L) {
                return 0L;
            }
            n2 -= n2 >> 1;
            n = CharsTrie.skipDelta(charSequence, n);
        }
        do {
            int n3 = ++n;
            int n4 = charSequence.charAt(n3);
            boolean bl = (n4 & 0x8000) != 0;
            int n5 = CharsTrie.readValue(charSequence, ++n, n4 &= Short.MAX_VALUE);
            n = CharsTrie.skipValue(n, n4);
            if (bl) {
                if (l != 0L) {
                    if (n5 == (int)(l >> 1)) continue;
                    return 0L;
                }
                l = (long)n5 << 1 | 1L;
                continue;
            }
            if ((l = CharsTrie.findUniqueValue(charSequence, n + n5, l)) != 0L) continue;
            return 0L;
        } while (--n2 > 1);
        return (long)(n + 1) << 33 | l & 0x1FFFFFFFFL;
    }

    private static long findUniqueValue(CharSequence charSequence, int n, long l) {
        int n2 = charSequence.charAt(n++);
        while (true) {
            if (n2 < 48) {
                if (n2 == 0) {
                    n2 = charSequence.charAt(n++);
                }
                if ((l = CharsTrie.findUniqueValueFromBranch(charSequence, n, n2 + '\u0001', l)) == 0L) {
                    return 0L;
                }
                n = (int)(l >>> 33);
                n2 = charSequence.charAt(n++);
                continue;
            }
            if (n2 < 64) {
                n += n2 - 48 + 1;
                n2 = charSequence.charAt(n++);
                continue;
            }
            boolean bl = (n2 & 0x8000) != 0;
            int n3 = bl ? CharsTrie.readValue(charSequence, n, n2 & Short.MAX_VALUE) : CharsTrie.readNodeValue(charSequence, n, n2);
            if (l != 0L) {
                if (n3 != (int)(l >> 1)) {
                    return 0L;
                }
            } else {
                l = (long)n3 << 1 | 1L;
            }
            if (bl) {
                return l;
            }
            n = CharsTrie.skipNodeValue(n, n2);
            n2 &= 0x3F;
        }
    }

    private static void getNextBranchChars(CharSequence charSequence, int n, int n2, Appendable appendable) {
        while (n2 > 5) {
            CharsTrie.getNextBranchChars(charSequence, CharsTrie.jumpByDelta(charSequence, ++n), n2 >> 1, appendable);
            n2 -= n2 >> 1;
            n = CharsTrie.skipDelta(charSequence, n);
        }
        do {
            CharsTrie.append(appendable, charSequence.charAt(n++));
            n = CharsTrie.skipValue(charSequence, n);
        } while (--n2 > 1);
        CharsTrie.append(appendable, charSequence.charAt(n));
    }

    private static void append(Appendable appendable, int n) {
        try {
            appendable.append((char)n);
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    @Override
    public java.util.Iterator iterator() {
        return this.iterator();
    }

    static int access$500(int n, int n2) {
        return CharsTrie.skipNodeValue(n, n2);
    }

    static int access$600(CharSequence charSequence, int n, int n2) {
        return CharsTrie.readValue(charSequence, n, n2);
    }

    static int access$700(CharSequence charSequence, int n, int n2) {
        return CharsTrie.readNodeValue(charSequence, n, n2);
    }

    static int access$800(CharSequence charSequence, int n) {
        return CharsTrie.skipDelta(charSequence, n);
    }

    static int access$900(CharSequence charSequence, int n) {
        return CharsTrie.jumpByDelta(charSequence, n);
    }

    static int access$1000(int n, int n2) {
        return CharsTrie.skipValue(n, n2);
    }

    static {
        $assertionsDisabled = !CharsTrie.class.desiredAssertionStatus();
        valueResults_ = new BytesTrie.Result[]{BytesTrie.Result.INTERMEDIATE_VALUE, BytesTrie.Result.FINAL_VALUE};
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Iterator
    implements java.util.Iterator<Entry> {
        private CharSequence chars_;
        private int pos_;
        private int initialPos_;
        private int remainingMatchLength_;
        private int initialRemainingMatchLength_;
        private boolean skipValue_;
        private StringBuilder str_ = new StringBuilder();
        private int maxLength_;
        private Entry entry_ = new Entry(null);
        private ArrayList<Long> stack_ = new ArrayList();

        private Iterator(CharSequence charSequence, int n, int n2, int n3) {
            this.chars_ = charSequence;
            this.pos_ = this.initialPos_ = n;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_ = n2;
            this.maxLength_ = n3;
            int n4 = this.remainingMatchLength_;
            if (n4 >= 0) {
                if (this.maxLength_ > 0 && ++n4 > this.maxLength_) {
                    n4 = this.maxLength_;
                }
                this.str_.append(this.chars_, this.pos_, this.pos_ + n4);
                this.pos_ += n4;
                this.remainingMatchLength_ -= n4;
            }
        }

        public Iterator reset() {
            this.pos_ = this.initialPos_;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_;
            this.skipValue_ = false;
            int n = this.remainingMatchLength_ + 1;
            if (this.maxLength_ > 0 && n > this.maxLength_) {
                n = this.maxLength_;
            }
            this.str_.setLength(n);
            this.pos_ += n;
            this.remainingMatchLength_ -= n;
            this.stack_.clear();
            return this;
        }

        @Override
        public boolean hasNext() {
            return this.pos_ >= 0 || !this.stack_.isEmpty();
        }

        @Override
        public Entry next() {
            int n = this.pos_;
            if (n < 0) {
                if (this.stack_.isEmpty()) {
                    throw new NoSuchElementException();
                }
                long l = this.stack_.remove(this.stack_.size() - 1);
                int n2 = (int)l;
                n = (int)(l >> 32);
                this.str_.setLength(n2 & 0xFFFF);
                if ((n2 >>>= 16) > 1) {
                    if ((n = this.branchNext(n, n2)) < 0) {
                        return this.entry_;
                    }
                } else {
                    this.str_.append(this.chars_.charAt(n++));
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return this.truncateAndStop();
            }
            while (true) {
                int n3;
                int n4;
                if ((n4 = this.chars_.charAt(n++)) >= 64) {
                    if (this.skipValue_) {
                        n = CharsTrie.access$500(n, n4);
                        n4 &= 0x3F;
                        this.skipValue_ = false;
                    } else {
                        n3 = (n4 & 0x8000) != 0 ? 1 : 0;
                        this.entry_.value = n3 != 0 ? CharsTrie.access$600(this.chars_, n, n4 & Short.MAX_VALUE) : CharsTrie.access$700(this.chars_, n, n4);
                        if (n3 != 0 || this.maxLength_ > 0 && this.str_.length() == this.maxLength_) {
                            this.pos_ = -1;
                        } else {
                            this.pos_ = n - 1;
                            this.skipValue_ = true;
                        }
                        this.entry_.chars = this.str_;
                        return this.entry_;
                    }
                }
                if (this.maxLength_ > 0 && this.str_.length() == this.maxLength_) {
                    return this.truncateAndStop();
                }
                if (n4 < 48) {
                    if (n4 == 0) {
                        n4 = this.chars_.charAt(n++);
                    }
                    if ((n = this.branchNext(n, n4 + 1)) >= 0) continue;
                    return this.entry_;
                }
                n3 = n4 - 48 + 1;
                if (this.maxLength_ > 0 && this.str_.length() + n3 > this.maxLength_) {
                    this.str_.append(this.chars_, n, n + this.maxLength_ - this.str_.length());
                    return this.truncateAndStop();
                }
                this.str_.append(this.chars_, n, n + n3);
                n += n3;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Entry truncateAndStop() {
            this.pos_ = -1;
            this.entry_.chars = this.str_;
            this.entry_.value = -1;
            return this.entry_;
        }

        private int branchNext(int n, int n2) {
            int n3;
            while (n2 > 5) {
                this.stack_.add((long)CharsTrie.access$800(this.chars_, ++n) << 32 | (long)(n2 - (n2 >> 1) << 16) | (long)this.str_.length());
                n2 >>= 1;
                n = CharsTrie.access$900(this.chars_, n);
            }
            char c = this.chars_.charAt(n++);
            boolean bl = ((n3 = this.chars_.charAt(n++)) & 0x8000) != 0;
            int n4 = CharsTrie.access$600(this.chars_, n, n3 &= Short.MAX_VALUE);
            n = CharsTrie.access$1000(n, n3);
            this.stack_.add((long)n << 32 | (long)(n2 - 1 << 16) | (long)this.str_.length());
            this.str_.append(c);
            if (bl) {
                this.pos_ = -1;
                this.entry_.chars = this.str_;
                this.entry_.value = n4;
                return 1;
            }
            return n + n4;
        }

        @Override
        public Object next() {
            return this.next();
        }

        Iterator(CharSequence charSequence, int n, int n2, int n3, 1 var5_5) {
            this(charSequence, n, n2, n3);
        }
    }

    public static final class Entry {
        public CharSequence chars;
        public int value;

        private Entry() {
        }

        Entry(1 var1_1) {
            this();
        }
    }

    public static final class State {
        private CharSequence chars;
        private int root;
        private int pos;
        private int remainingMatchLength;

        static CharSequence access$002(State state, CharSequence charSequence) {
            state.chars = charSequence;
            return state.chars;
        }

        static int access$102(State state, int n) {
            state.root = n;
            return state.root;
        }

        static int access$202(State state, int n) {
            state.pos = n;
            return state.pos;
        }

        static int access$302(State state, int n) {
            state.remainingMatchLength = n;
            return state.remainingMatchLength;
        }

        static CharSequence access$000(State state) {
            return state.chars;
        }

        static int access$100(State state) {
            return state.root;
        }

        static int access$200(State state) {
            return state.pos;
        }

        static int access$300(State state) {
            return state.remainingMatchLength;
        }
    }
}

