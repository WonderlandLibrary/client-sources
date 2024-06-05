package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.impl.*;

class QuantityFormatter
{
    private final SimpleFormatter[] templates;
    
    public QuantityFormatter() {
        this.templates = new SimpleFormatter[StandardPlural.COUNT];
    }
    
    public void addIfAbsent(final CharSequence variant, final String template) {
        final int idx = StandardPlural.indexFromString(variant);
        if (this.templates[idx] != null) {
            return;
        }
        this.templates[idx] = SimpleFormatter.compileMinMaxArguments(template, 0, 1);
    }
    
    public boolean isValid() {
        return this.templates[StandardPlural.OTHER_INDEX] != null;
    }
    
    public String format(final double number, final NumberFormat numberFormat, final PluralRules pluralRules) {
        final String formatStr = numberFormat.format(number);
        final StandardPlural p = selectPlural(number, numberFormat, pluralRules);
        SimpleFormatter formatter = this.templates[p.ordinal()];
        if (formatter == null) {
            formatter = this.templates[StandardPlural.OTHER_INDEX];
            assert formatter != null;
        }
        return formatter.format(formatStr);
    }
    
    public SimpleFormatter getByVariant(final CharSequence variant) {
        assert this.isValid();
        final int idx = StandardPlural.indexOrOtherIndexFromString(variant);
        final SimpleFormatter template = this.templates[idx];
        return (template == null && idx != StandardPlural.OTHER_INDEX) ? this.templates[StandardPlural.OTHER_INDEX] : template;
    }
    
    public static StandardPlural selectPlural(final double number, final NumberFormat numberFormat, final PluralRules rules) {
        String pluralKeyword;
        if (numberFormat instanceof DecimalFormat) {
            pluralKeyword = rules.select(((DecimalFormat)numberFormat).getFixedDecimal(number));
        }
        else {
            pluralKeyword = rules.select(number);
        }
        return StandardPlural.orOtherFromString(pluralKeyword);
    }
    
    public static StandardPlural selectPlural(final Number number, final NumberFormat fmt, final PluralRules rules, final StringBuffer formattedNumber, final FieldPosition pos) {
        final UFieldPosition fpos = new UFieldPosition(pos.getFieldAttribute(), pos.getField());
        fmt.format(number, formattedNumber, fpos);
        final PluralRules.FixedDecimal fd = new PluralRules.FixedDecimal(number.doubleValue(), fpos.getCountVisibleFractionDigits(), fpos.getFractionDigits());
        final String pluralKeyword = rules.select(fd);
        pos.setBeginIndex(fpos.getBeginIndex());
        pos.setEndIndex(fpos.getEndIndex());
        return StandardPlural.orOtherFromString(pluralKeyword);
    }
    
    public static StringBuilder format(final String compiledPattern, final CharSequence value, final StringBuilder appendTo, final FieldPosition pos) {
        final int[] offsets = { 0 };
        SimpleFormatterImpl.formatAndAppend(compiledPattern, appendTo, offsets, value);
        if (pos.getBeginIndex() != 0 || pos.getEndIndex() != 0) {
            if (offsets[0] >= 0) {
                pos.setBeginIndex(pos.getBeginIndex() + offsets[0]);
                pos.setEndIndex(pos.getEndIndex() + offsets[0]);
            }
            else {
                pos.setBeginIndex(0);
                pos.setEndIndex(0);
            }
        }
        return appendTo;
    }
}
