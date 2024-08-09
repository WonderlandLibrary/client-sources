/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import java.nio.BufferOverflowException;
import java.util.Arrays;

public final class Edits {
    private static final int MAX_UNCHANGED_LENGTH = 4096;
    private static final int MAX_UNCHANGED = 4095;
    private static final int MAX_SHORT_CHANGE_OLD_LENGTH = 6;
    private static final int MAX_SHORT_CHANGE_NEW_LENGTH = 7;
    private static final int SHORT_CHANGE_NUM_MASK = 511;
    private static final int MAX_SHORT_CHANGE = 28671;
    private static final int LENGTH_IN_1TRAIL = 61;
    private static final int LENGTH_IN_2TRAIL = 62;
    private static final int STACK_CAPACITY = 100;
    private char[] array = new char[100];
    private int length;
    private int delta;
    private int numChanges;

    public void reset() {
        this.numChanges = 0;
        this.delta = 0;
        this.length = 0;
    }

    private void setLastUnit(int n) {
        this.array[this.length - 1] = (char)n;
    }

    private int lastUnit() {
        return this.length > 0 ? this.array[this.length - 1] : 65535;
    }

    public void addUnchanged(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("addUnchanged(" + n + "): length must not be negative");
        }
        int n2 = this.lastUnit();
        if (n2 < 4095) {
            int n3 = 4095 - n2;
            if (n3 >= n) {
                this.setLastUnit(n2 + n);
                return;
            }
            this.setLastUnit(4095);
            n -= n3;
        }
        while (n >= 4096) {
            this.append(4095);
            n -= 4096;
        }
        if (n > 0) {
            this.append(n - 1);
        }
    }

    public void addReplace(int n, int n2) {
        if (n < 0 || n2 < 0) {
            throw new IllegalArgumentException("addReplace(" + n + ", " + n2 + "): both lengths must be non-negative");
        }
        if (n == 0 && n2 == 0) {
            return;
        }
        ++this.numChanges;
        int n3 = n2 - n;
        if (n3 != 0) {
            if (n3 > 0 && this.delta >= 0 && n3 > Integer.MAX_VALUE - this.delta || n3 < 0 && this.delta < 0 && n3 < Integer.MIN_VALUE - this.delta) {
                throw new IndexOutOfBoundsException();
            }
            this.delta += n3;
        }
        if (0 < n && n <= 6 && n2 <= 7) {
            int n4 = n << 12 | n2 << 9;
            int n5 = this.lastUnit();
            if (4095 < n5 && n5 < 28671 && (n5 & 0xFFFFFE00) == n4 && (n5 & 0x1FF) < 511) {
                this.setLastUnit(n5 + 1);
                return;
            }
            this.append(n4);
            return;
        }
        int n6 = 28672;
        if (n < 61 && n2 < 61) {
            n6 |= n << 6;
            this.append(n6 |= n2);
        } else if (this.array.length - this.length >= 5 || this.growArray()) {
            int n7 = this.length + 1;
            if (n < 61) {
                n6 |= n << 6;
            } else if (n <= Short.MAX_VALUE) {
                n6 |= 0xF40;
                this.array[n7++] = (char)(0x8000 | n);
            } else {
                n6 |= 62 + (n >> 30) << 6;
                this.array[n7++] = (char)(0x8000 | n >> 15);
                this.array[n7++] = (char)(0x8000 | n);
            }
            if (n2 < 61) {
                n6 |= n2;
            } else if (n2 <= Short.MAX_VALUE) {
                n6 |= 0x3D;
                this.array[n7++] = (char)(0x8000 | n2);
            } else {
                n6 |= 62 + (n2 >> 30);
                this.array[n7++] = (char)(0x8000 | n2 >> 15);
                this.array[n7++] = (char)(0x8000 | n2);
            }
            this.array[this.length] = (char)n6;
            this.length = n7;
        }
    }

    private void append(int n) {
        if (this.length < this.array.length || this.growArray()) {
            this.array[this.length++] = (char)n;
        }
    }

    private boolean growArray() {
        int n;
        if (this.array.length == 100) {
            n = 2000;
        } else {
            if (this.array.length == Integer.MAX_VALUE) {
                throw new BufferOverflowException();
            }
            n = this.array.length >= 0x3FFFFFFF ? Integer.MAX_VALUE : 2 * this.array.length;
        }
        if (n - this.array.length < 5) {
            throw new BufferOverflowException();
        }
        this.array = Arrays.copyOf(this.array, n);
        return false;
    }

    public int lengthDelta() {
        return this.delta;
    }

    public boolean hasChanges() {
        return this.numChanges != 0;
    }

    public int numberOfChanges() {
        return this.numChanges;
    }

    public Iterator getCoarseChangesIterator() {
        return new Iterator(this.array, this.length, true, true, null);
    }

    public Iterator getCoarseIterator() {
        return new Iterator(this.array, this.length, false, true, null);
    }

    public Iterator getFineChangesIterator() {
        return new Iterator(this.array, this.length, true, false, null);
    }

    public Iterator getFineIterator() {
        return new Iterator(this.array, this.length, false, false, null);
    }

    public Edits mergeAndAppend(Edits edits, Edits edits2) {
        Iterator iterator2 = edits.getFineIterator();
        Iterator iterator3 = edits2.getFineIterator();
        boolean bl = true;
        boolean bl2 = true;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while (true) {
            if (n3 == 0 && bl2 && (bl2 = iterator3.next())) {
                n3 = iterator3.oldLength();
                n4 = iterator3.newLength();
                if (n3 == 0) {
                    if (n2 == 0 || !iterator2.hasChange()) {
                        this.addReplace(n5, n6 + n4);
                        n6 = 0;
                        n5 = 0;
                        continue;
                    }
                    n6 += n4;
                    continue;
                }
            }
            if (n2 == 0) {
                if (bl && (bl = iterator2.next())) {
                    n = iterator2.oldLength();
                    n2 = iterator2.newLength();
                    if (n2 == 0) {
                        if (n3 == iterator3.oldLength() || !iterator3.hasChange()) {
                            this.addReplace(n5 + n, n6);
                            n6 = 0;
                            n5 = 0;
                            continue;
                        }
                        n5 += n;
                        continue;
                    }
                } else {
                    if (n3 == 0) break;
                    throw new IllegalArgumentException("The ab output string is shorter than the bc input string.");
                }
            }
            if (n3 == 0) {
                throw new IllegalArgumentException("The bc input string is shorter than the ab output string.");
            }
            if (!iterator2.hasChange() && !iterator3.hasChange()) {
                if (n5 != 0 || n6 != 0) {
                    this.addReplace(n5, n6);
                    n6 = 0;
                    n5 = 0;
                }
                int n7 = n <= n4 ? n : n4;
                this.addUnchanged(n7);
                n2 = n -= n7;
                n3 = n4 -= n7;
                continue;
            }
            if (!iterator2.hasChange() && iterator3.hasChange()) {
                if (n2 >= n3) {
                    this.addReplace(n5 + n3, n6 + n4);
                    n6 = 0;
                    n5 = 0;
                    n = n2 -= n3;
                    n3 = 0;
                    continue;
                }
            } else if (iterator2.hasChange() && !iterator3.hasChange()) {
                if (n2 <= n3) {
                    this.addReplace(n5 + n, n6 + n2);
                    n6 = 0;
                    n5 = 0;
                    n4 = n3 -= n2;
                    n2 = 0;
                    continue;
                }
            } else if (n2 == n3) {
                this.addReplace(n5 + n, n6 + n4);
                n6 = 0;
                n5 = 0;
                n3 = 0;
                n2 = 0;
                continue;
            }
            n5 += n;
            n6 += n4;
            if (n2 < n3) {
                n3 -= n2;
                n2 = 0;
                n4 = 0;
                continue;
            }
            n2 -= n3;
            n3 = 0;
            n = 0;
        }
        if (n5 != 0 || n6 != 0) {
            this.addReplace(n5, n6);
        }
        return this;
    }

    public static final class Iterator {
        private final char[] array;
        private int index;
        private final int length;
        private int remaining;
        private final boolean onlyChanges_;
        private final boolean coarse;
        private int dir;
        private boolean changed;
        private int oldLength_;
        private int newLength_;
        private int srcIndex;
        private int replIndex;
        private int destIndex;
        static final boolean $assertionsDisabled = !Edits.class.desiredAssertionStatus();

        private Iterator(char[] cArray, int n, boolean bl, boolean bl2) {
            this.array = cArray;
            this.length = n;
            this.onlyChanges_ = bl;
            this.coarse = bl2;
        }

        private int readLength(int n) {
            if (n < 61) {
                return n;
            }
            if (n < 62) {
                if (!$assertionsDisabled && this.index >= this.length) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && this.array[this.index] < '\u8000') {
                    throw new AssertionError();
                }
                return this.array[this.index++] & Short.MAX_VALUE;
            }
            if (!$assertionsDisabled && this.index + 2 > this.length) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.array[this.index] < '\u8000') {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.array[this.index + 1] < '\u8000') {
                throw new AssertionError();
            }
            int n2 = (n & 1) << 30 | (this.array[this.index] & Short.MAX_VALUE) << 15 | this.array[this.index + 1] & Short.MAX_VALUE;
            this.index += 2;
            return n2;
        }

        private void updateNextIndexes() {
            this.srcIndex += this.oldLength_;
            if (this.changed) {
                this.replIndex += this.newLength_;
            }
            this.destIndex += this.newLength_;
        }

        private void updatePreviousIndexes() {
            this.srcIndex -= this.oldLength_;
            if (this.changed) {
                this.replIndex -= this.newLength_;
            }
            this.destIndex -= this.newLength_;
        }

        private boolean noNext() {
            this.dir = 0;
            this.changed = false;
            this.newLength_ = 0;
            this.oldLength_ = 0;
            return true;
        }

        public boolean next() {
            return this.next(this.onlyChanges_);
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean next(boolean bl) {
            int n;
            char c;
            block19: {
                if (this.dir > 0) {
                    this.updateNextIndexes();
                } else {
                    if (this.dir < 0 && this.remaining > 0) {
                        ++this.index;
                        this.dir = 1;
                        return false;
                    }
                    this.dir = 1;
                }
                if (this.remaining >= 1) {
                    if (this.remaining > 1) {
                        --this.remaining;
                        return false;
                    }
                    this.remaining = 0;
                }
                if (this.index >= this.length) {
                    return this.noNext();
                }
                if ((c = this.array[this.index++]) <= '\u0fff') {
                    this.changed = false;
                    this.oldLength_ = c + '\u0001';
                    while (this.index < this.length && (c = this.array[this.index]) <= '\u0fff') {
                        ++this.index;
                        this.oldLength_ += c + '\u0001';
                    }
                    this.newLength_ = this.oldLength_;
                    if (!bl) {
                        return false;
                    }
                    this.updateNextIndexes();
                    if (this.index >= this.length) {
                        return this.noNext();
                    }
                    ++this.index;
                }
                this.changed = true;
                if (c <= '\u6fff') {
                    n = c >> 12;
                    int n2 = c >> 9 & 7;
                    int n3 = (c & 0x1FF) + 1;
                    if (this.coarse) {
                        this.oldLength_ = n3 * n;
                        this.newLength_ = n3 * n2;
                        break block19;
                    } else {
                        this.oldLength_ = n;
                        this.newLength_ = n2;
                        if (n3 > 1) {
                            this.remaining = n3;
                        }
                        return false;
                    }
                }
                if (!$assertionsDisabled && c > Short.MAX_VALUE) {
                    throw new AssertionError();
                }
                this.oldLength_ = this.readLength(c >> 6 & 0x3F);
                this.newLength_ = this.readLength(c & 0x3F);
                if (!this.coarse) {
                    return false;
                }
            }
            while (this.index < this.length && (c = this.array[this.index]) > '\u0fff') {
                ++this.index;
                if (c <= '\u6fff') {
                    n = (c & 0x1FF) + 1;
                    this.oldLength_ += (c >> 12) * n;
                    this.newLength_ += (c >> 9 & 7) * n;
                    continue;
                }
                if (!$assertionsDisabled && c > Short.MAX_VALUE) {
                    throw new AssertionError();
                }
                this.oldLength_ += this.readLength(c >> 6 & 0x3F);
                this.newLength_ += this.readLength(c & 0x3F);
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean previous() {
            int n;
            char c;
            block21: {
                if (this.dir >= 0) {
                    if (this.dir > 0) {
                        if (this.remaining > 0) {
                            --this.index;
                            this.dir = -1;
                            return false;
                        }
                        this.updateNextIndexes();
                    }
                    this.dir = -1;
                }
                if (this.remaining > 0) {
                    c = this.array[this.index];
                    if (!($assertionsDisabled || '\u0fff' < c && c <= '\u6fff')) {
                        throw new AssertionError();
                    }
                    if (this.remaining <= (c & 0x1FF)) {
                        ++this.remaining;
                        this.updatePreviousIndexes();
                        return false;
                    }
                    this.remaining = 0;
                }
                if (this.index <= 0) {
                    return this.noNext();
                }
                if ((c = this.array[--this.index]) <= '\u0fff') {
                    this.changed = false;
                    this.oldLength_ = c + '\u0001';
                    while (this.index > 0 && (c = this.array[this.index - 1]) <= '\u0fff') {
                        --this.index;
                        this.oldLength_ += c + '\u0001';
                    }
                    this.newLength_ = this.oldLength_;
                    this.updatePreviousIndexes();
                    return false;
                }
                this.changed = true;
                if (c <= '\u6fff') {
                    n = c >> 12;
                    int n2 = c >> 9 & 7;
                    int n3 = (c & 0x1FF) + 1;
                    if (this.coarse) {
                        this.oldLength_ = n3 * n;
                        this.newLength_ = n3 * n2;
                        break block21;
                    } else {
                        this.oldLength_ = n;
                        this.newLength_ = n2;
                        if (n3 > 1) {
                            this.remaining = 1;
                        }
                        this.updatePreviousIndexes();
                        return false;
                    }
                }
                if (c <= Short.MAX_VALUE) {
                    this.oldLength_ = this.readLength(c >> 6 & 0x3F);
                    this.newLength_ = this.readLength(c & 0x3F);
                } else {
                    if (!$assertionsDisabled && this.index <= 0) {
                        throw new AssertionError();
                    }
                    while ((c = this.array[--this.index]) > Short.MAX_VALUE) {
                    }
                    if (!$assertionsDisabled && c <= '\u6fff') {
                        throw new AssertionError();
                    }
                    n = this.index++;
                    this.oldLength_ = this.readLength(c >> 6 & 0x3F);
                    this.newLength_ = this.readLength(c & 0x3F);
                    this.index = n;
                }
                if (!this.coarse) {
                    this.updatePreviousIndexes();
                    return false;
                }
            }
            while (this.index > 0 && (c = this.array[this.index - 1]) > '\u0fff') {
                --this.index;
                if (c <= '\u6fff') {
                    n = (c & 0x1FF) + 1;
                    this.oldLength_ += (c >> 12) * n;
                    this.newLength_ += (c >> 9 & 7) * n;
                    continue;
                }
                if (c > Short.MAX_VALUE) continue;
                ++this.index;
                this.oldLength_ += this.readLength(c >> 6 & 0x3F);
                this.newLength_ += this.readLength(c & 0x3F);
                this.index = n;
            }
            this.updatePreviousIndexes();
            return false;
        }

        public boolean findSourceIndex(int n) {
            return this.findIndex(n, true) == 0;
        }

        public boolean findDestinationIndex(int n) {
            return this.findIndex(n, false) == 0;
        }

        private int findIndex(int n, boolean bl) {
            int n2;
            int n3;
            if (n < 0) {
                return 1;
            }
            if (bl) {
                n3 = this.srcIndex;
                n2 = this.oldLength_;
            } else {
                n3 = this.destIndex;
                n2 = this.newLength_;
            }
            if (n < n3) {
                if (n >= n3 / 2) {
                    while (true) {
                        boolean bl2 = this.previous();
                        if (!$assertionsDisabled && !bl2) {
                            throw new AssertionError();
                        }
                        int n4 = n3 = bl ? this.srcIndex : this.destIndex;
                        if (n >= n3) {
                            return 1;
                        }
                        if (this.remaining <= 0) continue;
                        n2 = bl ? this.oldLength_ : this.newLength_;
                        char c = this.array[this.index];
                        if (!($assertionsDisabled || '\u0fff' < c && c <= '\u6fff')) {
                            throw new AssertionError();
                        }
                        int n5 = (c & 0x1FF) + 1 - this.remaining;
                        int n6 = n5 * n2;
                        if (n >= n3 - n6) {
                            int n7 = (n3 - n - 1) / n2 + 1;
                            this.srcIndex -= n7 * this.oldLength_;
                            this.replIndex -= n7 * this.newLength_;
                            this.destIndex -= n7 * this.newLength_;
                            this.remaining += n7;
                            return 1;
                        }
                        this.srcIndex -= n5 * this.oldLength_;
                        this.replIndex -= n5 * this.newLength_;
                        this.destIndex -= n5 * this.newLength_;
                        this.remaining = 0;
                    }
                }
                this.dir = 0;
                this.destIndex = 0;
                this.replIndex = 0;
                this.srcIndex = 0;
                this.newLength_ = 0;
                this.oldLength_ = 0;
                this.remaining = 0;
                this.index = 0;
            } else if (n < n3 + n2) {
                return 1;
            }
            while (this.next(false)) {
                if (bl) {
                    n3 = this.srcIndex;
                    n2 = this.oldLength_;
                } else {
                    n3 = this.destIndex;
                    n2 = this.newLength_;
                }
                if (n < n3 + n2) {
                    return 1;
                }
                if (this.remaining <= 1) continue;
                int n8 = this.remaining * n2;
                if (n < n3 + n8) {
                    int n9 = (n - n3) / n2;
                    this.srcIndex += n9 * this.oldLength_;
                    this.replIndex += n9 * this.newLength_;
                    this.destIndex += n9 * this.newLength_;
                    this.remaining -= n9;
                    return 1;
                }
                this.oldLength_ *= this.remaining;
                this.newLength_ *= this.remaining;
                this.remaining = 0;
            }
            return 0;
        }

        public int destinationIndexFromSourceIndex(int n) {
            int n2 = this.findIndex(n, true);
            if (n2 < 0) {
                return 1;
            }
            if (n2 > 0 || n == this.srcIndex) {
                return this.destIndex;
            }
            if (this.changed) {
                return this.destIndex + this.newLength_;
            }
            return this.destIndex + (n - this.srcIndex);
        }

        public int sourceIndexFromDestinationIndex(int n) {
            int n2 = this.findIndex(n, false);
            if (n2 < 0) {
                return 1;
            }
            if (n2 > 0 || n == this.destIndex) {
                return this.srcIndex;
            }
            if (this.changed) {
                return this.srcIndex + this.oldLength_;
            }
            return this.srcIndex + (n - this.destIndex);
        }

        public boolean hasChange() {
            return this.changed;
        }

        public int oldLength() {
            return this.oldLength_;
        }

        public int newLength() {
            return this.newLength_;
        }

        public int sourceIndex() {
            return this.srcIndex;
        }

        public int replacementIndex() {
            return this.replIndex;
        }

        public int destinationIndex() {
            return this.destIndex;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append("{ src[");
            stringBuilder.append(this.srcIndex);
            stringBuilder.append("..");
            stringBuilder.append(this.srcIndex + this.oldLength_);
            if (this.changed) {
                stringBuilder.append("] \u21dd dest[");
            } else {
                stringBuilder.append("] \u2261 dest[");
            }
            stringBuilder.append(this.destIndex);
            stringBuilder.append("..");
            stringBuilder.append(this.destIndex + this.newLength_);
            if (this.changed) {
                stringBuilder.append("], repl[");
                stringBuilder.append(this.replIndex);
                stringBuilder.append("..");
                stringBuilder.append(this.replIndex + this.newLength_);
                stringBuilder.append("] }");
            } else {
                stringBuilder.append("] (no-change) }");
            }
            return stringBuilder.toString();
        }

        Iterator(char[] cArray, int n, boolean bl, boolean bl2, 1 var5_5) {
            this(cArray, n, bl, bl2);
        }
    }
}

