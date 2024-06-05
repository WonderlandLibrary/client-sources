package com.ibm.icu.impl.number;

import com.ibm.icu.number.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public class Grouper
{
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
    
    public static Grouper forStrategy(final NumberFormatter.GroupingStrategy grouping) {
        switch (grouping) {
            case OFF: {
                return Grouper.GROUPER_NEVER;
            }
            case MIN2: {
                return Grouper.GROUPER_MIN2;
            }
            case AUTO: {
                return Grouper.GROUPER_AUTO;
            }
            case ON_ALIGNED: {
                return Grouper.GROUPER_ON_ALIGNED;
            }
            case THOUSANDS: {
                return Grouper.GROUPER_WESTERN;
            }
            default: {
                throw new AssertionError();
            }
        }
    }
    
    public static Grouper forProperties(final DecimalFormatProperties properties) {
        if (!properties.getGroupingUsed()) {
            return Grouper.GROUPER_NEVER;
        }
        short grouping1 = (short)properties.getGroupingSize();
        short grouping2 = (short)properties.getSecondaryGroupingSize();
        final short minGrouping = (short)properties.getMinimumGroupingDigits();
        grouping1 = ((grouping1 > 0) ? grouping1 : ((grouping2 > 0) ? grouping2 : grouping1));
        grouping2 = ((grouping2 > 0) ? grouping2 : grouping1);
        return getInstance(grouping1, grouping2, minGrouping);
    }
    
    public static Grouper getInstance(final short grouping1, final short grouping2, final short minGrouping) {
        if (grouping1 == -1) {
            return Grouper.GROUPER_NEVER;
        }
        if (grouping1 == 3 && grouping2 == 3 && minGrouping == 1) {
            return Grouper.GROUPER_WESTERN;
        }
        if (grouping1 == 3 && grouping2 == 2 && minGrouping == 1) {
            return Grouper.GROUPER_INDIC;
        }
        if (grouping1 == 3 && grouping2 == 3 && minGrouping == 2) {
            return Grouper.GROUPER_WESTERN_MIN2;
        }
        if (grouping1 == 3 && grouping2 == 2 && minGrouping == 2) {
            return Grouper.GROUPER_INDIC_MIN2;
        }
        return new Grouper(grouping1, grouping2, minGrouping);
    }
    
    private static short getMinGroupingForLocale(final ULocale locale) {
        final ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale);
        final String result = resource.getStringWithFallback("NumberElements/minimumGroupingDigits");
        return Short.valueOf(result);
    }
    
    private Grouper(final short grouping1, final short grouping2, final short minGrouping) {
        this.grouping1 = grouping1;
        this.grouping2 = grouping2;
        this.minGrouping = minGrouping;
    }
    
    public Grouper withLocaleData(final ULocale locale, final PatternStringParser.ParsedPatternInfo patternInfo) {
        if (this.grouping1 != -2 && this.grouping1 != -4) {
            return this;
        }
        short grouping1 = (short)(patternInfo.positive.groupingSizes & 0xFFFFL);
        short grouping2 = (short)(patternInfo.positive.groupingSizes >>> 16 & 0xFFFFL);
        final short grouping3 = (short)(patternInfo.positive.groupingSizes >>> 32 & 0xFFFFL);
        if (grouping2 == -1) {
            grouping1 = (short)((this.grouping1 == -4) ? 3 : -1);
        }
        if (grouping3 == -1) {
            grouping2 = grouping1;
        }
        short minGrouping;
        if (this.minGrouping == -2) {
            minGrouping = getMinGroupingForLocale(locale);
        }
        else if (this.minGrouping == -3) {
            minGrouping = (short)Math.max(2, getMinGroupingForLocale(locale));
        }
        else {
            minGrouping = this.minGrouping;
        }
        return getInstance(grouping1, grouping2, minGrouping);
    }
    
    public boolean groupAtPosition(int position, final DecimalQuantity value) {
        assert this.grouping1 != -2 && this.grouping1 != -4;
        if (this.grouping1 == -1 || this.grouping1 == 0) {
            return false;
        }
        position -= this.grouping1;
        return position >= 0 && position % this.grouping2 == 0 && value.getUpperDisplayMagnitude() - this.grouping1 + 1 >= this.minGrouping;
    }
    
    public short getPrimary() {
        return this.grouping1;
    }
    
    public short getSecondary() {
        return this.grouping2;
    }
    
    static {
        GROUPER_NEVER = new Grouper((short)(-1), (short)(-1), (short)(-2));
        GROUPER_MIN2 = new Grouper((short)(-2), (short)(-2), (short)(-3));
        GROUPER_AUTO = new Grouper((short)(-2), (short)(-2), (short)(-2));
        GROUPER_ON_ALIGNED = new Grouper((short)(-4), (short)(-4), (short)1);
        GROUPER_WESTERN = new Grouper((short)3, (short)3, (short)1);
        GROUPER_INDIC = new Grouper((short)3, (short)2, (short)1);
        GROUPER_WESTERN_MIN2 = new Grouper((short)3, (short)3, (short)2);
        GROUPER_INDIC_MIN2 = new Grouper((short)3, (short)2, (short)2);
    }
}
