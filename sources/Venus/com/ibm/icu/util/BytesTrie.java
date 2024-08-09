/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class BytesTrie
implements Cloneable,
Iterable<Entry> {
    private static Result[] valueResults_;
    static final int kMaxBranchLinearSubNodeLength = 5;
    static final int kMinLinearMatch = 16;
    static final int kMaxLinearMatchLength = 16;
    static final int kMinValueLead = 32;
    private static final int kValueIsFinal = 1;
    static final int kMinOneByteValueLead = 16;
    static final int kMaxOneByteValue = 64;
    static final int kMinTwoByteValueLead = 81;
    static final int kMaxTwoByteValue = 6911;
    static final int kMinThreeByteValueLead = 108;
    static final int kFourByteValueLead = 126;
    static final int kMaxThreeByteValue = 0x11FFFF;
    static final int kFiveByteValueLead = 127;
    static final int kMaxOneByteDelta = 191;
    static final int kMinTwoByteDeltaLead = 192;
    static final int kMinThreeByteDeltaLead = 240;
    static final int kFourByteDeltaLead = 254;
    static final int kFiveByteDeltaLead = 255;
    static final int kMaxTwoByteDelta = 12287;
    static final int kMaxThreeByteDelta = 917503;
    private byte[] bytes_;
    private int root_;
    private int pos_;
    private int remainingMatchLength_;
    static final boolean $assertionsDisabled;

    public BytesTrie(byte[] byArray, int n) {
        this.bytes_ = byArray;
        this.pos_ = this.root_ = n;
        this.remainingMatchLength_ = -1;
    }

    public BytesTrie(BytesTrie bytesTrie) {
        this.bytes_ = bytesTrie.bytes_;
        this.root_ = bytesTrie.root_;
        this.pos_ = bytesTrie.pos_;
        this.remainingMatchLength_ = bytesTrie.remainingMatchLength_;
    }

    public BytesTrie clone() throws CloneNotSupportedException {
        return (BytesTrie)super.clone();
    }

    public BytesTrie reset() {
        this.pos_ = this.root_;
        this.remainingMatchLength_ = -1;
        return this;
    }

    public long getState64() {
        return (long)this.remainingMatchLength_ << 32 | (long)this.pos_;
    }

    public BytesTrie resetToState64(long l) {
        this.remainingMatchLength_ = (int)(l >> 32);
        this.pos_ = (int)l;
        return this;
    }

    public BytesTrie saveState(State state) {
        State.access$002(state, this.bytes_);
        State.access$102(state, this.root_);
        State.access$202(state, this.pos_);
        State.access$302(state, this.remainingMatchLength_);
        return this;
    }

    public BytesTrie resetToState(State state) {
        if (this.bytes_ != State.access$000(state) || this.bytes_ == null || this.root_ != State.access$100(state)) {
            throw new IllegalArgumentException("incompatible trie state");
        }
        this.pos_ = State.access$200(state);
        this.remainingMatchLength_ = State.access$300(state);
        return this;
    }

    public Result current() {
        int n;
        int n2 = this.pos_;
        if (n2 < 0) {
            return Result.NO_MATCH;
        }
        return this.remainingMatchLength_ < 0 && (n = this.bytes_[n2] & 0xFF) >= 32 ? valueResults_[n & 1] : Result.NO_VALUE;
    }

    public Result first(int n) {
        this.remainingMatchLength_ = -1;
        if (n < 0) {
            n += 256;
        }
        return this.nextImpl(this.root_, n);
    }

    public Result next(int n) {
        int n2;
        int n3 = this.pos_;
        if (n3 < 0) {
            return Result.NO_MATCH;
        }
        if (n < 0) {
            n += 256;
        }
        if ((n2 = this.remainingMatchLength_) >= 0) {
            if (n == (this.bytes_[n3++] & 0xFF)) {
                int n4;
                this.remainingMatchLength_ = --n2;
                this.pos_ = n3;
                return n2 < 0 && (n4 = this.bytes_[n3] & 0xFF) >= 32 ? valueResults_[n4 & 1] : Result.NO_VALUE;
            }
            this.stop();
            return Result.NO_MATCH;
        }
        return this.nextImpl(n3, n);
    }

    public Result next(byte[] byArray, int n, int n2) {
        if (n >= n2) {
            return this.current();
        }
        int n3 = this.pos_;
        if (n3 < 0) {
            return Result.NO_MATCH;
        }
        int n4 = this.remainingMatchLength_;
        block0: while (true) {
            int n5;
            if (n == n2) {
                this.remainingMatchLength_ = n4;
                this.pos_ = n3;
                return n4 < 0 && (n5 = this.bytes_[n3] & 0xFF) >= 32 ? valueResults_[n5 & 1] : Result.NO_VALUE;
            }
            byte by = byArray[n++];
            if (n4 >= 0) {
                if (by != this.bytes_[n3]) {
                    this.stop();
                    return Result.NO_MATCH;
                }
                ++n3;
                --n4;
                continue;
            }
            this.remainingMatchLength_ = n4;
            while (true) {
                if ((n5 = this.bytes_[n3++] & 0xFF) < 16) {
                    Result result = this.branchNext(n3, n5, by & 0xFF);
                    if (result == Result.NO_MATCH) {
                        return Result.NO_MATCH;
                    }
                    if (n == n2) {
                        return result;
                    }
                    if (result == Result.FINAL_VALUE) {
                        this.stop();
                        return Result.NO_MATCH;
                    }
                    by = byArray[n++];
                    n3 = this.pos_;
                    continue;
                }
                if (n5 < 32) {
                    n4 = n5 - 16;
                    if (by != this.bytes_[n3]) {
                        this.stop();
                        return Result.NO_MATCH;
                    }
                    ++n3;
                    --n4;
                    continue block0;
                }
                if ((n5 & 1) != 0) {
                    this.stop();
                    return Result.NO_MATCH;
                }
                n3 = BytesTrie.skipValue(n3, n5);
                if (!$assertionsDisabled && (this.bytes_[n3] & 0xFF) >= 32) break block0;
            }
            break;
        }
        throw new AssertionError();
    }

    public int getValue() {
        int n = this.pos_;
        int n2 = this.bytes_[n++] & 0xFF;
        if (!$assertionsDisabled && n2 < 32) {
            throw new AssertionError();
        }
        return BytesTrie.readValue(this.bytes_, n, n2 >> 1);
    }

    public long getUniqueValue() {
        int n = this.pos_;
        if (n < 0) {
            return 0L;
        }
        long l = BytesTrie.findUniqueValue(this.bytes_, n + this.remainingMatchLength_ + 1, 0L);
        return l << 31 >> 31;
    }

    public int getNextBytes(Appendable appendable) {
        int n;
        int n2 = this.pos_;
        if (n2 < 0) {
            return 1;
        }
        if (this.remainingMatchLength_ >= 0) {
            BytesTrie.append(appendable, this.bytes_[n2] & 0xFF);
            return 0;
        }
        if ((n = this.bytes_[n2++] & 0xFF) >= 32) {
            if ((n & 1) != 0) {
                return 1;
            }
            n2 = BytesTrie.skipValue(n2, n);
            n = this.bytes_[n2++] & 0xFF;
            if (!$assertionsDisabled && n >= 32) {
                throw new AssertionError();
            }
        }
        if (n < 16) {
            if (n == 0) {
                n = this.bytes_[n2++] & 0xFF;
            }
            BytesTrie.getNextBranchBytes(this.bytes_, n2, ++n, appendable);
            return n;
        }
        BytesTrie.append(appendable, this.bytes_[n2] & 0xFF);
        return 0;
    }

    public Iterator iterator() {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, 0, null);
    }

    public Iterator iterator(int n) {
        return new Iterator(this.bytes_, this.pos_, this.remainingMatchLength_, n, null);
    }

    public static Iterator iterator(byte[] byArray, int n, int n2) {
        return new Iterator(byArray, n, -1, n2, null);
    }

    private void stop() {
        this.pos_ = -1;
    }

    private static int readValue(byte[] byArray, int n, int n2) {
        int n3 = n2 < 81 ? n2 - 16 : (n2 < 108 ? n2 - 81 << 8 | byArray[n] & 0xFF : (n2 < 126 ? n2 - 108 << 16 | (byArray[n] & 0xFF) << 8 | byArray[n + 1] & 0xFF : (n2 == 126 ? (byArray[n] & 0xFF) << 16 | (byArray[n + 1] & 0xFF) << 8 | byArray[n + 2] & 0xFF : byArray[n] << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF)));
        return n3;
    }

    private static int skipValue(int n, int n2) {
        if (!$assertionsDisabled && n2 < 32) {
            throw new AssertionError();
        }
        if (n2 >= 162) {
            n = n2 < 216 ? ++n : (n2 < 252 ? (n += 2) : (n += 3 + (n2 >> 1 & 1)));
        }
        return n;
    }

    private static int skipValue(byte[] byArray, int n) {
        int n2 = byArray[n++] & 0xFF;
        return BytesTrie.skipValue(n, n2);
    }

    private static int jumpByDelta(byte[] byArray, int n) {
        int n2;
        if ((n2 = byArray[n++] & 0xFF) >= 192) {
            if (n2 < 240) {
                n2 = n2 - 192 << 8 | byArray[n++] & 0xFF;
            } else if (n2 < 254) {
                n2 = n2 - 240 << 16 | (byArray[n] & 0xFF) << 8 | byArray[n + 1] & 0xFF;
                n += 2;
            } else if (n2 == 254) {
                n2 = (byArray[n] & 0xFF) << 16 | (byArray[n + 1] & 0xFF) << 8 | byArray[n + 2] & 0xFF;
                n += 3;
            } else {
                n2 = byArray[n] << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
                n += 4;
            }
        }
        return n + n2;
    }

    private static int skipDelta(byte[] byArray, int n) {
        int n2;
        if ((n2 = byArray[n++] & 0xFF) >= 192) {
            n = n2 < 240 ? ++n : (n2 < 254 ? (n += 2) : (n += 3 + (n2 & 1)));
        }
        return n;
    }

    private Result branchNext(int n, int n2, int n3) {
        if (n2 == 0) {
            n2 = this.bytes_[n++] & 0xFF;
        }
        ++n2;
        while (n2 > 5) {
            if (n3 < (this.bytes_[n++] & 0xFF)) {
                n2 >>= 1;
                n = BytesTrie.jumpByDelta(this.bytes_, n);
                continue;
            }
            n2 -= n2 >> 1;
            n = BytesTrie.skipDelta(this.bytes_, n);
        }
        do {
            if (n3 == (this.bytes_[n++] & 0xFF)) {
                Result result;
                int n4 = this.bytes_[n] & 0xFF;
                if (!$assertionsDisabled && n4 < 32) {
                    throw new AssertionError();
                }
                if ((n4 & 1) != 0) {
                    result = Result.FINAL_VALUE;
                } else {
                    int n5;
                    ++n;
                    if ((n4 >>= 1) < 81) {
                        n5 = n4 - 16;
                    } else if (n4 < 108) {
                        n5 = n4 - 81 << 8 | this.bytes_[n++] & 0xFF;
                    } else if (n4 < 126) {
                        n5 = n4 - 108 << 16 | (this.bytes_[n] & 0xFF) << 8 | this.bytes_[n + 1] & 0xFF;
                        n += 2;
                    } else if (n4 == 126) {
                        n5 = (this.bytes_[n] & 0xFF) << 16 | (this.bytes_[n + 1] & 0xFF) << 8 | this.bytes_[n + 2] & 0xFF;
                        n += 3;
                    } else {
                        n5 = this.bytes_[n] << 24 | (this.bytes_[n + 1] & 0xFF) << 16 | (this.bytes_[n + 2] & 0xFF) << 8 | this.bytes_[n + 3] & 0xFF;
                        n += 4;
                    }
                    n4 = this.bytes_[n += n5] & 0xFF;
                    result = n4 >= 32 ? valueResults_[n4 & 1] : Result.NO_VALUE;
                }
                this.pos_ = n;
                return result;
            }
            n = BytesTrie.skipValue(this.bytes_, n);
        } while (--n2 > 1);
        if (n3 == (this.bytes_[n++] & 0xFF)) {
            this.pos_ = n;
            int n6 = this.bytes_[n] & 0xFF;
            return n6 >= 32 ? valueResults_[n6 & 1] : Result.NO_VALUE;
        }
        this.stop();
        return Result.NO_MATCH;
    }

    private Result nextImpl(int n, int n2) {
        block4: {
            do {
                int n3;
                if ((n3 = this.bytes_[n++] & 0xFF) < 16) {
                    return this.branchNext(n, n3, n2);
                }
                if (n3 < 32) {
                    int n4 = n3 - 16;
                    if (n2 == (this.bytes_[n++] & 0xFF)) {
                        this.remainingMatchLength_ = --n4;
                        this.pos_ = n;
                        return n4 < 0 && (n3 = this.bytes_[n] & 0xFF) >= 32 ? valueResults_[n3 & 1] : Result.NO_VALUE;
                    }
                    break block4;
                }
                if ((n3 & 1) != 0) break block4;
                n = BytesTrie.skipValue(n, n3);
            } while ($assertionsDisabled || (this.bytes_[n] & 0xFF) < 32);
            throw new AssertionError();
        }
        this.stop();
        return Result.NO_MATCH;
    }

    private static long findUniqueValueFromBranch(byte[] byArray, int n, int n2, long l) {
        while (n2 > 5) {
            if ((l = BytesTrie.findUniqueValueFromBranch(byArray, BytesTrie.jumpByDelta(byArray, ++n), n2 >> 1, l)) == 0L) {
                return 0L;
            }
            n2 -= n2 >> 1;
            n = BytesTrie.skipDelta(byArray, n);
        }
        do {
            int n3 = ++n;
            int n4 = byArray[n3] & 0xFF;
            boolean bl = (n4 & 1) != 0;
            int n5 = BytesTrie.readValue(byArray, ++n, n4 >> 1);
            n = BytesTrie.skipValue(n, n4);
            if (bl) {
                if (l != 0L) {
                    if (n5 == (int)(l >> 1)) continue;
                    return 0L;
                }
                l = (long)n5 << 1 | 1L;
                continue;
            }
            if ((l = BytesTrie.findUniqueValue(byArray, n + n5, l)) != 0L) continue;
            return 0L;
        } while (--n2 > 1);
        return (long)(n + 1) << 33 | l & 0x1FFFFFFFFL;
    }

    private static long findUniqueValue(byte[] byArray, int n, long l) {
        while (true) {
            int n2;
            if ((n2 = byArray[n++] & 0xFF) < 16) {
                if (n2 == 0) {
                    n2 = byArray[n++] & 0xFF;
                }
                if ((l = BytesTrie.findUniqueValueFromBranch(byArray, n, n2 + 1, l)) == 0L) {
                    return 0L;
                }
                n = (int)(l >>> 33);
                continue;
            }
            if (n2 < 32) {
                n += n2 - 16 + 1;
                continue;
            }
            boolean bl = (n2 & 1) != 0;
            int n3 = BytesTrie.readValue(byArray, n, n2 >> 1);
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
            n = BytesTrie.skipValue(n, n2);
        }
    }

    private static void getNextBranchBytes(byte[] byArray, int n, int n2, Appendable appendable) {
        while (n2 > 5) {
            BytesTrie.getNextBranchBytes(byArray, BytesTrie.jumpByDelta(byArray, ++n), n2 >> 1, appendable);
            n2 -= n2 >> 1;
            n = BytesTrie.skipDelta(byArray, n);
        }
        do {
            BytesTrie.append(appendable, byArray[n++] & 0xFF);
            n = BytesTrie.skipValue(byArray, n);
        } while (--n2 > 1);
        BytesTrie.append(appendable, byArray[n] & 0xFF);
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

    static int access$900(byte[] byArray, int n, int n2) {
        return BytesTrie.readValue(byArray, n, n2);
    }

    static int access$1100(int n, int n2) {
        return BytesTrie.skipValue(n, n2);
    }

    static int access$1200(byte[] byArray, int n) {
        return BytesTrie.skipDelta(byArray, n);
    }

    static int access$1300(byte[] byArray, int n) {
        return BytesTrie.jumpByDelta(byArray, n);
    }

    static {
        $assertionsDisabled = !BytesTrie.class.desiredAssertionStatus();
        valueResults_ = new Result[]{Result.INTERMEDIATE_VALUE, Result.FINAL_VALUE};
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Iterator
    implements java.util.Iterator<Entry> {
        private byte[] bytes_;
        private int pos_;
        private int initialPos_;
        private int remainingMatchLength_;
        private int initialRemainingMatchLength_;
        private int maxLength_;
        private Entry entry_;
        private ArrayList<Long> stack_ = new ArrayList();

        private Iterator(byte[] byArray, int n, int n2, int n3) {
            this.bytes_ = byArray;
            this.pos_ = this.initialPos_ = n;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_ = n2;
            this.maxLength_ = n3;
            this.entry_ = new Entry(this.maxLength_ != 0 ? this.maxLength_ : 32, null);
            int n4 = this.remainingMatchLength_;
            if (n4 >= 0) {
                if (this.maxLength_ > 0 && ++n4 > this.maxLength_) {
                    n4 = this.maxLength_;
                }
                Entry.access$600(this.entry_, this.bytes_, this.pos_, n4);
                this.pos_ += n4;
                this.remainingMatchLength_ -= n4;
            }
        }

        public Iterator reset() {
            this.pos_ = this.initialPos_;
            this.remainingMatchLength_ = this.initialRemainingMatchLength_;
            int n = this.remainingMatchLength_ + 1;
            if (this.maxLength_ > 0 && n > this.maxLength_) {
                n = this.maxLength_;
            }
            Entry.access$700(this.entry_, n);
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
                Entry.access$700(this.entry_, n2 & 0xFFFF);
                if ((n2 >>>= 16) > 1) {
                    if ((n = this.branchNext(n, n2)) < 0) {
                        return this.entry_;
                    }
                } else {
                    Entry.access$800(this.entry_, this.bytes_[n++]);
                }
            }
            if (this.remainingMatchLength_ >= 0) {
                return this.truncateAndStop();
            }
            while (true) {
                int n3;
                int n4;
                if ((n4 = this.bytes_[n++] & 0xFF) >= 32) {
                    n3 = (n4 & 1) != 0 ? 1 : 0;
                    this.entry_.value = BytesTrie.access$900(this.bytes_, n, n4 >> 1);
                    this.pos_ = n3 != 0 || this.maxLength_ > 0 && Entry.access$1000(this.entry_) == this.maxLength_ ? -1 : BytesTrie.access$1100(n, n4);
                    return this.entry_;
                }
                if (this.maxLength_ > 0 && Entry.access$1000(this.entry_) == this.maxLength_) {
                    return this.truncateAndStop();
                }
                if (n4 < 16) {
                    if (n4 == 0) {
                        n4 = this.bytes_[n++] & 0xFF;
                    }
                    if ((n = this.branchNext(n, n4 + 1)) >= 0) continue;
                    return this.entry_;
                }
                n3 = n4 - 16 + 1;
                if (this.maxLength_ > 0 && Entry.access$1000(this.entry_) + n3 > this.maxLength_) {
                    Entry.access$600(this.entry_, this.bytes_, n, this.maxLength_ - Entry.access$1000(this.entry_));
                    return this.truncateAndStop();
                }
                Entry.access$600(this.entry_, this.bytes_, n, n3);
                n += n3;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Entry truncateAndStop() {
            this.pos_ = -1;
            this.entry_.value = -1;
            return this.entry_;
        }

        private int branchNext(int n, int n2) {
            int n3;
            while (n2 > 5) {
                this.stack_.add((long)BytesTrie.access$1200(this.bytes_, ++n) << 32 | (long)(n2 - (n2 >> 1) << 16) | (long)Entry.access$1000(this.entry_));
                n2 >>= 1;
                n = BytesTrie.access$1300(this.bytes_, n);
            }
            byte by = this.bytes_[n++];
            boolean bl = ((n3 = this.bytes_[n++] & 0xFF) & 1) != 0;
            int n4 = BytesTrie.access$900(this.bytes_, n, n3 >> 1);
            n = BytesTrie.access$1100(n, n3);
            this.stack_.add((long)n << 32 | (long)(n2 - 1 << 16) | (long)Entry.access$1000(this.entry_));
            Entry.access$800(this.entry_, by);
            if (bl) {
                this.pos_ = -1;
                this.entry_.value = n4;
                return 1;
            }
            return n + n4;
        }

        @Override
        public Object next() {
            return this.next();
        }

        Iterator(byte[] byArray, int n, int n2, int n3, 1 var5_5) {
            this(byArray, n, n2, n3);
        }
    }

    public static final class Entry {
        public int value;
        private byte[] bytes;
        private int length;

        private Entry(int n) {
            this.bytes = new byte[n];
        }

        public int bytesLength() {
            return this.length;
        }

        public byte byteAt(int n) {
            return this.bytes[n];
        }

        public void copyBytesTo(byte[] byArray, int n) {
            System.arraycopy(this.bytes, 0, byArray, n, this.length);
        }

        public ByteBuffer bytesAsByteBuffer() {
            return ByteBuffer.wrap(this.bytes, 0, this.length).asReadOnlyBuffer();
        }

        private void ensureCapacity(int n) {
            if (this.bytes.length < n) {
                byte[] byArray = new byte[Math.min(2 * this.bytes.length, 2 * n)];
                System.arraycopy(this.bytes, 0, byArray, 0, this.length);
                this.bytes = byArray;
            }
        }

        private void append(byte by) {
            this.ensureCapacity(this.length + 1);
            this.bytes[this.length++] = by;
        }

        private void append(byte[] byArray, int n, int n2) {
            this.ensureCapacity(this.length + n2);
            System.arraycopy(byArray, n, this.bytes, this.length, n2);
            this.length += n2;
        }

        private void truncateString(int n) {
            this.length = n;
        }

        Entry(int n, 1 var2_2) {
            this(n);
        }

        static void access$600(Entry entry, byte[] byArray, int n, int n2) {
            entry.append(byArray, n, n2);
        }

        static void access$700(Entry entry, int n) {
            entry.truncateString(n);
        }

        static void access$800(Entry entry, byte by) {
            entry.append(by);
        }

        static int access$1000(Entry entry) {
            return entry.length;
        }
    }

    public static enum Result {
        NO_MATCH,
        NO_VALUE,
        FINAL_VALUE,
        INTERMEDIATE_VALUE;


        public boolean matches() {
            return this != NO_MATCH;
        }

        public boolean hasValue() {
            return this.ordinal() >= 2;
        }

        public boolean hasNext() {
            return (this.ordinal() & 1) != 0;
        }
    }

    public static final class State {
        private byte[] bytes;
        private int root;
        private int pos;
        private int remainingMatchLength;

        static byte[] access$002(State state, byte[] byArray) {
            state.bytes = byArray;
            return byArray;
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

        static byte[] access$000(State state) {
            return state.bytes;
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

