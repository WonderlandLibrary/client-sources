package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.number.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class ParsedNumber
{
    public DecimalQuantity_DualStorageBCD quantity;
    public int charEnd;
    public int flags;
    public String prefix;
    public String suffix;
    public String currencyCode;
    public static final int FLAG_NEGATIVE = 1;
    public static final int FLAG_PERCENT = 2;
    public static final int FLAG_PERMILLE = 4;
    public static final int FLAG_HAS_EXPONENT = 8;
    public static final int FLAG_HAS_DECIMAL_SEPARATOR = 32;
    public static final int FLAG_NAN = 64;
    public static final int FLAG_INFINITY = 128;
    public static final int FLAG_FAIL = 256;
    public static final Comparator<ParsedNumber> COMPARATOR;
    
    public ParsedNumber() {
        this.clear();
    }
    
    public void clear() {
        this.quantity = null;
        this.charEnd = 0;
        this.flags = 0;
        this.prefix = null;
        this.suffix = null;
        this.currencyCode = null;
    }
    
    public void copyFrom(final ParsedNumber other) {
        this.quantity = ((other.quantity == null) ? null : ((DecimalQuantity_DualStorageBCD)other.quantity.createCopy()));
        this.charEnd = other.charEnd;
        this.flags = other.flags;
        this.prefix = other.prefix;
        this.suffix = other.suffix;
        this.currencyCode = other.currencyCode;
    }
    
    public void setCharsConsumed(final StringSegment segment) {
        this.charEnd = segment.getOffset();
    }
    
    public void postProcess() {
        if (this.quantity != null && 0x0 != (this.flags & 0x1)) {
            this.quantity.negate();
        }
    }
    
    public boolean success() {
        return this.charEnd > 0 && 0x0 == (this.flags & 0x100);
    }
    
    public boolean seenNumber() {
        return this.quantity != null || 0x0 != (this.flags & 0x40) || 0x0 != (this.flags & 0x80);
    }
    
    public Number getNumber() {
        return this.getNumber(0);
    }
    
    public Number getNumber(final int parseFlags) {
        final boolean sawNaN = 0x0 != (this.flags & 0x40);
        final boolean sawInfinity = 0x0 != (this.flags & 0x80);
        final boolean forceBigDecimal = 0x0 != (parseFlags & 0x1000);
        final boolean integerOnly = 0x0 != (parseFlags & 0x10);
        if (sawNaN) {
            return Double.NaN;
        }
        if (sawInfinity) {
            if (0x0 != (this.flags & 0x1)) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        else {
            assert this.quantity != null;
            if (this.quantity.isZero() && this.quantity.isNegative() && !integerOnly) {
                return -0.0;
            }
            if (this.quantity.fitsInLong() && !forceBigDecimal) {
                return this.quantity.toLong(false);
            }
            return this.quantity.toBigDecimal();
        }
    }
    
    boolean isBetterThan(final ParsedNumber other) {
        return ParsedNumber.COMPARATOR.compare(this, other) > 0;
    }
    
    static {
        COMPARATOR = new Comparator<ParsedNumber>() {
            @Override
            public int compare(final ParsedNumber o1, final ParsedNumber o2) {
                return o1.charEnd - o2.charEnd;
            }
        };
    }
}
