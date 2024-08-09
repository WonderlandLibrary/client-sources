/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.number.DecimalFormatProperties;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.PatternStringParser;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;

public class Grouper {
    private static final Grouper GROUPER_NEVER;
    private static final Grouper GROUPER_MIN2;
    private static final Grouper GROUPER_AUTO;
    private static final Grouper GROUPER_ON_ALIGNED;
    private static final Grouper GROUPER_WESTERN;
    private static final Grouper GROUPER_INDIC;
    private static final Grouper GROUPER_WESTERN_MIN2;
    private static final Grouper GROUPER_INDIC_MIN2;
    private final short grouping1;
    private final short grouping2;
    private final short minGrouping;
    static final boolean $assertionsDisabled;

    public static Grouper forStrategy(NumberFormatter.GroupingStrategy groupingStrategy) {
        switch (1.$SwitchMap$com$ibm$icu$number$NumberFormatter$GroupingStrategy[groupingStrategy.ordinal()]) {
            case 1: {
                return GROUPER_NEVER;
            }
            case 2: {
                return GROUPER_MIN2;
            }
            case 3: {
                return GROUPER_AUTO;
            }
            case 4: {
                return GROUPER_ON_ALIGNED;
            }
            case 5: {
                return GROUPER_WESTERN;
            }
        }
        throw new AssertionError();
    }

    public static Grouper forProperties(DecimalFormatProperties decimalFormatProperties) {
        if (!decimalFormatProperties.getGroupingUsed()) {
            return GROUPER_NEVER;
        }
        short s = (short)decimalFormatProperties.getGroupingSize();
        short s2 = (short)decimalFormatProperties.getSecondaryGroupingSize();
        short s3 = (short)decimalFormatProperties.getMinimumGroupingDigits();
        s = s > 0 ? s : (s2 > 0 ? s2 : s);
        s2 = s2 > 0 ? s2 : s;
        return Grouper.getInstance(s, s2, s3);
    }

    public static Grouper getInstance(short s, short s2, short s3) {
        if (s == -1) {
            return GROUPER_NEVER;
        }
        if (s == 3 && s2 == 3 && s3 == 1) {
            return GROUPER_WESTERN;
        }
        if (s == 3 && s2 == 2 && s3 == 1) {
            return GROUPER_INDIC;
        }
        if (s == 3 && s2 == 3 && s3 == 2) {
            return GROUPER_WESTERN_MIN2;
        }
        if (s == 3 && s2 == 2 && s3 == 2) {
            return GROUPER_INDIC_MIN2;
        }
        return new Grouper(s, s2, s3);
    }

    private static short getMinGroupingForLocale(ULocale uLocale) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        String string = iCUResourceBundle.getStringWithFallback("NumberElements/minimumGroupingDigits");
        return Short.valueOf(string);
    }

    private Grouper(short s, short s2, short s3) {
        this.grouping1 = s;
        this.grouping2 = s2;
        this.minGrouping = s3;
    }

    public Grouper withLocaleData(ULocale uLocale, PatternStringParser.ParsedPatternInfo parsedPatternInfo) {
        if (this.grouping1 != -2 && this.grouping1 != -4) {
            return this;
        }
        short s = (short)(parsedPatternInfo.positive.groupingSizes & 0xFFFFL);
        short s2 = (short)(parsedPatternInfo.positive.groupingSizes >>> 16 & 0xFFFFL);
        short s3 = (short)(parsedPatternInfo.positive.groupingSizes >>> 32 & 0xFFFFL);
        if (s2 == -1) {
            short s4 = s = this.grouping1 == -4 ? (short)3 : -1;
        }
        if (s3 == -1) {
            s2 = s;
        }
        short s5 = this.minGrouping == -2 ? Grouper.getMinGroupingForLocale(uLocale) : (this.minGrouping == -3 ? (short)Math.max(2, Grouper.getMinGroupingForLocale(uLocale)) : this.minGrouping);
        return Grouper.getInstance(s, s2, s5);
    }

    public boolean groupAtPosition(int n, DecimalQuantity decimalQuantity) {
        if (!($assertionsDisabled || this.grouping1 != -2 && this.grouping1 != -4)) {
            throw new AssertionError();
        }
        if (this.grouping1 == -1 || this.grouping1 == 0) {
            return true;
        }
        return (n -= this.grouping1) >= 0 && n % this.grouping2 == 0 && decimalQuantity.getUpperDisplayMagnitude() - this.grouping1 + 1 >= this.minGrouping;
    }

    public short getPrimary() {
        return this.grouping1;
    }

    public short getSecondary() {
        return this.grouping2;
    }

    static {
        $assertionsDisabled = !Grouper.class.desiredAssertionStatus();
        GROUPER_NEVER = new Grouper(-1, -1, -2);
        GROUPER_MIN2 = new Grouper(-2, -2, -3);
        GROUPER_AUTO = new Grouper(-2, -2, -2);
        GROUPER_ON_ALIGNED = new Grouper(-4, -4, 1);
        GROUPER_WESTERN = new Grouper(3, 3, 1);
        GROUPER_INDIC = new Grouper(3, 2, 1);
        GROUPER_WESTERN_MIN2 = new Grouper(3, 3, 2);
        GROUPER_INDIC_MIN2 = new Grouper(3, 2, 2);
    }
}

