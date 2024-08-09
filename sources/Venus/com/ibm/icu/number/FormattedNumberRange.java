/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.number.NumberRangeFormatter;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.FormattedValue;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.util.Arrays;

public class FormattedNumberRange
implements FormattedValue {
    final FormattedStringBuilder string;
    final DecimalQuantity quantity1;
    final DecimalQuantity quantity2;
    final NumberRangeFormatter.RangeIdentityResult identityResult;

    FormattedNumberRange(FormattedStringBuilder formattedStringBuilder, DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, NumberRangeFormatter.RangeIdentityResult rangeIdentityResult) {
        this.string = formattedStringBuilder;
        this.quantity1 = decimalQuantity;
        this.quantity2 = decimalQuantity2;
        this.identityResult = rangeIdentityResult;
    }

    @Override
    public String toString() {
        return this.string.toString();
    }

    @Override
    public <A extends Appendable> A appendTo(A a) {
        try {
            a.append(this.string);
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
        return a;
    }

    @Override
    public char charAt(int n) {
        return this.string.charAt(n);
    }

    @Override
    public int length() {
        return this.string.length();
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.string.subString(n, n2);
    }

    @Override
    public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
        return FormattedValueStringBuilderImpl.nextPosition(this.string, constrainedFieldPosition, null);
    }

    public boolean nextFieldPosition(FieldPosition fieldPosition) {
        return FormattedValueStringBuilderImpl.nextFieldPosition(this.string, fieldPosition);
    }

    @Override
    public AttributedCharacterIterator toCharacterIterator() {
        return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, null);
    }

    public BigDecimal getFirstBigDecimal() {
        return this.quantity1.toBigDecimal();
    }

    public BigDecimal getSecondBigDecimal() {
        return this.quantity2.toBigDecimal();
    }

    public NumberRangeFormatter.RangeIdentityResult getIdentityResult() {
        return this.identityResult;
    }

    public int hashCode() {
        return Arrays.hashCode(this.string.toCharArray()) ^ Arrays.hashCode(this.string.toFieldArray()) ^ this.quantity1.toBigDecimal().hashCode() ^ this.quantity2.toBigDecimal().hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof FormattedNumberRange)) {
            return true;
        }
        FormattedNumberRange formattedNumberRange = (FormattedNumberRange)object;
        return Arrays.equals(this.string.toCharArray(), formattedNumberRange.string.toCharArray()) && Arrays.equals(this.string.toFieldArray(), formattedNumberRange.string.toFieldArray()) && this.quantity1.toBigDecimal().equals(formattedNumberRange.quantity1.toBigDecimal()) && this.quantity2.toBigDecimal().equals(formattedNumberRange.quantity2.toBigDecimal());
    }
}

