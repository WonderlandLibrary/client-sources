/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.ConstrainedFieldPosition;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.util.List;

public class FormattedValueFieldPositionIteratorImpl {
    private FormattedValueFieldPositionIteratorImpl() {
    }

    public static boolean nextPosition(List<FieldPosition> list, ConstrainedFieldPosition constrainedFieldPosition) {
        int n;
        int n2 = list.size();
        for (n = (int)constrainedFieldPosition.getInt64IterationContext(); n < n2; ++n) {
            FieldPosition fieldPosition = list.get(n);
            Format.Field field = fieldPosition.getFieldAttribute();
            Integer n3 = null;
            if (field instanceof FieldWithValue) {
                n3 = ((FieldWithValue)field).value;
                field = ((FieldWithValue)field).field;
            }
            if (!constrainedFieldPosition.matchesField(field, n3)) continue;
            int n4 = fieldPosition.getBeginIndex();
            int n5 = fieldPosition.getEndIndex();
            constrainedFieldPosition.setState(field, n3, n4, n5);
            break;
        }
        constrainedFieldPosition.setInt64IterationContext(n == n2 ? (long)n : (long)(n + 1));
        return n < n2;
    }

    public static AttributedCharacterIterator toCharacterIterator(CharSequence charSequence, List<FieldPosition> list) {
        AttributedString attributedString = new AttributedString(charSequence.toString());
        for (int i = 0; i < list.size(); ++i) {
            Format.Field field;
            FieldPosition fieldPosition = list.get(i);
            Serializable serializable = field = fieldPosition.getFieldAttribute();
            if (field instanceof FieldWithValue) {
                serializable = Integer.valueOf(((FieldWithValue)field).value);
                field = ((FieldWithValue)field).field;
            }
            attributedString.addAttribute(field, serializable, fieldPosition.getBeginIndex(), fieldPosition.getEndIndex());
        }
        return attributedString.getIterator();
    }

    public static void addOverlapSpans(List<FieldPosition> list, Format.Field field, int n) {
        int n2 = Integer.MAX_VALUE;
        int n3 = 0;
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        int n6 = list.size();
        block0: for (int i = 0; i < n6; ++i) {
            FieldPosition fieldPosition = list.get(i);
            for (int j = i + 1; j < n6; ++j) {
                FieldPosition fieldPosition2 = list.get(j);
                if (fieldPosition.getFieldAttribute() != fieldPosition2.getFieldAttribute()) continue;
                n2 = Math.min(n2, fieldPosition.getBeginIndex());
                n3 = Math.max(n3, fieldPosition.getEndIndex());
                n4 = Math.min(n4, fieldPosition2.getBeginIndex());
                n5 = Math.max(n5, fieldPosition2.getEndIndex());
                continue block0;
            }
        }
        if (n2 != Integer.MAX_VALUE) {
            FieldPosition fieldPosition = new FieldPosition(new FieldWithValue(field, n));
            fieldPosition.setBeginIndex(n2);
            fieldPosition.setEndIndex(n3);
            list.add(fieldPosition);
            fieldPosition = new FieldPosition(new FieldWithValue(field, 1 - n));
            fieldPosition.setBeginIndex(n4);
            fieldPosition.setEndIndex(n5);
            list.add(fieldPosition);
        }
    }

    public static void sort(List<FieldPosition> list) {
        boolean bl;
        int n = list.size();
        do {
            bl = true;
            for (int i = 0; i < n - 1; ++i) {
                FieldPosition fieldPosition = list.get(i);
                FieldPosition fieldPosition2 = list.get(i + 1);
                long l = 0L;
                if (fieldPosition.getBeginIndex() != fieldPosition2.getBeginIndex()) {
                    l = fieldPosition2.getBeginIndex() - fieldPosition.getBeginIndex();
                } else if (fieldPosition.getEndIndex() != fieldPosition2.getEndIndex()) {
                    l = fieldPosition.getEndIndex() - fieldPosition2.getEndIndex();
                } else if (fieldPosition.getFieldAttribute() != fieldPosition2.getFieldAttribute()) {
                    boolean bl2 = fieldPosition.getFieldAttribute() instanceof FieldWithValue;
                    boolean bl3 = fieldPosition2.getFieldAttribute() instanceof FieldWithValue;
                    l = bl2 && !bl3 ? 1L : (bl3 && !bl2 ? -1L : (long)(fieldPosition.hashCode() - fieldPosition2.hashCode()));
                }
                if (l >= 0L) continue;
                bl = false;
                list.set(i, fieldPosition2);
                list.set(i + 1, fieldPosition);
            }
        } while (!bl);
    }

    private static class FieldWithValue
    extends Format.Field {
        private static final long serialVersionUID = -3850076447157793465L;
        public final Format.Field field;
        public final int value;

        public FieldWithValue(Format.Field field, int n) {
            super(field.toString());
            this.field = field;
            this.value = n;
        }
    }
}

