/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import java.util.Comparator;

public class ParsedNumber {
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
    static final boolean $assertionsDisabled;

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

    public void copyFrom(ParsedNumber parsedNumber) {
        this.quantity = parsedNumber.quantity == null ? null : (DecimalQuantity_DualStorageBCD)parsedNumber.quantity.createCopy();
        this.charEnd = parsedNumber.charEnd;
        this.flags = parsedNumber.flags;
        this.prefix = parsedNumber.prefix;
        this.suffix = parsedNumber.suffix;
        this.currencyCode = parsedNumber.currencyCode;
    }

    public void setCharsConsumed(StringSegment stringSegment) {
        this.charEnd = stringSegment.getOffset();
    }

    public void postProcess() {
        if (this.quantity != null && 0 != (this.flags & 1)) {
            this.quantity.negate();
        }
    }

    public boolean success() {
        return this.charEnd > 0 && 0 == (this.flags & 0x100);
    }

    public boolean seenNumber() {
        return this.quantity != null || 0 != (this.flags & 0x40) || 0 != (this.flags & 0x80);
    }

    public Number getNumber() {
        return this.getNumber(0);
    }

    public Number getNumber(int n) {
        boolean bl;
        boolean bl2 = 0 != (this.flags & 0x40);
        boolean bl3 = 0 != (this.flags & 0x80);
        boolean bl4 = 0 != (n & 0x1000);
        boolean bl5 = bl = 0 != (n & 0x10);
        if (bl2) {
            return Double.NaN;
        }
        if (bl3) {
            if (0 != (this.flags & 1)) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        if (!$assertionsDisabled && this.quantity == null) {
            throw new AssertionError();
        }
        if (this.quantity.isZeroish() && this.quantity.isNegative() && !bl) {
            return -0.0;
        }
        if (this.quantity.fitsInLong() && !bl4) {
            return this.quantity.toLong(true);
        }
        return this.quantity.toBigDecimal();
    }

    boolean isBetterThan(ParsedNumber parsedNumber) {
        return COMPARATOR.compare(this, parsedNumber) > 0;
    }

    static {
        $assertionsDisabled = !ParsedNumber.class.desiredAssertionStatus();
        COMPARATOR = new Comparator<ParsedNumber>(){

            @Override
            public int compare(ParsedNumber parsedNumber, ParsedNumber parsedNumber2) {
                return parsedNumber.charEnd - parsedNumber2.charEnd;
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((ParsedNumber)object, (ParsedNumber)object2);
            }
        };
    }
}

