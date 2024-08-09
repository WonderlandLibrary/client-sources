/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.text.ConstrainedFieldPosition;
import com.ibm.icu.text.FormattedValue;
import com.ibm.icu.text.PluralRules;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.util.Arrays;

public class FormattedNumber
implements FormattedValue {
    final FormattedStringBuilder string;
    final DecimalQuantity fq;

    FormattedNumber(FormattedStringBuilder formattedStringBuilder, DecimalQuantity decimalQuantity) {
        this.string = formattedStringBuilder;
        this.fq = decimalQuantity;
    }

    @Override
    public String toString() {
        return this.string.toString();
    }

    @Override
    public int length() {
        return this.string.length();
    }

    @Override
    public char charAt(int n) {
        return this.string.charAt(n);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.string.subString(n, n2);
    }

    @Override
    public <A extends Appendable> A appendTo(A a) {
        return Utility.appendTo(this.string, a);
    }

    @Override
    public boolean nextPosition(ConstrainedFieldPosition constrainedFieldPosition) {
        return FormattedValueStringBuilderImpl.nextPosition(this.string, constrainedFieldPosition, null);
    }

    @Override
    public AttributedCharacterIterator toCharacterIterator() {
        return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, null);
    }

    public boolean nextFieldPosition(FieldPosition fieldPosition) {
        this.fq.populateUFieldPosition(fieldPosition);
        return FormattedValueStringBuilderImpl.nextFieldPosition(this.string, fieldPosition);
    }

    public BigDecimal toBigDecimal() {
        return this.fq.toBigDecimal();
    }

    @Deprecated
    public PluralRules.IFixedDecimal getFixedDecimal() {
        return this.fq;
    }

    public int hashCode() {
        return Arrays.hashCode(this.string.toCharArray()) ^ Arrays.hashCode(this.string.toFieldArray()) ^ this.fq.toBigDecimal().hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof FormattedNumber)) {
            return true;
        }
        FormattedNumber formattedNumber = (FormattedNumber)object;
        return Arrays.equals(this.string.toCharArray(), formattedNumber.string.toCharArray()) && Arrays.equals(this.string.toFieldArray(), formattedNumber.string.toFieldArray()) && this.fq.toBigDecimal().equals(formattedNumber.fq.toBigDecimal());
    }
}

