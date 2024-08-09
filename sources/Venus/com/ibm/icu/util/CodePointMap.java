/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class CodePointMap
implements Iterable<Range> {
    static final boolean $assertionsDisabled = !CodePointMap.class.desiredAssertionStatus();

    protected CodePointMap() {
    }

    public abstract int get(int var1);

    public abstract boolean getRange(int var1, ValueFilter var2, Range var3);

    public boolean getRange(int n, RangeOption rangeOption, int n2, ValueFilter valueFilter, Range range) {
        if (!$assertionsDisabled && rangeOption == null) {
            throw new AssertionError();
        }
        if (!this.getRange(n, valueFilter, range)) {
            return true;
        }
        if (rangeOption == RangeOption.NORMAL) {
            return false;
        }
        int n3 = rangeOption == RangeOption.FIXED_ALL_SURROGATES ? 57343 : 56319;
        int n4 = Range.access$000(range);
        if (n4 < 55295 || n > n3) {
            return false;
        }
        if (Range.access$100(range) == n2) {
            if (n4 >= n3) {
                return false;
            }
        } else {
            if (n <= 55295) {
                Range.access$002(range, 55295);
                return false;
            }
            Range.access$102(range, n2);
            if (n4 > n3) {
                Range.access$002(range, n3);
                return false;
            }
        }
        if (this.getRange(n3 + 1, valueFilter, range) && Range.access$100(range) == n2) {
            Range.access$202(range, n);
            return false;
        }
        Range.access$202(range, n);
        Range.access$002(range, n3);
        Range.access$102(range, n2);
        return false;
    }

    @Override
    public Iterator<Range> iterator() {
        return new RangeIterator(this, null);
    }

    public StringIterator stringIterator(CharSequence charSequence, int n) {
        return new StringIterator(this, charSequence, n);
    }

    public class StringIterator {
        @Deprecated
        protected CharSequence s;
        @Deprecated
        protected int sIndex;
        @Deprecated
        protected int c;
        @Deprecated
        protected int value;
        final CodePointMap this$0;

        @Deprecated
        protected StringIterator(CodePointMap codePointMap, CharSequence charSequence, int n) {
            this.this$0 = codePointMap;
            this.s = charSequence;
            this.sIndex = n;
            this.c = -1;
            this.value = 0;
        }

        public void reset(CharSequence charSequence, int n) {
            this.s = charSequence;
            this.sIndex = n;
            this.c = -1;
            this.value = 0;
        }

        public boolean next() {
            if (this.sIndex >= this.s.length()) {
                return true;
            }
            this.c = Character.codePointAt(this.s, this.sIndex);
            this.sIndex += Character.charCount(this.c);
            this.value = this.this$0.get(this.c);
            return false;
        }

        public boolean previous() {
            if (this.sIndex <= 0) {
                return true;
            }
            this.c = Character.codePointBefore(this.s, this.sIndex);
            this.sIndex -= Character.charCount(this.c);
            this.value = this.this$0.get(this.c);
            return false;
        }

        public final int getIndex() {
            return this.sIndex;
        }

        public final int getCodePoint() {
            return this.c;
        }

        public final int getValue() {
            return this.value;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class RangeIterator
    implements Iterator<Range> {
        private Range range;
        final CodePointMap this$0;

        private RangeIterator(CodePointMap codePointMap) {
            this.this$0 = codePointMap;
            this.range = new Range();
        }

        @Override
        public boolean hasNext() {
            return -1 <= Range.access$000(this.range) && Range.access$000(this.range) < 0x10FFFF;
        }

        @Override
        public Range next() {
            if (this.this$0.getRange(Range.access$000(this.range) + 1, null, this.range)) {
                return this.range;
            }
            throw new NoSuchElementException();
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object next() {
            return this.next();
        }

        RangeIterator(CodePointMap codePointMap, 1 var2_2) {
            this(codePointMap);
        }
    }

    public static final class Range {
        private int start = -1;
        private int end = -1;
        private int value = 0;

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        public int getValue() {
            return this.value;
        }

        public void set(int n, int n2, int n3) {
            this.start = n;
            this.end = n2;
            this.value = n3;
        }

        static int access$000(Range range) {
            return range.end;
        }

        static int access$100(Range range) {
            return range.value;
        }

        static int access$002(Range range, int n) {
            range.end = n;
            return range.end;
        }

        static int access$102(Range range, int n) {
            range.value = n;
            return range.value;
        }

        static int access$202(Range range, int n) {
            range.start = n;
            return range.start;
        }
    }

    public static interface ValueFilter {
        public int apply(int var1);
    }

    public static enum RangeOption {
        NORMAL,
        FIXED_LEAD_SURROGATES,
        FIXED_ALL_SURROGATES;

    }
}

