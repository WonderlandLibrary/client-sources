package com.ibm.icu.text;

import java.nio.*;
import java.util.*;

public final class Edits
{
    private static final int MAX_UNCHANGED_LENGTH = 4096;
    private static final int MAX_UNCHANGED = 4095;
    private static final int MAX_SHORT_CHANGE_OLD_LENGTH = 6;
    private static final int MAX_SHORT_CHANGE_NEW_LENGTH = 7;
    private static final int SHORT_CHANGE_NUM_MASK = 511;
    private static final int MAX_SHORT_CHANGE = 28671;
    private static final int LENGTH_IN_1TRAIL = 61;
    private static final int LENGTH_IN_2TRAIL = 62;
    private static final int STACK_CAPACITY = 100;
    private char[] array;
    private int length;
    private int delta;
    private int numChanges;
    
    public Edits() {
        this.array = new char[100];
    }
    
    public void reset() {
        final int length = 0;
        this.numChanges = length;
        this.delta = length;
        this.length = length;
    }
    
    private void setLastUnit(final int last) {
        this.array[this.length - 1] = (char)last;
    }
    
    private int lastUnit() {
        return (this.length > 0) ? this.array[this.length - 1] : '\uffff';
    }
    
    public void addUnchanged(int unchangedLength) {
        if (unchangedLength < 0) {
            throw new IllegalArgumentException("addUnchanged(" + unchangedLength + "): length must not be negative");
        }
        final int last = this.lastUnit();
        if (last < 4095) {
            final int remaining = 4095 - last;
            if (remaining >= unchangedLength) {
                this.setLastUnit(last + unchangedLength);
                return;
            }
            this.setLastUnit(4095);
            unchangedLength -= remaining;
        }
        while (unchangedLength >= 4096) {
            this.append(4095);
            unchangedLength -= 4096;
        }
        if (unchangedLength > 0) {
            this.append(unchangedLength - 1);
        }
    }
    
    public void addReplace(final int oldLength, final int newLength) {
        if (oldLength < 0 || newLength < 0) {
            throw new IllegalArgumentException("addReplace(" + oldLength + ", " + newLength + "): both lengths must be non-negative");
        }
        if (oldLength == 0 && newLength == 0) {
            return;
        }
        ++this.numChanges;
        final int newDelta = newLength - oldLength;
        if (newDelta != 0) {
            if ((newDelta > 0 && this.delta >= 0 && newDelta > Integer.MAX_VALUE - this.delta) || (newDelta < 0 && this.delta < 0 && newDelta < Integer.MIN_VALUE - this.delta)) {
                throw new IndexOutOfBoundsException();
            }
            this.delta += newDelta;
        }
        if (0 >= oldLength || oldLength > 6 || newLength > 7) {
            int head = 28672;
            if (oldLength < 61 && newLength < 61) {
                head |= oldLength << 6;
                head |= newLength;
                this.append(head);
            }
            else if (this.array.length - this.length >= 5 || this.growArray()) {
                int limit = this.length + 1;
                if (oldLength < 61) {
                    head |= oldLength << 6;
                }
                else if (oldLength <= 32767) {
                    head |= 0xF40;
                    this.array[limit++] = (char)(0x8000 | oldLength);
                }
                else {
                    head |= 62 + (oldLength >> 30) << 6;
                    this.array[limit++] = (char)(0x8000 | oldLength >> 15);
                    this.array[limit++] = (char)(0x8000 | oldLength);
                }
                if (newLength < 61) {
                    head |= newLength;
                }
                else if (newLength <= 32767) {
                    head |= 0x3D;
                    this.array[limit++] = (char)(0x8000 | newLength);
                }
                else {
                    head |= 62 + (newLength >> 30);
                    this.array[limit++] = (char)(0x8000 | newLength >> 15);
                    this.array[limit++] = (char)(0x8000 | newLength);
                }
                this.array[this.length] = (char)head;
                this.length = limit;
            }
            return;
        }
        final int u = oldLength << 12 | newLength << 9;
        final int last = this.lastUnit();
        if (4095 < last && last < 28671 && (last & 0xFFFFFE00) == u && (last & 0x1FF) < 511) {
            this.setLastUnit(last + 1);
            return;
        }
        this.append(u);
    }
    
    private void append(final int r) {
        if (this.length < this.array.length || this.growArray()) {
            this.array[this.length++] = (char)r;
        }
    }
    
    private boolean growArray() {
        int newCapacity;
        if (this.array.length == 100) {
            newCapacity = 2000;
        }
        else {
            if (this.array.length == Integer.MAX_VALUE) {
                throw new BufferOverflowException();
            }
            if (this.array.length >= 1073741823) {
                newCapacity = Integer.MAX_VALUE;
            }
            else {
                newCapacity = 2 * this.array.length;
            }
        }
        if (newCapacity - this.array.length < 5) {
            throw new BufferOverflowException();
        }
        this.array = Arrays.copyOf(this.array, newCapacity);
        return true;
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
        return new Iterator(this.array, this.length, true, true);
    }
    
    public Iterator getCoarseIterator() {
        return new Iterator(this.array, this.length, false, true);
    }
    
    public Iterator getFineChangesIterator() {
        return new Iterator(this.array, this.length, true, false);
    }
    
    public Iterator getFineIterator() {
        return new Iterator(this.array, this.length, false, false);
    }
    
    public Edits mergeAndAppend(final Edits ab, final Edits bc) {
        final Iterator abIter = ab.getFineIterator();
        final Iterator bcIter = bc.getFineIterator();
        boolean abHasNext = true;
        boolean bcHasNext = true;
        int aLength = 0;
        int ab_bLength = 0;
        int bc_bLength = 0;
        int cLength = 0;
        int pending_aLength = 0;
        int pending_cLength = 0;
        while (true) {
            if (bc_bLength == 0 && bcHasNext && (bcHasNext = bcIter.next())) {
                bc_bLength = bcIter.oldLength();
                cLength = bcIter.newLength();
                if (bc_bLength == 0) {
                    if (ab_bLength == 0 || !abIter.hasChange()) {
                        this.addReplace(pending_aLength, pending_cLength + cLength);
                        pending_cLength = (pending_aLength = 0);
                        continue;
                    }
                    pending_cLength += cLength;
                    continue;
                }
            }
            if (ab_bLength == 0) {
                if (abHasNext && (abHasNext = abIter.next())) {
                    aLength = abIter.oldLength();
                    ab_bLength = abIter.newLength();
                    if (ab_bLength == 0) {
                        if (bc_bLength == bcIter.oldLength() || !bcIter.hasChange()) {
                            this.addReplace(pending_aLength + aLength, pending_cLength);
                            pending_cLength = (pending_aLength = 0);
                            continue;
                        }
                        pending_aLength += aLength;
                        continue;
                    }
                }
                else {
                    if (bc_bLength == 0) {
                        if (pending_aLength != 0 || pending_cLength != 0) {
                            this.addReplace(pending_aLength, pending_cLength);
                        }
                        return this;
                    }
                    throw new IllegalArgumentException("The ab output string is shorter than the bc input string.");
                }
            }
            if (bc_bLength == 0) {
                throw new IllegalArgumentException("The bc input string is shorter than the ab output string.");
            }
            if (!abIter.hasChange() && !bcIter.hasChange()) {
                if (pending_aLength != 0 || pending_cLength != 0) {
                    this.addReplace(pending_aLength, pending_cLength);
                    pending_cLength = (pending_aLength = 0);
                }
                final int unchangedLength = (aLength <= cLength) ? aLength : cLength;
                this.addUnchanged(unchangedLength);
                aLength = (ab_bLength = aLength - unchangedLength);
                cLength = (bc_bLength = cLength - unchangedLength);
            }
            else {
                if (!abIter.hasChange() && bcIter.hasChange()) {
                    if (ab_bLength >= bc_bLength) {
                        this.addReplace(pending_aLength + bc_bLength, pending_cLength + cLength);
                        pending_cLength = (pending_aLength = 0);
                        ab_bLength = (aLength = ab_bLength - bc_bLength);
                        bc_bLength = 0;
                        continue;
                    }
                }
                else if (abIter.hasChange() && !bcIter.hasChange()) {
                    if (ab_bLength <= bc_bLength) {
                        this.addReplace(pending_aLength + aLength, pending_cLength + ab_bLength);
                        pending_cLength = (pending_aLength = 0);
                        bc_bLength = (cLength = bc_bLength - ab_bLength);
                        ab_bLength = 0;
                        continue;
                    }
                }
                else if (ab_bLength == bc_bLength) {
                    this.addReplace(pending_aLength + aLength, pending_cLength + cLength);
                    pending_cLength = (pending_aLength = 0);
                    bc_bLength = (ab_bLength = 0);
                    continue;
                }
                pending_aLength += aLength;
                pending_cLength += cLength;
                if (ab_bLength < bc_bLength) {
                    bc_bLength -= ab_bLength;
                    ab_bLength = (cLength = 0);
                }
                else {
                    ab_bLength -= bc_bLength;
                    bc_bLength = (aLength = 0);
                }
            }
        }
    }
    
    public static final class Iterator
    {
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
        
        private Iterator(final char[] a, final int len, final boolean oc, final boolean crs) {
            this.array = a;
            this.length = len;
            this.onlyChanges_ = oc;
            this.coarse = crs;
        }
        
        private int readLength(final int head) {
            if (head < 61) {
                return head;
            }
            if (head < 62) {
                assert this.index < this.length;
                assert this.array[this.index] >= '\u8000';
                return this.array[this.index++] & '\u7fff';
            }
            else {
                assert this.index + 2 <= this.length;
                assert this.array[this.index] >= '\u8000';
                assert this.array[this.index + 1] >= '\u8000';
                final int len = (head & 0x1) << 30 | (this.array[this.index] & '\u7fff') << 15 | (this.array[this.index + 1] & '\u7fff');
                this.index += 2;
                return len;
            }
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
            final int n = 0;
            this.newLength_ = n;
            this.oldLength_ = n;
            return false;
        }
        
        public boolean next() {
            return this.next(this.onlyChanges_);
        }
        
        private boolean next(final boolean onlyChanges) {
            if (this.dir > 0) {
                this.updateNextIndexes();
            }
            else {
                if (this.dir < 0 && this.remaining > 0) {
                    ++this.index;
                    this.dir = 1;
                    return true;
                }
                this.dir = 1;
            }
            if (this.remaining >= 1) {
                if (this.remaining > 1) {
                    --this.remaining;
                    return true;
                }
                this.remaining = 0;
            }
            if (this.index >= this.length) {
                return this.noNext();
            }
            int u = this.array[this.index++];
            if (u <= 4095) {
                this.changed = false;
                this.oldLength_ = u + 1;
                while (this.index < this.length && (u = this.array[this.index]) <= 4095) {
                    ++this.index;
                    this.oldLength_ += u + 1;
                }
                this.newLength_ = this.oldLength_;
                if (!onlyChanges) {
                    return true;
                }
                this.updateNextIndexes();
                if (this.index >= this.length) {
                    return this.noNext();
                }
                ++this.index;
            }
            this.changed = true;
            if (u <= 28671) {
                final int oldLen = u >> 12;
                final int newLen = u >> 9 & 0x7;
                final int num = (u & 0x1FF) + 1;
                if (!this.coarse) {
                    this.oldLength_ = oldLen;
                    this.newLength_ = newLen;
                    if (num > 1) {
                        this.remaining = num;
                    }
                    return true;
                }
                this.oldLength_ = num * oldLen;
                this.newLength_ = num * newLen;
            }
            else {
                assert u <= 32767;
                this.oldLength_ = this.readLength(u >> 6 & 0x3F);
                this.newLength_ = this.readLength(u & 0x3F);
                if (!this.coarse) {
                    return true;
                }
            }
            while (this.index < this.length && (u = this.array[this.index]) > 4095) {
                ++this.index;
                if (u <= 28671) {
                    final int num2 = (u & 0x1FF) + 1;
                    this.oldLength_ += (u >> 12) * num2;
                    this.newLength_ += (u >> 9 & 0x7) * num2;
                }
                else {
                    assert u <= 32767;
                    this.oldLength_ += this.readLength(u >> 6 & 0x3F);
                    this.newLength_ += this.readLength(u & 0x3F);
                }
            }
            return true;
        }
        
        private boolean previous() {
            if (this.dir >= 0) {
                if (this.dir > 0) {
                    if (this.remaining > 0) {
                        --this.index;
                        this.dir = -1;
                        return true;
                    }
                    this.updateNextIndexes();
                }
                this.dir = -1;
            }
            if (this.remaining > 0) {
                final int u = this.array[this.index];
                assert 4095 < u && u <= 28671;
                if (this.remaining <= (u & 0x1FF)) {
                    ++this.remaining;
                    this.updatePreviousIndexes();
                    return true;
                }
                this.remaining = 0;
            }
            if (this.index <= 0) {
                return this.noNext();
            }
            final char[] array = this.array;
            final int index = this.index - 1;
            this.index = index;
            int u = array[index];
            if (u <= 4095) {
                this.changed = false;
                this.oldLength_ = u + 1;
                while (this.index > 0 && (u = this.array[this.index - 1]) <= 4095) {
                    --this.index;
                    this.oldLength_ += u + 1;
                }
                this.newLength_ = this.oldLength_;
                this.updatePreviousIndexes();
                return true;
            }
            this.changed = true;
            if (u <= 28671) {
                final int oldLen = u >> 12;
                final int newLen = u >> 9 & 0x7;
                final int num = (u & 0x1FF) + 1;
                if (!this.coarse) {
                    this.oldLength_ = oldLen;
                    this.newLength_ = newLen;
                    if (num > 1) {
                        this.remaining = 1;
                    }
                    this.updatePreviousIndexes();
                    return true;
                }
                this.oldLength_ = num * oldLen;
                this.newLength_ = num * newLen;
            }
            else {
                if (u <= 32767) {
                    this.oldLength_ = this.readLength(u >> 6 & 0x3F);
                    this.newLength_ = this.readLength(u & 0x3F);
                }
                else {
                    assert this.index > 0;
                    char[] array2;
                    int index2;
                    do {
                        array2 = this.array;
                        index2 = this.index - 1;
                        this.index = index2;
                    } while ((u = array2[index2]) > 32767);
                    assert u > 28671;
                    final int headIndex = this.index++;
                    this.oldLength_ = this.readLength(u >> 6 & 0x3F);
                    this.newLength_ = this.readLength(u & 0x3F);
                    this.index = headIndex;
                }
                if (!this.coarse) {
                    this.updatePreviousIndexes();
                    return true;
                }
            }
            while (this.index > 0 && (u = this.array[this.index - 1]) > 4095) {
                --this.index;
                if (u <= 28671) {
                    final int num2 = (u & 0x1FF) + 1;
                    this.oldLength_ += (u >> 12) * num2;
                    this.newLength_ += (u >> 9 & 0x7) * num2;
                }
                else {
                    if (u > 32767) {
                        continue;
                    }
                    final int headIndex = this.index++;
                    this.oldLength_ += this.readLength(u >> 6 & 0x3F);
                    this.newLength_ += this.readLength(u & 0x3F);
                    this.index = headIndex;
                }
            }
            this.updatePreviousIndexes();
            return true;
        }
        
        public boolean findSourceIndex(final int i) {
            return this.findIndex(i, true) == 0;
        }
        
        public boolean findDestinationIndex(final int i) {
            return this.findIndex(i, false) == 0;
        }
        
        private int findIndex(final int i, final boolean findSource) {
            if (i < 0) {
                return -1;
            }
            int spanStart;
            int spanLength;
            if (findSource) {
                spanStart = this.srcIndex;
                spanLength = this.oldLength_;
            }
            else {
                spanStart = this.destIndex;
                spanLength = this.newLength_;
            }
            if (i < spanStart) {
                if (i >= spanStart / 2) {
                    while (true) {
                        final boolean hasPrevious = this.previous();
                        assert hasPrevious;
                        spanStart = (findSource ? this.srcIndex : this.destIndex);
                        if (i >= spanStart) {
                            return 0;
                        }
                        if (this.remaining <= 0) {
                            continue;
                        }
                        spanLength = (findSource ? this.oldLength_ : this.newLength_);
                        final int u = this.array[this.index];
                        assert 4095 < u && u <= 28671;
                        final int num = (u & 0x1FF) + 1 - this.remaining;
                        final int len = num * spanLength;
                        if (i >= spanStart - len) {
                            final int n = (spanStart - i - 1) / spanLength + 1;
                            this.srcIndex -= n * this.oldLength_;
                            this.replIndex -= n * this.newLength_;
                            this.destIndex -= n * this.newLength_;
                            this.remaining += n;
                            return 0;
                        }
                        this.srcIndex -= num * this.oldLength_;
                        this.replIndex -= num * this.newLength_;
                        this.destIndex -= num * this.newLength_;
                        this.remaining = 0;
                    }
                }
                else {
                    this.dir = 0;
                    final int index = 0;
                    this.destIndex = index;
                    this.replIndex = index;
                    this.srcIndex = index;
                    this.newLength_ = index;
                    this.oldLength_ = index;
                    this.remaining = index;
                    this.index = index;
                }
            }
            else if (i < spanStart + spanLength) {
                return 0;
            }
            while (this.next(false)) {
                if (findSource) {
                    spanStart = this.srcIndex;
                    spanLength = this.oldLength_;
                }
                else {
                    spanStart = this.destIndex;
                    spanLength = this.newLength_;
                }
                if (i < spanStart + spanLength) {
                    return 0;
                }
                if (this.remaining <= 1) {
                    continue;
                }
                final int len2 = this.remaining * spanLength;
                if (i < spanStart + len2) {
                    final int n2 = (i - spanStart) / spanLength;
                    this.srcIndex += n2 * this.oldLength_;
                    this.replIndex += n2 * this.newLength_;
                    this.destIndex += n2 * this.newLength_;
                    this.remaining -= n2;
                    return 0;
                }
                this.oldLength_ *= this.remaining;
                this.newLength_ *= this.remaining;
                this.remaining = 0;
            }
            return 1;
        }
        
        public int destinationIndexFromSourceIndex(final int i) {
            final int where = this.findIndex(i, true);
            if (where < 0) {
                return 0;
            }
            if (where > 0 || i == this.srcIndex) {
                return this.destIndex;
            }
            if (this.changed) {
                return this.destIndex + this.newLength_;
            }
            return this.destIndex + (i - this.srcIndex);
        }
        
        public int sourceIndexFromDestinationIndex(final int i) {
            final int where = this.findIndex(i, false);
            if (where < 0) {
                return 0;
            }
            if (where > 0 || i == this.destIndex) {
                return this.srcIndex;
            }
            if (this.changed) {
                return this.srcIndex + this.oldLength_;
            }
            return this.srcIndex + (i - this.destIndex);
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
        
        @Deprecated
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("{ src[");
            sb.append(this.srcIndex);
            sb.append("..");
            sb.append(this.srcIndex + this.oldLength_);
            if (this.changed) {
                sb.append("] \u21dd dest[");
            }
            else {
                sb.append("] \u2261 dest[");
            }
            sb.append(this.destIndex);
            sb.append("..");
            sb.append(this.destIndex + this.newLength_);
            if (this.changed) {
                sb.append("], repl[");
                sb.append(this.replIndex);
                sb.append("..");
                sb.append(this.replIndex + this.newLength_);
                sb.append("] }");
            }
            else {
                sb.append("] (no-change) }");
            }
            return sb.toString();
        }
    }
}
