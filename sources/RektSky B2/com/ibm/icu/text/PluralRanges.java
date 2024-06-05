package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.util.*;
import com.ibm.icu.util.*;

@Deprecated
public final class PluralRanges implements Freezable<PluralRanges>, Comparable<PluralRanges>
{
    private volatile boolean isFrozen;
    private Matrix matrix;
    private boolean[] explicit;
    
    @Deprecated
    public PluralRanges() {
        this.matrix = new Matrix();
        this.explicit = new boolean[StandardPlural.COUNT];
    }
    
    @Deprecated
    public void add(final StandardPlural rangeStart, final StandardPlural rangeEnd, final StandardPlural result) {
        if (this.isFrozen) {
            throw new UnsupportedOperationException();
        }
        this.explicit[result.ordinal()] = true;
        if (rangeStart == null) {
            for (final StandardPlural rs : StandardPlural.values()) {
                if (rangeEnd == null) {
                    for (final StandardPlural re : StandardPlural.values()) {
                        this.matrix.setIfNew(rs, re, result);
                    }
                }
                else {
                    this.explicit[rangeEnd.ordinal()] = true;
                    this.matrix.setIfNew(rs, rangeEnd, result);
                }
            }
        }
        else if (rangeEnd == null) {
            this.explicit[rangeStart.ordinal()] = true;
            for (final StandardPlural re2 : StandardPlural.values()) {
                this.matrix.setIfNew(rangeStart, re2, result);
            }
        }
        else {
            this.explicit[rangeStart.ordinal()] = true;
            this.explicit[rangeEnd.ordinal()] = true;
            this.matrix.setIfNew(rangeStart, rangeEnd, result);
        }
    }
    
    @Deprecated
    public StandardPlural get(final StandardPlural start, final StandardPlural end) {
        final StandardPlural result = this.matrix.get(start, end);
        return (result == null) ? end : result;
    }
    
    @Deprecated
    public boolean isExplicit(final StandardPlural start, final StandardPlural end) {
        return this.matrix.get(start, end) != null;
    }
    
    @Deprecated
    public boolean isExplicitlySet(final StandardPlural count) {
        return this.explicit[count.ordinal()];
    }
    
    @Deprecated
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PluralRanges)) {
            return false;
        }
        final PluralRanges otherPR = (PluralRanges)other;
        return this.matrix.equals(otherPR.matrix) && Arrays.equals(this.explicit, otherPR.explicit);
    }
    
    @Deprecated
    @Override
    public int hashCode() {
        return this.matrix.hashCode();
    }
    
    @Deprecated
    @Override
    public int compareTo(final PluralRanges that) {
        return this.matrix.compareTo(that.matrix);
    }
    
    @Deprecated
    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }
    
    @Deprecated
    @Override
    public PluralRanges freeze() {
        this.isFrozen = true;
        return this;
    }
    
    @Deprecated
    @Override
    public PluralRanges cloneAsThawed() {
        final PluralRanges result = new PluralRanges();
        result.explicit = this.explicit.clone();
        result.matrix = this.matrix.clone();
        return result;
    }
    
    @Deprecated
    @Override
    public String toString() {
        return this.matrix.toString();
    }
    
    private static final class Matrix implements Comparable<Matrix>, Cloneable
    {
        private byte[] data;
        
        Matrix() {
            this.data = new byte[StandardPlural.COUNT * StandardPlural.COUNT];
            for (int i = 0; i < this.data.length; ++i) {
                this.data[i] = -1;
            }
        }
        
        void set(final StandardPlural start, final StandardPlural end, final StandardPlural result) {
            this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()] = (byte)((result == null) ? -1 : ((byte)result.ordinal()));
        }
        
        void setIfNew(final StandardPlural start, final StandardPlural end, final StandardPlural result) {
            final byte old = this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()];
            if (old >= 0) {
                throw new IllegalArgumentException("Previously set value for <" + start + ", " + end + ", " + StandardPlural.VALUES.get(old) + ">");
            }
            this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()] = (byte)((result == null) ? -1 : ((byte)result.ordinal()));
        }
        
        StandardPlural get(final StandardPlural start, final StandardPlural end) {
            final byte result = this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()];
            return (result < 0) ? null : StandardPlural.VALUES.get(result);
        }
        
        StandardPlural endSame(final StandardPlural end) {
            StandardPlural first = null;
            for (final StandardPlural start : StandardPlural.VALUES) {
                final StandardPlural item = this.get(start, end);
                if (item == null) {
                    continue;
                }
                if (first == null) {
                    first = item;
                }
                else {
                    if (first != item) {
                        return null;
                    }
                    continue;
                }
            }
            return first;
        }
        
        StandardPlural startSame(final StandardPlural start, final EnumSet<StandardPlural> endDone, final Output<Boolean> emit) {
            emit.value = false;
            StandardPlural first = null;
            for (final StandardPlural end : StandardPlural.VALUES) {
                final StandardPlural item = this.get(start, end);
                if (item == null) {
                    continue;
                }
                if (first == null) {
                    first = item;
                }
                else {
                    if (first != item) {
                        return null;
                    }
                    if (endDone.contains(end)) {
                        continue;
                    }
                    emit.value = true;
                }
            }
            return first;
        }
        
        @Override
        public int hashCode() {
            int result = 0;
            for (int i = 0; i < this.data.length; ++i) {
                result = result * 37 + this.data[i];
            }
            return result;
        }
        
        @Override
        public boolean equals(final Object other) {
            return other instanceof Matrix && 0 == this.compareTo((Matrix)other);
        }
        
        @Override
        public int compareTo(final Matrix o) {
            for (int i = 0; i < this.data.length; ++i) {
                final int diff = this.data[i] - o.data[i];
                if (diff != 0) {
                    return diff;
                }
            }
            return 0;
        }
        
        public Matrix clone() {
            final Matrix result = new Matrix();
            result.data = this.data.clone();
            return result;
        }
        
        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder();
            for (final StandardPlural i : StandardPlural.values()) {
                for (final StandardPlural j : StandardPlural.values()) {
                    final StandardPlural x = this.get(i, j);
                    if (x != null) {
                        result.append(i + " & " + j + " \u2192 " + x + ";\n");
                    }
                }
            }
            return result.toString();
        }
    }
}
