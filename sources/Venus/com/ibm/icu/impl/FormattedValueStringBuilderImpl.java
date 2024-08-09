/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.UnicodeSet;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;

public class FormattedValueStringBuilderImpl {
    static final boolean $assertionsDisabled = !FormattedValueStringBuilderImpl.class.desiredAssertionStatus();

    public static boolean nextFieldPosition(FormattedStringBuilder formattedStringBuilder, FieldPosition fieldPosition) {
        Format.Field field = fieldPosition.getFieldAttribute();
        if (field == null) {
            if (fieldPosition.getField() == 0) {
                field = NumberFormat.Field.INTEGER;
            } else if (fieldPosition.getField() == 1) {
                field = NumberFormat.Field.FRACTION;
            } else {
                return true;
            }
        }
        if (!(field instanceof NumberFormat.Field)) {
            throw new IllegalArgumentException("You must pass an instance of com.ibm.icu.text.NumberFormat.Field as your FieldPosition attribute.  You passed: " + field.getClass().toString());
        }
        ConstrainedFieldPosition constrainedFieldPosition = new ConstrainedFieldPosition();
        constrainedFieldPosition.constrainField(field);
        constrainedFieldPosition.setState(field, null, fieldPosition.getBeginIndex(), fieldPosition.getEndIndex());
        if (FormattedValueStringBuilderImpl.nextPosition(formattedStringBuilder, constrainedFieldPosition, null)) {
            fieldPosition.setBeginIndex(constrainedFieldPosition.getStart());
            fieldPosition.setEndIndex(constrainedFieldPosition.getLimit());
            return false;
        }
        if (field == NumberFormat.Field.FRACTION && fieldPosition.getEndIndex() == 0) {
            int n;
            boolean bl = false;
            for (n = formattedStringBuilder.zero; n < formattedStringBuilder.zero + formattedStringBuilder.length; ++n) {
                if (FormattedValueStringBuilderImpl.isIntOrGroup(formattedStringBuilder.fields[n]) || formattedStringBuilder.fields[n] == NumberFormat.Field.DECIMAL_SEPARATOR) {
                    bl = true;
                    continue;
                }
                if (bl) break;
            }
            fieldPosition.setBeginIndex(n - formattedStringBuilder.zero);
            fieldPosition.setEndIndex(n - formattedStringBuilder.zero);
        }
        return true;
    }

    public static AttributedCharacterIterator toCharacterIterator(FormattedStringBuilder formattedStringBuilder, Format.Field field) {
        ConstrainedFieldPosition constrainedFieldPosition = new ConstrainedFieldPosition();
        AttributedString attributedString = new AttributedString(formattedStringBuilder.toString());
        while (FormattedValueStringBuilderImpl.nextPosition(formattedStringBuilder, constrainedFieldPosition, field)) {
            attributedString.addAttribute(constrainedFieldPosition.getField(), constrainedFieldPosition.getField(), constrainedFieldPosition.getStart(), constrainedFieldPosition.getLimit());
        }
        return attributedString.getIterator();
    }

    public static boolean nextPosition(FormattedStringBuilder formattedStringBuilder, ConstrainedFieldPosition constrainedFieldPosition, Format.Field field) {
        int n = -1;
        NullField nullField = null;
        for (int i = formattedStringBuilder.zero + constrainedFieldPosition.getLimit(); i <= formattedStringBuilder.zero + formattedStringBuilder.length; ++i) {
            int n2;
            NullField nullField2;
            Format.Field field2 = nullField2 = i < formattedStringBuilder.zero + formattedStringBuilder.length ? formattedStringBuilder.fields[i] : NullField.END;
            if (nullField != null) {
                if (nullField == nullField2) continue;
                n2 = i - formattedStringBuilder.zero;
                if (nullField != NumberFormat.Field.GROUPING_SEPARATOR) {
                    n2 = FormattedValueStringBuilderImpl.trimBack(formattedStringBuilder, n2);
                }
                if (n2 <= n) {
                    n = -1;
                    nullField = null;
                    --i;
                    continue;
                }
                int n3 = n;
                if (nullField != NumberFormat.Field.GROUPING_SEPARATOR) {
                    n3 = FormattedValueStringBuilderImpl.trimFront(formattedStringBuilder, n3);
                }
                constrainedFieldPosition.setState(nullField, null, n3, n2);
                return false;
            }
            if (constrainedFieldPosition.matchesField(NumberFormat.Field.INTEGER, null) && i > formattedStringBuilder.zero && i - formattedStringBuilder.zero > constrainedFieldPosition.getLimit() && FormattedValueStringBuilderImpl.isIntOrGroup(formattedStringBuilder.fields[i - 1]) && !FormattedValueStringBuilderImpl.isIntOrGroup(nullField2)) {
                for (n2 = i - 1; n2 >= formattedStringBuilder.zero && FormattedValueStringBuilderImpl.isIntOrGroup(formattedStringBuilder.fields[n2]); --n2) {
                }
                constrainedFieldPosition.setState(NumberFormat.Field.INTEGER, null, n2 - formattedStringBuilder.zero + 1, i - formattedStringBuilder.zero);
                return false;
            }
            if (field != null && constrainedFieldPosition.matchesField(field, null) && i > formattedStringBuilder.zero && (i - formattedStringBuilder.zero > constrainedFieldPosition.getLimit() || constrainedFieldPosition.getField() != field) && FormattedValueStringBuilderImpl.isNumericField(formattedStringBuilder.fields[i - 1]) && !FormattedValueStringBuilderImpl.isNumericField(nullField2)) {
                for (n2 = i - 1; n2 >= formattedStringBuilder.zero && FormattedValueStringBuilderImpl.isNumericField(formattedStringBuilder.fields[n2]); --n2) {
                }
                constrainedFieldPosition.setState(field, null, n2 - formattedStringBuilder.zero + 1, i - formattedStringBuilder.zero);
                return false;
            }
            if (nullField2 == NumberFormat.Field.INTEGER) {
                nullField2 = null;
            }
            if (nullField2 == null || nullField2 == NullField.END || !constrainedFieldPosition.matchesField(nullField2, null)) continue;
            n = i - formattedStringBuilder.zero;
            nullField = nullField2;
        }
        if (!$assertionsDisabled && nullField != null) {
            throw new AssertionError();
        }
        return true;
    }

    private static boolean isIntOrGroup(Format.Field field) {
        return field == NumberFormat.Field.INTEGER || field == NumberFormat.Field.GROUPING_SEPARATOR;
    }

    private static boolean isNumericField(Format.Field field) {
        return field == null || NumberFormat.Field.class.isAssignableFrom(field.getClass());
    }

    private static int trimBack(FormattedStringBuilder formattedStringBuilder, int n) {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES).spanBack(formattedStringBuilder, n, UnicodeSet.SpanCondition.CONTAINED);
    }

    private static int trimFront(FormattedStringBuilder formattedStringBuilder, int n) {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES).span(formattedStringBuilder, n, UnicodeSet.SpanCondition.CONTAINED);
    }

    static class NullField
    extends Format.Field {
        private static final long serialVersionUID = 1L;
        static final NullField END = new NullField("end");

        private NullField(String string) {
            super(string);
        }
    }
}

