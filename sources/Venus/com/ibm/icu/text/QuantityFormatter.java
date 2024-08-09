/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.text.SimpleFormatter;
import java.text.FieldPosition;

class QuantityFormatter {
    private final SimpleFormatter[] templates = new SimpleFormatter[StandardPlural.COUNT];
    static final boolean $assertionsDisabled = !QuantityFormatter.class.desiredAssertionStatus();

    public void addIfAbsent(CharSequence charSequence, String string) {
        int n = StandardPlural.indexFromString(charSequence);
        if (this.templates[n] != null) {
            return;
        }
        this.templates[n] = SimpleFormatter.compileMinMaxArguments(string, 0, 1);
    }

    public boolean isValid() {
        return this.templates[StandardPlural.OTHER_INDEX] != null;
    }

    public String format(double d, NumberFormat numberFormat, PluralRules pluralRules) {
        String string = numberFormat.format(d);
        StandardPlural standardPlural = QuantityFormatter.selectPlural(d, numberFormat, pluralRules);
        SimpleFormatter simpleFormatter = this.templates[standardPlural.ordinal()];
        if (simpleFormatter == null) {
            simpleFormatter = this.templates[StandardPlural.OTHER_INDEX];
            if (!$assertionsDisabled && simpleFormatter == null) {
                throw new AssertionError();
            }
        }
        return simpleFormatter.format(string);
    }

    public SimpleFormatter getByVariant(CharSequence charSequence) {
        if (!$assertionsDisabled && !this.isValid()) {
            throw new AssertionError();
        }
        int n = StandardPlural.indexOrOtherIndexFromString(charSequence);
        SimpleFormatter simpleFormatter = this.templates[n];
        return simpleFormatter == null && n != StandardPlural.OTHER_INDEX ? this.templates[StandardPlural.OTHER_INDEX] : simpleFormatter;
    }

    public static StandardPlural selectPlural(double d, NumberFormat numberFormat, PluralRules pluralRules) {
        String string = numberFormat instanceof DecimalFormat ? pluralRules.select(((DecimalFormat)numberFormat).getFixedDecimal(d)) : pluralRules.select(d);
        return StandardPlural.orOtherFromString(string);
    }

    public static StringBuilder format(String string, CharSequence charSequence, StringBuilder stringBuilder, FieldPosition fieldPosition) {
        int[] nArray = new int[1];
        SimpleFormatterImpl.formatAndAppend(string, stringBuilder, nArray, charSequence);
        if (fieldPosition.getBeginIndex() != 0 || fieldPosition.getEndIndex() != 0) {
            if (nArray[0] >= 0) {
                fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + nArray[0]);
                fieldPosition.setEndIndex(fieldPosition.getEndIndex() + nArray[0]);
            } else {
                fieldPosition.setBeginIndex(0);
                fieldPosition.setEndIndex(0);
            }
        }
        return stringBuilder;
    }
}

